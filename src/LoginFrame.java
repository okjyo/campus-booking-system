import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;

    public LoginFrame() {
        setTitle("Campus Booking System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        cmbRole = new JComboBox<>(new String[]{"Student", "Admin"});

        panel.add(new JLabel("Username:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(new JLabel("Role:"));
        panel.add(cmbRole);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> handleLogin());

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> System.exit(0));

        panel.add(btnCancel);
        panel.add(btnLogin);

        add(new JLabel("Smart Campus Room & Facility Booking System", 
            SwingConstants.CENTER), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String role = cmbRole.getSelectedItem().toString();

        // Hardcoded credentials
        boolean validStudent = username.equals("student") && password.equals("student123");
        boolean validAdmin = username.equals("admin") && password.equals("admin123");

        if (role.equals("Student") && validStudent) {
            dispose();
            new MainFrame("student");
        } else if (role.equals("Admin") && validAdmin) {
            dispose();
            new MainFrame("admin");
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password!",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}