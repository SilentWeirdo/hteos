(function() {
	/**
	 * @author 李球 <service@hteos.com>
	 * @class HteOS.tile.Music 音乐播放器应用
	 * @constructor
	 */
	var Music = HteOS.controller.Music = function(tile) {
		var music = this;
		music.$el = tile.getEl();
		if (window.HTMLAudioElement) {
			this.load();
		} else {
            HteOS.Masker.unmask(music.$el);
			HteOS.Masker.error(music.$el,"不支持音乐播放");
		}
	};
	/**
	 * 渲染播放器应用
	 */
	Music.prototype.render = function() {
		this.$el.find(".hte-tile-content").html('<div class="hte-tile-inner">' + '<div class="music-panel">' + '<audio class="music-audio" src="" autoplay></audio>' + '<img class="music-pic" src="http://img.9ku.com/fm/images/fm/noAlbumPic.png" />' + '<div class="music-info">' + '<div class="music-title">-</div>' + '<div class="music-artist">-</div>' + '<div class="music-controls">' + '<span data-toggle="tooltip" data-placement="bottom" data-original-title="下一首" class="music-control next-control glyphicon glyphicon-fast-forward"></span>' + '<span data-toggle="tooltip" data-placement="bottom" data-original-title="暂停" class="music-control pause-control glyphicon glyphicon-pause"></span>' + '<span data-toggle="tooltip" data-placement="bottom" data-original-title="播放" class="music-control play-control glyphicon glyphicon-play" style="display: none;"></span>' + '<span class="music-duration">-</span>' + '<span class="music-status"></span>' + '</div>' + '</div>' + '</div>' + '<div class="music-copyright">9ku.com</div>' + '</div>');
		this.rendered = true;
		$(".music-pic").error(function(){
			$(this).attr("src","http://www.9ku.com/fm/images/fm/noAlbumPic.png");
		});
		this.initEvents();
	};

	/**
	 * 初始控件的事件
	 */
	Music.prototype.initEvents = function() {
		var music = this,
			el = this.$el,
			audio = el.find('audio');
		// 下一首
		el.find(".next-control").click(function(event) {
			music.pause();
			window.clearInterval(music.interval);
			music.start();
			event.stopPropagation();
		});
		// 暂停
		el.find(".pause-control").click(function(event) {
			music.pause();
            event.stopPropagation();
		});

		// 播放
		el.find(".play-control").click(function(event) {
			music.play();
            event.stopPropagation();
		});

		// 开始加载
		audio.on('loadstart', function() {
			music.updateStatus('正在加载...');
		});

		// 加载错误
		audio.on('error', function() {
			music.updateStatus('加载失败');
		});

		// 播放结束
		audio.on('ended', function() {
			music.start();
		});

		// 开始播放
		audio.on('play', function() {
			el.find(".pause-control").show();
			el.find(".play-control").hide();
			window.clearInterval(music.interval);
			music.update();
		});

		// 加载元数据
		audio.on('loadedmetadata', function() {
			music.current = audio.get(0).duration;
			music.progress(audio.get(0).duration);
		});
		// 播放暂停
		audio.on('pause', function() {
			window.clearInterval(music.interval);
			el.find(".pause-control").hide();
			el.find(".play-control").show();
		});

	};

	/**
	 * 设置定时器，定时更新播放器的进度
	 */
	Music.prototype.update = function() {
		var music = this;
		if (music.current > 1) {
			music.current -= 1;
			music.progress(music.current);
		}
		music.interval = window.setInterval(function() {
			if (music.current > 1) {
				music.current -= 1;
				music.progress(music.current);
			}
		}, 1000);
	};

	/**
	 * 开始播放音乐，随机选取一首进行播放
	 */
	Music.prototype.start = function() {

	    if(!$song_data){
	        return ;
        }

		var songlist = $song_data[0].songlist,
			index = Math.floor(Math.random() * (songlist.length + 1));
		var song = songlist[index];
		var el = this.$el;
		el.find(".music-title").html(song.gqname);
		el.find(".music-title").attr("title", song.gqname);
		el.find(".music-artist").html(song.gsname);
		el.find(".music-pic").attr("src",song.zjpic);
		el.find(".music-audio").attr("src", song.mp3);
	};

	/**
	 * 更新播放器的状态
	 */
	Music.prototype.updateStatus = function(status) {
		this.$el.find('.music-duration').hide();
		this.$el.find('.music-status').show();
		this.$el.find('.music-status').html(status);
	};

	/**
	 * 更新播放器的进度
	 */
	Music.prototype.progress = function(duration) {
		if (duration) {
			this.$el.find('.music-duration').show();
			this.$el.find('.music-status').hide();
			var minute = Math.floor(duration / 60);
			var second = Math.floor(duration % 60);
			if (second == 60) {
				second = 0;
			}
			minute = minute >= 10 ? minute : "0" + minute;
			second = second >= 10 ? second : "0" + second;
			this.$el.find('.music-duration').html(minute + ":" + second);
		}
	};

	/**
	 * 获取歌曲清单
	 */
	Music.prototype.load = function() {
		var music = this;
		window.$song_data = [];
		window.song_list = function() {};
		HteOS.Masker.mask(music.$el,"正在加载...");
		$.ajax({
			url: "http://www.9ku.com/fm/fenge/huayu.js",
			dataType: "script",
			scriptCharset: 'utf-8',
			success: function() {
				music.render();
				music.start();
				HteOS.Masker.unmask(music.$el);
			},
			error: function() {
				HteOS.Masker.error(music.$el,"加载音乐列表清单失败...");
			}
		});
	};

	/**
	 * 暂停播放
	 */
	Music.prototype.pause = function() {
		var audio = this.$el.find('audio').get(0);
		if(audio){
			this.$el.find('audio').get(0).pause();
		}
	};

	/**
	 * 开始播放
	 */
	Music.prototype.play = function() {
		this.$el.find('audio').get(0).play();
	};
	
	Music.prototype.stop = Music.prototype.pause;
	
	Music.prototype.destroy = function(){
		window.clearInterval(this.interval);
		this.pause();
		this.$el = null;
	}

})();