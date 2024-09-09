//package za.ac.cput.timetableproject.gui;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import za.ac.cput.timetableproject.dao.TimetableViewDao;
//import za.ac.cput.timetableproject.domain.TimeTable;
//
//public class ViewTimetableGui extends JPanel {
//
//    private JTable timetableTable;
//    private DefaultTableModel tableModel;
//    private JComboBox<String> groupComboBox; // ComboBox for group selection
//    private Map<String, Integer> groupMap; // To map group names to IDs
//
//    public ViewTimetableGui() {
//        setLayout(new BorderLayout());
//        initializeUI();
//    }
//
//    private void initializeUI() {
//        // Set up the table model and JTable
//        String[] columnNames = {"TimeTable ID", "Venue ID", "Subject ID", "Lecture ID", "Group ID", "Slot ID"};
//        tableModel = new DefaultTableModel(columnNames, 0);
//        timetableTable = new JTable(tableModel);
//
//        // Add the table to a JScrollPane
//        JScrollPane scrollPane = new JScrollPane(timetableTable);
//        add(scrollPane, BorderLayout.CENTER);
//
//        // Create panel for controls
//        JPanel controlPanel = new JPanel();
//        controlPanel.setLayout(new FlowLayout());
//
//        // Create and add the combo box
//        groupComboBox = new JComboBox<>();
//        groupMap = new HashMap<>();
//        populateGroupComboBox(); // Populate with group names and IDs
//        controlPanel.add(new JLabel("Select Group:"));
//        controlPanel.add(groupComboBox);
//
//        // Create and add the view button
//        JButton viewButton = new JButton("View Timetable");
//        viewButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String selectedGroupName = (String) groupComboBox.getSelectedItem();
//                Integer selectedGroupId = groupMap.get(selectedGroupName);
//                if (selectedGroupId != null) {
//                    loadTimetableData(selectedGroupId);
//                }
//            }
//        });
//        controlPanel.add(viewButton);
//
//        // Add control panel to the top of the main panel
//        add(controlPanel, BorderLayout.NORTH);
//    }
//
//    private void populateGroupComboBox() {
//        TimetableViewDao dao = new TimetableViewDao();
//        try {
//            // Fetch group names and IDs
//            Map<String, Integer> groupData = dao.getGroupData();
//            for (Map.Entry<String, Integer> entry : groupData.entrySet()) {
//                groupComboBox.addItem(entry.getKey());
//                groupMap.put(entry.getKey(), entry.getValue());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error loading group data.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void loadTimetableData(int groupId) {
//        TimetableViewDao dao = new TimetableViewDao();
//        try {
//            // Fetch data based on selected group ID
//            List<TimeTable> timetableList = dao.getTimetableByGroup(groupId);
//
//            // Clear existing data
//            tableModel.setRowCount(0);
//
//            // Add data to table model
//            for (TimeTable timetable : timetableList) {
//                Object[] rowData = {
//                    timetable.getTimeTableId(),
//                    timetable.getVenueId(),
//                    timetable.getSubjectId(),
//                    timetable.getLectureId(),
//                    timetable.getGroupId(),
//                    timetable.getSlotId()
//                };
//                tableModel.addRow(rowData);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error loading timetable data.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}


package za.ac.cput.timetableproject.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.ac.cput.timetableproject.dao.TimetableViewDao;

public class ViewTimetableGui extends JPanel {

    private JTable timetableTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> groupComboBox; // ComboBox for group selection

    public ViewTimetableGui() {
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        // Set up the table model and JTable
        String[] columnNames = {"Day", "08:30 - 09:10", "09:15 - 09:55", "10:00 - 10:40", "10:45 - 11:25",
            "11:30 - 12:10", "12:15 - 12:55", "13:00 - 13:40", "13:45 - 14:25",
            "14:30 - 15:10", "15:15 - 15:55", "16:00 - 16:40", "16:45 - 17:25"};
        tableModel = new DefaultTableModel(columnNames, 5);
        timetableTable = new JTable(tableModel);

        // Set column widths
        int columnWidth = 100; // Adjust as needed
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            timetableTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
        }

