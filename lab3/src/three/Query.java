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

public class Query {
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
	ResultSet ret = null;

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

	/**
	 * 查询某个老师教的课名
	 * 
	 * @param tname
	 */
	public void LianJie(String tname) {
		try {
			sql = "Select cname From Course, Teacher where Course.cid = Teacher.cid and Teacher.Tname = \'"
					+ tname + "\'";
			Con();
			ret = pst.executeQuery(sql);
			while (ret.next()) {
				String s1 = ret.getString(1);
				System.out.println("| " + s1 + " |");
				tokenListTbModel.addRow(new Object[] { "| " + s1 + " |"});
			} // 显示数据
			System.out.println("------Query Finish!------");
			tokenListTbModel.addRow(new Object[] { "------Query Finish!------" });
			close();
		} catch (SQLException e) {
			System.out.println("Delete Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 某个学院中每个专业各多少人
	 */
	public void Groupby(String c) {
		try {
			sql = "Select  Major.majorname, count(Student.sid) From Student natural join Major natural join academy where academy.academyname = \'"
					+ c + "\' group by Major.smajornum";
			Con();
			ret = pst.executeQuery(sql);
			System.out.println("| " + "Major" + " |" + "Number" + " |");
			while (ret.next()) {
				String s1 = ret.getString(1);
				String s2 = ret.getString(2);
				System.out.println("| " + s1 + " |" + s2 + " |");
				tokenListTbModel.addRow(new Object[] { "| " + s1 + " |" + s2 + " |" });
			} // 显示数据
			System.out.println("------Query Finish!------");
			tokenListTbModel.addRow(new Object[] { "------Query Finish!------" });
			close();
		} catch (SQLException e) {
			System.out.println("Delete Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 查询没有选某个课程名字的同学名
	 * 
	 * @param tname
	 */
	public void Qiantao(String tname) {
		try {
			sql = "Select sname From Student where sclass not in ( Select Student.sname From choose,Course where choose.cid = Course.cid and Course.cname = \'"
					+ tname + "\');";
			Con();
			ret = pst.executeQuery(sql);
			while (ret.next()) {
				String s =ret.getString(1);
				System.out.println("| " + s + " |");
				tokenListTbModel.addRow(new Object[] { "| " + s + " |" });
			} // 显示数据
			System.out.println("------Query Finish!------");
			tokenListTbModel.addRow(new Object[] { "------Query Finish!------" });
			close();
		} catch (SQLException e) {
			System.out.println("Delete Error:\t" + e.getMessage());
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
	private JTextArea textArea3;

	private void initialize() throws Exception {
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 580, 640);
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

		//三个查询的输入文本框
		textArea1 = new JTextArea();
		textArea1.setBackground(new Color(255, 245, 238));
		textArea1.setBounds(155, 77, 120, 34);
		panel.add(textArea1);
		textArea1.setColumns(10);

		JLabel lblToken7 = new JLabel("查询");
		lblToken7.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken7.setBounds(24, 77, 82, 34);
		panel.add(lblToken7);
		
		JLabel lblToken2 = new JLabel("老师的课名");
		lblToken2.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken2.setBounds(310, 77, 140, 34);
		panel.add(lblToken2);
		
		JButton btnNewButton_3 = new JButton("OK");
		btnNewButton_3.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_3.setBackground(new Color(255, 228, 225));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea1.getText();
				try {
					LianJie(str);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_3.setBounds(460, 77, 60, 34);
		panel.add(btnNewButton_3);
		
		textArea2 = new JTextArea();
		textArea2.setBackground(new Color(255, 245, 238));
		textArea2.setBounds(155, 120, 120, 34);
		panel.add(textArea2);
		textArea2.setColumns(10);
		
		JLabel lblToken6 = new JLabel("查询");
		lblToken6.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken6.setBounds(24, 120, 82, 34);
		panel.add(lblToken6);
		
		JLabel lblToken3 = new JLabel("学院每个专业人数");
		lblToken3.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken3.setBounds(310, 120, 140, 34);
		panel.add(lblToken3);
		
		JButton btnNewButton_2 = new JButton("OK");
		btnNewButton_2.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_2.setBackground(new Color(255, 228, 225));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea2.getText();
				String[] str2 = str.split(" "); // 按照空格分隔
				try {
					System.out.println("=====================Start=====================");
					Groupby(str);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_2.setBounds(460, 120, 60, 34);
		panel.add(btnNewButton_2);
		
		textArea3 = new JTextArea();
		textArea3.setBackground(new Color(255, 245, 238));
		textArea3.setBounds(155, 160, 120, 34);
		panel.add(textArea3);
		textArea3.setColumns(10);
		
		JLabel lblToken5 = new JLabel("查询没有选");
		lblToken5.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken5.setBounds(24, 160, 142, 34);
		panel.add(lblToken5);
		
		JLabel lblToken4 = new JLabel("课程名字的同学名");
		lblToken4.setFont(new Font("宋体", Font.BOLD, 15));
		lblToken4.setBounds(310, 160, 152, 34);
		panel.add(lblToken4);
		
		JButton btnNewButton_1 = new JButton("OK");
		btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea3.getText();
				String[] str2 = str.split(" "); // 按照空格分隔
				try {
					System.out.println("=====================Start=====================");
					
					Qiantao(str);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setBounds(460, 160, 60, 34);
		panel.add(btnNewButton_1);

		// 表格
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "查詢条目" });

		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255, 240, 245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(tokenListTbModel);

		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tokenListTbModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setViewportView(tokenListTb);
		tokenSP.setBounds(51, 210, 441, 362);
		panel.add(tokenSP);

		JLabel lblToken = new JLabel("Query TABLE");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(185, 30, 254, 34);
		panel.add(lblToken);
	}
	public Query() {
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
