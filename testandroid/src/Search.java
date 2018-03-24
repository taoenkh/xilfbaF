
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/androidsearch")


public class Search extends HttpServlet
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
        String loginUser = "taoenkh";
        String loginPasswd = "wangtao1";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=true";

        response.setContentType("text/html");    // Response mime type
        //User a = (User) request.getSession().getAttribute("user");
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        //System.out.println(a.password+ a.getUsername());
   

        
        try
           {
        	ArrayList<Movies> movieList = new ArrayList<Movies>(); 
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();
              
              String title1 = "";

              String limit = "5";
             
              int pgnum = 0;

            		 
              title1 = request.getParameter("title");
              

	          
              
              

	          
          
	          String offset = "0";
	          
	          
	          if (request.getParameter("offset") != null) {
	        	  
	        	  offset = request.getParameter("offset");
	        	  pgnum = Integer.parseInt(offset);
	          }

             
              

				

              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Statement statement = dbcon.createStatement();
              
	          

	          String othercondition = "";
	          String ttle = "";

	          String titles[] = title1.split(" ");
	          String titleq = "";
	          for (String i : titles) {
	        	  titleq+= "+"+i+"* ";
	          }
	          
	          
	        	  
	          
	          
	          
	          int tempoff = (pgnum-1)*5;
              String query = "SELECT n.id,title, year, director,group_concat(distinct stars.name ),group_concat(distinct genres.name)\r\n" + 
              		"from (select* from movies where match(title) against ('"+titleq+"'in boolean mode)"+" or title like '%"+title1+"%' or edth(title,'"+title1+"',"+Math.max(title1.length()/4,1)+") = 1) as n,stars,stars_in_movies,genres_in_movies\r\n" + 
          		  
              		"left join genres on genres_in_movies.genreId = genres.id\r\n" + 
              		"where stars.id = stars_in_movies.starID\r\n" + 
              		"and stars_in_movies.movieID = n.id\r\n" + 
              		"and n.id = genres_in_movies.movieId\r\n" +
              		
              		"group by n.id\n"+
              		 
              		"\nlimit "+ limit   +
              		 "\n"+ 
              		"offset " + tempoff +";";

              ResultSet rs = statement.executeQuery(query);
	          int count = 0;

	          
	         
              		
              System.out.println(query);
              // Perform the query
              
              
              
              
              JsonArray ja = new JsonArray();
              
              

              boolean flag = false;
              
              while (rs.next())
            	  
              {		
            	  count ++;
            	  if (! flag) {

              }
            	  if (count == 1) {
            	 

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
                  String[] singlegenre = genre.split(",");
                  ArrayList<String> starsa = new ArrayList<String>(Arrays.asList(singlestars));
                  ArrayList<String> genrea = new ArrayList<String>(Arrays.asList(singlegenre));
                  Movies tempm = new Movies(id,title,year,director,genrea,starsa);
                  System.out.println(tempm.getYear()+" year");
                  movieList.add(tempm);
                 
                  

                  //System.out.println(id + title + year + director + stars + genre +rs.next());


                 
              }
              int size = 0;
              
              String queryc = "select count(*) from movies where MATCH(title) against ('"+titleq+"' in boolean mode) or title like '%"+title1+"%'" + " or SIMILARTO(title,'"+title1+"',"+Math.max((int)title1.length()/4,1)+") = 1" +" limit 5;\r\n"; 

              
              
              System.out.println(queryc);
              ResultSet rs1 =statement.executeQuery(queryc);
 
              
              if (rs1.next()) {
            	  
            	  size = rs1.getInt(1);
              }
              System.out.println(queryc);
              if (count == 0) {
            	  

            	  
            	  
              }
              Movielist mymovielist = new Movielist(movieList,size,title1,pgnum);
              String listOfJsonm = new Gson().toJson(mymovielist);
              
              out.println(listOfJsonm);

              //}
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
