$package('Love.system.user');
Love.system.user = function(){
	var _box = null;
	var roleGrid = null;
	var authGrid = null;
	var loginGrid = $('#login-list');
	var _this = {
		viewRole:function(id){
			$('#viewRole').datagrid({    
    			url:'viewRole.s',    
    			rownumbers:true,
    			queryParams:{userId:id},
    			singleSelect:true,
    			striped:true,
    			columns:[[    
        			{field:'code',title:'编号',width:200,sortable:true},
					{field:'name',title:'名称',width:80,sortable:true},
					{field:'createTime',title:'创建时间',width:150,sortable:true,
						formatter:function(value,row,index){  
							if(value != null && value != ''){
								var unixTimestamp = new Date(value);  
                         		return unixTimestamp.toLocaleString();
							}
                         }
                    },
					{field:'modifyTime',title:'修改时间',width:150,sortable:true,
						formatter:function(value,row,index){  
                        	if(value != null && value != ''){
								var unixTimestamp = new Date(value);  
                         		return unixTimestamp.toLocaleString();
							} 
                         }
                    } 
    			]]    
			});  
			$('#viewRole-win').window('open');
		},
		viewAuth:function(id){
			$('#viewAuth').datagrid({    
    			url:'viewAuth.s',    
    			rownumbers:true,
    			queryParams:{userId:id},
    			singleSelect:true,
    			striped:true,
    			columns:[[    
        			{field:'code',title:'编号',width:200,sortable:true},
					{field:'name',title:'名称',width:80,sortable:true},
					{field:'createTime',title:'创建时间',width:150,sortable:true,
						formatter:function(value,row,index){  
							if(value != null && value != ''){
								var unixTimestamp = new Date(value);  
                         		return unixTimestamp.toLocaleString();
							}
                         }
                    },
					{field:'modifyTime',title:'修改时间',width:150,sortable:true,
						formatter:function(value,row,index){  
                        	if(value != null && value != ''){
								var unixTimestamp = new Date(value);  
                         		return unixTimestamp.toLocaleString();
							} 
                         }
                    },
					{field:'fullName',title:'所属菜单',width:200,sortable:true}  
    			]]    
			});  
			$('#viewAuth-win').window('open');
		},
		viewLogin : function(username){
				$('#beginLogin').datebox('setValue', '');
				$('#endLogin').datebox('setValue', '');
				$('#loginSearchForm').form().resetForm();
				var param = $('#loginSearchForm').serializeObject();
				loginGrid.datagrid({   
				title: '登录列表',
    			url:'viewLogin.s?username='+username,   
   				iconCls:'icon-data',    
   				queryParams : param,
    			rownumbers:true,
    			singleSelect:true,
    			striped:true,
    			nowrap: true,
				autoRowHeight: false,
				height:400,
				collapsible:true,
				remoteSort: false,
				pagination:true,
				method: 'post',
				loadMsg: 'Loading in ...',
				idField: 'id',
    			columns:[[    
					{field:'loginTime',title:'登录时间',width:150,sortable:true,
						formatter:function(value,row,index){  
							if(value != null && value != ''){
								var unixTimestamp = new Date(value);  
                         		return unixTimestamp.toLocaleString();
							}
                         }
                    },
					{field:'ip',title:'登录IP',width:100,sortable:true},  
					{field:'ipAddress',title:'登录地址',width:200,sortable:true}  
    			]]    
			});  
			$('#loginList-win').window('open');
		},
		roleListWin:function(){
			return $("#roleList-win");
		},
		roleListForm:function(){
			return $("#roleListForm");
		},
		authListWin:function(){
			return $("#authList-win");
		},
		authListForm:function(){
			return $("#authListForm");
		},
		config:{
			event:{
				edit:function(){
					_box.handler.edit(function(result){
						var username = result.data.username;
						if(username == contants['adminUser']){
						$("#edit-win").dialog('close');
							Love.alert('警告','超级管理员不能进行该操作!','warning');
						}
					});
				}
			},
  			dataGrid:{
  				title:'用户列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'username',title:'用户名',width:200,sortable:true},
						{field:'nickname',title:'昵称',width:80,sortable:true},
						{field:'createTime',title:'创建时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'modifyTime',title:'修改时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        },
						{field:'isvalid',title:'状态',width:80,align:'center',sortable:true,styler:function(value,row,index){
								if(value == 'N'){
								  return 'color:red;';  
								}
							},
							formatter:function(value,row,index){
								if(value == 'Y'){
									return "可用";
								}
								if(value == 'N'){
									return "禁用";
								}
							}},
							{field:'loginCount',title:'登录次数',width:80,sortable:true},
							{field:'viewLogin',title:'登录情况',width:120,align:'center',formatter:function(value,row,index){
								var html ="<a href='#' onclick='Love.system.user.viewLogin(\""+row.username+"\")'>查看</a>";
								return html;
							}},
							{field:'viewRole_Auth',title:'查看',width:120,align:'center',formatter:function(value,row,index){
								var html ="<a href='#' onclick='Love.system.user.viewRole(\""+row.id+"\")'>角色</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick='Love.system.user.viewAuth(\""+row.id+"\")'>权限</a>";
								return html;
							}}
						
				]],
				toolbar:[
					{id:'btnadd',text:'添加',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btndelete',text:'删除',btnType:'remove'},
					{id:'btnrun',text:'启停',btnType:'run'},
					{id:'btngrantRole',text:'分配角色',btnType:'grantRole',iconCls:'icon-setting',handler:function(){
						$('#roleSearchForm').form().resetForm();
						var param = $('#roleSearchForm').serializeObject();
						roleGrid.datagrid('load',param);
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							var username = selected[0].username;
							if(username == contants['adminUser']){
								Love.alert('警告','超级管理员不能进行该操作!','warning');
							}else{
							checkedItems.splice(0,checkedItems.length);
							roleGrid.datagrid('clearChecked');
							roleGrid.datagrid('clearSelections');
							$.ajax({
								type:'POST',
								async:false,
								url:'getRoleIdsByUser.s',
								data:{id:selected[0].id},
								success:function(data){
									for(var i = 0; i < data.length; i++){
										checkedItems.push(data[i]);
									}
								}
							});
							checkboxUtil.ischeckItem($('#role-list')[0]);
							_this.roleListWin().window('open');
							}
						}
					}},
					{id:'btngrantAuth',text:'分配权限',btnType:'grantAutn',iconCls:'icon-setting',handler:function(){
						$('#authSearchForm').form().resetForm();
						$('#combMenu').combotree('clear');
						var param = $('#authSearchForm').serializeObject();
						authGrid.datagrid('load',param);
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							var username = selected[0].username;
							if(username == contants['adminUser']){
								Love.alert('警告','超级管理员不能进行该操作!','warning');
							}else{
							checkedItems.splice(0,checkedItems.length);
							authGrid.datagrid('clearChecked');
							authGrid.datagrid('clearSelections');
							$.ajax({
								type:'POST',
								async:false,
								url:'getAuthIdsByUser.s',
								data:{id:selected[0].id},
								success:function(data){
									for(var i = 0; i < data.length; i++){
										checkedItems.push(data[i]);
									}
								}
							});
							checkboxUtil.ischeckItem($('#auth-list')[0]);
							_this.authListWin().window('open');
							}
						}
					}},
					{id:'btnresetPassword',text:'重置密码',btnType:'resetPassword',iconCls:'icon-reset',handler:function(){
						var records =  _box.utils.getCheckedRows();
							if (_box.utils.checkSelect(records)){
								$.messager.confirm('确认','确定重置密码?',function(r){  
					    			if (r){
					    				var idKey = 'id'; //主键名称
					    				var  datas = $("input[name='"+idKey+"']", $("#listForm").list ).fieldSerialize(); //序列化字段
					   					$.ajax({
											type:'POST',
											url:'resetPassword.s',
											data:datas,
											success:function(data){
												Love.alert('提示',data.msg,'info');
											}
										});
					    }  
					});
				}
					}}
				]
			}
		},
		init:function(){
			//初始化角色信息
			roleGrid = $('#role-list');
			roleGrid.datagrid({ 
				title: '角色列表',
   				url:'roleDataList.s',   
   				iconCls:'icon-data',
   				view:fileview,
    			columns:[[
						{field:'id',checkbox:true},
						{field:'code',title:'编号',width:200,sortable:true},
						{field:'name',title:'名称',width:80,sortable:true},
						{field:'createTime',title:'创建时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'modifyTime',title:'修改时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        }
				]],
				height: 400,
				nowrap: true,
				autoRowHeight: false,
				striped: true,
				collapsible:true,
				remoteSort: false,
				pagination:true,
				rownumbers:true,
				singleSelect:false,
				checkOnSelect:true,
				selectOnCheck:true,
				method: 'post',
				loadMsg: 'Loading in ...',
				idField: 'id',
				onCheckAll:function(rows){
					checkboxUtil.addcheckItem(rows);
				}, 
				onCheck:function(rowIndex,rowData){
					var rows = [rowData];
					checkboxUtil.addcheckItem(rows);
				},
				onUncheckAll:function(rows){
					checkboxUtil.removeAllItem(rows);
				},
				onUncheck:function(rowIndex,rowData){
					checkboxUtil.removeSingleItem(rowIndex,rowData);
				},
				toolbar:  [{
					iconCls: 'icon-save',
					text:'保存',
					handler: function(){
						var selected = _box.utils.getCheckedRows();
						var ids = checkedItems.join(',');
						$.ajax({
							type : 'POST',
							url : 'grantRole.s',
							data : {userId :selected[0].id,ids : ids},
							success : function(data){
								_this.roleListWin().window('close');
								Love.alert('提示',data.msg,'info');
							},
							error : function(){
								Love.alert('提示','操作失败','info');
							}
						});
					}
				},'-',{
					iconCls: 'icon-clear',
					text:'清空',
					handler: function(){
						roleGrid.datagrid('uncheckAll');
						checkedItems.splice(0,checkedItems.length);
					}
				}]
			});  
			var roleSearchForm = $('#roleSearchForm');
			roleSearchForm.find("#btn-search").click(function(callback){
				var param = roleSearchForm.serializeObject();
				roleGrid.datagrid('load',param);
			});
			
			
			//初始化权限信息
			authGrid = $('#auth-list');
			authGrid.datagrid({ 
				title: '权限列表',
   				url:'authDataList.s',   
   				iconCls:'icon-data',
   				view:fileview,
    			columns:[[
						{field:'id',checkbox:true},
						{field:'code',title:'编号',width:200,sortable:true},
						{field:'name',title:'名称',width:80,sortable:true},
						{field:'createTime',title:'创建时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'modifyTime',title:'修改时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        },
						{field:'fullName',title:'所属菜单',width:200,sortable:true}
						
				]],
				height: 400,
				nowrap: true,
				autoRowHeight: false,
				striped: true,
				collapsible:true,
				remoteSort: false,
				pagination:true,
				rownumbers:true,
				singleSelect:false,
				checkOnSelect:true,
				selectOnCheck:true,
				method: 'post',
				loadMsg: 'Loading in ...',
				idField: 'id',
				/*onSelect:function(rowIndex, rowData){
					//选择一行
					authGrid.datagrid('checkRow',rowIndex);
				},
				onUnselect:function(rowIndex, rowData){
					authGrid.datagrid('uncheckRow',rowIndex);
				},*/
				onCheckAll:function(rows){
					checkboxUtil.addcheckItem(rows);
				}, 
				onCheck:function(rowIndex,rowData){
					var rows = [rowData];
					checkboxUtil.addcheckItem(rows);
				},
				onUncheckAll:function(rows){
					checkboxUtil.removeAllItem(rows);
				},
				onUncheck:function(rowIndex,rowData){
					checkboxUtil.removeSingleItem(rowIndex,rowData)
				},
				toolbar:  [{
					iconCls: 'icon-save',
					text:'保存',
					handler: function(){
						var selected = _box.utils.getCheckedRows();
						var ids = checkedItems.join(',');
						$.ajax({
							type : 'POST',
							url : 'grantAuth.s',
							data : {userId :selected[0].id,ids : ids},
							success : function(data){
								_this.authListWin().window('close');
								Love.alert('提示',data.msg,'info');
							},
							error : function(){
								Love.alert('提示','操作失败','info');
							}
						});
					}
				},'-',{
					iconCls: 'icon-clear',
					text:'清空',
					handler: function(){
						authGrid.datagrid('uncheckAll');
						checkedItems.splice(0,checkedItems.length);
					}
				}]
			});  
			var authSearchForm = $('#authSearchForm');
			authSearchForm.find("#btn-search").click(function(callback){
				var param = authSearchForm.serializeObject();
				authGrid.datagrid('load',param);
			});
			
			//查看登录情况
			var loginSearchForm = $('#loginSearchForm');
			loginSearchForm.find("#btn-search").click(function(callback){
				var param = loginSearchForm.serializeObject();
				loginGrid.datagrid('load',param);
			});
			
			$('#combMenu').combotree({    
    			url:urls['msUrl']+'/system/menu/getMenuTreeNoBtn.s',
    			checkbox:false
			});  
			
			
			_box = new DataGrid(_this.config); 
			_box.init();
			
		}
	}
	return _this;
}();

$(function(){
	Love.system.user.init();
});		