package cn.ewsd.fix.service;

import cn.ewsd.fix.model.MFixAssess;
import cn.ewsd.mdata.model.Organization;

import java.util.List;

/**
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2021-05-13 15:11:59
 */
public interface MFixAssessService extends FixBaseService<MFixAssess, String> {
	MFixAssess getFixAssess(String assType, String assMonth, String mngTeam, String teamNo, String prjNo);

	List<Organization> getFixAssessTeam(String assMonth, String userTeam);

	List<Organization> getMngTeam();
}
