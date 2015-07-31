$package('Love.blog.visitor');
Love.blog.visitor = function(){
	var _box = null;
	var viewBlack = null;
	var _this = {
		viewBlack : function(status){
			$('#viewBlack').datagrid({    
    			url:'viewByStatus.s',    
    			rownumbers:true,
    			queryParams:{status:status},
    			singleSelect:true,
    			striped:true,
    			columns:[[    
        			{field:'ip',title:'IP',width:100,sortable:true},
						{field:'ipAddress',title:'IP所在地',width:150,sortable:true},
						{field:'createTime',title:'首次登录时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'loginTime',title:'最后登录时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        },
                        {field:'loginCount',title:'登录次数',width:80,sortable:true,align:'center'}
    			]]    
			});  
			$('#viewBlack-win').window('open');
		},
		viewForbid : function(status){
			$('#viewForbid').datagrid({    
    			url:'viewByStatus.s',    
    			rownumbers:true,
    			queryParams:{status:status},
    			singleSelect:true,
    			striped:true,
    			columns:[[    
        			{field:'ip',title:'IP',width:100,sortable:true},
						{field:'ipAddress',title:'IP所在地',width:150,sortable:true},
						{field:'forbidDay',title:'禁言天数',width:80,sortable:true,align:'center'},
							{field:'forbidStart',title:'开始禁言时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'forbidEnd',title:'结束禁言时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        }
    			]]    
			});  
			$('#viewForbid-win').window('open');
		},
		config:{
			event:{
				edit:function(){
					var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							if(selected[0].status == "DEFAULT"){
								_box.handler.edit();
							}else if(selected[0].status == "FORBID"){
								Love.alert('警告','此访客已禁言','warning');  
							}else if(selected[0].status == "BLACK"){
								Love.alert('警告','此访客已拉黑','warning');  
							}else{
								Love.alert('警告','操作失败','warning'); 
							}
						}
				}
			},
  			dataGrid:{
  				title:'访客列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'ip',title:'IP',width:100,sortable:true},
						{field:'ipAddress',title:'IP所在地',width:150,sortable:true},
						{field:'createTime',title:'首次登录时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'loginTime',title:'最后登录时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        },
                        {field:'loginCount',title:'登录次数',width:80,sortable:true,align:'center'},
						{field:'status',title:'状态',width:50,align:'center',sortable:true,styler:function(value,row,index){
								if(value == 'BLACK'){
								  return 'color:gray;';  
								}
								if(value == 'FORBID'){
								  return 'color:red;';  
								}
							},
							formatter:function(value,row,index){
								if(value == 'DEFAULT'){
									return "正常";
								}
								if(value == 'BLACK'){
									return "拉黑";
								}
								if(value == 'FORBID'){
									return "禁言";
								}
							}},
							{field:'forbidDay',title:'禁言天数',width:80,sortable:true,align:'center'},
							{field:'forbidStart',title:'开始禁言时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'forbidEnd',title:'结束禁言时间',width:150,sortable:true,
							formatter:function(value,row,index){  
                        		if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								} 
                         	}
                        }
						
				]],
				toolbar:[
					{id:'btnedit',text:'禁言',btnType:'edit'},
					{id:'btnoutforbid',text:'解除禁言',btnType:'outforbid',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							if(selected[0].status == 'DEFAULT'){
								Love.alert('警告','此访客状态正常','warning');  
							}else if(selected[0].status == 'BLACK'){
								Love.alert('警告','此访客已拉黑','warning');  
							}else if(selected[0].status == 'FORBID'){
								$.ajax({
								type:'POST',
								async:false,
								url:'outForbid.s',
								data:{id:selected[0].id},
								success:function(result){
									$("#data-list").datagrid('load');
									Love.alert('提示',result.msg,'info');
								},
								error:function(){
									Love.alert('警告','操作失败','warning');  
								}
							});
							}else{
								Love.alert('警告','操作失败','warning');  
							}
						}
					}},
					{id:'btnviewforbid',text:'查看禁言名单',btnType:'viewforbid',iconCls:'icon-search',handler:function(){
						Love.blog.visitor.viewForbid('FORBID');
					}},
					{id:'btninblack',text:'拉黑',btnType:'inblack',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							if(selected[0].status == 'BLACK'){
								Love.alert('警告','此访客已拉黑','warning');  
							}else{
								$.ajax({
								type:'POST',
								async:false,
								url:'inBlack.s',
								data:{id:selected[0].id},
								success:function(result){
									$("#data-list").datagrid('load');
									Love.alert('提示',result.msg,'info');
								},
								error:function(){
									Love.alert('警告','操作失败','warning');  
								}
							});
							}
						}
					}},
					{id:'btnoutblack',text:'移出黑名单',btnType:'outblack',iconCls:'icon-edit',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
							if(selected[0].status == 'DEFAULT'){
								Love.alert('警告','此访客状态正常','warning');  
							}else if(selected[0].status == 'FORBID'){
								Love.alert('警告','此访客禁言','warning');  
							}else if(selected[0].status == 'BLACK'){
								$.ajax({
								type:'POST',
								async:false,
								url:'outBlack.s',
								data:{id:selected[0].id},
								success:function(result){
									$("#data-list").datagrid('load');
									Love.alert('提示',result.msg,'info');
								},
								error:function(){
									Love.alert('警告','操作失败','warning');  
								}
							});
							}else{
								Love.alert('警告','操作失败','warning');  
							}
						}
					}},
					{id:'btnviewblack',text:'查看黑名单',btnType:'viewblack',iconCls:'icon-search',handler:function(){
						Love.blog.visitor.viewBlack('BLACK');
					}}
				]
			}
		},
		init:function(){
			_box = new DataGrid(_this.config); 
			_box.init();
			
		}
	}
	return _this;
}();

$(function(){
	Love.blog.visitor.init();
});		