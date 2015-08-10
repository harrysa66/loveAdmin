<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
<div class="warp easyui-panel" data-options="border:false">
     	<form id="saveForm" class="ui-form" method="post" action="save.s">  
     		 <input class="hidden" type="text" name="id" value="${indexPage.id }">
     		 <div class="ui-edit">
	     	   <div class="ftitle">首页信息</div>    
	           <div class="fitem">  
	               <label>标题:</label>  
	               <input class="easyui-validatebox" type="text" name="title" data-options="required:true" value="${indexPage.title }">
	           </div>  
	           <div class="fitem">  
	               <label>副标题:</label>  
	               <input class="easyui-validatebox" type="text" name="subtitle" data-options="required:true" value="${indexPage.subtitle }">
	           </div> 
	           <div class="fitem">  
	           <label></label>
	           		<a id="saveBtn" href="#" class="easyui-linkbutton">保存</a>
	           </div> 
	         </div>
     	</form>
  	 </div> 
<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/indexPage.js"></script>
  </body>
</html>