        // Set fixed row height to match button heights
        int rowHeight = 60; // Adjust as needed to match button height
        timetableTable.setRowHeight(rowHeight);

        // Add the table to a JScrollPane with horizontal and vertical scroll bars
        JScrollPane scrollPane = new JScrollPane(timetableTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Optional: remove border for better integration
        add(scrollPane, BorderLayout.CENTER);

        // Create panel for controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create and add the combo box
        groupComboBox = new JComboBox<>();
        populateGroupComboBox(); // Populate with group IDs
        controlPanel.add(new JLabel("Select Group:"));
        controlPanel.add(groupComboBox);

        // Create and add the view button
        JButton viewButton = new JButton("View Timetable");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = (String) groupComboBox.getSelectedItem();
                if (selectedGroup != null) {
                    int groupId = Integer.parseInt(selectedGroup); // Assuming the combo box contains group IDs as strings
                    loadTimetableData(groupId);
                } else {
                    JOptionPane.showMessageDialog(ViewTimetableGui.this, "Please select a group.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        controlPanel.add(viewButton);

        // Add control panel to the top of the main panel
        add(controlPanel, BorderLayout.NORTH);

        // Set default values for days
        tableModel.setValueAt("Monday", 0, 0);
        tableModel.setValueAt("Tuesday", 1, 0);
        tableModel.setValueAt("Wednesday", 2, 0);
        tableModel.setValueAt("Thursday", 3, 0);
        tableModel.setValueAt("Friday", 4, 0);

        // Set default values for breaks
        for (int i = 0; i < 5; i++) {
            tableModel.setValueAt("Break", i, 7); // Assuming column 7 is reserved for breaks
        }
    }

    private void populateGroupComboBox() {
        // Fetch group IDs from the database and populate the combo box
        // Example: Replace with actual data fetching logic
        groupComboBox.addItem("1");
        groupComboBox.addItem("2");
        groupComboBox.addItem("3");
    }

    private void loadTimetableData(int groupId) {
        TimetableViewDao dao = new TimetableViewDao();
        try {
            // Fetch data based on selected group ID
            ResultSet resultSet = dao.getTimetableByGroup(groupId);

            // Clear existing data
            tableModel.setRowCount(0);
            tableModel.setRowCount(5);

            // Set default values for days
            for (int i = 0; i < 5; i++) {
                tableModel.setValueAt(getDayName(i), i, 0);
                tableModel.setValueAt("Break", i, 7); // Assuming column 7 is reserved for breaks
            }

            // Add data to table model
            while (resultSet.next()) {
                String day = resultSet.getString("day");
                String slot = resultSet.getString("slot");
                String type = resultSet.getString("type");
                String subject = resultSet.getString("subject");
                String venue = resultSet.getString("venue");
                String lecturer = resultSet.getString("lecturer");

                // Find row and column indices
                int rowIndex = getRowIndexForDay(day);
                int columnIndex = getColumnIndexForSlot(slot);

                // Ensure indices are valid
                if (rowIndex != -1 && columnIndex != -1) {
                    tableModel.setValueAt(type + ": " + subject + " (" + lecturer + ") - " + venue, rowIndex, columnIndex);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading timetable data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getDayName(int index) {
        switch (index) {
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thursday";
            case 4: return "Friday";
            default: return "";
        }
    }

    private int getRowIndexForDay(String day) {
        switch (day) {
            case "Monday": return 0;
            case "Tuesday": return 1;
            case "Wednesday": return 2;
            case "Thursday": return 3;
            case "Friday": return 4;
            default: return -1;
        }
    }

    private int getColumnIndexForSlot(String slot) {
        switch (slot) {
            case "08:30 - 09:10": return 1;
            case "09:15 - 09:55": return 2;
            case "10:00 - 10:40": return 3;
            case "10:45 - 11:25": return 4;
            case "11:30 - 12:10": return 5;
            case "12:15 - 12:55": return 6;
            case "13:00 - 13:40": return 7;
            case "13:45 - 14:25": return 8;
            case "14:30 - 15:10": return 9;
            case "15:15 - 15:55": return 10;
            case "16:00 - 16:40": return 11;
            case "16:45 - 17:25": return 12;
            default: return -1;
        }
    }
}

