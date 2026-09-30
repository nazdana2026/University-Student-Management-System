package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import dao.DashboardDAO;

public class DashboardFrame extends JFrame {

    private User currentUser;
    private JPanel sidebar;
    private JPanel mainPanel;

    private DashboardDAO dashboardDAO;

    public DashboardFrame(User user) {

        this.currentUser = user;

        this.dashboardDAO = new DashboardDAO();

        setTitle("School Automation - Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ================= SIDEBAR =================

        sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBounds(0, 0, 220, 600);
        sidebar.setBackground(new Color(45, 55, 72));
        add(sidebar);

        JLabel logoLabel = new JLabel("SCHOOL");
        logoLabel.setBounds(55, 30, 120, 35);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 22));
        sidebar.add(logoLabel);

        JLabel systemLabel = new JLabel("AUTOMATION");
        systemLabel.setBounds(55, 60, 120, 25);
        systemLabel.setForeground(Color.LIGHT_GRAY);
        systemLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        sidebar.add(systemLabel);


        // ================= MENU BUTTONS =================

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setBounds(20, 110, 180, 40);
        sidebar.add(dashboardButton);


        JButton studentButton = new JButton("Student Management");
        studentButton.setBounds(20, 165, 180, 40);
        sidebar.add(studentButton);


        JButton courseButton = new JButton("Course Management");
        courseButton.setBounds(20, 220, 180, 40);
        sidebar.add(courseButton);


        JButton gradeButton = new JButton("Grade Management");
        gradeButton.setBounds(20, 275, 180, 40);
        sidebar.add(gradeButton);


        JButton profileButton = new JButton("My Profile");
        profileButton.setBounds(20, 330, 180, 40);
        sidebar.add(profileButton);


        JButton enrollmentButton = new JButton("Enrollment");
        enrollmentButton.setBounds(20, 385, 180, 40);
        sidebar.add(enrollmentButton);


        JButton rankingButton = new JButton("Student Ranking");
        rankingButton.setBounds(20, 440, 180, 40);
        sidebar.add(rankingButton);


        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(20, 500, 180, 40);
        sidebar.add(logoutButton);


        // ================= MAIN PANEL =================

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(220, 0, 780, 600);
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);


        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setBounds(40, 30, 300, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        mainPanel.add(titleLabel);


        JLabel welcomeLabel =
                new JLabel("Welcome to School Automation System");

        welcomeLabel.setBounds(40, 75, 400, 30);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(welcomeLabel);


        // ================= STUDENT CARD =================

        JPanel studentCard = new JPanel();
        studentCard.setLayout(null);
        studentCard.setBounds(40, 140, 200, 110);
        studentCard.setBackground(new Color(230, 240, 255));
        mainPanel.add(studentCard);

        JLabel studentTitle = new JLabel("Students");
        studentTitle.setBounds(20, 15, 150, 25);
        studentTitle.setFont(new Font("Arial", Font.BOLD, 16));
        studentCard.add(studentTitle);

        int totalStudents = dashboardDAO.getTotalStudents();

        JLabel studentText =
                new JLabel("Total: " + totalStudents);

        studentText.setBounds(20, 50, 150, 25);
        studentText.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        studentCard.add(studentText);


        // ================= COURSE CARD =================

        JPanel courseCard = new JPanel();
        courseCard.setLayout(null);
        courseCard.setBounds(270, 140, 200, 110);
        courseCard.setBackground(new Color(235, 250, 235));
        mainPanel.add(courseCard);

        JLabel courseTitle = new JLabel("Courses");
        courseTitle.setBounds(20, 15, 150, 25);
        courseTitle.setFont(new Font("Arial", Font.BOLD, 16));
        courseCard.add(courseTitle);

        int totalCourses =
                dashboardDAO.getTotalCourses();

        JLabel courseText =
                new JLabel("Total: " + totalCourses);

        courseText.setBounds(20, 50, 150, 25);
        courseText.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        courseCard.add(courseText);


        // ================= PROFILE CARD =================

        JPanel profileCard = new JPanel();
        profileCard.setLayout(null);
        profileCard.setBounds(500, 140, 200, 110);
        profileCard.setBackground(new Color(250, 240, 230));
        mainPanel.add(profileCard);

        JLabel profileTitle = new JLabel("Profile");
        profileTitle.setBounds(20, 15, 150, 25);
        profileTitle.setFont(new Font("Arial", Font.BOLD, 16));
        profileCard.add(profileTitle);

        JLabel profileText = new JLabel("My Account");
        profileText.setBounds(20, 50, 150, 25);
        profileCard.add(profileText);


        // ================= RECENT ACTIVITY =================

        JLabel activityTitle = new JLabel("Recent Activity");
        activityTitle.setBounds(40, 300, 250, 30);
        activityTitle.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(activityTitle);


        JLabel activity1 =
                new JLabel("• Student Management is available");

        activity1.setBounds(40, 340, 400, 25);
        mainPanel.add(activity1);


        JLabel activity2 =
                new JLabel("• Course Management is available");

        activity2.setBounds(40, 370, 400, 25);
        mainPanel.add(activity2);


        JLabel activity3 =
                new JLabel("• Enrollment and Grade Management are available");

        activity3.setBounds(40, 400, 450, 25);
        mainPanel.add(activity3);


        JLabel activity4 =
                new JLabel("• Student Ranking is available");

        activity4.setBounds(40, 430, 400, 25);
        mainPanel.add(activity4);


        // ================= BUTTON ACTIONS =================

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(
                        null,
                        "You are already on Dashboard."
                );
            }
        });


        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new StudentFrame(currentUser);
                dispose();
            }
        });


        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new CourseFrame(currentUser);
                dispose();
            }
        });


        gradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new GradeFrame(currentUser);
                dispose();
            }
        });


        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new ProfileFrame(currentUser);
                dispose();
            }
        });


        enrollmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new EnrollmentFrame(currentUser);
                dispose();
            }
        });


        rankingButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new RankingFrame(currentUser);
                dispose();
            }
        });


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {

                    dispose();

                    new LoginFrame();
                }
            }
        });


        setVisible(true);
    }
}