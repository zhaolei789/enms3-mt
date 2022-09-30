package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.*;
import cn.ewsd.system.model.DicItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface DataService extends MaterialBaseService<MSettleItem, String> {

    List<MSettleItem> getData(String userTeam, String occMonth);

    List<MSettleItem> getItem(String userTeam);

    void save(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void saveData(HttpServletRequest request, UserInfo userInfo) throws Exception;

    List<MSettleItem> getDayList(String occMonth, String itemNo);
}
