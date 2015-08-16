<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <%@include file="/view/resource.jsp" %>
  </head>
	<body>
<div class="warp easyui-panel" data-options="border:false">
     	<form id="saveForm" class="ui-form" method="post" action="save.s" enctype="multipart/form-data">  
     		 <input class="hidden" type="text" name="id" value="${daysIndex.id }">
     		 <div class="ui-edit">
	     	   <div class="ftitle">纪念日首页信息</div>    
	           <div class="fitem">  
	               <label>男孩标题:</label>  
	               <input class="easyui-validatebox" type="text" name="boyTitle" data-options="required:true" value="${daysIndex.boyTitle }">
	           </div>  
	           <div id="boyDiv" class="fitem">  
	               <label>男孩头像:</label>  
	               <c:if test="${empty daysIndex.boyId}">
	               <input class="easyui-validatebox" type="file" name="boy">
	               </c:if>
	               <c:if test="${not empty daysIndex.boyId}">
	               <a href="javascript:viewImg('${daysIndex.boyUrl}')">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
	               <a href="javascript:delBoyImg()">删除</a>
	               </c:if>
	           </div>
	           <div class="fitem">  
	               <label>女孩标题:</label>  
	               <input class="easyui-validatebox" type="text" name="girlTitle" data-options="required:true" value="${daysIndex.girlTitle }">
	           </div> 
	           <div id="girlDiv" class="fitem">  
	               <label>女孩头像:</label>  
	               <c:if test="${empty daysIndex.girlId}">
	               <input class="easyui-validatebox" type="file" name="girl">
	               </c:if>
	               <c:if test="${not empty daysIndex.girlId}">
	               <a href="javascript:viewImg('${daysIndex.girlUrl}')">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
	               <a href="javascript:delGirlImg()">删除</a>
	               </c:if>
	           </div>
	           <div class="fitem">  
	               <label>内容一:</label>  
	               <input class="easyui-validatebox" type="text" name="contentOne" data-options="required:true" value="${daysIndex.contentOne }">
	           </div> 
	           <div class="fitem">  
	               <label>内容二:</label>  
	               <input class="easyui-validatebox" type="text" name="contentTwo" data-options="required:true" value="${daysIndex.contentTwo }">
	           </div> 
	           <div class="fitem">  
	               <label>内容三:</label>  
	               <input class="easyui-validatebox" type="text" name="contentThree" data-options="required:true" value="${daysIndex.contentThree }">
	           </div> 
	           <div class="fitem">  
	           <label></label>
	           		<a id="saveBtn" href="#" class="easyui-linkbutton">保存</a>
	           </div> 
	         </div>
     	</form>
  	 </div> 
  	 
  	 <!-- 查看头像 -->
  	 <div id="viewFile-win" class="easyui-dialog" title="查看头像" data-options="closed:true,modal:true,top:0"> 
  	 <img id="viewImg" width="148px" height="148px" alt="" src="" >
  	 </div>
<script type="text/javascript" src="${msUrl}/js/commons/DataGrid.js"></script>
<script type="text/javascript" src="${msUrl}/js/ux/blog/daysIndex.js"></script>
  </body>
</html>
