package za.ac.cput.timetableproject.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ViewSavedTimetableByGroupProcedure {
    public static ResultSet execute(Connection conn, int groupId) throws SQLException {
        String sql = "SELECT * FROM timetable WHERE groupId = ?";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, groupId);
            return stmt.executeQuery();
        }
    }
}

