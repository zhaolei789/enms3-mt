package cn.ewsd.system.mapper;
import cn.ewsd.mdata.model.Organization;
import cn.ewsd.system.model.SysUserQryOrg;
import cn.ewsd.system.model.SysUserStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface SysUserQryOrgMapper extends tk.mybatis.mapper.common.Mapper<SysUserQryOrg> {
    List<SysUserQryOrg> getPageSet(@Param("filterSort") String filterSort);

    int executeDeleteBatch(Object[] var1);

    List<SysUserQryOrg> getQryOrgByUserId(String userId);

    List<Organization> getDeptSet(@Param("deptIds") String deptIds, @Param("deptLevel") String deptLevel, @Param("justVirtual") boolean justVirtual);
}
