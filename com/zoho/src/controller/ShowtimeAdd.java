package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class ShowtimeAdd
{
	public ShowtimeAdd(String session, String time , Connection conn)
	{
		 try
		 {
		Statement st = conn.createStatement();
		String query = "INSERT INTO showtime (session, slot) VALUES ('" + session + "', '" + time + "')";
		int row = st.executeUpdate(query);
		System.out.println("\n ShowTime added successfully  !!  \n ");
		  } catch (SQLException e) {
		   System.out.println("ShowTime Insertion  failed: " + e.getMessage());
		  }
	}
  }
