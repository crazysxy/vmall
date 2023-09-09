/* 构造函数 */
function Swiper(options){
	this.imgs = options.imgs;
	this.width = options.width||1226;
	this.height = options.height||460;
	this.elem = $(options.elem);
	this.index = 0;
	this.speed = options.speed||3000;
}

/* 原型对象 */
Swiper.prototype = {
	
	constructor: Swiper,
	
	init: function(){
		var that = this;
		// 初始化主窗口
		this.elem.css({
			position: 'relative',
			width: this.width,
			height: this.height,
		});
		
		// 初始化图片
		var images = $("<div class='imgs'></div>")
		
		for (var i=0; i<this.imgs.length; i++){
			var img = $('<img src="'+this.imgs[i].img+'">');
			img.css({
				width: this.width,
				height: this.height,});
				
			var a = $('<a href="'+this.imgs[i].link+'"></a>')
			a.css({
				width: this.width,
				height: this.height,
				position: 'absolute',
				display: 'none'});
			a.append(img);
			if (i==0){
				a.show();
			}
			images.append(a);
		}
		
		// 添加到主窗口
		this.elem.append(images);
		
		// 添加左右按钮
		var pre = $("<div class='pre'></div>");
		pre.css({
			position: 'absolute',
			left: 234,
			top: this.height/2-42,
			width: 42,
			height: 69,
			backgroundImage: 'url(img/icon-slides.png)',
			backgroundPosition: 83,
			cursor: 'pointer',
		});
		
		pre.click(function(){
			that.preImg();
		});
		
		pre.mouseover(function(){
			pre.css({
				backgroundPosition: 0,
			});
		});
		pre.mouseout(function(){
			pre.css({
				backgroundPosition: 83,
			});
		});
		this.elem.append(pre);
		
		var next = $("<div class='next'></div>");
		next.css({
			position: 'absolute',
			right: 0,
			top: this.height/2-42,
			width: 42,
			height: 69,
			backgroundImage: 'url(img/icon-slides.png)',
			backgroundPosition: 42,
			cursor: 'pointer',
		});
		
		next.click(function(){
			that.nextImg();
		});
		
		next.mouseover(function(){
			next.css({
				backgroundPosition: 124,
			});
		});
		next.mouseout(function(){
			next.css({
				backgroundPosition: 42,
			});
		});
		this.elem.append(next);
		
		
		// 开始关闭
		this.elem.mouseover(function(){
			that.stop();
		});
		this.elem.mouseout(function(){
			that.start();
		});
		
		// 生成点
		var points = $("<ul></ul>");
		for(var i=0; i<this.imgs.length; i++){
			var point = $("<li data-index="+i+"></li>")
			point.css({
				width: 6,
				height: 6,
				border: '2px solid rgba(255,255,255,.3)',
				background: 'rgba(0,0,0,.4)',
				borderRadius: 10,
				margin: 4,
				float: 'left',
			});
			
			if (i==0){
				point.css({
					border: '2px solid rgba(0,0,0,.4)',
					background: 'rgba(255,255,255,.4)',
				});
			}
			
			point.mouseover(function(){
				$(this).css({
					border: '2px solid rgba(0,0,0,.4)',
					background: 'rgba(255,255,255,.4)',
				});
			});
			point.mouseout(function(){
				
				if (that.index != $(this).attr("data-index")){
					$(this).css({
						border: '2px solid rgba(255,255,255,.3)',
						background: 'rgba(0,0,0,.4)',
					});
				}
			});
			
			point.click(function(){
				that.skipTo($(this).attr("data-index"));
			})
			
			points.append(point);
		}
		
		points.css({
			position: 'absolute',
			right: 30,
			bottom: 20,
		});
		
		this.elem.append(points);
		
		// 启动轮播
		this.start();
	},
	// 开始轮播
	start: function(){
		if (this.interval == undefined || this.interval==null){
			var that = this;
			this.interval = setInterval(function(){
				that.nextImg();
			}, this.speed);
		}
	},
	// 停止轮播
	stop: function(){
		if (this.interval != undefined && this.interval!=null){
			clearInterval(this.interval);
			this.interval = null;
		}
	},
	// 下一页
	nextImg: function(){
		this.skipTo(this.index+1);
	},
	// 上一页
	preImg: function(){
		this.skipTo(this.index-1);
	},
	// 某一页
	skipTo: function(i){
		i = i<0 ? this.imgs.length-1 : i;
		i = i>=this.imgs.length ? 0 : i;
		
		this.elem.find(".imgs a").stop().fadeOut();
		this.elem.find(".imgs a:eq("+i+")").stop().fadeIn();
		
		this.index = i;
		
		this.elem.find("ul li").css({
			border: '2px solid rgba(255,255,255,.3)',
			background: 'rgba(0,0,0,.4)',
		});
		this.elem.find("ul li:eq("+i+")").css({
			border: '2px solid rgba(0,0,0,.4)',			background: 'rgba(255,255,255,.4)',
		});
	}
}

