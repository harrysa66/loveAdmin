$package('Love.main');

Love.main = function(){
	return {
		treeSelect:function(){
			var _this = $(this);
			var title=_this.text();
			var url=_this.attr('href');
			Love.main.addTab(title,url);
			return false;
		},
		treeInit:function(){
			var  $items =  $('#tree-box').find(".menu-item");
			$items.bind('click',this.treeSelect);
		},
		addTab:function(_title,_url){
			var boxId = '#tab-box';
			if($(boxId).tabs('exists',_title) ){
				var tab = $(boxId).tabs('getTab',_title);
				var index = $(boxId).tabs('getTabIndex',tab);
				$(boxId).tabs('select',index);
				if(tab && tab.find('iframe').length > 0){  
					 var _refresh_ifram = tab.find('iframe')[0];  
				     _refresh_ifram.contentWindow.location.href=_url;  
			    } 
			}else{		
				var _content ="<iframe scrolling='auto' frameborder='0' src='"+_url+"' style='width:100%; height:100%'></iframe>";
				$(boxId).tabs('add',{
					    title:_title,
					    content:_content,
					    closable:true});
				
			}
		},
		menuHover:function(){
			//菜单鼠标进入效果
			$('.menu-item').hover(
				function () {
					$(this).stop().animate({ paddingLeft: "25px" }, 200,function(){
						$(this).addClass("orange");
					});
				}, 
				function () {
					$(this).stop().animate({ paddingLeft: "15px" },function(){
						$(this).removeClass("orange");
					});
				}
			);
		},
		modifyPwd:function(){
			var pwdForm = $("#pwdForm");
			if(pwdForm.form('validate')){
				//var parentId =$('#search_parentId').val();
				//$("#edit_parentId").val(parentId);
				Love.saveForm(pwdForm,function(data){
					if(data.success){
						$('#modify-pwd-win').dialog('close');
				    	pwdForm.resetForm();
			       		Love.alert('提示',data.msg,'info',function(){
			       			window.location.href=urls['msUrl']+"/j_spring_security_logout";
			       		});
		        	}else{
		       	  	 Love.alert('提示',data.msg,'error');  
		        	}
				});
			 }
		},
		init:function(){
			
			this.treeInit();
			this.menuHover();
			
			//修改密码绑定事件
			$('.modify-pwd-btn').click(function(){
				$("#pwdForm").resetForm()
				$('#modify-pwd-win').dialog('open');
			});
			$('#btn-pwd-close').click(function(){
				$('#modify-pwd-win').dialog('close');
			});
			$('#btn-pwd-submit').click(this.modifyPwd);
			
			$('#tab-box').tabs({    
    			onSelect:function(title){    
        			  
    			},
    			onContextMenu:function(e, title,index){
    				e.preventDefault();
        			$('#tabs-menu').menu('show', {
            			left: e.pageX,
            			top: e.pageY
        			});
        			$('#tabs-menu').data("currtab",title);
    			}
			}); 
			
    		//关闭当前标签页
    		$("#closecur").bind("click",function(){
    			var currtab_title = $('#tabs-menu').data("currtab");
        		var tab = $('#tab-box').tabs('getTab',currtab_title);
        		var index = $('#tab-box').tabs('getTabIndex',tab);
        		if(index != 0){
        			$('#tab-box').tabs('close',index);
        		}
    		});
    		//关闭所有标签页
    		$("#closeall").bind("click",function(){
        		var tablist = $('#tab-box').tabs('tabs');
        		for(var i=tablist.length-1;i>=1;i--){
            		$('#tab-box').tabs('close',i);
        		}
    		});
    		//关闭非当前标签页（先关闭右侧，再关闭左侧）
    		$("#closeother").bind("click",function(){
        		var tablist = $('#tab-box').tabs('tabs');
        		var currtab_title = $('#tabs-menu').data("currtab");
        		var tab = $('#tab-box').tabs('getTab',currtab_title);
        		var index = $('#tab-box').tabs('getTabIndex',tab);
        		for(var i=tablist.length-1;i>index;i--){
           			$('#tab-box').tabs('close',i);
        		}
        		var num = index-1;
        		for(var i=num;i>=1;i--){
            		$('#tab-box').tabs('close',1);
        		}
    		});
    		//关闭当前标签页右侧标签页
    		$("#closeright").bind("click",function(){
        		var tablist = $('#tab-box').tabs('tabs');
        		var currtab_title = $('#tabs-menu').data("currtab");
        		var tab = $('#tab-box').tabs('getTab',currtab_title);
        		var index = $('#tab-box').tabs('getTabIndex',tab);
        		for(var i=tablist.length-1;i>index;i--){
            		$('#tab-box').tabs('close',i);
        		}
    		});
    		//关闭当前标签页左侧标签页
    		$("#closeleft").bind("click",function(){
        		var currtab_title = $('#tabs-menu').data("currtab");
        		var tab = $('#tab-box').tabs('getTab',currtab_title);
        		var index = $('#tab-box').tabs('getTabIndex',tab);
        		var num = index-1;
        		for(var i=1;i<=num;i++){
            		$('#tab-box').tabs('close',1);
        		}
    		});
			
		}
	};
}();

$(function(){
	Love.main.init();
});		