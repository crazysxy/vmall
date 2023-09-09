/**
 * 专门给主页用的JS
 */

/**
 * 统一调用需要执行的方法
 */


/**
 * 使用ajax 初始化分类列表
 */

function initSort(){
	$.ajax({
		url: '/category/selectAllParent',	// 获取分类信息
		type: 'get',
		dataType: 'json',
		success: function(resp){
			// 成功获取到了一级分类
			// 接下来我们要渲染界面
			var html = "";
			$.each(resp.data, function(index, item){
				if (index >= 10) return false;	// 最多只能显示十个
				html += '<li><a href="javascript:;">'+item.name+'<i class="mi-icon icon-right"></i></a>';
				var width = parseInt((item.children.length+5)/6) * 248;
				width = width>(4*248) ? 4*248 : width;
				html += '<div class="children" style="width: '+width+'px;"><ul class="left">';
				
				// 生成二级分类, 遍历它的二级分类
				$.each(item.children, function(i, son){
					html += '<li><a href="/search?categoryId='+son.id+'">\
							<img src="/upload/'+son.pic+'" >\
							<span>'+son.name+'</span>\
							</a></li>';
					if (i>=23){ 
						return false;
					}
					
					if ( (i+1)%6 == 0 ){// 5, 11, 17, 23, 29
						html += '</ul>';
						html += '<ul class="left">';
					}
					
				})
				
				html += '</ul></div>';
				html += '</li>';
			});
			$(".goods-sorts>ul").html(html);
		},
		error: function(){
			
		}
	});
}

// initSort();

