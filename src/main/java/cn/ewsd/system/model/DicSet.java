package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;
import io.swagger.models.auth.In;

import javax.persistence.Table;


@Table(name = "sys_dic_set")
public class DicSet extends MCoreBase {

    private String code;
    private String text;
    private Integer sort;
    private String remark;
    private Integer isDel;

    public DicSet(){
        super();
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
