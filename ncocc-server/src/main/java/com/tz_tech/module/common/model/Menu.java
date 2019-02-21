package com.tz_tech.module.common.model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

    /**
     * 菜单ID
     */
    private int id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单Url
     */
    private String url;

    /**
     * 菜单ID
     */
    private int parent_id;

    /**
     * 菜单备注
     */
    private String remark;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单顺序
     */
    private int sort;

    /**
     * 子菜单
     */
    private List<Menu> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parent_id=" + parent_id +
                ", remark='" + remark + '\'' +
                ", icon='" + icon + '\'' +
                ", sort=" + sort +
                ", children=" + children +
                '}';
    }
}
