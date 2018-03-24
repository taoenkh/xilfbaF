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


@WebServlet("/checkout")

public class checkout extends HttpServlet {
	
	
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
	        out.println("<H1>Check out</H1>");
	        try
	           {
	              //Class.forName("org.gjt.mm.mysql.Driver");
	              //Class.forName("com.mysql.jdbc.Driver").newInstance();
	              HashMap <String ,Integer>temp = (HashMap<String, Integer>)(request.getSession().getAttribute("cart"));
	              //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	  			Context initCtx = new InitialContext();


	            Context envCtx = (Context) initCtx.lookup("java:comp/env");

	            
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/Write");
	            //System.out.println(ds.toString()+" "+"");

	            System.out.println("dsad");
	            Connection dbcon = ds.getConnection();
	              // Declare our statement
	              Statement statement = dbcon.createStatement();
	              User a = (User) request.getSession().getAttribute("user");
	              String username = a.getUsername();
	              String getcustid = "SELECT id,ccId from customers where customers.email ='"+username+"';";
	              ResultSet rs0 = statement.executeQuery(getcustid);
	              
	              String custid = "";
	              String fname = "";
	              String lname = "";
	              String creditc = "";
	              
	              while (rs0.next()) {
	            	  custid = rs0.getString(1);
	            	  creditc = rs0.getString(2);
	            	  
	              }
	              String getccinfo = "SELECT * FROM creditcards where creditcards.id = '"+ creditc +"'; ";
	              ResultSet rs = statement.executeQuery(getccinfo);
	              
	              
	              rs.next();	            	 
	              String id = rs.getString(1);
	              String firsts = rs.getString(2);
	              String lastts = rs.getString(3);
	              String expire = rs.getString(4);
	              
	              
	              out.println(""
	              		+ "			<div id = \"from\" align = \"center\">\r\n" + 
	              		"				\r\n" + 
	              		"				<h2>You are almost there</h2>\r\n" + 
	              		"				<FORM id = \"checkoutform\"  action =\"checkout\" METHOD=\"GET\">\r\n" + 
	              		"				  <label>FirstName:</label> \r\n" + 
	              		"				  <INPUT TYPE=\"TEXT\" NAME=\"fname\" required autofocus> <br/>\r\n" + 
	              		"				  <label>LastName:</label>\r\n" + 
	              		"				   <INPUT TYPE=\"Text\" NAME=\"lname\" required><br />\r\n" + 
	              		"				   \r\n" + 
	              		"				   				  <label>CreditCard:</label>\r\n" + 
	              		"				   <INPUT TYPE=\"Text\" NAME=\"ccid\" required><br />\r\n" + 
	              		"				   \r\n" + 
	              		"				   				  <label> Expiration Date:</label>\r\n" + 
	              		"				   <INPUT TYPE=\"Text\" NAME=\"expdate\" required><br />\r\n" + 
	              		"				   \r\n" + 
	              		"				  \r\n" + 
	              		"				   <INPUT TYPE=\"SUBMIT\" VALUE=\"Checkout\">\r\n" + 
	              		"				  \r\n" + 
	              		"				</FORM>\r\n" + 
	              		"				\r\n" + 
	              		"				\r\n" + 
	              		"\r\n" + 
	              		"			</div>"
	              		+ ""

	              		+ "");
	              
	              
	              String efname = request.getParameter("fname");
	              String elname = request.getParameter("lname");
	              String eccid = request.getParameter("ccid");
	              String eexpt = request.getParameter("expdate");
	
	              if (efname != null) {
	              
	              if (id.equals(eccid) && firsts.equals(efname) && lastts.equals(elname) && expire.equals(eexpt)) {
	            	  
	            	  
	            	  
	            	  
	            	 for (HashMap.Entry<String, Integer> entry : temp.entrySet()) {
	            		  String exe = "select id from movies where movies.title='"+entry.getKey()+"';";
	            		  rs = statement.executeQuery(exe);
	            		  
	            		  rs.next();
	            		  String movid = rs.getString(1);
	            		  for (int i = 0;i<entry.getValue();i++) {
	            			  String query = "INSERT INTO sales(customerId,movieId,saleDate) VALUES('"+custid+"','"+movid+"', curdate());";
	            			  statement.executeUpdate(query);
	            			  System.out.println(query);
	            		  }
	            	  
	            	  }
	            	 
	            	 HashMap <String,Integer> n = new HashMap<String,Integer>();
	            	 request.getSession().setAttribute("cart", n);
	            	 out.println("<h1>Successfully paid!</h1>");
	            	 out.println("<a href ='/cs122b' >Back to Home Page</a>");
	            	  
	            	  
	              }
	              else {
	            	  out.println("<h1>Please enter correct information!</h1>");
	            	  out.println("<a href = shoppingcart>Back to Cart</a>");
	            	  
	              }
	            	  
	              }
	              // Iterate through each row of rs

	              
	              
	              
	              rs.close();
	              rs0.close();
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
