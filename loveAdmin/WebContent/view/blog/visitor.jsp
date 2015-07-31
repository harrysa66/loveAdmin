<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
<div class="warp easyui-panel" data-options="border:false">
	<!-- Search panel start -->
 	 <div class="easyui-panel ui-search-panel" title="查询项" data-options="striped: true,collapsible:true,iconCls:'icon-search'">  
 	 <form id="searchForm">
 	 	<p class="ui-fields">
            <label class="ui-label">IP:</label> 
            <input name="ip" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">IP所在地:</label> 
            <input name="ipAddress" class="easyui-box ui-text" style="width:100px;">
            <label>状态:</label>  
	        <select class="easyui-combobox" name="status" style="width:100px;" editable="false" panelHeight="100">
	        	<option value="" selected="selected">全部</option>
	        	<option value="DEFAULT">正常</option>
	        	<option value="BLACK">拉黑</option>
                <option value="FORBID">禁言</option>
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
     <div id="edit-win" class="easyui-dialog" title="禁言" data-options="closed:true,iconCls:'icon-save',modal:true" style="width:300px;height:200px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">禁言设置</div>    
	           <div class="fitem">  
	           		<label>禁言天数:</label> 
	               <input type="text" class="easyui-numberbox" name="forbidDay" value="1" data-options="min:1"></input>
	           </div> 
	         </div>
     	</form>
  	 </div> 
  	 
  	 <!-- 查看黑名单 -->
  	 <div id="viewBlack-win" class="easyui-dialog" title="查看黑名单" data-options="closed:true,modal:true,top:0" style="width:900px;height:520px;"> 
  	 <table id="viewBlack"></table>
  	 </div>
  	 
  	 <!-- 查看禁言名单 -->
  	 <div id="viewForbid-win" class="easyui-dialog" title="查看禁言名单" data-options="closed:true,modal:true,top:0" style="width:900px;height:520px;"> 
  	 <table id="viewForbid"></table>
  	 </div>
  	 
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/visitor.js"></script>
  </body>
</html>
