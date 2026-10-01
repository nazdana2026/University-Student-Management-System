package view;

import javax.swing.*;

import dao.StudentDAO;
import model.Student;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Font;
import model.User;


public class StudentFrame extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentNumberField;
    private JTextField departmentField;
    private JTextField emailField;
    private JTextField phoneField;
    private JButton saveButton;
    private JTable studentTable;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JButton deleteButton;
    private JButton updateButton;
    private User currentUser;

    public StudentFrame(User user) {
        this.currentUser = user;

        setTitle("Student Management");
        setSize(1600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Student Management");
        title.setBounds(250, 20, 200, 30);
        add(title);


// First Name
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 80, 100, 25);
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(180, 80, 220, 25);
        add(firstNameField);

// Last Name
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 120, 100, 25);
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(180, 120, 220, 25);
        add(lastNameField);

        // Student Number
        JLabel studentNumberLabel = new JLabel("Student Number:");
        studentNumberLabel.setBounds(50, 160, 120, 25);
        add(studentNumberLabel);

        studentNumberField = new JTextField();
        studentNumberField.setBounds(180, 160, 220, 25);
        add(studentNumberField);

// Department
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 200, 120, 25);
        add(departmentLabel);

        departmentField = new JTextField();
        departmentField.setBounds(180, 200, 220, 25);
        add(departmentField);

// Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 240, 120, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(180, 240, 220, 25);
        add(emailField);

// Phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 280, 120, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(180, 280, 220, 25);
        add(phoneField);

        saveButton = new JButton("Save");
        saveButton.setBounds(120, 550, 120, 35);
        add(saveButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(280, 550, 120, 35);
        add(deleteButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(440, 550, 120, 35);
        add(updateButton);


        JButton backButton = new JButton("Back");
        backButton.setBounds(600, 550, 120, 35);
        add(backButton);

        saveButton.setFocusPainted(false);
        updateButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);

        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new DashboardFrame(currentUser);
            }
        });



        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student student = new Student();

                student.setFirstName(firstNameField.getText());
                student.setLastName(lastNameField.getText());
                student.setStudentNumber(studentNumberField.getText());
                student.setDepartment(departmentField.getText());
                student.setEmail(emailField.getText());
                student.setPhone(phoneField.getText());

                StudentDAO studentDAO = new StudentDAO();
                studentDAO.addStudent(student);

                JOptionPane.showMessageDialog(null, "Student Added Successfully!");
            }
        });

        String[] columns = {
                "ID",
                "First Name",
                "Last Name",
                "Student Number",
                "Department",
                "Email",
                "Phone"
        };

        model = new DefaultTableModel(columns, 0);

        studentTable = new JTable(model);

        studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        studentTable.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(100);  // First Name
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // Last Name
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(120);  // Student Number
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(150);  // Department
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(180);  // Email
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(120);  // Phone

        scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(500, 50, 550, 450);

        add(scrollPane);


        studentTable.setRowHeight(20);
        studentTable.getTableHeader().setReorderingAllowed(false);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(500, 20, 60, 25);
        add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(560, 20, 180, 25);
        add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(760, 20, 100, 25);
        add(searchButton);


        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int row = studentTable.getSelectedRow();

                firstNameField.setText(model.getValueAt(row, 1).toString());
                lastNameField.setText(model.getValueAt(row, 2).toString());
                studentNumberField.setText(model.getValueAt(row, 3).toString());
                departmentField.setText(model.getValueAt(row, 4).toString());
                emailField.setText(model.getValueAt(row, 5).toString());
                phoneField.setText(model.getValueAt(row, 6).toString());
            }
        });
        loadStudents();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = studentTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student!");
                    return;
                }

                int id = (int) model.getValueAt(selectedRow, 0);

                StudentDAO studentDAO = new StudentDAO();
                studentDAO.deleteStudent(id);

                loadStudents();

                JOptionPane.showMessageDialog(null, "Student Deleted Successfully!");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = studentTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a student!");
                    return;
                }

                Student student = new Student();

                student.setId((int) model.getValueAt(selectedRow, 0));
                student.setFirstName(firstNameField.getText());
                student.setLastName(lastNameField.getText());
                student.setStudentNumber(studentNumberField.getText());
                student.setDepartment(departmentField.getText());
                student.setEmail(emailField.getText());
                student.setPhone(phoneField.getText());

                StudentDAO studentDAO = new StudentDAO();
                studentDAO.updateStudent(student);

                loadStudents();

                JOptionPane.showMessageDialog(null, "Student Updated Successfully!");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                StudentDAO studentDAO = new StudentDAO();

                List<Student> students = studentDAO.searchStudent(searchField.getText());

                model.setRowCount(0);

                for (Student student : students) {

                    model.addRow(new Object[]{
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.getStudentNumber(),
                            student.getDepartment(),
                            student.getEmail(),
                            student.getPhone()
                    });
                }
            }
        });



        setVisible(true);
    }

    public void loadStudents() {

        model.setRowCount(0);

        StudentDAO studentDAO = new StudentDAO();

        List<Student> students = studentDAO.getAllStudents();

        for (Student student : students) {

            Object[] row = {
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getStudentNumber(),
                    student.getDepartment(),
                    student.getEmail(),
                    student.getPhone()
            };

            model.addRow(row);
        }
    }
}