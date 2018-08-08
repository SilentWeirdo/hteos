(function () {
    /**
     * @author 李球 <service@hteos.com>
     * @class HteOS.tile.Clock 时钟应用
     * @constructor
     */
    var Clock = HteOS.controller.Clock = function (el, options) {
        var clock = this;
        clock.$el = $(el);
        clock.period = options.period || 'm';
        this.tpl = HteOS.Template.compile('<div class="clock">' + '<h3 class="clock-time">' + '<%=time%>' + '</h3>' + '<h4 class="clock-date">' + '<%=date%>' + '</h4>' + '<div>');
        this.start();
    };

    Clock.prototype.start = function () {
        var clock = this;
        this.update();
        if (this.period == 'm') {
            clock.intervalId = window.setInterval(function () {
                clock.update();
            }, 1000 * 60);
        } else if (this.period == 'h') {
            clock.intervalId = window.setInterval(function () {
                clock.update();
            }, 1000 * 60 * 60);
        } else if (this.period == 's') {
            clock.intervalId = window.setInterval(function () {
                clock.update();
            }, 1000);
        }
    };

    Clock.prototype.stop = function () {
        window.clearInterval(this.intervalId);
    };

    /**
     * 更新时间，每1分钟更新一次
     */
    Clock.prototype.update = function () {
        var date = new Date();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        day = day > 9 ? day : '0' + day;
        /* hours = date.getHours();
        hours = hours > 9 ? hours : '0' + hours;
        var minutes = date.getMinutes();
        minutes = minutes > 9 ? minutes : '0' + minutes;
        month = month > 9 ? month : '0' + month;
        */
        var day2 = date.getDay();
        var names = ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"]
        this.$el.find(".hte-tile-content").html(this.tpl({
            time: names[day2],
            date: date.getFullYear() + '-' + month + '-' + day
        }));
    };

    Clock.prototype.destroy = function () {
        this.stop();
        this.$el = null;
    }
})();