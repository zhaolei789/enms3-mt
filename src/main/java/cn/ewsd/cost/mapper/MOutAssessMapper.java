package cn.ewsd.cost.mapper;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.mdata.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface MOutAssessMapper extends tk.mybatis.mapper.common.Mapper<MOutAssess> {

    List<MOutAssess> getDetailUpDataList(@Param("filterSort") String filterSort, @Param("monthQry") String monthQry, @Param("teamQry") String teamQry, @Param("storeQry") String storeQry,
                                         @Param("addrQry") String addrQry, @Param("purchaseQry") String purchaseQry, @Param("reserveNoQry") String reserveNoQry, @Param("drawSrcQry") String drawSrcQry,
                                         @Param("itemNameQry") String itemNameQry, @Param("matQry") String matQry, @Param("ifMngDept") boolean ifMngDept, @Param("matMngTeam") String matMngTeam);

    List<MOutAssess> getItemList(String monthQry);

    List<MOutAssess> getItemForSelect(@Param("teamNo") String teamNo, @Param("ifMngDept") boolean ifMngDept);

    List<MOutAssess> getPrjForSelect(String teamNo);

    int insertMOutAssess(MOutAssess mOutAssess);

    int insertDetailUpData(@Param("month") String month, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    int updateJDK(@Param("mngTeam") String mngTeam, @Param("month") String month);

    int updateZJCL(@Param("month") String month, @Param("itemName") String itemName);

    int updateRHY(@Param("month") String month, @Param("itemName") String itemName);

    int updateZHCL(String month);

    int updateDC(String month);

    int updateTYCL(String month);

    int updatePrj(String month);

    int deleteDetailUpDataAll(@Param("month") String month, @Param("drawSrcQry") String drawSrcQry, @Param("teamQry") String teamQry);

    int updateMOutAssess(MOutAssess mOutAssess);

    List<Organization> getOutAssessDept(String month);

    List<MOutAssess> getMatAssessOccList(@Param("month1") String month1, @Param("month2") String month2, @Param("teamNo") String teamNo);
}
