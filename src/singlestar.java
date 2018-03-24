import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet("/singlestar")

public class singlestar extends HttpServlet {
	
	
	 public String getServletInfo()
	    {
	       return "Servlet connects to MySQL database and displays result of a SELECT";
	    }

	    // Use http GET

	    public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
//	        String loginUser = "taoenkh";
//	        String loginPasswd = "wangtao1";
//	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=true";

	        response.setContentType("text/html");    // Response mime type

	        // Output stream to STDOUT
	        PrintWriter out = response.getWriter();
	        out.println("<HTML><HEAD><TITLE>Star Details</TITLE>"
	        		+ "<link rel=\"stylesheet\" href=\"./css/style.css\" type=\"text/css\"  />"
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ ""
	        		+ "</HEAD>");
	        out.println("<BODY>"
	        		+ "<div id=\"header\" align=\"left\">\r\n" + 
	        		"<span id = \"chart\"><a href =\"/cs122b/shoppingcart\"></a></span>"+
	        		"        <h1 id=\"logo\"><a href=\"/cs122b/index.html\"></a></h1>\r\n" + 
	        		"        <br>\r\n" + 
	        		"        <br>\r\n" + 
	        		"        <br>\r\n" + 
	        		"        <br>\r\n" + 
	        		"      </div>"
	        		+ ""
	        		+ ""
	        		+ "");
	        out.println("<div id ='out' style ='background-color:white;opacity:0.9;'>");
	        out.println("<H1>Star Details</H1>");
	        try
	           {
	              //Class.forName("org.gjt.mm.mysql.Driver");
	              //Class.forName("com.mysql.jdbc.Driver").newInstance();

	              //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				Context initCtx = new InitialContext();


	            Context envCtx = (Context) initCtx.lookup("java:comp/env");

	            
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	            //System.out.println(ds.toString()+" "+"");

	            System.out.println("dsad");
	            Connection dbcon = ds.getConnection();
	              // Declare our statement
	              
	              String starname = request.getParameter("name");
	              String query = "SELECT stars.name,stars.birthYear,stars.id,group_concat(distinct movies.title) FROM moviedb.stars,movies,stars_in_movies\r\n" + 
	              		"where stars.name ='"+ starname +"'"+ 
	              		"and stars.id = stars_in_movies.starID\r\n" + 
	              		"and movies.id = stars_in_movies.movieID;";
	              		

	              // Perform the query
	              PreparedStatement statement = dbcon.prepareStatement(query);
	              ResultSet rs = statement.executeQuery();
	              JsonArray ja = new JsonArray();

	              // Iterate through each row of rs
	              out.println("<TABLE border>");
	              out.println("<tr>" + "<td>" + "Star Name" + "</td>" + "<td>" + "Birth Year" + "</td>" + "<td>" +"Star ID"+ "</td>"+"<td>"+"Starred in"+"</td>"
	                      + "</tr>");
	              while (rs.next())
	              {
	            	  
	            	  String star = rs.getString(1);
	                  String birthyear = rs.getString(2);
	                  String starid = rs.getString(3);
	                  String title = rs.getString(4);
	                  System.out.println(title);
	                  String []singletitles = title.split(",");
	                  String singletitle = "";
	                  
	                  for (int i = 0; i<singletitles.length; i++) {
	                	  
	                	  singletitle += "<a href = './singletitle?title="+singletitles[i] +"'>";
	                	  singletitle += singletitles[i];
	                	  singletitle += "</a>\n";
	                	  
	                  }
	                  
	                  System.out.println(singletitles.length +" "+ singletitle);
	                  
	                  out.println("<tr>" + "<td>"  +star + "</td>" + "<td>" + birthyear + "</td>" + "<td>" +starid+ "</td>"+"<td>"+singletitle+"</td>"
	  						
	                          + "</tr>");

	              }
	              out.println("</TABLE>");
	              out.println("</div>");
	              
	              
	             
	              
	              rs.close();
	              statement.close();
	              dbcon.close();
	            }
	        catch (SQLException ex) {
	              while (ex != null) {
	                    System.out.println ("SQL Exception:  " + ex.getMessage ());
	                    ex = ex.getNextException ();
	                }  // end while
	            }  // end catch SQLException

	        catch(java.lang.Exception ex)
	            {
	                out.println("<HTML>" +
	                            "<HEAD><TITLE>" +
	                            "MovieDB: Error" +
	                            "</TITLE></HEAD>\n<BODY>" +
	                            "<P>SQL error in doGet: " +
	                            ex.getMessage() + "</P></BODY></HTML>");
	                return;
	            }
	         out.close();
	    }
	    
	     public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
		doGet(request, response);
		} 
}
