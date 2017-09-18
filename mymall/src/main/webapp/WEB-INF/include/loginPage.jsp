<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


<script>
$(function(){
	
	check();
	load();
}
);

function check(){
	
	<c:if test="${!empty msg}">
	$("span.errorMessage").html("${msg}");
	$("div.loginErrorMessageDiv").show();		
	</c:if>
	
	$("form.loginForm").submit(function(){
		if(0==$("#name").val().length||0==$("#password").val().length){
			$("span.errorMessage").html("请输入账号密码");
			$("div.loginErrorMessageDiv").show();			
			return false;
		}
		return true;
	});
	
	$("form.loginForm input").keyup(function(){
		$("div.loginErrorMessageDiv").hide();	
	});
	
	
	
	var left = window.innerWidth/2+162;
	$("div.loginSmallDiv").css("left",left);
}

function load(){
	var uname = $("#username");
	var pwd = $("#password");
	var remember = $("input[type='checkbox']");
		  var arrStr = document.cookie.split(";"); 
		  for(var i = 0;i < arrStr.length;i ++){ 
		  var temp = arrStr[i].split("="); 
		  if($.trim(temp[0]) == "password"){
			     pwd.val(temp[1]);  
		  }	else if($.trim(temp[0]) == "username") {
			  uname.val(temp[1]); 
				remember.checked=true;
		  } 
		  }
};

function login_check(){

	var uname = $("#username");
	var pwd = $("#password");
	var rcode = $("#randomcode");
	var remember = $("input[type='checkbox']").is(':checked');

		if ($.trim(uname.val()) == "") {
			$('#username_span').css('display','block');
			$("#passwordspan").html("");
			$("#userspan")
					.html(
							"<font color='red'>用户名不能为空！</font>");
			uname.focus();
		} else if ($.trim(pwd.val()) == "") {
			$('#username_span').css('display','none');
			$('#password_span').css('display','block');
			$("#userspan").html("");
			$("#passwordspan").html(
					"<font color='red'>密码不能为空！</font>");
			pwd.focus();
		} else if ($.trim(rcode.val()) == "") {
			$("#userspan").html("");
			$("#randomcode_span")
					.html(
							"<font color='red'>验证码不能为空！</font>");
			rcode.focus();
		}else {
			$('#password_span').css('display','none');
			$("#userspan").html("");
			$("#passwordspan").html("");
			$("#randomcode").html("");
			$.ajax({
						url : '${pageContext.request.contextPath}/login',
						data : {
							username : uname.val(),
							password : pwd.val(),
							checkCode : rcode.val(),
							remember : remember,
						},
						type : 'post',
						cache : false,
						dataType : 'json',
						success : function(data) {
							if (data.msg == 'account_error') {
						      console.log("account_error");
								$("#errorspan")
										.html(
												"<font color='red'> 用户不存在！</font>");
								randomcode_refresh();
							}else if (data.msg == 'checkCode_error') {
								$("#errorspan")
								.html(
										"<font color='red'> 驗證碼錯誤！</font>");
								randomcode_refresh();
					        } else if (data.msg == 'password_error') {
								$("#errorspan")
										.html(
												"<font color='red'> 密码错误！</font>");
								randomcode_refresh();
							} else if (data.msg == 'authentication_error') {
								$("#errorspan")
										.html(
												"<font color='red'> 您没有授权！</font>");
								randomcode_refresh();
							} else if (data.msg == 'success') {
								location.href = "${pageContext.request.contextPath}/forehome";
					          }else {
					        	  $("#errorspan")
									.html(
										"<font color='red'> 您没有授权！</font>");
							         randomcode_refresh();
							}
						},
						error : function() {
							// view("异常！");  
							alert("异常！");
						}
					});
		}};

function randomcode_refresh(){
	$("#randomcode_img").attr('src',
			'${pageContext.request.contextPath}/validatecode.jsp?time' + new Date().getTime());
}

</script>


<div id="loginDiv" style="position: relative">

	<div class="simpleLogo">
		<a href="${pageContext.request.contextPath}/forehome"><img src="img/site/simpleLogo.png"></a>
	</div>

	
	<img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">
	
	<form class="loginForm" action="${pageContext.request.contextPath}/login" submit="" method="post">
		<div id="loginSmallDiv" class="loginSmallDiv">
			<div class="loginErrorMessageDiv">
				<div class="alert alert-danger" >
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				  	<span class="errorMessage"></span>
				</div>
			</div>
				
			<div class="login_acount_text">账户登录</div>
			<div class="loginInput" >
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-user"></span>
				</span>
				<input id="username" name="username" placeholder="手机/会员名/邮箱" type="text">
					<div id="username_span"style="display:none;padding-bottom:7px;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="userspan"></span>
					</div>			
			</div>
			
			<div class="loginInput " >
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-lock"></span>
				</span>
				<input id="password" name="password" type="password" placeholder="密码" type="text">
				<div id="password_span"style="display:none;margin:0 0 0 0;padding:0 0 0 0;">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="passwordspan"></span>
				</div>
			</div>
			
			<div id="randiv" style="display:block">
							
			验证码：<input id="randomcode" name="randomcode" size="8" /> <img
			id="randomcode_img" src="${pageContext.request.contextPath}/validatecode.jsp" alt=""
				width="56" height="20"  /> <a
				href=javascript:randomcode_refresh()>刷新</a>
			<div style="margin-left:98px;">
			<span id="randomcode_span"></span>
			</div>	
				
			</div>
			
			<!-- REMEMBERME -->					
			<div class="checkbox" >
			   记住密码 <input type="checkbox" name="remember" id="remember" checked="checked" style="margin-left:20px;"/>  
			<span id="errorspan" style="margin-left:88px;"></span>
			</div>
			
			<div>
				<a class="notImplementLink" href="#nowhere">忘记登录密码</a> 
				<a href="register.jsp" class="pull-right">免费注册</a> 
			</div>
			<div style="margin-top:20px">
				<a onclick="login_check()"><button class="btn btn-block redButton" id="login"  type="button">登录</button></a>
			</div>
		</div>	
	</form>


</div>	