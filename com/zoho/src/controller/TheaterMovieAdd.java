package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
public class TheaterMovieAdd{
	public TheaterMovieAdd(int mId, String movieName, String director, String session, String timeslot, double price, Connection conn){
		int theaterId = 0, movieId= 0, showtimeId = 0;
		try  {
			String query = "SELECT id FROM theaters WHERE manager = ?;";
			PreparedStatement ps = conn.prepareStatement(query);  
			ps.setInt(1, mId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
			        theaterId = rs.getInt("id");
			        String query2 = "SELECT id FROM movieList WHERE trim(movie_title) = ? and trim(director) = ?;";
				 ps = conn.prepareStatement(query2);  
				ps.setString(1, movieName);
				ps.setString(2, director);  
				 rs = ps.executeQuery();
				if(rs.next()){
			        		movieId = rs.getInt("id");
			        		String query3 = "SELECT id FROM showtime WHERE session = ?::session_enum and slot = ?::time;";
					 ps = conn.prepareStatement(query3);  
					ps.setString(1, session);
					ps.setString(2, timeslot);  
					 rs = ps.executeQuery();
					if(rs.next()){
				        		showtimeId = rs.getInt("id");
				        	}
				        	else
				        	{
				        		System.out.println("No Available Show time Found on Given Session and TimeSlot !! ");
			  			return;
				        	}
			        	}
			        	else
			        	{
			        		System.out.println("No Movie Found on Given Movie Name and Director !! ");
			  		return;
			        	}
			  }
			  else
			  {
			  	System.out.println("No Theater on Given Manager !! ");
			  	return;
			  }
			Statement st = conn.createStatement();
			String query4 = "INSERT INTO availableMovies (theater_id, movie_id,showtime_id, price) VALUES (' " + theaterId + " ', ' " + movieId+" ', ' " + showtimeId + " ', ' " + price + " ' )";
			int row = st.executeUpdate(query4);
			System.out.println("\n Movie Updated Successfully !! 🎉️ \n ");
	    	} catch (SQLException e) {
			System.out.println("Insertion failed: " + e.getMessage());
		}
	}
}
