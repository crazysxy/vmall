/**
 * 订单列表 的js操作
 */

//统一调用
getOrderList();

/*
 * 获取所有的订单信息
 */
function getOrderList(){
	let status = [1,2];
	// ajax
	$.ajax({
		url: '/user/order/arrearage?status=1&status=2',
		type: 'get',
		data: {status:status},
		dataType: 'json',
		success: function(resp){
			var html = "";
			$.each(resp.data, function(index, item){
				
				// 遍历当前订单下的详情, 统计总金额
				var sum = 0;
				$.each(item.orderDetails, function(i, detail){
					sum += detail.price*detail.count;
				});
				
				html += '<div class="order-box '+(item.status<1 ? 'order-pay': '')+'">\
							<div class="order-header">\
								<div class="status '+(item.status<1 ? 'status-pay': '')+'">'
								+ ['等待付款', '等待发货', '运送中', '已收货', '退货中', '退货中','已退货','已取消'][item.status] +
								'</div>\
							</div>\
							\
							<table class="detail">\
								<thead>\
									<tr>\
										<th class="info">'+item.createTime+' | '+(item.address!=null?item.address.contact:'')+' | 订单号：'+item.id+'| 在线支付（支付宝快捷支付）</th>\
										<th class="money">应付金额： <span>'+sum+'</span> 元</th>\
									</tr>\
								</thead>\
								<tbody>'
						$.each(item.orderDetails, function(i, detail){
							html += '<tr>\
										<td class="goods">\
											<div class="goods-box">\
												<div class="img">\
													<a href=""><img src="/upload/'+detail.goods.pics[0].url+'" ></a>\
												</div>\
												<div class="goods-info">\
													<p class="name"><a href="/goods?id='+detail.goods.id+'">'+detail.goods.name+' '+detail.goods.version+' '+detail.goods.color+'</a></p>\
													<p class="price">'+detail.price+'元 x '+detail.count+'</p>\
												</div>\
											</div>\
										</td>\
										<td class="actions">'+
											( item.status == 0 ? '<a class="active" href="/user/order/pay?orderId='+item.id+'">立即付款</a>' : '') +
											( item.status == 0||item.status == 1 ||item.status==2 ? '<a class="" style="background: #1e9fff;color: #F25807"  href="/user/order/cancel?orderId='+item.id+'">取消订单</a>' : '')+
											( item.status == 1 ||item.status==2 ? '<a class="" style="background: #dee06e;color: #F25807"  href="/user/order/updateStatus?orderId='+item.id+'&status=5' + ''+' ">退货</a>' : '')+
											( item.status == 1 ? '<a class="" href="">已支付</a>' : '')+
											( item.status == 2 ? '<a class="" style="background: #62f506;color: #4e55ce" href="/user/order/updateStatus?orderId='+item.id+'&status=3' + ''+' ">确认收货</a>' : '')+
											( item.status == 3 ? '<a class="" href="">已收货</a>' : '')+
											( item.status == 5 ? '<a class="" href="/user/order/updateStatus?orderId='+item.id+'&status=4' + ''+' ">申请退款</a>' : '')+
											( item.status == 4 ? '<a class="" href="">退款中</a>' : '')+
											( item.status == 5 ? '<a class="" href="">退货中</a>' : '')+
											( item.status == 6 ? '<a class="" href="">已退款</a>' : '')+
											( item.status == 7 ? '<a class="" href="">订单已取消</a>' : '')+
											'\
											\
										</td>\
									</tr>'
						});
				
						html +=	'</tbody>\
							</table>\
						</div>';
			});
			
			$('.order-list').html(html);
		},
		error: function(){
			
		}
	});
}
