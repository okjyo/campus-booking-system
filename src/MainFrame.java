import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private BookingManager bookingManager = new BookingManager();
    private DefaultTableModel tableModel;
    private JTable bookingTable;

    // Form fields
    private JTextField txtStudentName, txtStudentID, txtFacilityName;
    private JTextField txtRoomNumber, txtBookingDate, txtTimeSlot, txtPurpose;
    private JComboBox<String> cmbStatus;
    private JTextField txtSearch;
    private JComboBox<String> cmbSearchType, cmbSortType;
    private JTextField txtUpdateID, txtDeleteID;

    public MainFrame(String role) {
        setTitle("Smart Campus Room & Facility Booking System - " + 
        (role.equals("admin") ? "Admin" : "Student"));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("View All Bookings", createViewPanel());
        tabbedPane.addTab("Add Booking", createAddPanel());

        // Only admin can see Update and Delete tabs
        if (role.equals("admin")) {
            tabbedPane.addTab("Update Booking", createUpdatePanel());
            tabbedPane.addTab("Delete Booking", createDeletePanel());
        }

        tabbedPane.addTab("Search & Sort", createSearchSortPanel());

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
    });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnLogout);

        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
}

    // ── VIEW ALL BOOKINGS TAB ──
    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Student Name", "Student ID", "Facility",
                            "Room", "Date", "Time Slot", "Purpose", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingTable);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadTable(bookingManager.getAllBookings()));

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnRefresh, BorderLayout.SOUTH);

        loadTable(bookingManager.getAllBookings());
        return panel;
    }

    // ── ADD BOOKING TAB ──
    private JPanel createAddPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        txtStudentName = new JTextField();
        txtStudentID = new JTextField();
        txtFacilityName = new JTextField();
        txtRoomNumber = new JTextField();
        txtBookingDate = new JTextField("YYYY-MM-DD");
        txtTimeSlot = new JTextField("e.g. 9:00AM - 11:00AM");
        txtPurpose = new JTextField();
        cmbStatus = new JComboBox<>(new String[]{"Pending"});
        cmbStatus.setEnabled(false);

        panel.add(new JLabel("Student Name:"));
        panel.add(txtStudentName);
        panel.add(new JLabel("Student ID:"));
        panel.add(txtStudentID);
        panel.add(new JLabel("Facility Name:"));
        panel.add(txtFacilityName);
        panel.add(new JLabel("Room Number:"));
        panel.add(txtRoomNumber);
        panel.add(new JLabel("Booking Date (YYYY-MM-DD):"));
        panel.add(txtBookingDate);
        panel.add(new JLabel("Time Slot:"));
        panel.add(txtTimeSlot);
        panel.add(new JLabel("Purpose:"));
        panel.add(txtPurpose);
        panel.add(new JLabel("Status:"));
        panel.add(cmbStatus);

        JButton btnAdd = new JButton("Add Booking");
        btnAdd.addActionListener(e -> {
            // Input validation
            if (txtStudentName.getText().isEmpty() || txtStudentID.getText().isEmpty() ||
                txtFacilityName.getText().isEmpty() || txtRoomNumber.getText().isEmpty() ||
                txtBookingDate.getText().isEmpty() || txtTimeSlot.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields!", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Booking booking = new Booking(0,
                    txtStudentName.getText(),
                    txtStudentID.getText(),
                    txtFacilityName.getText(),
                    txtRoomNumber.getText(),
                    txtBookingDate.getText(),
                    txtTimeSlot.getText(),
                    txtPurpose.getText(),
                    cmbStatus.getSelectedItem().toString()
                );
                bookingManager.addBooking(booking);
                JOptionPane.showMessageDialog(this, "Booking added successfully!");
                clearAddFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(btnAdd);
        return panel;
    }

    // ── UPDATE BOOKING TAB ──
    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        txtUpdateID = new JTextField();
        JTextField txtUpdFacility = new JTextField();
        JTextField txtUpdRoom = new JTextField();
        JTextField txtUpdDate = new JTextField("YYYY-MM-DD");
        JTextField txtUpdTimeSlot = new JTextField();
        JTextField txtUpdPurpose = new JTextField();
        JComboBox<String> cmbUpdStatus = new JComboBox<>(
            new String[]{"Pending", "Approved", "Cancelled"});

        panel.add(new JLabel("Booking ID to Update:"));
        panel.add(txtUpdateID);
        panel.add(new JLabel("New Facility Name:"));
        panel.add(txtUpdFacility);
        panel.add(new JLabel("New Room Number:"));
        panel.add(txtUpdRoom);
        panel.add(new JLabel("New Date (YYYY-MM-DD):"));
        panel.add(txtUpdDate);
        panel.add(new JLabel("New Time Slot:"));
        panel.add(txtUpdTimeSlot);
        panel.add(new JLabel("New Purpose:"));
        panel.add(txtUpdPurpose);
        panel.add(new JLabel("New Status:"));
        panel.add(cmbUpdStatus);

        JButton btnUpdate = new JButton("Update Booking");
        btnUpdate.addActionListener(e -> {
            if (txtUpdateID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Booking ID!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtUpdateID.getText());
                bookingManager.updateBooking(id,
                    txtUpdFacility.getText(),
                    txtUpdRoom.getText(),
                    txtUpdDate.getText(),
                    txtUpdTimeSlot.getText(),
                    txtUpdPurpose.getText(),
                    cmbUpdStatus.getSelectedItem().toString()
                );
                JOptionPane.showMessageDialog(this, "Booking updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Booking ID must be a number!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(btnUpdate);
        return panel;
    }

    // ── DELETE BOOKING TAB ──
    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        txtDeleteID = new JTextField();

        panel.add(new JLabel("Booking ID to Delete:"));
        panel.add(txtDeleteID);

        JButton btnDelete = new JButton("Delete Booking");
        btnDelete.addActionListener(e -> {
            if (txtDeleteID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Booking ID!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtDeleteID.getText());
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete booking ID " + id + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    bookingManager.deleteBooking(id);
                    JOptionPane.showMessageDialog(this, "Booking deleted successfully!");
                    txtDeleteID.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Booking ID must be a number!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel());
        panel.add(btnDelete);
        return panel;
    }

    // ── SEARCH & SORT TAB ──
    private JPanel createSearchSortPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        txtSearch = new JTextField();
        cmbSearchType = new JComboBox<>(new String[]{"Search by Facility", "Search by Date"});
        cmbSortType = new JComboBox<>(new String[]{"Sort by Date", "Sort by Status"});

        String[] columns = {"ID", "Student Name", "Student ID", "Facility",
                            "Room", "Date", "Time Slot", "Purpose", "Status"};
        DefaultTableModel searchTableModel = new DefaultTableModel(columns, 0);
        JTable searchTable = new JTable(searchTableModel);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search keyword!",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList<Booking> results;
            if (cmbSearchType.getSelectedIndex() == 0) {
                results = bookingManager.searchByFacility(keyword);
            } else {
                results = bookingManager.searchByDate(keyword);
            }
            loadToModel(searchTableModel, results);
        });

        JButton btnSort = new JButton("Sort");
        btnSort.addActionListener(e -> {
            ArrayList<Booking> results;
            if (cmbSortType.getSelectedIndex() == 0) {
                results = bookingManager.sortByDate();
            } else {
                results = bookingManager.sortByStatus();
            }
            loadToModel(searchTableModel, results);
        });

        controlPanel.add(new JLabel("Search Keyword:"));
        controlPanel.add(txtSearch);
        controlPanel.add(new JLabel("Search Type:"));
        controlPanel.add(cmbSearchType);
        controlPanel.add(new JLabel());
        controlPanel.add(btnSearch);
        controlPanel.add(new JLabel("Sort Type:"));
        controlPanel.add(cmbSortType);

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnSearch);
        btnPanel.add(btnSort);

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(searchTable), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ── HELPER METHODS ──
    private void loadTable(ArrayList<Booking> list) {
        tableModel.setRowCount(0);
        for (Booking b : list) {
            tableModel.addRow(new Object[]{
                b.getBookingID(), b.getStudentName(), b.getStudentID(),
                b.getFacilityName(), b.getRoomNumber(), b.getBookingDate(),
                b.getTimeSlot(), b.getPurpose(), b.getStatus()
            });
        }
    }

    private void loadToModel(DefaultTableModel model, ArrayList<Booking> list) {
        model.setRowCount(0);
        for (Booking b : list) {
            model.addRow(new Object[]{
                b.getBookingID(), b.getStudentName(), b.getStudentID(),
                b.getFacilityName(), b.getRoomNumber(), b.getBookingDate(),
                b.getTimeSlot(), b.getPurpose(), b.getStatus()
            });
        }
    }

    private void clearAddFields() {
        txtStudentName.setText("");
        txtStudentID.setText("");
        txtFacilityName.setText("");
        txtRoomNumber.setText("");
        txtBookingDate.setText("YYYY-MM-DD");
        txtTimeSlot.setText("e.g. 9:00AM - 11:00AM");
        txtPurpose.setText("");
        cmbStatus.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}