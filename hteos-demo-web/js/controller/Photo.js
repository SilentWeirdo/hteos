(function() {
	/**
	 * @author 李球 <service@hteos.com>
	 * @class HteOS.tile.Photo 腾讯图片新闻
	 * @constructor
	 */
	var Photo = HteOS.controller.Photo = function(tile) {
		var photo = this;
		photo.$el = tile.getContent();
		photo.duration = 1000 * 60 * 10;
		photo.intervalId = window.setInterval(function() {
			photo.load();
		}, photo.duration);
		photo.load();
		photo.tpl = HteOS.Template.compile('<% for(i = 0;i < list.length; i++){ %>'
			+ '<div class="hte-tile-live">'
                + '<a href="<%=list[i].seturl%>" target="_blank">'
                    + '<img alt="" src="<%=list[i].cover%>">'
                    + '<div class="hte-tile-text"><%=list[i].setname%></div>'
                + '</a>'
            + '</div>'
            + '<% } %>');
        window.cacheMoreData = null;
		window.cacheMoreData = function(data){
            HteOS.Masker.unmask(photo.$el);
            photo.render(data);
            photo.start();
        }
    };

	Photo.prototype.start = function(){
		var photo = this;
		if (!photo.lived) {
			photo.live = new HteOS.controller.Live(photo.$el,{});
			photo.lived = true;
		} else {
			photo.live.slide();
		}
	}
	
	/**
	 * 加载图片新闻列表
	 */
	Photo.prototype.load = function() {
		var photo = this;
		HteOS.Masker.loading(photo.$el,'正在加载...');
		$.ajax({
			url: "http://pic.news.163.com/photocenter/api/list/0001/00AN0001,00AO0001,00AP0001/0/10/cacheMoreData.json",
			dataType: "script",
			success: function(data) {

			},
			error: function() {
                HteOS.Masker.unmask(photo.$el);
				HteOS.Masker.error(photo.$el,'加载失败');
			}
		});

	};

	/**
	 * 渲染图片新闻
	 */
	Photo.prototype.render = function(data) {
		this.$el.html(this.tpl({
			list: data
		}));
	};
	
	Photo.prototype.stop = function(){
		window.clearInterval(this.intervalId);
		if(this.live){
			this.live.stop();
		}
	}
	
	Photo.prototype.destroy = function(){
		this.stop();
		if(this.live){
			this.live.destroy();
		}
		this.$el = null;
	}
	return Photo;
})();