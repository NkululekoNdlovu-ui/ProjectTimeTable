/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.Lecture;
import za.ac.cput.timetableproject.domain.Venue;

/**
 *
 * @author hloni
 */
public class VenueDao {

    PreparedStatement ps;
    Connection con;

   public VenueDao() throws SQLException {
    try {
        // Initialize the database connection
        this.con = DatabaseConnection.createConnection();
        //createVenueTable();
        if (this.con != null && !this.con.isClosed()) {
            JOptionPane.showMessageDialog(null, "Connection Established");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to establish connection");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error establishing connection: " + e.getMessage());
    }
}

   public void createVenueTable() {
    String sql = "CREATE TABLE Venue("
               + "VenueID INT PRIMARY KEY, "
               + "VenueName VARCHAR(20), "
               + "VenueCapacity INT)";

    //PreparedStatement ps = null;

    try {
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Table created successfully or already exists.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "SQL error occurred: " + e.getMessage());
    } finally {
        // Close resources in the finally block
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
        }
    }
}


    public void udpateVenueTable(int venueId, String newVenueName, int venueCapacity) {

        String sql = "Update Venue SET VenueName = ? ,VenueCapacity = ? WHERE VenueID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, newVenueName);
            ps.setInt(2, venueCapacity);
            ps.setInt(3, venueId);

            int row = ps.executeUpdate();

            if (row > 0) {
                JOptionPane.showMessageDialog(null, "Table Updated");
            } else {
                JOptionPane.showMessageDialog(null, "Not Updatd ");
            }

        } catch (SQLException k) {

        } finally {

            try {
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException k) {
                JOptionPane.showMessageDialog(null, k.getMessage());
            }

        }
    }

  public boolean saveVenue(Venue venue) {
    if (this.con == null) {
        JOptionPane.showMessageDialog(null, "No database connection.");
        return false;
    }

    String checkSql = "SELECT COUNT(*) FROM Venue WHERE VenueID = ?";
    String sql = "INSERT INTO Venue(VenueID, VenueName, VenueCapacity) VALUES(?,?,?)";

    try {
        // Check if VenueID already exists
        ps = con.prepareStatement(checkSql);
        ps.setInt(1, venue.getVenue_id());
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();

        if (count > 0) {
            JOptionPane.showMessageDialog(null, "Error: Venue ID already exists.");
            return false;
        } else {
            // Close the first PreparedStatement
            ps.close();

            // Insert the new venue
            ps = con.prepareStatement(sql);
            ps.setInt(1, venue.getVenue_id());
            ps.setString(2, venue.getDescription());
            ps.setInt(3, venue.getCapacity());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error inserting Venue.");
            }
        }
    } catch (SQLException k) {
        JOptionPane.showMessageDialog(null, "SQL Error: " + k.getMessage());
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "Closing Error: " + k.getMessage());
        }
    }
    return false;
}


    
    public ArrayList<Venue> readVenue(){
        
        ArrayList<Venue> list = new ArrayList<>();
        String sql =  " SELECT * FROM Venue ";
        
        try{
             ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {

                while (rs.next()) {
                    int key = rs.getInt(1);
                    String name = rs.getString(2);
                    int capacity = rs.getInt(3);
                    
                    list.add(new Venue( key,name,capacity));
                    
                }
            }
        }catch(SQLException k){
            
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LectureDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }
    
     public void deleteVenue(int id){
         
         String sql = "DELETE FROM Venue WHERE VenueID = ?";
         
         try{
              ps = con.prepareStatement(sql);
            ps.setDouble(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                
            } else {
                JOptionPane.showMessageDialog(null, "No Venue with the provided id ");
            }
             
         }catch(Exception k){
             
         }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException k) {
                JOptionPane.showMessageDialog(null, k.getMessage());
            }

        }
     }
     public boolean isVenueIdExists(int id) throws SQLException {
    String sql = "SELECT COUNT(*) FROM Venue WHERE VenueID = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}
     public ArrayList<String> getAllVenues() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT * FROM Venue";  // Adjust the SQL if needed

        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    int vId = rs.getInt(1);  // Assuming ID is the first column
                    String name = rs.getString(2);  // Assuming name is the second column
                    list.add(name);
                }
            }
        } catch (SQLException k) {
            k.printStackTrace();  // Print the exception for debugging
        }
        return list;
    }

}
