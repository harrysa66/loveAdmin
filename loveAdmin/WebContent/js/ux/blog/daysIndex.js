$package('Love.blog.indexPage');
$(function(){
	$("#saveBtn").click(function(){
		$('#saveForm').form('submit', {    
    		success: function(data){    
        		var data = eval('(' + data + ')');  // change the JSON string to javascript object    
        		if (data.success){    
            		Love.alert('提示',data.msg,'info',function(){
            			Love.refreshTab();
            		});
        		}else{
        			Love.alert('警告',data.msg,'warning',function(){
            			Love.refreshTab();
            		});
        		}    
    		}    
		});
	});
	
	
});		

function viewImg(url){
	$('#viewImg').attr('src',url);
	$('#viewFile-win').window({
		width:165,    
  		height:185
		}).window('open');
}

function delBoyImg(){
	$("#boyDiv").empty();
	var html = "<label>男孩头像:</label><input class='easyui-validatebox' type='file' name='boy'>";
	$("#boyDiv").append(html);
}

function delGirlImg(){
	$("#girlDiv").empty();
	var html = "<label>女孩头像:</label><input class='easyui-validatebox' type='file' name='girl'>";
	$("#girlDiv").append(html);
}