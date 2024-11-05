package view;

import controller.*;
import model.Movie;
import model.Theater;
import model.User;

import java.io.Console;
import java.sql.Connection;
import java.util.*;
public class Helper
{
	 Scanner in = new Scanner(System.in);
	 Console console = System.console();
	 public int menu()
	 {
			 	System.out.println("\n!+========================================+!\n");
				System.out.println("Welcome to CineReserve Application !! 🤩️ \n");
				System.out.println("Choose your Choice : \n");
				System.out.println("0. Exit");
				System.out.println("1. Sign Up / Create Account ");
				System.out.println("2. Sign In / Login ");
				System.out.println("\n!+========================================+!");
				System.out.println("\n Enter your Choice : ");
				int choice = in.nextInt();
				return choice;
	 }
	public void signUp(Connection conn)
	{
			     System.out.println("\n!+*****************************************+!\n");
			    System.out.println("Account Creation : \n");
			    in.nextLine();
			    System.out.println("\nEnter your Good Name : ");
			    String name = in.nextLine();
			    System.out.println("\nEnter your E-mail Id (Must be Unique) : ");
			    String email = in.next();
			    System.out.println("\nEnter your Mobile Number : ");
			    String number = in.next();
			    System.out.println("\nEnter your Username (Must be Unique) : ");
			    String username = in.next();
			    char[] passwordArray = console.readPassword("Enter your Password : \n");
			    String password = new String(passwordArray);
		   	User user = new User(name, email, number,0," ");
		   	signUp s = new signUp(user, username, password, conn);
		   	System.out.println(" \n 🎉️ Account Created Successfully ! 🎉️\n");
		   	System.out.println("\n!+*****************************************+!\n"); 
	}
	public User signIn(Connection conn)
	{
				 System.out.println("\n!+*****************************************+!\n");
			    System.out.println("Login : ");
			    System.out.println("\nEnter your Username : ");
			    String username = in.next();
			    char[] passwordArray = console.readPassword("\nEnter your Password : \n");
			    String password = new String(passwordArray);
			    signIn sin = new signIn();
			    System.out.println("\n!+*****************************************+!\n");
			    return sin.userSignIn(username,password,conn);
	}
    public int DeleteAccount(Connection conn, User u)
    {
        System.out.println("\nAre You Sure.. You Want to Delete your Account ? ");
        System.out.println("\nEnter \"Yes\" to Continue or \"No\" to Cancel");
        if(in.next().equalsIgnoreCase("Yes"))
        {
            signIn s = new signIn();
            s.deleteAccount(conn,u);
            return 1;
        }
        else return 0;

    }
	public void userRole(Connection conn)
	{
				System.out.println("\n!+*****************************************+!\n");
			   System.out.println("Inserting User Role :");
			    System.out.println("\nEnter the User email ID : ");
			    String email = in.next();
			    in.nextLine();
			     System.out.println("\nEnter the Role ('admin', 'owner') : ");
			    String role = in.nextLine();
			    UserRole ur = new UserRole();
			    ur.setUserRole(email,role,conn);
			    System.out.println("!+*****************************************+!\n");
	}
	public void userRoleList(Connection conn)
	{
				UserRole url = new UserRole();
				url.DisplayRole(conn);
	}
	public void newTheaterInsertion(Connection conn, User u)
	{
			System.out.println("\n!+*****************************************+!\n");
			 System.out.println("\nTheater Registration :\n");
			in.nextLine();
			 System.out.println("\nEnter your Theater Name : ");
			    String Theatername = in.nextLine();
			     System.out.println("\nEnter your Theater Email Id : ");
			    String email = in.next();
			    in.nextLine();
			    System.out.println("\nEnter your Theater Address : ");
			    String address = in.nextLine();
			    System.out.println("\nEnter your Theater Location : ");
			    String location = in.next();
			    in.nextLine();
			    System.out.println("\nEnter your Theater Manager Name : ");
			    String managerName = in.nextLine();
			     System.out.println("\nEnter your Theater Manager Email : ");
			    String managerEmail = in.next();
			    Theater t = new Theater(Theatername, email , address, location, managerName, managerEmail);
			    TheaterAdd add = new TheaterAdd(t, u.getId(),conn);
			    System.out.println("\n!+*****************************************+!\n");
	}
	 public void movieInsertion(Connection conn)
              {
                     System.out.println("\n !+ ***************************************************+!\n");
                     System.out.println("Enter how many movies you want to enter : ");
                     int count = in.nextInt();
                     for(int i=0;i<count;i++)
                     {
                     in.nextLine();
                     System.out.println("\nEnter your Movie Name : ");
                      String title = in.nextLine();
                      System.out.println("\nEnter Movie Genre : ");
                      String genre= in.nextLine();
                      System.out.println("\nEnter Movie Language  :  ");
                      String language = in.next();
                     System.out.println("\nEnter Movie Director  :  ");
                      String director = in.next();
                      in.nextLine();
                      System.out.println("\nEnter Movie Release Date :");
                      String release = in.nextLine();
                       System.out.println("\nEnter Movie Duration  :");
                      String duration = in.nextLine();
                       System.out.println("\nEnter Movie Rating :");
                      float rating  = in.nextFloat();
                      Movie m =new Movie(title , genre , language , director , release , duration , rating);
                      MovieAdd add = new MovieAdd();
                      add.Movieadd(m,conn);
                      }
                      System.out.println("!+ ***************************************************+!\n");
     }
     public void ShowTimeInsertion(Connection conn)
     {
                     System.out.println("\n !+ ***************************************************+!\n");
                     in.nextLine();
                     System.out.println("\nEnter your Session : ");
                      String session= in.nextLine();
                      System.out.println("\nEnter Your Time : ");
                      String time = in.nextLine();
                      ShowtimeAdd add = new ShowtimeAdd(session,time,conn);
                      System.out.println("!+ ***************************************************+!\n");
     }
      public void TheaterMovieUpdation(Connection conn, User u)
      {
      	   System.out.println("\n !+ ***************************************************+!\n");
      	   in.nextLine();
      	   Display d = new Display();
      	   d.TheaterDisplay(conn, u);
           System.out.println("\nEnter how many movies you want to enter : ");
            int count = in.nextInt();
            in.nextLine();
           for(int i=0;i<count;i++)
           {
           System.out.println("\nChoose a Movie : ");
       	 d.movieDisplay(conn);
          System.out.println("\nEnter movie name : ");
          String movieName = in.nextLine();
          System.out.println("\nEnter movie director name : ");
          String directorName = in.nextLine();
          System.out.println("\nEnter session : ");
          String timeSession = in.next();
         in.nextLine();
          System.out.println("\nEnter time slot : ");
          String timeSlot = in.nextLine();
          System.out.println("\nEnter amount : ");
          double amount = in.nextDouble();
          TheaterMovieAdd book = new TheaterMovieAdd(u.getId(),movieName,directorName,timeSession,timeSlot,amount,conn);
          }
           System.out.println("\n !+ ***************************************************+!\n");
     }
     public void bookATicket(Connection conn, User u)
     {
     System.out.println("\nChoose a Movie : ");
        Display d = new Display();
       d.movieDisplay(conn);
        
        in.nextLine();
        System.out.println("\nEnter movie name : ");
        String movieName = in.nextLine();
        System.out.println("\nEnter Location : ");
        String location = in.nextLine();

        BookingDB db = new BookingDB();
        System.out.println("\nAvailable Theaters (Choose one) : ");
        db.availableTheaters(movieName, location, conn);
        
        String theater = in.nextLine();
        Integer selectedTheaterId = null;
        for (Map.Entry<Integer, String> entry : db.theaterMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(theater)) {
                selectedTheaterId = entry.getKey();
                break;
            }
        }
        
        if (selectedTheaterId != null) {
            System.out.println("\nAvailable Timeslots (Choose one): ");
            db.fetchShowtimesForTheater(selectedTheaterId, conn);

            String time = in.nextLine();
            System.out.println("\nEnter Date to Watch : ");
            String date = in.next();
            db.Booking(movieName,theater,location, time,date, u.getId(), conn,u);
        } else {
            System.out.println("Invalid theater selected.");
        }
    }
    
    public void showTickets(Connection conn, User u)
    {
        BookingSeatSystem bs = new BookingSeatSystem();
        bs.showUserBookedTickets(conn,u);
    }
    public void cancelTicket(Connection conn, User u)
    {
        System.out.println("\nEnter Date of Watching : ");
        String date = in.next();
        BookingSeatSystem bs = new BookingSeatSystem();
        bs.cancelUserBookedTickets(date,conn,u);
    }
    public void thankYou()
    {
    	System.out.println("\nThankyou for using our Application  🎉️ 🙏️ ");
    	System.out.println("\nThankyou.. Come Again !! ☺️\n");
    }
     }
