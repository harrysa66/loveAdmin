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
            <label>类型:</label>  
	        <input id="searchType" name="typeId" value="">
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
     <!-- Edit Form -->
     <div id="edit-win" class="easyui-dialog" title="修改" data-options="closed:true,modal:true" style="width:400px;height:210px;">  
     	<form id="editForm" class="ui-form" method="post">  
     		 <input class="hidden" type="text" name="id">
     		 <div class="ui-edit">
	     	   <div class="ftitle">媒体组信息</div>    
	           <div class="fitem">  
	               <label>名称:</label>  
	               <input class="easyui-validatebox" type="text" name="name" data-options="required:true">
	           </div> 
	           <div class="fitem">  
	               <label>类型:</label>  
					<input id="selectType" name="typeId" value="">
	           </div>  
	         </div>
     	</form>
  	 </div> 
  	 
  	 <!-- cover Form -->
     <div id="cover-win" class="easyui-dialog" title="设置封面" data-options="closed:true,modal:true,top:0,buttons:'#fileBtn'" style="width:400px;height:150px;">  
     	<form id="coverForm" class="ui-form" method="post" enctype="multipart/form-data" action="coverUpload.s">  
     		 <input id="groupId" class="hidden" type="text" name="groupId">
     		 <div class="ui-edit">
	           <div class="fitem">  
	               <label>封面:</label>  
	               <input class="easyui-validatebox" type="file" name="groupCover" data-options="required:true">
	           </div> 
	         </div>
     	</form>
  	 </div> 
  	 <div id="fileBtn">
		<a href="#" id="fileSaveBtn" class="easyui-linkbutton" onclick="javascript:Love.blog.mediaGroup.fileSubmit()">保存</a>
		<a href="#" id="fileCloseBtn" class="easyui-linkbutton" onclick="javascript:Love.blog.mediaGroup.fileClose()">关闭</a>
	</div>
	
	<!-- 查看封面 -->
  	 <div id="viewFile-win" class="easyui-dialog" title="查看封面" data-options="closed:true,modal:true,top:0"> 
  	 <img id="coverImg" alt="" src="" >
  	 </div>
  
</div>

<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/mediaGroup.js"></script>
  </body>
</html>
