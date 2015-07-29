$package('Love.system.role');
Love.system.role = function(){
	var _box = null;
	var _this = {
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
						var code = result.data.code;
						if(code == contants['admin']){
						$("#edit-win").dialog('close');
							Love.alert('警告','超级管理员不能进行该操作!','warning');
						}
					});
				}
			},
  			dataGrid:{
  				title:'角色列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'code',title:'编码',width:200,sortable:true},
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
							}}
						
				]],
				toolbar:[
					{id:'btnadd',text:'添加',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btndelete',text:'删除',btnType:'remove'},
					{id:'btnrun',text:'启停',btnType:'run'},
					{id:'btngrant',text:'分配权限',btnType:'grantAutn',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							_this.authListWin().window('open');
							/*_this.editPwdForm().resetForm();
							_this.editPwdForm().find("input[name='id']").val(selected[0].id);
							_this.editPwdForm().find("input[name='email']").val(selected[0].email);
							_this.editPwdWin().window('open'); */
							
						}
					}}
				]
			}
		},
		init:function(){
			var authGrid = $('#auth-list');
			authGrid.datagrid({ 
				title: '权限列表',
   				url:'authDataList.s',   
    			columns:[[
						{field:'id',checkbox:true},
						{field:'code',title:'编码',width:200,sortable:true},
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
				checkOnSelect:false,
				selectOnCheck:false,
				method: 'post',
				loadMsg: 'Loading in ...',
				idField: 'id',
				onLoadSuccess: function(){
					authGrid.datagrid('unselectAll');
					authGrid.datagrid('uncheckAll');
				},
				onSelect:function(rowIndex, rowData){
					//选择一行
					var rows = authGrid.datagrid('getRows');
					$.each(rows,function(i){
						if(i != rowIndex){
							authGrid.datagrid('uncheckRow',i);
							authGrid.datagrid('unselectRow',i);
						}
					})
					authGrid.datagrid('checkRow',rowIndex);
				},
				toolbar:  [{
					iconCls: 'icon-save',
					text:'保存',
					handler: function(){alert('编辑按钮')}
				},'-',{
					iconCls: 'icon-cancel',
					text:'撤销',
					handler: function(){alert('帮助按钮')}
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
	Love.system.role.init();
});		