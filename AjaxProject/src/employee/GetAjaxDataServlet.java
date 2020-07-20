package employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/GetAjaxDataServlet")
public class GetAjaxDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetAjaxDataServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		// {"data":[ [f,l,e,j,h,s], [], [],...[] ]} 배열안에 배열... db꺼를 이런식으로 만들어서 가져올꺼임.
		EmpDAO dao = new EmpDAO();
		List<Employee> list = dao.getAjaxData();
		JSONArray outAry = new JSONArray();
		for(Employee emp : list) {
			JSONArray inAry = new JSONArray();
			inAry.add(emp.getFirstName());
			inAry.add(emp.getLastName());
			inAry.add(emp.getEmail());
			inAry.add(emp.getJobId());
			inAry.add(emp.getHireDate());
			inAry.add(emp.getSalary());
			
			outAry.add(inAry);
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", outAry);
		
		PrintWriter out = response.getWriter();
		out.println(jsonObj.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
