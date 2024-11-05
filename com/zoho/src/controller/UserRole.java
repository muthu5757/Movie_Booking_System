package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
public class UserRole{
	public void setUserRole(String email,String role,Connection conn){
		try  {
			String query = "SELECT id FROM users WHERE TRIM(email) = ?;";
			PreparedStatement ps = conn.prepareStatement(query);  
			ps.setString(1, email);  
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
			    int id = rs.getInt("id");
			    	String query2 = "insert into roles(user_id,roles)values(' "+id+"' , '"+role+"');";
				Statement st = conn.createStatement();
				st.executeUpdate(query2);
				System.out.println("\nRole Inserted Successfully ! 👍️\n");
			    } else
			    {
			    	System.out.println("No User on given E-mail ID !! ");
			    }
	    	} catch (SQLException e) {
			System.out.println("SignIn failed: " + e.getMessage());
		}
	}
	public void DisplayRole(Connection conn)
	{
		try  {
			String query = "SELECT name, roles FROM users join roles on roles.user_id = users.id;";
			PreparedStatement ps = conn.prepareStatement(query);  
			ResultSet rs = ps.executeQuery();
			System.out.println("name\t\t Roles");
			while(rs.next()){
			        System.out.println(rs.getString("name")+"  |  "+rs.getString("roles") );  
			  }
	    	} catch (SQLException e) {
			System.out.println("SignIn failed: " + e.getMessage());
		}
	}
}
