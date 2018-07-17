<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><#if env=='test'>线下测试环境</#if><#if env=='dev'>程序开发环境</#if><#if env=='online'>线上测试环境</#if><#if env=='prod'>程序生产环境</#if> ${msg!}- ${projectName!}配置系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <style>
    .value_td,.delete {cursor:pointer;}
    .value_desc,.value_inp {width:540px;}
    #push_win ul li{list-style:none;border:2px solid #3c8dbc;border-left:6px solid #3c8dbc;width:260px;float:left;padding:10px;margin-left:10px;margin-top:10px;}
    #edit_win ul li{list-style:none;border:2px solid #3c8dbc;border-left:6px solid #3c8dbc;width:260px;float:left;padding:10px;margin-left:10px;margin-top:10px;}
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
		                			<td style="height: 20px;">${bo.systemName!}</td>
		                			<td style="height: 20px;">${bo.confFileName!}</td>
		                			<td style="height: 20px;">${bo.key!}</td>
		                			<td style="height: 20px;width:300px;word-wrap:break-word;word-break:break-all;" class="value_td" status="0">
		                				<p>${bo.value!}</p>
		                				<div style="display:none;">
		                					<input value='${bo.value!}' class="value_inp"/>
		                					<input class="value_desc" placeholder="修改备注"/>
		                					<input type="button" value="保存" class="save_btn" data-id="${bo.id!}"/>
		                					<input type="button" value="取消" class="cancel_btn"/>
		                				</div>
		                			</td>
		                			<td style="height: 20px;">${bo.desc!}</td>
		                			<td >
		                				<div style="height:20px;">
			                				<#if bo.nodes??>
		                						<#list bo.nodes as n>
		                							${n!}<br/>
		                							<input type="hidden" class="node${bo.id!}" value="${n!}"/>
		                						</#list>
		                					</#if>
		                				</div>
		                			</td>
		                			<td style="height: 20px;">${bo.createDate?datetime!}</td>
		                			<td style="height: 20px;">${bo.modifyDate?datetime!}</td>
		                			<td style="height: 20px;">
		                				<a target="_blank" href="logs/${bo.id!}">历史</a>&nbsp;&nbsp;
		                				<#if (bo.nodes??) && bo.nodes?size gt 0>
		                					<a href="javascript:layer.alert('有节点订阅不能删除');" style="color:#ccc;">删除</a>&nbsp;&nbsp;
		                					<a class="push" style="cursor:pointer;" data-id="${bo.id!}" data-val="${bo.value!}" 
		                						data-system="${bo.systemName!}" data-filename="${bo.confFileName!}" data-key="${bo.key!}">推送</a>&nbsp;&nbsp;
		                				<#else>
		                				    <a class="delete" data-id="${bo.id!}">删除</a>&nbsp;&nbsp;
                                            <a href="javascript:layer.alert('无节点订阅不能推送');" style="color:#ccc;">推送</a>&nbsp;&nbsp;
		                				</#if>
		                				<a class="edit_conf" style="cursor:pointer;" data-id="${bo.id!}" data-val="${bo.value!}"
		                				data-system="${bo.systemName!}" data-filename="${bo.confFileName!}" data-key="${bo.key!}">编辑</a>&nbsp;&nbsp;
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

<div id="push_win" style="display:none;">
	<div style="margin:10px;margin-left:48px;">
        <span class="system_span"></span> > <span class="filename_span"></span> > <span class="key_span"></span> > <span class="value_span"></span>
    </div>
	<div style="margin:10px;">
		<input type="hidden" id="confId"/>
		<input type="checkbox" style="margin-left:38px;" checked="checked" class="check_all"/>全选
	</div>
	<div>
		<ul>
			
		</ul>
	</div>
	<div style="text-align:center;">
			<input type="button" value="推送" id="push_btn" class="btn btn-primary" style="margin-top:20px;margin-bottom:20px;"/>
	</div>
</div>

<div id="edit_win" style="display:none;">
	<div style="margin:10px;margin-left:48px;">
        <span class="system_span"></span> > <span class="filename_span"></span> > <span class="key_span"></span>
    </div>
	<div>
        <textarea style="margin-left:48px;" class="value_inp" rows="8"></textarea>
	</div>
	<div>
        <input style="margin-left:48px;margin-top:10px;" class="value_desc" placeholder="修改备注"/>
	</div>
	<div>
        <input type="checkbox" style="margin-left:48px;margin-top:10px;" checked="checked" class="edit_check_all"/>全选
    </div>
    <div>
        <ul>

        </ul>
    </div>
    <div style="text-align:center;">
        <input type="button" value="修改" id="push_btn" class="btn btn-primary save_btn" style="margin-top:20px;margin-bottom:20px;"/>
    </div>
