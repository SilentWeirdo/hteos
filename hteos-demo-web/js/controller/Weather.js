((function() {
	/**
	 * @author 李球 <service@hteos.com>
	 * @class HteOS.tile.Weather 天气预报应用
	 * @constructor
	 */
	var Weather = HteOS.controller.Weather = function(tile) {
		var weather = this;
		weather.$el = tile.getContent();
		this.render();
		this.start();
		//http://wgeo.weather.com.cn/ip/?_=1532568640566
	};
	
	Weather.prototype.start = function() {
		var weather = this;
		if (weather.duration && weather.duration > 1000 * 60 * 10) {
			weather.intervalId = window.setInterval(function() {
				weather.load();
			}, weather.duration);
		}
		weather.load();
	}

	/**
	 * 渲染应用
	 */
	Weather.prototype.render = function() {
		this.$el.find(".hte-tile-content").html('<div class="hte-tile-inner">' + /*'<div class="weather-temperature">--</div>' + */'<div class="weather-city">--</div>'  + '<img class="weather-pic" src=""></img>'+ '<div class="weather-weather">--  / --</div>' + '</div>');
		this.rendered = true;
	};

	/**
	 * 加载天气预报数据
	 */
	Weather.prototype.load = function() {
		var weather = this;
		HteOS.Masker.unmask(weather.$el);
		HteOS.Masker.loading(weather.$el,'正在加载...');
		/*var xhr = $.getScript("http://wgeo.weather.com.cn/ip/?_=" + new Date().getTime());
		xhr.success(function () {
		    
		    var xhr2 = $.get("http://localhost:8080/api/proxy",{
		        url : "http://www.weather.com.cn/data/sk/"+id+".html?_="+ new Date().getTime()
            }).success(function (data) {
                console.log(data);
            })
		    
            var xhr2 = $.get();
            xhr2.success(function (data) {
                console.log(data);
            });
        });*/
        //var url = "http://wthrcdn.etouch.cn/weather_mini?citykey=101071201";
        var url = "https://www.sojson.com/open/api/weather/json.shtml?city=%E5%8D%97%E6%98%8C";
        $.ajax({
            url: url,
            // scriptCharset: "gbk",
            method:'GET',
            crossDomain: true,
            success: function (data) {
                //请求无误，但是数据有误，desc为接口返回的数据中的一部分，往下看
                if(desc != "ok"){
                    alert("请求地区有误");
                }
                //
                //这儿写上正常处理的流程

                var description = "";
                for(var i in data.data){
                    var property=data.data[i];
                    description+=i+" = "+property+"\n";
                }
            },
            error:function(){
                HteOS.Masker.error(weather.$el,'加载天气数据失败');
            }});
		/*$.ajax({
			url: HteOS.API.WEATHER,
			dataType: 'json',
			success: function(data) {
				
			}
		}).done(function(data) {
			HteOS.Masker.unmask(weather.$el);
			var result = data.results[0];
			var weatherData = result.weather_data[0];
			weather.$el.find('.weather-pic').attr('src',
				weatherData.dayPictureUrl);
			/!*weather.$el.find('.weather-temperature')
				.html(weatherData.temperature);*!/
			/!*if (weatherData.temperature.length < 5) {
				weather.$el.find('.weather-pic')
					.insertBefore(weather.$el
						.find('.weather-temperature'));
			}*!/
			weather.$el.find('.weather-city').html(result.currentCity+" "+ weatherData.temperature);
			weather.$el.find('.weather-weather')
				.html(weatherData.weather + ' / ' + weatherData.wind);
		}).fail(function() {
			HteOS.Masker.mask(weather.$el,{
				msg: '加载失败',
				transparent: true
			});
			weather.$el.click(function() {
				weather.load();
			});
		});*/
	};
	
	Weather.prototype.stop = function(){
		window.clearInterval(this.intervalId);
	}
	
	Weather.prototype.destroy = function(){
		this.stop();
		this.$el = null;
	}
})());