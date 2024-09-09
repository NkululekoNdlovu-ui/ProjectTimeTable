/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class TimetableViewDao {
    
   private PreparedStatement ps;
    private Connection con;

    public ResultSet getAllGroups() throws SQLException {
        String query = "SELECT groupId FROM groups"; // Adjust table and column name as necessary
        PreparedStatement statement = con.prepareStatement(query);
        return statement.executeQuery();
    }

    public ResultSet getTimetableByGroup(int groupId) throws SQLException {
        String query = "SELECT * FROM timetable WHERE groupId = ?"; // Adjust table and column name as necessary
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, groupId);
        return statement.executeQuery();
    }
}

