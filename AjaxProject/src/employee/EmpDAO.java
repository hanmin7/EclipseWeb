package employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpDAO {
	Connection conn = null;

	public Connection getConnect() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hr", "hr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	

	
	
	public List<Employee> getAjaxData() {
		conn = getConnect();
		String sql = "select first_name, last_name, email, job_id, hire_date, salary from employees";
		List<Employee> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next() ) {
				Employee emp = new Employee();
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("Last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setJobId(rs.getString("job_id"));
				emp.setHireDate(rs.getString("hire_date"));
				emp.setSalary(rs.getInt("salary"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	
	
	
	public void updateEmp(String empId, String salary) {
		conn = getConnect();
		String sql = "update employees set salary = " + salary + " where employee_id =" + empId;
//		String sql = "update employees set salary = ? where employee_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, salary);
//			pstmt.setString(2, empId);
			int r = pstmt.executeUpdate(); //del,update,insert...시는 executeUpdate로~  list가져오는 select시 executeQuery로 
			System.out.println(r + "건 업데이트됨.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void deleteEmp(String empId) {
		conn = getConnect();
		String sql = "delete from employees where employee_id = " + empId;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int r = pstmt.executeUpdate();
			System.out.println(r + "건 삭제됨.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public Map<String, String> getJobCode() {
		conn = getConnect();
		Map<String, String> map = new HashMap<>();
		String sql = "select job_id, job_title from jobs order by 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				map.put(rs.getString("job_id"), rs.getString("job_title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	
	
	
	public void insertEmp(Employee emp) {
		conn = getConnect();
		String sql = "insert into employees (employee_id, last_name, email, hire_date, job_id)"
				+ "values(employees_seq.nextval,?,?,?,?)";
//		System.out.println(emp.getHireDate());
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getLastName());
			pstmt.setString(2, emp.getEmail());
			pstmt.setString(3, emp.getHireDate());
			pstmt.setString(4, emp.getJobId());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건 입력.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Employee> getEmpList() {
		conn = getConnect();
		List<Employee> list = new ArrayList<>();
		try {
//			conn.prepareStatement("select employ ee_id, first_name, email, salary from employees");
			String sql = "select employee_id, first_name, email, salary from employees order by 1 desc";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Employee emp = new Employee(
						rs.getInt("employee_id"), rs.getString("first_name"), rs.getString("email"), rs.getInt("salary")
				);
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
