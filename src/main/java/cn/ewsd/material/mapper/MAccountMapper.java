package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MAccount;
import cn.ewsd.material.model.MAccountMat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MAccountMapper extends tk.mybatis.mapper.common.Mapper<MAccount>{
    List<MAccount> getAccountList(@Param("teamQry") String teamQry, @Param("month") String month, @Param("lastMonth") String lastMonth);

    int updateAccount(MAccount mAccount);

    int insertAccount(MAccount mAccount);

    int deleteAccount(@Param("occMonth") String occMonth, @Param("teamNo") String teamNo);

    List<MAccount> getMatAssTyResultList(String occMonth);
}
