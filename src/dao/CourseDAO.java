package dao;

import database.DBConnection;
import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void addCourse(Course course) {

        Connection conn = DBConnection.getConnection();

        String sql = "INSERT INTO courses(course_name, course_code, credit) VALUES(?, ?, ?)";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseCode());
            ps.setDouble(3, course.getCredit());

            ps.executeUpdate();

            System.out.println("Course Added Successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {

        List<Course> courses = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM courses";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Course course = new Course();

                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCredit(rs.getDouble("credit"));

                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public void deleteCourse(int id) {

        Connection conn = DBConnection.getConnection();

        String sql = "DELETE FROM courses WHERE course_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Course deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateCourse(Course course) {

        Connection conn = DBConnection.getConnection();

        String sql = "UPDATE courses SET course_name=?, course_code=?, credit=? WHERE course_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseCode());
            ps.setDouble(3, course.getCredit());
            ps.setInt(4, course.getId());

            ps.executeUpdate();

            System.out.println("Course updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Course> searchCourse(String keyword) {

        List<Course> courses = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM courses " +
                "WHERE course_name LIKE ? " +
                "OR course_code LIKE ?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            String search = "%" + keyword + "%";

            ps.setString(1, search);
            ps.setString(2, search);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Course course = new Course();

                course.setId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCredit(rs.getDouble("credit"));

                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }


}
