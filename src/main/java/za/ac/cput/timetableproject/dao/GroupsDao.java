///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package za.ac.cput.timetableproject.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import javax.swing.JOptionPane;
//import za.ac.cput.timetableproject.connection.DatabaseConnection;
//import za.ac.cput.timetableproject.domain.Group;
//
///////**
//// *
//// * @author hloni
//// */
//// 
////public class GroupsDao {
////    PreparedStatement ps;
////    Connection con;
////    
////    public GroupsDao() {
////        try {
////            if (this.con == null || this.con.isClosed()) {  // Check if the connection is null or closed
////                this.con = DatabaseConnection.createConnection();
////                JOptionPane.showMessageDialog(null, "Connection Established");
////            }
////        } catch (SQLException k) {
////            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
////        }
////    }
////    
////    
////    public boolean save(Group group) {
////        String sql = "INSERT INTO Group (GroupID, GroupCapacity) VALUES (?, ?)";
////
////        String checkSql = "SELECT COUNT(*) FROM Group WHERE GroupID = ?";
////        try {
////            ps = this.con.prepareStatement(checkSql);
////            ps.setInt(1,group.getGroupId() );
////            ResultSet rs = ps.executeQuery();
////            rs.next();
////            int count = rs.getInt(1);
////
////            if (count > 0) {
////                JOptionPane.showMessageDialog(null, "Error: GroupID already exists.");
////                return false;
////            } else {
////                ps.close();
////
////                ps = con.prepareStatement(sql);
////                ps.setInt(1, group.getGroupId());
////                ps.setInt(2, group.getGroupCapacity());
////
////                int ok = ps.executeUpdate();
////
////                if (ok > 0) {
////                    return true;
////                } else {
////                    JOptionPane.showMessageDialog(null, "Error inserting Group.");
////                }
////            }
////        } catch (SQLException k) {
////            JOptionPane.showMessageDialog(null, k.getMessage());
////        } finally {
////            try {
////                if (ps != null) {
////                    ps.close();
////                }
////            } catch (SQLException k) {
////                JOptionPane.showMessageDialog(null, k.getMessage());
////            }
////        }
////        return false;
////    }
////
////    // Method to update group information
////    public void updateGroup(String groupId, int newCapacity) {
////        String sql = "UPDATE Group SET GroupCapacity = ? WHERE GroupID = ?";
////
////        try {
////            ps = con.prepareStatement(sql);
////            ps.setInt(1, newCapacity);
////            ps.setString(2, groupId);
////
////            int rowsUpdated = ps.executeUpdate();
////            if (rowsUpdated > 0) {
////                JOptionPane.showMessageDialog(null, "Group updated successfully.");
////            } else {
////                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
////            }
////        } catch (SQLException k) {
////            JOptionPane.showMessageDialog(null, k.getMessage());
////        } finally {
////            try {
////                if (ps != null) {
////                    ps.close();
////                }
////            } catch (SQLException k) {
////                JOptionPane.showMessageDialog(null, k.getMessage());
////            }
////        }
////    }
////
////    // Method to delete a group by its ID
////    public void deleteGroup(String groupId) {
////        String sql = "DELETE FROM Group WHERE GroupID = ?";
////
////        try {
////            ps = con.prepareStatement(sql);
////            ps.setString(1, groupId);
////
////            int rowsDeleted = ps.executeUpdate();
////            if (rowsDeleted > 0) {
////                JOptionPane.showMessageDialog(null, "Group deleted successfully.");
////            } else {
////                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
////            }
////        } catch (SQLException k) {
////            JOptionPane.showMessageDialog(null, k.getMessage());
////        } finally {
////            try {
////                if (ps != null) {
////                    ps.close();
////                }
////            } catch (SQLException k) {
////                JOptionPane.showMessageDialog(null, k.getMessage());
////            }
////        }
////    }
////
////    // Method to check if a group ID exists
////    public boolean isGroupIdExists(int groupId) throws SQLException {
////        String sql = "SELECT COUNT(*) FROM Group WHERE GroupID = ?";
////        try (PreparedStatement ps = con.prepareStatement(sql)) {
////            ps.setInt(1, groupId);
////            ResultSet rs = ps.executeQuery();
////            if (rs.next()) {
////                return rs.getInt(1) > 0;
////            }
////        }
////        return false;
////    }
////
////    // Method to read all groups from the database
////    public ArrayList<Group> readGroups() {
////        ArrayList<Group> list = new ArrayList<>();
////
////        String sql = "SELECT * FROM Group";
////
////        try {
////            ps = con.prepareStatement(sql);
////            ResultSet rs = ps.executeQuery();
////            if (rs != null) {
////                while (rs.next()) {
////                    int id = rs.getInt(1);
////                    int capacity = rs.getInt(2);
////                    list.add(new Group(id, capacity));
////                }
////            }
////        } catch (SQLException k) {
////            JOptionPane.showMessageDialog(null, k.getMessage());
////        } finally {
////            if (ps != null) {
////                try {
////                    ps.close();
////                } catch (SQLException ex) {
////                  
////                }
////            }
////        }
////        return list;
////    }
////
////}
//
//package za.ac.cput.timetableproject.dao;
//
//import java.sql.*;
//import java.util.ArrayList;
//import javax.swing.JOptionPane;
//import za.ac.cput.timetableproject.connection.DatabaseConnection;
//import za.ac.cput.timetableproject.domain.Group;
//
//public class GroupsDao {
//
//    private PreparedStatement ps;
//    private Connection con;
//
//    public GroupsDao() {
//        try {
//            // Initialize the database connection
//            if (this.con == null || this.con.isClosed()) {
//                this.con = DatabaseConnection.createConnection();
//                JOptionPane.showMessageDialog(null, "Connection Established");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL error occurred: " + e.getMessage());
//        }
//    }
//
//    public boolean save(Group group) {
//        if (this.con == null) {
//            JOptionPane.showMessageDialog(null, "No database connection.");
//            return false;
//        }
//
//        String checkSql = "SELECT COUNT(*) FROM `Group` WHERE GroupID = ?";
//        String sql = "INSERT INTO `Group` (GroupID, GroupCapacity) VALUES (?, ?)";
//
//        try {
//            // Check if GroupID already exists
//            ps = con.prepareStatement(checkSql);
//            ps.setInt(1, group.getGroupId());
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            int count = rs.getInt(1);
//            rs.close();
//
//            if (count > 0) {
//                JOptionPane.showMessageDialog(null, "Error: GroupID already exists.");
//                return false;
//            } else {
//                // Close the first PreparedStatement
//                ps.close();
//
//                // Insert the new group
//                ps = con.prepareStatement(sql);
//                ps.setInt(1, group.getGroupId());
//                ps.setInt(2, group.getGroupCapacity());
//
//                int rowsAffected = ps.executeUpdate();
//                if (rowsAffected > 0) {
//                    return true;
//                } else {
//                    JOptionPane.showMessageDialog(null, "Error inserting Group.");
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//        return false;
//    }
//
//    public void updateGroup(int groupId, int newCapacity) {
//        String sql = "UPDATE `Group` SET GroupCapacity = ? WHERE GroupID = ?";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, newCapacity);
//            ps.setInt(2, groupId);
//
//            int rowsUpdated = ps.executeUpdate();
//            if (rowsUpdated > 0) {
//                JOptionPane.showMessageDialog(null, "Group updated successfully.");
//            } else {
//                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//    }
//
//    public void deleteGroup(int groupId) {
//        String sql = "DELETE FROM `Group` WHERE GroupID = ?";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, groupId);
//
//            int rowsDeleted = ps.executeUpdate();
//            if (rowsDeleted > 0) {
//                JOptionPane.showMessageDialog(null, "Group deleted successfully.");
//            } else {
//                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//    }
//
//    public boolean isGroupIdExists(int groupId) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM `Group` WHERE GroupID = ?";
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, groupId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        }
//        return false;
//    }
//
//    public ArrayList<Group> readGroups() {
//        ArrayList<Group> list = new ArrayList<>();
//        String sql = "SELECT * FROM `Group`";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            if (rs != null) {
//                while (rs.next()) {
//                    int id = rs.getInt("GroupID");
//                    int capacity = rs.getInt("GroupCapacity");
//                    list.add(new Group(id, capacity));
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//        return list;
//    }
//
//    private void closePreparedStatement() {
//        try {
//            if (ps != null) {
//                ps.close();
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
//        }
//    }
//}
//

