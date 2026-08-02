import java.sql.*;
import java.util.ArrayList;

public class BookingManager {
    private ArrayList<Booking> bookings = new ArrayList<>();

    // Add new booking
    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (studentName, studentID, facilityName, roomNumber, bookingDate, timeSlot, purpose, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getStudentName());
            stmt.setString(2, booking.getStudentID());
            stmt.setString(3, booking.getFacilityName());
            stmt.setString(4, booking.getRoomNumber());
            stmt.setString(5, booking.getBookingDate());
            stmt.setString(6, booking.getTimeSlot());
            stmt.setString(7, booking.getPurpose());
            stmt.setString(8, booking.getStatus());
            stmt.executeUpdate();
            System.out.println("Booking added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }

    // Get all bookings
    public ArrayList<Booking> getAllBookings() {
        bookings.clear();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("studentName"),
                    rs.getString("studentID"),
                    rs.getString("facilityName"),
                    rs.getString("roomNumber"),
                    rs.getString("bookingDate"),
                    rs.getString("timeSlot"),
                    rs.getString("purpose"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
        return bookings;
    }

    // Update booking
    public boolean updateBooking(int bookingID, String facilityName, String roomNumber,
                           String bookingDate, String timeSlot, String purpose, String status) {
    // Only update status if other fields are empty
        String sql;
        if (facilityName.isEmpty() && roomNumber.isEmpty() && timeSlot.isEmpty() && purpose.isEmpty()) {
            sql = "UPDATE bookings SET status=? WHERE bookingID=?";
        } else {
            sql = "UPDATE bookings SET facilityName=?, roomNumber=?, bookingDate=?, timeSlot=?, purpose=?, status=? WHERE bookingID=?";
        }

        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (facilityName.isEmpty() && roomNumber.isEmpty() && timeSlot.isEmpty() && purpose.isEmpty()) {
                stmt.setString(1, status);
                stmt.setInt(2, bookingID);
            } else {
                stmt.setString(1, facilityName);
                stmt.setString(2, roomNumber);
                stmt.setString(3, bookingDate);
                stmt.setString(4, timeSlot);
                stmt.setString(5, purpose);
                stmt.setString(6, status);
                stmt.setInt(7, bookingID);
            }
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating booking: " + e.getMessage());
            return false;
        }
    }

    // Delete booking
    public boolean deleteBooking(int bookingID) {
        String sql = "DELETE FROM bookings WHERE bookingID=?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }

    // Search by facility name
    public ArrayList<Booking> searchByFacility(String facilityName) {
        bookings.clear();
        String sql = "SELECT * FROM bookings WHERE facilityName LIKE ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + facilityName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("studentName"),
                    rs.getString("studentID"),
                    rs.getString("facilityName"),
                    rs.getString("roomNumber"),
                    rs.getString("bookingDate"),
                    rs.getString("timeSlot"),
                    rs.getString("purpose"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching: " + e.getMessage());
        }
        return bookings;
    }

    // Search by date
    public ArrayList<Booking> searchByDate(String date) {
        bookings.clear();
        String sql = "SELECT * FROM bookings WHERE bookingDate = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("studentName"),
                    rs.getString("studentID"),
                    rs.getString("facilityName"),
                    rs.getString("roomNumber"),
                    rs.getString("bookingDate"),
                    rs.getString("timeSlot"),
                    rs.getString("purpose"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error searching by date: " + e.getMessage());
        }
        return bookings;
    }

    // Sort by date
    public ArrayList<Booking> sortByDate() {
        bookings.clear();
        String sql = "SELECT * FROM bookings ORDER BY bookingDate ASC";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("studentName"),
                    rs.getString("studentID"),
                    rs.getString("facilityName"),
                    rs.getString("roomNumber"),
                    rs.getString("bookingDate"),
                    rs.getString("timeSlot"),
                    rs.getString("purpose"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error sorting: " + e.getMessage());
        }
        return bookings;
    }

    // Sort by status
    public ArrayList<Booking> sortByStatus() {
        bookings.clear();
        String sql = "SELECT * FROM bookings ORDER BY status ASC";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("bookingID"),
                    rs.getString("studentName"),
                    rs.getString("studentID"),
                    rs.getString("facilityName"),
                    rs.getString("roomNumber"),
                    rs.getString("bookingDate"),
                    rs.getString("timeSlot"),
                    rs.getString("purpose"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error sorting by status: " + e.getMessage());
        }
        return bookings;
    }
}