package c2;

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

/**
 * Servlet implementation class P2
 */
@WebServlet("/P2")
public class P2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public P2() {
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
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<style>");
		pw.println("body { background-color: aqua;}");
		pw.println("h3 {color : red;}");
		pw.println("</style>");
		pw.println("<body>");
		String action =request.getParameter("action");
		int cid=0;
		try
		{
			cid=Integer.parseInt(request.getParameter("cid"));
			
		}
		catch(NumberFormatException e)
		{
			pw.println("<h3> Invalid Customer Id!! <h3>");
			return;
		}
		String checkdate=request.getParameter("cdate");
		if(checkdate==null|| checkdate.trim().isEmpty())
		{
			pw.println("<h3> Invalid CheckoutDate</h3>");
			return;
		}
		java.sql.Date cdate;
		try
		{
			cdate=java.sql.Date.valueOf(checkdate);
		}
		catch(IllegalArgumentException e)
		{
			pw.println("<h3> Invalid date format</h3>");
			return;
		}
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_management","root","Srinu@12345");
			if("fetch".equals(action))
			{
				PreparedStatement ps=con.prepareStatement("select * from hotel where cid=?");
				ps.setInt(1, cid);
				ResultSet re= ps.executeQuery();
				if(re.next())
				{
					String name=re.getString("cname");
					java.sql.Date checkin_date=re.getDate("checkin_date");
					long diff= cdate.getTime()-checkin_date.getTime();
					long days=diff/(1000*60*60*24);
					if(days<=0)
					{
						days=1;
					}
					int dayprice=1000;
					long tot=days*dayprice;
					pw.println("<h2>Checkout Details</h2>");
					pw.println("Customer ID: " + cid + "<br>");
					pw.println("CheckinDate : "+ checkin_date + "<br>");
					pw.println("CheckoutDate : "+cdate+"<br>");
                    pw.println("Customer Name: " + name + "<br>");
                    pw.println("Days Stayed: " + days + "<br>");
                    pw.println("Total Bill: " + tot+"$" + "<br><br>");
                    pw.println("<form action='P2' method='post'>");
            		pw.println("<input type='hidden' name='cid' value='" + cid + "'>");
            		pw.println("<input type='hidden' name='cdate' value='" + cdate + "'>"); 
            		pw.println("<input type='hidden' name='action' value='confirm'>");
            		pw.println("<input type='submit' value='Confirm Checkout'>");
            		pw.println("</form>");                
				}
				else 
				{
					pw.println("<h3>Customer not found!</h3>");
				}
			}
				else if ("confirm".equals(action)) {

	                PreparedStatement ps = con.prepareStatement(
	                    "UPDATE hotel SET status=? WHERE cid=?");
	                ps.setString(1, "checked out");
	                ps.setInt(2, cid);
	                int i = ps.executeUpdate();

	                if (i > 0) {
	                    pw.println("<h2>Checkout Successful!</h2>");
	                    pw.println("<a href='index.html'>Go Back to Home</a>");
	                } else {
	                    pw.println("<h3>Checkout failed!</h3>");
	                }
	            }
			  con.close();
		}
			catch (Exception e) {
	            pw.println("<h3>Error: " + e.getMessage() + "</h3>");
}
		pw.println("<a href='checkout.html'>back</a>");
		pw.println("</body></html>");

}
}