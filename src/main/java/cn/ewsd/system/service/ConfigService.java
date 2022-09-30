package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Config;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * @author 佐佑科技(service@ewsd.cn)
 * @date 2015-12-09
 */
public interface ConfigService extends SystemBaseService<Config, String> {

    Config selectByPrimaryKey(String pk);

    Integer updateByPrimaryKey(Config config);

    List<Config> getListByAll();

    /**
     * 根据code获得Config
     *
     * @param code
     * @return
     */
    public String getConfigByCode(String code);

    /**
     * 缓存Config
     *
     * @throws Exception
     */
    public void cacheConfig() throws Exception;

    Map<String, Map<String, String>> redisCacheConfig() throws Exception;

    /**
     * 初始化Config
     *
     * @param sc
     */
    public void init(ServletContext sc);

    /**
     * 根据type和key获得Config的value
     *
     * @param type
     * @param key
     * @return
     */
    public String getValue(String type, String key);

    String getConfigValue(String key);

    Integer getMaxSort();

    Config getListByCodeAndUuid(String code, String uuid);

    PageSet<Config> getPageSet(PageParam pageParam, String filterStr);


    List<Config> getDataByTablesAndField(String tables, String field);



    /**
     * @MethodName batchInsert 批量添加
     * @Description TODO
     * @Param config 
     * @Return java.util.List<cn.ewsd.system.model.Config>
     * @Author 朱永敬<zhuyongjing@zuoyour.com>
     * @Date 2019-08-27 18:46
     */
    int batchInsert(List<Config> config);
}