import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

 class LoginSystem {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame frame;

    public LoginSystem() {
        frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        // Create components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Add components to the frame
        frame.setLayout(null);
        usernameLabel.setBounds(20, 30, 80, 25);
        frame.add(usernameLabel);
        usernameField.setBounds(100, 30, 160, 25);
        frame.add(usernameField);
        passwordLabel.setBounds(20, 60, 80, 25);
        frame.add(passwordLabel);
        passwordField.setBounds(100, 60, 160, 25);
        frame.add(passwordField);
        loginButton.setBounds(100, 100, 80, 25);
        frame.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Access allowed.");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            FlipFlopGame G1 = new FlipFlopGame();
                            System.out.println(G1.time);

                        }
                    });
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Access denied.");
                }
            }
        });
    }

    public void showLogin() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.showLogin();
    }

    public static boolean authenticateUser(String username, String password) {
        try {
            // Establish a connection to the database
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://webdb.accdb");
            Statement stmt = conn.createStatement();

            // Prepare a SQL statement to query the database
            String sql = "SELECT * FROM users WHERE uname = '" + username.toLowerCase() + "' AND password = '" + password + "'";

            // Execute the query and check if any rows are returned
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                // Username and password match found
                resultSet.close();
                stmt.close();
                conn.close();
                return true;
            }

            // Close the database connections
            resultSet.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // No matching username and password found
    }
}
