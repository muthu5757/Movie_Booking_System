package controller;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class BookingDB {
    Scanner in = new Scanner(System.in);
    public int theaterId = 0, movieId = 0, showtimeId = 0, id = 0;

    public Map<Integer, String> theaterMap = new HashMap<>();

    Map<Integer, List<Integer>> theaterTime = new HashMap<>(); 
    Map<Integer, String> theaterTimeId = new HashMap<>(); 
    
    public void availableTheaters(String movieName, String location, Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT id FROM movielist WHERE trim(movie_title) = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, movieName);
            rs = ps.executeQuery();

            if (rs.next()) {
                movieId = rs.getInt("id");

                rs.close();
                ps.close();

                String query2 = "SELECT theater_id, showtime_id FROM availableMovies WHERE movie_id= ?;";
                ps = conn.prepareStatement(query2);
                ps.setInt(1, movieId);
                rs = ps.executeQuery();

                while (rs.next()) {
                    theaterId = rs.getInt("theater_id");
                    showtimeId = rs.getInt("showtime_id");

                    String query3 = "SELECT trim(theater_name) FROM theaters WHERE id = ? AND trim(location) = ?;";
                    PreparedStatement ps2 = conn.prepareStatement(query3);
                    ps2.setInt(1, theaterId);
                    ps2.setString(2, location);
                    ResultSet rs2 = ps2.executeQuery();

                    while (rs2.next()) {
                        theaterMap.put(theaterId, rs2.getString(1));

                        if (!theaterTime.containsKey(theaterId)) {
                            theaterTime.put(theaterId, new ArrayList<>());
                        }
                        theaterTime.get(theaterId).add(showtimeId);
                    }

                    rs2.close();
                    ps2.close();
                }

                for (String theaterName : theaterMap.values()) {
                    System.out.println("** " + theaterName);
                }

            } else {
                System.out.println("No movie found with the given name!!");
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void fetchShowtimesForTheater(int theaterId, Connection conn) {
        List<Integer> showtimeIds = theaterTime.get(theaterId);
        if (showtimeIds != null && !showtimeIds.isEmpty()) {
            for (Integer sId : showtimeIds) {
                availableTimeSlots(sId, conn); 
            }
            showTimeSlots();  
        } else {
            System.out.println("No showtime found for the selected theater.");
        }
    }

    public void availableTimeSlots(int sId, Connection conn) {
        PreparedStatement ps1 = null;
        ResultSet rs4 = null;
        try {
            String query4 = "SELECT to_char(slot, 'HH12:MI AM') as \"12-hour Format\" FROM showtime WHERE id = ?;";
            ps1 = conn.prepareStatement(query4);
            ps1.setInt(1, sId);
            rs4 = ps1.executeQuery();

            while (rs4.next()) {
                theaterTimeId.put(sId, rs4.getString("12-hour Format"));
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            try {
                if (rs4 != null) rs4.close();
                if (ps1 != null) ps1.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void showTimeSlots() {
        for (String time : theaterTimeId.values()) {
            System.out.println("* " + time);
        }
    }

    public void Booking(String movieName, String location, String userInput, String timeInput,String date, int userId, Connection conn, User u) {
        Integer selectedTheaterId = null;
        Integer selectedShowtimeId = null;

        for (Map.Entry<Integer, String> entry : theaterMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(userInput)) {
                selectedTheaterId = entry.getKey();
                break;
            }
        }

        if (selectedTheaterId == null) {
        System.out.println("No theater found with the given name.");
            return;
         } 

            for (Map.Entry<Integer, String> entry : theaterTimeId.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(timeInput)) {
                    selectedShowtimeId = entry.getKey();
                    break;
                }
            }

            if (selectedShowtimeId == null) {
                System.out.println("No showtime found for the given time.");
                return;
            }
            
        BookingSeatSystem bookingSystem = new BookingSeatSystem();
        try {
            String query = "SELECT id, price FROM availableMovies WHERE theater_id = ? and movie_id = ? and showtime_id = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, selectedTheaterId);
            ps.setInt(2, movieId);
            ps.setInt(3, selectedShowtimeId);
            ResultSet rs = ps.executeQuery();
	
            double price = 0;
            if (rs.next()) {
                id = rs.getInt("id");
                price = rs.getDouble("price");
            }
            
            Scanner in = new Scanner(System.in);
            
	System.out.println("The Price of Movie per Ticket is 💸️ Rs. " + price);
	System.out.println("\nTo Proceed - Enter \" Yes \" otherwise Enter \"No\" ");
	String proceed = in.next();
	if(proceed.equalsIgnoreCase("Yes"))
	{
            bookingSystem.displayAvailableSeats(id, conn, date);

            System.out.println("\nHow many seats you want? : 👬️");
            int count = in.nextInt();
            System.out.println("\nEnter seat numbers one by one: ");
            String arr[] = new String[count];
            for (int i = 0; i < count; i++) {
                arr[i] = in.next();
            }
            List<String> selectedSeats = Arrays.asList(arr);

            price *= count;
            String bookingQuery = "INSERT INTO booking(user_id, movie_id, seat_count,date_to_watch, price) VALUES (?, ?, ?, ?) RETURNING id;";
            PreparedStatement bookingPs = conn.prepareStatement(bookingQuery);
            bookingPs.setInt(1, userId);
            bookingPs.setInt(2, id);
            bookingPs.setInt(3, count);
            bookingPs.setString(4,date);
            bookingPs.setDouble(5, price);
            rs = bookingPs.executeQuery();

            int bookingId = 0;
            if (rs.next()) {
                bookingId = rs.getInt("id");
            }

            if(bookingSystem.bookSelectedSeats(bookingId, selectedSeats,date, id, conn)==1)
                System.out.println("\nTicket Booked Successfully !! 🎉️\n");
            else return;

        System.out.println("****************TICKET******************");
        BookingSeatSystem bs = new BookingSeatSystem();
        bs.printTickets(bookingId,movieName,location,userInput,timeInput,date,conn, u);
        System.out.println("****************TICKET******************");
	}
	else
	{
		return;
	}
	
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }
}

