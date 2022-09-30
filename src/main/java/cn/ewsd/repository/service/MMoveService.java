package cn.ewsd.repository.service;

import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.material.model.WzscPlan;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.model.MMove;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
public interface MMoveService extends RepositoryBaseService<MMove, String> {

	PageSet<MMove> getMoveOrderList(PageParam pageParam, String monthQry);

	int insertMove(String inStore, String outStore) throws Exception;

	int deleteMove(String transNo) throws Exception;

	PageSet<MMaterial> getMoveMatList(PageParam pageParam, String transNo, String outStore, String matCodeQry, String matNameQry);

	MMove getMove(String transNo);

	void saveMat(String transNo, String[] matNos) throws Exception;

	PageSet<MMove> getMoveList(PageParam pageParam, String transNo);

	void deleteMat(String transNo, String[] listNos) throws Exception;

	void saveAmount(String transNo, String[] listNos, HttpServletRequest request) throws Exception;

	void submit(String transNo) throws Exception;

	PageSet<MMove> getMoveCheckList(PageParam pageParam, String userId);

	PageSet<MMove> getCheckList(PageParam pageParam, String transNo);

	void dealMove(String transNo, String[] listNos) throws Exception;

	void backMove(String transNo, String[] listNos) throws Exception;
}
