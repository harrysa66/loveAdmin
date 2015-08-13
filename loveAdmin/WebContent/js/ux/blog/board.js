$package('Love.blog.board');
Love.blog.board = function(){
	var _box = null;
	//var editor = null;
	var um = null;
	var _this = {
		viewContent : function(id){
			$.ajax({
				type : 'POST',
				url : 'view.s',
				data : {id :id},
				success : function(result){
					$('#viewContent-win').window({
						title : '留言内容查看',
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
		viewReply : function(id){
			$.ajax({
				type : 'POST',
				url : 'view.s',
				data : {id :id},
				success : function(result){
					$('#viewReply-win').window({
						title : '回复内容查看',
						modal:true,
						top:0,
						closable:true,
						width:900,
						height:450,
						content:result.data.replyContent
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
							if(selected[0].replyContent == null || selected[0].replyContent == ""){
								_box.handler.edit();
							}else{
								Love.alert('警告','此留言已回复','warning');  
							}
						}
				},
				save:function(){
					//$("#replyContent").val(editor.getData());
					$("#replyContent").val(um.getContent());
					_box.handler.save();
				}
			},
  			dataGrid:{
  				title:'留言板列表',
	   			url:'dataList.s',
	   			columns:[[
						{field:'id',checkbox:true},
						{field:'visitorId',title:'访客',width:100,sortable:true,formatter:function(value,row,index){
							return row.ip;
						}},
						{field:'createTime',title:'创建时间',width:150,sortable:true,
							formatter:function(value,row,index){  
								if(value != null && value != ''){
									var unixTimestamp = new Date(value);  
                         			return unixTimestamp.toLocaleString();
								}
                         	}
                        },
						{field:'userId',title:'用户',width:200,sortable:true,formatter:function(value,row,index){
							return row.nickname;
						}},
						{field:'replyTime',title:'回复时间',width:150,sortable:true,
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
									return "已回复";
								}
								if(value == 'N'){
									return "未回复";
								}
							}},
							{field:'viewContent',title:'查看',width:120,align:'center',formatter:function(value,row,index){
								var html ="<a href='#' onclick='Love.blog.board.viewContent(\""+row.id+"\")'>内容</a>&nbsp;&nbsp;&nbsp;<a href='#' onclick='Love.blog.board.viewReply(\""+row.id+"\")'>回复</a>";
								return html;
							}}
						
				]],
				toolbar:[
					{id:'btnedit',text:'回复',btnType:'edit'},
					{id:'btndelete',text:'删除',btnType:'remove'}
				]
			}
		},
		init:function(){
			//editor = CKEDITOR.replace('replyContentText');
			um = UM.getEditor('myEditor',{
 							toolbar:[
 				            		'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
 				            		'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
 				            		'| justifyleft justifycenter justifyright justifyjustify |',
 				            		'link unlink | horizontal ',
 				            		'| emotion print preview fullscreen', 'drafts', 'formula'
 				        		]
 						});
			_box = new DataGrid(_this.config); 
			_box.init();
			
		}
	}
	return _this;
}();

$(function(){
	Love.blog.board.init();
});		