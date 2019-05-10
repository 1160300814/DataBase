package three;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 测试用，正常运行不用它
 * @author jsq
 *
 */
public class test {
	public static final String url = "jdbc:mysql://localhost:3306/DB?"
			+ "useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";// &allowPublicKeyRetrieval=true";
																							// //
																							// 程序要连接的数据库和要使用的JDBC驱动程序,默认端口号3306
	public static final String name = "com.mysql.cj.jdbc.Driver"; // 指定连接类型
	public static final String user = "root"; // 程序连接时所用的数据库用户名
	public static final String password = "1160300814";
	public static Connection con = null;
	public static Statement pst = null;
	private static String sql = new String();
	// private static ResultSet ret = null;

	/**
	 * 链接数据库
	 */
	public static void Con() {
		try {
			Class.forName(name).newInstance();// 指定连接类型
			con = DriverManager.getConnection(url, user, password);// 获取连接
			pst = con.createStatement();// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭连接
	 */
	public static void close() {
		try {
			con.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//测试插入
		 try {
			Con();
			sql = "INSERT INTO WORKS_ON VALUES (\'" + "12351"  + "\',\'" + 5 + "\');";

			System.out.println(sql);
			System.out.println(pst.executeUpdate(sql));
			// System.out.println("-----------------+-------------------------");
			close();
		} catch (Exception e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("error:\t"+e.getMessage());
			close();
		}
		//测试视图
//		try {
//			testView();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//测试删除
	/*	try {
			sql = "delete from WORKS_ON where " + "ESSN" + " = \'" + "12351" + "\';";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Delete Insert!------");
			close();
		} catch (SQLException e) {
			System.out.println("Error:\t" + e.getMessage());
			close();
		}*/
	}

	public static void testView() throws Exception {
		Views view = new Views();
		view.SelectView(2);
		view.SelectView(1);
	}

}
