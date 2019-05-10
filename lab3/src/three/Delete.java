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

/*�Զ����쳣*/
class MyException extends Exception {
	public MyException(String msg) {
		super(msg);
	}
}

public class Delete {
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

	public Delete() {
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

	/**
	 * ��ĳ������ɾ����������str=value�ļ�¼
	 * 
	 * @param tablename ����
	 * @param str       ����
	 * @param value     ��ֵ
	 */
	public void deleteSQL(String tablename, String str, String value) {
		try {
			sql = "delete from "+tablename+" where " + str + " = \'" + value + "\';";
			Con();
			int s = pst.executeUpdate(sql);
			tokenListTbModel.addRow(new Object[] { s });
			System.out.println(s);
			tokenListTbModel.addRow(new Object[] { "------Delete Insert!------" });
			System.out.println("------Delete Insert!------");
			close();
		} catch (SQLException e) {// �ظ�����ʾ"Duplicate entry 'xxx' for key 'PRIMARY'"
			System.out.println("Delete Error:\t" + e.getMessage());
			tokenListTbModel.addRow(new Object[] { "Delete Error:\t" + e.getMessage() });
			close();
		}
	}

	/**
	 * ���ӻ�
	 */
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
		lblToken2.setFont(new Font("����", Font.BOLD, 30));
		lblToken2.setBounds(14, 77, 120, 34);
		panel.add(lblToken2);

		JButton btnNewButton_1 = new JButton("OK");
		btnNewButton_1.setFont(new Font("����", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textArea.getText();
				String[] str2 = str.split(" "); // ���տո�ָ�
				try {
					System.out.println("=====================Start=====================");
					if (str2.length != 2) {
						throw new MyException("Delete error:\t�����ʽ����!");
					}
					String[] str3 = str2[1].split(",");
					if (str3.length != 2) {
						throw new MyException("Delete error:\t�����ʽ����!");
					}
					deleteSQL(str2[0], str3[0], str3[1]);
				} catch (MyException e3) {
					tokenListTbModel.addRow(new Object[] { "Delete error:\t�����ʽ����!" });
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_1.setBounds(14, 400, 285, 34);
		panel.add(btnNewButton_1);

		// ���
		tokenListTbModel = new DefaultTableModel(new Object[][] {}, new String[] { "ɾ����Ŀ" });

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

		JLabel lblToken = new JLabel("Delete TABLE");
		lblToken.setFont(new Font("����", Font.BOLD, 30));
		lblToken.setBounds(190, 30, 254, 34);
		panel.add(lblToken);
	}

}