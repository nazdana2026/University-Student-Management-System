package Test;

import dao.UserDAO;
import model.User;

public class TestLogin {

    public static void main(String[] args) {

        User user = new User();

        user.setUsername("admin");
        user.setPassword("1234");

        UserDAO userDAO = new UserDAO();

        if (userDAO.login(user)) {
            System.out.println("Login Successful!");
        } else {
            System.out.println("Login Failed!");
        }
    }
}
