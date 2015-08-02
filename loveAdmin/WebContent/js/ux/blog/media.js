$package('Love.blog.media');
Love.blog.media = function(){
	var _box = null;
	var selectGroup = null;
	var editIndex = undefined;
	var mediaGrid= $('#data-list');
	var _this = {
		setGroupWin:function(){
			return $("#setGroup-win");
		},
		viewFile:function(id){
			$('#viewFile-win').window('open');
		},
		groupClose:function(){
			$.messager.confirm('确认','确定关闭该窗口?',function(r){  
				    if (r){  
				    	$('#setGroupForm').form().resetForm();
				     	_this.setGroupWin().dialog('close');
				    }
				});
		},
		groupSubmit:function(){
			$('#setGroupForm').form('submit', {    
    			success: function(data){    
        			var data = eval('(' + data + ')');  
        			if(data.success){
        				 _this.setGroupWin().dialog('close');
						$('#setGroupForm').form().resetForm();
						var param = $('#searchForm').serializeObject();
						$('#data-list').datagrid('reload',param);
			 			Love.alert('提示',data.msg,'info');
		        	}else{
		       	   		Love.alert('提示',data.msg,'error');  
		        	}   
    			}    
			});  
		},
		endEditing:function(){
			if (editIndex == undefined){return true}
			if (mediaGrid.datagrid('validateRow', editIndex)){
				mediaGrid.datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		},
		config:{
  			dataGrid:{
  				title:'媒体文件列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'name',title:'名称',width:80,sortable:true,editor:{
							type:'text'
						}},
						{field:'groupId',title:'所属分组',width:80,sortable:true,styler:function(value){
									if(value == '' || value == null){
								 	 return 'color:red;';  
									}
								},formatter:function(value,row,index){
									if(value == '' || value == null){
								 	 	return '未分组';  
									}else{
										return row.groupName;
									}
						}},
						{field:'userId',title:'上传用户',width:80,sortable:true,formatter:function(value,row,index){
									return row.nickname;
						}},
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
							{field:'fileId',title:'查看',width:120,align:'center',styler:function(value){
									if(value == '' || value == null){
								 	 return 'color:red;';  
									}
								},
								formatter:function(value,row,index){
									if(value == '' || value == null){
								 	 	return '无文件';  
									}else{
										var html ="<a href='#' onclick='Love.blog.media.viewFile(\""+value+"\")'>"+row.filename+"</a>";
										return html;
									}
							}}
						
				]],
				toolbar:[
					{id:'btnaddfile',text:'批量上传',btnType:'addFile',iconCls:'icon-add',handler:function(){
						$("#uploadfile-win").window('open');
					}},
					{id:'btnsetname',text:'设置名称',btnType:'setName',iconCls:'icon-edit',handler:function(){
						var rows = mediaGrid.datagrid('getRows');
						for ( var i = 0; i < rows.length; i++) {
							mediaGrid.datagrid('endEdit', i);
						}
						var changes = mediaGrid.datagrid('getChanges');
						var data = JSON.stringify(changes);
						$.ajax({
								type:'POST',
								async:false,
								url:'setName.s',
								data:{params:data},
								success:function(data){
									mediaGrid.datagrid('acceptChanges');
									var param = $('#searchForm').serializeObject();
									$('#data-list').datagrid('reload',param);
			 						Love.alert('提示',data.msg,'info');
								}
							});
					}},
					{id:'btnsetgroup',text:'设置分组',btnType:'setGroup',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						var idArray = new Array();
						for(var i = 0; i < selected.length; i++){
							idArray.push(selected[i].id);
						}
						var ids = idArray.join(",");
						$('#groupIds').val(ids);
						$('#setGroup-win').window('open');
					}},
					{id:'btndelete',text:'删除',btnType:'remove'},
					{id:'btnrun',text:'启停',btnType:'run'}
				]
			}
		},
		init:function(){
			$('#searchGroup').combobox({    
    			url:'selectGroup.s',    
    			valueField:'id',    
    			textField:'name',
    			editable:false
			});
			$('#searchUser').combobox({    
    			url:'selectUser.s',    
    			valueField:'id',    
    			textField:'nickname',
    			editable:false
			});
			selectGroup = $('#selectGroup');
			selectGroup.combobox({    
    			url:'selectGroup.s',    
    			valueField:'id',    
    			textField:'name',
    			editable:false
			});
			
			mediaGrid.datagrid({
				onDblClickCell: function(index,field,value){
					if (editIndex != index){
					if (Love.blog.media.endEditing()){
						mediaGrid.datagrid('selectRow', index).datagrid('beginEdit', index);
						var ed = $(this).datagrid('getEditor', {index:index,field:field});
						$(ed.target).focus();
						editIndex = index;
					} else {
						mediaGrid.datagrid('selectRow', editIndex);
					}
				}
				}
			});
			
			_box = new DataGrid(_this.config); 
			_box.init();
			
		}
	}
	return _this;
}();

$(function(){
	Love.blog.media.init();
});		