package three;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Views {
	/* 可视化 */
	private JFrame frame2;
	private JFrame frame1;
	private DefaultTableModel tokenListTbModel;// 选课信息表格式
	private DefaultTableModel labViewModel;// 实验室管理表格式

	// 链接数据库
	public static final String url = "jdbc:mysql://localhost:3306/DB?"
			+ "useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false"; // 程序要连接的数据库和要使用的JDBC驱动程序,默认端口号3306
	public static final String name = "com.mysql.cj.jdbc.Driver"; // 指定连接类型
	public static final String user = "root"; // 程序连接时所用的数据库用户名
	public static final String password = "1160300814";

	public Connection conn = null;
	public PreparedStatement pst = null;
	ResultSet ret = null;

	private String sql = new String();

	/**
	 * 链接数据库
	 */
	public void Con() {
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
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
			this.ret.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Views() throws Exception {

	}
	/*
	public void createV1() {
		sql = "CREATE VIEW  View1(StudentNum, StudentName, CourseNum, CourdeName, TeacherName, Grade) AS  SELECT  SX.Sno，SX.Sname，SY.Ssex，SX.Sage，SY.Sdept FROM  SX，SY";
	}*/

	/**
	 * 建立选课信息表窗口
	 * 
	 * @throws Exception
	 */

	private void initialize() throws Exception { // 第二张 
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 798, 626);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame2.getSize();
		frame2.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 180, 180));
		panel.setBounds(0, 0, 800, 626);
		frame2.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 193, 193));
		panel_1.setBounds(23, 13, 750, 600);
		panel.add(panel_1);
		panel_1.setLayout(null);

		new JScrollPane();// 设有滚动条

		// 选课信息表格
	tokenListTbModel = new DefaultTableModel(new Object[][] {},
				new String[] {"num", "StudentNum", "StudentName", "CourseNum", "CourdeName", "TeacherName", "Grade" });

		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255, 240, 245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(tokenListTbModel);

		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tokenListTbModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setViewportView(tokenListTb);
		tokenSP.setBounds(60, 77, 621, 372);
		panel_1.add(tokenSP);

		JLabel lblToken = new JLabel("选课信息");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(312, 30, 254, 34);
		panel_1.add(lblToken);
	}

	/**
	 * 生成选课信息表
	 * 
	 * @throws Exception
	 */

	private void InsertIotoSelectView() throws Exception {

		sql = "select sid,sname,cid,cname,tname,grade from student natural join choose natural join course natural join teacher;";
		Con();// 创建DBHelper对象
		try {
			int num = 0;
			ret = pst.executeQuery();// 执行语句，得到结果集 
			System.out.println("--------------");
			while (ret.next()) {
				//System.out.println("| " + ret.getString("ENAME") + " |");
				num++;
				tokenListTbModel.addRow(new Object[] {num,ret.getString(1),ret.getString(2),ret.getString(3),ret.getString(4),ret.getString(5),ret.getString(6)});
				
			} // 显示数据 System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立选课实验室管理表窗口
	 * 
	 * @throws Exception
	 */
	private void initialize1() throws Exception {
		// 第二张
		frame1 = new JFrame();
		frame1.setBounds(100, 100, 798, 626);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame1.getSize();
		frame1.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 180, 180));
		panel.setBounds(0, 0, 800, 626);
		frame1.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 193, 193));
		panel_1.setBounds(23, 13, 750, 600);
		panel.add(panel_1);
		panel_1.setLayout(null);

		new JScrollPane();// 设有滚动条

		// 实验室管理信息表格

		labViewModel = new DefaultTableModel(new Object[][] {}, 
				new String[] { "num","LabNum", "LabName", "StudentNum","StudentName", "TeacherNum", "TeacherName", "PaperNum", "PaperName"});

		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255, 240, 245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(labViewModel);

		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(labViewModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setViewportView(tokenListTb);
		tokenSP.setBounds(60, 77, 621, 372);
		panel_1.add(tokenSP);

		JLabel lblToken = new JLabel("实验室信息表");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(312, 30, 254, 34);
		panel_1.add(lblToken);
	}

	/**
	 * 生成实验室管理表
	 * 
	 * @throws Exception
	 */
	private void InsertIotoLabView() throws Exception {
		

		sql = "select labnum,labname,sid,sname,tid,tname,papernum,papername from lab  natural join student natural join teacher natural join paper;";
		Con();// 创建DBHelper对象
		try {
			int num = 0;
			ret = pst.executeQuery();// 执行语句，得到结果集
			System.out.println("--------------");
			while (ret.next()) {
				//String s =ret.getString("ENAME");
				//System.out.println("| " + s + " |");
				labViewModel.addRow(new Object[] {num,ret.getString(1),ret.getString(2), ret.getString(3),ret.getString(4),ret.getString(5),ret.getString(6),ret.getString(7),ret.getString(8)});
				num++;
			} // 显示数据
			System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void SelectView(int num) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(num==1) {//Lab
						initialize1();
						frame1.setVisible(true);
						InsertIotoLabView();
					}else if(num==2) {
						initialize();
						frame2.setVisible(true);
						InsertIotoSelectView();
					}else {
						System.out.println("error:\t输入不正确!");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	

}
