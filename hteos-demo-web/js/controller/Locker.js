(function (factory) {
    if (typeof define === "function" && define.amd) {
        // AMD. Register as an anonymous module.
        define(factory);
    } else {
        // Browser globals
        factory();
    }
}(function () {
    /**
     * @author LIQIU <service@hteos.com>
     * @class HteOS.Locker 锁屏组件
     * @singleton
     */
    HteOS.Locker = {
        locked: false
    };

    /**
     * 监听锁屏DOM，如果正在锁定屏幕而遮罩的DOM不存在，则显示出来
     */
    HteOS.Locker.listen = function (options) {
        if ($(".hte-lock-panel").length == 0) {
            HteOS.Locker.render();
        }
        if (HteOS.UA.advance) {
            $(".hte-lock-panel").hide();
            $(".hte-lock-panel").fadeIn();
        } else {
            $(".hte-lock-panel").show();
        }
    };

    /**
     * 锁定屏幕
     */
    HteOS.Locker.lock = function (options) {
        console.log("lock");
        HteOS.Locker.listen();
        HteOS.Locker.locked = true;
        $(".hte-lock-face > img").attr("src", HteOS.Preference.face);
        $(".hte-lock-name").html(HteOS.Preference.name);
        var top = ($(document.body).height() - $(".hte-lock-content").height()) / 2 - 50;
        $(".hte-lock-content").css("margin-top", top);
        this.interval = window.setInterval(function () {
            if (HteOS.Locker.locked && $(".hte-lock-panel").length == 0) {
                HteOS.Locker.lock();
            }
        }, 1000);
    };

    /**
     * 解锁屏幕
     */
    HteOS.Locker.unlock = function (options) {
        if ($("#hte-unlock-input").val()) {
            $("#unlock-preloader").show();
            $(".hte-unlock-panel").hide();
            $.getJSON(HteOS.server + "/user/unlock", {
                password: $("#hte-unlock-input").val()
            }, function (data) {
                if (data.success) {
                    HteOS.Locker.locked = false;
                    window.clearInterval(HteOS.Locker.interval);
                    if (HteOS.UA.advance) {
                        $(".hte-lock-panel").fadeOut(function () {
                            $("#unlock-preloader").hide();
                            $(".hte-unlock-panel").show();
                            $("#hte-unlock-input").val("");
                        });
                    } else {
                        $(".hte-lock-panel").hide();
                    }
                } else {
                    $("#unlock-preloader").hide();
                    $(".hte-unlock-panel").show();
                    $("#unlock-alert").html("解锁失败，输入的密码不正确");
                    $("#unlock-alert").show();
                    /*window.setTimeout(function() {
                        $("#unlock-alert").fadeOut();
                    }, 1000);*/
                }
            });
        }
    };

    /**
     * 渲染锁屏组件
     */
    HteOS.Locker.render = function () {
        var me = this;
        var tpl = $("<div class=\"hte-lock-panel\">"
            + "<div class=\"hte-lock-content\">"
            + "<div class=\"hte-lock-face \">"
            + "<img >"
            + "</div>"
            + "<div class=\"hte-lock-user\">"
            + "<h3 class=\"hte-lock-name\"></h3>"
            + "</div>"
            + "<div id=\"unlock-preloader\" style=\"margin-top: 15px;text-align:center;width: 100%;display:none;\">" + "<img src=\"images/preloader.gif\" height=\"24px;\"></div>"
            + "<div class=\"hte-unlock-panel\">"
            + "<input id=\"hte-unlock-input\" type=\"password\" class=\"form-control\" " + "placeholder=\"输入登录密码解锁\" required autofocus> "
            + "<span class=\"hte-lock-unlock-btn metro-icon-arrow-right\"></span>"
            + "<div style=\"color:#fff;font-size:12px;margin-top: 10px;\">提示：QQ登录用户登录密码为：123456</div>"
            + "</div>"
            + "<div id=\"unlock-alert\" style=\"display:none;margin-top:15px;color:#fff;\"></div>"
            + "</div>"
            + "</div>");
        var $panel = $(tpl).appendTo($(document.body));
        $('.hte-lock-unlock-btn').click(HteOS.Locker.unlock);
        $("#hte-unlock-input").keyup(function (event) {
            if (event.keyCode == 13) {
                HteOS.Locker.unlock();
            }
        });
    };
}));