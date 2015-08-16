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
        		}    
    		}    
		});
	});
});		