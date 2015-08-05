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
            <label class="ui-label">名称:</label> 
            <input name="name" class="easyui-box ui-text" style="width:100px;">
            <label>分组:</label>  
	        <input id="searchGroup" name="groupId" value="">
	        <label>用户:</label>  
	        <input id="searchUser" name="userId" value="">
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
     <!-- setGroup Form -->
     <div id="setGroup-win" class="easyui-dialog" title="分组" data-options="closed:true,modal:true,buttons:'#groupBtn'" style="width:400px;height:200px;">  
     	<form id="setGroupForm" class="ui-form" method="post" action="setGroup.s">  
     		 <input id="groupIds" class="hidden" type="text" name="ids">
     		 <div class="ui-edit">
	     	   <div class="ftitle">设置分组</div>    
	           <div class="fitem">  
	               <label>分组:</label>  
					<input id="selectGroup" name="groupId" value="">
	           </div>  
	         </div>
     	</form>
  	 </div> 
  	 <div id="groupBtn">
		<a href="#" id="groupSaveBtn" class="easyui-linkbutton" onclick="javascript:Love.blog.media.groupSubmit()">保存</a>
		<a href="#" id="groupCloseBtn" class="easyui-linkbutton" onclick="javascript:Love.blog.media.groupClose()">关闭</a>
	</div>
	
	<div id="uploadfile-win"></div>
  	 
	<!-- 查看文件 -->
  	 <div id="viewFile-win" class="easyui-dialog" title="查看文件" data-options="closed:true,modal:true,top:0" style="width:500px;height:400px;"> 
  	 	<div id="showDiv"></div>
  	 </div>
  
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/media.js"></script>
  </body>
</html>
