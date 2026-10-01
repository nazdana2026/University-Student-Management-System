package dao;

import database.DBConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Add Student
    public void addStudent(Student student) {

        Connection conn = DBConnection.getConnection();

        String sql = "INSERT INTO students(first_name, " +
                "last_name, student_number, department," +
                " email, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getStudentNumber());
            ps.setString(4, student.getDepartment());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getPhone());

            ps.executeUpdate();

            System.out.println("Student added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get All Students
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM students";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Student student = new Student();

                student.setId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setDepartment(rs.getString("department"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Update Student
    public void updateStudent(Student student) {

        Connection conn = DBConnection.getConnection();

        String sql = "UPDATE students SET first_name=?, last_name=?, student_number=?, department=?, email=?, phone=? WHERE student_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getStudentNumber());
            ps.setString(4, student.getDepartment());
            ps.setString(5, student.getEmail());
            ps.setString(6, student.getPhone());
            ps.setInt(7, student.getId());

            ps.executeUpdate();

            System.out.println("Student updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Delete Student
    public void deleteStudent(int id) {

        Connection conn = DBConnection.getConnection();

        String sql = "DELETE FROM students WHERE student_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Student deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Student> searchStudent(String keyword) {

        List<Student> students = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM students WHERE first_name LIKE ? OR last_name LIKE ? OR student_number LIKE ?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Student student = new Student();

                student.setId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setDepartment(rs.getString("department"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

}



