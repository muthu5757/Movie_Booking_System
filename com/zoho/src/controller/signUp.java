package controller;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class signUp{
	public signUp(User user, String username, String password, Connection con){
		try  {
			Statement st = con.createStatement();
			String query = "INSERT INTO users (name, email, mobile_number) VALUES (' " + user.getName() + " ', ' " + user.getEmail() + " ', ' " + user.getMobileNumber() + " ' )";
			int row = st.executeUpdate(query);
			ResultSet rs = st.executeQuery("SELECT LASTVAL()"); 
			if (rs.next()) {
			int userId = rs.getInt(1); 
		    	String query2 = "INSERT INTO logincredential (username, password, user_id) VALUES (' " + username + " ', ' " + password + " ', " + userId + ")";
		    	int row2 = st.executeUpdate(query2);
		}
	    	} catch (SQLException e) {
			System.out.println("Signup failed: " + e.getMessage());
		}
	}
}
