package com.love.util;

import java.util.ArrayList;
import java.util.List;

import com.love.framework.common.TreeNode;
import com.love.system.po.Menu;

public class TreeUtil {
	List<Menu> rootMenus;
	List<Menu> childMenus;
	
	public TreeUtil(List<Menu> rootMenus,List<Menu> childMenus){
		this.rootMenus = rootMenus;
		this.childMenus = childMenus;
	}  
	
	public List<TreeNode> getTreeNode(){
		return getRootNodes();
	}
	
	/**
	 * 
	 * @param menu
	 * @return
	 */
	private TreeNode MenuToNode(Menu menu){
		if(menu == null){
			return null;
		}
		TreeNode node = new TreeNode();
		node.setId(menu.getCode());
		node.setDataId(menu.getId());
		node.setText(menu.getName());
		node.setUrl(menu.getUrl());
		node.setParentId(menu.getParentId());
		node.getAttributes().put("type", "0");
		node.getAttributes().put("id", menu.getId());
		return node;
	}
	
	private List<TreeNode> getRootNodes(){
		List<TreeNode> rootNodes = new ArrayList<TreeNode>();
		for(Menu menu : rootMenus){
			TreeNode node = MenuToNode(menu);
			if(node != null){
				addChlidNodes(node);
				rootNodes.add(node);
			}
		}
		return rootNodes;
	}
	
	/**
	 * 
	 * @param menu
	 * @return
	 */
	private void addChlidNodes(TreeNode rootNode){
		List<TreeNode> childNodes = new ArrayList<TreeNode>();  
		for(Menu menu : childMenus){
			if(rootNode.getDataId().equals(menu.getParentId())){
				TreeNode node = MenuToNode(menu);
				childNodes.add(node);
			}
		}
		rootNode.setChildren(childNodes);
	}
	
}
