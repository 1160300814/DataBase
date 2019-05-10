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

public class Replace {
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

	/**
	 * 在表tablename 中，將满足键who=is的记录中，键change值改为what
	 * 
	 * @param tablename
	 * @param change
	 * @param what
	 * @param who
	 * @param is
	 */
	
	public void ReplaceSQL(String tablename, String change, String what, String who, String is) {
		try {
			sql = "Update  " + tablename + " set " + change + " = \'" + what + "\' where " + who + " = \'" + is + "\';";
			Con();
			int s = pst.executeUpdate(sql);
			System.out.println(s);
			tokenListTbModel.addRow(new Object[] { s });
			tokenListTbModel.addRow(new Object[] { "------Replace Insert!------" });
			/*sql = "select * from "+tablename+";";
			System.out.println(sql);
			ResultSet ret = null;
			ret = pst.executeQuery(sql);
			while (ret.next()) {
				//System.out.println("| " + ret.getString(1) + " |");
				tokenListTbModel.addRow(new Object[] {"| " + ret.getString(1) + " |"+ ret.getString(2) + " |"});
			} // 显示数据*/
			System.out.println("------Replace Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Delete Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 可视化
	 */
	public Replace() {
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

	private JFrame frame2;
	private DefaultTableModel tokenListTbModel;
	private JTextArea textArea;

	private void initialize() throws Exception {
		// 第二张
		frame2 = new JFrame();
		frame2.setBounds(100, 100,  670, 526);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame2.getSize();
		frame2.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 180, 180));
		panel.setBounds(0, 0, 820, 626);
		frame2.getContentPane().add(panel);
		panel.setLayout(null);

		new JScrollPane();

		textArea = new JTextArea();
		textArea.setBackground(new Color(255, 245, 238));

		textArea.setBounds(14, 120, 285, 250);
		panel.add(textArea);
		textArea.setColumns(10);

		JLabel lblToken2 = new JLabel("Input:");
		lblToken2.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken2.setBounds(14, 77, 120, 34);
		panel.add(lblToken2);
		
		JButton btnNewButton_1 = new JButton("OK");
		btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea.getText();
				String[] str2 = str.split(" "); // 按照逗号分隔
				try {
					System.out.println("=====================Start=====================");
					if (str2.length != 2) {
						throw new MyException("Replace error:\t输入格式不对!");
					}
					String[] str3 = str2[1].split(",");
					if (str3.length != 4) {
						throw new MyException("Replace error:\t输入格式不对!");
					}
					ReplaceSQL(str2[0], str3[0], str3[1], str3[2], str3[3]);
				} catch (MyException e3) {
					tokenListTbModel.addRow(new Object[] { "Replacet error:\t输入格式不对!" });
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setBounds(14, 400, 285, 34);
		panel.add(btnNewButton_1);

		// 表格
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "修改" });

		JTable tokenListTb = new JTable();
		tokenListTb.setBackground(new Color(255, 240, 245));
		tokenListTb.setFillsViewportHeight(true);
		tokenListTb.setModel(tokenListTbModel);

		RowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tokenListTbModel);
		tokenListTb.setRowSorter(sorter);
		JScrollPane tokenSP = new JScrollPane();
		tokenSP.setViewportView(tokenListTb);
		tokenSP.setBounds(313, 120, 320, 322);
		panel.add(tokenSP);

		JLabel lblToken = new JLabel("Replace TABLE");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(190, 30, 254, 34);
		panel.add(lblToken);
	}

}
