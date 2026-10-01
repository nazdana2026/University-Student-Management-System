package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {


    // TOTAL STUDENTS
    public int getTotalStudents() {

        return getCount("SELECT COUNT(*) FROM students");
    }



    // TOTAL COURSES
    public int getTotalCourses() {

        return getCount("SELECT COUNT(*) FROM courses");
    }

    // TOTAL ENROLLMENTS
    public int getTotalEnrollments() {

        return getCount("SELECT COUNT(*) FROM enrollments");
    }

    // TOTAL GRADES
    public int getTotalGrades() {

        return getCount("SELECT COUNT(*) FROM grades");
    }

    // COUNT METHOD
    private int getCount(String sql) {

        Connection conn =
                DBConnection.getConnection();

        try {

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}