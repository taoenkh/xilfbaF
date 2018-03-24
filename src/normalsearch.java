
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

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

@WebServlet("/normalsearch")


public class normalsearch extends HttpServlet
{
	
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    @SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        long TJ = 0;
        long TS = 0;
        long Time1 = System.nanoTime();
        response.setContentType("text/html");    // Response mime type
        //User a = (User) request.getSession().getAttribute("user");
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println(a.password+ a.getUsername());
   
        out.println("<HTML><HEAD><TITLE>Search</TITLE>"
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
        out.println("<H1>MovieDB: movies found </H1>");
        
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              
              out.println("<div id = 'sorting'>");
              String title1 = "";
              String year1 = "";
              String director1 = "";
              String star = "";
              String limit = "20";
              String sorting = "";
              if (request.getParameter("limit")!=null) {
	        	  
	        	  
	        	  limit = request.getParameter("limit");
	          }

            		 
              title1 = request.getParameter("title");


	          
              out.println("<a href = 'normalsearch?title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param=titled&limit="+limit+"'>Sort by title (Descending order)</a>");
              out.println("<a href = 'normalsearch?title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param=titlea&limit="+limit+"'>Sort by title (Ascending order)</a>");
              out.println("<a href = 'normalsearch?title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param=yeard&limit="+limit+"'>Sort by year (Descending order)</a>");
              out.println("<a href = 'normalsearch?title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param=yeara&limit="+limit+"'>Sort by year (Ascending order)</a>");
              out.println("</div>");
              if (request.getParameter("param") != null) {
            	  sorting = request.getParameter("param");
              }
              
              
              out.println("<div id = 'page' align = 'center'>");
              out.println("<a href = 'normalsearch?limit=5&title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param="+sorting+"'>5</a>");
              out.println("<a href = 'normalsearch?limit=10&title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param="+sorting+"'>10</a>");
              out.println("<a href = 'normalsearch?limit=15&title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param="+sorting+"'>15</a>");
              out.println("<a href = 'normalsearch?limit=20&title=" +title1+"&year="+year1+"&director="+director1+"&name="+star+"&param="+sorting+"'>20</a>");
              out.println("</div>");
	          
              
              
	          if (request.getParameter("limit")!=null) {
	        	  
	        	  
	        	  limit = request.getParameter("limit");
	          }
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
              try {
            	  
            	  
            	  
            	 
  				if (sorting.equals("titled")) {
  					temp += "order by n.title desc";
  				}
  				if (sorting.equals("titlea")) {
  					temp += "order by n.title asc";
  				}
  				if (sorting.equals("yeard")) {
  					temp += "order by n.year desc";
  				}
  				if (sorting.equals("yeara")) {
  					temp += "order by n.year asc";
  				}
              }catch(Exception e){
            	  System.out.println("no param for now");
            	  
              }
				
  	        String loginUser = "taoenkh";
  	        String loginPasswd = "wangtao1";
  	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=true";
              Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              long Querytime = System.nanoTime();
//  			Context initCtx = new InitialContext();
//
//
//            Context envCtx = (Context) initCtx.lookup("java:comp/env");
//
//            
//            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            //System.out.println(ds.toString()+" "+"");

            System.out.println("dsad");
            //Connection dbcon = ds.getConnection();
              // Declare our statement
              
              
	          
	          System.out.println(title1);
	          System.out.println(year1.equals(""));
	          System.out.println(director1);
	          System.out.println(star);
	          String titles[] = title1.split(" ");
	          String titleq = "";
	          for (String i : titles) {
	        	  titleq+= "+"+i+"* ";
	          }
	          
	          
	        	  
	          String titlefuz = "";
	          for (String i : titles) {
	        	  titlefuz += i;
	          }
	          
	          
	          int tempoff = Integer.parseInt(limit)*ofi;
              String query = "SELECT n.id,title, year, director,group_concat(distinct stars.name ),group_concat(distinct genres.name)\r\n" + 
                		"from (select* from movies where match(title) against ('"+titleq+"'in boolean mode)"+" or title like '%"+title1+"%' or edth(title,'"+title1+"',"+Math.max(title1.length()/4,1)+") = 1) as n,stars,stars_in_movies,genres_in_movies\r\n" + 
            		  
                		"left join genres on genres_in_movies.genreId = genres.id\r\n" + 
                		"where stars.id = stars_in_movies.starID\r\n" + 
                		"and stars_in_movies.movieID = n.id\r\n" + 
                		"and n.id = genres_in_movies.movieId\r\n" +
                		
                		"group by n.id\n"+
                		temp +
                		"\nlimit "+ limit   +
                		 "\n"+ 
                		"offset " + tempoff +";";
              System.out.println(query);
              PreparedStatement statement = dbcon.prepareStatement(query);
              ResultSet rs = statement.executeQuery();
	          int count = 0;
	          if (title1.equals("") && year1.equals("") && director1.equals("")&& star.equals("")) {
	        	  
	        	  
	        	  
	        	  
	        	  out.println("<h1>Please Enter atleast one field!</h1>");
	          }
	          else {
	         
              		
              System.out.println(query);
              // Perform the query
              
              
              
              
              JsonArray ja = new JsonArray();
              
              
              out.println("<TABLE border >");
              //System.out.println(rs.next() + "one");
//              if (!rs.next()) {
//            	  out.println("<h3>No result found</h3>");
//            	  
//              }
//              else {
            	  
              // Iterate through each row of rs
              boolean flag = false;
              
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
                  out.println("<a href = 'normalsearch?limit="+limit+"&title=" +title1+offsetp+"&year="+year1+"&director="+director1+"&name="+star+"&param="+sorting+"'>Prev</a>");
                  out.println("<a href = 'normalsearch?limit="+limit+"&title=" +title1+offsetn+"&year="+year1+"&director="+director1+"&name="+star+"&param="+sorting+"'>Next</a>");
                  out.println("</div>");
            	  }
              		flag = true;
            	  //System.out.println(rs);
            	  String id = rs.getString(1);
                  String title = rs.getString(2);
                  String year = rs.getString(3);
                  String director = rs.getString(4);
                  String stars = rs.getString(5);
                  String genre = rs.getString(6);
                  String[] singlestars = stars.split(",");
                  
                  
                  String singlestar = "";
                  for (int i = 0; i<singlestars.length; i++) {
                	  
                	  singlestar += "<a href = './singlestar?name="+singlestars[i]+"'>";
                	  singlestar += singlestars[i];
                	  singlestar += "</a>\n";
                	  
                  }
                  //System.out.println(id + title + year + director + stars + genre +rs.next());

                  out.println("<tr>" + "<td>" +id+ "</td>" + "<td>" + "<a href ='./singletitle?title="+title+"'>"  +title +"</a>"+ "</td>" + "<td>" +year+ "</td>"+"<td>"+director+"</td>"
  						+"<td>"+singlestar+"</td>" + "<td>"+genre+"</td>"+"<td><a href = '/cs122b/shoppingcart?tobuy="+title+"'> Add to cart</a></td>"
                          + "</tr>");
                 
              }
	          }
              if (count == 0) {
            	  
            	  out.println("<h3>No result found</h3>");
            	  out.println("<a href='/cs122b' >Back to Search</a>");
            	  
            	  
              }
              //out.write(ja.toString());
              out.println("</TABLE>");
              out.println("</div>");
              //}
              rs.close();
              statement.close();
              dbcon.close();
              TJ = System.nanoTime() - Querytime;
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
         
         
         TS = System.nanoTime() - Time1;
         
//         String TJtext = "C:\\Users\\NEW\\Desktop\\Time\\TJ.txt";
//         String TStext = "C:\\Users\\NEW\\Desktop\\Time\\TS.txt";
         
         String TJtext = "/home/ubuntu/time/TJ.txt";
         String TStext = "/home/ubuntu/time/TS.txt";
         
         try {
     		FileOutputStream write1 = new FileOutputStream(TJtext , true);
    		
    		PrintStream print1 = new PrintStream(write1);
    		
    		FileOutputStream write2 = new FileOutputStream(TStext, true);
    		
    		PrintStream print2 = new PrintStream(write2);
        	 
        	print1.println(TJ);
        	print2.println(TS);
        	print1.close();
        	print2.close();
 
        	 
        	 
         }catch (Exception e) {
        	 System.out.println(e);
         }
    }
    
     public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
	} 
}
