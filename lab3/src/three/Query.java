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
																							// ����Ҫ���ӵ����ݿ��Ҫʹ�õ�JDBC��������,Ĭ�϶˿ں�3306
	public static final String typename = "com.mysql.cj.jdbc.Driver"; // ָ����������
	public static final String user = "root"; // ��������ʱ���õ����ݿ��û���
	public static final String password = "1160300814";
	public Connection conn = null;
	public Statement pst = null;
	private String sql = new String();
	ResultSet ret = null;

	/**
	 * �������ݿ�
	 */
	public void Con() {
		try {
			Class.forName(typename).newInstance();// ָ����������
			conn = DriverManager.getConnection(url, user, password);// ��ȡ����
			pst = conn.createStatement();// ׼��ִ�����
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯĳ����ʦ�̵Ŀ���
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
			} // ��ʾ����
			System.out.println("------Query Finish!------");
			tokenListTbModel.addRow(new Object[] { "------Query Finish!------" });
			close();
		} catch (SQLException e) {
			System.out.println("Delete Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * ĳ��ѧԺ��ÿ��רҵ��������
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
			} // ��ʾ����
			System.out.println("------Query Finish!------");
			tokenListTbModel.addRow(new Object[] { "------Query Finish!------" });
			close();
		} catch (SQLException e) {
			System.out.println("Delete Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * ��ѯû��ѡĳ���γ����ֵ�ͬѧ��
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
			} // ��ʾ����
			System.out.println("------Query Finish!------");
			tokenListTbModel.addRow(new Object[] { "------Query Finish!------" });
			close();
		} catch (SQLException e) {
			System.out.println("Delete Error:\t" + e.getMessage());
			close();
		}
	}
	/**
	 * ���ӻ�
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

		//������ѯ�������ı���
		textArea1 = new JTextArea();
		textArea1.setBackground(new Color(255, 245, 238));
		textArea1.setBounds(155, 77, 120, 34);
		panel.add(textArea1);
		textArea1.setColumns(10);

		JLabel lblToken7 = new JLabel("��ѯ");
		lblToken7.setFont(new Font("����", Font.BOLD, 15));
		lblToken7.setBounds(24, 77, 82, 34);
		panel.add(lblToken7);
		
		JLabel lblToken2 = new JLabel("��ʦ�Ŀ���");
		lblToken2.setFont(new Font("����", Font.BOLD, 15));
		lblToken2.setBounds(310, 77, 140, 34);
		panel.add(lblToken2);
		
		JButton btnNewButton_3 = new JButton("OK");
		btnNewButton_3.setFont(new Font("����", Font.BOLD, 15));
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
		
		JLabel lblToken6 = new JLabel("��ѯ");
		lblToken6.setFont(new Font("����", Font.BOLD, 15));
		lblToken6.setBounds(24, 120, 82, 34);
		panel.add(lblToken6);
		
		JLabel lblToken3 = new JLabel("ѧԺÿ��רҵ����");
		lblToken3.setFont(new Font("����", Font.BOLD, 15));
		lblToken3.setBounds(310, 120, 140, 34);
		panel.add(lblToken3);
		
		JButton btnNewButton_2 = new JButton("OK");
		btnNewButton_2.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_2.setBackground(new Color(255, 228, 225));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea2.getText();
				String[] str2 = str.split(" "); // ���տո�ָ�
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
		
		JLabel lblToken5 = new JLabel("��ѯû��ѡ");
		lblToken5.setFont(new Font("����", Font.BOLD, 15));
		lblToken5.setBounds(24, 160, 142, 34);
		panel.add(lblToken5);
		
		JLabel lblToken4 = new JLabel("�γ����ֵ�ͬѧ��");
		lblToken4.setFont(new Font("����", Font.BOLD, 15));
		lblToken4.setBounds(310, 160, 152, 34);
		panel.add(lblToken4);
		
		JButton btnNewButton_1 = new JButton("OK");
		btnNewButton_1.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea3.getText();
				String[] str2 = str.split(" "); // ���տո�ָ�
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

		// ���
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "��ԃ��Ŀ" });

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
		lblToken.setFont(new Font("����", Font.BOLD, 30));
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
