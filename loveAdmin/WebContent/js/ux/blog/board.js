$package('Love.blog.board');
Love.blog.board = function(){
	var _box = null;
	var editor = null;
	var _this = {
		viewContent : function(id){
			$.ajax({
				type : 'POST',
				url : 'view.s',
				data : {id :id},
				success : function(result){
					$('#viewContent-win').window({
						title : '文章内容查看',
						modal:true,
						top:0,
						closable:true,
						width:900,
						height:450,
						content:result.data.content
					}).window('open');
				},
				error : function(){
					Love.alert('提示','操作失败','info');
				}
			});
		},
		config:{
			event:{
				edit:function(){
					var selected = _box.utils.getCheckedRows();
						if ( _box.utils.checkSelectOne(selected)){
								editor.setData(selected[0].content);
							_box.handler.edit();
						}
				},
				save:function(){
					$("#content").val(editor.getData());
					_box.handler.save();
				}
			},
  			dataGrid:{
  				title:'留言板列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'visitorId',title:'访客',width:100,sortable:true},
						{field:'userId',title:'用户',width:200,sortable:true},
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
							{field:'viewContent',title:'查看',width:120,align:'center',formatter:function(value,row,index){
								var html ="<a href='#' onclick='Love.blog.article.viewContent(\""+row.id+"\")'>内容</a>";
								return html;
							}}
						
				]]
			}
		},
		init:function(){
			editor = CKEDITOR.replace('articleContent');
			_box = new DataGrid(_this.config); 
			_box.init();
			
		}
	}
	return _this;
}();

$(function(){
	Love.blog.board.init();
});		