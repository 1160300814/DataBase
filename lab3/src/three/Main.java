package three;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	private static JFrame frame2;

	private static void initialize() throws Exception {
		frame2 = new JFrame();
		frame2.setBounds(100, 100, 280, 526);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame2.getSize();
		frame2.setLocation((screensize.width - frameSize.width) / 2, (screensize.height - frameSize.height) / 2);

		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 180, 180));
		panel.setBounds(0, 0, 280, 526);
		frame2.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 193, 193));
		panel_1.setBounds(23, 13, 215, 450);
		panel.add(panel_1);
		panel_1.setLayout(null);

		// 标题
		JLabel lblToken = new JLabel("Welcome!");
		lblToken.setFont(new Font("宋体", Font.BOLD, 30));
		lblToken.setBounds(42, 50, 207, 34);
		panel_1.add(lblToken);

		/* 按钮 */
		// 插入
		final JButton btnNewButton_2 = new JButton("插入");
		btnNewButton_2.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_2.setBackground(new Color(255, 228, 225));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Insert();
			}
		});
		btnNewButton_2.setBounds(42, 120, 127, 34);
		panel_1.add(btnNewButton_2);

		// 删除
		JButton btnNewButton_1 = new JButton("删除");
		btnNewButton_1.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Delete();
			}

		});
		btnNewButton_1.setBounds(42, 160, 127, 34);
		panel_1.add(btnNewButton_1);

		// 查询
		JButton btnNewButton_3 = new JButton("查询");
		btnNewButton_3.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_3.setBackground(new Color(255, 228, 225));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Query();
			}

		});
		btnNewButton_3.setBounds(42, 200, 127, 34);
		panel_1.add(btnNewButton_3);

		// 修改
		JButton btnNewButton_4 = new JButton("修改");
		btnNewButton_4.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_4.setBackground(new Color(255, 228, 225));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Replace();
			}

		});
		btnNewButton_4.setBounds(42, 240, 127, 34);
		panel_1.add(btnNewButton_4);

		// 成绩视图
		JButton btnNewButton_5 = new JButton("成绩视图");
		btnNewButton_5.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_5.setBackground(new Color(255, 228, 225));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Views().SelectView(2);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_5.setBounds(42, 280, 127, 34);
		panel_1.add(btnNewButton_5);

		// 实验室
		JButton btnNewButton_6 = new JButton("实验室视图");
		btnNewButton_6.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_6.setBackground(new Color(255, 228, 225));
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Views().SelectView(1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_6.setBounds(42, 320, 127, 34);
		panel_1.add(btnNewButton_6);
		// 索引
		JButton btnNewButton_7 = new JButton("索引查询");
		btnNewButton_7.setFont(new Font("宋体", Font.BOLD, 15));
		btnNewButton_7.setBackground(new Color(255, 228, 225));
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Others();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNewButton_7.setBounds(42, 360, 127, 34);
		panel_1.add(btnNewButton_7);
	}

	public static void main(String[] args) {
		System.out.println("======== Welcome! ========");
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
