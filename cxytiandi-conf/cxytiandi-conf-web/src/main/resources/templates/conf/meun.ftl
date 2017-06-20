 <aside class="main-sidebar">
    <section class="sidebar">
      <ul class="sidebar-menu">
      	<li class="active treeview">
          <a href="#">
            <i class="fa fa-id-card"></i> <span>配置管理</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
          	<#if login_user_envs?seq_contains('dev')>
          		<li <#if env?? && env == 'dev'>class="active"</#if>><a href="../?env=dev"><i class="fa fa-circle-o"></i>程序开发环境</a></li>
          	</#if>
          	
          	<#if login_user_envs?seq_contains('test')>
           	 	<li <#if env?? && env == 'test'>class="active"</#if>><a href="../?env=test"><i class="fa fa-circle-o"></i>线下测试环境</a></li>
            </#if>
            
            <#if login_user_envs?seq_contains('online')>
           	 	<li <#if env?? && env == 'online'>class="active"</#if>><a href="../?env=online"><i class="fa fa-circle-o"></i>线上测试环境</a></li>
            </#if>
            
            <#if login_user_envs?seq_contains('prod')>
           		 <li <#if env?? && env == 'prod'>class="active"</#if>><a href="../?env=prod"><i class="fa fa-circle-o"></i>程序生产环境</a></li>
            </#if>
          </ul>
        </li>
      </ul>
    </section>
 </aside>

