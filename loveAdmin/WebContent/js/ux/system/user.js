$package('Love.system.user');
Love.system.user = function(){
	var _box = null;
	var roleGrid = null;
	var authGrid = null;
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
					{id:'btngrantRole',text:'分配角色',btnType:'grantRole',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							var username = selected[0].username;
							if(username == contants['adminUser']){
								Love.alert('警告','超级管理员不能进行该操作!','warning');
							}else{
							roleGrid.datagrid('load');
							checkedItems.splice(0,checkedItems.length);
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
					{id:'btngrantAuth',text:'分配权限',btnType:'grantAutn',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							var username = selected[0].username;
							if(username == contants['adminUser']){
								Love.alert('警告','超级管理员不能进行该操作!','warning');
							}else{
							authGrid.datagrid('load');
							checkedItems.splice(0,checkedItems.length);
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
					checkboxUtil.addcheckItem(rows)
				}, 
				onCheck:function(rowIndex,rowData){
					checkboxUtil.addcheckItem(rowData)
				},
				onUncheckAll:function(rows){
					checkboxUtil.removeAllItem(rows)
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
					iconCls: 'icon-cancel',
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
				roleGrid.datagrid('reload',param);
			});
			
			
			//初始化权限信息
			authGrid = $('#auth-list');
			authGrid.datagrid({ 
				title: '权限列表',
   				url:'authDataList.s',   
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
					checkboxUtil.addcheckItem(rows)
				}, 
				onCheck:function(rowIndex,rowData){
					checkboxUtil.addcheckItem(rowData)
				},
				onUncheckAll:function(rows){
					checkboxUtil.removeAllItem(rows)
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
					iconCls: 'icon-cancel',
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
				authGrid.datagrid('reload',param);
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