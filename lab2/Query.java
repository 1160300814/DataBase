package lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
	static String sql = new String();
	static Contact pre = null;
	static ResultSet ret = null;
	final static String emp = "EMPLOYEE";
	final static String dep = "DEPARTMENT";
	final static String wor = "WORKS_ON";
	final static String pro = "PROJECT";
	static String command = new String();

	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		while (true) {
			System.out.print("查询开始：例如 n \"x,y\"\n");
			System.out.print("MengSQL>");
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			command = strin.readLine();
			command = command.replaceAll("\\s+", " ");
			if (command.trim().equals("quit") || command.trim().equals("exit")) {
				System.out.println("谢谢使用！");
				break;
			}
			// 读入输入串
			String EachCMD[] = new String[2];
			String hhh[] = command.split(" ");
			if (hhh.length != 2) {
				System.out.printf("输入格式不对,请重新输入!");
				continue;
			}
			EachCMD[0] = hhh[0];
			EachCMD[1] = hhh[1];

			int cmd = Integer.parseInt(EachCMD[0]);
			String params[] = EachCMD[1].substring(1, EachCMD[1].length() - 1).split("\\,");
			switch (cmd) {
			case 1:
				EmployeeNum(params);
				break;
			case 2:
				EmployeeName(params);
				break;
			case 3:
				EmployeeWorkAT(params);
				break;
			case 4:
				EmployeeWorkATandLowSalary(params);
				break;
			case 5:
				EmployeeNotIn(params);
				break;
			case 6:
				EmployeeOf(params);
				break;
			case 7:
				EmployeeIN(params);
				break;
			case 8:
				EmployeeSalaryLow(params);
				break;
			case 9:
				Atlast(params);
				break;
			default:
				System.out.println("暂时没有" + cmd + "号指令！");
				break;
			}
		}
	}

	// 至少参与了%N%个项目且工作总时间不超过%HOURS%小时的员工名字，其中%N%和%HOURS%为C语言编写的程序的输入参数
	private static void Atlast(String[] params) {
		int num = 0;
		sql = "select ENAME from " + emp + " natural join WORKS_ON group by ESSN having count(PNO) >= " + params[0]
				+ " and sum(HOURS) <= " + params[1] + ";";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			// System.out.println("+------------+");
			System.out.println("至少参与了" + params[0] + "个项目且工作总时间不超过" + params[1] + "小时的员工名字");
			System.out.println("|    ENAME    |");
			System.out.println("--------------");
			while (ret.next()) {
				System.out.println("| " + ret.getString("ENAME") + " |");
				num++;
			} // 显示数据
			System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 参加了项目编号为%PNO%的项目的员工号
	public static void EmployeeNum(String[] params) {
		int num = 0;
		sql = "select ESSN from " + wor + " where PNO = \"" + params[0] + "\"";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			// System.out.println("+------------+");
			System.out.println("参加了项目编号为" + params[0] + "的项目的员工号");
			System.out.println("|    ESSN    |");
			System.out.println("--------------");
			while (ret.next()) {
				System.out.println("| " + ret.getString("ESSN") + " |");
				num++;
			} // 显示数据
			System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 参加了项目名为%PNAME%的员工名字
	public static void EmployeeName(String[] params) {
		int num = 0;
		sql = "select ENAME from " + emp + " natural join WORKS_ON natural join PROJECT where PNAME = \"" + params[0]
				+ "\"" + ";";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			// System.out.println("+------------+");
			System.out.println("参加了项目名为" + params[0] + "的员工名字");
			System.out.println("|    ENAME   |");
			System.out.println("--------------");
			while (ret.next()) {
				System.out.println("|  " + ret.getString("ENAME") + "  |");
				num++;
			} // 显示数据
			System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 在%DNAME%工作的所有工作人员的名字和地址
	public static void EmployeeWorkAT(String[] params) {
		int num = 0;
		sql = "select ENAME,ADDRESS from " + emp + "," + dep + " where DNAME = \"" + params[0] + "\""
				+ " and EMPLOYEE.DNO = DEPARTMENT.DNO;";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			System.out.println("在" + params[0] + "工作的所有工作人员的名字和地址");
			System.out.println("|    ENAME       |		ADDRESS   |");
			System.out.println("-----------------+-------------------------");
			while (ret.next()) {
				System.out.println("|     " + ret.getString("ENAME") + " 	 |  " + ret.getString("ADDRESS") + "	|");
				num++;
			} // 显示数据
			System.out.println("-----------------+-------------------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 在%DNAME%工作且工资低于%SALARY%元的员工名字和地址
	public static void EmployeeWorkATandLowSalary(String[] params) {
		int num = 0;
		sql = "select ENAME,ADDRESS from " + emp + "," + dep + " where SALARY < " + Integer.parseInt(params[1])
				+ " and DNAME = \"" + params[0] + "\"" + " and EMPLOYEE.DNO = DEPARTMENT.DNO;";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			System.out.println("在" + params[0] + "工作且工资低于" + params[1] + "元的员工名字和地址");
			System.out.println("|    ENAME       |		ADDRESS   |");
			System.out.println("-----------------+-------------------------");
			while (ret.next()) {
				System.out.println("|     " + ret.getString("ENAME") + " 	 |  " + ret.getString("ADDRESS") + "	|");
				num++;
			} // 显示数据
			System.out.println("-----------------+-------------------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 没有参加项目编号为%PNO%的项目的员工姓名
	public static void EmployeeNotIn(String[] params) {
		int num = 0;
		sql = "select ENAME from " + emp + " where EMPLOYEE.ESSN not in ( select ESSN from WORKS_ON where PNO = \""
				+ params[0] + "\");";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			System.out.println("没有参加项目编号为" + params[0] + "的项目的员工姓名");
			System.out.println("|    ENAME   |");
			System.out.println("--------------");
			while (ret.next()) {
				System.out.println("|  	" + ret.getString("ENAME") + "  |");
				num++;
			} // 显示数据
			System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 由%ENAME%领导的工作人员的姓名和所在部门的名字
	public static void EmployeeOf(String[] params) {
		int num = 0;
		sql = "select b.ENAME,DEPARTMENT.DNAME from " + emp + " as b," + emp + " as a," + dep
				+ " where b.DNO = DEPARTMENT.DNO and a.ENAME = \"" + params[0]
				+ "\" and b.SUPERSSN = a.ESSN and b.ENAME <> \"" + params[0] + "\";";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			System.out.println("由" + params[0] + "领导的工作人员的姓名和所在部门的名字");
			System.out.println("|    ENAME       |		DNAME	   |");
			System.out.println("-----------------+-------------------------");
			while (ret.next()) {
				System.out.println("|     " + ret.getString("ENAME") + " 	 |  " + ret.getString("DNAME") + "	|");
				num++;
			} // 显示数据
			System.out.println("-----------------+-------------------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 至少参加了项目编号为%PNO1%和%PNO2%的项目的员工号
	public static void EmployeeIN(String[] params) {
		int num = 0;
		sql = "select ESSN from " + wor + " where PNO = \"" + params[0]
				+ "\" and ESSN in (select ESSN from WORKS_ON where PNO = \"" + params[1] + "\");";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			System.out.println("至少参加了项目编号为" + params[0] + "和" + params[1] + "的项目的员工号");
			System.out.println("|    ESSN    |");
			System.out.println("--------------");
			while (ret.next()) {
				System.out.println("| " + ret.getString("ESSN") + " |");
				num++;
			} // 显示数据
			System.out.println("--------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 员工平均工资低于%SALARY%元的部门名称
	public static void EmployeeSalaryLow(String[] params) {
		int num = 0;
		sql = "select DNAME from " + dep + "," + emp
				+ " where EMPLOYEE.DNO = DEPARTMENT.DNO group by EMPLOYEE.DNO having avg(SALARY) < "
				+ Integer.parseInt(params[0]) + ";";// SQL语句
		pre = new Contact(sql);// 创建DBHelper对象
		try {
			ret = pre.pst.executeQuery();// 执行语句，得到结果集
			System.out.println("员工平均工资低于" + params[0] + "元的部门名称");
			System.out.println("|    	DNAME		 |");
			System.out.println("--------------------------");
			while (ret.next()) {
				System.out.println("|  " + ret.getString("DNAME") + "    |");
				num++;
			} // 显示数据
			System.out.println("--------------------------");
			System.out.println("查询出" + num + "行数据！");
			ret.close();
			pre.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}