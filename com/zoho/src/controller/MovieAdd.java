package controller;

import model.Movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class MovieAdd{
	public void Movieadd(Movie m, Connection conn){
		try{
			Statement st = conn.createStatement();
			String query = "INSERT INTO movieList (movie_title, genre, language, director, release_on, duration, rating) VALUES (' " + m.getTitle() + " ', '" + m.getGenre() + "', '" + m.getLanguage()+ "', '" + m.getDirector() + "', '" + m.getReleaseOn() + "', '" + m.getDuration()+ "', '" + m.getRating()+" ' )";
			int row = st.executeUpdate(query);
			System.out.println("\n Movie Added Successfully !! 🎉️ \n ");
	    	} catch (SQLException e) {
			System.out.println("Movie add become failed: " + e.getMessage());
		}
	}
}
