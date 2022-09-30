package cn.ewsd.base.utils;

import cn.ewsd.base.bean.PageData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.DatabaseMetaData;

public class DbUtil {

	private static DbUtil dbUtil = new DbUtil();
	public static DbUtil getDbUtil(){
		return dbUtil;
	}

	/**获取本数据库的所有表名(通过PageData)
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Object[] getTables(PageData pd) throws ClassNotFoundException, SQLException{
		String dbtype = pd.getString("dbtype");				//数据库类型
		String username = pd.getString("username");			//用户名
		String password = pd.getString("password");			//密码
		String address = pd.getString("dbAddress");			//数据库连接地址
		String dbport = pd.getString("dbport");				//端口
		String databaseName = pd.getString("databaseName");	//数据库名
		Connection conn = DbUtil.getCon(dbtype,username,password,address+":"+dbport,databaseName);
		if("oracle".equals(dbtype)){databaseName = username.toUpperCase();}
		Object[] arrOb = {databaseName,DbUtil.getTablesByCon(conn, "sqlserver".equals(dbtype)?null:databaseName),dbtype};
		return arrOb;
	}

	/**
	 * @return 获取conn对象(通过配置文件)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getKRCon() throws ClassNotFoundException, SQLException{
		String dbtype = "mysql";				//数据库类型
		//String address = "localhost";			//数据库连接地址
		//String username = "krmis";			//用户名
		//String password = "JN2coal@";			//密码
		//String address = "139.186.10.59";			//数据库连接地址
		//String username = "root";			//用户名
		//String password = "root";			//密码
		String address = PropertiesUtils.getBootstrapYml("server.database-host").toString();
		String username = PropertiesUtils.getBootstrapYml("spring.datasource.default.username").toString();//用户名
		String password = PropertiesUtils.getBootstrapYml("spring.datasource.default.password").toString();//密码
		String dbport = "3306";				//端口
		String databaseName = "krmis-xg";	//数据库名
		return DbUtil.getCon(dbtype,username,password,address+":"+dbport,databaseName);
	}

	/**
	 * @return 获取conn对象(通过PageData)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getKRCon(PageData pd) throws ClassNotFoundException, SQLException{
		String dbtype = pd.getString("dbtype");				//数据库类型
		String username = pd.getString("username");			//用户名
		String password = pd.getString("password");			//密码
		String address = pd.getString("dbAddress");			//数据库连接地址
		String dbport = pd.getString("dbport");				//端口
		String databaseName = pd.getString("databaseName");	//数据库名
		return DbUtil.getCon(dbtype,username,password,address+":"+dbport,databaseName);
	}

	/**
	 * @param dbtype	数据库类型
	 * @param username	用户名
	 * @param password	密码
	 * @param dburl		数据库连接地址:端口
	 * @param databaseName 数据库名
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getCon(String dbtype,String username,String password,String dburl,String databaseName) throws SQLException, ClassNotFoundException{
		if("mysql".equals(dbtype)){
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://"+dburl+"/"+databaseName+"?user="+username+"&password="+password+"&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
			//String url = "jdbc:mysql://139.186.10.59:3306/krmis?user=root&password=JN2coal@&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
			return DriverManager.getConnection(url);
		}else if("oracle".equals(dbtype)){
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@"+dburl+":"+databaseName, username, password);
		}else if("sqlserver".equals(dbtype)){
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection("jdbc:sqlserver://"+dburl+"; DatabaseName="+databaseName, username, password);
		}else{
			return null;
		}
	}

	/**获取某个conn下的所有表
	 * @param conn 数据库连接对象
	 * @param schema mysql:数据库名; oracle:用户名;sqlserver:null
	 * @return
	 */
	public static List<String> getTablesByCon(Connection conn, String schema) {
		try {
			List<String> listTb = new ArrayList<String>();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, schema, null, new String[] { "TABLE" });
			while (rs.next()) {
				listTb.add(rs.getString(3));
			}
			return listTb;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**动态读取数据记录
	 * @param sql 查询语句
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Object[] executeQueryKR(String sql) throws Exception{
		List<String> columnList = new ArrayList<String>();				//存放字段名
		List<List<Object>> dataList = new ArrayList<List<Object>>();	//存放数据(从数据库读出来的一条条的数据)
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = DbUtil.getKRCon();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		columnList = DbUtil.getFieldLsit(conn,sql);
		while(rs.next()){
			List<Object> onedataList = new ArrayList<Object>(); 		//存放每条记录里面每个字段的值
			for(int i =1;i<columnList.size()+1;i++){
				onedataList.add(rs.getObject(i));
			}
			dataList.add(onedataList);									//把每条记录放list中
		}
		Object[] arrOb = {columnList,dataList};
		conn.close();
		return arrOb;
	}

	/**
	 * 动态查询对象
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static PageData executeQueryObject(String sql) throws Exception{
		List<String> columnList = new ArrayList<String>();				//存放字段名
		List<List<Object>> dataList = new ArrayList<List<Object>>();	//存放数据(从数据库读出来的一条条的数据)
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = DbUtil.getKRCon();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		columnList = DbUtil.getFieldLsit(conn,sql);
		while(rs.next()){
			List<Object> onedataList = new ArrayList<Object>(); 		//存放每条记录里面每个字段的值
			for(int i =1;i<columnList.size()+1;i++){
				onedataList.add(rs.getObject(i));
			}
			dataList.add(onedataList);									//把每条记录放list中
		}
		Object[] arrOb = {columnList,dataList};
		conn.close();
		PageData res = new PageData();
		if(dataList.size()<1) {
			return null;
		}
		if(null != arrOb) {
			List<Object> Tdata = dataList.get(0);
			for (int j = 0; j < Tdata.size(); j++) {
				res.put(columnList.get(j), Tdata.get(j));
			}
		}
		return res;
	}

	/**
	 * 动态查询列表
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<PageData> executeQueryList(String sql) throws Exception{
		List<String> columnList = new ArrayList<String>();				//存放字段名
		List<List<Object>> dataList = new ArrayList<List<Object>>();	//存放数据(从数据库读出来的一条条的数据)
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = DbUtil.getKRCon();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		columnList = DbUtil.getFieldLsit(conn,sql);
		while(rs.next()){
			List<Object> onedataList = new ArrayList<Object>(); 		//存放每条记录里面每个字段的值
			for(int i =1;i<columnList.size()+1;i++){
				onedataList.add(rs.getObject(i));
			}
			dataList.add(onedataList);									//把每条记录放list中
		}
		Object[] arrOb = {columnList,dataList};
		conn.close();
		List<PageData> Dbres =new ArrayList<PageData>();
		if(null != arrOb){
			for (int i = 0; i < dataList.size(); i++) {
				PageData resPd = new PageData();
				List<Object> Tdata = dataList.get(i);
				for (int j = 0; j < Tdata.size(); j++) {
					resPd.put(columnList.get(j), Tdata.get(j));
				}
				Dbres.add(resPd);
			}
		}
		return Dbres;
	}


	/**执行 INSERT、UPDATE 或 DELETE
	 * @param sql 语句
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void executeUpdateKR(String sql) throws ClassNotFoundException, SQLException{
		Statement stmt = null;
		Connection conn = null;
		conn = DbUtil.getKRCon();
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.close();
	}

	/**
	 * 执行INSERT、UPDATE 或 DELETE
	 * @param sql
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int executeUpdateKR2(String sql) throws ClassNotFoundException, SQLException{
		int resNum = 0;
		Statement stmt = null;
		Connection conn = null;
		conn = DbUtil.getKRCon();
		stmt = conn.createStatement();
		resNum = stmt.executeUpdate(sql);
		conn.close();
		return resNum;
	}

	/**字段名列表
	 * @param conn
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getFieldLsit(Connection conn, String table) throws SQLException{
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(table);
		pstmt.execute();  									//这点特别要注意:如果是Oracle而对于mysql可以不用加.
		List<String> columnList = new ArrayList<String>();	//存放字段
		ResultSetMetaData rsmd = (ResultSetMetaData) pstmt.getMetaData();
		for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
			columnList.add(rsmd.getColumnName(i));			//把字段名放list里
		}
		return columnList;
	}

	/**(字段名、类型、长度)列表
	 * @param conn
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String,String>> getFieldParameterLsit(Connection conn, String table, String dbtype) throws SQLException{
		if("oracle".equals(dbtype)){
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement("select * from " + table);
			pstmt.execute();
			PreparedStatement pstmtc = (PreparedStatement) conn.prepareStatement("select * from user_col_comments where Table_Name='" + table + "'");
			pstmtc.execute();
			List<Map<String,String>> columnList = new ArrayList<Map<String,String>>();	//存放字段
			ResultSetMetaData rsmd = (ResultSetMetaData) pstmt.getMetaData();
			ResultSet rs = pstmtc.getResultSet();
			List<Map<String,String>> commentList = new ArrayList<Map<String,String>>();	//字段的注释
			while (rs.next()) {
				Map<String,String> cmap = new HashMap<String, String>();
				cmap.put("COLUMN_NAME",rs.getString("COLUMN_NAME"));				//字段名称
				cmap.put("COMMENTS", rs.getString("COMMENTS"));						//字段注释备注
				commentList.add(cmap);
			}
			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				Map<String,String> fmap = new HashMap<String,String>();
				String columnName = rsmd.getColumnName(i);								//字段名称
				fmap.put("fieldNanme", columnName);
				fmap.put("fieldType", rsmd.getColumnTypeName(i));						//字段类型名称
				fmap.put("fieldLength", String.valueOf(rsmd.getColumnDisplaySize(i)));	//长度
				fmap.put("fieldSccle", String.valueOf(rsmd.getScale(i)));				//小数点右边的位数
				for(int n = 0;n < commentList.size(); n++){
					if(columnName.equals(commentList.get(n).get("COLUMN_NAME").toString())){
						String	fieldComment = "备注"+i;
						if(null != commentList.get(n).get("COMMENTS")){
							fieldComment = commentList.get(n).get("COMMENTS").toString();
							fieldComment = "".equals(fieldComment.trim())?"备注"+i:fieldComment;
						}
						fmap.put("fieldComment", fieldComment);						//字段注释备注
					}
				}
				columnList.add(fmap);													//把字段名放list里
			}
			return columnList;
		}else if("mysql".equals(dbtype)){
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement("show full fields from " + table);
			pstmt.execute();  															//这点特别要注意:如果是Oracle而对于mysql可以不用加.
			List<Map<String,String>> columnList = new ArrayList<Map<String,String>>();	//存放字段
			ResultSet rs = pstmt.getResultSet();
			while (rs.next()) {
				Map<String,String> fmap = new HashMap<String,String>();
				fmap.put("fieldNanme", rs.getString("Field"));							//字段名称
				//截取
				String typeStr = rs.getString("Type"); //varchar(30)
				String typName = "", length = "0", sccle = "0";
				if(typeStr.indexOf("(") != -1 && typeStr.indexOf("") != -1){
					typName = typeStr.substring(0, typeStr.indexOf("("));
					String numStr = typeStr.substring(typeStr.indexOf("(")+1, typeStr.indexOf(")"));
					if(numStr.contains(",")){ //3,3
						length = numStr.split(",")[0];
						sccle = numStr.split(",")[1];
					}else {
						length = numStr;
					}
				} else {
					typName = typeStr;
				}
				fmap.put("fieldType", typName);								//字段类型名称
				fmap.put("fieldLength", length);								//长度
				fmap.put("fieldSccle", sccle);									//小数点右边的位数
				fmap.put("fieldComment", rs.getString("Comment"));				//字段注释
				columnList.add(fmap);
			}
			return columnList;
		}else{ //sqlserver类型
			String sql = "SELECT "+
					"CONVERT(varchar(200),B.name) AS column_name,"+
					"CONVERT(varchar(200),C.value) AS column_description"+
					" FROM sys.tables A"+
					" INNER JOIN sys.columns B ON B.object_id = A.object_id"+
					" LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id"+
					" WHERE A.name = '"+table+"'";
			PreparedStatement fullpstmt = (PreparedStatement) conn.prepareStatement(sql);
			fullpstmt.execute();
			ResultSet rs = fullpstmt.getResultSet();
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement("select * from " + table);
			pstmt.execute();  															//这点特别要注意:如果是Oracle而对于mysql可以不用加.
			List<Map<String,String>> columnList = new ArrayList<Map<String,String>>();	//存放字段
			ResultSetMetaData rsmd = (ResultSetMetaData) pstmt.getMetaData();
			for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
				Map<String,String> fmap = new HashMap<String,String>();
				fmap.put("fieldComment","");
				fmap.put("fieldNanme", rsmd.getColumnName(i));							//字段名称
				while (rs.next()) {
					if(rsmd.getColumnName(i).equals(rs.getString("column_name"))){
						fmap.put("fieldComment",rs.getString("column_description")==null?"":rs.getString("column_description"));//字段注释
						break;
					}
				}
				fmap.put("fieldType", rsmd.getColumnTypeName(i));						//字段类型名称
				fmap.put("fieldLength", String.valueOf(rsmd.getColumnDisplaySize(i)));	//长度
				fmap.put("fieldSccle", String.valueOf(rsmd.getScale(i)));				//小数点右边的位数
				columnList.add(fmap);													//把字段名放list里
			}
			return columnList;
		}
	}




}
