package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MAskCode;
import cn.ewsd.material.model.MMakeForm;
import cn.ewsd.repository.model.MIn;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MAskCodeService extends MaterialBaseService<MAskCode, String> {
    PageSet<MAskCode> getPageSet(PageParam pageParam, String filterSort, String occDate1Qry, String occDate2Qry, String userTeam, String askNameQry);

    List<MMakeForm> getMakeFormList(String askNo, String matQry);

    void saveAskCode(HttpServletRequest request, UserInfo userInfo, MAskCode mAskCode) throws Exception;

    void deleteAskCode(String askNo) throws Exception;

    void submitAskCode(HttpServletRequest request, UserInfo userInfo) throws Exception;

    List<MAskCode> getCheckIndexList(String userId, String userTeam);

    PageSet<MAskCode> getCheckPageSet(PageParam pageParam, String filterSort, String askTeam, String askMonth, String askStep);

    void checkAskCode(HttpServletRequest request, UserInfo userInfo) throws Exception;

    void backAskCode(HttpServletRequest request, UserInfo userInf) throws Exception;

    PageSet<MAskCode> getMakeBackPageSet(PageParam pageParam, String filterSort, String date1Qry, String addrQry, String matQry, String teamQry, boolean flagQry, String curDate, String stepQry, String day1Qry, String day2Qry, String needTypeQry);

    List<MAskCode> getMakeBackList(String date1Qry, String addrQry, String matQry, String teamQry, boolean flagQry, String curDate, String stepQry, String day1Qry, String day2Qry, String needTypeQry);

    PageSet<MIn> getMakeBackDetailPageSet(PageParam pageParam, String filterSort, String startDateQry, String endDateQry, String matNameQry);

    List<MIn> getMakeBackDetailList(String startDateQry, String endDateQry, String matNameQry);

    void submitMakeBack(HttpServletRequest request, UserInfo userInfo) throws Exception;

    MAskCode getAskByNo(String askNo);

    void updateAsk(HttpServletRequest request, UserInfo userInfo) throws Exception;
}
