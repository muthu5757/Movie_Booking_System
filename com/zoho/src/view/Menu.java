package view;

import model.User;

import java.util.Scanner;
import java.sql.Connection;

public class Menu
{
	Scanner in = new Scanner(System.in);
	 public void superAdmin(Helper h, Connection conn, User u)
	 {
			 	System.out.println("!+========================================+!\n");
				System.out.println("Welcome SuperAdmin !!"+u.getName()+" 🤩️\n");
				while(true)
				{
				System.out.println("\nChoose your Choice : \n");
				System.out.println("0. Logout");
				System.out.println("1. Insert User Role");
				System.out.println("2. User Role List");
				System.out.println("\n!+========================================+!");
				System.out.println("\n\n Enter your Choice : ");
				int choice = in.nextInt();
					switch(choice)
					{
						case 0:
							return;
						case 1:
							  h.userRole(conn);
							    break;
						case 2:
							h.userRoleList(conn);
							break;
					}
				}
	 }
	 public void Admin(Helper h, Connection conn, User u)
	 {
			 	System.out.println("!+========================================+!\n");
				System.out.println("Welcome Admin !! "+u.getName()+" 🤩️\n");
				while(true)
				{
				System.out.println("Choose your Choice : \n");
				System.out.println("0. Logout");
				System.out.println("1. Add Movie");
				System.out.println("\n!+========================================+!");
				System.out.println("\n\n Enter your Choice : ");
				int choice = in.nextInt();
					switch(choice)
					{
						case 0:
							return;
						case 1:
							  h.movieInsertion(conn);
							  break;
					}
				}
	 }
	 public void Owner(Helper h, Connection conn, User u)
	 {
			 	System.out.println("!+========================================+!\n");
				System.out.println("Welcome Owner !!"+u.getName()+" 🤩️\n");
				while(true)
				{
				System.out.println("Choose your Choice : \n");
				System.out.println("0. Logout");
				System.out.println("1. Theater Registration");
				System.out.println("\n!+========================================+!");
				System.out.println("\n\n Enter your Choice : ");
				int choice = in.nextInt();
					switch(choice)
					{
						case 0:
							return;
						case 1:
							 h.newTheaterInsertion(conn,u);
							break;
					}
				}
	 }
	 public void TheaterManager(Helper h, Connection conn, User u)
	 {
			 	System.out.println("!+========================================+!\n");
				System.out.println("Welcome Theater Manager !!"+u.getName()+"🤩️ \n");
				while(true)
				{
				System.out.println("Choose your Choice : \n");
				System.out.println("0. Logout");
				System.out.println("1. Showtimes Insertion");
				System.out.println("2. Theater Movie Updation");
				System.out.println("\n!+========================================+!");
				System.out.println("\n\n Enter your Choice : ");
				int choice = in.nextInt();
					switch(choice)
					{
						case 0:
							return;
						case 1:
							h.ShowTimeInsertion(conn);
							break;
						case 2:
							h.TheaterMovieUpdation(conn, u);
							break;
					}
				}
	 }
	 public void Customer(Helper h, Connection conn, User u)
	 {
			 	System.out.println("!+========================================+!\n");
				System.out.println("Welcome Customer !!"+u.getName()+"🤩️ \n");
				while(true)
				{
				System.out.println("\nChoose your Choice : \n");
				System.out.println("0. Logout");
				System.out.println("1. Book a Ticket");
				System.out.println("2. Show my Tickets");
				System.out.println("3. Cancel Tickets");
				System.out.println("4. Delete Account");
				System.out.println("\n!+========================================+!");
				System.out.println("\n\n Enter your Choice : ");
				int choice = in.nextInt();
					switch(choice)
					{
						case 0:
							return;
						case 1:
							h.bookATicket(conn,u);
							break;
						case 2:
							h.showTickets(conn,u);
							break;
						case 3:
							h.cancelTicket(conn,u);
							break;
						case 4:
							if(h.DeleteAccount(conn,u)==1)
								return;
					}
				}  
	 }
}

