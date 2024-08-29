//package za.ac.cput.timetableproject.connection;
//
//import java.sql.*;
//import javax.swing.*;
//
//public class DatabaseConnection {
//    private static Connection con;
//    private static boolean connectionEstablished = false;
//
//    public static Connection createConnection() throws SQLException {
//        if (con == null || con.isClosed()) {
//            String url = "jdbc:derby://localhost:1527/Lecture5";
//            String username = JOptionPane.showInputDialog("Enter the username for the database:");
//            String password = JOptionPane.showInputDialog("Enter the password:");
//
//            con = DriverManager.getConnection(url, username, password);
//            
//            if (!connectionEstablished) {
//                JOptionPane.showMessageDialog(null, "Connection Established");
//                connectionEstablished = true;
//            }
//        }
//        return con;
//    }
//
//    public static void closeConnection() {
//        try {
//            if (con != null && !con.isClosed()) {
//                con.close();
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
//        }
//    }
//}


package za.ac.cput.timetableproject.connection;

import java.sql.*;
import javax.swing.*;

public class DatabaseConnection {
    private static Connection con;
    private static boolean connectionEstablished = false;

    public static Connection createConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            String url = "jdbc:derby://localhost:1527/Lecture5";
            String username = JOptionPane.showInputDialog("Enter the username for the database:");

            // Create a JPasswordField to securely input the password
            JPasswordField passwordField = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(
                null,
                passwordField,
                "Enter the password:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                // Retrieve the password as a char array
                char[] password = passwordField.getPassword();

                // Convert the char array to a String
                String passwordString = new String(password);

                // Establish the connection
                con = DriverManager.getConnection(url, username, passwordString);

                if (!connectionEstablished) {
                    //JOptionPane.showMessageDialog(null, "Connection Established");
                    connectionEstablished = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Connection canceled by user.");
                throw new SQLException("User canceled the connection.");
            }
        }
        return con;
    }

    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing connection: " + e.getMessage());
        }
    }
}
