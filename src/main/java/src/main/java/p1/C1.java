package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class C1
 */
@WebServlet("/C1")
public class C1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public C1() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session=request.getSession(false);

		if(session==null ||
		session.getAttribute("admin")==null)
		{
		    response.sendRedirect("admin.html");
		    return;
		}
		PrintWriter pw=response.getWriter();
		int s1=Integer.parseInt(request.getParameter("cid"));
		String s2=request.getParameter("cname");
		long s3=Long.parseLong(request.getParameter("cmob"));
		String s4=request.getParameter("checkin");
		int s5=Integer.parseInt(request.getParameter("days"));
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management","root","Srinu@12345");
			PreparedStatement ps=con.prepareStatement("insert into hotel(cid,cname,cmobile,checkin_date,days) values(?,?,?,?,?)");
			ps.setInt(1, s1);
			ps.setString(2, s2);
			ps.setLong(3, s3);
			java.sql.Date sdate= java.sql.Date.valueOf(s4);
			ps.setDate(4, sdate);
			ps.setInt(5, s5);
			
			ps.executeUpdate();
			pw.println("<h2>values successfully inserted</h2><br></br>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		response.setContentType("text/html");
		pw.println("<a href='index.html'>back</a><br></br>");
		pw.println("<a href='logout.html'>Logout</a><br></br>");
	 
	}

}
