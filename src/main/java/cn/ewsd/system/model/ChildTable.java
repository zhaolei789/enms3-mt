package cn.ewsd.system.model;




import cn.ewsd.base.utils.ColumnEntity;

import java.util.List;

public class ChildTable {
    //标题
    private String title;
    //列
    private List<ColumnEntity> clouns;
    private String Id;
    private String pathName;
    private String className;
    private String classname;
    //字表关联字段
    private String relation;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ColumnEntity> getClouns() {
        return clouns;
    }

    public void setClouns(List<ColumnEntity> clouns) {
        this.clouns = clouns;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
