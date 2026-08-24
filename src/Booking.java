public class Booking {
    private int bookingID;
    private String studentName;
    private String studentID;
    private String facilityName;
    private String roomNumber;
    private String bookingDate;
    private String timeSlot;
    private String purpose;
    private String status;

    public Booking(int bookingID, String studentName, String studentID,
                   String facilityName, String roomNumber, String bookingDate,
                   String timeSlot, String purpose, String status) {
        this.bookingID = bookingID;
        this.studentName = studentName;
        this.studentID = studentID;
        this.facilityName = facilityName;
        this.roomNumber = roomNumber;
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.purpose = purpose;
        this.status = status;
    }

    public int getBookingID() { return bookingID; }
    public String getStudentName() { return studentName; }
    public String getStudentID() { return studentID; }
    public String getFacilityName() { return facilityName; }
    public String getRoomNumber() { return roomNumber; }
    public String getBookingDate() { return bookingDate; }
    public String getTimeSlot() { return timeSlot; }
    public String getPurpose() { return purpose; }
    public String getStatus() { return status; }

    public void setBookingID(int bookingID) { this.bookingID = bookingID; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setStudentID(String studentID) { this.studentID = studentID; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Booking[ID=" + bookingID + ", Student=" + studentName +
               ", Facility=" + facilityName + ", Room=" + roomNumber +
               ", Date=" + bookingDate + ", Status=" + status + "]";
    }
}