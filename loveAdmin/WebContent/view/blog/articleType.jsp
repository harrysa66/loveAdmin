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
            <label class="ui-label">类型编号:</label> 
            <input name="code" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">类型名称:</label> 
            <input name="name" class="easyui-box ui-text" style="width:100px;">
            <label>状态:</label>  
	        <select class="easyui-combobox" name="isvalid" style="width:100px;" editable="false" panelHeight="80">
	        	<option value="" selected="selected">全部</option>
	        	<option value="Y">可用</option>
                <option value="N">禁用</option>
            </select>
            <label>展示:</label> 
            <select class="easyui-combobox" name="isshow" style="width:100px;" editable="false" panelHeight="80">
	        	<option value="" selected="selected">全部</option>
	        	<option value="Y">显示</option>
                <option value="N">隐藏</option>
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
     <div id="edit-win" class="easyui-dialog" title="修改" data-options="closed:true,modal:true" style="width:400px;height:250px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">文章类型信息</div>    
	           <div class="fitem">  
	               <label>类型编号:</label>  
	               <input class="easyui-validatebox" type="text" name="code" data-options="required:true">
	           </div>  
	           <div class="fitem">  
	               <label>类型名称:</label>  
	               <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
	           </div> 
	           <div class="fitem">  
	               <label>展示顺序:</label>  
	               <input class="easyui-numberbox" type="text" name="display" data-options="min:1,required:true">
	           </div> 
	         </div>
     	</form>
  	 </div> 
  
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/articleType.js"></script>
  </body>
</html>
