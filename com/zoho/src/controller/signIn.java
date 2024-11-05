package controller;

import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class signIn{
	public User userSignIn(String username, String password, Connection conn){
		String role;
		try  {
			String CHECK_PASSWORD = "SELECT user_id, password FROM logincredential WHERE TRIM(username) = ?;";
			PreparedStatement ps = conn.prepareStatement(CHECK_PASSWORD);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				    if (rs.getString("password").trim().equals(password.trim()))
				     {
				        	int id = rs.getInt("user_id");
				        	String RETRIEVE_ROLE = "SELECT roles FROM roles WHERE user_id = ?;";
				        	 ps = conn.prepareStatement(RETRIEVE_ROLE);
					ps.setInt(1, id);  
					 rs = ps.executeQuery();
					if(rs.next())
						role =  rs.getString("roles");
					else
						role = "customer";
					
					String FETCH_USER_DETAILS = "SELECT * FROM users WHERE id = ?;";
					 ps = conn.prepareStatement(FETCH_USER_DETAILS);
					ps.setInt(1, id);  
					 rs = ps.executeQuery();
					if(rs.next())
					{
						User user = new User(rs.getString("name"),rs.getString("email"),rs.getString("mobile_number"),id,role);
						System.out.println(" \nLogin Successfully !  👍️\n");
				        		return user;
					}
				    } 
				    else 
				    {
				        System.out.println("Incorrect password");
				       }
			  }
			  else
			  {
			  	System.out.println("Username not Found !\n");
			  }
	    	} catch (SQLException e) {
			System.out.println("SignIn failed: " + e.getMessage());
		}
		return null;
	}
	public void deleteAccount(Connection conn, User u)
	{
		try {
			String delete_query = "update logincredential set status = ? where user_id = ?;";
			PreparedStatement ps = conn.prepareStatement(delete_query);
			ps.setString(1, "inactive");
			ps.setInt(2, u.getId());
			ResultSet rs = ps.executeQuery();
			System.out.println("Account Deleted Successfully !!");
		}catch (SQLException e) {
			System.out.println("SignIn failed: " + e.getMessage());
		}
	}
}
