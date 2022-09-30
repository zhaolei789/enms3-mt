package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.MStock;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MStockMapper extends tk.mybatis.mapper.common.Mapper<MStock> {
    int getCountByMatNo(String matNo);

    List<MMaterial> getMaterialInStockByStoreNo(String storeNo);

    List<MStock> getTgAmount();

    List<MStock> getSfAmount();

    int updateStock(MStock mStock);

    int insertStock(MStock mStock);

    MStock getStock(@Param("storeNo") String storeNo, @Param("matNo") String matNo);

    List<MStock> getKKXL(String matNo);

    double getStockAmount(String matNo);

    List<MMaterial> getStockPackMat(@Param("userId") String userId, @Param("storeQry") String storeQry, @Param("flagQry") String flagQry, @Param("matQry") String matQry);

    List<MStock> getStockPackStore(String matNo);

    List<MStock> getStockByStoreNo(String storeNo);

    int updStock(@Param("inAmount") BigDecimal inAmount, @Param("stockAmount") BigDecimal stockAmount, @Param("bulkAmount") BigDecimal bulkAmount, @Param("siteCode") String siteCode,
                 @Param("storeNo") String storeNo, @Param("matNo") String matNo);

    int updStock1(@Param("inAmount") BigDecimal inAmount, @Param("stockAmount") BigDecimal stockAmount, @Param("bulkAmount") BigDecimal bulkAmount, @Param("siteCode") String siteCode,
                  @Param("storeNo") String storeNo, @Param("matNo") String matNo);

    List<MStock> getDxStockList(@Param("storeNoQry") String storeNoQry, @Param("flagQry") String flagQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MStock> getStockQryList(@Param("storeNoQry") String storeNoQry, @Param("abcTypeQry") String abcTypeQry, @Param("flagQry") String flagQry, @Param("ifPower") boolean ifPower,
                                 @Param("matCodeQry") String matCodeQry, @Param("erpTypeQry") String erpTypeQry, @Param("matNameQry") String matNameQry, @Param("warnFlag") String warnFlag);

    List<MStock> getOldApplyList(@Param("storeQry") String storeQry, @Param("matQry") String matQry);

    List<MMaterial> getUrgentPlanStock(@Param("teamNo") String teamNo, @Param("planMonth") String planMonth, @Param("storeQry") String storeQry,
                                       @Param("reserveQry") String reserveQry, @Param("matQry") String matQry);

    double getPlanIosStockAmount(@Param("reserveNo") String reserveNo, @Param("storeNo") String storeNo, @Param("matNo") String matNo);

    List<MStock> getWarnStock(@Param("warnCol") String warnCol, @Param("storeQry") String storeQry, @Param("flagQry") String flagQry, @Param("codeQry") String codeQry, @Param("nameQry") String nameQry);

    List<MStock> getInOutStock(@Param("userId") String userId, @Param("storeQry") String storeQry, @Param("ifPower") boolean ifPower, @Param("beginDateQry") String beginDateQry,
                               @Param("endDateQry") String endDateQry, @Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry);

    List<MStock> getAllStockQryList(@Param("storeNoQry") String storeNoQry, @Param("stLevelQry") String stLevelQry, @Param("ifPower") boolean ifPower,
                                    @Param("matCodeQry") String matCodeQry, @Param("erpTypeQry") String erpTypeQry, @Param("matNameQry") String matNameQry);

    List<MStock> getStockSumList(@Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry, @Param("xjFlagQry") String xjFlagQry, @Param("ifPower") boolean ifPower);

    List<HashMap<String, Object>> getStockSumMat(@Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry, @Param("xjFlagQry") String xjFlagQry, @Param("ifPower") boolean ifPower);

    List<MStock> getStockSumDetailList(@Param("matCodeQry") String matCodeQry, @Param("matNameQry") String matNameQry, @Param("xjFlagQry") String xjFlagQry, @Param("storeNoQry") String storeNoQry);

    List<MStock> getExpirationAlertList(@Param("matQry") String matQry, @Param("nowDate") String nowDate);
}
