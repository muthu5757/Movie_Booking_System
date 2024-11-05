package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection
{
	 private final String url = "jdbc:postgresql://localhost:5432/Movie_Booking_System";
	 private final String user = "postgres";
	 private final String password = "Ammubca57&";
	private Connection conn = null;
	public DBConnection()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url,user,password);
			if(conn!=null)
				System.out.println("Connection Established !");
			else
				System.out.println("Connection Failed !");
		}
		catch(Exception e)
		{
			 System.out.println(e);
		}
	}
	public Connection connect()
	{
		return conn;
	}
	public void closeConnect() {
	        if (conn != null) {
		   try {
		       conn.close();
		       System.out.println("Connection closed.");
		   } catch (SQLException e) {
		       e.printStackTrace();
		   }
	        }
	    }   
}
