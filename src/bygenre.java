
/* A servlet to display the contents of the MySQL movieDB database */

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

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

@WebServlet("/bygenre")


public class bygenre extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
//        String loginUser = "taoenkh";
//        String loginPasswd = "wangtao1";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=true";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>Browse by Genre</TITLE>"
        
                + "<link rel=\"stylesheet\" href=\"./css/style.css\" type=\"text/css\"  />"
        		+
        		"</HEAD>");
        out.println("<BODY>"
        		+ "<div id=\"header\" align=\"left\">\r\n" + 
        		"<span id = \"chart\"><a href =\"/cs122b/shoppingcart?tobuy=\"></a></span>"+
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
        out.println("<H1>Browsing by Genre</H1>");
        
	
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
             // Class.forName("com.mysql.jdbc.Driver").newInstance();
              out.println("<div id = 'sorting'>");
              String genre = request.getParameter("genre");
              String limit = "20";
              String sorting = "";
              if (request.getParameter("limit")!=null) {
	        	  
	        	  
	        	  limit = request.getParameter("limit");
	          }
              
              out.println("<a href = 'bygenre?genre="+genre+"&limit="+limit+"&param=titled'>Sort by title (Descending order)</a>");
              out.println("<a href = 'bygenre?genre="+genre+"&limit="+limit+"&param=titlea'>Sort by title (Ascending order)</a>");
              out.println("<a href = 'bygenre?genre="+genre+"&limit="+limit+"&param=yeard'>Sort by Year (Descending order)</a>");
              out.println("<a href = 'bygenre?genre="+genre+"&limit="+limit+"&param=yeara'>Sort by Year (Ascending order)</a>");
              out.println("</div>");	
              
              if (request.getParameter("param") != null) {
            	  sorting = request.getParameter("param");
              }
              out.println("<div id = 'page' align = 'center'>");
              out.println("<a href = 'bygenre?limit=5&genre="+genre+"&param="+sorting+"'>5</a>");
              out.println("<a href = 'bygenre?limit=10&genre="+genre+"&param="+sorting+"'>10</a>");
              out.println("<a href = 'bygenre?limit=15&genre="+genre+"&param="+sorting+"'>15</a>");
              out.println("<a href = 'bygenre?limit=20&genre="+genre+"&param="+sorting+"'>20</a>");
              out.println("</div>");
              
              
             
	          String offset = "0";
	          
	          if (request.getParameter("offset") != null) {
	        	  
	        	  offset = request.getParameter("offset");
	          }
	          int ofi  = Integer.parseInt(offset);
	          if (ofi - 1 <0) {
	        	  ofi = 0;
	          }
              String offsetn = "&offset="+(++ofi);
              String offsetp = "&offset="+(ofi-=2);
              ofi+=1;
              
              
				String temp = "";
				if (sorting.equals("titled")) {
					temp += "order by movies.title desc";
				}
				if (sorting.equals("titlea")) {
					temp += "order by movies.title asc";
				}
				if (sorting.equals("yeard")) {
					temp += "order by movies.year desc";
				}
				if (sorting.equals("yeara")) {
					temp += "order by movies.year asc";
				}
              //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
				Context initCtx = new InitialContext();


	            Context envCtx = (Context) initCtx.lookup("java:comp/env");

	            
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	            //System.out.println(ds.toString()+" "+"");

	            System.out.println("dsad");
	            Connection dbcon = ds.getConnection();
	            
             

              
	          
              int tempoff = Integer.parseInt(limit)*ofi;
              String query = ""
              		+ "select movies.id, movies.title,movies.year,movies.director,group_concat(distinct stars.name),group_concat(distinct genres.name) from  (select movies.id as gid from movies,genres,genres_in_movies \r\n" + 
              		"where movies.id = genres_in_movies.movieId\r\n" + 
              		"and genres.id = genres_in_movies.genreId \r\n" + 
              		"and genres.name = '"+genre+"') as g, genres,genres_in_movies,stars,stars_in_movies,movies\r\n" + 
              		"where movies.id = genres_in_movies.movieId\r\n" + 
              		"and genres.id = genres_in_movies.genreId\r\n" + 
              		"and movies.id = g.gid\r\n" + 
              		"and g.gid = stars_in_movies.movieID\r\n" + 
              		"and stars_in_movies.starID = stars.id\r\n" + 
              		"group by movies.id\n"
              		+ 
              		temp + 
              		"\nlimit "+ limit   +
                	"\n"+ 
                	"offset " + tempoff +";";
              PreparedStatement ps = dbcon.prepareStatement(query);
              System.out.println(query);
              // Perform the query
              ResultSet rs = ps.executeQuery();
              JsonArray ja = new JsonArray();

              // Iterate through each row of rs
              out.println("<TABLE border>");
              
              boolean flag = false;
              int count = 0;
              while (rs.next())
              {	
            	  count ++;
            	  if (! flag) {
            	  out.println("<tr>" + "<td>" + "Id" + "</td>" + "<td>" + "Title" + "</td>" + "<td>" +"Year"+ "</td>"+"<td>"+"Director"+"</td>"
                          +"<td>Stars</td> <td>Genres</td>"+ "</tr>");
              }
            	  if (count == 1) {
            		  out.println("<div id = 'next' align = 'center'>");
                      System.out.println(offsetn+"offn");
                      System.out.println(offsetp+"offp");
                      out.println("<a href = 'bygenre?limit="+limit +offsetp+"&genre="+genre+"&param="+sorting+"'>Prev</a>");
                      out.println("<a href = 'bygenre?limit="+limit +offsetn+"&genre="+genre+"&param="+sorting+"'>Next</a>");
                      out.println("</div>");
            	  }
            	  flag = true;
            	  String id = rs.getString(1);
                  String title = rs.getString(2);
                  String year = rs.getString(3);
                  String director = rs.getString(4);
                  String stars = rs.getString(5);
                  String genre1 = rs.getString(6);
                  String[] singlestars = stars.split(",");
                  
                  
                  String singlestar = "";
                  for (int i = 0; i<singlestars.length; i++) {
                	  
                	  singlestar += "<a href = 'singlestar?name="+singlestars[i]+"'>";
                	  singlestar += singlestars[i];
                	  singlestar += "</a>\n";
                	  
                  }
                  
                  
                  out.println("<tr>" + "<td>" +id+ "</td>" + "<td>" + "<a href ='singletitle?title="+title+"'>" +title +"</a>"+ "</td>" + "<td>" +year+ "</td>"+"<td>"+director+"</td>"
    						+"<td>"+singlestar+"</td>" + "<td>"+genre1+"</td>"+"<td><a href = '/cs122b/shoppingcart?tobuy="+title+"'> Add to cart</a></td>"
                            + "</tr>");
              }
              //out.write(ja.toString());
              out.println("</TABLE>");
              out.println("</div>");
             
              if (count == 0) {
            	  out.println("<div style ='background-color:white;opacity:0.9;'>");
            	  out.println("<h3>No result found</h3>");
            	  out.println("<a href='/cs122b' >Back to Search</a>");
            	  out.println("</div>");
            	  
            	  
              }
              rs.close();
              ps.close();
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
