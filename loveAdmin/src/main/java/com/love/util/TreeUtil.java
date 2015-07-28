package com.love.util;

import java.util.ArrayList;
import java.util.List;

import com.love.framework.common.TreeNode;
import com.love.system.po.Menu;
import com.love.system.po.MenuBtn;

public class TreeUtil {
	List<Menu> rootMenus;
	List<Menu> childMenus;
	List<MenuBtn> childBtns;
	
	public TreeUtil(List<Menu> rootMenus,List<Menu> childMenus){
		this.rootMenus = rootMenus;
		this.childMenus = childMenus;
	}  
	public TreeUtil(List<Menu> rootMenus,List<Menu> childMenus,List<MenuBtn> childBtns){
		this.rootMenus = rootMenus;
		this.childMenus = childMenus;
		this.childBtns = childBtns;
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
	
	/**
	 * 
	 * @param menu
	 * @return
	 */
	private TreeNode BtnToNode(MenuBtn btn){
		if(btn == null){
			return null;
		}
		TreeNode node = new TreeNode();
		node.setId(btn.getCode());
		node.setDataId(btn.getId());
		node.setText(btn.getName());
		node.setParentId(btn.getMenuId());
		node.getAttributes().put("type", "1");
		node.getAttributes().put("id", btn.getId());
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
				if(childBtns != null && !childBtns.isEmpty()){
					addChlidBtn(node);
				}
				childNodes.add(node);
			}
		}
		rootNode.setChildren(childNodes);
	}
	
	/**
	 * 设置菜单button
	 * @param menu
	 * @return
	 */
	private void addChlidBtn(TreeNode treeNode){
		List<TreeNode> childNodes = new ArrayList<TreeNode>(); 
		for(MenuBtn btn : childBtns){
			if(treeNode.getDataId().equals(btn.getMenuId())){
				TreeNode node = BtnToNode(btn);
				childNodes.add(node);
			}
		}
		treeNode.setChildren(childNodes);
	}
	
}
