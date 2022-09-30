package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.DicSetMapper;
import cn.ewsd.system.model.DicItem;
import cn.ewsd.system.model.DicSet;
import cn.ewsd.system.service.DicItemService;
import cn.ewsd.system.service.DicSetService;
import cn.ewsd.system.service.RedissonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
@Service("dicSetService")
public class DicSetServiceImpl extends SystemBaseServiceImpl<DicSet, String> implements DicSetService {

//    @Resource
//    private ISystemBaseDao<DicSet, String> dicSetDao;

    @Resource
    private DicItemService dicItemService;

    @Resource
    private DicSetMapper dicSetMapper;
    @Autowired
    private RedissonService redissonService;
//    @Resource
//    public void setSystemBaseDao(ISystemBaseDao<DicSet, String> dicSetDao) {
//        super.setBaseDao(dicSetDao);
//    }

    @Autowired
    private ServletContext servletContext;

    @Override
    public void init(ServletContext servletContext) {
        this.servletContext = servletContext;

        Map<String, Object> localDics = new HashMap<>();
        Map<String, String> map = null;

        List<DicSet> dicSets = dicSetMapper.selectAll();
        for (DicSet dicSet : dicSets) {
            String code = dicSet.getCode();
           // "SELECT text,value FROM sys_dic_item WHERE puuid = '" + dicSet.getUuid() + "'"
            List<Map<String, Object>> dicItems = dicItemService.getListByPuuid(dicSet.getUuid());

            //servletContext.setAttribute(code + "DicSet", dicItems);

            JSONArray jsonArray = JSONArray.fromObject(dicItems);
            String jsonStr = jsonArray.toString();
            servletContext.setAttribute(code + "DicSet", jsonStr);

            localDics.put(code, dicItems);
        }
    }

    @Override
    public Object getDicItem(String key) {
        Object value = servletContext.getAttribute(key);
        return value;
    }

    @Override
    public DicSet getDicSetByCode(String code) {
        return dicSetMapper.getDicSetByCode(code);
    }

//    @Override
//    public DicSet getDicSetByCode(String code) {
//        return dicSetDao.getDetailByHql(String.format("FROM DicSet WHERE code = '%s'", code));
//    }

    @Override
    public DicSet getListByCode(String code) {
        return dicSetMapper.getListByCode(code);
    }

    @Override
    public Integer updateIsDel(int i, String s) {
        return dicSetMapper.updateIsDel(i,s);
    }

    @Override
    public List<DicSet> getListByCodes(String code) {
        return dicSetMapper.getListByCodes(code);
    }

    @Override
    public PageSet<DicSet> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<DicSet> list = dicSetMapper.getPageSet(filterSort);
        PageInfo<DicSet> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public DicItem getListByValue(String value) {
        return dicSetMapper.getListByValue(value);
    }

    @Override
    public Boolean getListByValueAndUuid(String value, String uuid) {
        Integer integer=dicSetMapper.getListByValueAndUuid(value,uuid);
        return integer>0?true:false;
    }

    @Override
    public List<DicSet> getAllDicSet() {
        return dicSetMapper.getAllDicSet();
    }

}
