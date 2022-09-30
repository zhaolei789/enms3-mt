package cn.ewsd.system.mapper;
import cn.ewsd.material.model.MOffer;
import cn.ewsd.system.model.TCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface TCheckMapper extends tk.mybatis.mapper.common.Mapper<TCheck> {
    int executeSave(TCheck tCheck);

    List<TCheck> getTCheckByNo(String checkNo);

    List<TCheck> getTCheckList(@Param("checkNos") String checkNos);
}
