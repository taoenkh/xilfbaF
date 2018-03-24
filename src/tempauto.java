
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.text.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


// server endpoint URL
@WebServlet("/autocomplete")
public class tempauto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * populate the Marvel heros and DC heros hash map.
	 * Key is hero ID. Value is hero name.
	 */
	public static HashMap<String, ArrayList<String>> movietitle = new HashMap<>();
	public static HashMap<String, ArrayList<String>> starname = new HashMap<>();
	public static HashMap<Integer, String> marvelHerosMap = new HashMap<>();
	public static HashMap<Integer, String> dcHerosMap = new HashMap<>();
	
	static {
		marvelHerosMap.put(1, "Blade");
		marvelHerosMap.put(2, "Ghost Rider");
		marvelHerosMap.put(3, "Luke Cage");
		marvelHerosMap.put(4, "Silver Surfer");
		marvelHerosMap.put(5, "Beast");
		marvelHerosMap.put(6, "Thing");
		marvelHerosMap.put(7, "Black Panther");
		marvelHerosMap.put(8, "Invisible Woman");
		marvelHerosMap.put(9, "Nick Fury");
		marvelHerosMap.put(10, "Storm");
		marvelHerosMap.put(11, "Iron Man");
		marvelHerosMap.put(12, "Professor X");
		marvelHerosMap.put(13, "Hulk");
		marvelHerosMap.put(14, "Cyclops");
		marvelHerosMap.put(15, "Thor");
		marvelHerosMap.put(16, "Jean Grey");
		marvelHerosMap.put(17, "Wolverine");
		marvelHerosMap.put(18, "Daredevil");
		marvelHerosMap.put(19, "Captain America");
		marvelHerosMap.put(20, "Spider-Man");
	}
	
	static {
		dcHerosMap.put(101, "Superman");
		dcHerosMap.put(102, "Batman");
		dcHerosMap.put(103, "Wonder Woman");
		dcHerosMap.put(104, "Flash");
		dcHerosMap.put(105, "Green Lantern");
		dcHerosMap.put(106, "Catwoman");
		dcHerosMap.put(107, "Nightwing");
		dcHerosMap.put(108, "Captain Marvel");
		dcHerosMap.put(109, "Aquaman");
		dcHerosMap.put(110, "Green Arrow");
		dcHerosMap.put(111, "Martian Manhunter");
		dcHerosMap.put(112, "Batgirl");
		dcHerosMap.put(113, "Supergirl");
		dcHerosMap.put(114, "Black Canary");
		dcHerosMap.put(115, "Hawkgirl");
		dcHerosMap.put(116, "Cyborg");
		dcHerosMap.put(117, "Robin");
	}
    
    public tempauto() {
        super();
    }

    /*
     * 
     * Match the query against Marvel and DC heros and return a JSON response.
     * 
     * For example, if the query is "super":
     * The JSON response look like this:
     * [
     * 	{ "value": "Superman", "data": { "category": "dc", "heroID": 101 } },
     * 	{ "value": "Supergirl", "data": { "category": "dc", "heroID": 113 } }
     * ]
     * 
     * The format is like this because it can be directly used by the 
     *   JSON auto complete library this example is using. So that you don't have to convert the format.
     *   
     * The response contains a list of suggestions.
     * In each suggestion object, the "value" is the item string shown in the dropdown list,
     *   the "data" object can contain any additional information.
     * 
     * 
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		try {

			String query = request.getParameter("query");
			String queries[] = query.split(" ");
			
			
             // Declare our statement
             
			Context initCtx = new InitialContext();


            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            
            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
            //System.out.println(ds.toString()+" "+"");

            System.out.println("dsad");
            Connection dbcon = ds.getConnection();
            
            
            System.out.println("dsadafter");
             String morewords = "";
             
             for (String i :queries) {
            	 morewords+= "+"+i+"* ";
             }
             String fs = "";
             for (String i:queries) {
            	 fs +=i;
             }
             String searchq = "select id, title from movies where MATCH(title) against ('"+morewords+"' in boolean mode) or title like '%"+query+"%'" + " or edth(title,'"+query+"',"+Math.max((int)query.length()/4,1)+") = 1" +" limit 5;\r\n" + 
              		"";
             String searchstar = "select id, name from stars where MATCH(name) against ('"+morewords+"' in boolean mode) or name like '%"+query+"%'" + " or edth(name,'"+query+"',"+Math.max((int)query.length()/4,1)+") = 1" +" limit 5;\r\n" + 
               		"";
             System.out.println(searchq);
 			System.out.println(searchstar);
 			
 			PreparedStatement ps = dbcon.prepareStatement(searchstar);
 			PreparedStatement pt = dbcon.prepareStatement(searchq);
             ResultSet rs = pt.executeQuery();
             int countm = 0;
             ArrayList<String> tempmovie = new ArrayList<String>();
             while (rs.next()) {
            	 
            	 //String id = rs.getString(1);
            	 String id = rs.getString(1);
            	 String title = rs.getString(2);
            	 tempmovie.add(title);
            	 countm++;
             }
             movietitle.put(morewords,tempmovie);

             ResultSet rs1 = ps.executeQuery();
             int counts = 0;
             ArrayList<String> tempname = new ArrayList<String>();
             while (rs1.next()) {
            	 
            	 String id = rs1.getString(1);
            	 String name = rs1.getString(2);
            	 tempname.add(name);
            	 counts ++;
             }
             starname.put(morewords, tempname);

			ps.close();
			pt.close();
			System.out.println(movietitle.size()+" size of title map");
			System.out.println(starname.size()+" size of star map");
			
			
			// setup the response json arrray
			
			// get the query string from parameter
			
			//System.out.println("shabi");
			System.out.println(query);
			JsonArray jsonArray = new JsonArray();
			// return the empty json array if query is null or empty
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			
			// search on marvel heros and DC heros and add the results to JSON Array
			// this example only does a substring match
			// TODO: in project 4, you should do full text search with MySQL to find the matches on movies and stars
			
			//for (Integer id : marvelHerosMap.keySet()) {
//				String heroName = marvelHerosMap.get(id);
//				if (heroName.toLowerCase().contains(query.toLowerCase())) {
//					jsonArray.add(generateJsonObject(id, heroName, "marvel"));
//				}
//			}
//			
//			for (Integer id : dcHerosMap.keySet()) {
//				String heroName = dcHerosMap.get(id);
//				if (heroName.toLowerCase().contains(query.toLowerCase())) {
//					jsonArray.add(generateJsonObject(id, heroName, "dc"));
//				}
//			}
			
			int starin = 0;
			int moviein = 0;
			for (String id: movietitle.keySet()) {
				
				ArrayList<String> title = movietitle.get(id);
				if (id.equals(morewords)) {
					for(String i:title) {
						moviein++;
						jsonArray.add(generateJsonObject(moviein,i,"Title"));
					}
				}

			}
			System.out.println("moviemap"+jsonArray.toString());
			System.out.println("moveihash"+movietitle.toString());
			System.out.println(moviein+" moviein");
			for (String id: starname.keySet()) {
				
				ArrayList<String> title = starname.get(id);
				if (id.equals(morewords)) {
					for (String i:title) {
						starin++;
						jsonArray.add(generateJsonObject(starin,i,"Stars"));
					}
				}

			}
			System.out.println(starin+ " starin");
			
			response.getWriter().write(jsonArray.toString());
			return;
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Iron Man",
	 *   "data": { "category": "marvel", "heroID": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(Integer heroID, String heroName, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", heroName);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("heroID", heroID);
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}
	private static JsonObject mygenerateJsonObject(String ID, String heroName, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", heroName);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("heroID", ID);
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}


}
