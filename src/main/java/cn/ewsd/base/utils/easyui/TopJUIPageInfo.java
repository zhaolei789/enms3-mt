package cn.ewsd.base.utils.easyui;

import java.util.ArrayList;
import java.util.List;

public class TopJUIPageInfo {
    private long total;
    private List rows = new ArrayList<>();

    public TopJUIPageInfo() {
        super();
    }

    public TopJUIPageInfo(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}