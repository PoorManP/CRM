

<!DOCTYPE html><%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" +
			request.getServerName() + ":" + request.getServerPort() +
			request.getContextPath() + "/";
//		response.setContentType();
%>
<html>
<head>
	<base href=<%=basePath%>>
	<meta http-equiv="Content-Type" content="text/html; charset=GB2312" />
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>


	<script type="text/javascript">

		$(function () {


			if (window.top != window) {
				window.top.location = window.location;
			}
			$("#loginAct").val("");
			//让用户名自动获取点
			$("#loginAct").focus();

		//	为登录按钮绑定事件 登录事件
			$("#btn").click(function () {

				login();

			});

			$(window).keydown(function (event) {
				if (event.keyCode == 13) {
					login();
				}

			});



		});
		function login() {
			//登录
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());

			if (loginAct == "" || loginPwd == "") {
				$("#msg").html("账号密码或密码不能为空!");
				return false;
			}

			$.ajax(
					{
						url: "login.do",
						data: {"loginAct":loginAct, "loginPwd": loginPwd},
						type: "post",
						dataType: "json",
						success: function (response) {

							// alert("dsf");
							if (response.success) {


								window.location.href = "workbench/index.jsp";
								// 跳转到欢迎页
								//如果登录成功
							} else {
								// 失败
								$("#msg").html(response.msg);
							}
						}}
			);
		}
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white;
		 font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;color: red">
						
							<span id="msg"></span>
						
					</div>
<%--					默认行为是提交表彰按钮
						触发的行为由自己手动
--%>
					<button type="button" id="btn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>