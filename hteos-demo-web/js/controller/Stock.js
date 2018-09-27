(function() {
	/**
	 * @author 李球 <service@hteos.com>
	 * @class HteOS.controller.Stock 新浪股票应用
	 * @constructor
	 */
	var Stock = HteOS.controller.Stock = function(tile) {
		var stock = this;
		stock.$el = tile.getContent();
		stock.duration =  1000 * 60 * 10;
		stock.tpl = HteOS.Template.compile('<% for(i = 0;i < list.length; i++){ %>' + '<div class="hte-tile-live hte-tile-inner" >' + '<% if(list[i].scale > 0 ){ %>' + '<div class="stock-scale stock-rise">+<%=list[i].scale%>%</div>' + '<% } else { %>' + '<div class="stock-scale stock-reduce"><%=list[i].scale%>%</div>' + '<% } %>' + '<h4 class="stock-name"><%=list[i].name%></h4>' + '<div class="stock-value"><%=list[i].value%></div>' + '<% if(list[i].change > 0 ){ %>' + '<div class="stock-change stock-rise">+<%=list[i].change%></div>' + '<% } else { %>' + '<div class="stock-change stock-reduce"><%=list[i].change%></div>' + '<% } %>' + '</div>' + '<% } %>' + '<div class="hte-tile-text">新浪股票</div>');
		this.start();
	};
	
	Stock.prototype.start = function(){
        var stock = this;
        window.setInterval(function() {
            //stock.load();
        }, stock.duration);
        stock.load();

		if (!stock.lived) {
			stock.live = new HteOS.controller.Live(stock.$el,{});
			stock.lived = true;
		} else {
			stock.live.slide();
		}
	}

	/**
	 * 加载股票信息
	 */
	Stock.prototype.load = function() {
		var stock = this;
		HteOS.Masker.loading(stock.$el,'正在加载...');
		$.ajax({
			url: "http://hq.sinajs.cn/rn=" + new Date().getTime() + "&list=s_sh000001,s_sz399001,s_sh000300,CFF_IF1404,s_sz399006",
			dataType: "script",
			scriptCharset: 'gb2312',
			cache: true,
			success: function() {
				HteOS.Masker.unmask(stock.$el);
				stock.render();
			},
			error: function() {
                HteOS.Masker.error(stock.$el,'加载数据失败');
			}
		});

	};

	/**
	 * 渲染股票列表
	 */
	Stock.prototype.render = function() {
		var stock = this,
			list = [],
			types = ['hq_str_s_sh000001',
				'hq_str_s_sz399001', 'hq_str_s_sh000300', 'hq_str_s_sz399006'
			];
		for (var i = 0; i < types.length; i++) {
			var str = window[types[i]];
			var data = str.split(",");
			list.push({
				name: data[0],
				value: new Number(data[1]).toFixed(2),
				change: new Number(data[2]).toFixed(2),
				scale: new Number(data[3]).toFixed(2)
			});
		}
		// 渲染股票

		stock.$el.html(stock.tpl({
			list: list
		}));
	};
	
	Stock.prototype.stop = function(){
		window.clearInterval(this.intervalId);
		if(this.live){
			this.live.stop();
		}
	}
	
	Stock.prototype.destroy = function(){
		this.stop();
		if(this.live){
			this.live.destroy();
		}
		this.$el = null;
	}

})();