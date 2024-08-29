package za.ac.cput.timetableproject.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.timetableproject.dao.LectureDao;
import za.ac.cput.timetableproject.domain.Lecture;


public class LectureGui extends JPanel {

    private JButton add_New, change, delete;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane pane;
    private ArrayList<Lecture>list ;

    public LectureGui() {
        setLayout(new BorderLayout());

        // Initialize buttons
        add_New = new JButton("Add New");
        change = new JButton("Change");
        delete = new JButton("Delete");

        // Set button size
        Dimension buttonSize = new Dimension(100, 30);
        add_New.setPreferredSize(buttonSize);
        change.setPreferredSize(buttonSize);
        delete.setPreferredSize(buttonSize);

        // Initialize table and scroll pane
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        pane = new JScrollPane(table);
        setSize(450, 250);
        
        list = new ArrayList<>();

        // Set up the GUI layout
        setGui();
    }

    public void setGui() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3)); // GridLayout to align buttons horizontally

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add columns to table model
        tableModel.addColumn("LectureID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Initials");

        // Add buttons to panel
        panel.add(add_New);
        panel.add(change);
        panel.add(delete);

        // Add components to main panel
        mainPanel.add(pane, BorderLayout.CENTER);
        mainPanel.add(panel, BorderLayout.SOUTH);

        // Add main panel to the current panel
        this.add(mainPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        add_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add new lecture logic
               
                JPanel innerPanel = new JPanel(new GridLayout(4, 2));
                JTextField lectureCode = new JTextField();
                JTextField name = new JTextField();
                JTextField surname = new JTextField();
                JTextField intials = new JTextField();

                JLabel LectureID = new JLabel("LectureID");
                JLabel lectureName = new JLabel("Lecture Name");
                JLabel lecureSurname = new JLabel("Surname ");
                JLabel lectureIntials = new JLabel("Intials ");

                innerPanel.add(LectureID);
                innerPanel.add(lectureCode);
                innerPanel.add(lectureName);
                innerPanel.add(name);
                innerPanel.add(lecureSurname);
                innerPanel.add(surname);
                innerPanel.add(lectureIntials);
                innerPanel.add(intials);
                
                int result = JOptionPane.showConfirmDialog(null, innerPanel,
                        "Enter Lecture Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String idStr = lectureCode.getText();

                        if (!idStr.matches("\\d+")) {
                            JOptionPane.showMessageDialog(null, "LectureID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int idd = Integer.parseInt(idStr);
                        String Lname = name.getText();
                        String Sname = surname.getText();
                        String Linitials = intials.getText();

                        if (Lname.length() < 3) {
                            JOptionPane.showMessageDialog(null, "Lecture Name must be at least 4 letters long.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (Sname.length() < 3  ) {
                            JOptionPane.showMessageDialog(null, "Lecture Surname must be at least 4 letters long.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!Lname.matches("[a-zA-Z]+")) {
                            JOptionPane.showMessageDialog(null, "Lecture Name must contain only letters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (!Sname.matches("[a-zA-Z]+")) {
                            JOptionPane.showMessageDialog(null, "Lecture Surname must contain only letters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (Linitials.length() != 2 || !Linitials.equalsIgnoreCase("" + Lname.charAt(0) + Sname.charAt(0))) {
                            JOptionPane.showMessageDialog(null, "Initials must be the first letters of Name and Surname.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        LectureDao dao = new LectureDao();

                        if (dao.isLectureIdExists(idd)) {
                            JOptionPane.showMessageDialog(null, "LectureID already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Lecture newLecture = new Lecture(idd, Lname, Sname, Linitials);
                        dao.save(newLecture);

                        tableModel.addRow(new Object[]{idd, Lname, Sname, Linitials});
                         list = dao.readLecture();

//                        for (Lecture dd : list) {
//
//                            tableModel.addRow(new Object[]{dd.getLectureID(), dd.getLectureName(), dd.getLectureSurname(), dd.getLectureIntials()});
//                        }
                         
                        JOptionPane.showMessageDialog(null, "Lecture successfully added");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "LectureID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception k) {
                        JOptionPane.showMessageDialog(null, "Error occurred: " + k.getMessage());
                    }
                }
            }
            
        });

        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Change lecture logic
                 int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    String oldName = table.getValueAt(selectedRow, 0).toString();
                    String oldSurname = table.getValueAt(selectedRow, 1).toString();

                    JPanel innerPanel = new JPanel(new GridLayout(4, 2));
                    JTextField newName = new JTextField();
                    JTextField newSurname = new JTextField();
                    JTextField newInitials = new JTextField();

                    JLabel labelNewName = new JLabel("New Lecture Name:");
                    JLabel labelNewSurname = new JLabel("New Surname:");
                    JLabel labelNewInitials = new JLabel("New Initials:");

                    innerPanel.add(labelNewName);
                    innerPanel.add(newName);
                    innerPanel.add(labelNewSurname);
                    innerPanel.add(newSurname);
                    innerPanel.add(labelNewInitials);
                    innerPanel.add(newInitials);

                    int result = JOptionPane.showConfirmDialog(null, innerPanel, "Update Lecture Details", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            String updatedName = newName.getText().trim();
                            String updatedSurname = newSurname.getText().trim();
                            String updatedInitials = newInitials.getText().trim();

                            if (!updatedName.matches("[a-zA-Z]+")) {
                                JOptionPane.showMessageDialog(null, "Lecture Name must contain only letters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (!updatedSurname.matches("[a-zA-Z]+")) {
                                JOptionPane.showMessageDialog(null, "Surname must contain only letters.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (updatedInitials.length() != 2 || !updatedInitials.equalsIgnoreCase("" + updatedName.charAt(0) + updatedSurname.charAt(0))) {
                                JOptionPane.showMessageDialog(null, "Initials must be the first letters of Name and Surname.", "Input Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            LectureDao dao = new LectureDao();
                            dao.udpateLectureTable(oldName, oldSurname, updatedName, updatedSurname, updatedInitials);

                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                            tableModel.setValueAt(updatedName, selectedRow, 0);
                            tableModel.setValueAt(updatedSurname, selectedRow, 1);
                            tableModel.setValueAt(updatedInitials, selectedRow, 2);

                            JOptionPane.showMessageDialog(null, "Lecture updated successfully!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                        }
                    }
                }
            }
            
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Delete lecture logic
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int lectureId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this lecture?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            LectureDao dao = new LectureDao();
                            dao.delete(lectureId);

                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                            tableModel.removeRow(selectedRow);

                            JOptionPane.showMessageDialog(null, "Lecture deleted successfully!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error occurred: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a lecture to delete.");
                }
            
            }
        });
    }
}
