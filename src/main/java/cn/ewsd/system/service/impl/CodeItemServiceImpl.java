package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.PropertiesUtil;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.CodeItemMapper;
import cn.ewsd.system.model.CodeItem;
import cn.ewsd.system.service.CodeItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.*;

@SuppressWarnings({"unchecked"})
@Service("codeItemService")
public class CodeItemServiceImpl extends SystemBaseServiceImpl<CodeItem, String> implements CodeItemService {
    @Resource
    private CodeItemMapper codeItemMapper;

//    @Resource
//    private SystemHBaseDao<CodeItem, String> codeItemDao;

//    @Resource
//    public void setCodeItemDao(ISystemBaseDao<CodeItem, String> codeItemDao) {
//        super.setBaseDao(codeItemDao);
//    }

    public CodeItem codeItem;

    @Autowired
    private ServletContext servletContext;

//    @Override
//    public Integer saveByEntity(CodeItem codeItem) {
//        return codeItemDao.save(codeItem);
//    }
//
//    @Override
//    public Integer updateByEntity(CodeItem codeItem) {
//        return codeItemDao.update(codeItem);
//    }
//
//    @Override
//    public Integer deleteByUuid(String uuid) {
//        return codeItemDao.delete(getDetailByPrimaryKey(uuid));
//    }
//
//    @Override
//    public CodeItem getDetailByPrimaryKey(String uuid) {
//        return codeItemDao.get(CodeItem.class, uuid);
//    }

    @Override
    public String getTextById(String id) {
          //  "from CodeItem where id = '" + id + "'"
            codeItem = codeItemMapper.getListById(id);
            return codeItem.getText();
    }

    @Override
    public String getTextByCodesetIdAndId(String codesetId, String id) {
        try {
            //"from CodeItem where codesetId = '" + codesetId + "' and id = '" + id + "' and isdel = 0"
            codeItem = codeItemMapper.getDetailByCodesetIdAndId(codesetId,id);
            return codeItem.getText();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getTextByCodesetIdAndCode(String codesetId, String code) {
        try {
            //"from CodeItem where codesetId = '" + codesetId + "' and code = '" + code + "' and isdel = 0"
            codeItem = codeItemMapper.getDetailByCodesetIdAndCode(codesetId,code);
            return codeItem.getText();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getCodeByCodeSetIdAndText(String codesetId, String text) {
        try {
            //"from CodeItem where codesetId = '" + codesetId + "' and text = '" + text + "' and isdel = 0"
            codeItem = codeItemMapper.getDetailByCodesetIdAndText(codesetId,text);
            return codeItem.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    List<String> fatherIdsArray = new ArrayList<String>();

    @Override
    public String getFatherIds(String codeSetId, String id, String levelId) {
        fatherIdsArray.clear();
        return recursionFn(codeSetId, id, levelId);
    }

    @Override
    public String recursionFn(String codeSetId, String id, String levelId) {
       // String hql = "from CodeItem where codeSetId like '" + codeSetId + "%' and id = '" + id + "' and levelId <> '" + levelId + "'";
        Integer count = codeItemMapper.getCountByCodeSetIdAndId(codeSetId,id,levelId);
        if (count >= 1) {
            CodeItem codeItem = codeItemMapper.getDetailByHqlCodeSetIdAndId(codeSetId,id,levelId);
            recursionFn(codeSetId, codeItem.getPid(), levelId);
            fatherIdsArray.add(codeItem.getPid());
        }
        return fatherIdsArray.toString().substring(1, fatherIdsArray.toString().length() - 1).replace(" ", "");
    }

    @Override
    public void init(ServletContext servletContext) {
        this.servletContext = servletContext;
        List<CodeItem> list = codeItemMapper.selectAll();
        Map<String, Map<String, String>> localDics = new HashMap<String, Map<String, String>>();
        Map<String, String> map = null;
        for (CodeItem codeItem : list) {
            String type = codeItem.getType();
            if (localDics.containsKey(type)) {
                map = localDics.get(type);
                map.put(codeItem.getCodeSetId() + codeItem.getCode(), codeItem.getText());
            } else {
                map = new HashMap<String, String>();
                map.put(codeItem.getCodeSetId() + codeItem.getCode(), codeItem.getText());
                localDics.put(type, map);
            }
        }

        Properties prop = PropertiesUtil.getProperties("dictionary.properties");
        for (Object key : prop.keySet()) {
            if(key.toString().contains("codeItem_")) {
                Object value = prop.get(key);
                Object obj = JSONObject.fromObject(localDics.get(value));
                servletContext.setAttribute((String) key, obj);
            }
        }
    }

    @Override
    public String getValue(String type, String key) {
        JSONObject jsonObject = (JSONObject) servletContext.getAttribute(type);
        return (String) jsonObject.get(key);
    }

    @Override
    public PageSet<CodeItem> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<CodeItem> list = codeItemMapper.getPageSet(filterSort);
        PageInfo<CodeItem> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<CodeItem> getListByLevelId(String filterStr) {
        return codeItemMapper.getListByLevelId(filterStr);
    }

    @Override
    public List<CodeItem> getListByCodeSetIdAndLevelId(String codeSetId, String levelId) {
        return codeItemMapper.getListByCodeSetIdAndLevelId(codeSetId,levelId);
    }

    @Override
    public List<CodeItem> getListByPid(String pid) {
        return codeItemMapper.getListByPid(pid);
    }

    @Override
    public List<CodeItem> getListByCodeSetIdAndPid(String codeSetId, String pid) {
        return codeItemMapper.getListByCodeSetIdAndPid(codeSetId,pid);
    }

    @Override
    public List<CodeItem> getListByCodeSetId(String codeSetId) {
        return codeItemMapper.getListByCodeSetId(codeSetId);
    }

    @Override
    public List<CodeItem> getListByPidAndLevelId(String pid, int levelId) {
        return codeItemMapper.getListByPidAndLevelId(pid,levelId);
    }

    @Override
    public String getFatherIdsVarchar(Map map) {
        return codeItemMapper.getFatherIdsVarchar(map);
    }

    @Override
    public Integer cascadeDeleteByUuid(String uuid) {
        return codeItemMapper.cascadeDeleteByUuid(uuid);
    }

    @Override
    public CodeItem getCodeItemById(String id) {
        return codeItemMapper.getCodeItemById(id);
    }

}
