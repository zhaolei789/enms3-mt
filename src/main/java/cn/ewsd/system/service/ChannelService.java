package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.Channel;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * @author 佐佑科技(service@ewsd.cn)
 * @date 2016-10-08
 */
public interface ChannelService extends SystemBaseService<Channel, String> {







    /**
     * 初始化Channel
     *
     * @param sc
     */
    public void init(ServletContext sc);

    /**
     * 缓存Channel
     *
     * @throws Exception
     */
    public void cacheChannel() throws Exception;

//    /**
//     * 根据code获得子Channel列表
//     *
//     * @param code
//     * @return
//     */
//    public List<Channel> getChildChannelsByCode(String code);
//
//    /**
//     * 根据code获得兄弟Channel列表
//     *
//     * @param code
//     * @return
//     */
//    public List<Channel> getBrotherChannelsByCode(String code);
//
//    /**
//     * 根据id获得Channel的Text
//     *
//     * @param id
//     * @return
//     */
//    public String getTextById(Integer id);
//
//    /**
//     * 根据id获得以逗号分隔的id字符串,包含id本身
//     *
//     * @param id
//     * @return
//     */
//    public String getChildIdsById(Integer id);

    List<Channel> getListById(Integer id);

    List<Channel> getListByPidAndLevelId(String pid, int levelId);

    String getChildIds(Map map);

    PageSet<Channel> getPageSet(PageParam pageParam, String filterSort);

    List<Channel> getListByLevelId(String levelId);

    String getextById(Integer id);

    List<Channel> getListByStatusAndPidAndLevelId(int i, int i1, int i2);

    List<Channel> getListByPidAndLevelIdString(int i, int i1);

    String getChildIdsById(int i);

    List<Channel> getListByPid(int i);
}