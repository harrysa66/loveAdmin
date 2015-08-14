<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>LOVE博客后台管理系统</title>
    <%@include file="/view/resource.jsp" %>
    <link rel="stylesheet" type="text/css" href="${msUrl}/css/main.css">
    <script type="text/javascript" src="${msUrl}/js/ux/main/main.js"></script>
  </head>
  <body class="easyui-layout">
  	
 	<div class="ui-header" data-options="region:'north',split:true,border:false" style="height:40px;overflow: hidden;">
 	<h1>LOVE博客后台管理系统</h1>
 	<div  class="ui-login">
 		<div class="ui-login-info">
	 		欢迎 <span class="orange">${user.nickname}</span> [<span class="orange">${userIp}</span>]登录系统 
	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 		<a class="modify-pwd-btn"  href="javascript:void(0);">修改密码</a> |
 			<a class="logout-btn" href="j_spring_security_logout">退出</a>
 		</div>
 	</div>
 	</div>
	<!-- 树形菜单 -->
	<div data-options="region:'west',split:true,title:'导航'" style="width:200px;">
		<div id="tree-box" class="easyui-accordion" data-options="fit:true,border:false">
			<c:forEach var="item" items="${menuList}">
			<div title="${item.text}">
				<c:forEach var="node" items="${item.children}">
				<a class="menu-item" href="${msUrl}${node.url}">${node.text}</a>
				</c:forEach>
			</div>
			</c:forEach>
		</div>
	</div>
	<div data-options="region:'south',split:true,border:false" style="height: 30px;overflow:hidden;">
		<div class="panel-header" style="border: none;text-align: center;" >CopyRight &copy; 2015 harrysa66 版权所有. &nbsp;&nbsp;官方网址:www.hebingqing.cn  &nbsp;&nbsp;津ICP备15003227号</div>
	</div>
	<!-- 中间内容页面 -->
	<div data-options="region:'center'" >
		<div class="easyui-tabs" id="tab-box" data-options="fit:true,border:false">
			<div title="欢迎" style="padding:20px;overflow:hidden;"> 
				<iframe src="${webUrl}" width="1130" height="520" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
			</div>
		</div>	
	</div>
	
	<!-- 右键菜单 -->
	<div id="tabs-menu" class="easyui-menu" style="width:150px;">
        <div id="closecur" class="context-menu-item" data-options="iconCls:'icon-close'">关闭</div>
    	<div id="closeall" class="context-menu-item" data-options="iconCls:'icon-close'">关闭全部</div>
    	<div id="closeother" class="context-menu-item" data-options="iconCls:'icon-close'">关闭其他</div>
    	<div class="menu-sep" class="context-menu-item"></div>
    	<div id="closeright" class="context-menu-item" data-options="iconCls:'icon-close'">关闭右侧标签页</div>
    	<div id="closeleft" class="context-menu-item" data-options="iconCls:'icon-close'">关闭左侧标签页</div> 
	</div>

	<!--  modify password start -->
	<div id="modify-pwd-win"  class="easyui-dialog" buttons="#editPwdbtn" title="修改用户密码" data-options="closed:true,modal:true" style="width:350px;height:200px;">
		<form id="pwdForm" action="modifyPassword.s" class="ui-form" method="post">
     		 <input class="hidden" name="id" value="${user.id}">
     		 <input class="hidden" name="username" value="${user.username}">
     		 <div class="ui-edit">
	           <div class="fitem">  
	              <label>旧密码:</label>  
	              <input id="oldPwd" name="oldPwd" type="password" class="easyui-validatebox"  data-options="required:true"/>
	           </div>
	            <div class="fitem">  
	               <label>新密码:</label>  
	               <input id="newPwd" name="newPwd" type="password" class="easyui-validatebox" data-options="required:true" />
	           </div> 
	           <div class="fitem">  
	               <label>重复密码:</label>  
	              <input id="rpwd" name="rpwd" type="password" class="easyui-validatebox"   required="required" validType="equals['#newPwd']" />
	           </div> 
	         </div>
     	 </form>
     	 <div id="editPwdbtn" class="dialog-button" >  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-submit">提交</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" id="btn-pwd-close">关闭</a>  
         </div>
	</div>
	<!-- modify password end  -->
  </body>
</html>
