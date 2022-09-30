package cn.ewsd.cost.mapper;
import cn.ewsd.cost.model.MRation;
import cn.ewsd.material.model.MMaterial;
import freemarker.cache.MruCacheStorage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-09-13 18:44:03
 */
public interface MRationMapper extends tk.mybatis.mapper.common.Mapper<MRation> {
    List<MRation> getRationList(@Param("rationQry") String rationQry);

    int insertRation(MRation mRation);

    MRation getRationByNo(String rationNo);

    int updateRation(MRation mRation);

    int deleteRation(@Param("rationNos") String rationNos);

    int deleteRationMat(@Param("rationNos") String rationNos);

    List<MMaterial> getRationMatList(@Param("rationNo") String rationNo, @Param("matQry") String matQry);

    List<MMaterial> getMatForSelect(String q);

    int deleteRationMat1(@Param("rationNo") String rationNo, @Param("matNos") String[] matNos);

    int insertRationMat(@Param("matNo") String matNo, @Param("rationNo") String rationNo);
}
