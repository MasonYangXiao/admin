package com.admin.controller.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author mason
 *
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTreeModel {

    private Long id;
    @JsonIgnore
    private Long pid;

    private String title;

    //private String field;  //节点字段名
    
  //  private String href;//点击节点弹出新窗口对应的 url。需开启 isJump 参数
  //  private Boolean spread;//节点是否初始展开，默认 false
    private Boolean checked;
    /** 状态 是否禁用*/
    private boolean disabled;

    private List<MenuTreeModel> children= new ArrayList<MenuTreeModel>();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuTreeModel treeModel = (MenuTreeModel) o;

        return id != null ? id.equals(treeModel.id) : treeModel.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
