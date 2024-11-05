
package main;

import controller.DBConnection;
import view.Helper;
import view.Menu;
import model.User;
import java.sql.Connection;
import java.util.NoSuchElementException;

public class Main{
    public static void main(String[] args) {
    	DBConnection db = new DBConnection();
        try (Connection conn = db.connect()) {
	Helper h = new Helper();
	while(true)
	{
	int choice = h.menu();
		if(choice==0)
		{	
			h.thankYou();
			db.closeConnect();
			System.exit(0);
		}
		else if(choice==1)
			    h.signUp(conn);
		else if(choice==2)
		{
			Menu m = new Menu();
			User u = h.signIn(conn);
			   switch(u.getRole())
			   {
			   	case "superadmin" : 
			   			m.superAdmin(h,conn,u);
			   			break;
			   	case "owner" :
			   			m.Owner(h,conn,u);
			   			break;
			   	case "theater manager" :
			   			m.TheaterManager(h,conn,u);
			   			break;
			   	case "customer" :
			   			m.Customer(h,conn,u);
			   			break;
			   	/*case "admin" :
			   			m.Admin(h,conn);
			   			break;
			   	*/
			   	case "Error" :
			   			System.out.println("Something Went Wrong!! Please Correct !");
			   }
		}
		else 
			  System.out.println("Invalid Choice");
	}
	}
	catch(NoSuchElementException e)
	{
	
	}
	catch(Exception e)
	{
			 System.out.println(e);
	}
	finally
	{
		db.closeConnect();
	}
}
}
