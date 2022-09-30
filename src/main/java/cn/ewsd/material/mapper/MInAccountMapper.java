package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MInAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MInAccountMapper extends tk.mybatis.mapper.common.Mapper<MInAccount> {
    List<MInAccount> getSumSetBalaGroupByOrderNo();

    List<MInAccount> getInAccountList(@Param("yearQry") String yearQry, @Param("monQry") String monQry, @Param("numberQry") String numberQry, @Param("orderQry") String orderQry);

    int insertInAccount(MInAccount mInAccount);

    int deleteByUuid(String uuid);
}
