package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MMakeForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MMakeFormMapper extends tk.mybatis.mapper.common.Mapper<MMakeForm> {
    List<MMakeForm> getMakeFormList(@Param("matQry") String matQry, @Param("askNo") String askNo);

    int insertMakeForm(MMakeForm mMakeForm);

    int deleteByAskNo(String askNo);
}
