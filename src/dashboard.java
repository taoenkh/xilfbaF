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



@WebServlet("/_dashboard")

public class dashboard extends HttpServlet {
	
	
	 public String getServletInfo()
	    {
	       return "Servlet connects to MySQL database and displays result of a SELECT";
	    }

	    // Use http GET

	    public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
	    	PrintWriter out = response.getWriter();
	    	response.setContentType("text/html");
	    	User emp = (User)request.getSession().getAttribute("user"); 
	    	if (emp.getUsername().equals("classta@email.edu")) {
//		        String loginUser = "taoenkh";
//		        String loginPasswd = "wangtao1";
//		        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=true";

		            // Response mime type

		        // Output stream to STDOUT
		        
		        out.println("<HTML><HEAD><TITLE>Dashboard</TITLE>"
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
		        		
		        		"        <h1 id=\"logo\"><a href=\"/cs122b/index.html\"></a></h1>\r\n" + 
		        		"        <br>\r\n" + 
		        		"        <br>\r\n" + 
		        		"        <br>\r\n" + 
		        		"        <br>\r\n" + 
		        		"      </div>"
		        		 
		        		+ ""
		        		+ "");
		        out.println("<div id ='out' style ='background-color:white;opacity:0.9;'>");
		        out.println("<H1>Dashboard</H1>");
		        try
		           {
		              //Class.forName("org.gjt.mm.mysql.Driver");
		              //Class.forName("com.mysql.jdbc.Driver").newInstance();

					Context initCtx = new InitialContext();


		            Context envCtx = (Context) initCtx.lookup("java:comp/env");

		            
		            DataSource ds = (DataSource) envCtx.lookup("jdbc/Write");
		            //System.out.println(ds.toString()+" "+"");

		            System.out.println("dsad");
		            Connection dbcon = ds.getConnection();
		              // Declare our statement
		              Statement statement = dbcon.createStatement();
		              String starname = request.getParameter("name");
		             
		              		

		              // Perform the query
		              
		              

		              // Iterate through each row of rs

		              
		              out.println("<div id = \"from\" align = \"center\">");
		              out.println("<h2>Add Star</h2>\r\n" + 
		              		"	<form action = \"_dashboard\" method = \"get\" >\r\n" + 
		              		"	<label>Star Name:</label>\r\n" + 
		              		"	<input type = \"text\" name = \"s1name\" required>\r\n" + 
		              		"	<br/>\r\n" + 
		              		"	<br/>\r\n" + 
		              		"	<label>Birth Year:</label>\r\n" + 
		              		"	<input type = \"number\" name = \"birthyear\">"
		              		+ "<br/>"
		              		+"<INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\r\n"
		              		+ "</form>"
		              		+ ""
		              		+ "");
		              

		              out.println("			\r\n" + 
		              		"				\r\n" + 
		              		"				<h2>Adding a movie</h2>\r\n" + 
		              		"				<FORM id = \"addform\"  action =\"_dashboard\" METHOD=\"GET\">\r\n" + 
		              		"				  <label>Movie Name:</label> \r\n" + 
		              		"				  <INPUT TYPE=\"TEXT\" NAME=\"mname\" required autofocus> <br/><br/>\r\n" + 
		              		"				  <label>Director:</label>\r\n" + 
		              		"				   <INPUT TYPE=\"Text\" NAME=\"dname\" required><br /><br/>\r\n" + 
		              		"				   \r\n" + 
		              		"				   				  <label>Genre:</label>\r\n" + 
		              		"				   <INPUT TYPE=\"Text\" NAME=\"genre\" required><br /><br/>\r\n" + 
		              		"				   \r\n" + 
		              		"				   				  <label> Year:</label>\r\n" + 
		              		"				   <INPUT TYPE=\"number\" NAME=\"year\" required><br /><br/>\r\n" +
		              		 "<label> Star Name:</label>\r\n" + 
			              		"				   <INPUT TYPE=\"Text\" NAME=\"starname\" required><br /><br/>\r\n"+
			              		"<label> Birth Year:</label>\r\n" + 
			              		"				   <INPUT TYPE=\"number\" NAME=\"starbyr\" required><br /><br/>\r\n"+
		              		"				   \r\n" + 
		              		"				  \r\n" + 
		              		"				   <INPUT TYPE=\"SUBMIT\" VALUE=\"Add\">\r\n" + 
		              		"				  \r\n" + 
		              		"				</FORM>\r\n" + 
		              		"				\r\n" + 
		              		"				\r\n" + 
		              		"\r\n" + 
		              		"			");   

		              System.out.println("here");
		              boolean starerror = false;
		              boolean movieerror = false;
		              boolean success1 = false;
		              String Starname = "";
		              String sy ="";
		              String errors = "";
		              if (request.getParameter("s1name")!=null  &&request.getParameter("birthyear") !=null) {
			              Starname = request.getParameter("s1name");
			              sy = request.getParameter("birthyear");
			              int staryear = Integer.parseInt(sy);
			              CallableStatement starc = dbcon.prepareCall("{call add_star(?,?)}");
			              starc.setString(1, Starname);
			              starc.setInt(2, staryear);
			              starc.execute();
			              System.out.println("andhere");
			              ResultSet rs1 = starc.getResultSet();
			              System.out.println("andhere1");
			              if (rs1 != null) {
				              while (rs1.next()) {
				            	  System.out.print("star wrong");
				            	  
				            		  errors = rs1.getString(1);
				            		  starerror = true;
				            	  
				            	  
				              }
			              }
			              else {
			            	  success1 = true;
			              }
			              System.out.println("andhere2");
		              }
		              
		              String error = "";
		              
		              String years = "";
		              String birthye= "";
		              String moviename = "";
		              String director = "";
		              String genre = "";
		              System.out.println(request.getParameter("year")+" this");
		              boolean success = false;
		             if (request.getParameter("year") != null) {
		             
			              years = request.getParameter("year");
			              birthye = request.getParameter("starbyr");
			              moviename = request.getParameter("mname");
			              director = request.getParameter("dname");
			              genre = request.getParameter("genre");
			              int year = Integer.parseInt(years.trim());
			              String sname = request.getParameter("starname");
			              int byear1 = Integer.parseInt(birthye.trim());
			              CallableStatement mycall = dbcon.prepareCall("{call add_movie(?,?,?,?,?,?)}");
			              
			              mycall.setString(1,moviename);
			              mycall.setString(2,director);
			              mycall.setString(3,genre);
			              mycall.setInt(4,year);
			              mycall.setString(5,sname);
			              mycall.setInt(6,byear1);
			       
			              mycall.execute();
			              ResultSet rs0 = mycall.getResultSet();
			              if (rs0 != null) {
				              while (rs0.next()) {
				            	  
				            	  error = rs0.getString(1);
				            	  movieerror = true;
				              }
			              }else {
			            	  success = true;
			              }
			              
			              mycall.close();
		             }
		             
		             
		              if (movieerror) {
		            	  out.println("<h3>"+  error   +"</h3>");
		            	  
		              }
		              if (success) {
		            	  out.println("<h3>"+  "Successfully added movie!"  +"</h3>");
		            	  
		            	  
		              }
		              if (success1) {
		            	  out.println("<h3>"+  "Successfully added star!"   +"</h3>");
		              }
		              
		              if (starerror) {
		            	  out.println("<h3>"+  errors   +"</h3>");
		            	  
		            	  
		              }
		              
		              String query1 = "creditcards;";
		              String query2 = "customers;";
		              String query3 = "employee;";
		              String query4 = "genres;";
		              String query5 = "genres_in_movies;";
		              String query6 = "movies;";
		              String query7 = "ratings;";
		              String query8 = "sales;";
		              String query9 = "stars;";
		              String query10 = "stars_in_movies;";
		              
		              List<String> queries = Arrays.asList(query1,
		            		  query2,query3,query4,query5,query6,query7,query8,query9,query10);
		              
		              for (String i :queries) {
		            	  String qr = "SHOW COLUMNS FROM moviedb.";
		            	  
		            	 ResultSet rs = statement.executeQuery(qr+i);
		              out.println("<h3>" +i.substring(0,1).toUpperCase() + i.substring(1,i.length()-1)+"</h3>");
		              out.println("<TABLE border>");
		              out.println("<tr>" + "<td>" + "Field" + "</td>" + "<td>" + "Type" 
		                      + "</tr>");
		              while (rs.next()) {
		            	  String field = rs.getString(1);
		            	  String type = rs.getString(2);
		            	  
		                  out.println("<tr>" + "<td>"  +field + "</td>" + "<td>" + type + "</td>" 
			  						
	                          + "</tr>");
		            	  
		              }
		              out.println("</TABLE>");
		              
		              rs.close();
		              }

		              
		              
		              out.println("</div>");
		              
		              
			             out.println("</BODY>"
				             		+ ""
				             		+ "</HTML>");
		             // rs.close();
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
	    	else {
	    		
	    		
	    		out.println("<HTML><HEAD><TITLE>404 Not found</TITLE>"
	    				+ "</HEAD>"
	    				+ "<body>"
	    				+ "<h1>404 Not Found</h1>"
	    				+ ""
	    				+ ""
	    				+ ""
	    				+ "</body>"
	    				+ ""
	    				+ "</HTML>"
	    				+ "");
	    	}

	    }
	    
	     public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
		doGet(request, response);
		} 
}
