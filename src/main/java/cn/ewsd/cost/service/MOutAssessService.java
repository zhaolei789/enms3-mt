package cn.ewsd.cost.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.cost.model.MOutAssess;
import cn.ewsd.mdata.model.Organization;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 矿长
 * 
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-08-31 17:13:54
 */
public interface MOutAssessService extends CostBaseService<MOutAssess, String> {

    PageSet<MOutAssess> getDetailUpDataPageSet(PageParam pageParam, String filterSort, String monthQry, String teamQry, String storeQry, String addrQry, String purchaseQry, String reserveNoQry, String drawSrcQry, String itemNameQry, String matQry, boolean ifMngDept, String matMngTeam);

    List<MOutAssess> getItemList(String monthQry);

    List<MOutAssess> getItemForSelect(String teamNo, boolean ifMngDept);

    List<MOutAssess> getPrjForSelect(String teamNo);

    List<MOutAssess> getDetailUpDataList(String monthQry, String teamQry, String storeQry, String addrQry, String purchaseQry, String reserveNoQry, String drawSrcQry, String itemNameQry, String matQry, boolean ifMngDept, String matMngTeam);

    void insertDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void insDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void deleteDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void saveDetailUpData(HttpServletRequest request, UserInfo userInfo) throws Exception;

    List<Organization> getOutAssessDept(String month);
}
