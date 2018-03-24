
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



@WebServlet("/shoppingcart")
public class shoppingcart extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static void clearcart(HttpServletRequest request) {
		 HashMap<String,Integer> emp = new HashMap<String,Integer>();
		 request.getSession().setAttribute("cart", emp);
	}
	public String getServletInfo() {
        return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String loginUser = "taoenkh";
//        String loginPasswd = "wangtao1";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=true";
        
        response.setContentType("text/html"); // Response mime type
       
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
        out.println("<HTML><HEAD><TITLE>Shopping Cart</TITLE>"
        		+ "<link rel=\"stylesheet\" href=\"./css/style.css\" type=\"text/css\"  />"
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

       

        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
        	
        	String params = "";
        	System.out.println(request.getParameter("clear")+"here");
        	if (request.getParameter("tobuy") == null) {
        		params = request.getParameter("clear");
        		clearcart(request);
        		
        		out.println("<div align = 'center' style ='background-color:white;opacity:0.9;'><h1>Shopping Cart cleared</h1>");
        		
        		out.println("<a href = '/cs122b'>Back to Home</a></div>");
        	}
        	else {
        			
        	
        	HashMap <String ,Integer>temp = (HashMap<String, Integer>)(request.getSession().getAttribute("cart"));
        	System.out.println(temp.size());
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
			Context initCtx = new InitialContext();


            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            //System.out.println(ds.toString()+" "+"");

            System.out.println("dsad");
            Connection dbcon = ds.getConnection();
            Statement statement = dbcon.createStatement();
            

            
            String getid = "";
            String movname = "";
            if (request.getParameter("tobuy")!= null && !request.getParameter("tobuy").equals("")) {
            	int num = 1;
            	movname = request.getParameter("tobuy");
            	
                String add = "";
                if (request.getParameter("num") == null) {
	            	if (temp.get(movname) != null) {
	            		temp.put(movname,temp.get(movname) +1);
	            	}else {
	            		
	            		temp.put(movname,num);
	            	}
                }
                if (request.getParameter("num")!=null) {
                	add = request.getParameter("num");
                	System.out.println(add);
                	if (add.equals("p1")) {
                		temp.put(movname, temp.get(movname) + 1);
                	}else {
                		temp.put(movname, temp.get(movname) - 1);	
                	}
                	if (temp.get(movname) == 0 ) {
                		temp.remove(movname);
                	}
                }
                System.out.println(temp.toString());
                System.out.println(add);
            	getid = "SELECT id FROM movies\r\n" + 
                 		"where movies.title ='"+movname +"';";
            	
            }
            String movieid = "";
            if (temp.size() != 0) {
            	if (!getid.equals("")) {
            		ResultSet rs = statement.executeQuery(getid);
            		
                    while (rs.next()) {
                    	
                    	movieid = rs.getString(1);
                    
                    }
                    rs.close();
              	
                }
            	out.println("<div id ='out' style ='background-color:white;opacity:0.9;'>");
            	 out.println("<TABLE border>");
                 out.println("<tr>" + "<td>" + "Title" + "</td>" + "<td>" + "Quantity" + "</td>" 
                         + "</tr>");
                 for (HashMap.Entry<String, Integer> entry : temp.entrySet()) {
                	 String title = entry.getKey();
                	 int n = entry.getValue();
                	 out.println("<tr>" + "<td>" + title + "</td>" + "<td>" + n + "</td>" + "<td>" +"<a href ='/cs122b/shoppingcart?tobuy="+title+"&num=p1"+"'>+1</a>"+ "</td>"+"<td>"+"<a href = '/cs122b/shoppingcart?tobuy="+title+"&num=m1'>-1</a>"+"</td>"
                             + "</tr>");
                	  
                 }
            	out.println("</TABLE>");
            	
            	
            	
            	
            	out.println("<form method='get' align = 'center' action = 'checkout?movid"+movieid+"'>"
            			+ "<button type = 'submit'> Check Out</button>"
            			+ "</form>"
            			+ ""
            			+ ""
            			+ ""
            			+ ""
            			+ "");
            	
            	out.println("<form method = 'get' align = 'center' action = 'shoppingcart?clear=tes'>"
            			+ ""
            			+ ""
            			+ "<button type='submit'>Delete All</button>"
            			+ "</form> ");
            	
            	
            	

            }
            else {
            	
            	out.println("<div id ='out' style ='background-color:white;opacity:0.9;'>");
                out.println("<H1>Nothing added to cart yet!</H1>");
                out.println("<a href = '/cs122b'>Back to Home</a>");
            }
            
            
            out.println("</div>");
            
            

            
            
           
            
             
            //String query = "INSERT INTO sales(customerId,movieId,saleDate) VALUES("+custid+",'"+movid+"', curdate());";
            		

            // Perform the query
            //ResultSet rs = statement.executeQuery(query);
            

//            // Iterate through each row of rs
//            while (rs.next()) {
//            	
//            	
//               
//            }
//
//            
            
            statement.close();
            dbcon.close();
            
        	}
        } catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error" + "</TITLE></HEAD>\n<BODY>"
                    + "<P>SQL error in doGet: " + ex.getMessage() + "</P></BODY></HTML>");
            return;
        }
        out.close();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}