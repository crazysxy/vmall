/**
 * 搜索页面的js
 */

var allSorts;

// 搜索商品
function searchGoods(name, goodsSortId, firstSortId){
	$.ajax({
		url: '/api/goods/search',
		type: 'get',
		data: {name: name, goodsSortId: goodsSortId, firstSortId: firstSortId},
		dataType: 'json',
		success: function(data){
			var html = "";
			$.each(data, function(index, item){
				if ((index+1)%4==0) {
					html += '<li class="r">';
				} else {
					html += '<li>';
				}
				
				html += '<a href="/goods?id='+item.id+'">\
							<div class="img">\
								<img src="upload/'+item.pictures[0].name+'" >\
							</div>\
							<h2 class="desc">'+item.name+'</h2>\
							<p class="price">'+item.price+'元 起</p>\
							<div class="thumbs">\
								<ul class="clear">';
				
				$.each(item.pictures, function(i, pic){
					html += '<li><img src="upload/'+pic.name+'" ></li>'
				});
									
				html += '		</ul>\
							</div>\
							<div class="flags">\
								<span>赠</span>\
								<span>加价购</span>\
							</div>\
						</a>\
					</li>';
			});
			
			$(".goods>ul").html(html);
		},
		error: function(){
			
		}
	});
}

/**
 * 获取一级分类
 */
function getFirstSort(){
	$.ajax({
		url: '/api/goodsSort/getParent',
		type: 'get',
		data: {},
		dataType: 'json',
		success: function(data){
			// 保存所有分类信息
			allSorts = data;
			
			var html = '<span class="label">一级分类:</span>';
			
			if (goodsSortId == 0){
				html += '<li class="active"><a href="javascript:;" data-sort="0">全部</a></li>';
			} else {
				html += '<li><a href="javascript:;" data-sort="0">全部</a></li>';
			}
				
			
			$.each(data, function(index, item){
				if (item.id == getFirstSortId(data, goodsSortId)){
					html += '<li class="active"><a href="javascript:;" data-sort="'+item.id+'">'+item.name+'</a></li>';
				} else {
					html += '<li><a href="javascript:;" data-sort="'+item.id+'">'+item.name+'</a></li>';
				}
				
			});
			$(".first-sort").html(html);
			
			// 生成二级分类
			if (getFirstSortId(data, goodsSortId) != 0){
				// 调用方法生成代码
				initSecondSort(data, getFirstSortId(data, goodsSortId));
			} else {
				$(".second-sort").hide();
			}
		},
		error: function(){
			
		}
	});
}

function initSecondSort(data, firstSortId){
	// 遍历查找当前的一级分类
	for (var i=0; i<data.length; i++){
		if (data[i].id == firstSortId){
			// 遍历 data[i] 里面的二级分类 sonSorts
			var html = '<span class="label">二级分类:</span>';
			
			if (goodsSortId == 0){
				html += '<li class="active"><a href="javascript:;" data-sort="0">全部</a></li>';
			} else {
				html += '<li><a href="javascript:;" data-sort="0">全部</a></li>';
			}
			
			$.each(data[i].sonSorts, function(index, item){
				
				if (item.id == goodsSortId){
					html += '<li class="active"><a href="javascript:;" data-sort="'+item.id+'">'+item.name+'</a></li>';
				} else {
					html += '<li><a href="javascript:;"  data-sort="'+item.id+'">'+item.name+'</a></li>';
				}
			});
			
			$(".second-sort").html(html);
			$(".second-sort").show();
			
			break;
		}
	}
}

function getFirstSortId(data, secondSortId){
	
	// 遍历一级分类
	for(var i=0; i<data.length; i++){
		
		if (data[i].sonSorts == null) continue;
		
		// 遍历二级分类
		for (var j=0; j<data[i].sonSorts.length; j++){
			
			if (data[i].sonSorts[j].id == secondSortId){
				return data[i].id;
			}
		}
	}
	
	return 0;
}

/**
 * 给一级分类绑定事件, 预绑定
 */
$(".first-sort").on("click", "a", function(){
	$(".first-sort li").removeClass("active");
	$(this).parent().addClass("active");
	var firstSortId = $(this).attr("data-sort");
	
	goodsSortId = 0;
	
	// 切换二级分类
	if (firstSortId != 0){
		initSecondSort(allSorts, firstSortId);
	} else {
		$(".second-sort").hide();
	}
	
	// 重新刷新数据
	searchGoods(name, goodsSortId, firstSortId);
});

$(".second-sort").on("click", "a", function(){
	$(".second-sort li").removeClass("active");
	$(this).parent().addClass("active");
	goodsSortId = $(this).attr("data-sort");
	var firstSortId = $(".first-sort .active a").attr("data-sort");
	
	// 重新刷新数据
	searchGoods(name, goodsSortId, firstSortId);
});


