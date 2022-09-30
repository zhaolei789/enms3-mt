package cn.ewsd.base.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 数据容器，存储查询数据结果
 * 
 * @author 冯俊伟
 * @since 1.0.0
 * @version 1.0.0
 */
public class DataCantainer<T> {
	private ArrayList<T> dataList = new ArrayList<T>();
	private String[] columnNames;
	private int[] columnTypes;
	private int columnCount = 0;
	private int rowCount = 0;
	private int totalCount = 0;
	private int startIndex = 1;
	private int itemNum = 50000;

	public static final String KEY_FLAG = "|$*&@|";

	/**
	 * 初始化数据方法
	 *
	 * @since 1.0.0
	 */
	public DataCantainer(ArrayList<T> dataList){
	    this.dataList = dataList;
	    this.rowCount = dataList.size();
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	/**
	 * 设置分页起始索引
	 *
	 * @param startIndex 起始索引
	 * @since 1.0.0
	 * @version 1.0.0
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

    /**
     * 获取提取记录的起始行号
     * <br>行号从1开始
     */
    public int getStartIndex() {
        return startIndex;
    }

	/**
	 * 获取记录数
	 *
	 * @since 1.0.0
	 * @return 记录数
	 */
	public int getRowCount(){
		return rowCount;
	}

	/**
	 * 获取总记录数
	 *
	 * @since 1.0.0
	 * @return 总记录数
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 获取指定行记录
	 *
	 * @param index 行索引
	 * @return 当前行数据
	 * @since 1.0.0
	 */
	public T getRow(int index){
		return dataList.get(index);
	}

	/**
	 * 根据条件键值对筛选匹配生成新的DataCantainer
	 *
	 * @param conditionMap 条件键值对
	 * @return 符合条件的DataCantainer
	 * @since 1.0.0
	 */
	public DataCantainer<T> findDataCantainer(HashMap<String, String> conditionMap){
		DataCantainer<T> dc = new DataCantainer<T>(new ArrayList<T>());

		dc.columnCount = columnCount;
		dc.columnNames = columnNames;
		dc.dataList = new ArrayList<T>();

		try {
			for(int i = 0; i < rowCount; i++){
				boolean isEqual = true;
				T bean = dataList.get(i);
				Iterator<String> it = conditionMap.keySet().iterator();
				while(it.hasNext()){
					String columnName = it.next();
					String value = conditionMap.get(columnName);
					Class<?> c = bean.getClass();
					Method m = c.getMethod("get" + convertColumnName(columnName, true));
					if(!value.equals(""+m.invoke(bean))){
						isEqual = false;
						break;
					}
				}
				if(isEqual){
					dc.dataList.add(bean);
				}
			}
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		dc.rowCount = dc.dataList.size();
		dc.totalCount = dc.dataList.size();

		return dc;
	}

	/**
	 * 根据条件键值对筛选匹配生成新的DataCantainer
	 *
	 * @param conditionField 条件字段
	 * @param conditionValue 条件字段值
	 * @return 符合条件的DataCantainer
	 * @since 1.0.0
	 */
	public DataCantainer<T> findDataCantainer(String conditionField, String conditionValue){
		HashMap<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(conditionField, conditionValue);
		return findDataCantainer(conditionMap);
	}

	/**
	 * 根据条件键值对筛选匹配生成新的DataCantainer
	 *
	 * @param conditionField1 条件字段1
	 * @param conditionValue1 条件字段值1
	 * @param conditionField2 条件字段2
	 * @param conditionValue2 条件字段值2
	 * @return 符合条件的DataCantainer
	 * @since 1.0.0
	 */
	public DataCantainer<T> findDataCantainer(String conditionField1, String conditionValue1, String conditionField2, String conditionValue2){
		HashMap<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put(conditionField1, conditionValue1);
		conditionMap.put(conditionField2, conditionValue2);
		return findDataCantainer(conditionMap);
	}

	/**
	 * 根据条件键值对（索引区间）筛选匹配生成新的DataCantainer
	 *
	 * @param conditionMap 条件键值对
	 * @param indexArr 索引区间列表
	 * @return 符合条件的DataCantainer
	 * @since 1.0.0
	 */
	public DataCantainer<T> findDataCantainer(LinkedHashMap<String, String> conditionMap, ArrayList<int[]> indexArr){
		DataCantainer<T> dc = new DataCantainer<T>(new ArrayList<T>());

		dc.columnCount = columnCount;
		dc.columnNames = columnNames;
		dc.dataList = new ArrayList<T>();

		try {
			for(int i = 0; i < rowCount; i++){
				boolean isEqual = true;
				T bean = dataList.get(i);
				Iterator<String> it = conditionMap.keySet().iterator();
				int count = 0;
				while(it.hasNext()){
					String columnName = it.next();
					String value = conditionMap.get(columnName);
					Class<?> c = bean.getClass();
					Method m = c.getMethod("get" + convertColumnName(columnName, true));
					int[] indexs = indexArr.get(count);
					String val = m.invoke(bean).toString().substring(indexs[0], indexs[1]);
					count++;
					if(!value.equals(val)){
						count = 0;
						isEqual = false;
						break;
					}
				}
				if(isEqual){
					dc.dataList.add(bean);
				}
			}
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		dc.rowCount = dc.dataList.size();
		dc.totalCount = dc.dataList.size();

		return dc;
	}

	/**
	 * 根据条件键值对（索引区间）筛选匹配生成新的DataCantainer
	 *
	 * @param conditionField 条件字段
	 * @param conditionValue 条件字段值
	 * @param indexs 索引区间
	 * @return 符合条件的DataCantainer
	 * @since 1.0.0
	 */
	public DataCantainer<T> findDataCantainer(String conditionField, String conditionValue, int[] indexs){
		LinkedHashMap<String, String> conditionMap = new LinkedHashMap<String, String>();
		conditionMap.put(conditionField, conditionValue);
		ArrayList<int[]> indexArr = new ArrayList<int[]>();
		indexArr.add(indexs);
		return findDataCantainer(conditionMap, indexArr);
	}

	/**
	 * 根据条件键值对筛选匹配生成新的DataCantainer
	 *
	 * @param conditionField1 条件字段1
	 * @param conditionValue1 条件字段值1
	 * @param conditionField2 条件字段2
	 * @param conditionValue2 条件字段值2
	 * @param indexs1 索引区间1
	 * @param indexs2 索引区间2
	 * @return 符合条件的DataCantainer
	 * @since 1.0.0
	 */
	public DataCantainer<T> findDataCantainer(String conditionField1, String conditionValue1, String conditionField2, String conditionValue2, int[] indexs1, int[] indexs2){
		LinkedHashMap<String, String> conditionMap = new LinkedHashMap<String, String>();
		conditionMap.put(conditionField1, conditionValue1);
		conditionMap.put(conditionField2, conditionValue2);
		ArrayList<int[]> indexArr = new ArrayList<int[]>();
		indexArr.add(indexs1);
		indexArr.add(indexs2);
		return findDataCantainer(conditionMap, indexArr);
	}

	/**
	 * 根据每组记录数将DataCantainer数据分组
	 *
	 * @param pageCount 每组记录数
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(int pageCount){
		ArrayList<DataCantainer<T>> retList = new ArrayList<DataCantainer<T>>();

		for(int r = 0; r < rowCount;){
			DataCantainer<T> dc = new DataCantainer<T>(new ArrayList<T>());

			dc.columnCount = columnCount;
			dc.columnNames = columnNames;
			dc.dataList = new ArrayList<T>();

			for(int i = 0; i < pageCount && r < rowCount; i++, r++){
				dc.dataList.add(getRow(r));
			}

			dc.totalCount = dc.dataList.size();
			dc.rowCount = dc.dataList.size();

			retList.add(dc);
		}

		return retList;
	}

	/**
	 * 根据字段列表值相等将DataCantainer分组
	 *
	 * @param conditions 字段列表
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(String[] conditions){
		ArrayList<DataCantainer<T>> retList = new ArrayList<DataCantainer<T>>();
		HashMap<String, String> groupMap = new HashMap<String, String>();

		try {
			for(int i = 0; i < rowCount; i++){
				String groupId = "";
				HashMap<String, String> conditionMap = new HashMap<String, String>();
				T o = dataList.get(i);
				Class<?> c = o.getClass();

				for(int j = 0; j < conditions.length; j++){
					Method m = c.getMethod("get" + convertColumnName(conditions[j], true));
					String value = m.invoke(o).toString();
					groupId += value + KEY_FLAG;
					conditionMap.put(conditions[j], value);
				}

				if(!groupMap.containsKey(groupId)){
					retList.add(findDataCantainer(conditionMap));
					groupMap.put(groupId, "");
				}
			}
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return retList;
	}

	/**
	 * 根据字段值相等将DataCantainer分组
	 *
	 * @param conditionField 字段名
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(String conditionField){
		return getGroup(new String[]{conditionField});
	}

	/**
	 * 根据字段列表值相等将DataCantainer分组
	 *
	 * @param conditionField1  字段名1
	 * @param conditionField2 字段名2
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(String conditionField1, String conditionField2){
		return getGroup(new String[]{conditionField1, conditionField2});
	}

	/**
	 * 根据字段列表值（索引区间）相等将DataCantainer分组
	 *
	 * @param conditions 字段列表
	 * @param indexArr 字段匹配索引区间
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(String[] conditions, ArrayList<int[]> indexArr){
		ArrayList<DataCantainer<T>> retList = new ArrayList<DataCantainer<T>>();
		HashMap<String, String> groupMap = new HashMap<String, String>();

		try {
			for(int i = 0; i < rowCount; i++){
				String groupId = "";
				LinkedHashMap<String, String> conditionMap = new LinkedHashMap<String, String>();
				T o = dataList.get(i);
				Class<?> c = o.getClass();

				for(int j = 0; j < conditions.length; j++){
					Method m = c.getMethod("get" + convertColumnName(conditions[j], true));
					String value = m.invoke(o).toString();
					int[] indexs = indexArr.get(j);
					value = value.substring(indexs[0], indexs[1]);
					groupId += value + KEY_FLAG;
					conditionMap.put(conditions[j], value);
				}

				if(!groupMap.containsKey(groupId)){
					retList.add(findDataCantainer(conditionMap, indexArr));
					groupMap.put(groupId, "");
				}
			}
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return retList;
	}

	/**
	 * 根据字段值相等（索引区间）将DataCantainer分组
	 *
	 * @param conditionField 字段名
	 * @param indexs 索引区间
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(String conditionField, int[] indexs){
		ArrayList<int[]> indexArr = new ArrayList<int[]>();
		indexArr.add(indexs);
		return getGroup(new String[]{conditionField}, indexArr);
	}

	/**
	 * 根据字段列表值相等（索引区间）将DataCantainer分组
	 *
	 * @param conditionField1  字段名1
	 * @param conditionField2 字段名2
	 * @param indexs1 索引区间1
	 * @param indexs2 索引区间2
	 * @return 分组后的DataCantainer数组
	 * @since 1.0.0
	 */
	public ArrayList<DataCantainer<T>> getGroup(String conditionField1, String conditionField2, int[] indexs1, int[] indexs2){
		ArrayList<int[]> indexArr = new ArrayList<int[]>();
		indexArr.add(indexs1);
		indexArr.add(indexs2);
		return getGroup(new String[]{conditionField1, conditionField2}, indexArr);
	}

	/**
	 * 根据字段求和
	 *
	 * @param fieldName 字段名
	 * @return 该字段求和结果
	 * @since 1.0.0
	 */
	public double getSum(String fieldName){
		double sum = 0.0;
		try {
			for(int i = 0; i < rowCount; i++){
				Object o = dataList.get(i);
				Class<?> c = o.getClass();
				Method m = c.getMethod("get" + convertColumnName(fieldName, true));
				try {
					sum += Double.parseDouble(m.invoke(o).toString());
				} catch (Exception e) {
					sum += m.invoke(o)==null ? 0 : (double)m.invoke(o);
				}
			}
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return sum;
	}

	/**
	 * 将数据库字段名转换为驼峰命名
	 * 例如：user_name转换为UserName或userName
	 *
	 * @param columnName 要转换的字段名
	 * @param flag 是否将第一个字符转换为大写
	 * @return 转换完后的字段名
	 * @since 1.0.0
	 */
	private String convertColumnName(String columnName, boolean flag){
		String[] colNames = columnName.split("_");
		String fieldName = "";

		for(int j = 0; j < colNames.length; j++){
			fieldName += j==0 & !flag ? colNames[j] : colNames[j].substring(0, 1).toUpperCase() + colNames[j].substring(1);
		}

		return fieldName;
	}

	public ArrayList<T> getList(){
		return dataList;
	}

	/**
	 * 返回JSONArray结构的数据集
	 *
	 * @return JSONArray数据集
	 * @since 1.0.0
	 */
//	public JSONArray getDataJSONArray(){
//		return dataJsonArr;
//	}
}
