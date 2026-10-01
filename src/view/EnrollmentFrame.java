package view;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import model.Course;
import model.Enrollment;
import model.Student;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EnrollmentFrame extends JFrame {

    private JComboBox<String> studentComboBox;
    private JComboBox<String> courseComboBox;

    private DefaultTableModel model;
    private JTable enrollmentTable;

    private List<Student> students;
    private List<Course> courses;

    private User currentUser;

    public EnrollmentFrame(User user) {

        this.currentUser = user;

        setTitle("Enrollment Management");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // TITLE

        JLabel titleLabel = new JLabel("Enrollment Management");
        titleLabel.setBounds(330, 20, 300, 30);
        add(titleLabel);

        //  STUDENT
        JLabel studentLabel = new JLabel("Student:");
        studentLabel.setBounds(50, 80, 100, 25);
        add(studentLabel);

        studentComboBox = new JComboBox<>();
        studentComboBox.setBounds(150, 80, 250, 30);
        add(studentComboBox);


        studentComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (students != null &&
                        studentComboBox.getSelectedIndex() != -1) {

                    loadEnrollments();
                }
            }
        });

        // COURSE

        JLabel courseLabel = new JLabel("Course:");
        courseLabel.setBounds(50, 140, 100, 25);
        add(courseLabel);

        courseComboBox = new JComboBox<>();
        courseComboBox.setBounds(150, 140, 250, 30);
        add(courseComboBox);

        // ENROLL BUTTON

        JButton enrollButton = new JButton("Enroll");
        enrollButton.setBounds(150, 200, 120, 35);
        add(enrollButton);

        // TABLE
        String[] columns = {
                "Enrollment ID",
                "Student ID",
                "Student Name",
                "Course ID",
                "Course Name",
                "Course Code"
        };

        model = new DefaultTableModel(columns, 0);

        enrollmentTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(enrollmentTable);
        scrollPane.setBounds(430, 80, 700, 300);
        add(scrollPane);

        // Table width
        enrollmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        enrollmentTable.getColumnModel().getColumn(0).setPreferredWidth(110);
        enrollmentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        enrollmentTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        enrollmentTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        enrollmentTable.getColumnModel().getColumn(4).setPreferredWidth(180);
        enrollmentTable.getColumnModel().getColumn(5).setPreferredWidth(130);

        // DELETE BUTTON

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(430, 410, 120, 35);
        add(deleteButton);

        // BACK BUTTON

        JButton backButton = new JButton("Back");
        backButton.setBounds(570, 410, 120, 35);
        add(backButton);

        //  LOAD DATA

        loadStudents();
        loadCourses();
        loadEnrollments();

        // ENROLL ACTION

        enrollButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int studentIndex =
                        studentComboBox.getSelectedIndex();

                int courseIndex =
                        courseComboBox.getSelectedIndex();

                if (studentIndex == -1 || courseIndex == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select student and course!"
                    );

                    return;
                }

                Student selectedStudent =
                        students.get(studentIndex);

                Course selectedCourse =
                        courses.get(courseIndex);

                Enrollment enrollment = new Enrollment();

                enrollment.setStudentId(
                        selectedStudent.getId()
                );

                enrollment.setCourseId(
                        selectedCourse.getId()
                );

                EnrollmentDAO enrollmentDAO =
                        new EnrollmentDAO();

                enrollmentDAO.addEnrollment(enrollment);

                loadEnrollments();

                JOptionPane.showMessageDialog(
                        null,
                        "Course enrolled successfully!"
                );
            }
        });

        //  DELETE ACTION
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow =
                        enrollmentTable.getSelectedRow();

                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please select an enrollment!"
                    );

                    return;
                }

                int id =
                        (int) model.getValueAt(
                                selectedRow,
                                0
                        );

                EnrollmentDAO enrollmentDAO =
                        new EnrollmentDAO();

                enrollmentDAO.deleteEnrollment(id);

                loadEnrollments();

                JOptionPane.showMessageDialog(
                        null,
                        "Enrollment deleted successfully!"
                );
            }
        });

        //  BACK ACTION
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new DashboardFrame(currentUser);
            }
        });

        setVisible(true);
    }

    //LOAD STUDENTS

    private void loadStudents() {

        StudentDAO studentDAO = new StudentDAO();

        students = studentDAO.getAllStudents();

        studentComboBox.removeAllItems();

        for (Student student : students) {

            String studentInfo =
                    student.getId()
                            + " - "
                            + student.getFirstName()
                            + " "
                            + student.getLastName();

            studentComboBox.addItem(studentInfo);
        }
    }

    //  LOAD COURSES
    private void loadCourses() {

        CourseDAO courseDAO = new CourseDAO();

        courses = courseDAO.getAllCourses();

        courseComboBox.removeAllItems();

        for (Course course : courses) {

            String courseInfo =
                    course.getId()
                            + " - "
                            + course.getCourseName()
                            + " ("
                            + course.getCourseCode()
                            + ")";

            courseComboBox.addItem(courseInfo);
        }
    }

    // LOAD ENROLLMENTS
    private void loadEnrollments() {

        model.setRowCount(0);

        int studentIndex =
                studentComboBox.getSelectedIndex();

        if (studentIndex == -1) {
            return;
        }

        Student selectedStudent =
                students.get(studentIndex);

        EnrollmentDAO enrollmentDAO =
                new EnrollmentDAO();

        List<Object[]> enrollments =
                enrollmentDAO.getEnrollmentDetailsByStudent(
                        selectedStudent.getId()
                );

        for (Object[] row : enrollments) {

            model.addRow(row);
        }
    }
}