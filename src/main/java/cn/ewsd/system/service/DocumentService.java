package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Document;

import java.util.List;

/**
 * @ClassName IDocumentService
 * @Description
 * @Author 佐佑科技<service@ewsd.cn>
 * @Date 2017/3/18 12:15
 */
public interface DocumentService extends SystemBaseService<Document, String> {

//    /**
//     * 根据uuid获得文档详情
//     *
//     * @param uuid
//     * @return
//     */
//    public Document getDocumentByUuid(String uuid);
//
//    /**
//     * 根据uuid删除Document
//     *
//     * @param uuid
//     * @return
//     */
//    public Integer deleteDocumentByUuid(String uuid);
//
//    /**
//     * 更新文档访问次数加1
//     *
//     * @param uuid
//     */
//    public void updateViewCountByUuid(String uuid);
//
//    /**
//     * 获得某个cid下的热门文章
//     *
//     * @param cid
//     * @param limit
//     * @return
//     */
//    public List<Document> getHotListByCid(Integer cid, Integer limit);
//
//    /**
//     * 获得某个cid及子cid下的热门文章
//     *
//     * @param cids
//     * @param limit
//     * @return
//     */
//    public List<Document> getHotListByCids(String cids, Integer limit);
//
//    /**
//     * 获得某个cid下的文章
//     *
//     * @param cid
//     * @param limit
//     * @return
//     */
//    public List<Document> getLimitedListByCid(Integer cid, Integer limit);
//
//    /**
//     * 获得某个cid及子cid下的文章
//     *
//     * @param cids
//     * @param limit
//     * @return
//     */
//    public List<Document> getLimitedListByCids(String cids, Integer limit);

    PageSet<Document> getPageSet(PageParam pageParam, String filterSort);

    Integer getListByCid(Integer id);

    List<Document> getLimited();
}