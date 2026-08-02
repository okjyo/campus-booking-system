# Smart Campus Room & Facility Booking System
A Java Swing desktop application for managing campus room and facility bookings, connected to a MySQL database via JDBC.

## Prerequisites
- Java JDK 17+
- MySQL 8.0+
- VS Code with Extension Pack for Java

## Database Setup
1. Open MySQL Workbench
2. Connect to localhost:3306
3. Run this SQL:
```sql
CREATE DATABASE campus_booking;
USE campus_booking;
CREATE TABLE bookings (
    bookingID INT AUTO_INCREMENT PRIMARY KEY,
    studentName VARCHAR(100) NOT NULL,
    studentID VARCHAR(20) NOT NULL,
    facilityName VARCHAR(100) NOT NULL,
    roomNumber VARCHAR(20) NOT NULL,
    bookingDate DATE NOT NULL,
    timeSlot VARCHAR(50) NOT NULL,
    purpose VARCHAR(255),
    status VARCHAR(20) DEFAULT 'Pending'
);
```

## How to Run
Open terminal in VS Code and run:

**Step 1 — Compile:**
```bash
javac -cp "lib/mysql-connector-j-26.7.0.jar" -d bin src/*.java
```

**Step 2 — Run:**
```bash
java -cp "bin;lib/mysql-connector-j-26.7.0.jar" LoginFrame
```

## Login Credentials
| Role | Username | Password |
|------|----------|----------|
| Student | student | student123 |
| Admin | admin | admin123 |

## Features
- Student login — Add bookings, view and search records
- Admin login — Full access including update and delete
- Search by facility name or date
- Sort by date or status
- Input validation and exception handling throughout

