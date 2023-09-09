/* 
* 密码框 
 */

$("#paypwd").on("input propertychange", function(){
	showPaypwd(true);
})

function showPaypwd(focus){
	var pwd = $("#paypwd").val();
	
	$(".paypwd li").each(function(index, elem){
		if (index < pwd.length){
			$(this).text("•");
			$(this).removeClass("b");
		} else if (focus && index == pwd.length){
			$(this).text("");
			$(this).addClass("b");
		} else {
			$(this).text("");
			$(this).removeClass("b");
		}
	});
}

$("#paypwd").focus(function(){
	$(".paypwd li").css({
		"border-color": "#FF6700"});
	showPaypwd(true);
})

$("#paypwd").blur(function(){
	$(".paypwd li").css({
		"border-color": "#E0E0E0"});
	
	showPaypwd(false);
})

$("#pay").submit(function(){
	var pwd = $("#paypwd").val();
	
	if (pwd.length<6){
		alert("请输入6位支付密码!")
		return false;
	}
})