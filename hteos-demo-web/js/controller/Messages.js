(function () {
    /***
     * @class HteOS.Messages
     * 即时通讯组件
     * */
    var Messages = HteOS.controller.Messages = function () {
        this.onShellRendered = function () {
            this.init();
        }
    };
    Messages.prototype.init = function () {
        var msg = this;
        msg.$el = $(".hte-messager-panel");
        msg.$panel = msg.$el.parents(".hte-side");
        msg.conatctTpl = HteOS.Template
            .compile('<div id="contact-<%=id%>" data-name="<%=name%>" data-id="<%=id%>" class="contact">'
                + '<img src="images/face.png">'
                + '<div class="contact-info">'
                + '<div class="contact-name"><%=name%></div>'
                + '<div class="contact-group"><%=group%></div>'
                + '<span class="label label-primary message-count" style="display:none">0</span>'
                + '</div>' + '</div>');
        msg.messageTpl = HteOS.Template
            .compile('<img src="images/face.png" class="message-face">'
                + '<div class="message-name"><%=from%></div>'
                + '<div class="message">' + '<div class="arrow"></div>'
                + '<%=message%>' + '</div>');
        msg.dialogTpl = HteOS.Template
            .compile('<div class="message-header">'
                + '<h4>与<%=name%>进行聊天&nbsp;<span class="metro-icon-arrow-right-3 smaller dialog-nav-control"></span></h4>'
                + '</div>'
                + '<div class="message-list">'
                + '<div class="message-list-inner"><div>'
                + '</div>'
                + '<div class="message-input-wrapper">'
                + '<div class="input-group">'
                + '<input type="text" class="form-control message-input">'
                + '<div class="input-group-btn">'
                + '<button type="button" class="btn btn-default message-btn">发送</button>'
                + '</div>' + '</div>' + '</div>');
        msg.messages = {};
        //判断是否已经登录
        if (!HteOS.Preference.logon) {
            //$("[data-action]").dock("toggle");
            HteOS.Masker.error(msg.$panel.find(".hte-messager-panel"), {
                transparent: true,
                msg: "游客无法使用即时通讯，请登录",
            });
        } else {
            msg.initEvents();
            msg.connect();
        }
    };

    Messages.prototype.initEvents = function () {
        var me = this;
        $(document.body).on('click', ".contact", function () {
            var c = $(this), id = c.data("id"), name = c.data('name');
            c.find(".message-count").html(0).hide();
            var dialog = me.$panel.find(".messager-dialog[data-to='" + id
                + "']");
            if (dialog.length == 0) {
                var dialog = $('<div id="dialog-'
                    + id
                    + '" class="messager-dialog hte-transition-short" data-to="'
                    + id + '">');
                dialog.html(me.dialogTpl({
                    name: name
                }));
                me.$panel.find(".hte-dock-body").append(dialog);
                dialog.css({
                    right: 0
                });
                var cache = me.messages[id];
                if (cache) {
                    for (var i = 0; i < cache.length; i++) {
                        var messageEl = $('<div class="message-row left">');
                        messageEl.html(me.messageTpl(cache[i]));
                        dialog.find('.message-list-inner').append(messageEl);
                    }
                    delete me.messages[id];
                }
            } else {
                dialog.css({
                    right: 0
                });
            }
        });

        $(document.body).on('click', '.dialog-nav-control', function () {
            $(this).parents(".messager-dialog").css({
                right: -500
            });
        });

        $(document.body).on('keypress', '.message-input', function (event) {
            if (event.keyCode == 13) {
                var input = $(this), val = input.val();
                if (val) {
                    var dialog = $(this).parents(".messager-dialog"), to = dialog
                        .data('to');
                    me.message(val, to, dialog);
                    input.val('');
                }
            }
        });

        // 点击按钮发送消息
        $(document.body).on('click', '.message-btn', function (event) {
            var dialog = $(this).parents(".messager-dialog"), input = dialog
                .find('.message-input'), val = input.val();
            if (val) {
                var to = dialog.data('to');
                me.message(val, to, dialog);
                input.val('');
            }
        });

        $(".discussion-input").keypress(function (event) {
            var input = $(this), val = input.val();
            if (event.keyCode == 13 && val) {
                me.discuss(val);
                $(this).val('');
            }
        });

        $(".discussion-btn").click(function () {
            var input = $(".discussion-input"), val = input.val();
            if (val) {
                me.discuss(val);
                input.val('');
            }
        });
    };

    Messages.prototype.message = function (message, to, dialog) {
        var messageEl = $('<div class="message-row right">');
        messageEl.html(this.messageTpl({
            from: HteOS.Preference.name,
            message: message
        }));
        var inner = dialog.find('.message-list-inner');
        inner.append(messageEl);
        var width = messageEl.width() - 40 - 20
            - messageEl.find(".message").outerWidth();
        messageEl.find(".message").css('margin-left', width);
        if (inner.get(0)) {
            inner.get(0).scrollTop = inner.get(0).scrollHeight;
        }
        this.send({
            type: 'message',
            message: message,
            to: to,
            from: HteOS.Preference.name,
            org: HteOS.Preference.id
        });
    };

    Messages.prototype.discuss = function (message) {
        this.send({
            type: 'discuss',
            message: message
        });
    };

    Messages.prototype.connect = function () {
        var me = this, panel = me.$el;
        if (window.WebSocket) {
            HteOS.Masker.loading(panel, {
                transparent: true,
                msg: '正在连接服务器...'
            });
            var host = window.location.host;
            var port = window.location.port ? '' : (':8080');
            if (host == 'localhost') {
                port = ':8080';
                // 本地测试
            }
            var url = HteOS.wsServer || "ws://localhost:8080/ws/messager";
            me.websocket = new WebSocket(url);
            me.websocket.onmessage = function (message) {
                me.receive.call(me, message);
            }
            me.websocket.onopen = function () {
                HteOS.Masker.unmask(panel);
                me.initEvents();
            }
            me.websocket.onclose = function (e) {
                if (!me.error) {
                    HteOS.Masker.error(panel, {
                        transparent: true,
                        msg: '服务器连接已断开'
                    });
                }
            }
            me.websocket.onerror = function () {
                me.error = true;
                HteOS.Masker.error(panel, {
                    transparent: true,
                    msg: '连接服务器发生错误'
                });
            }
        } else {
            HteOS.Masker.errr(panel, {
                transparent: true,
                msg: '浏览器不支持即时通讯'
            });
        }
    };

    Messages.prototype.notify = function (message) {
        var me = this, isHidden = document.webkitHidden || document.mozHidden
            || document.msHidden || document.hidden || false;
        if (isHidden) {
            // 浏览器标签隐藏则进行桌面提示
            me.showNotifications(message);
        }
        // 查找是否打开的对话面板
        var dialog = me.$panel.find(".messager-dialog[data-to='" + message.org
            + "']");
        // 没有打开对话面板
        if (dialog.length == 0 || dialog.css('right').replace('px', '') < 0) {
            // 更新消息数量
            var contact = $("#contact-" + message.org);
            var count = contact.find(".message-count");
            count.html(parseInt(count.html()) + 1);
            count.show();
        }
        if (dialog.length == 0) {
            // 如果没有生成对话框，则缓存起来
            var cache = me.messages[message.org] || [];
            cache.push(message);
            me.messages[message.org] = cache;
        } else if (dialog.length > 0) {
            // 如果已经生成而且已经展示出来了，则渲染
            var messageEl = $('<div class="message-row left">');
            messageEl.html(me.messageTpl(message));
            var inner = dialog.find('.message-list-inner');
            inner.append(messageEl);
            if (inner.get(0)) {
                inner.get(0).scrollTop = inner.get(0).scrollHeight;
            }
        }
    };

    Messages.prototype.showNotifications = function (data) {
        if (window.webkitNotifications) {
            if (window.webkitNotifications.checkPermission() > 0) {
                window.webkitNotifications.requestPermission();
            }
            var message = data.message;
            if (data.type == 'message') {
                var face = HteOS.basePath + 'images/default.png';
                var title = data.from + "向您发送了消息."
            } else {
                var face = HteOS.basePath + 'images/logo.png';
                var title = "HteOS讨论组消息."
            }
            var notification = window.webkitNotifications.createNotification(
                face, title, message);
            notification.show();
        }
    };

    Messages.prototype.receive = function (message) {
        var me = this;
        var data = JSON.parse(message.data);
        if (data.type == 'online') {
            var onlines = data.onlines;
            for (var i = 0; i < onlines.length; i++) {
                me.add(onlines[i]);
            }
        } else if (data.type == 'message') {
            me.notify(data);
        } else if (data.type == 'join') {
            me.add(data.account);
        } else if (data.type == 'leave') {
            $("#contact-" + data.account).parent().remove();
        } else if (data.type == 'discuss') {
            var isHidden = document.webkitHidden || document.mozHidden
                || document.msHidden || document.hidden || false;
            if (isHidden) {
                // 浏览器标签隐藏则进行桌面提示
                me.showNotifications(message);
            }
            var discuss = $('<div class="message-row">');
            if (data.self == true) {
                discuss.addClass('right');
            } else {
                discuss.addClass('left');
            }
            discuss.html(me.messageTpl(data));
            var list = me.$panel.find('.discussion-list');
            list.append(discuss);
            if (data.self == true) {
                var width = discuss.width() - 40 - 20
                    - discuss.find(".message").outerWidth();
                discuss.find(".message").css('margin-left', width);
            }
            if (list.get(0)) {
                list.get(0).scrollTop = list.get(0).scrollHeight;
            }
        }
    };

    Messages.prototype.add = function (contact) {
        var contactHTML = this.conatctTpl(contact);
        var li = $("<li>").html(contactHTML);
        this.$panel.find(".contact-list").append(li);
    };

    Messages.prototype.send = function (options) {
        this.websocket.send(JSON.stringify(options));
    };
})();