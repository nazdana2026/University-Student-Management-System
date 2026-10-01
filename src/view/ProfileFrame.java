package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.UserDAO;

public class ProfileFrame extends JFrame {

    private User currentUser;
    public ProfileFrame(User user) {

        this.currentUser = user;
        setTitle("My Profile");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        //TITLE
        JLabel titleLabel = new JLabel("My Profile");
        titleLabel.setBounds(270, 25, 200, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(titleLabel);
        // PROFILE CARD
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBounds(80, 90, 540, 260);
        profilePanel.setBackground(new Color(245, 247, 250));
        add(profilePanel);


        // User ID
        JLabel idLabel = new JLabel("User ID:");
        idLabel.setBounds(50, 35, 120, 25);
        idLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profilePanel.add(idLabel);

        JLabel idValue = new JLabel(String.valueOf(currentUser.getId()));
        idValue.setBounds(180, 35, 250, 25);
        profilePanel.add(idValue);


        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 85, 120, 25);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profilePanel.add(usernameLabel);

        JLabel usernameValue = new JLabel(currentUser.getUsername());
        usernameValue.setBounds(180, 85, 250, 25);
        profilePanel.add(usernameValue);


        // Role
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 135, 120, 25);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profilePanel.add(roleLabel);

        JLabel roleValue = new JLabel(currentUser.getRole());
        roleValue.setBounds(180, 135, 250, 25);
        profilePanel.add(roleValue);


        // Status
        JLabel statusLabel = new JLabel("Account Status:");
        statusLabel.setBounds(50, 185, 120, 25);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        profilePanel.add(statusLabel);

        JLabel statusValue = new JLabel("Active");
        statusValue.setBounds(180, 185, 250, 25);
        statusValue.setForeground(new Color(0, 130, 70));
        statusValue.setFont(new Font("Arial", Font.BOLD, 14));
        profilePanel.add(statusValue);


        //  BUTTONS

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(80, 380, 180, 35);
        add(changePasswordButton);


        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(420, 380, 180, 35);
        add(backButton);


        //  CHANGE PASSWORD

        changePasswordButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JPasswordField oldPasswordField = new JPasswordField();
                JPasswordField newPasswordField = new JPasswordField();
                JPasswordField confirmPasswordField = new JPasswordField();

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                panel.add(new JLabel("Old Password:"));
                panel.add(oldPasswordField);

                panel.add(Box.createVerticalStrut(10));

                panel.add(new JLabel("New Password:"));
                panel.add(newPasswordField);

                panel.add(Box.createVerticalStrut(10));

                panel.add(new JLabel("Confirm New Password:"));
                panel.add(confirmPasswordField);

                int result = JOptionPane.showConfirmDialog(
                        null,
                        panel,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result != JOptionPane.OK_OPTION) {
                    return;
                }

                String oldPassword =
                        new String(oldPasswordField.getPassword());

                String newPassword =
                        new String(newPasswordField.getPassword());

                String confirmPassword =
                        new String(confirmPasswordField.getPassword());


                // Check empty fields
                if (oldPassword.isEmpty()
                        || newPassword.isEmpty()
                        || confirmPassword.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Please fill all fields!"
                    );

                    return;
                }


                // Check new passwords
                if (!newPassword.equals(confirmPassword)) {

                    JOptionPane.showMessageDialog(
                            null,
                            "New passwords do not match!"
                    );

                    return;
                }


                // Check new password length
                if (newPassword.length() < 4) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Password must contain at least 4 characters!"
                    );

                    return;
                }


                UserDAO userDAO = new UserDAO();

                boolean changed = userDAO.changePassword(
                        currentUser.getId(),
                        oldPassword,
                        newPassword
                );


                if (changed) {

                    currentUser.setPassword(newPassword);

                    JOptionPane.showMessageDialog(
                            null,
                            "Password changed successfully!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Old password is incorrect!"
                    );
                }
            }
        });


        //  BACK

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new DashboardFrame(currentUser);
            }
        });


        setVisible(true);
    }
}