(function (win) {

    var Detail = HteOS.controller.StoreDetail = function (params) {
        HteOS.apply(this, params);
        this.server = HteOS.server || 'http://localhost:8080';
    };

    Detail.prototype.onViewRender = function () {

        var self = this;
        this.vm = new Vue({
            el: "#hte-module-" + self.module.id,
            data: {
                app: {},
                loading: false,
                loadError: false,
                loaded: false,
                review: "",
                installing: false
            },
            filters: {
                category: function (value) {
                    return self.getType(value);
                }
            },
            methods: {
                score: function (value) {
                    return self.getScore(value);
                },
                install: function () {
                    self.install();
                }
            }
        });

        this.load(this.id);
    };

    Detail.prototype.load = function (id) {
        var self = this;
        self.vm.loading = true;
        self.vm.loadError = false;
        var xhr = $.getJSON(this.server + "/store/app/" + id);
        xhr.success(function (result) {
            self.vm.app = result.app;
            self.vm.loaded = true;
        });
        xhr.error(function () {
            self.vm.loadError = true;
        })
        xhr.complete(function () {
            self.vm.loading = false;
        });
    };

    Detail.prototype.install = function () {
        var self = this;
        if (!HteOS.Preference.logon) {
            HteOS.Messager.alert("HteOS提示", "很抱歉，您还没有登录。请先登录后再安装应用！");
            return;
        }
        self.vm.installing = true;
        var xhr = $.post(HteOS.server + "/store/app/install", {
            appId: self.id
        });
        xhr.success(function (data) {
            if (data.success) {
                HteOS.Launcher.install(data.data);
                self.vm.app.install = true;
            } else {
                HteOS.Messager.alert("HteOS提示", "安装失败:" + data.message);
            }
        });
        xhr.error(function () {
            HteOS.Messager.alert("HteOS提示", "安装失败，请稍候再试");
        })
        xhr.complete(function () {
            self.vm.installing = false;
        })
    }

    Detail.prototype.review = function () {
        var self = this;
        var text = this.vm.review;
        if (!text) {
            HteOS.Messager.alert("HteOS提示", "请填写对应用的评价");
            return;
        }
        if (text.length > 200) {
            HteOS.Messager.alert("HteOS提示", "评价太长了，不能超过200个字符");
            return;
        }
        var xhr = $.post(HteOS.server + "/store/app/review", {
            appId: self.id,
            review: text,
            score: 5
        });
        xhr.success(function (data) {
            if (data.success) {
                self.app.reviews.push({
                    account: data.account,
                    reviewDate: new Date().getTime(),
                    review: text,
                    score: data.score,
                    face: data.face
                });
            } else {
                HteOS.Messager.alert("提示","提交评价失败：" + data.message);
            }
        });
        xhr.error(function (data) {
            HteOS.Messager.alert("提示","提交评价失败，请稍候再试");
        });
    }

    Detail.prototype.getScore = function (score) {
        score = score || 0;
        return this.scores[score];
    }

    Detail.prototype.getType = function (type) {
        return this.types[type];
    }

    Detail.prototype.types = {
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

    Detail.prototype.scores = {
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


})(window);