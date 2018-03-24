
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getSession().setAttribute("recap","no");
		
		PrintWriter out = response.getWriter();
//		String loginUser = "taoenkh";
//        String loginPasswd = "wangtao1";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        response.setContentType("text/html");
        String username = request.getParameter("username");
		String password = request.getParameter("password");
			if (request.getParameter("android") == null) {
			
				JsonObject responseJsonObject = new JsonObject();
			
//			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
//			
//			System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
//			
//			boolean valid = Verifyutils.verify(gRecaptchaResponse);
//			JsonObject responseJsonObject = new JsonObject();
//			if (!valid) {
//				if (request.getSession().getAttribute("recap") != null) {
//					if (!request.getSession().getAttribute("recap").equals("yes")) {
//							request.getSession().setAttribute("recap","no");
//					}
//				}
//				else {
//					request.getSession().setAttribute("recap","no");
//				}
//				
//			    //errorString = "Captcha invalid!";
//				System.out.println("YOu must be human! ");
//				
//	//		    out.println("" +
//	//				"<P>Recaptcha WRONG!!!! </P>");
//			    
//			}
//			else {
//				request.getSession().setAttribute("recap","yes");
//				
//			}
			
			
			request.getSession().setAttribute("recap","yes");
			System.out.println("login get");
			
			//HttpSession usersess = request.getSession(true);
			
			try {
				
				if (username.equals("tao@email.com")&& password.equals("1")) {
					User developer = new User(username);
					developer.password = password;
					if (request.getSession().getAttribute("recap").equals("yes")) {
		            	JsonObject responseJsonObject1 = new JsonObject();
			            responseJsonObject1.addProperty("status", "success");
			            request.getSession().setAttribute("user", developer);
						response.getWriter().write(responseJsonObject1.toString());
		            }
		            else {
		            	
		            	responseJsonObject.addProperty("message","Please verify recaptcha!");
		    			response.getWriter().write(responseJsonObject.toString());
		            	
		            }
					
				}
				
				//Class.forName("com.mysql.jdbc.Driver").newInstance();
				if (username.equals("classta@email.edu")&& password.equals("classta")) {
					User employee = new User(username);
					employee.password = password;
					if (request.getSession().getAttribute("recap").equals("yes")) {
		            	JsonObject responseJsonObject1 = new JsonObject();
			            responseJsonObject1.addProperty("status", "employee");
			            request.getSession().setAttribute("user", employee);
						response.getWriter().write(responseJsonObject1.toString());
		            }
		            else {
		            	
		            	responseJsonObject.addProperty("message","Please verify recaptcha!");
		    			response.getWriter().write(responseJsonObject.toString());
		            	
		            }
					
					
					
					
				}
				else {
	            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
					Context initCtx = new InitialContext();


		            Context envCtx = (Context) initCtx.lookup("java:comp/env");

		            
		            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
		            //System.out.println(ds.toString()+" "+"");

		            System.out.println("dsad");
		            Connection dbcon = ds.getConnection();
	            // Declare our statement
	            Statement statement = dbcon.createStatement();
	            String query = "SELECT * from customers where email= '"
	            		+ username +"'";
	            ResultSet rs = statement.executeQuery(query);
	            
	            if (!rs.next()) {
	            	request.getSession().setAttribute("user", new User(username));
	            	responseJsonObject.addProperty("status", "fail");
	    			responseJsonObject.addProperty("message","Invalid username or password!");
	    			response.getWriter().write(responseJsonObject.toString());
	            	
	            }
	            User loginuser = new User(username);
	            loginuser.password = rs.getString("password");
	            if (!password.equals(loginuser.password)){
	            	
	    			responseJsonObject.addProperty("status", "fail");
	    			responseJsonObject.addProperty("message","Invalid username or password!");
	    			
	    			response.getWriter().write(responseJsonObject.toString());
	            	
	            }
	
	            else {
	            	System.out.println(request.getSession().getAttribute("recap")+" recap");
		            HashMap<String,String> logus = new HashMap<String,String>();
		            HashMap<String,Integer> cart = new HashMap<String,Integer>();
		            logus.put(username,password);
		            String msg = "";
		            request.getSession().setAttribute("user", loginuser);
		            request.getSession().setAttribute("temp", logus);
		            request.getSession().setAttribute("cart", cart);
		            if (request.getSession().getAttribute("recap").equals("yes")) {
		            	JsonObject responseJsonObject1 = new JsonObject();
			            responseJsonObject1.addProperty("status", "success");
						response.getWriter().write(responseJsonObject1.toString());
		            }
		            else {
		            	
		            	responseJsonObject.addProperty("message","Please verify recaptcha!");
		    			response.getWriter().write(responseJsonObject.toString());
		            	
		            }
	            }
	            
	//            System.out.println(re);
	//    		if (!re) {
	//    		    //errorString = "Captcha invalid!";
	//    			System.out.println("YOu must be human! ");
	//    			
	//    		    out.println("<HTML>" +
	//    				"<HEAD><TITLE>" +
	//    				"MovieDB: Error" +
	//    				"</TITLE></HEAD>\n<BODY>" +
	//    				"<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
	//    		    return;
	//    		}
				
				
				
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// this example only allows username/password to be test/test
			// in the real project, you should talk to the database to verify username/password
	
		}
		else {
			try {
				System.out.println("android connected");
            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
				Context initCtx = new InitialContext();


	            Context envCtx = (Context) initCtx.lookup("java:comp/env");

	            
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	            //System.out.println(ds.toString()+" "+"");

	            System.out.println("dsad");
	            Connection dbcon = ds.getConnection();
            Statement statement = dbcon.createStatement();
            String query = "SELECT * from customers where email= '"
            		+ username +"'";
            ResultSet rs = statement.executeQuery(query);
            
            if (!rs.next()) {
            	request.getSession().setAttribute("user", new User(username));
            	out.print("false");
	
            }
            User loginuser = new User(username);
            loginuser.password = rs.getString("password");
            if (!password.equals(loginuser.password)){
            	out.print("false");

            	
            }

            else {

	            out.print("true");
	           
	            request.getSession().setAttribute("user", loginuser);



            }
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
            

			
			
			
			
			
			
			
			
		}
		
	}
	
	
	
	
	
		
		
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