//
//public class GroupsDao {
//
//    private PreparedStatement ps;
//    private Connection con;
//
//    public GroupsDao() {
//        try {
//            if (this.con == null || this.con.isClosed()) {
//                this.con = DatabaseConnection.createConnection();
//                JOptionPane.showMessageDialog(null, "Connection Established");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL error occurred: " + e.getMessage());
//        }
//    }
//    
//
//    public boolean save(Group group) {
//        if (this.con == null) {
//            JOptionPane.showMessageDialog(null, "No database connection.");
//            return false;
//        }
//
//        String checkSql = "SELECT COUNT(*) FROM `Group` WHERE GroupID = ?";
//        String sql = "INSERT INTO `Group` (GroupID, GroupName) VALUES (?, ?)";
//
//        try {
//            ps = con.prepareStatement(checkSql);
//            ps.setInt(1, group.getGroupId());
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            int count = rs.getInt(1);
//            rs.close();
//
//            if (count > 0) {
//                JOptionPane.showMessageDialog(null, "Error: GroupID already exists.");
//                return false;
//            } else {
//                ps.close();
//
//                ps = con.prepareStatement(sql);
//                ps.setInt(1, group.getGroupId());
//                ps.setString(2, group.getGroupName());
//
//                int rowsAffected = ps.executeUpdate();
//                return rowsAffected > 0;
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//        return false;
//    }
//
//    public void updateGroup(int groupId, String newGroupName) {
//        String sql = "UPDATE `Group` SET GroupName = ? WHERE GroupID = ?";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setString(1, newGroupName);
//            ps.setInt(2, groupId);
//
//            int rowsUpdated = ps.executeUpdate();
//            if (rowsUpdated > 0) {
//                JOptionPane.showMessageDialog(null, "Group updated successfully.");
//            } else {
//                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//    }
//
//    public void deleteGroup(int groupId) {
//        String sql = "DELETE FROM `Group` WHERE GroupID = ?";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ps.setInt(1, groupId);
//
//            int rowsDeleted = ps.executeUpdate();
//            if (rowsDeleted > 0) {
//                JOptionPane.showMessageDialog(null, "Group deleted successfully.");
//            } else {
//                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//    }
//
//    public boolean isGroupIdExists(int groupId) throws SQLException {
//        String sql = "SELECT COUNT(*) FROM `Group` WHERE GroupID = ?";
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, groupId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0;
//            }
//        }
//        return false;
//    }
//
//    public ArrayList<Group> readGroups() {
//        ArrayList<Group> list = new ArrayList<>();
//        String sql = "SELECT * FROM `Group`";
//
//        try {
//            ps = con.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            if (rs != null) {
//                while (rs.next()) {
//                    int id = rs.getInt("GroupID");
//                    String groupName = rs.getString("GroupName");
//                    list.add(new Group(id, groupName));
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
//        } finally {
//            closePreparedStatement();
//        }
//        return list;
//    }
//
//    private void closePreparedStatement() {
//        try {
//            if (ps != null) {
//                ps.close();
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
//        }
//    }
//}


    package za.ac.cput.timetableproject.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.Group;

