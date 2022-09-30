package cn.ewsd.system.model;

import cn.ewsd.common.model.MCoreBase;

import javax.persistence.Table;


@Table(name = "sys_dic_item")
public class DicItem extends MCoreBase {

    private String puuid;
    private String text;
    private String value;
    private Integer sort;
    private String selected;
    private String remark;
    private String valueColour;
    private Integer isDel;

    public String getValueColour() {
        return valueColour;
    }

    public void setValueColour(String valueColour) {
        this.valueColour = valueColour;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DicItem() {
        super();
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
