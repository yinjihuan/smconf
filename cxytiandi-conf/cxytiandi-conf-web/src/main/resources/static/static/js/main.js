// JavaScript Document

/* 登录方式切换 */
$(".login .tab02").click(function(){
	$(".login .content02").show();
	$(".login .content01").hide();
	$(".login .tab02").addClass("current");
	$(".login .tab01").removeClass("current");
})

$(".login .tab01").click(function(){
	$(".login .content01").show();
	$(".login .content02").hide();
	$(".login .tab01").addClass("current");
	$(".login .tab02").removeClass("current");
})

/* 发送验证码等待状态 */
$(".sendCode").click(function(){
	$(".waiting").show();
	$(".sendCode").hide();
})

/* 勾选记住手机号码 */
$(".option .check").click(function(){
	var a = $(this).find("img").css("display");
	if(a == "block"){
		$(".option .check").removeClass("selected");
		$(".option .check a img").hide();
	}else{
		$(".option .check").addClass("selected");
		$(".option .check a img").show();
	}
})

/* 勾选我已阅读并接受《用户服务协议》 */
$(".agreement .check").click(function(){
	var a = $(this).find("img").css("display");
	if(a == "block"){
		$(".agreement .check").removeClass("selected");
		$(".agreement .check a img").hide();
	}else{
		$(".agreement .check").addClass("selected");
		$(".agreement .check a img").show();
	}
})

/* 点击相应区域，让其内的input获取焦点 */
$(".inputInfo_content div span").click(function(){
	$(this).find("input").focus();
})

/* 增加押品房产 */
$(".addhouse").click(function(){
	var a=$(".addhouse");
	var x;
	for(var i=0;i<=1;i++){
		if(a[i]==this){
			x=i;
			break;
		}
	}
	$(".inputInfo").eq(x+1).show();
	$(".addhouse").eq(x+1).show();
	$(".addhouse").eq(x).hide();
	n=x;
})

/* 删除押品房产 */
$(".delete").click(function(){
	var a=$(".delete");
	var x;
	for(var i=0;i<a.length;i++){
		if(a[i]==this){
			x=i;
			break;
		}
	}
	$(".inputInfo").eq(x+1).hide();
	$(".addhouse").hide();
	$(".addhouse").eq(x).show();
	n=x;
})

/* 款项用途类型选择 */
$(".radio").click(function(){
	var a=$(".radio");
	var x;
	for(var i=0;i<a.length;i++){
		if(a[i]==this){
			x=i;
			break;
		}
	}
	$(".radio span").removeClass("current");
	$(".radio span").eq(x).addClass("current");
	$(".radio span i").removeClass("current");
	$(".radio span i").eq(x).addClass("current");
	n=x;
})

/* 说明文字动效 */
$(".note").hover(function(){
	n=$(".note").index(this);
	$(".noteText").eq(n).show();
},function(){
	n=$(".note").index(this);
	$(".noteText").eq(n).hide();
})

/* 内容切换 */
$(".detailMain01 li").click(function(){
	var a=$(".detailMain01 li");
	var x;
	for(var i=0;i<a.length;i++){
		if(a[i]==this){
			x=i;
			break;
		}
	}
	$(".detailMain01 li").removeClass("current");
	$(".detailMain01 li").eq(x).addClass("current");
	$(".detailMain01 .content").hide();
	$(".detailMain01 .content").eq(x).show();
	n=x;
})

$(".detailMain02 li").click(function(){
	var a=$(".detailMain02 li");
	var x;
	for(var i=0;i<a.length;i++){
		if(a[i]==this){
			x=i;
			break;
		}
	}
	$(".detailMain02 li").removeClass("current");
	$(".detailMain02 li").eq(x).addClass("current");
	$(".detailMain02 .content").hide();
	$(".detailMain02 .content").eq(x).show();
	n=x;
})
