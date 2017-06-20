<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>${projectName!}配置系统</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="../static/bootstrap/fonts/font-awesome.min.css">
	<link rel="stylesheet" href="../static/bootstrap/css/ionicons.min.css">
	<link rel="stylesheet" href="../static/dist/css/AdminLTE.min.css">
</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="http://cxytiandi.com/"><b>${projectName!}配置系统</b></a>
		</div>
		<div class="login-box-body">
			<p class="login-box-msg" style="color:red;">${msg!}</p>

			<form action="../user/login" method="post">
				<div class="form-group has-feedback">
					<input name="username" type="text" class="form-control" placeholder="用户名">
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input name="pass" type="password" class="form-control" placeholder="密码">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
