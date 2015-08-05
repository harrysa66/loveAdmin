<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
<div class="warp easyui-panel" data-options="border:false">
	<!-- Search panel start -->
 	 <div class="easyui-panel ui-search-panel" title="查询项" data-options="striped: true,collapsible:true,iconCls:'icon-searchfile'">  
 	 <form id="searchForm">
 	 	<p class="ui-fields">
            <label class="ui-label">用户名:</label> 
            <input name="username" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">昵称:</label> 
            <input name="nickname" class="easyui-box ui-text" style="width:100px;">
            <label>状态:</label>  
	        <select class="easyui-combobox" name="isvalid" style="width:100px;" editable="false" panelHeight="80">
	        	<option value="" selected="selected">全部</option>
	        	<option value="Y">可用</option>
                <option value="N">禁用</option>
            </select>
        </p>  
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div> 
     <!--  Search panel end -->
     
     <!-- DataList  -->
     <form id="listForm" method="post">
     <table id="data-list"></table>
	 </form> 
     <!-- Edit Form -->
     <div id="edit-win" class="easyui-dialog" title="修改" data-options="closed:true,modal:true" style="width:400px;height:210px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">用户信息</div>    
	           <div class="fitem">  
	               <label>用户名:</label>  
	               <input class="easyui-validatebox" type="text" name="username" data-options="required:true">
	           </div>  
	           <div class="fitem">  
	               <label>昵称:</label>  
	               <input class="easyui-validatebox" type="text" name="nickname" data-options="required:true">
	           </div> 
	         </div>
     	</form>
  	 </div> 
  	 
  	 <!-- 分配角色 -->
  	 <div id="roleList-win" class="easyui-dialog" title="分配角色" data-options="closed:true,modal:true,top:0" style="width:900px;height:520px;"> 
  	 	<div class="easyui-panel ui-search-panel" title="查询项" data-options="striped: true,collapsible:true,iconCls:'icon-searchfile'">  
 	 <form id="roleSearchForm">
 	 	<p class="ui-fields">
            <label class="ui-label">角色编号:</label> 
            <input name="code" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">角色名称:</label> 
            <input name="name" class="easyui-box ui-text" style="width:100px;">
        </p>  
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div>
  	 	<form id="roleListForm" method="post">
     		<table id="role-list"></table>
	 	</form>
  	 </div>
  	 
  	 <!-- 分配权限 -->
  	 <div id="authList-win" class="easyui-dialog" title="分配权限" data-options="closed:true,modal:true,top:0" style="width:900px;height:520px;"> 
  	 	<div class="easyui-panel ui-search-panel" title="查询项" data-options="striped: true,collapsible:true,iconCls:'icon-searchfile'">  
 	 <form id="authSearchForm">
 	 	<p class="ui-fields">
            <label class="ui-label">权限编号:</label> 
            <input name="code" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">权限名称:</label> 
            <input name="name" class="easyui-box ui-text" style="width:100px;">
            <label>菜单类型:</label>  
	        <select class="easyui-combobox" name="authType" style="width:100px;" editable="false" panelHeight="80">
	        	<option value="" selected="selected">全部</option>
	        	<option value="MENU_">菜单权限</option>
                <option value="BTN_">按钮权限</option>
            </select>
            <label>所属菜单:</label>  
            <input id="combMenu" name="menuCode" value=""> 
        </p>  
        <a href="#" id="btn-search" class="easyui-linkbutton" iconCls="icon-search">查询</a>
      </form>  
     </div>
  	 	<form id="authListForm" method="post">
     		<table id="auth-list"></table>
	 	</form>
  	 </div>
  	 
  	 <!-- 查看角色 -->
  	 <div id="viewRole-win" class="easyui-dialog" title="查看角色" data-options="closed:true,modal:true,top:0" style="width:900px;height:520px;"> 
  	 <table id="viewRole"></table>
  	 </div>
  	 
  	 <!-- 查看权限 -->
  	 <div id="viewAuth-win" class="easyui-dialog" title="查看权限" data-options="closed:true,modal:true,top:0" style="width:900px;height:520px;"> 
  	 <table id="viewAuth"></table>
  	 </div>
  
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/system/user.js"></script>
  </body>
</html>
