package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.DocumentMapper;
import cn.ewsd.system.model.Document;
import cn.ewsd.system.service.DocumentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName DocumentServiceImpl
 * @Description 
 * @Author 佐佑科技<service@ewsd.cn>
 * @Date 2017/3/18 12:15
 */
@Service("documentServiceImpl")
public class DocumentServiceImpl extends SystemBaseServiceImpl<Document, String> implements DocumentService {

    @Resource
    private DocumentMapper documentMapper;

//    @Resource
//    private SystemHBaseDao<Document, String> documentDao;
//
//
//    @Override
//    public Document getDocumentByUuid(String uuid) {
//        return documentDao.get(Document.class, uuid);
//    }
//
//    @Override
//    public Integer deleteDocumentByUuid(String uuid) {
//        return documentDao.delete(getDocumentByUuid(uuid));
//    }
//
//    @Override
//    public void updateViewCountByUuid(String uuid) {
//        try {
//            Document document = documentDao.get(Document.class, uuid);
//            document.setViewNum(document.getViewNum() + 1);
//            documentDao.update(document);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<Document> getHotListByCid(Integer cid, Integer limit) {
//        return documentDao.getLimitedListByHql("SELECT new Document(uuid, title, description, thumbnail, viewNum, creator, createTime) FROM Document WHERE cid IN (" + cid + ") ORDER BY viewNum DESC", 0, limit);
//    }
//
//    @Override
//    public List<Document> getHotListByCids(String cids, Integer limit) {
//        return documentDao.getLimitedListByHql("SELECT new Document(uuid, title, description, thumbnail, viewNum, creator, createTime) FROM Document WHERE cid IN (" + cids + ") ORDER BY viewNum DESC", 0, limit);
//    }
//
//    @Override
//    public List<Document> getLimitedListByCid(Integer cid, Integer limit) {
//        return documentDao.getLimitedListByHql("SELECT new Document(uuid, title, description, thumbnail, viewNum, creator, createTime) FROM Document WHERE cid IN (" + cid + ") ORDER BY createTime DESC", 0, limit);
//    }
//
//    @Override
//    public List<Document> getLimitedListByCids(String cids, Integer limit) {
//        return documentDao.getLimitedListByHql("SELECT new Document(uuid, title, description, thumbnail, viewNum, creator, createTime) FROM Document WHERE cid IN (" + cids + ") ORDER BY createTime DESC", 0, limit);
//    }

    @Override
    public PageSet<Document> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Document> list = documentMapper.getPageSet(filterSort);
        PageInfo<Document> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public Integer getListByCid(Integer id) {
        return documentMapper.getListByCid(id);
    }

    @Override
    public List<Document> getLimited() {
        return documentMapper.getLimited();

    }
}