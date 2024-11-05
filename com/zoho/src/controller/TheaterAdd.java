package controller;

import model.Theater;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
public class TheaterAdd{
	public TheaterAdd(Theater t, int ownerId, Connection conn){
	int managerId = 0;
		try  {
			String query = "Select id from users where email = ? ";
			   PreparedStatement ps = conn.prepareStatement(query);
			   ps.setString(1, t.getManagerEmail());
			   ResultSet rs = ps.executeQuery();
			   
			  if (rs.next()) {
			      managerId = rs.getInt("id");
			   }   
			Statement st = conn.createStatement();
			String query2 = "INSERT INTO theaters (theater_name, owner_id,email, address, location, manager) VALUES (' " + t.getTheaterName() + " ', ' " + ownerId +" ', ' " + t.getTheaterEmail()+ " ', ' " + t.getTheaterAddress()+ " ', ' " + t.getTheaterLocation() + " ', ' " + managerId +" ' )";
			int row = st.executeUpdate(query2);
			System.out.println("\n Theater Registered Successfully !! 🎉️ \n ");
	    	} catch (SQLException e) {
			System.out.println("Insertion failed: " + e.getMessage());
		}
	}
}
