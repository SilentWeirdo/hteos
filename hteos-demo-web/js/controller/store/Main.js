(function (win) {

    HteOS.store = {};

    var Main = HteOS.store.Main = function () {
        this.server = HteOS.server || 'http://localhost';
    };

    Main.prototype.onViewRender = function () {

        var self = this;

        function load(type, value) {
            this.active = type;
            if (type === 'index') {
                self.loadIndex();
            } else if (type === 'category') {
                self.loadCategory(value, 1);
            }
        }

        function loadMoreCategory() {
            self.loadCategory(this.currentCategory, this.categoryResult.pageable.pageNumber + 2, true);
        }

        function loadMoreSearch() {
            self.search(this.searchKey, this.searchResult.pageable.pageNumber + 2, true);
        }

        function search(searchKey, event) {
            if (searchKey) {
                this.active = 'search';
                self.search(searchKey);
            } else {
                this.active = 'index';
                self.loadIndex();
            }
        }

        this.vm = new Vue({
            el: "#hte-module-" + self.module.id,
            data: {
                searchKey: "",
                loading: false,
                loadError: false,
                active: "index",
                categoryName: "",
                categoryTotal: "",
                searchKey: "",
                searchTotal: "",
                searchResult: {},
                categoryResult: {},
                currentCategory: "",
                channels: []
            },
            filters: {
                category: function (value) {
                    return self.getType(value);
                }
            },
            methods: {
                search: search,
                score: function (value) {
                    return self.getScore(value);
                },
                load: load,
                loadMoreCategory: loadMoreCategory,
                loadMoreSearch: loadMoreSearch,
                openDetail: function (app, event) {
                    HteOS.TaskManager.start({
                        id: app.id,
                        name: app.name + ' - 应用详情',
                        icon: app.icon,
                        shell: 'window',
                        templateUrl: "tpl/store-detail.html",
                        controller: "HteOS.store.Detail",
                        params: {
                            id: app.id
                        }
                    });
                    event.stopPropagation();
                }
            }
        });

        //加载首页
        this.loadIndex();
    };

    Main.prototype.loadIndex = function () {
        var self = this;
        self.vm.loading = true;
        self.vm.loadError = false;
        var xhr = $.getJSON(HteOS.server + "/store");
        xhr.success(function (data) {
            var channels = [];
            channels.push({
                name: '热门应用',
                icon: 'glyphicon glyphicon-fire',
                apps: data.hot
            });
            channels.push({
                name: '最多使用',
                icon: 'glyphicon glyphicon-user',
                apps: data.install
            });
            channels.push({
                name: '评分最高',
                icon: 'glyphicon glyphicon-thumbs-up',
                apps: data.score
            });
            channels.push({
                name: '最新应用',
                icon: 'glyphicon glyphicon-dashboard',
                apps: data.new
            });
            self.vm.channels = channels;
        });
        xhr.error(function () {
            self.vm.loadError = true;
        });
        xhr.complete(function () {
            self.vm.loading = false;
        })
    }

    Main.prototype.loadCategory = function (category, page) {
        var self = this;
        self.vm.loading = true;
        self.vm.loadError = false;
        self.vm.currentCategory = category;
        var xhr = $.getJSON(HteOS.server + "/store/category/" + category, {
            size: 8,
            page: page
        });
        xhr.success(function (result) {
            if (page > 1) {
                result.content = self.vm.categoryResult.content.concat(result.content);
            }
            self.vm.categoryResult = result;
        });
        xhr.error(function () {
            self.vm.loadError = true;
        });
        xhr.complete(function () {
            self.vm.loading = false;
        });
    }


    // 加载分类
    Main.prototype.search = function (searchKey, page) {
        var self = this;
        if (!page) {
            page = 1;
        }
        var xhr = $.ajax({
            url: HteOS.server + "/store/search/",
            method: "post",
            data: {
                size: 8,
                page: page,
                key: searchKey
            }
        });
        xhr.success(function (result) {
            if (page > 1) {
                result.content = self.vm.searchResult.content.concat(result.content);
            }
            self.vm.searchResult = result;
        });
        xhr.error(function () {
            self.vm.loadError = true;
        });
        xhr.complete(function () {
            self.vm.loading = false;
        });
    }
    Main.prototype.getScore = function (score) {
        score = score || 0;
        return this.scores[score];
    }

    Main.prototype.getType = function (type) {
        return this.types[type];
    }

    Main.prototype.types = {
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

    Main.prototype.scores = {
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

    HteOS.TemplateManager.register("store.html", "<div class=\"store-main container\">\n" +
        "    <div class=\"store-preloader\" v-show=\"loading\">\n" +
        "        <img src=\"images/preloader_black.gif\">\n" +
        "    </div>\n" +
        "    <div class=\"hte-store-header row\">\n" +
        "        <div class=\"col-md-8 col-sm-6 col-xs-12\" style=\"padding-top: 10px;padding-bottom: 10px;\">\n" +
        "            <h3 style=\"display: inline-block;\">\n" +
        "                <img src=\"images/logo.png\" height=\"30\" tyle=\"margin-top: -5px;\">&nbsp;&nbsp;<span\n" +
        "                    style=\"font-family:Segoe Script\">HteOS</span> 应用商店\n" +
        "            </h3>\n" +
        "        </div>\n" +
        "        <div class=\"col-md-4 col-sm-6 col-xs-12\">\n" +
        "            <div class=\"pull-right app-store-search\">\n" +
        "                <input id=\"search-key\" name=\"key\" v-model=\"searchKey\" @keyup=\"search(searchKey,$event)\" type=\"text\"\n" +
        "                       class=\"form-control\" placeholder=\"输入关键字进行搜索\">\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"col-md-12 col-sm-12 col-xs-12\" style=\"padding-right:0px;padding-left: 0px;\">\n" +
        "            <nav class=\"navbar navbar-default store-nav\" role=\"navigation\">\n" +
        "                <!-- Brand and toggle get grouped for better mobile display -->\n" +
        "                <div class=\"navbar-header\">\n" +
        "                    <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\"\n" +
        "                            data-target=\"#bs-example-navbar-collapse-1\">\n" +
        "                        <span class=\"sr-only\">Toggle navigation</span> <span\n" +
        "                            class=\"icon-bar\"></span> <span class=\"icon-bar\"></span> <span\n" +
        "                            class=\"icon-bar\"></span>\n" +
        "                    </button>\n" +
        "                </div>\n" +
        "                <div class=\"collapse navbar-collapse\"\n" +
        "                     id=\"bs-example-navbar-collapse-1\">\n" +
        "                    <ul class=\"nav navbar-nav app-store-nav\">\n" +
        "                        <li :class=\"{ active: active === 'index'}\"><a id=\"store-index\" href=\"#\" @click=\"load('index')\">首页</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 1 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',1)\">资讯</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 2 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',2)\">社交</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 3 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',3)\">游戏</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 4 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',4)\">娱乐</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 5 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',5)\">视频</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 6 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',6)\">音乐</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 7 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',7)\">办公</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 8 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',8)\">工具</a>\n" +
        "                        </li>\n" +
        "                        <li :class=\"{ active: active === 'category' && currentCategory === 9 }\">\n" +
        "                            <a href=\"#\" class=\"store-category\" @click=\"load('category',9)\">其他</a>\n" +
        "                        </li>\n" +
        "                    </ul>\n" +
        "                </div>\n" +
        "                <!-- /.navbar-collapse -->\n" +
        "            </nav>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"hte-store-body row\">\n" +
        "\n" +
        "        <div v-show=\"loading\" class='app-channel-preloader'>\n" +
        "            <i class='fa fa-spinner fa-pulse'></i> 正在加载中...\n" +
        "        </div>\n" +
        "\n" +
        "        <div v-show=\"loadError\" class='app-channel-preloader'>\n" +
        "            <i class='fa fa-remove-circle'></i> 加载失败，请稍后再试\n" +
        "        </div>\n" +
        "\n" +
        "        <div v-show=\"active === 'index' && !loading\">\n" +
        "            <div v-for=\"channel in channels\">\n" +
        "                <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "                    <h4>\n" +
        "                        <i :class=\"channel.icon\"></i> {{channel.name}}\n" +
        "                    </h4>\n" +
        "                </div>\n" +
        "                <div class=\"app-channel col-md-12  col-sm-12 col-xs-12\" data-channel=\"hot\">\n" +
        "                    <div v-for=\"app in channel.apps\" @click=\"openDetail(app,$event)\"\n" +
        "                         class=\"col-md-3 col-sm-6 col-xs-12 app-wrapper\">\n" +
        "                        <div class=\"app-wrapper-inner\">\n" +
        "                            <img class=\"app-image\" :src=\"app.image\">\n" +
        "                            <div class=\"app-overview\">\n" +
        "                                <img class=\"app-icon\" :src=\"app.icon\">\n" +
        "                                <div class=\"app-name\">{{app.name}}</div>\n" +
        "                                <div class=\"app-score\" v-html=\"score(app.score)\"></div>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div v-show=\"active === 'category' && !loading\">\n" +
        "            <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <h4>\n" +
        "                    <i class=\"fa fa-desktop\"></i> 分类 ：<span>{{currentCategory | category}}</span> -\n" +
        "                    共有<span>{{categoryResult.totalElements}}</span>个应用\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <div class=\"app-channel app-category-list store-category-body col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <div v-for=\"app in categoryResult.content\" @click=\"openDetail(app,$event)\"\n" +
        "                     class=\"col-md-3 col-sm-6 col-xs-12 app-wrapper\">\n" +
        "                    <div class=\"app-wrapper-inner\">\n" +
        "                        <img class=\"app-image\" :src=\"app.image\">\n" +
        "                        <div class=\"app-overview\">\n" +
        "                            <img class=\"app-icon\" :src=\"app.icon\">\n" +
        "                            <div class=\"app-name\">{{app.name}}</div>\n" +
        "                            <div class=\"app-score\" v-html=\"score(app.score)\"></div>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div v-show=\"!categoryResult.last\" class=\"col-md-12 col-sm-12 col-xs-12 text-center\"\n" +
        "                 style=\"margin:20px 0px;\">\n" +
        "                <button type=\"button\" class=\"btn btn-default\" @click=\"loadMoreCategory\">\n" +
        "                    <span class=\"glyphicon glyphicon-refresh\"></span>&nbsp;加载更多\n" +
        "                </button>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "\n" +
        "        <div v-show=\"active === 'search' && !loading\">\n" +
        "            <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <h4>\n" +
        "                    <i class=\"glyphicon glyphicon-search\"></i> 搜索\"<span>{{searchKey}}</span>\" -\n" +
        "                    搜索到<span>{{searchResult.totalElements}}</span>个相关的应用\n" +
        "                </h4>\n" +
        "            </div>\n" +
        "            <div class=\"app-channel app-category-list store-search-result col-md-12 col-sm-12 col-xs-12\">\n" +
        "                <div v-for=\"app in searchResult.content\" @click=\"openDetail(app,$event)\"\n" +
        "                     class=\"col-md-3 col-sm-6 col-xs-12 app-wrapper\">\n" +
        "                    <div class=\"app-wrapper-inner\">\n" +
        "                        <img class=\"app-image\" :src=\"app.image\">\n" +
        "                        <div class=\"app-overview\">\n" +
        "                            <img class=\"app-icon\" :src=\"app.icon\">\n" +
        "                            <div class=\"app-name\">{{app.name}}</div>\n" +
        "                            <div class=\"app-score\" v-html=\"score(app.score)\"></div>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div v-show=\"!searchResult.last\" class=\"col-md-12 col-sm-12 col-xs-12 text-center\"\n" +
        "                 style=\"margin:20px 0px;\">\n" +
        "                <button type=\"button\" class=\"btn btn-default\" @click=\"loadMoreSearch\">\n" +
        "                    <span class=\"glyphicon glyphicon-refresh\"></span>&nbsp;加载更多\n" +
        "                </button>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"hte-store-footer row\">\n" +
        "        <div class=\"col-md-12 col-sm-12 col-xs-12 store-footer\">\n" +
        "            <img src=\"images/logo.png\" height=\"25\"\n" +
        "                 style=\"margin-top: -5px;\">&nbsp;&nbsp;<span\n" +
        "                style=\"font-family:Segoe Script\">HteOS</span> - 与众不同的<span\n" +
        "                style=\"font-family:Segoe Script\">Web</span>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "</div>");

})(window);