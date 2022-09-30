package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MHandIn;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.mdata.model.Organization;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
public interface MHandInService extends MaterialBaseService<MHandIn, String> {
    PageSet<MHandIn> getPageSet(PageParam pageParam, String filterSort, String date1Qry, String date2Qry, String teamNoQry, String matCodeQry, String matNameQry);

	List<Organization> getTeamSet(String date1Qry, String date2Qry, String teamNoQry, String matCodeQry, String matNameQry);

	List<MHandIn> getList(String date1Qry, String date2Qry, String teamNoQry, String matCodeQry, String matNameQry);

	List<MMaterial> getMatList(String q, String userTeam);

	void saveOldMatReg(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void delOldMatReg(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void updateOldMatReg(HttpServletRequest request, UserInfo userInfo) throws Exception;

	PageSet<MHandIn> getOldMatRegDetailPageSet(PageParam pageParam, String filterSort, String monthQry, String userDeptIds, String teamNoQry, String matCodeQry, String matNameQry);

	PageSet<MHandIn> getRetDetailPageSet(PageParam pageParam, String filterSort, String flagQry, String userDeptIds, String date1Qry, String date2Qry, String teamNoQry, String typeQry, String matQry);

	List<MHandIn> getRetDetailList(String flagQry, String userDeptIds, String date1Qry, String date2Qry, String teamNoQry, String typeQry, String matQry);
}
