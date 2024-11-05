package controller;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class BookingSeatSystem {
    	List<String> bookedSeats = new ArrayList<>();
    public void displayAvailableSeats(int id, Connection conn,String date) {

        String[] allSeats = {
        			"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10",
        			"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10",
        			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10",
        			"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10",
        					};
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT trim(seat.seat_number) FROM seat " +
						"JOIN booking ON seat.booking_id = booking.id "+ 
								" WHERE seat.movieSlot_id = ? and watch_date = ?; ";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2,date);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                bookedSeats.add(rs.getString("btrim"));
            }
            
            System.out.println("\nAvailable Seats:");
            for (String seat : allSeats) {
                if (bookedSeats.contains(seat)) {
                    System.out.print(" -BOOKED- ");
                } else {
                    System.out.print(seat + " ");
                }
            }
            System.out.println();
            
        } catch (SQLException e) {
            System.out.println("Error fetching seats: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    public int bookSelectedSeats(int bookingId, List<String> selectedSeats,String date, int id, Connection conn) {
        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO seat (booking_id,movieSlot_id,watch_date, seat_number) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query);

            for (String seat : selectedSeats) {
                if (bookedSeats.contains(seat)) {
                    System.out.println("You Booked Seat - "+seat+" is Already Booked.. So its Rejected ❌️");
                    System.out.println("So Your Booking is Failed");
                    String FailedQuery = "Update booking set status = ? where id = ?";
                    PreparedStatement bookingPs = conn.prepareStatement(FailedQuery);
                    bookingPs.setString(1, "Failed");
                    bookingPs.setInt(2, bookingId);
                    ResultSet rs = bookingPs.executeQuery();
                    return 0;
                }
            }

            for (String seat : selectedSeats) {

                ps.setInt(1, bookingId);
                ps.setInt(2, id);
                ps.setString(3, date);
                ps.setString(4, seat);
                ps.addBatch();  
         }

            ps.executeBatch();
            System.out.println("Seats Successfully Booked! 👍️");
            
        } catch (SQLException e) {
            System.out.println("Error booking seats: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return 1;
    }

    public void cancelUserBookedTickets(String date, Connection conn, User u)
    {
        try
        {
            String seatCancelQuery = "Update booking set status = ? where user_id = ? and date_to_watch = ? RETURNING id;";
            PreparedStatement bookingPs = conn.prepareStatement(seatCancelQuery);
            bookingPs.setString(1, "cancelled");
            bookingPs.setInt(2, u.getId());
            bookingPs.setString(4,date);
            ResultSet rs = bookingPs.executeQuery();
            if(rs.next())
            {
                int id = rs.getInt("id");
                String seatDeleteQuery = "delete from seat where booking_id = ? and watch_date = ?";
                PreparedStatement Ps = conn.prepareStatement(seatDeleteQuery);
                Ps.setInt(1, id);
                Ps.setString(2, date);
                ResultSet rs2 = bookingPs.executeQuery();
                System.out.println("Booking and Seats Cancelled Successfully !!");
            }
            else
            {
                System.out.println("You have no bookings on this Date - "+date);
                return;
            }
        }catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
    public void printTickets(int b_id,String movieName, String location, String userInput, String timeInput,String date,Connection conn, User u)
    {
    	try {
                 String query1 = "SELECT trim(seat.seat_number) FROM seat " +
						"JOIN booking ON seat.booking_id = booking.id "+ 
								" WHERE seat.booking_id = ?; ";
		   PreparedStatement  ps5 = conn.prepareStatement(query1);
		   ps5.setInt(1, b_id);
		   ResultSet rs5 = ps5.executeQuery();

		   while (rs5.next()) {
               System.out.println("\n!_____________________________________________________!\n");
               System.out.println("\nName                 : "+u.getName());
               System.out.println("\nTheater              : "+userInput);
               System.out.println("\nPlace                : "+location);
               System.out.println("\nMovie                : "+movieName);
               System.out.println("\nTimeslot             : "+timeInput);
               System.out.println("\nDate of Watching     : "+date);
		       System.out.println("\nSeat Number          : "+rs5.getString("btrim"));
               System.out.println("!_____________________________________________________!");
           }

        } catch (SQLException e) {
            System.out.println("Error fetching seats: " + e.getMessage());
        }
    }
    public void showUserBookedTickets(Connection conn, User u)
    {
        try {

            System.out.println("Movie \t| Theater \t| Place  \t| TimeSlot \t| Date to Watch \t| Date of Booking \t| Time of Booking \t| Total Seats");
            String query = "SELECT ml.movie_title, t.theater_name, t.location, st.slot, "
                    + "DATE(b.date_to_watch) AS 'Date to Watch', "
                    + "DATE(b.booked_on) AS 'Date of Booking', "
                    + "TIME(b.booked_on) AS 'Time of Booking', "
                    + "b.seat_count "
                    + "FROM booking b "
                    + "JOIN availableMovies av ON b.movie_id = av.id "
                    + "JOIN theaters t ON av.theater_id = t.id "
                    + "JOIN movieList ml ON av.movie_id = ml.id "
                    + "JOIN showtime st ON av.showtime_id = st.id "
                    + "WHERE b.user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("movie_title") + "\t| " +
                        rs.getString("theater_name") + "\t| " +
                        rs.getString("location") + "\t| " +
                        rs.getString("slot") + "\t| " +
                        rs.getDate("Date to Watch") + "\t| " +
                        rs.getDate("Date of Booking") + "\t| " +
                        rs.getTime("Time of Booking") + "\t| " +
                        rs.getInt("seat_count"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching seats: " + e.getMessage());
        }
    }
}

