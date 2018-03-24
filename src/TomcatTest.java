
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class TomcatTest extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getServletInfo() {
        return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String loginUser = "taoenkh";
//        String loginPasswd = "wangtao1";
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE>"
        		+"<style type = \"text/css\">"
        	
        		+"body {background: url(\"http://www.dq995.com/data/out/13/820401.jpg\"); font-family:\"Times New Roman\" ;color:#4E524E;}"
        		+"h1 {text-align:center;color:grey;font-family:\"Times New Roman\";}"
        		+"TABLE {font-family:\"Times New Roman\";}"
        		+"table {\r\n" + 
        		"\r\n" + 
        		"  height: 40%;\r\n" + 
        		"  left: 10%;\r\n" + 
        		"  margin: 20px auto;\r\n" + 
        		"  overflow-y: scroll;\r\n" + 
        		"  position: static;\r\n" + 
        		"  width: 80%;\r\n" + 
        		"opacity:0.7"+
        		"}"
        		+"tr {\r\n" + 
        		"  background:white ;\r\n" + 
        		"  border-bottom: 1px solid grey;\r\n" + 
        		"  margin-bottom: 5px;\r\n" + 
        		"}\r\n" + 
        		"\r\n" + 
        		"td {\r\n" + 
        		"   font-size: 12px;\r\n" + 
        		"  font-family: 'Times New Roman', sans-serif;\r\n" +  
        		"  padding: 20px;\r\n" + 
        		"  text-align: left;\r\n" + 
        		"  width: 33.3333%;\r\n" + 
        		"opacity:0.7"+
        		"}"
        		+"td:hover{background: grey; opcacity:0.5;}"
        		+"</style>"
        		+
        		"</HEAD>");
        out.println("<BODY><H1>Top 20 rating movies</H1>");

        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
           // Class.forName("com.mysql.jdbc.Driver").newInstance();

            //Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Context initCtx = new InitialContext();


            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            //System.out.println(ds.toString()+" "+"");

            System.out.println("dsad");
            Connection dbcon = ds.getConnection();
            // Declare our statement
            Statement statement = dbcon.createStatement();

            String query = "SELECT title, year, director,r.rating,group_concat(distinct stars.name),group_concat(distinct genres.name)\r\n" + 
            		"from (select* from ratings,movies \r\n" + 
            		"where ratings.movieId = movies.id\r\n" + 
            		"order by rating desc \r\n" + 
            		"limit 20) as r,stars,stars_in_movies,genres_in_movies\r\n" + 
            		"left join genres on genres_in_movies.genreId = genres.id\r\n" + 
            		"where stars.id = stars_in_movies.starID\r\n" + 
            		"and stars_in_movies.movieID = r.id\r\n" + 
            		"and r.id = genres_in_movies.movieId\r\n" +
            		"group by r.id\r\n"+
            		"order by rating desc ";
            		

            // Perform the query
            ResultSet rs = statement.executeQuery(query);

            out.println("<TABLE border>");
            out.println("<tr>" + "<td>" + "Title" + "</td>" + "<td>" + "Year" + "</td>" + "<td>" +"Director"+ "</td>"+"<td>"+"Rating"+"</td>"
                    +"<td>Stars</td> <td>Genres</td>"+ "</tr>");
            // Iterate through each row of rs
            while (rs.next()) {
                String title = rs.getString(1);
                String year = rs.getString(2);
                String director = rs.getString(3);
                String rating = rs.getString(4);
                String stars = rs.getString(5);
                String genre = rs.getString(6);
                
;                out.println("<tr>" + "<td>" + title + "</td>" + "<td>" + year + "</td>" + "<td>" +director+ "</td>"+"<td>"+rating+"</td>"
						+"<td>"+stars+"</td>" + "<td>"+genre+"</td>"
                        + "</tr>");
            }

            out.println("</TABLE>");

            rs.close();
            statement.close();
            dbcon.close();
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