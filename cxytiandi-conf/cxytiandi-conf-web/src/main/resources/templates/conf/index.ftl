<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><#if env=='test'>线下测试环境</#if><#if env=='dev'>程序开发环境</#if><#if env=='online'>线上测试环境</#if><#if env=='prod'>程序生产环境</#if> ${msg!}- ${projectName!}配置系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <style>
    .value_td,.delete {cursor:pointer;}
    .value_desc {width:300px;}
  </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <#include "header.ftl">
  <#include "meun.ftl">
   <div class="content-wrapper">
    <section class="content">
      <div class="row">
      	<div class="col-xs-12">
          <div class="box">
            <!-- /.box-header -->
            <div class="box-body">
              <div id="example1_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
              	<#if msg == ''>
	              	<div class="row"  style="margin-bottom:10px;">
		              	<form action="/">
		              		<div class="col-sm-12">
			              		<div id="example1_filter" class="dataTables_filter">
			              			<input name="page" id="page" type="hidden" value="1"/>
			              			<input name="env"  type="hidden" value="${env!}"/>
			              			系统名称：<input name="systemName"  type="text" value="${conf.systemName!}" class="form-control"/>&nbsp;
			              			配置文件名称：<input name="confFileName" type="text" value="${conf.confFileName!}" class="form-control" />&nbsp;
			              			配置Key：<input name="key" type="text" value="${conf.key!}" class="form-control"/>&nbsp;
			              			<button type="submit" class="btn btn-primary" id="queryBtn">查询</button>
			              		</div>
		              		</div>
		              	</form>
	              	</div>
              	</#if>
              	<div class="row">
              	<div class="col-sm-12">
              	<table id="example1" class="table table-bordered table-striped dataTable" role="grid" aria-describedby="example1_info">
              		<#if msg == ''>
	                	<thead>
	             		   <tr role="row">
	             		      <th>系统名称</th>
	             		   	  <th>配置文件名称</th>
	             		   	  <th>配置Key</th>
	             		   	  <th>配置Value</th>
	             		   	  <th>描述</th>
	             		   	  <th>节点信息</th>
	             		   	  <th>创建时间</th>
	             		   	  <th>修改时间</th>
	             		   	  <th>操作</th>
	             		   </tr>
	               		 </thead>
	                <#else>
	                  <h1 style="text-align:center;">${msg}</h1>
               		</#if>
	                <tbody>
	                	<#if confList??>
	                		<#list confList as bo>
		                		<tr>
		                			<td style="line-height: 100px;">${bo.systemName!}</td>
		                			<td style="line-height: 100px;">${bo.confFileName!}</td>
		                			<td style="line-height: 100px;">${bo.key!}</td>
		                			<td style="line-height: 100px;" class="value_td" status="0">
		                				<p>${bo.value!}</p>
		                				<div style="display:none;">
		                					<input value="${bo.value!}" class="value_inp"/>
		                					<input class="value_desc" placeholder="修改备注"/>
		                					<input type="button" value="保存" class="save_btn" data-id="${bo.id!}"/>
		                					<input type="button" value="取消" class="cancel_btn"/>
		                				</div>
		                			</td>
		                			<td style="line-height: 100px;">${bo.desc!}</td>
		                			<td >
		                				<div style="height:100px;overflow:auto;">
			                				<#if bo.nodes??>
		                						<#list bo.nodes as n>
		                							${n!}<br/>
		                						</#list>
		                					</#if>
		                				</div>
		                			</td>
		                			<td style="line-height: 100px;">${bo.createDate?datetime!}</td>
		                			<td style="line-height: 100px;">${bo.modifyDate?datetime!}</td>
		                			<td style="line-height: 100px;">
		                				<#if (bo.nodes??) && bo.nodes?size gt 0>
		                					<a href="javascript:layer.alert('有节点订阅不能删除');" style="color:#ccc;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
		                				<#else>
		                				   <a class="delete" data-id="${bo.id!}">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
		                				</#if>
		                				<a target="_blank" href="logs/${bo.id!}">历史</a>
		                			</td>
		                		</tr>
	                		</#list>
	                	</#if>
	                </tbody>
              </table>
              </div></div>
              <div class="row">
              	<div class="col-sm-5">
              		<!--<div class="dataTables_info" id="example1_info" role="status" aria-live="polite">共1000条&nbsp;100页</div>-->
              	</div>
              	<div class="col-sm-7">
              		<div class="dataTables_paginate paging_simple_numbers" id="pageBar" style="text-align:right;">
              			
              		</div>
              	</div>
              	</div></div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
      </div>
    </section>
  </div>
</div>
<script>
	$(".value_td").click(function(){
		$(this).find("div").css("display","block");
		$(this).find("p").css("display","none");
		$(this).attr("status", "1");
	});
	
	$(".value_td,.save_btn,.cancel_btn").click(function(event) {
		event.stopPropagation();
	});
		
	$(".save_btn").click(function(){
		var node = $(this);
		var value = node.parent().find(".value_inp").val();
		var desc = node.parent().find(".value_desc").val();
		if (value == "") {
			layer.msg("值不能为空");
			return false;
		}
		if (desc == "") {
			layer.msg("修改备注不能为空");
			return false;
		}
		var id = node.attr("data-id");
		$.ajax({
		    url:"../conf/update",
	        method:'POST',
			data:{"value":value,"id":id, "desc":desc},
			success:function () {
				node.parent().css("display","none");
				node.parent().parent().find("p").html(value);
				node.parent().parent().find("p").css("display","block");
				node.parent().find(".value_desc").val("");
	        },
			error:function () {
				layer.alert('修改失败,请重试!')
	        }
		});
		
	});
	
	
	$(".delete").click(function(){
		var node = $(this);
		var id = node.attr("data-id");
		layer.confirm("确认删除吗？请确认代码中的配置key已删除", function(){
			$.ajax({
			    url:"../conf/remove",
		        method:'POST',
				data:{"id":id},
				success:function () {
					window.location.reload();
		        },
				error:function () {
					layer.alert('删除失败,请重试!')
		        }
			});
		});
	});
	
	$(".cancel_btn").click(function(){
		var node = $(this);
		node.parent().css("display","none");
		//node.parent().parent().find("p").html(value);
		node.parent().parent().find("p").css("display","block");
		node.parent().find(".value_desc").val("");
	});
</script>
</body>
</html>
