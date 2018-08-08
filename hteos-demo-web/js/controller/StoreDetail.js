(function (win) {

    var detail = HteOS.controller.StoreDetail = function (params) {
        HteOS.apply(this, params);
        this.server = HteOS.server || 'http://localhost:8080';
    };

    detail.prototype.preload = function () {
        this.shell.getEl().find(".store-preloader").show();
    }

    detail.prototype.preloaded = function () {
        this.shell.getEl().find(".store-preloader").hide();
    }

    detail.prototype.onShellRendered = function () {
        this.load(this.id);
    };

    detail.prototype.load = function (id) {
        var self = this;
        self.preload();
        $.ajax({
            url: this.server + "/store/app/" + id,
            dataType: "json",
            success: function (data) {
                self.preloaded();
                var app = data.app;
                var shell = self.shell.getEl();
                shell.find("[data-property='icon']").attr("src", app.icon);
                shell.find("[data-property='name']").html(app.name);
                shell.find("[data-property='score']").html(app.score || 0);
                shell.find("[data-property='reviewCount']").html(app.reviewCount || 0);
                shell.find("[data-property='scoreHTML']").html(self.getScore(app.score));
                shell.find("[data-property='type']").html(self.getType(app.category));
                shell.find("[data-property='provider']").html("来自" + (app.provider || "HteOS"));
                shell.find("[data-property='installCount']").html((app.installCount || 0) + "人使用");
                shell.find("[data-property='version']").html(app.appVersion);
                shell.find("[data-property='lastUpdateDate']").html(app.lastUpdateDate);
                shell.find("[data-property='subject']").html(app.subject);
                shell.find("[data-property='desc']").html(app.desc);
                self.setStatus(data.install);
                shell.find(".app-reviews").html("");
                self.renderReviews(data.reviews.reviews);
            }
        });
    };

    detail.prototype.setStatus = function (status) {
        var self = this;
        var shell = self.shell.getEl();
        if (status === true) {
            shell.find(".app-action-d-btn").attr("disabled", "disabled");
            shell.find(".app-action-d-btn").html("<span class=\"glyphicon glyphicon-ok\"></span> 已安装");
            shell.find(".app-action-d-btn").unbind("click");
            shell.find(".review-text").removeAttr("disabled");
            shell.find(".review-btn").removeAttr("disabled").bind("click", function () {
                self.review();
            }).html("提交")
        } else {
            shell.find(".app-action-d-btn").html("<span class=\"glyphicon glyphicon-download-alt\"></span> 安装");
            shell.find(".app-action-d-btn").removeAttr("disabled");
            shell.find(".app-action-d-btn").bind("click", function () {
                self.install();
            });
            shell.find(".review-text").attr("disabled", "disabled");
            shell.find(".review-btn").attr("disabled", "disabled").unbind("click").html("请先安装应用");
        }
    }

    detail.prototype.renderReviews = function (reviews) {
        var tpl = "<li class=\"left clearfix\">"
            + "<span class=\"app-review-account-face pull-left\">"
            + "<img src=\"{face}\" class=\"img-circle\" />" + "</span>"
            + "<div class=\"app-review-body clearfix\">"
            + "<div class=\"header\">"
            + "<strong class=\"primary-font\">{account}</strong>"
            + "<span class=\"app-review-score\">{score}</span>"
            + "<small class=\"pull-right text-muted\">"
            + "<span class=\"glyphicon glyphicon-time\"></span> {date}"
            + "</small>" + "</div>" + "<p>{review}</p>" + "</div>"
            + "</li>";
        for (var i = 0; i < reviews.length; i++) {
            review = reviews[i];
            var date = new Date(review.reviewDate);
            var html = tpl;
            html = html.replace(
                "{face}", review.face)
                .replace("{account}", review.account).replace(
                    "{score}", this.getScore(review.score))
                .replace("{date}", date.format()).replace("{review}",
                    review.review);
            $(".app-reviews").prepend($(html));
        }
    }

    detail.prototype.install = function () {
        var self = this;
        var shell = self.shell.getEl();
        if (!HteOS.Preference.logon) {
            HteOS.Messager.alert("HteOS提示", "很抱歉，您还没有登录。请先登录后再安装应用！");
            return;
        }
        shell.find(".app-action-d-btn").attr("disabled", "disabled");
        shell.find(".app-action-d-btn").html("<span class=\"glyphicon glyphicon-download-alt\"></span> 正在安装..");
        shell.find(".app-action-d-btn").unbind("click");
        $.ajax({
                url: this.server + "/store/app/install",
                data: {
                    appId: self.id
                },
                dataType: "json",
                success: function (data) {
                    if(data.success){
                        HteOS.Launcher.install(data.data);
                        self.setStatus(true);
                    }else{
                        HteOS.Messager.alert("HteOS提示", "安装失败，请稍候再试");
                        shell.find(".app-action-d-btn").html("<span class=\"glyphicon glyphicon-download-alt\"></span> 安装");
                        shell.find(".app-action-d-btn").removeAttr("disabled");
                        shell.find(".app-action-d-btn").bind("click", install);
                    }
                },
                error: function () {
                    HteOS.Messager.alert("HteOS提示", "安装失败，请稍候再试");
                    shell.find(".app-action-d-btn").html("<span class=\"glyphicon glyphicon-download-alt\"></span> 安装");
                    shell.find(".app-action-d-btn").removeAttr("disabled");
                    shell.find(".app-action-d-btn").bind("click", install);
                }
            });
    }

    detail.prototype.review =function() {
        var self = this;
        var shell = self.shell.getEl();
        var text = shell.find(".review-text").val();
        var score = shell.find("#ratings-hidden").val();
        if (!score) {
            shell.find("#operate-tip").html("请为应用进行打分");
            shell.find("#operate-tip").css("color", "red");
            return;
        }
        if (!text) {
            shell.find("#operate-tip").html("请填写对应用的评价");
            shell.find("#operate-tip").css("color", "red");
            return;
        }

        if (text.length > 200) {
            shell.find("#operate-tip").html("评价太长了，不能超过200个字符");
            shell.find("#operate-tip").css("color", "red");
            return;
        }

        shell.find("#operate-tip").html("");
        shell.find(".review-btn").attr("disabled", "disabled");
        shell.find(".review-btn").unbind("click", review);
        shell.find(".review-btn").html("正在提交...");
        $.ajax({
            url : ctx + "/store/app/review",
            method : "post",
            data : {
                appId : self.id,
                review : text,
                score : score
            },
            dataType : "json",
            success : function(data) {
                if (data.success) {
                    shell.find("#operate-tip").css("color", "green");
                    shell.find("#operate-tip").html("评价成功");
                    shell.find(".review-btn").removeAttr("disabled");
                    shell.find(".review-btn").bind("click", review);
                    shell.find(".review-btn").html("提交");
                    var reviews = [];
                    reviews.push({
                        account : account,
                        reviewDate : new Date().getTime(),
                        review : text,
                        score : score,
                        face : face
                    });
                    self.renderReviews(reviews);
                } else {
                    shell.find(".review-btn").removeAttr("disabled");
                    shell.find(".review-btn").bind("click", review);
                    shell.find(".review-btn").html("提交");
                }
            },
            error : function() {
                shell.find("#operate-tip").html("提交评价失败，请稍候再试");
                shell.find("#operate-tip").css("color", "red");
                shell.find(".review-btn").removeAttr("disabled");
                shell.find(".review-btn").bind("click", review);
                shell.find(".review-btn").html("提交");
            }
        });
    }

    detail.prototype.getScore = function (score) {
        score = score || 0;
        return this.scores[score];
    }

    detail.prototype.getType = function (type) {
        return this.types[type];
    }

    detail.prototype.types = {
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

    detail.prototype.scores = {
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