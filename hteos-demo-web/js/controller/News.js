(function () {
    /**
     * @author 李球 <service@hteos.com>
     * @class HteOS.tile.News 新浪新闻应用
     * @constructor
     */
    var News = HteOS.controller.News = function (tile) {
        var news = this;
        news.$el = tile.getContent();
        news.duration = 1000 * 60 * 10;
        window.setInterval(function () {
            news.load();
        }, news.duration);
        news.load();
        news.tpl = HteOS.Template
            .compile('<% for(i = 0;i < list.length; i++){ %>' +
                '<div class="hte-tile-live hte-tile-inner">' +
                '<a href="<%=list[i].url%>" target="_blank" style="color:#fff">' +
                '<div class="news-title">' +
                '<%=list[i].title%>' +
                '</div>' +
                '<div class="news-channel">' +
                '<%=list[i].channel.title%>' +
                '</div>' +
                '<div class="news-time">' +
                '	<%=list[i].time%>' +
                '</div>' +
                '</a>'
                + '</div>'
                + '<% } %>'
                + '<div class="hte-tile-text">新浪新闻</div>');
    };

    News.prototype.start = function () {
        var news = this;
        if (!news.lived) {
            news.live = new HteOS.controller.Live(news.$el, {});
            news.lived = true;
        } else {
            news.live.slide();
        }
    };

    /**
     * 获取新闻列表
     */
    News.prototype.load = function () {
        var news = this;
        HteOS.Masker.mask(news.$el, '正在加载...');
        $.ajax({
            url: "http://roll.news.sina.com.cn/interface/rollnews_ch_out_interface.php?col=89&spec=&type=&ch=01" + "&k=&offset_page=0&offset_num=0&num=60&asc=&page=1&r=0.1516552458051592",
            dataType: "script",
            scriptCharset: 'gb2312',
            success: function () {
                HteOS.Masker.unmask(news.$el);
                news.render();
                news.start();
            },
            error: function () {
                HteOS.Masker.mask(news.$el, '加载失败');
            }
        });
    };

    /**
     * 渲染新闻
     */
    News.prototype.render = function () {
        var news = this, list = jsonData.list;
        list = list.slice(0, 8);
        for (var i = 0; i < list.length; i++) {
            var time = list[i].time;
            list[i].time = new Date(time * 1000).format();
        }
        jsonData.list = list;
        news.$el.html(news.tpl(jsonData));
        window.jsonData = null;
    };

    News.prototype.stop = function () {
        this.live.stop();
    }

    News.prototype.destroy = function () {
        this.$el = null;
        this.live.destroy();
    }
})();