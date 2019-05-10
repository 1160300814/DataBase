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

/**
 * 向表中插入数据，直接调用即可，包含连接数据库和断开。
 * 
 * @author jsq
 *
 */

public class Insert {
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
	 * 像数据库插入值
	 * 
	 * @param tablename 表名
	 * @param value     属性值
	 */
	public void InsertSQL(String tablename, String[] value) {
		try {
			String mid = "";
			sql = "INSERT INTO " + tablename + " VALUES (\'";
			for (int i = 0; i < value.length; i++) {
				
				if(i==value.length-1) {
					sql = sql + value[i] + "\')";
					break;
				}
				sql = sql + value[i] + "\',\'";
			}
			System.out.println(sql);
			Con();
			int n = pst.executeUpdate(sql);
			System.out.println(n);
			tokenListTbModel.addRow(new Object[] { n });
			tokenListTbModel.addRow(new Object[] { "------Insert Finish!------" });
			System.out.println("------Insert Finish!------");
			showStart();
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			tokenListTbModel.addRow(new Object[] { "Insert Error:\t" + e.getMessage() });
			System.out.println("Insert Error:\t" + e.getMessage());
			close();
		}
	}
	/**
	 *触发器中，只要lab删除一条记录，就在history记录一下，类似日志
	 *	 * @throws SQLException
	 */
	public void showStart() throws SQLException {
		//delimiter $
		//mysql> create trigger lab_insert after insert on lab for each row
	    //-> begin
	    //-> insert into history values('someone','add a new value');
	    //-> end $
		sql = "select * from history";
		Con();
		ResultSet ret = null;
		ret = pst.executeQuery(sql);
		//tokenListTbModel.addRow(new Object[] {0 + " | " + "姓名" + " |"+ "年龄" + " |"});
		int num=0;
		while (ret.next()) {
			//System.out.println("| " + ret.getString(1) + " |");
			num++;
			tokenListTbModel.addRow(new Object[] {num + " | " + ret.getString(1) + " |"+ ret.getString(2) + " |"});
		} // 显示数据
	}

	/**
	 * 可视化
	 */
	public Insert() {
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
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 670, 526);
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
						throw new MyException("Insert error:\t输入格式不对!");
					}
					String[] str3 = str2[1].split(",");
					InsertSQL(str2[0], str3);
				} catch (MyException e3) {
					tokenListTbModel.addRow(new Object[] { "Insert error:\t输入格式不对!" });
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setBounds(14, 400, 285, 34);
		panel.add(btnNewButton_1);

		// 表格
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "插入数目" });

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

		JLabel lblToken = new JLabel("Insert TABLE");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(190, 30, 254, 34);
		panel.add(lblToken);
	}

	/**
	 * 向表Account插入数据
	 * 
	 * @param num      学生编号
	 * @param age      年龄
	 * @param name     学生姓名
	 * @param classnum 班号
	 * @param majornum 专业编号
	 * @param labnum   实验室编号
	 */
	public void InsertIotoStudent(String num, String age, String name, String classnum, String majornum,
			String labnum) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + num + "\',\'" + age + "\',\'" + name + "\',\'" + classnum + "\',\'"
					+ majornum + "\',\'" + labnum + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向表Major中插入数据
	 * 
	 * @param num        专业编号
	 * @param name       专业名字
	 * @param collegenum 所属学院编号
	 */
	public void InsertIotoMajor(String num, String name, String collegenum) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + num + "\',\'" + name + "\',\'" + collegenum + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向表College插入数据
	 * 
	 * @param num  学院编号
	 * @param name 学院名称
	 */
	public void InsertIotoCollege(String num, String name) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + num + "\',\'" + name + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向Teacher表插入数据
	 * 
	 * @param classnum   班号
	 * @param num        老师编号
	 * @param sex        老师性别
	 * @param name       老师姓名
	 * @param age        老师年龄
	 * @param coursenum  老师所教授的课程号
	 * @param collegenum 老师所属学院编号
	 * @param labnum
	 */
	public void InsertIotoTeacher(String classnum, String num, String sex, String name, String age, String coursenum,
			String collegenum, String labnum) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + classnum + "\',\'" + num + "\',\'" + sex + "\',\'" + name + "\',\'"
					+ age + "\',\'" + coursenum + "\',\'" + collegenum + "\',\'" + labnum + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向Select表插入数据，记录学生选课信息及成绩
	 * 
	 * @param studentnum 学生编号
	 * @param coursenum  课程编号
	 * @param grade      成绩
	 */
	public void InsertIotoSelect(String studentnum, String coursenum, String grade) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + studentnum + "\',\'" + coursenum + "\',\'" + grade + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向表Course中插入数据
	 * 
	 * @param num  课程编号
	 * @param name 课程名称
	 */
	public void InsertIotoCourse(String num, String name) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + num + "\',\'" + name + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向表Lab中插入数据
	 * 
	 * @param num  实验室编号
	 * @param name 实验室名称
	 */
	public void InsertIotoLab(String num, String name) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + num + "\',\'" + name + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}

	/**
	 * 向表Publish中插入数据,记录老师发布的论文
	 * 
	 * @param teachernum 老师编号
	 * @param papernum   论文编号
	 * @param papername  论文题目
	 */
	public void InsertIotoPublish(String teachernum, String papernum, String papername) {
		try {
			sql = "INSERT INTO Account VALUES (\'" + teachernum + "\',\'" + papernum + "\',\'" + papername + "\');";
			Con();
			System.out.println(pst.executeUpdate(sql));
			System.out.println("------Finish Insert!------");
			close();
		} catch (SQLException e) {// 重复会提示"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Error:\t" + e.getMessage());
			close();
		}
	}
}
