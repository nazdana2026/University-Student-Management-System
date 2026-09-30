package dao;

import database.DBConnection;
import model.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
    // =========================================================
    // ADD GRADE
    // =========================================================
    public void addGrade(Grade grade) {
        Connection conn = DBConnection.getConnection();
        String sql =
                "INSERT INTO grades(student_id, course_id, grade) " +
                        "VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, grade.getStudentId());
            ps.setInt(2, grade.getCourseId());
            ps.setDouble(3, grade.getGrade());
            ps.executeUpdate();
            System.out.println("Grade added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // =========================================================
    // CHECK GRADE EXISTS
    // =========================================================

    public boolean gradeExists(int studentId, int courseId) {
        Connection conn = DBConnection.getConnection();
        String sql =
                "SELECT * FROM grades " +
                        "WHERE student_id=? AND course_id=?";
        try {
            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return false;
    }


    // =========================================================
    // GET ALL GRADES
    // =========================================================

    public List<Grade> getAllGrades() {

        List<Grade> grades = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM grades";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Grade grade = new Grade();

                grade.setId(
                        rs.getInt("grade_id")
                );

                grade.setStudentId(
                        rs.getInt("student_id")
                );

                grade.setCourseId(
                        rs.getInt("course_id")
                );

                grade.setGrade(
                        rs.getDouble("grade")
                );

                grades.add(grade);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return grades;
    }


    // =========================================================
    // GET ENROLLED COURSES
    // =========================================================

    public List<Object[]> getEnrolledCourses(int studentId) {

        List<Object[]> courses = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql =
                "SELECT c.course_id, c.course_name, " +
                        "c.course_code, c.credit " +
                        "FROM enrollments e " +
                        "JOIN courses c ON e.course_id = c.course_id " +
                        "WHERE e.student_id = ? " +
                        "ORDER BY c.course_name";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Object[] row = {

                        rs.getInt("course_id"),

                        rs.getString("course_name"),

                        rs.getString("course_code"),

                        rs.getDouble("credit")
                };

                courses.add(row);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return courses;
    }


    // =========================================================
    // UPDATE GRADE
    // =========================================================

    public void updateGrade(Grade grade) {

        Connection conn = DBConnection.getConnection();

        String sql =
                "UPDATE grades SET " +
                        "student_id=?, course_id=?, grade=? " +
                        "WHERE grade_id=?";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, grade.getStudentId());
            ps.setInt(2, grade.getCourseId());
            ps.setDouble(3, grade.getGrade());
            ps.setInt(4, grade.getId());

            ps.executeUpdate();

            System.out.println(
                    "Grade updated successfully!"
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // =========================================================
    // DELETE GRADE
    // =========================================================
    public void deleteGrade(int id) {
        Connection conn = DBConnection.getConnection();
        String sql =
                "DELETE FROM grades WHERE grade_id=?";
        try {
            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println(
                    "Grade deleted successfully!"
            );

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // =========================================================
    // GET GRADES BY STUDENT
    // =========================================================

    public List<Object[]> getGradesByStudent(int studentId) {

        List<Object[]> grades = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql =
                "SELECT g.grade_id, " +
                        "g.student_id, " +
                        "CONCAT(s.first_name, ' ', s.last_name) " +
                        "AS student_name, " +
                        "g.course_id, " +
                        "c.course_name, " +
                        "c.course_code, " +
                        "c.credit, " +
                        "g.grade " +
                        "FROM grades g " +
                        "JOIN students s " +
                        "ON g.student_id = s.student_id " +
                        "JOIN courses c " +
                        "ON g.course_id = c.course_id " +
                        "WHERE g.student_id = ? " +
                        "ORDER BY g.grade_id";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double grade =
                        rs.getDouble("grade");

                double credit =
                        rs.getDouble("credit");

                String letterGrade =
                        getLetterGrade(grade);

                double gradePoint =
                        getGradePoint(grade);

                String status =
                        getStatus(grade);


                Object[] row = {

                        rs.getInt("grade_id"),

                        rs.getInt("student_id"),

                        rs.getString("student_name"),

                        rs.getInt("course_id"),

                        rs.getString("course_name"),

                        rs.getString("course_code"),

                        credit,

                        grade,

                        letterGrade,

                        gradePoint,

                        String.format(
                                "%.2f%%",
                                grade
                        ),

                        status
                };

                grades.add(row);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return grades;
    }


    // =========================================================
    // PAU LETTER GRADE
    // =========================================================

    public String getLetterGrade(double grade) {
        if (grade >= 95) {
            return "A1";
        }
        if (grade >= 90) {
            return "A2";
        }
        if (grade >= 85) {
            return "A3";
        }
        if (grade >= 80) {
            return "B1";
        }
        if (grade >= 75) {
            return "B2";
        }
        if (grade >= 70) {
            return "B3";
        }
        if (grade >= 65) {
            return "C1";
        }
        if (grade >= 60) {
            return "C2";
        }
        if (grade >= 55) {
            return "D1";
        }
        if (grade >= 50) {
            return "D2";
        }
        return "F1";
    }


    // =========================================================
    // PAU GRADE POINT
    // =========================================================

    public double getGradePoint(double grade) {
        if (grade >= 95) {
            return 4.00;
        }
        if (grade >= 90) {
            return 3.75;
        }
        if (grade >= 85) {
            return 3.50;
        }
        if (grade >= 80) {
            return 3.25;
        }
        if (grade >= 75) {
            return 3.00;
        }
        if (grade >= 70) {
            return 2.75;
        }
        if (grade >= 65) {
            return 2.50;
        }
        if (grade >= 60) {
            return 2.25;
        }
        if (grade >= 55) {
            return 2.00;
        }
        if (grade >= 50) {
            return 1.75;
        }

        return 0.00;
    }


    // =========================================================
    // STATUS
    // =========================================================

    public String getStatus(double grade) {

        if (grade >= 60) {
            return "Pass";
        }

        if (grade >= 50) {
            return "Conditional Pass";
        }

        return "Fail";
    }


    // =========================================================
    // CALCULATE GPA
    // =========================================================

    public double calculateGPA(int studentId) {

        Connection conn =
                DBConnection.getConnection();

        String sql =
                "SELECT g.grade, c.credit " +
                        "FROM grades g " +
                        "JOIN courses c " +
                        "ON g.course_id = c.course_id " +
                        "WHERE g.student_id = ?";

        double totalQualityPoints = 0;
        double totalCredits = 0;

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                double grade =
                        rs.getDouble("grade");

                double credit =
                        rs.getDouble("credit");

                double gradePoint =
                        getGradePoint(grade);

                totalQualityPoints +=
                        gradePoint * credit;

                totalCredits += credit;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        if (totalCredits == 0) {
            return 0;
        }

        return totalQualityPoints / totalCredits;
    }


    // =========================================================
    // CALCULATE OVERALL PERCENTAGE
    // =========================================================

    public double calculateOverallPercentage(int studentId) {

        Connection conn =
                DBConnection.getConnection();

        String sql =
                "SELECT g.grade " +
                        "FROM grades g " +
                        "WHERE g.student_id = ?";

        double total = 0;
        int count = 0;

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                total +=
                        rs.getDouble("grade");

                count++;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        if (count == 0) {
            return 0;
        }

        return total / count;
    }


    // =========================================================
    // TOTAL CREDITS
    // =========================================================

    public double calculateTotalCredits(int studentId) {

        Connection conn =
                DBConnection.getConnection();

        String sql =
                "SELECT SUM(c.credit) AS total_credit " +
                        "FROM grades g " +
                        "JOIN courses c " +
                        "ON g.course_id = c.course_id " +
                        "WHERE g.student_id = ?";

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getDouble("total_credit");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }
}