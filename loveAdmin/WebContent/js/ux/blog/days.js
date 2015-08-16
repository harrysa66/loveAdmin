$package('Love.blog.days');
Love.blog.days = function(){
	var _box = null;
	var _this = {
		config:{
			event:{
				edit:function(){
					_box.handler.edit(function(result){
						var memorialDate = result.data.fmtDate;
						$('#memorialDate').datetimebox('setValue', memorialDate);
					});
				}
			},
  			dataGrid:{
  				title:'纪念日列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'memorialTitle',title:'标题',width:200,sortable:true},
						{field:'memorialDate',title:'纪念日时间',width:150,sortable:true,
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
				]]
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
	Love.blog.days.init();
});		