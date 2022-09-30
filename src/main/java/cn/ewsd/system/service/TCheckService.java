package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.TCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface TCheckService extends SystemBaseService<TCheck, String> {
    public List<TCheck> getTCheckByNo(String checkNo);

    List<TCheck> getTCheckList(String checkNos);
}
