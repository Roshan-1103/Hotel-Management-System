package p3;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class V3
 */
@WebServlet("/V3")
public class V3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public V3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		 pw.println("<html><head><title>Customers</title>");
		 pw.println("<style>");
		 pw.println("body{");
		 pw.println("background-color:aqua");
		 pw.println("}");
		 pw.println("</style>");
		 pw.println("</head>");
		 pw.println("<body>");
	        pw.println("<h2>Customer Details</h2>");
	        try
	        {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management","root","Srinu@12345");
				PreparedStatement ps=con.prepareStatement("select * from hotel order by cid ");
				PreparedStatement ps1=con.prepareStatement("update hotel set status='Checked In' where status is null");
				ps1.executeUpdate();
				ResultSet rs= ps.executeQuery();
		 pw.println("<table border='1' cellpadding='10'>");
         pw.println("<tr>");
         pw.println("<th>ID</th>");
         pw.println("<th>Name</th>");
         pw.println("<th>Mobile</th>");
         pw.println("<th>Check-in Date</th>");
         pw.println("<th>Days</th>");
         pw.println("<th>Status</th>");
         pw.println("</tr>");

         while (rs.next()) {
             pw.println("<tr>");
             pw.println("<td>" + rs.getInt("cid") + "</td>");
             pw.println("<td>" + rs.getString("cname") + "</td>");
             pw.println("<td>" + rs.getString("cmobile") + "</td>");
             pw.println("<td>" + rs.getDate("checkin_date") + "</td>");
             pw.println("<td>" +rs.getInt("days")+"</td>");
             pw.println("<td>" +rs.getString("status")+"</td>");
             pw.println("</tr>");
         }
         pw.println("</table>");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	        pw.println("<a href='index.html'> back<a>");
	        pw.println("</body></html>");
	        
}
}
