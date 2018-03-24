
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

@WebServlet("/genre")


public class genres extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        

        response.setContentType("application/json");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
//              Class.forName("com.mysql.jdbc.Driver").newInstance();
//
//              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
//              // Declare our statement
//              Statement statement = dbcon.createStatement();
			Context initCtx = new InitialContext();


            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            //System.out.println(ds.toString()+" "+"");

            System.out.println("dsad");
            Connection dbcon = ds.getConnection();
            
            Statement statement = dbcon.createStatement();


	          
	          
              String query = "select name from genres;";
              		

              // Perform the query
              ResultSet rs = statement.executeQuery(query);
              JsonArray ja = new JsonArray();

              // Iterate through each row of rs
             
              while (rs.next())
              {
            	  String gname = rs.getString(1);
                 
                  JsonObject jsonObject = new JsonObject();
                 
                  jsonObject.addProperty("genre", gname);
                  ja.add(jsonObject);

              }
              out.write(ja.toString());
             
              
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
