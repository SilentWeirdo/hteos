(function () {
    Date.prototype.format = function () {
        var year = this.getFullYear();
        var month = this.getMonth() + 1;
        var day = this.getDate();
        var hours = this.getHours();
        var minutes = this.getMinutes();
        day = day > 9 ? day : '0' + day;
        month = month > 9 ? month : '0' + month;
        hours = hours > 9 ? hours : '0' + hours;
        minutes = minutes > 9 ? minutes : '0' + minutes;
        return year + '-' + month + '-' + day + " " + hours + ":" + minutes;
    }

    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function (obj) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == obj) {
                    return i;
                }
            }
        }
    }

    Array.prototype.removeAt = function (index) {
        this.splice(index, 1);
    }
    Array.prototype.remove = function (obj) {
        var index = this.indexOf(obj);
        if (index >= 0) {
            this.removeAt(index);
        }
    }
})();

(function ($) {
    $.fn.mask = function (options) {
        var el = $(this);
        var maskEl = el.find(".hte-mask");
        if (maskEl.length == 1) {
            maskEl.html('');
        } else {
            maskEl = $('<div class="hte-mask"></div>');
        }
        var maskElInner = $('<div class="hte-mask-inner"></div>');
        if (!(typeof options == 'object')) {
            options = {
                msg: options
            }
        }
        if (options.transparent) {
            maskEl.addClass('hte-mask-transparent');
        } else {
            maskEl.removeClass('hte-mask-transparent');
        }
        maskEl.append(maskElInner);
        var maskMsgEl;
        if (options.msg) {
            maskMsgEl = $('<div class="hte-mask-msg">'
                + '<img src=\"images/preloader.gif\" style=\"height:18px;margin-right:5px;margin-top:-5px;\"> '
                + options.msg + '</div>');
            maskElInner.append(maskMsgEl);
        }
        el.append(maskEl);
        if (maskMsgEl) {
            maskMsgEl.css("line-height", (maskEl.height()) + 'px');
        }
    }

    $.fn.unmask = function () {
        $(this).find(' > .hte-mask').remove();
    }
})(window.jQuery);

(function ($) {
    "user strict";

    $.messager = {
        tpl: template.compile('<div class="hte-messager-content">'
            + '<div class="hte-messager-title">' + '<h2><%=title%></h2>'
            + '</div>' + '<div class="hte-messager-body"></div>'
            + '<div class="hte-messager-buttons">' + '</div>' + '</div>')
    };

    $.messager.alert = function (title, message, callback) {
        var options = {};
        options.buttons = [{
            cls: 'btn-default',
            key: 'ok',
            text: '确定',
            autofocus: true
        }]
        options.title = title || '';
        options.message = message || '';
        options.callback = callback || null;
        $.messager.message(options);
    }

    $.messager.confirm = function (title, message, callback) {
        var options = {};
        options.buttons = [{
            cls: 'btn-default',
            key: 'yes',
            text: '确定'
        }, {
            cls: 'btn-default',
            key: 'no',
            text: '取消',
            autofocus: true
        }]
        options.title = title || '';
        options.message = message || '';
        options.callback = callback || null;
        $.messager.message(options);
    }

    $.messager.message = function (options) {
        var el = $("<div class='hte-messager'>");
        el.html($.messager.tpl(options));
        el.find(".hte-messager-body").html(options.message);
        var buttons = options.buttons, content = el
            .find(".hte-messager-content");
        if (buttons.length > 0) {
            for (var i = 0; i < buttons.length; i++) {
                var b = buttons[i];
                var button = $("<button class='btn hte-messager-btn'>");
                button.addClass(b.cls);
                button.attr("data-key", b.key);
                button.attr("data-toggle", "hte-messager");
                button.html(b.text);
                if (b.autofocus) {
                    button.attr("autofocus", "autofocus");
                    button.focus();
                }
                button.click(function () {
                    el.remove()
                });
                if (options.callback) {
                    button.click(function () {
                        options.callback
                            .call(this, $(this).data('key'));
                    });
                }
                el.find(".hte-messager-buttons").append(button);
            }
        }
        el.appendTo($(document.body));
        el.show();
        content.css("left", (el.width() - content.outerWidth()) / 2);
        console.log(1);
        content.css("top", (el.height() - content.outerHeight()) / 2);
    }
})(window.jQuery);