public class GroupsDao {

    private PreparedStatement ps;
    private Connection con;

    public GroupsDao() throws SQLException {
        try {
            // Initialize the database connection
            this.con = DatabaseConnection.createConnection();
            //createGroupTable();
            if (this.con != null && !this.con.isClosed()) {
                JOptionPane.showMessageDialog(null, "Connection Established");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to establish connection.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + e.getMessage());
        }
    }

    // Method to create Group table if it doesn't exist
    private void createGroupTable() throws SQLException {
        String createTableSQL = "CREATE TABLE Groups("
                + "GroupID INT PRIMARY KEY, "
                + "GroupName VARCHAR(255) NOT NULL)";
        
        try (Statement stmt = con.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating Group table: " + e.getMessage());
        }
    }

    public boolean save(Group group) {
        if (this.con == null) {
            JOptionPane.showMessageDialog(null, "No database connection.");
            return false;
        }

        String checkSql = "SELECT COUNT(*) FROM Groups WHERE GroupID = ?";
        String sql = "INSERT INTO Groups(GroupID, GroupName) VALUES (?, ?)";

        try {
            // Check if GroupID already exists
            ps = con.prepareStatement(checkSql);
            ps.setInt(1, group.getGroupId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Error: GroupID already exists.");
                return false;
            } else {
                // Insert the new group
                ps = con.prepareStatement(sql);
                ps.setInt(1, group.getGroupId());
                ps.setString(2, group.getGroupName());

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } finally {
            closePreparedStatement();
        }
        return false;
    }

    public void updateGroup(int groupId, String newGroupName) {
        String sql = "UPDATE Groups SET GroupName = ? WHERE GroupID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, newGroupName);
            ps.setInt(2, groupId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Group updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } finally {
            closePreparedStatement();
        }
    }

    public void deleteGroup(int groupId) {
        String sql = "DELETE FROM Groups WHERE GroupID = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, groupId);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Group deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "No group found with the provided GroupID.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } finally {
            closePreparedStatement();
        }
    }

    public boolean isGroupIdExists(int groupId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Groups WHERE GroupID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, groupId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public ArrayList<Group> readGroups() {
        ArrayList<Group> list = new ArrayList<>();
        String sql = "SELECT * FROM Groups";

        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("GroupID");
                    String groupName = rs.getString("GroupName");
                    list.add(new Group(id, groupName));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } finally {
            closePreparedStatement();
        }
        return list;
    }

    private void closePreparedStatement() {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing resources: " + e.getMessage());
        }
    }
    
    public ArrayList<String> getAllGroups(){
        ArrayList<String> list = new ArrayList();
        String sql = "SELECT * FROM Groups";
        
        try{
            ps = con.prepareStatement(sql);
            
            
            ResultSet rs = ps.executeQuery();
            
            if(rs != null){
                while(rs.next()){
                    
                    int gId = rs.getInt(1);
                    String name = rs.getString(2);
                    list.add(name);
                    
                }
            }
        }catch(SQLException k){
            
        }
        return list;
    }
}

