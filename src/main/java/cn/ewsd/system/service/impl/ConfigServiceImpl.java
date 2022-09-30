package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.PropertiesUtil;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.ConfigMapper;
import cn.ewsd.system.model.Config;
import cn.ewsd.system.properties.MyProperties;
import cn.ewsd.system.service.ConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author 佐佑科技(service @ ewsd.cn)
 * @date 2015-12-09
 */
@SuppressWarnings({"unchecked"})
@Service("configService")
@CacheConfig(cacheNames = "config")
public class ConfigServiceImpl extends SystemBaseServiceImpl<Config, String> implements ConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Resource
    private ConfigService configService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MyProperties myProperties;

    @Override
//    @Cacheable(key = "#p0")
    public Config selectByPrimaryKey(String pk) {
        return configMapper.selectByPrimaryKey(pk);
    }

    @Override
    //@CachePut(key = "#p0.uuid")
//    @Cacheable(key="#param.uuid")
    public Integer updateByPrimaryKey(Config param) {
        return configMapper.updateByPrimaryKey(param);
    }

    @Override
    public void cacheConfig() throws Exception {
        //查询所有的配置信息
        List<Config> list = configService.selectAll();
        Map<String, Map<String, String>> localDics = new HashMap<String, Map<String, String>>();
        Map<String, String> map = null;
        for (cn.ewsd.system.model.Config config : list) {
            String type = config.getType();
            if (localDics.containsKey(type)) {
                map = localDics.get(type);
                map.put(config.getCode(), config.getValue());
            } else {
                map = new HashMap<String, String>();
                map.put(config.getCode(), config.getValue());
                localDics.put(type, map);
            }
        }
        //将指定的配置属性赋值到前台
        Properties prop = PropertiesUtil.getProperties("dictionary.properties");
        for (Object key : prop.keySet()) {
            if (key.toString().contains("config_")) {
                Object value = prop.get(key);
                Object obj = JSONObject.fromObject(localDics.get(value));
                servletContext.setAttribute((String) key, obj);
            }
        }
    }
    @Override
    @Cacheable("configList")
    public Map<String, Map<String, String>> redisCacheConfig() throws Exception {
        //查询所有的配置信息
        List<Config> list = configService.selectAll();
        Map<String, Map<String, String>> localDics = new HashMap<String, Map<String, String>>();
        Map<String, String> map = null;
        for (cn.ewsd.system.model.Config config : list) {
            String type = config.getType();
            if (localDics.containsKey(type)) {
                map = localDics.get(type);
                map.put(config.getCode(), config.getValue());
            } else {
                map = new HashMap<String, String>();
                map.put(config.getCode(), config.getValue());
                localDics.put(type, map);
            }
        }
        return localDics;
        //将指定的配置属性赋值到前台
//        Properties prop = PropertiesUtil.getProperties("dictionary.properties");
//        for (Object key : prop.keySet()) {
//            if (key.toString().contains("config_")) {
//                Object value = prop.get(key);
//                Object obj = JSONObject.fromObject(localDics.get(value));
//                servletContext.setAttribute((String) key, obj);
//            }
//        }
    }

    // http://blog.csdn.net/newstruts/article/details/18668269
    // http://www.cnblogs.com/xiohao/p/5279581.html
    // @PostConstruct

    public void init(ServletContext servletContext) {
        this.servletContext = servletContext;
        List<Config> configs = this.getListByAll();
        Map<String, Map<String, String>> localDics = new HashMap<String, Map<String, String>>();
        Map<String, String> map = null;
        for (Config config : configs) {
            String type = config.getType();
            if (localDics.containsKey(type)) {
                map = localDics.get(type);
                map.put(config.getCode(), config.getValue());
            } else {
                map = new HashMap<String, String>();
                map.put(config.getCode(), config.getValue());
                localDics.put(type, map);
            }
        }

        /*Properties prop = PropertiesUtil.getProperties("config.properties");
        for (Object key : prop.keySet()) {
            if (key.toString().contains("sysConfig")) {
                Object value = prop.get(key);
                //从本地字典中取值出来转换为json对象
                Object obj = JSONObject.fromObject(localDics.get(value));
                //将json对象存到容器中
                servletContext.setAttribute((String) key, obj);
            }
        }*/

        String configDic = myProperties.getConfigDic();
        String[] configDicArray = configDic.split(",");
        for (int i = 0; i < configDicArray.length; i++) {
            String value = configDicArray[i];
            if (value.contains("sysConfig")) {
                //从本地字典中取值出来转换为json对象
                Object obj = JSONObject.fromObject(localDics.get(value));
                //将json对象存到容器中
                servletContext.setAttribute((String) value, obj);
            }
        }

    }

    @Override
    public String getValue(String type, String key) {
        JSONObject jsonObject = (JSONObject) servletContext.getAttribute(type);
        return (String) jsonObject.get(key);
    }

    @Override
    public String getConfigValue(String key) {
        String value = servletContext.getAttribute(key).toString();
        return value;
    }


    public boolean isDic(String type) {
        JSONObject jsonObject = (JSONObject) servletContext.getAttribute(type);
        return jsonObject == null ? false : true;
    }

    public String getKey(String type, String value) {
        JSONObject jsonObject = (JSONObject) servletContext.getAttribute(type);
        for (Object key : jsonObject.keySet()) {
            String sValue = (String) jsonObject.get(key);
            if (sValue.equals(value)) {
                return (String) key;
            }
        }
        return null;
    }

    // @PreDestroy
    // public void destroy() {
    // localDics = null;
    // }

    @SuppressWarnings("unchecked")
    public Map<String, String> findDictionary(String type) {
        JSONObject jsonObject = (JSONObject) servletContext.getAttribute(type);
        return jsonObject;
    }

    @Override
    public Integer getMaxSort() {
        return configMapper.getMaxSort();
    }

    @Override
    @Cacheable(unless="#result == null || #result.size() == 0")
    public Config getListByCodeAndUuid(String code, String uuid) {
        return configMapper.getListByCodeAndUuid(code, uuid);
    }

    @Override
//    @Cacheable("configList")
    public PageSet<Config> getPageSet(PageParam pageParam, String filterStr) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Config> list = configMapper.getPageSet(filterStr);
        PageInfo<Config> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<Config> getDataByTablesAndField(String tables, String field) {
        return configMapper.getDataByTablesAndField(tables,field);
    }



    @Override
    public int batchInsert(List<Config> config) {
        return configMapper.batchInsert(config);
    }

    @Override
    @Cacheable(unless="#result == null || #result.size() == 0")
    public List<Config> getListByAll() {
        return configMapper.getListByAll();
    }

    @Override
    @Cacheable(unless="#result == null")
    public String getConfigByCode(String code) {
        //  String hql = "SELECT value FROM Config WHERE code = '" + code + "'";
        return configMapper.getConfigByCode(code);
    }
}