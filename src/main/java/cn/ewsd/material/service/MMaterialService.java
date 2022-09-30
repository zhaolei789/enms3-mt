package cn.ewsd.material.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.*;
import cn.ewsd.system.model.DicItem;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MMaterialService extends MaterialBaseService<MMaterial, String> {

    PageSet<MMaterial> getPageSet(PageParam pageParam, String filterSort);

	void executeSave(MMaterial mMaterial, MErpMaterial mErpMaterial, MMakeMatDept mMakeMatDept) throws Exception;

	List<DicItem> getOldTeamForSelect();

	List<DicItem> getFixTeamForSelect();

	String getNewSequenceByMatCode(String matCode);

	MMaterial queryObject(String uuid);

	void executeUpdate(MMaterial mMaterial, MMakeMatDept mMakeMatDept) throws Exception;

	List<MFactoryAddr> getFactoryAddr();
	
	int executeDeleteBatch(String[] uuids);

	PageSet<MMaterial> getMatPageSet(PageParam pageParam, String filterSort, String factoryNo, String itemNo, String planType, String prjNo, String planMonth, String matCodeQry, String matTypeQry, String matNameQry, String lastPlan, String lastMonth);

	List<MMaterial> getMatListForSel(String q, String newOld);

	MMaterial getMatByNo(String matNo);

	PageSet<MMaterial> getFixTaskPageSet(PageParam pageParam, String filterSort, String userTeam, String matQry);

	List<MMaterial> getOldMatList();

	List<MMaterial> getMaterialName(String matType, String query);

	MMaterial getMatByCode(String matCode);
}
