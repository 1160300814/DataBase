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
	/* ���ӻ� */
	private JFrame frame2;
	private JFrame frame1;
	private DefaultTableModel tokenListTbModel;// ѡ����Ϣ���ʽ
	private DefaultTableModel labViewModel;// ʵ���ҹ�����ʽ

	// �������ݿ�
	public static final String url = "jdbc:mysql://localhost:3306/DB?"
			+ "useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false"; // ����Ҫ���ӵ����ݿ��Ҫʹ�õ�JDBC��������,Ĭ�϶˿ں�3306
	public static final String name = "com.mysql.cj.jdbc.Driver"; // ָ����������
	public static final String user = "root"; // ��������ʱ���õ����ݿ��û���
	public static final String password = "1160300814";

	public Connection conn = null;
	public PreparedStatement pst = null;
	ResultSet ret = null;

	private String sql = new String();

	/**
	 * �������ݿ�
	 */
	public void Con() {
		try {
			Class.forName(name);// ָ����������
			conn = DriverManager.getConnection(url, user, password);// ��ȡ����
			pst = conn.prepareStatement(sql);// ׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر�����
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
		sql = "CREATE VIEW  View1(StudentNum, StudentName, CourseNum, CourdeName, TeacherName, Grade) AS  SELECT  SX.Sno��SX.Sname��SY.Ssex��SX.Sage��SY.Sdept FROM  SX��SY";
	}*/

	/**
	 * ����ѡ����Ϣ����
	 * 
	 * @throws Exception
	 */

	private void initialize() throws Exception { // �ڶ��� 
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

		new JScrollPane();// ���й�����

		// ѡ����Ϣ���
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

		JLabel lblToken = new JLabel("ѡ����Ϣ");
		lblToken.setFont(new Font("����", Font.BOLD, 30));
		lblToken.setBounds(312, 30, 254, 34);
		panel_1.add(lblToken);
	}

	/**
	 * ����ѡ����Ϣ��
	 * 
	 * @throws Exception
	 */

	private void InsertIotoSelectView() throws Exception {

		sql = "select sid,sname,cid,cname,tname,grade from student natural join choose natural join course natural join teacher;";
		Con();// ����DBHelper����
		try {
			int num = 0;
			ret = pst.executeQuery();// ִ����䣬�õ������ 
			System.out.println("--------------");
			while (ret.next()) {
				//System.out.println("| " + ret.getString("ENAME") + " |");
				num++;
				tokenListTbModel.addRow(new Object[] {num,ret.getString(1),ret.getString(2),ret.getString(3),ret.getString(4),ret.getString(5),ret.getString(6)});
				
			} // ��ʾ���� System.out.println("--------------");
			System.out.println("��ѯ��" + num + "�����ݣ�");
			close();// �ر�����
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ѡ��ʵ���ҹ������
	 * 
	 * @throws Exception
	 */
	private void initialize1() throws Exception {
		// �ڶ���
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

		new JScrollPane();// ���й�����

		// ʵ���ҹ�����Ϣ���

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

		JLabel lblToken = new JLabel("ʵ������Ϣ��");
		lblToken.setFont(new Font("����", Font.BOLD, 30));
		lblToken.setBounds(312, 30, 254, 34);
		panel_1.add(lblToken);
	}

	/**
	 * ����ʵ���ҹ����
	 * 
	 * @throws Exception
	 */
	private void InsertIotoLabView() throws Exception {
		

		sql = "select labnum,labname,sid,sname,tid,tname,papernum,papername from lab  natural join student natural join teacher natural join paper;";
		Con();// ����DBHelper����
		try {
			int num = 0;
			ret = pst.executeQuery();// ִ����䣬�õ������
			System.out.println("--------------");
			while (ret.next()) {
				//String s =ret.getString("ENAME");
				//System.out.println("| " + s + " |");
				labViewModel.addRow(new Object[] {num,ret.getString(1),ret.getString(2), ret.getString(3),ret.getString(4),ret.getString(5),ret.getString(6),ret.getString(7),ret.getString(8)});
				num++;
			} // ��ʾ����
			System.out.println("--------------");
			System.out.println("��ѯ��" + num + "�����ݣ�");
			close();// �ر�����
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
						System.out.println("error:\t���벻��ȷ!");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	

}
