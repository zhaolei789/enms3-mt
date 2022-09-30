package cn.ewsd.material.service;

import cn.ewsd.base.utils.jwt.UserInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MTypeMat;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MTypeMatService extends MaterialBaseService<MTypeMat, String> {

	PageSet<MTypeMat> getPageSet(PageParam pageParam, String filterSort, String matCodeQry, String matNameQry);

	List<MTypeMat> getMTypeMatList(String matCodeQry, String matNameQry);

	PageSet<MTypeMat> getComPlanPageSet(PageParam pageParam, String filterSort, String planMonth, String matQry);

	void comIns(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void insertTypeMat(MTypeMat mTypeMat) throws Exception;

	void saveTypeMat(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void deleteTypeMat(HttpServletRequest request, UserInfo userInfo) throws Exception;

	void dealTypeMat(HttpServletRequest request, UserInfo userInfo) throws Exception;
}
