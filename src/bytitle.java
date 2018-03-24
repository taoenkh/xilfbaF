
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

@WebServlet("/bytitle")


public class bytitle extends HttpServlet
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
        out.println("<HTML><HEAD><TITLE>Browse by Title</TITLE>"
        		
        		
        		
        		
                
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

        
        try
           {
	            String title1 = request.getParameter("title");
	            String limit = "20";
	            String sorting = "";
	            if (request.getParameter("limit")!=null) {
		        	  
		        	  
		        	  limit = request.getParameter("limit");
		          }
	            out.println("<div id ='out' style ='background-color:white;opacity:0.9;'>");
	            out.println("<H1>Browsing by Title </H1>");
	            out.println("<div id = 'sorting'>");
	            out.println("<a href = 'bytitle?title="+title1+"&param=titled&limit="+limit+"'>Sort by title (Descending order)</a>");
	            out.println("<a href = 'bytitle?title="+title1+"&param=titlea&limit="+limit+"'>Sort by title (Ascending order)</a>");
	            out.println("<a href = 'bytitle?title="+title1+"&param=yeard&limit="+limit+"'>Sort by Year (Descending order)</a>");
	            out.println("<a href = 'bytitle?title="+title1+"&param=yeara&limit="+limit+"'>Sort by Year (Ascending order)</a>");
	            out.println("</div>");
	              if (request.getParameter("param") != null) {
	            	  sorting = request.getParameter("param");
	              }

	            out.println("<div id = 'page' align = 'center'>");
	              out.println("<a href = 'bytitle?limit=5&title="+title1+"&param="+sorting+"'>5</a>");
	              out.println("<a href = 'bytitle?limit=10&title="+title1+"&param="+sorting+"'>10</a>");
	              out.println("<a href = 'bytitle?limit=15&title="+title1+"&param="+sorting+"'>15</a>");
	              out.println("<a href = 'bytitle?limit=20&title="+title1+"&param="+sorting+"'>20</a>");
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
        			temp += "order by t.title desc";
        		}
        		if (sorting.equals("titlea")) {
        			temp += "order by t.title asc";
        		}
        		if (sorting.equals("yeard")) {
        			temp += "order by t.year desc";
        		}
        		if (sorting.equals("yeara")) {
        			temp += "order by t.year asc";
        		}
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
              //Statement statement = dbcon.createStatement();

              
	          
              int tempoff = Integer.parseInt(limit)*ofi;
              String query = "select t.id, t.title,t.year,t.director,group_concat(distinct stars.name),group_concat(distinct genres.name)\r\n" + 
              		"from (select * from movies where movies.title like '"+ title1+"%') as t, stars,stars_in_movies,genres_in_movies\r\n" + 
              		"left join genres on genres_in_movies.genreId = genres.id\r\n" + 
              		"where stars.id = stars_in_movies.starID\r\n" + 
              		"and stars_in_movies.movieID = t.id\r\n" + 
              		"and t.id = genres_in_movies.movieId\r\n" + 
              		"group by t.id \n"+
              		temp + "\nlimit "+ limit   +
           		 "\n"+ 
           		"offset " + tempoff +";";
              		
              System.out.println(query);

              // Perform the query
              PreparedStatement ps = dbcon.prepareStatement(query);
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
    	              out.println("<a href = 'bytitle?limit="+limit +offsetp+"&title="+title1+"&param="+sorting+"'>Prev</a>");
    	              out.println("<a href = 'bytitle?limit="+limit +offsetn+"&title="+title1+"&param="+sorting+"'>Next</a>");
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
                  out.println("<tr>" + "<td>" +id+ "</td>" + "<td>" + "<a href ='singletitle?title="+title+"&param="+sorting+"'>" +title +"</a>"+ "</td>" + "<td>" +year+ "</td>"+"<td>"+director+"</td>"
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
