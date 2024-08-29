/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.logging.Logger;

import javax.swing.*;
import za.ac.cput.timetableproject.connection.DatabaseConnection;
import za.ac.cput.timetableproject.domain.Lecture;

/**
 *
 * @author hloni
 */
public class LectureDao {

    private Connection con;
    private PreparedStatement ps;
    private DatabaseConnection dao;

    public LectureDao() {
        try {
            if (this.con == null || this.con.isClosed()) {  // Check if the connection is null or closed
                this.con = DatabaseConnection.createConnection();
                JOptionPane.showMessageDialog(null, "Connection Established");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occurred: " + k.getMessage());
        }
    }

    public void createLectureTable() {

        String sql = "CREATE table Lecture (" + "LectureID INT PRIMARY KEY,"
                + "LectureName VARCHAR(150)," + "LectureSurname VARCHAR(150) ,"
                + "LectureIntials VARCHAR(5))";

        try {
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have sucessfully created a table");
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, "SQL error occured " + k);
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

    public void udpateLectureTable(String oldName, String oldSurname, String intials, String newName, String newSurname) {

        String sql = "Update Lecture SET LectureName = ? , LectureSurname = ? ,LectureIntials = ?WHERE LectureName = ? AND LectureSurname = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, newSurname);
            ps.setString(3, intials);
            ps.setString(4, oldName);
            ps.setString(5, oldSurname);
            int rowsUpdated = ps.executeUpdate();

        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, k.getMessage());
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

    public ArrayList<Lecture> readLecture() {

        ArrayList<Lecture> list = new ArrayList<>();

        String sql = "SELECT * FROM Lecture";

        try {
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {

                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String surname = rs.getString(3);
                    String intial = rs.getString(4);

                    list.add(new Lecture(id, name, surname, intial));

                }
            }

        } catch (SQLException k) {

        } finally {
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

    public boolean save(Lecture lecture) {

        String sql = "INSERT INTO Lecture(LectureID, LectureName,LectureSurname,LectureIntials) VALUES(?,?,?,?)";

        String checkSql = "SELECT COUNT(*) FROM Lecture WHERE LectureID = ?";
        try {
            ps = this.con.prepareStatement(checkSql);
            ps.setInt(1, lecture.getLectureID());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                JOptionPane.showMessageDialog(null, "Error: Venue_Id already exists.");
                return false;
            } else {
                ps.close();

                ps = con.prepareStatement(sql);
                ps.setInt(1, lecture.getLectureID());
                ps.setString(2, lecture.getLectureName());
                ps.setString(3, lecture.getLectureSurname());
                ps.setString(4, lecture.getLectureIntials());

                int ok = ps.executeUpdate();

                if (ok > 0) {

                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Error inserting Lecture.");
                }

            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, k.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException k) {
                JOptionPane.showMessageDialog(null, k.getMessage());
            }

        }
        return false;
    }

    public void delete(int id) {

        String sql = "DELETE  FROM Lecture WHERE LectureID = ? ";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                
            } else {
                JOptionPane.showMessageDialog(null, "No lecture found with the provided name and surname.");
            }
        } catch (SQLException k) {
            JOptionPane.showMessageDialog(null, k.getMessage());
        }
    }

    public boolean isLectureIdExists(int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lecture WHERE LectureID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean isLectureExists(String name, String surname) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lecture WHERE LectureName = ? AND LectureSurname = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, surname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    public ArrayList<String> lecture(){
        
        ArrayList<String> list = new ArrayList();
        
        String sql = "Select * FROM Lecture";
        
        try{
            
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if(rs != null){
                
                while(rs.next()){
                    
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String surname = rs.getString(3);
                    String inti = rs.getString(4);
                    
                    list.add(name +" "+surname);
                }
                
            }
                    
        }catch(Exception k){
            k.getMessage();
        }
        return list;
    }

}
