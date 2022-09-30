package cn.ewsd.system.mapper;
import cn.ewsd.system.model.SysUserDept;
import cn.ewsd.system.model.SysUserQryOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-06-09 17:58:25
 */
public interface SysUserDeptMapper extends tk.mybatis.mapper.common.Mapper<SysUserDept> {
    List<SysUserDept> getPageSet(@Param("filterSort") String filterSort);

    int executeDeleteBatch(Object[] var1);
}
