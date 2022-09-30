package cn.ewsd.base.utils.easyui;

/**
 * 分布查询请求，参照 EasyUI 分页规范
 *
 * Created by admin on 2017-07-18.
 */
public class PageParam {

    private int page = 1; // 第几数
    private int rows = 20; // 每页记录数（行数）
    private String sort; // 排序字段
    private String order; // asc/desc

    public PageParam() {}

    public PageParam(int page, int rows) {
        this.page = page;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
