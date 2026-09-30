package view;

import dao.CourseDAO;
import model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import model.User;

public class CourseFrame extends JFrame {

    private JTextField courseNameField;
    private JTextField courseCodeField;
    private JTextField creditField;

    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;

    private JTable courseTable;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    private JTextField searchField;
    private JButton searchButton;

    private User currentUser;

    public CourseFrame(User user) {
        this.currentUser = user;

        setTitle("Course Management");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Title
        JLabel title = new JLabel("Course Management");
        title.setBounds(350, 20, 200, 30);
        add(title);

        // Course Name
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setBounds(50, 80, 120, 25);
        add(courseNameLabel);

        courseNameField = new JTextField();
        courseNameField.setBounds(180, 80, 220, 25);
        add(courseNameField);

        // Course Code
        JLabel courseCodeLabel = new JLabel("Course Code:");
        courseCodeLabel.setBounds(50, 130, 120, 25);
        add(courseCodeLabel);

        courseCodeField = new JTextField();
        courseCodeField.setBounds(180, 130, 220, 25);
        add(courseCodeField);

        // Credit
        JLabel creditLabel = new JLabel("Credit:");
        creditLabel.setBounds(50, 180, 120, 25);
        add(creditLabel);

        creditField = new JTextField();
        creditField.setBounds(180, 180, 220, 25);
        add(creditField);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.setBounds(120, 260, 120, 35);
        add(saveButton);

        // Save
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (courseNameField.getText().trim().isEmpty()
                        || courseCodeField.getText().trim().isEmpty()
                        || creditField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please fill all fields!"
                    );
                    return;
                }

                Course course = new Course();

                course.setCourseName(courseNameField.getText());
                course.setCourseCode(courseCodeField.getText());

                try {

                    String creditText = creditField.getText()
                            .trim()
                            .replace(",", ".");

                    double credit = Double.parseDouble(creditText);

                    course.setCredit(credit);

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Credit must be a number! Example: 3.5"
                    );

                    return;
                }

                CourseDAO courseDAO = new CourseDAO();
                courseDAO.addCourse(course);

                loadCourses();



                JOptionPane.showMessageDialog(
                        null,
                        "Course Added Successfully!"
                );

                clearFields();
            }
        });

        // Table Columns
        String[] columns = {
                "ID",
                "Course Name",
                "Course Code",
                "Credit"
        };

        model = new DefaultTableModel(columns, 0);

        courseTable = new JTable(model);

        // Table column widths
        courseTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        courseTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        courseTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        courseTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        courseTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        scrollPane = new JScrollPane(courseTable);
        scrollPane.setBounds(450, 80, 400, 300);
        add(scrollPane);

        // Load Courses
        loadCourses();

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(450, 40, 60, 25);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(510, 40, 180, 25);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(700, 40, 100, 25);
        add(searchButton);


        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(810, 40, 80, 25);
        add(clearButton);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                searchField.setText("");

                loadCourses();
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String keyword = searchField.getText().trim();

                if (keyword.isEmpty()) {
                    loadCourses();
                    return;
                }

                CourseDAO courseDAO = new CourseDAO();

                List<Course> courses =
                        courseDAO.searchCourse(keyword);

                model.setRowCount(0);

                for (Course course : courses) {

                    model.addRow(new Object[]{
                            course.getId(),
                            course.getCourseName(),
                            course.getCourseCode(),
                            course.getCredit()
                    });
                }
            }
        });

        // Select Course from Table
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int selectedRow = courseTable.getSelectedRow();

                if (selectedRow != -1) {

                    courseNameField.setText(
                            model.getValueAt(selectedRow, 1).toString()
                    );

                    courseCodeField.setText(
                            model.getValueAt(selectedRow, 2).toString()
                    );

                    creditField.setText(
                            model.getValueAt(selectedRow, 3).toString()
                    );
                }
            }
        });

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(450, 400, 120, 35);
        add(deleteButton);

        // Delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = courseTable.getSelectedRow();

                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a course!"
                    );

                    return;
                }

                int id = (int) model.getValueAt(selectedRow, 0);

                CourseDAO courseDAO = new CourseDAO();
                courseDAO.deleteCourse(id);

                loadCourses();
                clearFields();

                JOptionPane.showMessageDialog(
                        null,
                        "Course Deleted Successfully!"
                );
            }
        });

        // Update Button
        updateButton = new JButton("Update");
        updateButton.setBounds(590, 400, 120, 35);
        add(updateButton);

        // Update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = courseTable.getSelectedRow();

                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a course!"
                    );

                    return;
                }

                if (courseNameField.getText().trim().isEmpty()
                        || courseCodeField.getText().trim().isEmpty()
                        || creditField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please fill all fields!"
                    );

                    return;
                }

                Course course = new Course();

                course.setId(
                        (int) model.getValueAt(selectedRow, 0)
                );

                course.setCourseName(
                        courseNameField.getText()
                );

                course.setCourseCode(
                        courseCodeField.getText()
                );

                try {

                    String creditText = creditField.getText()
                            .trim()
                            .replace(",", ".");

                    double credit = Double.parseDouble(creditText);

                    course.setCredit(credit);

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Credit must be a number! Example: 3.5"
                    );

                    return;
                }

                CourseDAO courseDAO = new CourseDAO();

                courseDAO.updateCourse(course);

                loadCourses();

                JOptionPane.showMessageDialog(
                        null,
                        "Course Updated Successfully!"
                );

                clearFields();
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String keyword = searchField.getText().trim();

                if (keyword.isEmpty()) {
                    loadCourses();
                    return;
                }

                CourseDAO courseDAO = new CourseDAO();

                List<Course> courses =
                        courseDAO.searchCourse(keyword);

                model.setRowCount(0);

                for (Course course : courses) {

                    model.addRow(new Object[]{
                            course.getId(),
                            course.getCourseName(),
                            course.getCourseCode(),
                            course.getCredit()
                    });
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(730, 400, 120, 35);
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new DashboardFrame(currentUser);
            }
        });


        setVisible(true);
    }

    // Load Courses
    public void loadCourses() {

        model.setRowCount(0);

        CourseDAO courseDAO = new CourseDAO();

        List<Course> courses = courseDAO.getAllCourses();

        for (Course course : courses) {

            Object[] row = {
                    course.getId(),
                    course.getCourseName(),
                    course.getCourseCode(),
                    course.getCredit()
            };

            model.addRow(row);
        }
    }

    // Clear Fields
    private void clearFields() {

        courseNameField.setText("");
        courseCodeField.setText("");
        creditField.setText("");

        courseTable.clearSelection();
    }
}