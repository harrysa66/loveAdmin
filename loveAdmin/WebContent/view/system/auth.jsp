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
     <div id="edit-win" class="easyui-dialog" title="修改" data-options="closed:true,modal:true" style="width:400px;height:410px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">权限信息</div>    
	           <div class="fitem">  
	               <label>权限名称:</label>  
	               <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
	           </div>  
	            <div class="fitem" style="">  
	               <label>菜单列表:</label>
	               <span id="menuFullName"></span>
	               <div style="border: 1px solid #A4BED4; width:230px;height:200px;margin-left: 105px ;overflow: auto; ">  
	               	<ul id="menu-tree"  >
	               	</ul>
	               </div>
	               <!-- 
	               <select id="menu-tree" name="menuIds" class="easyui-combotree" data-options="url:'${msUrl}/sysMenu/getMenuTree.do'" multiple style="width:180px;"></select>
	                -->
	           </div>
	         </div>
     	</form>
  	 </div> 
  
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/system/auth.js"></script>
  </body>
</html>
