package three;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * �����ã��������в�����
 * @author jsq
 *
 */
public class test {
	public static final String url = "jdbc:mysql://localhost:3306/DB?"
			+ "useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";// &allowPublicKeyRetrieval=true";
																							// //
																							// ����Ҫ���ӵ����ݿ��Ҫʹ�õ�JDBC��������,Ĭ�϶˿ں�3306
	public static final String name = "com.mysql.cj.jdbc.Driver"; // ָ����������
	public static final String user = "root"; // ��������ʱ���õ����ݿ��û���
	public static final String password = "1160300814";
	public static Connection con = null;
	public static Statement pst = null;
	private static String sql = new String();
	// private static ResultSet ret = null;

	/**
	 * �������ݿ�
	 */
	public static void Con() {
		try {
			Class.forName(name).newInstance();// ָ����������
			con = DriverManager.getConnection(url, user, password);// ��ȡ����
			pst = con.createStatement();// ׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �ر�����
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
		//���Բ���
		 try {
			Con();
			sql = "INSERT INTO WORKS_ON VALUES (\'" + "12351"  + "\',\'" + 5 + "\');";

			System.out.println(sql);
			System.out.println(pst.executeUpdate(sql));
			// System.out.println("-----------------+-------------------------");
			close();
		} catch (Exception e) {// �ظ�����ʾ"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("error:\t"+e.getMessage());
			close();
		}
		//������ͼ
//		try {
//			testView();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//����ɾ��
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
