$package('Love.blog.mediaType');
Love.blog.mediaType = function(){
	var _box = null;
	var _this = {
		config:{
  			dataGrid:{
  				title:'媒体类型列表',
	   			url:'dataList.s',
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
                        {field:'types',title:'类别',width:80,align:'center',sortable:true,formatter:function(value,row,index){
								if(value == 'IMAGE'){
									return "图像";
								}
								if(value == 'VIDEO'){
									return "视频";
								}
								if(value == 'AUDIO'){
									return "音频";
								}
							}},
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
						{field:'isshow',title:'展示',width:80,align:'center',sortable:true,styler:function(value,row,index){
								if(value == 'N'){
								  return 'color:red;';  
								}
							},
							formatter:function(value,row,index){
								if(value == 'Y'){
									return "显示";
								}
								if(value == 'N'){
									return "隐藏";
								}
							}},
							{field:'display',title:'展示顺序',width:80,sortable:true}
						
				]],
				toolbar:[
					{id:'btnadd',text:'添加',btnType:'add'},
					{id:'btnedit',text:'修改',btnType:'edit'},
					{id:'btndelete',text:'删除',btnType:'remove'},
					{id:'btnrun',text:'启停',btnType:'run'},
					{id:'btnshow',text:'显示/隐藏',btnType:'show',iconCls:'icon-run',handler:function(){
						var selected = _box.utils.getCheckedRows();
						if (_box.utils.checkSelect(selected)){
							$.messager.confirm('确认','确定操作该记录?',function(r){  
					    		if (r){
					    			var idKey = 'id'; //主键名称
					    			var  data = $("input[name='"+idKey+"']", $("#listForm") ).fieldSerialize(); //序列化字段
					   				Love.runForm('show.s',data,function(result){
										var param = $("#searchForm").serializeObject();
										$("#data-list").datagrid('reload',param);
										Love.alert('提示',result.msg,'info');
									});
					    		}  
							});
						}
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
	Love.blog.mediaType.init();
});		