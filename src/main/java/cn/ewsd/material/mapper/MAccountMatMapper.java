package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MAccountMat;
import org.apache.ibatis.annotations.Param;

public interface MAccountMatMapper extends tk.mybatis.mapper.common.Mapper<MAccountMat>{
    MAccountMat getAccountMat(@Param("teamNo") String teamNo, @Param("matNo") String matNo);
}