(function ($) {
    $.fn.mask = function (options) {
        var el = $(this);
        var maskEl = el.find(".hte-mask");
        if (maskEl.length == 1) {
            maskEl.html('');
        } else {
            maskEl = $('<div class="hte-mask"></div>');
        }
        var maskElInner = $('<div class="hte-mask-inner"></div>');
        if (!(typeof options == 'object')) {
            options = {
                msg: options
            }
        }
        if (options.transparent) {
            maskEl.addClass('hte-mask-transparent');
        } else {
            maskEl.removeClass('hte-mask-transparent');
        }
        if (options.modal === true) {
            maskEl.removeClass('hte-mask-transparent');
        } else {
            maskEl.addClass('hte-mask-transparent');
        }
        maskEl.append(maskElInner);
        var maskMsgEl;
        if (options.msg) {
            maskMsgEl = $('<div class="hte-mask-msg">'
                + '<img src=\"img/preloader.gif\" style=\"height:18px;margin-right:5px;margin-top:-5px;color:#333;\"> '
                + options.msg + '</div>');
            maskElInner.append(maskMsgEl);
        }
        el.append(maskEl);
        if (maskElInner) {
            window.setTimeout(function () {
                maskMsgEl.css("line-height", (el.height()) + 'px');
            }, 100);
        }
    }

    $.fn.unmask = function () {
        $(this).find(' > .hte-mask').remove();
    }
})(window.jQuery);


$(document).ready(function () {
    //$(".dashboard-nav-container").height($(document).height() - 58);
    //$(".dashboard-center").height($(document).height() - 58);
    Index.Nav.load("statistics.html");
    Index.UI.init();
    $(window).resize(function () {
        $(document.body).hide();
        $(".dashboard-nav-container").height($(document).height() - 58);
        //$(".dashboard-center").height($(document).height() - 58);
        $(document.body).show();
    });
});
var Index = {

    UI: {

        init: function () {

            $(document).on("click", "a[data-toggle='nav']", function () {
                $(".dashboard-nav > li.active").removeClass('active');
                Index.Nav.load($(this).data("url"));
                $(this).parent().addClass("active");
            });
            $(document).on("click", "[data-toggle='toggle']", function () {
                Index.Nav.toggle($(this).data("url"));
            });

            $(document).on("click", "[data-dismiss='viewport']", function () {
                Index.Nav.dismiss($(this));
            });
        }

    },

    Nav: {

        dismiss: function (el) {
            var viewport = el.parents(".dashboard-viewport");
            viewport.animate({
                right: 0 - viewport.width()
            }, function () {
                viewport.remove();
                $(".dashboard-viewport").each(function () {
                    var zIndex = $(this).css('z-index');
                    $(this).css("z-index", zIndex + 1);
                });
            });
        },

        load: function (url) {
            $(document.body).mask("正在加载...");
            var xhr = $.ajax({
                url: url,
                type: 'html',
                method: 'get'
            });
            xhr.done(function (html) {
                $(document.body).unmask();
                $(".dashboard-center").html(html);
            });
            xhr.fail(function () {
                $(document.body).unmask();
                $.messager.alert(null, '无法加载资源：' + url);
            });
        },

        toggle: function (url) {
            $(".dashboard-center").mask("正在加载...");
            var xhr = $.ajax({
                url: url,
                type: 'html',
                method: 'get'
            });
            xhr.done(function (html) {
                $(".dashboard-center").unmask();
                var maxZIndex = 0;
                $(".dashboard-viewport").each(function () {
                    var zIndex = $(this).css('z-index');
                    maxZIndex = Math.max(maxZIndex, zIndex);
                    $(this).css("z-index", zIndex - 1);
                });
                var viewport = $("<div class='dashboard-viewport dashboard-viewport-closable'>")
                    .html(html);
                $(".dashboard-center").append(viewport);
                viewport.animate({
                    "right": 0,
                    "z-index": maxZIndex
                });
            });
        }
    }
};
