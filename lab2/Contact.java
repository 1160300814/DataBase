package lab2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Contact {
	public static final String url = "jdbc:mysql://localhost:3306/COMPANY?"
			+ "useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false"; // ����Ҫ���ӵ����ݿ��Ҫʹ�õ�JDBC��������,Ĭ�϶˿ں�3306
	public static final String name = "com.mysql.cj.jdbc.Driver"; // ָ����������
	public static final String user = "root"; // ��������ʱ���õ����ݿ��û���
	public static final String password = "1160300814";
	public Connection conn = null;
	public PreparedStatement pst = null;

	public Contact(String sql) {
		try {
			Class.forName(name);// ָ����������
			conn = DriverManager.getConnection(url, user, password);// ��ȡ����
			pst = conn.prepareStatement(sql);// ׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}