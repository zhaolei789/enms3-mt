package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.TdeptType;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-20 11:40:20
 */
public interface UtilService extends MdataBaseService<TdeptType, String> {
	boolean checkColumnDataExist(String tableName, ArrayList<String[]> conditionList);

	int getColumnNumber(String tableName, ArrayList<String[]> conditionList);

	boolean checkRight(String roleIds, int id);

	String getFMonth() throws Exception;

	double getBudgetBala(String prjNo, String payTeam, String planMonth, String itemNo, String prjType);

	double getOccurBala(String payTeam, String prjNo, String itemNo, String fMonth);

	String getBillCode(String tableName, String columnName, String prefix, int length, ArrayList<String[]> conditionList);

	boolean hasCheckRight(String processNo, String userId);
}
