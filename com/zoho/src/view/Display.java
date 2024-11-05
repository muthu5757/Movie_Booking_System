package view;

import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
class Display
{
	public void movieDisplay(Connection conn) {
	    try{
		 Statement stmt = conn.createStatement();
		 String query = "SELECT movie_title, director FROM movielist;";
		 ResultSet rs = stmt.executeQuery(query);
		   while (rs.next()) {
		       String movieName = rs.getString("movie_title");
		       System.out.println("Movie Name: " + movieName + " - Director : " + rs.getString("director"));
		   }
	        } catch (Exception e) {
		   e.printStackTrace();
	        }
	        }
	        
	      public void TheaterDisplay(Connection conn, User u) {
	    try{
		String query2 = "SELECT theater_name FROM theaters WHERE manager= ?;";
		       PreparedStatement ps = conn.prepareStatement(query2);
		       ps.setInt(1, u.getId());
		       ResultSet rs = ps.executeQuery();
		   while (rs.next()) {
		       System.out.println(" *** " + rs.getString("theater_name") + " *** ");
		   }
	        } catch (Exception e) {
		   e.printStackTrace();
	        }
	        }
}
