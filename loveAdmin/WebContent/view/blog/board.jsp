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
            <label class="ui-label">访客:</label> 
            <input name="visitorId" class="easyui-box ui-text" style="width:100px;">
            <label class="ui-label">用户:</label> 
            <input name="userId" class="easyui-box ui-text" style="width:100px;">
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
     <div id="edit-win" class="easyui-dialog" title="回复" data-options="closed:true,modal:true" style="width:1000px;height:450px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">回复信息</div>    
	           <div class="fitem">  
	           		<script id="myEditor" type="text/plain" style="width:940px;height:400px;"></script>
	               <!-- <textarea id="replyContentText" cols="20" rows="2" class="ckeditor" data-options="required:true"></textarea> -->
	               <input id="replyContent" type="hidden" name="replyContent">
	               <!-- <input class="easyui-validatebox" type="text" name="content" data-options="required:true"> -->
	           </div> 
	         </div>
     	</form>
  	 </div> 
  	 
  	 <div id="viewContent-win"></div>
  	 <div id="viewReply-win"></div>
  
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/board.js"></script>
  </body>
</html>
