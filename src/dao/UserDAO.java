package dao;
import database.DBConnection;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    // ================= LOGIN =================
    public boolean login(User user) {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM users WHERE TRIM(username)=? AND TRIM(password)=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                // Get user information from database
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return true;
            } else {
                System.out.println("Login Failed!");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }


    // ================= CHANGE PASSWORD =================

    public boolean changePassword(int userId, String oldPassword, String newPassword) {

        Connection conn = DBConnection.getConnection();

        // First check old password
        String checkSql = "SELECT * FROM users WHERE id=? AND password=?";

        try {

            PreparedStatement checkPs = conn.prepareStatement(checkSql);

            checkPs.setInt(1, userId);
            checkPs.setString(2, oldPassword);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {

                return false;
            }


            // Update password
            String updateSql = "UPDATE users SET password=? WHERE id=?";

            PreparedStatement updatePs = conn.prepareStatement(updateSql);

            updatePs.setString(1, newPassword);
            updatePs.setInt(2, userId);

            int result = updatePs.executeUpdate();

            return result > 0;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}