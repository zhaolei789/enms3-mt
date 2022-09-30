package cn.ewsd.cost.mapper;
import cn.ewsd.common.mapper.BaseMapper;
import cn.ewsd.cost.model.SysUserLikeMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户喜欢的菜单
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-30 09:10:09
 */
public interface SysUserLikeMenuMapper extends tk.mybatis.mapper.common.Mapper<SysUserLikeMenu> {

    //获取分页集
    List<SysUserLikeMenu> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(SysUserLikeMenu var1);

    int executeSave(Map<String, Object> var1);

    //void executeSaveBatch(List<SysUserLikeMenu> var1);

    int executeUpdate(SysUserLikeMenu var1);

    int executeUpdate(Map<String, Object> var1);

    int executeDelete(Object var1);

    int executeDelete(Map<String, Object> var1);

    int executeDeleteBatch(Object[] var1);

    SysUserLikeMenu queryObject(Object var1);

    List<SysUserLikeMenu> queryList(Map<String, Object> var1);

    List<SysUserLikeMenu> queryList(Object var1);

    int queryTotal(Map<String, Object> var1);

    int queryTotal();

    SysUserLikeMenu queryByUserIdAndUri(@Param("user_id") String user_id,@Param("menu_uri") String menu_uri);

    int executeAddClicksNumber(@Param("user_id") String user_id,@Param("menu_uri") String menu_uri);

    int queryTotalByUser(@Param("user_id") String user_id);

    List<SysUserLikeMenu> queryListByUser(@Param("user_id") String user_id);
}
