package cn.ewsd.repository.mapper;
import cn.ewsd.repository.model.MCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MCheckMapper extends tk.mybatis.mapper.common.Mapper<MCheck> {
    List<MCheck> getCheckList(@Param("yearQry") String yearQry, @Param("userId") String userId);

    int insertCheck(MCheck mCheck);

    MCheck getCheckByNo(String checkNo);

    int deleteCheckByNo(String checkNo);

    int updateCheck(MCheck mCheck);

    List<MCheck> getCheckSet(String checkStep);
}