</div>

<script>
	/**$(".value_td").click(function(){
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
	**/
	
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
	
	// 打开推送配置窗口
	$(".push").click(function(){
		$("#push_win ul li").remove();
		var cid = $(this).attr("data-id");
		$("#confId").val(cid);
		$("#push_win .system_span").html($(this).attr("data-system"));
		$("#push_win .filename_span").html($(this).attr("data-filename"));
		$("#push_win .key_span").html($(this).attr("data-key"));
		$("#push_win .value_span").html($(this).attr("data-val"));
		$(".node"+cid).each(function(i, o){
			$("#push_win ul").append("<li><input name='node_chk' type='checkbox' checked='checked' value='"+$(o).val()+"'/>"+$(o).val()+"</li>");
		});
		layer.open({
		  type: 1,
		  title: "推送配置信息",
		  closeBtn: 1,
		  area: ['630px', '400px'],
		  shadeClose: true,
		  content: $('#push_win')
		});
	});
	
	// 推送配置全选
	$(".check_all").click(function(){
		if(this.checked){ 
			$("#push_win [name=node_chk]").prop("checked", "checked");
		}else{ 
			$("#push_win [name=node_chk]").removeAttr("checked");
		} 
	});
	
	// 修改配置全选
	$(".edit_check_all").click(function(){
		if(this.checked){ 
			$("#edit_win [name=node_chk]").prop("checked", "checked");
		}else{ 
			$("#edit_win [name=node_chk]").removeAttr("checked");
		} 
	});
	
	// 推送确认按钮
	$("#push_btn").click(function(){
		var confId = $("#confId").val();
		var size = $("#push_win [name=node_chk]:checked").size();
		if (size == 0) {
			layer.alert('请选择要推送的节点信息!')
			return;
		}
		var datas = new Array();
		$("#push_win [name=node_chk]:checked").each(function(i,o){
			datas.push({"node":$(o).val(),"id":confId});
		});
		$.ajax({
		    url:"../conf/push",
	        method:'POST',
	        contentType: "application/json",  
            dataType: "json", 
			data:JSON.stringify(datas),
			success:function () {
				layer.alert('推送成功!',function(){
					layer.closeAll();
				});
				
	        },
			error:function () {
				layer.alert('推送失败,请重试!')
	        }
		});
	});

	// 打开编辑窗口
	$(".edit_conf").click(function () {
        $("#edit_win ul li").remove();
        var cid = $(this).attr("data-id");
        $("#confId").val(cid);
        var value = $(this).attr("data-val");
        $("#edit_win .value_inp").val(value);
        $("#edit_win .system_span").html($(this).attr("data-system"));
		$("#edit_win .filename_span").html($(this).attr("data-filename"));
		$("#edit_win .key_span").html($(this).attr("data-key"));
        $(".node"+cid).each(function(i, o){
            $("#edit_win ul").append("<li><input name='node_chk' type='checkbox' checked='checked' value='"+$(o).val()+"'/>"+$(o).val()+"</li>");
        });
        layer.open({
            type: 1,
            title: "修改配置信息",
            closeBtn: 1,
            area: ['630px', '500px'],
            shadeClose: true,
            content: $('#edit_win')
        });
    });
    
    $(".save_btn").click(function(){
		var node = $(this);
		var value =  $("#edit_win .value_inp").val();
		var desc = $("#edit_win .value_desc").val();
		if (value == "") {
			layer.alert("值不能为空");
			return false;
		}
		if (desc == "") {
			layer.alert("修改备注不能为空");
			return false;
		}
		var confId = $("#confId").val();
		var size = $("#edit_win [name=node_chk]:checked").size();
		if (size == 0) {
			layer.alert('请选择要推送的节点信息!')
			return;
		}
		var datas = new Array();
		$("#edit_win [name=node_chk]:checked").each(function(i,o){
			datas.push({"node":$(o).val(),"id":confId,value:value, desc:desc});
		});
		$.ajax({
		    url:"../conf/update",
	        method:'POST',
			contentType: "application/json",  
            dataType: "json", 
			data:JSON.stringify(datas),
			success:function () {
				layer.alert('修改成功!',function(){
					window.location.reload();
				});
	        },
			error:function () {
				layer.alert('修改失败,请重试!')
	        }
		});
		
	});
</script>
</body>
</html>
