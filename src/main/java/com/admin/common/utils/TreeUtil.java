package com.admin.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.admin.controller.model.MenuTreeModel;
import com.admin.controller.model.MenuVo;

public class TreeUtil {

    public static List<MenuVo> toTree(List<MenuVo> treeList, Long pid) {
        List<MenuVo> retList = new ArrayList<MenuVo>();
        for (MenuVo parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }
    private static MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
        for (MenuVo child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
    
    public static List<MenuTreeModel> toRoleTree(List<MenuTreeModel> treeList, Long pid) {
        List<MenuTreeModel> retList = new ArrayList<MenuTreeModel>();
        for (MenuTreeModel parent : treeList) {
            if (pid.equals(parent.getPid())) {
                retList.add(findRoleChildren(parent, treeList));
            }
        }
        return retList;
    }
    private static MenuTreeModel findRoleChildren(MenuTreeModel parent, List<MenuTreeModel> treeList) {
        for (MenuTreeModel child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findRoleChildren(child, treeList));
            }
        }
        return parent;
    }
}
