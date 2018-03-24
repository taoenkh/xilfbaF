
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet("/androidlogin")


public class tset extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
		//request.getSession().setAttribute("recap","no");
		
		PrintWriter out = response.getWriter();
		String loginUser = "taoenkh";
        String loginPasswd = "wangtao1";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        response.setContentType("application/json");
        String username = request.getParameter("username");
		String password = request.getParameter("password");
			System.out.println("1");
		
			try {
				System.out.println("android connected");
				Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();
            String query = "SELECT * from customers where email= '"
            		+ username +"'";
            ResultSet rs = statement.executeQuery(query);
            
            if (!rs.next()) {
            	
            	out.print("false");
	
            }else {
            if (rs.getString("password").equals(password)) {
            	out.print("true");
            }
            else {
            	out.print("false");
            	
            }
            }
            
            



			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
            

			
			
			
			
			
			
			
			
		
		
	}
    
     public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
	} 
}
