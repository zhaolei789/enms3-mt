package cn.ewsd.system.model;


import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


public class CodeTable {

    // 主表
    private String masterTable;
    //页面结构
    private String structure;
    //标题
    private String [] title;
    //子表
    private String [] childTable;
    //主子表关联字段
    private String [] associated;

    public String getMasterTable() {
        return masterTable;
    }

    public void setMasterTable(String masterTable) {
        this.masterTable = masterTable;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getChildTable() {
        return childTable;
    }

    public void setChildTable(String[] childTable) {
        this.childTable = childTable;
    }

    public String[] getAssociated() {
        return associated;
    }

    public void setAssociated(String[] associated) {
        this.associated = associated;
    }
}
