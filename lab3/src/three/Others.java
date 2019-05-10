package three;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Others {
	public static final String url = "jdbc:mysql://localhost:3306/DB?"
			+ "useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";// &allowPublicKeyRetrieval=true";
																							// //
																							// 程序要连接的数据库和要使用的JDBC驱动程序,默认端口号3306
	public static final String typename = "com.mysql.cj.jdbc.Driver"; // 指定连接类型
	public static final String user = "root"; // 程序连接时所用的数据库用户名
	public static final String password = "1160300814";
	public Connection conn = null;
	public Statement pst = null;
	private String sql = new String();

	/**
	 * 链接数据库
	 */
	public void Con() {
		try {
			Class.forName(typename).newInstance();// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.createStatement();// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// sql = "create index student_age on student(sage);";
	// sql = "SELECT count(sage) FROM student USE INDEX(student_age) WHERE sage <21
	// and sage > 18;";
	// sql = "CREATE TRIGGER table_age before SELECT ON tb_name FOR EACH ROW BEGIN
	// CREATE INDEX mytable_age ON mytable (age); END";
	// sql = "CREATE TRIGGER "+table+"_ageStart"+" before SELECT ON "+table+" FOR
	// EACH ROW BEGIN CREATE INDEX "+table+"_ageindex"+ " ON mytable (sage); END";

	public void UseIndex(String small, String big) {
		try {
			sql = "SELECT sname,sage FROM student USE INDEX(student_age) WHERE sage > " + small
					+ " and sage < " + big+ ";";
			Con();
			System.out.println(sql);
			ResultSet ret = null;
			ret = pst.executeQuery(sql);
			tokenListTbModel.addRow(new Object[] {0 + " | " + "姓名" + " |"+ "年龄" + " |"});
			int num=0;
			while (ret.next()) {
				//System.out.println("| " + ret.getString(1) + " |");
				num++;
				tokenListTbModel.addRow(new Object[] {num + " | " + ret.getString(1) + " |"+ ret.getString(2) + " |"});
			} // 显示数据
			tokenListTbModel.addRow(new Object[] { "------Insert Finish!------" });
			System.out.println("------Insert Finish!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			tokenListTbModel.addRow(new Object[] { "Insert Error:\t" + e.getMessage() });
			System.out.println("Insert Error:\t" + e.getMessage());
			close();
		}

	}

	/**
	 * 可视化
	 */
	private JFrame frame2;
	private DefaultTableModel tokenListTbModel;
	private JTextArea textArea1;
	private JTextArea textArea2;

	private void initialize() throws Exception {

		frame2 = new JFrame();
		frame2.setBounds(100, 100, 480, 640);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame2.getSize();
		frame2.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 180, 180));
		panel.setBounds(0, 0, 1020, 676);
		frame2.getContentPane().add(panel);
		panel.setLayout(null);

		new JScrollPane();

		// 查询的输入文本框
		textArea1 = new JTextArea();
		textArea1.setBackground(new Color(255, 245, 238));
		textArea1.setBounds(155, 77, 120, 34);
		panel.add(textArea1);
		textArea1.setColumns(10);

		JLabel lblToken7 = new JLabel("大于");
		lblToken7.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken7.setBounds(24, 77, 82, 34);
		panel.add(lblToken7);

		textArea2 = new JTextArea();
		textArea2.setBackground(new Color(255, 245, 238));
		textArea2.setBounds(155, 120, 120, 34);
		panel.add(textArea2);
		textArea2.setColumns(10);

		JLabel lblToken6 = new JLabel("小于");
		lblToken6.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken6.setBounds(24, 120, 82, 34);
		panel.add(lblToken6);
		
		JButton btnNewButton_1 = new JButton("开始查询");
		btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("=====================Start=====================");
					UseIndex(textArea1.getText(), textArea2.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setBounds(350, 100, 100, 34);
		panel.add(btnNewButton_1);

		// 表格
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "查询结果" });

		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255, 240, 245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(tokenListTbModel);

		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tokenListTbModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setViewportView(tokenListTb);
		tokenSP.setBounds(51, 190, 341, 402);
		panel.add(tokenSP);

		JLabel lblToken = new JLabel("查询年龄");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(155, 30, 254, 34);
		panel.add(lblToken);
	}

	public Others() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
