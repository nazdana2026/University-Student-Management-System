package dao;

import database.DBConnection;
import model.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    // Add enrollment
    public void addEnrollment(Enrollment enrollment) {
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES(?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourseId());
            ps.executeUpdate();
            System.out.println("Enrollment added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {

        List<Enrollment> enrollments = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM enrollments";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(
                        rs.getInt("enrollment_id")
                );
                enrollment.setStudentId(
                        rs.getInt("student_id")
                );
                enrollment.setCourseId(
                        rs.getInt("course_id")
                );
                enrollments.add(enrollment);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return enrollments;
    }



    public List<Object[]> getEnrollmentDetails() {

        List<Object[]> list = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = """
            SELECT 
                e.enrollment_id,
                s.student_id,
                CONCAT(s.first_name, ' ', s.last_name) AS student_name,
                c.course_id,
                c.course_name,
                c.course_code
            FROM enrollments e
            INNER JOIN students s
                ON e.student_id = s.student_id
            INNER JOIN courses c
                ON e.course_id = c.course_id
            """;

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("course_code")
                };

                list.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Object[]> getEnrollmentDetailsByStudent(int studentId) {

        List<Object[]> enrollments = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql =
                "SELECT e.enrollment_id, " +
                        "s.student_id, " +
                        "CONCAT(s.first_name, ' ', s.last_name) AS student_name, " +
                        "c.course_id, " +
                        "c.course_name, " +
                        "c.course_code " +
                        "FROM enrollments e " +
                        "JOIN students s ON e.student_id = s.student_id " +
                        "JOIN courses c ON e.course_id = c.course_id " +
                        "WHERE e.student_id = ?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("course_code")
                };

                enrollments.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrollments;
    }


    // Delete enrollment
    public void deleteEnrollment(int id) {

        Connection conn = DBConnection.getConnection();

        String sql =
                "DELETE FROM enrollments WHERE enrollment_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Enrollment deleted successfully!");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
