package dao;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RankingDAO {
    public List<Object[]> getStudentRanking() {

        List<Object[]> ranking = new ArrayList<>();

        Connection conn = DBConnection.getConnection();

        String sql =
                "SELECT s.student_id, " +
                        "CONCAT(s.first_name, ' ', s.last_name) AS student_name, " +
                        "s.department, " +
                        "COUNT(g.grade_id) AS course_count, " +
                        "SUM(g.grade * c.credit) / SUM(c.credit) AS average " +
                        "FROM students s " +
                        "JOIN grades g ON s.student_id = g.student_id " +
                        "JOIN courses c ON g.course_id = c.course_id " +
                        "GROUP BY s.student_id, s.first_name, s.last_name, s.department " +
                        "ORDER BY average DESC";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int rank = 1;
            int position = 0;
            double previousAverage = -1;

            while (rs.next()) {

                position++;

                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                String department = rs.getString("department");
                int courseCount = rs.getInt("course_count");
                double average = rs.getDouble("average");

                // Students with the same percentage get the same rank
                if (average != previousAverage) {
                    rank = position;
                }

                String percentage = String.format("%.2f%%", average);

                ranking.add(new Object[]{
                        rank,
                        studentId,
                        studentName,
                        department,
                        courseCount,
                        average,
                        percentage
                });

                previousAverage = average;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ranking;
    }
}