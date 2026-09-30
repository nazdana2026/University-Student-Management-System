package view;

import javax.swing.*;
import view.DashboardFrame;

import dao.UserDAO;
import model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 80, 25);
        add(usernameLabel);

        // Username TextField
        usernameField = new JTextField();
        usernameField.setBounds(140, 50, 180, 25);
        add(usernameField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 80, 25);
        add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 100, 180, 25);
        add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(140, 160, 100, 30);
        add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                User user = new User();

                user.setUsername(usernameField.getText());
                user.setPassword(new String(passwordField.getPassword()));

                UserDAO userDAO = new UserDAO();

                if (userDAO.login(user)) {

                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    dispose();

                    new DashboardFrame(user);

                } else {

                    JOptionPane.showMessageDialog(null, "Login Failed!");

                }
            }
        });

        setVisible(true);
    }
}