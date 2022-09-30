package cn.ewsd.base.utils.easyui;

import java.io.Serializable;
import java.util.List;

/**
 * 对查询结果进行分页封装，参照 EasyUI 分页规范
 *
 * Created by admin on 2017-07-18.
 */
public class PageSet<T> implements Serializable {

    //总记录数
    private long total;
    //结果集
    private List<T> rows;

    //总页数
    private int pages;

    public PageSet() {
    }

    public PageSet(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
