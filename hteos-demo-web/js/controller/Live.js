(function() {
	/**
	 * @author 李球 <service@hteos.com>
	 * @class HteOS.tile.Live 图标内容的动态切换
	 * @constructor
	 */
	var Live = HteOS.controller.Live = function(el, options) {
		var live = this;
		this.$el = $(el);
		this.duration = options.duration || 500;
		this.direction = options.direction || 'up';
		this.period = options.period || 5;
		this.start();
	};
	
	Live.prototype.start = function(){
		var live = this;
		this.interval = window.setInterval(function() {
			live.slide();
		}, this.period * 1000);
		live.slide();
	};
	
	Live.prototype.stop = function(){
		window.clearInterval(this.interval);
	};

	/**
	 * 切换内容
	 */
	Live.prototype.slide = function() {
		var active = this.$el.find(".hte-tile-live-active"),
			init = false;
		if (active.length == 0) {
			active = $(this.$el.find(".hte-tile-live").last());
			init = true;
		}
		if (active.length == 0) {
			return;
		}
		if (init == true) {
			active.addClass('hte-tile-live-active');
			active.css({
				top: 0,
				left: 0,
				'z-index': 1,
				opacity: 1
			});
			this.$el.find(".hte-tile-live").css({
				'opacity': 0,
				'z-index': 0
			});
			if (this.direction == 'up') {
				this.$el.find(".hte-tile-live").each(function() {
					$(this).css('top', $(this).height());
				});
			} else if (this.direction == 'down') {
				this.$el.find(".hte-tile-live").each(function() {
					$(this).css('top', 0 - $(this).height());
				});
			} else if (this.direction == 'left') {
				this.$el.find(".hte-tile-live").each(function() {
					$(this).css('left', $(this).width());
				});
			} else if (this.direction == 'right') {
				this.$el.find(".hte-tile-live").each(function() {
					$(this).css('left', 0 - $(this).width());
				});
			}
		}

		var next = active.next('.hte-tile-live');

		if (next.length == 0) {
			next = this.$el.find(".hte-tile-live").first();
		}

		if (this.direction == 'up' || this.direction == 'down') {
			// this.$el.css('left', 0);
		}

		var animate = HteOS.UA.advance;

		// 向上滚
		if (this.direction == 'up') {
			if (animate) {
				active.animate({
					top: 0 - active.height(),
					opacity: 0
				}, this.duration, function() {
					active.css('top', active.height());
					active.removeClass('hte-tile-live-active');
				});

				next.animate({
					top: 0,
					opacity: 1
				}, this.duration, function() {
					next.addClass('hte-tile-live-active');
				});
			} else {
				active.css('top', 0 - active.outerHeight());
				active.removeClass('hte-tile-live-active');
				next.css("top", 0);
				next.css("opacity", 1);
				next.addClass('hte-tile-live-active');
			}
			// 向下滚
		} else if (this.direction == 'down') {
			if (animate) {
				active.animate({
					top: active.height(),
					opacity: 0
				}, this.duration, function() {
					active.css('top', 0 - active.height());
					active.removeClass('hte-tile-live-active');
				});

				next.animate({
					top: 0,
					opacity: 1
				}, this.duration, function() {
					next.addClass('hte-tile-live-active');
				});
			} else {
				active.css('top', active.outHeight());
				active.removeClass('hte-tile-live-active');
				next.css("top", 0);
				next.css("opacity", 1);
				next.addClass('hte-tile-live-active');
			}
			// 向左滚
		} else if (this.direction == 'left') {
			if (animate) {
				active.animate({
					left: 0 - active.width(),
					opacity: 0
				}, this.duration, function() {
					active.css('left', active.width());
					active.removeClass('hte-tile-live-active');
				});

				next.animate({
					left: 0,
					opacity: 1
				}, this.duration, function() {
					next.addClass('hte-tile-live-active');
				});
			} else {
				active.css('left', active.outerWidth());
				active.removeClass('hte-tile-live-active');
				next.addClass('hte-tile-live-active');
				next.css("left", 0);
				next.css("opacity", 1);
			}
			// 向又滚
		} else if (this.direction == 'right') {
			if (animate) {
				active.animate({
					left: active.width(),
					opacity: 0
				}, this.duration, function() {
					active.css('left', 0 - active.width());
					active.removeClass('hte-tile-live-active');
				});

				next.animate({
					left: 0,
					opacity: 1
				}, this.duration, function() {
					next.addClass('hte-tile-live-active');
				});
			} else {
				active.css('left', active.outerWidth());
				active.removeClass('hte-tile-live-active');
				next.addClass('hte-tile-live-active');
				next.css("left", 0);
				next.css("opacity", 1);
			}
		}
	};
	
	Live.prototype.destroy = function(){
		this.stop();
		this.$el = null;
	}
})();