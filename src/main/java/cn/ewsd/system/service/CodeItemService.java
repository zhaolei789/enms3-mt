package cn.ewsd.system.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.system.model.CodeItem;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

public interface CodeItemService extends SystemBaseService<CodeItem, String> {
	
//	public Integer saveByEntity(CodeItem codeItem);
//
//	public Integer deleteByUuid(String uuid);
//
//	public Integer updateByEntity(CodeItem codeItem);
//
//	public CodeItem getDetailByPrimaryKey(String uuid);
	
	public String getTextById(String id);
	
	public String getTextByCodesetIdAndId(String codesetId, String id);
	
	public String getTextByCodesetIdAndCode(String codesetId, String code);
	
	public String getCodeByCodeSetIdAndText(String codesetId, String text);
	
	/**
	 * 获得父id字符串
	 * @param codeSetId 代码集
	 * @param id 编号
	 * @param levelId 不需要获取的层级编号
	 * @return
	 */
	public String getFatherIds(String codeSetId, String id, String levelId);
	
	public String recursionFn(String codeSetId, String id, String levelId);

	public void init(ServletContext sc);

	public String getValue(String type, String key);

    PageSet<CodeItem> getPageSet(PageParam pageParam, String filterSort);

	List<CodeItem> getListByLevelId(String filterStr);

	List<CodeItem> getListByCodeSetIdAndLevelId(String codeSetId, String levelId);

	List<CodeItem> getListByPid(String pid);

	List<CodeItem> getListByCodeSetIdAndPid(String codeSetId, String pid);

	List<CodeItem> getListByCodeSetId(String codeSetId);

	List<CodeItem> getListByPidAndLevelId(String pid, int i);

    String getFatherIdsVarchar(Map map);

    Integer cascadeDeleteByUuid(String s);

	CodeItem getCodeItemById(String pid);
}