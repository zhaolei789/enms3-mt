package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MFactoryAddr;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.system.model.DicItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MMaterialMapper extends tk.mybatis.mapper.common.Mapper<MMaterial> {

    List<MMaterial> getPageSet(@Param("filterSort") String filterSort);

    int executeSave(MMaterial var1);

    List<MFactoryAddr> getFactoryAddr();

    List<MFactoryAddr> getFactoryAddr1(@Param("factoryNo") String factoryNo, @Param("matAddr") String matAddr);

    List<DicItem> getOldTeamForSelect();

    List<MMaterial> getMatSequenceByMatCode(@Param("matCode")String matCode);

    MMaterial queryObject(Object var1);

    int updateMaterialByMatCode(@Param("matCode")String matCode, @Param("matName")String matName, @Param("matType")String matType, @Param("reFlag")String reFlag,
                                @Param("abcType")String abcType, @Param("packScale") BigDecimal packScale, @Param("useInfo")String useInfo, @Param("matCode1")String matCode1);

    int executeUpdate(MMaterial mMaterial);

    int executeDeleteBatch(Object[] var1);

    List<MMaterial> getMatList1(@Param("factoryNo") String factoryNo, @Param("itemFlag") boolean itemFlag, @Param("itemNo") String itemNo, @Param("planType") String planType,
                                @Param("prjNo") String prjNo, @Param("planMonth") String planMonth, @Param("matCodeQry") String matCodeQry, @Param("matTypeQry") String matTypeQry,
                                @Param("matNameQry") String matNameQry, @Param("lastPlan") String lastPlan, @Param("lastMonth") String lastMonth);

    List<MMaterial> getMatList2(@Param("itemFlag") boolean itemFlag, @Param("itemNo") String itemNo, @Param("planType") String planType, @Param("prjNo") String prjNo,
                                @Param("planMonth") String planMonth, @Param("matCodeQry") String matCodeQry, @Param("matTypeQry") String matTypeQry,
                                @Param("matNameQry") String matNameQry, @Param("lastPlan") String lastPlan, @Param("lastMonth") String lastMonth);

    MMaterial getMatByCode(String matCode);

    MMaterial getMatByNo(String matNo);

    List<MMaterial> getMatListForSel(@Param("q") String q, @Param("newOld") String newOld);

    String getNewMatNo(String matCode);

    String getOldMatNo(String matCode);

    List<MMaterial> getReturnMatList(@Param("month") String month, @Param("teamQry") String teamQry, @Param("matQry") String matQry, @Param("curMonth") String curMonth);

    List<MMaterial> getFixTaskList(@Param("userTeam") String userTeam, @Param("matQry") String matQry);

    List<DicItem> getFixTeamForSelect();

    List<MMaterial> getOldMatList();

    List<MMaterial> getMaterialName(@Param("matType") String matType, @Param("query") String query);

    MMaterial getMaterialByNameOrCode(@Param("matCode") String matCode, @Param("matName") String matName);

    int updateMaterial(MMaterial mMaterial);

    MMaterial getFBMaterial(@Param("matName") String matName, @Param("matNo") String matNo);

    List<MMaterial> getPlanSrc(String matNo);
}
