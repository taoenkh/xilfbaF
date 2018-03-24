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


@WebServlet("/singletitle")

public class Singletitle extends HttpServlet {
	
	
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
	        out.println("<HTML><HEAD><TITLE>Movie Details</TITLE>"
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
	        out.println("<H1>Movie Details</H1>");
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
	              
	              String title1 = request.getParameter("title");
	              String query = "SELECT title, year, director,r.rating,group_concat(distinct stars.name),group_concat(distinct genres.name)\r\n" + 
	              		"from (select* from ratings,movies  \r\n" + 
	              		"where ratings.movieId = movies.id and movies.title ='"+ title1+"'"+ 
	              		"order by rating) as r,stars,stars_in_movies,genres_in_movies\r\n" + 
	              		"left join genres on genres_in_movies.genreId = genres.id\r\n" + 
	              		"where stars.id = stars_in_movies.starID\r\n" + 
	              		"and stars_in_movies.movieID = r.id\r\n" + 
	              		"and r.id = genres_in_movies.movieId\r\n" + 
	              		"group by r.id\r\n;";
	              		

	              // Perform the query
	              PreparedStatement statement = dbcon.prepareStatement(query);
	              ResultSet rs = statement.executeQuery();
	              JsonArray ja = new JsonArray();

	              // Iterate through each row of rs
	              out.println("<TABLE border>");
	              out.println("<tr>" + "<td>" + "Title" + "</td>" + "<td>" + "Year" + "</td>" + "<td>" +"Director"+ "</td>"+"<td>"+"Rating"+"</td>"
	                      +"<td>Stars</td> <td>Genres</td>"+ "</tr>");
	              while (rs.next())
	              {
	            	  
	            	  String title = rs.getString(1);
	                  String year = rs.getString(2);
	                  String director = rs.getString(3);
	                  String rating = rs.getString(4);
	                  String stars = rs.getString(5);
	                  String genre = rs.getString(6);
	                  String[] singlestars = stars.split(",");
	                  
	                  
	                  String singlestar = "";
	                  for (int i = 0; i<singlestars.length; i++) {
	                	  
	                	  singlestar += "<a href = './singlestar?name="+singlestars[i]+"'>";
	                	  singlestar += singlestars[i];
	                	  singlestar += "</a>\n";
	                	  
	                  }
	                  String[] singlegenres = genre.split(",");
	                  String singlegenre = "";
	                  for (int i = 0; i < singlegenres.length;i++) {
	                	  singlegenre += "<a href = './bygenre?genre=" + singlegenres[i] +"'>";
	                	  singlegenre += singlegenres[i];
	                	  singlegenre += "</a>\n";
	                	  
	                	  
	                	  
	                  }
	                  
	                  
	                  
	                  
	                  out.println("<tr>" + "<td>" + title + "</td>" + "<td>" + year + "</td>" + "<td>" +director+ "</td>"+"<td>"+rating+"</td>"
	  						+"<td>"+singlestar+"</td>" + "<td>"+singlegenre+"</td>"+"<td><a href = '/cs122b/shoppingcart?tobuy="+title+"'> Add to cart</a></td>"
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
