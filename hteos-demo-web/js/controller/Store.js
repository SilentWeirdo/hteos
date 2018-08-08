(function (win) {

    var store = HteOS.controller.Store = function () {
        this.server = HteOS.server || 'http://localhost:8080';
    };

    store.prototype.preload =function() {
        this.shell.getEl().find(".store-preloader").show();
    }

    store.prototype.preloaded = function() {
        this.shell.getEl().find(".store-preloader").hide();
    }

    store.prototype.onShellRendered = function () {
        this.loadIndex();
        var self = this;

        $("#store-index").click(function () {
            self.loadIndex();
            $(".app-store-nav").find(".active").removeClass("active");
            $(this).parent().addClass("active");
        });

        $(".store-category").click(function (item) {

            self.currentCategory = $(this).data("category");
            self.page = 1;

            var html = HteOS.TemplateManager.get("store/category.html");
            $(".hte-store-body").html(html);
            $("#category-name").text(self.getType(self.currentCategory));

            self.loadCategory($(this).data("category"), 1, true);
            $(".app-store-nav").find(".active").removeClass("active");
            $(this).parent().addClass("active");

            $("#category-load-more-btn").off("click");
            $("#category-load-more-btn").click(function () {
                self.page++;
                self.loadCategory(self.currentCategory, self.page, false)
            });
        });

        $("#search-btn").click(function () {
            self.page = 1;
            self.searchKey = $("#search-key").val();

            if(!self.searchKey){
                return ;
            }

            var html = HteOS.TemplateManager.get("store/search.html");
            $(".hte-store-body").html(html);
            $("#search-key-text").text(self.searchKey);

            self.search(self.searchKey, self.page, true)

            $(".app-store-nav").find(".active").removeClass("active");
            $(this).parent().addClass("active");

            $("#search-load-more-btn").off("click");
            $("#search-load-more-btn").click(function () {
                self.page ++;
                self.search(self.searchKey, self.page, false)
            });

        });
    };

    store.prototype.loadIndex = function () {
        var self = this;
        var html = HteOS.TemplateManager.get("store/index.html");
        $(".hte-store-body").html(html);
        $("[data-channel]").each(function () {
            self.loadChannel($(this));
        });
    }

    var tpl = '<div data-id="{id}" class="col-md-3 col-sm-6 col-xs-12 app-wrapper">'
        + '<div class="app-wrapper-inner">'
        + '<img class="app-image" src="{image}">'
        + '<div class="app-overview">'
        + '<img class="app-icon" src="{icon}">'
        + '<div class="app-name">{name}</div>'
        + '<div class="app-score">{score}</div>'
        + '</div>'
        + '</div>'
        + '</div>';

    store.prototype.page = 1;

    store.prototype.size = 4;

    store.prototype.setTotal = function (total) {
        this.total = total;
        $("#total").html(total);
    }

    // 加载分类
    store.prototype.loadCategory = function (category, page, render) {

        var self = this;
        self.preload();
        $.getJSON(this.server + "/store/category/" + category, {
            size: self.size,
            page: page
        }).success(function (data) {
            self.preloaded();
            self.setTotal(data.totalElements);
            var rows = data.content;
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var app = self.render(rows[i]);
                    app.click(function () {
                        HteOS.TaskManager.start({
                            id: new Date().getTime(),
                            name: '应用详情',
                            shell: 'window',
                            templateUrl: "tpl/store-detail.html",
                            controller: "HteOS.controller.StoreDetail",
                            icon: 'images/logo.png',
                            params: {
                                id: $(this).data("id")
                            }
                        });
                    });
                    $(".store-category-body").append(app);
                    app.fadeIn();
                }
            }
            var total = Math.ceil(self.total / self.size);
            if (page >= total) {
                $("#category-load-more").hide();
                //$("#category-load-more-btn").attr("disabled", "disabled");
                //$("#category-load-text").html("全部加载了");
            } else {
                $("#category-load-more").show();
                $("#category-load-text").html("加载更多");
                $("#category-load-more-btn").removeAttr("disabled");
            }
        }).error(function () {
            self.preloaded();
            $(".hte-store-category-body").html("<div class='app-channel-preloader'>"
                + "<i class='icon-remove-circle'></i> 加载应用失败，请稍后再试<div>");
        });
    }

    store.prototype.render = function (app, index) {
        var html = tpl
        html = html.replace("{image}", app.image)
            .replace("{name}", app.name + " - " + this.getType(app.category))
            .replace("{score}", this.getScore(app.score))
            .replace("{icon}", app.icon)
            .replace("{subject}", app.subject).replace("{id}", app.id);
        var app = $(html);
        if (index < 4) {
            app.addClass("app-wrapper-top");
        }
        if ((index + 1) % 4 == 0) {
            app.addClass("app-wrapper-last");
        }
        return $(html);
    }

    // 加载分类
    store.prototype.search = function (key, page, remove) {
        var self = this;
        self.preload();
        var xhr = $.ajax({
            url: this.server + "/store/search/",
            method: "post",
            data: {
                size: self.size,
                page: page,
                key: key
            }
        });
        xhr.success(function (data) {
            self.preloaded();
            if (remove == true) {
                $("[data-search]").html("");
                self.setTotal(data.totalElements);
            }
            var rows = data.content;
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var app = self.render(rows[i]);
                    app.click(function () {
                        HteOS.TaskManager.start({
                            id: new Date().getTime(),
                            name: '应用详情',
                            shell: 'window',
                            templateUrl: "tpl/store-detail.html",
                            controller: "HteOS.controller.StoreDetail",
                            icon: 'images/logo.png',
                            params: {
                                id: $(this).data("id")
                            }
                        });
                    });
                    $(".store-search-result").append(app);
                    app.fadeIn();
                }
            }
            var total = Math.ceil(self.total / self.size);
            if (page >= total) {
                $("#search-load-more").hide();
                /*$("#category-load-more-btn").attr("disabled", "disabled");
                $("#category-load-text").html("全部加载了");*/
            } else {
                $("#search-load-more").show();
                $("#search-load-text").html("加载更多");
                $("#search-load-more-btn").removeAttr("disabled");
            }
        });
        xhr.error(function () {
            self.preloaded();
            $(".store-search-result").html("<div class='app-channel-preloader'>" + "<i class='icon-remove-circle'></i> 加载应用失败，请稍后再试<div>");
        });
    }

    // 加载频道
    store.prototype.loadChannel = function (channel) {

        var self = this;

        channel.html("<div class='app-channel-preloader'><i class='icon-spinner icon-spin'></i> 正在加载中...<div>");
        var xhr = $.getJSON(this.server + "/store/channel/" + channel.data("channel"), {
            size: channel.data("size") || 8
        });
        xhr.success(function (data) {
            channel.find(".app-channel-preloader").remove();
            for (var i = 0; i < data.length; i++) {
                var app = self.render(data[i], i);
                channel.append(app);
                app.click(function () {
                    HteOS.TaskManager.start({
                        id: new Date().getTime(),
                        name: '应用详情',
                        shell: 'window',
                        templateUrl: "tpl/store-detail.html",
                        controller: "HteOS.controller.StoreDetail",
                        icon: 'images/logo.png',
                        params: {
                            id: $(this).data("id")
                        }
                    });
                });
                app.fadeIn();
            }
        });
        xhr.error(function () {
            channel.html("<div class='app-channel-preloader'><i class='icon-remove-circle'></i> 无法加载，请稍后再试<div>");
        });

    };

    store.prototype.getScore = function (score) {
        score = score || 0;
        return this.scores[score];
    }

    store.prototype.getType = function (type) {
        return this.types[type];
    }

    store.prototype.types = {
        1: "资讯",
        2: '社交',
        3: '游戏',
        4: '娱乐',
        5: '视频',
        6: '音乐',
        7: '办公',
        8: '工具',
        9: '其他'
    }

    store.prototype.scores = {
        0: '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>',
        1: '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>',
        2: '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>',
        3: '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>',
        4: '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star-empty"></span>',
        5: '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
        + '<span class="glyphicon glyphicon-star"></span>'
    }

    HteOS.TemplateManager.register("store/index.html", "<div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <h4>\n" +
        "                    <i class=\"glyphicon glyphicon-fire\"></i> 热门应用\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <div class=\"app-channel col-md-12  col-sm-12 col-xs-12\" data-channel=\"hot\"></div>\n" +
        "            <div class=\"col-md-12  col-sm-12 col-xs-12\">\n" +
        "                <h4>\n" +
        "                    <span class=\"glyphicon glyphicon-user\"></span> 使用人数\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <div class=\"app-channel col-md-12 col-sm-12 col-xs-12\" data-channel=\"install\"></div>\n" +
        "            <div class=\"col-md-12  col-sm-12 col-xs-12\">\n" +
        "                <h4>\n" +
        "                    <span class=\"glyphicon glyphicon-thumbs-up\"></span> 评分最高\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <div class=\"app-channel col-md-12  col-sm-12 col-xs-12\" data-channel=\"score\"></div>\n" +
        "            <div class=\"col-md-12  col-sm-12 col-xs-12\">\n" +
        "                <h4>\n" +
        "                    <span class=\"glyphicon glyphicon-dashboard\"></span> 最新应用\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <div class=\"app-channel col-md-12  col-sm-12 col-xs-12\" data-channel=\"new\"></div>");

    HteOS.TemplateManager.register("store/category.html", "<div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "            <h4>\n" +
        "                <i class=\"fa fa-desktop\"></i> 分类 ：<span id=\"category-name\"></span> - 共有<span id=\"total\"></span>个应用\n" +
        "            </h4>\n" +
        "        </div>\n" +
        "        <div class=\"app-channel app-category-list store-category-body col-md-12 col-sm-12 col-xs-12\"></div>\n" +
        "        <div class=\"row\" id=\"category-load-more\" style=\"margin-top: 20px;margin-bottom:20px;display: none;\">\n" +
        "            <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <button id=\"category-load-more-btn\" type=\"button\" class=\"btn btn-default\"\n" +
        "                        style=\"font-size: 14px;width:100%;border-left: none;border-right: none;\">\n" +
        "                    <span class=\"glyphicon glyphicon-refresh\"></span>&nbsp;<span\n" +
        "                        id=\"category-load-text\">加载更多</span>\n" +
        "                </button>\n" +
        "            </div>\n" +
        "        </div>");

    HteOS.TemplateManager.register("store/search.html", "<div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "            <h4>\n" +
        "                <i class=\"glyphicon glyphicon-search\"></i> 搜索\"<span id=\"search-key-text\"></span>\" -\n" +
        "                搜索到<span id=\"total\">0</span>个相关的应用\n" +
        "            </h4>\n" +
        "        </div>\n" +
        "        <div class=\"app-channel app-category-list store-search-result col-md-12 col-sm-12 col-xs-12\"></div>\n" +
        "        <div class=\"row\" id=\"load-more\"\n" +
        "             style=\"margin-top: 20px;margin-bottom:20px;display: none;\">\n" +
        "            <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <button id=\"search-load-more-btn\" type=\"button\" class=\"btn btn-default\"\n" +
        "                        style=\"font-size: 14px;width:100%;border-left: none;border-right: none;\">\n" +
        "                    <span class=\"glyphicon glyphicon-refresh\"></span>&nbsp;<span\n" +
        "                        id=\"search-load-text\">加载更多</span>\n" +
        "                </button>\n" +
        "            </div>\n" +
        "        </div>");
})(window);