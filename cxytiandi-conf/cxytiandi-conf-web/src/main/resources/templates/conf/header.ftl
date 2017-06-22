  <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="../static/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="../static/bootstrap/css/ionicons.min.css">
  <link rel="stylesheet" href="../static/dist/css/AdminLTE.min.css">
  <link rel="stylesheet" href="../static/dist/css/skins/_all-skins.min.css">
<header class="main-header">
    <!-- Logo -->
    <a href="../" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>${projectName!}</b>配置系统</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>${projectName!}</b>配置系统</span>
    </a>
    
    <nav class="navbar navbar-static-top">
   	 <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li>
            <a target="_blank" href="https://github.com/yinjihuan/smconf">文档</a>
          </li>
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <span class="hidden-xs"><#if login_user_name?exists>${login_user_name}</#if></span>
            </a>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="../user/logout">退出</a>
          </li>
        </ul>
      </div>
    </nav>
</header>

<script src="../static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="../static/plugins/jQuery/jquery-ui.min.js"></script>
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/plugins/fastclick/fastclick.js"></script>
<script src="../static/dist/js/app.min.js"></script>
<script src="../static/layer/layer.js"></script>
<script src="../static/layer/laypage/laypage.js"></script>
 