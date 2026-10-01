package view;

import dao.GradeDAO;
import database.DBConnection;
import model.Grade;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeFrame extends JFrame {

    private User currentUser;

    private JComboBox<String> studentComboBox;
    private JComboBox<String> courseComboBox;

    private JTextField gradeField;
    private JTextField percentageField;
    private JTextField letterGradeField;
    private JTextField gradePointField;
    private JTextField statusField;

    private JTextField averageField;
    private JTextField gpaField;
    private JTextField totalCreditsField;

    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;

    private JTable gradeTable;
    private DefaultTableModel tableModel;

    private GradeDAO gradeDAO;

    private List<Integer> studentIds = new ArrayList<>();
    private List<Integer> courseIds = new ArrayList<>();

    private int selectedGradeId = -1;


    // CONSTRUCTOR

    public GradeFrame(User user) {

        currentUser = user;
        gradeDAO = new GradeDAO();

        setTitle("Grade Management");
        setSize(1350, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);



        // TITLE

        JLabel titleLabel =
                new JLabel("Grade Management");

        titleLabel.setBounds(500, 20, 300, 35);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        add(titleLabel);



        // STUDENT

        JLabel studentLabel =
                new JLabel("Student:");

        studentLabel.setBounds(
                40, 80, 100, 25
        );

        add(studentLabel);


        studentComboBox =
                new JComboBox<>();

        studentComboBox.setBounds(
                140, 80, 270, 30
        );

        add(studentComboBox);



        // COURSE
        JLabel courseLabel =
                new JLabel("Course:");

        courseLabel.setBounds(
                40, 125, 100, 25
        );

        add(courseLabel);


        courseComboBox =
                new JComboBox<>();

        courseComboBox.setBounds(
                140, 125, 270, 30
        );

        add(courseComboBox);


        // GRADE
        JLabel gradeLabel =
                new JLabel("Grade / 100:");

        gradeLabel.setBounds(
                40, 170, 100, 25
        );

        add(gradeLabel);


        gradeField =
                new JTextField();

        gradeField.setBounds(
                140, 170, 270, 30
        );

        add(gradeField);


        // PERCENTAGE
        JLabel percentageLabel =
                new JLabel("Percentage:");

        percentageLabel.setBounds(
                40, 215, 100, 25
        );

        add(percentageLabel);


        percentageField =
                new JTextField();

        percentageField.setBounds(
                140, 215, 270, 30
        );

        percentageField.setEditable(false);

        add(percentageField);


        // LETTER GRADE
        JLabel letterLabel =
                new JLabel("Letter:");

        letterLabel.setBounds(
                40, 260, 100, 25
        );

        add(letterLabel);


        letterGradeField =
                new JTextField();

        letterGradeField.setBounds(
                140, 260, 270, 30
        );

        letterGradeField.setEditable(false);

        add(letterGradeField);



        // GRADE POINT
        JLabel pointLabel =
                new JLabel("Grade Point:");

        pointLabel.setBounds(
                40, 305, 100, 25
        );

        add(pointLabel);


        gradePointField =
                new JTextField();

        gradePointField.setBounds(
                140, 305, 270, 30
        );

        gradePointField.setEditable(false);

        add(gradePointField);



        // STATUS

        JLabel statusLabel =
                new JLabel("Status:");

        statusLabel.setBounds(
                40, 350, 100, 25
        );

        add(statusLabel);


        statusField =
                new JTextField();

        statusField.setBounds(
                140, 350, 270, 30
        );

        statusField.setEditable(false);

        add(statusField);


        // OVERALL PERCENTAGE
        JLabel averageLabel =
                new JLabel("Overall %:");

        averageLabel.setBounds(
                40, 395, 100, 25
        );

        add(averageLabel);


        averageField =
                new JTextField();

        averageField.setBounds(
                140, 395, 270, 30
        );

        averageField.setEditable(false);

        add(averageField);



        // GPA
        JLabel gpaLabel =
                new JLabel("GPA:");

        gpaLabel.setBounds(
                40, 440, 100, 25
        );

        add(gpaLabel);


        gpaField =
                new JTextField();

        gpaField.setBounds(
                140, 440, 270, 30
        );

        gpaField.setEditable(false);

        add(gpaField);



        // TOTAL CREDITS
        JLabel creditLabel =
                new JLabel("Total Credits:");

        creditLabel.setBounds(
                40, 485, 100, 25
        );

        add(creditLabel);


        totalCreditsField =
                new JTextField();

        totalCreditsField.setBounds(
                140, 485, 270, 30
        );

        totalCreditsField.setEditable(false);

        add(totalCreditsField);


        // BUTTONS
        saveButton =
                new JButton("Save");

        saveButton.setBounds(
                40, 540, 90, 35
        );

        add(saveButton);


        updateButton =
                new JButton("Update");

        updateButton.setBounds(
                140, 540, 90, 35
        );

        add(updateButton);


        deleteButton =
                new JButton("Delete");

        deleteButton.setBounds(
                240, 540, 90, 35
        );

        add(deleteButton);


        backButton =
                new JButton("Back");

        backButton.setBounds(
                340, 540, 90, 35
        );

        add(backButton);



        // TABLE
        String[] columns = {

                "Grade ID",
                "Student ID",
                "Student Name",
                "Course ID",
                "Course Name",
                "Course Code",
                "Credit",
                "Grade",
                "Letter",
                "Grade Point",
                "Percentage",
                "Status"
        };


        tableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };


        gradeTable =
                new JTable(tableModel);

        gradeTable.setRowHeight(30);


        gradeTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );


        gradeTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );


        // Column widths

        gradeTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        gradeTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(80);

        gradeTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(160);

        gradeTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(80);

        gradeTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(150);

        gradeTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(110);

        gradeTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(70);

        gradeTable.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(70);

        gradeTable.getColumnModel()
                .getColumn(8)
                .setPreferredWidth(70);

        gradeTable.getColumnModel()
                .getColumn(9)
                .setPreferredWidth(100);

        gradeTable.getColumnModel()
                .getColumn(10)
                .setPreferredWidth(100);

        gradeTable.getColumnModel()
                .getColumn(11)
                .setPreferredWidth(130);


        JScrollPane scrollPane =
                new JScrollPane(gradeTable);

        scrollPane.setBounds(
                450, 70, 850, 510
        );

        add(scrollPane);


        // LOAD STUDENTS
        loadStudents();


        if (studentComboBox.getItemCount() > 0) {

            studentComboBox.setSelectedIndex(0);
        }



        // STUDENT CHANGE
        studentComboBox.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        int index =
                                studentComboBox
                                        .getSelectedIndex();


                        if (index >= 0) {

                            int studentId =
                                    studentIds.get(index);


                            loadCourses(studentId);

                            loadGrades();

                            calculateOverall();
                        }
                    }
                }
        );


        // GRADE CALCULATION
        gradeField.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        calculateGrade();
                    }
                }
        );


        // SAVE


        saveButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        saveGrade();
                    }
                }
        );

        // UPDATE

        updateButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        updateGrade();
                    }
                }
        );


        // DELETE
        deleteButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        deleteGrade();
                    }
                }
        );


        // TABLE CLICK

        gradeTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        int row =
                                gradeTable.getSelectedRow();


                        if (row >= 0) {

                            selectedGradeId =
                                    Integer.parseInt(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            0
                                                    )
                                                    .toString()
                                    );


                            int studentId =
                                    Integer.parseInt(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            1
                                                    )
                                                    .toString()
                                    );


                            int courseId =
                                    Integer.parseInt(
                                            tableModel
                                                    .getValueAt(
                                                            row,
                                                            3
                                                    )
                                                    .toString()
                                    );


                            String grade =
                                    tableModel
                                            .getValueAt(
                                                    row,
                                                    7
                                            )
                                            .toString();


                            selectStudent(studentId);

                            loadCourses(studentId);

                            selectCourse(courseId);

                            gradeField.setText(grade);

                            calculateGrade();
                        }
                    }
                }
        );


        // BACK

        backButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(
                            ActionEvent e) {

                        dispose();

                        new DashboardFrame(
                                currentUser
                        );
                    }
                }
        );


        setVisible(true);
    }



    // LOAD STUDENTS

    private void loadStudents() {

        studentComboBox.removeAllItems();

        studentIds.clear();

        Connection conn =
                DBConnection.getConnection();


        String sql =
                "SELECT student_id, first_name, last_name " +
                        "FROM students " +
                        "ORDER BY first_name";


        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {

                int id =
                        rs.getInt("student_id");


                String name =
                        rs.getString("first_name")
                                + " "
                                + rs.getString("last_name");


                studentIds.add(id);


                studentComboBox.addItem(
                        id + " - " + name
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }



    // LOAD COURSES
    private void loadCourses(int studentId) {

        courseComboBox.removeAllItems();

        courseIds.clear();


        List<Object[]> courses =
                gradeDAO.getEnrolledCourses(
                        studentId
                );


        for (Object[] course : courses) {

            int id =
                    (int) course[0];

            String name =
                    (String) course[1];

            String code =
                    (String) course[2];


            courseIds.add(id);


            courseComboBox.addItem(
                    id
                            + " - "
                            + name
                            + " ("
                            + code
                            + ")"
            );
        }
    }


    // LOAD GRADES

    private void loadGrades() {

        tableModel.setRowCount(0);


        int index =
                studentComboBox.getSelectedIndex();


        if (index == -1) {

            return;
        }


        int studentId =
                studentIds.get(index);


        List<Object[]> grades =
                gradeDAO.getGradesByStudent(
                        studentId
                );


        for (Object[] row : grades) {

            tableModel.addRow(row);
        }
    }


    // CALCULATE CURRENT GRADE
    private void calculateGrade() {

        try {

            double grade =
                    Double.parseDouble(
                            gradeField.getText()
                    );


            if (grade < 0 || grade > 100) {

                percentageField.setText("");

                letterGradeField.setText("");

                gradePointField.setText("");

                statusField.setText(
                        "Invalid Grade"
                );

                return;
            }


            double gradePoint =
                    gradeDAO.getGradePoint(
                            grade
                    );


            String letter =
                    gradeDAO.getLetterGrade(
                            grade
                    );


            String status =
                    gradeDAO.getStatus(
                            grade
                    );


            percentageField.setText(
                    String.format(
                            "%.2f%%",
                            grade
                    )
            );


            letterGradeField.setText(
                    letter
            );


            gradePointField.setText(
                    String.format(
                            "%.2f",
                            gradePoint
                    )
            );


            statusField.setText(
                    status
            );

        } catch (NumberFormatException e) {

            percentageField.setText("");

            letterGradeField.setText("");

            gradePointField.setText("");

            statusField.setText("");
        }
    }


    // SAVE

    private void saveGrade() {

        if (studentComboBox.getSelectedIndex() == -1
                || courseComboBox.getSelectedIndex() == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a student and course."
            );

            return;
        }


        try {

            double grade =
                    Double.parseDouble(
                            gradeField.getText()
                    );


            if (grade < 0 || grade > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Grade must be between 0 and 100."
                );

                return;
            }


            int studentId =
                    studentIds.get(
                            studentComboBox
                                    .getSelectedIndex()
                    );


            int courseId =
                    courseIds.get(
                            courseComboBox
                                    .getSelectedIndex()
                    );


            if (gradeDAO.gradeExists(
                    studentId,
                    courseId)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This student already has a grade for this course."
                );

                return;
            }


            Grade newGrade =
                    new Grade();


            newGrade.setStudentId(
                    studentId
            );


            newGrade.setCourseId(
                    courseId
            );


            newGrade.setGrade(
                    grade
            );


            gradeDAO.addGrade(
                    newGrade
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Grade added successfully!"
            );


            loadGrades();

            calculateOverall();

            clearFields();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid grade."
            );
        }
    }


    // UPDATE

    private void updateGrade() {

        if (selectedGradeId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a grade from the table."
            );

            return;
        }


        try {

            double grade =
                    Double.parseDouble(
                            gradeField.getText()
                    );


            if (grade < 0 || grade > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Grade must be between 0 and 100."
                );

                return;
            }


            int studentId =
                    studentIds.get(
                            studentComboBox
                                    .getSelectedIndex()
                    );


            int courseId =
                    courseIds.get(
                            courseComboBox
                                    .getSelectedIndex()
                    );


            Grade updatedGrade =
                    new Grade();


            updatedGrade.setId(
                    selectedGradeId
            );


            updatedGrade.setStudentId(
                    studentId
            );


            updatedGrade.setCourseId(
                    courseId
            );


            updatedGrade.setGrade(
                    grade
            );


            gradeDAO.updateGrade(
                    updatedGrade
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Grade updated successfully!"
            );


            loadGrades();

            calculateOverall();

            clearFields();

            selectedGradeId = -1;

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid grade."
            );
        }
    }


    // DELETE

    private void deleteGrade() {

        if (selectedGradeId == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a grade from the table."
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this grade?",
                        "Delete Grade",
                        JOptionPane.YES_NO_OPTION
                );


        if (result ==
                JOptionPane.YES_OPTION) {

            gradeDAO.deleteGrade(
                    selectedGradeId
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Grade deleted successfully!"
            );


            loadGrades();

            calculateOverall();

            clearFields();

            selectedGradeId = -1;
        }
    }


    // CALCULATE OVERALL

    private void calculateOverall() {

        int index =
                studentComboBox.getSelectedIndex();


        if (index == -1) {

            averageField.setText("");

            gpaField.setText("");

            totalCreditsField.setText("");

            return;
        }


        int studentId =
                studentIds.get(index);


        double percentage =
                gradeDAO.calculateOverallPercentage(
                        studentId
                );


        double gpa =
                gradeDAO.calculateGPA(
                        studentId
                );


        double credits =
                gradeDAO.calculateTotalCredits(
                        studentId
                );


        List<Object[]> grades =
                gradeDAO.getGradesByStudent(
                        studentId
                );


        if (grades.isEmpty()) {

            averageField.setText(
                    "No grades"
            );

            gpaField.setText(
                    "No grades"
            );

            totalCreditsField.setText(
                    "0"
            );

            return;
        }


        averageField.setText(
                String.format(
                        "%.2f%%",
                        percentage
                )
        );


        gpaField.setText(
                String.format(
                        "%.2f / 4.00",
                        gpa
                )
        );


        totalCreditsField.setText(
                String.format(
                        "%.1f",
                        credits
                )
        );
    }


    // SELECT STUDENT

    private void selectStudent(int id) {

        for (int i = 0;
             i < studentIds.size();
             i++) {

            if (studentIds.get(i) == id) {

                studentComboBox
                        .setSelectedIndex(i);

                return;
            }
        }
    }

    // SELECT COURSE

    private void selectCourse(int id) {

        for (int i = 0;
             i < courseIds.size();
             i++) {

            if (courseIds.get(i) == id) {

                courseComboBox
                        .setSelectedIndex(i);

                return;
            }
        }
    }


    // CLEAR
    private void clearFields() {

        gradeField.setText("");

        percentageField.setText("");

        letterGradeField.setText("");

        gradePointField.setText("");

        statusField.setText("");

        selectedGradeId = -1;
    }
}