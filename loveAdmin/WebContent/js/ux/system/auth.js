$package('Love.system.auth');
Love.system.auth = function(){
	var _box = null;
	//var parents = new Array();
	var _this = {
		menu: $('#menu-tree'),
		/*getParents:function(node){
			var parentNode = _this.menu.tree('getParent',node.target);
			if(parentNode != null){
				parents.push(parentNode);
				_this.getParents(parentNode);
			}
			return parents;
		},*/
		buildTreeData:function(node){
			/*$.each(nodes,function(i,note){
				var id = note.attributes.id;
				var type = note.attributes.type;
				var $id = $("<input type='hidden' name='menuIds' class='c_menus'>");
				if(type == 0){
					$id.attr('name','menuIds');
				}else if(type == 1){
					$id.attr('name','btnIds');
				}
				$id.val(id);
				_box.form.edit.append($id);
			});*/
			var id = node.attributes.id;
			var code = node.id;
			var $id = $("<input type='hidden' name='menuId' class='c_menus'>");
			var $code = $("<input type='hidden' name='menuCode' class='c_menus'>");
			$id.val(id);
			$code.val(code);
			_box.form.edit.append($id);
			_box.form.edit.append($code);
		},
		setTreeValue:function(id){
			var node = _this.menu.tree("find",id);
			if(node && node.target){
				_this.menu.tree('select',node.target);
				//判断是否选择或者半选状态 
				/*if($(node.target).find(".tree-checkbox0")[0]){
					_this.menu.tree('check',node.target);
				}*/
			}
		},
		clearTreeData:function(){
			$(".tree-node-selected",_this.menu).removeClass("tree-node-selected");
			$('.c_menus').remove();
		},
		config:{
			event:{
				add:function(){
					_this.clearTreeData();
					_box.handler.add();
				},
				edit:function(){
					_this.clearTreeData();
					_box.handler.edit(function(result){
						var menuId  = result.data.menuId;
						_this.setTreeValue(menuId);
						$("#menuFullName").text(result.menuFullName);
						/*$.each(btnIds,function(i,id){
							_this.setTreeValue("btn_"+id);
						});
						
						$.each(menuIds,function(i,id){
							_this.setTreeValue("menu_"+id);
						});*/
					});
				},
				save:function(){
					//var checknodes = _this.menu.tree('getChecked');	
					//var innodes = _this.menu.tree('getChecked','indeterminate');
					//var nodes = new Array();
					var selectnode = _this.menu.tree('getSelected');
					if(selectnode == null){
						Love.alert('警告','未选中菜单','warning');
					}else{
						//nodes.push(selectnode);
						//var parents = _this.getParents(selectnode);
						_this.buildTreeData(selectnode);
						//_this.buildTreeData(parents);
						_box.handler.save();
					}
				}
			},
  			dataGrid:{
  				title:'权限列表',
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
						{field:'fullName',title:'所属菜单',width:200,sortable:true}
						
				]]
			}
		},
		init:function(){
			_box = new DataGrid(_this.config); 
			_box.init();
			
			_this.menu.tree({
				url:urls['msUrl']+'/system/menu/getMenuTree.s',
				checkbox:false
			});
		}
	}
	return _this;
}();

$(function(){
	Love.system.auth.init();
});		