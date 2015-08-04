$package('Love.blog.media');
Love.blog.media = function(){
	var _box = null;
	var selectGroup = null;
	var editIndex = undefined;
	var mediaGrid= $('#data-list');
	var showDiv = $('#showDiv');
	var _this = {
		setGroupWin:function(){
			return $("#setGroup-win");
		},
		viewFile:function(id){
			showDiv.empty();
			$.ajax({
				type:'POST',
				async:false,
				url:'getFileUrl.s',
				data:{id:id},
				success:function(result){
					if(result.fileType == 'image'){
						var img = $('<img/>');//创建一个img
    					img.attr("src",result.data.url);//设置图片
    					img.appendTo(showDiv);//设置图片
    					if(result.width != '' && result.height != ''){
							$('#viewFile-win').window({
								width:result.width+30,    
  								height:result.height+40,
  								onClose:function(){showDiv.empty();}
							}).window('open');
						}
					}else if(result.fileType.indexOf('audio') >= 0){
						var audio = $('<audio>');
						var audioEnd = $('</audio>');
						var source = $('<source>');
    					audio.attr("autoplay",'autoplay');
    					audio.attr("loop",'loop');
    					audio.attr("controls",'controls');
    					source.attr("src",result.data.url);
    					source.attr("type",result.data.contentType);
    					audio.appendTo(showDiv);
    					source.appendTo(audio);
    					audio.appendTo(audioEnd);
						$('#viewFile-win').window({
							width:result.width,    
  							height:result.height,
  							onClose:function(){showDiv.empty();}
						}).window('open');
					}else if(result.fileType.indexOf('video') >= 0 || result.fileType.indexOf('/ogg') >=0){
						var video = $('<video>');
						var videoEnd = $('</video>');
						var source = $('<source>');
						var content = $('浏览器不支持该视频');
    					video.attr("autoplay",'autoplay');
    					video.attr("loop",'loop');
    					video.attr("controls",'controls');
    					video.attr("width",result.width);
    					video.attr("height",result.height);
    					source.attr("src",result.data.url);
    					source.attr("type",result.data.contentType);
    					video.appendTo(showDiv);
    					source.appendTo(video);
    					content.appendTo(video);
    					video.appendTo(videoEnd);
						$('#viewFile-win').window({
							width:result.width+200,    
  							height:result.height+100,
  							onClose:function(){showDiv.empty();}
						}).window('open');
					}else{
						$('#viewFile-win').window({
							width:600,    
  							height:400,
  							onClose:function(){showDiv.empty();}
						}).window('open');
					}
				}
			});
			$('#viewFile-win').window('open');
		},
		GetFrameWindow:function(frame){
			return frame && typeof(frame)=='object' && frame.tagName == 'IFRAME' && frame.contentWindow;
		},
		Uploader:function(callBack){
			var addWin = $("#uploadfile-win");
			var upladoer = $('<iframe/>');
			upladoer.attr({'src':urls['msUrl']+'/upload/upload.jsp',width:'100%',height:'100%',frameborder:'0',scrolling:'yes'});
			addWin.window({
				title:"上传文件",
				height:450,
				width:1000,
				minimizable:false,
				modal:true,
				collapsible:true,
				maximizable:true,
				resizable:true,
				content:upladoer,
				onClose:function(){
					var fw = _this.GetFrameWindow(upladoer[0]);
					var files = fw.files;
					//$(this).window('destroy');
					var param = $('#searchForm').serializeObject();
					$('#data-list').datagrid('reload',param);
					callBack.call(this,files);
				},
				onOpen:function(){
					var target = $(this);
					setTimeout(function(){
						var fw = _this.GetFrameWindow(upladoer[0]);
						fw.target = target;
					},100);
				}
			});
		},
		makerUpload:function(){
			_this.Uploader(function(files){
	 			if(files && files.length>0){
	 				Love.alert('提示',"成功上传："+files.join(","),'info');
	 			}
 			});
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
					{id:'btnaddfile',text:'上传',btnType:'addFile',iconCls:'icon-add',handler:function(){
						//$("#uploadfile-win").window('open');
						_this.makerUpload();
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