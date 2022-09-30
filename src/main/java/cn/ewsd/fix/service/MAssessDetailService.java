package cn.ewsd.fix.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.fix.model.MAssessDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-13 15:11:55
 */
public interface MAssessDetailService extends FixBaseService<MAssessDetail, String> {
	List<MAssessDetail> getBackAssessList(String assMonth, String userTeam, String teamQry, String prjQry, String mngTeamQry);

	void dealBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void saveBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void submitBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void backBackAss(HttpServletRequest request, UserInfo userInfo) throws Exception;
}
