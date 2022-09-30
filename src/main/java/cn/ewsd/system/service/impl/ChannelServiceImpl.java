package cn.ewsd.system.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.system.mapper.ChannelMapper;
import cn.ewsd.system.model.Channel;
import cn.ewsd.system.service.ChannelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * @author 佐佑科技(service@ewsd.cn)
 * @date 2016-10-08
 */
@SuppressWarnings({"unchecked"})
@Service("channelService")
public class ChannelServiceImpl extends SystemBaseServiceImpl<Channel, String> implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Resource
    private ChannelService channelService;










    @Autowired
    private ServletContext servletContext;

    // http://blog.csdn.net/newstruts/article/details/18668269
    // http://www.cnblogs.com/xiohao/p/5279581.html
    // @PostConstruct
    public void init(ServletContext servletContext) {
        this.servletContext = servletContext;
        List<Channel> channels = channelService.selectAll();
        for (Channel channel : channels) {
            servletContext.setAttribute(channel.getCode() + "Channel", channel);
        }
        //"FROM Channel WHERE pid = '41' AND levelId = '2' ORDER BY sort ASC"
        List<Channel> menuChannels = channelService.getListByPidAndLevelIdString(41,2);
        servletContext.setAttribute("menuChannels", menuChannels);

        //"FROM Channel WHERE pid = '16' AND levelId = '2' ORDER BY sort ASC"
        List<Channel> portalChannels = channelService.getListByPidAndLevelIdString(16,2);
        servletContext.setAttribute("portalChannels", portalChannels);
    }


    @Override
    public void cacheChannel() throws Exception {
        //所有栏目
        List<Channel> channels = channelService.selectAll();
        for (Channel channel : channels) {
            servletContext.setAttribute(channel.getCode() + "Channel", channel);
        }

        //门户网站菜单
      //  FROM Channel WHERE status <> '0' AND pid = '41' AND levelId = '2' ORDER BY sort ASC
        List<Channel> menuChannels = channelService.getListByStatusAndPidAndLevelId(0,41,2);
        servletContext.setAttribute("menuChannels", menuChannels);
    }

//    @Override
//    public String getTextById(Integer id) {
//        //SELECT text FROM Channel WHERE id = '" + id + "'
//        return channelService.getextById(id);
//    }

//    @Override
//    public String getChildIdsById(Integer id) {
//        return channelService.callProAndReturn("{call p_get_child_ids(?,?,?)}", "sys_channel", id, "@idStr");
//    }

    @Override
    public List<Channel> getListById(Integer id) {
        return channelMapper.getListById(id);
    }

    @Override
    public List<Channel> getListByPidAndLevelId(String pid, int levelId) {
        return channelMapper.getListByPidAndLevelId(pid,levelId);
    }

    @Override
    public String getChildIds(Map map) {
        return channelMapper.getChildIds(map);
    }

    @Override
    public PageSet<Channel> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<Channel> list = channelMapper.getPageSet(filterSort);
        PageInfo<Channel> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public List<Channel> getListByLevelId(String levelId) {
        return channelMapper.getListByLevelId(levelId);
    }

    @Override
    public String getextById(Integer id) {
        return null;
    }


    @Override
    public List<Channel> getListByStatusAndPidAndLevelId(int i, int i1, int i2) {
        return channelMapper.getListByStatusAndPidAndLevelId(i,i1,i2);

    }

    @Override
    public List<Channel> getListByPidAndLevelIdString(int i, int i1) {
        return channelMapper.getListByPidAndLevelIdString(i,i1);
    }

    @Override
    public String getChildIdsById(int i) {
        return channelMapper.getChildIdsById(i);
    }

    @Override
    public List<Channel> getListByPid(int i) {
        return channelMapper.getListByPid(i);
    }
}