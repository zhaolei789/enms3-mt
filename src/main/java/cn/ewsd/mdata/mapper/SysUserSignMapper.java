package cn.ewsd.mdata.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.mdata.model.SysUserSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户签名
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-05-06 15:43:29
 */
public interface SysUserSignMapper extends tk.mybatis.mapper.common.Mapper<SysUserSign> {

    //获取分页集
    List<SysUserSign> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserSign var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserSign> var1);

    int executeUpdate(SysUserSign var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserSign queryObject(Object var1);

    List<SysUserSign> queryList(Map<String, Object> var1);

    List<SysUserSign> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    int setAllStatus0(Object var1);

    SysUserSign getUserSign(Object var1);

}
