package view;

import dao.RankingDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RankingFrame extends JFrame {

    private User currentUser;

    private JTable rankingTable;
    private DefaultTableModel tableModel;

    private JButton backButton;


    public RankingFrame(User user) {

        this.currentUser = user;


        // FRAME

        setTitle("Student Ranking");

        setSize(1200, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(
                new Color(245, 247, 250)
        );




        JLabel titleLabel =
                new JLabel("Student Ranking");

        titleLabel.setBounds(
                450, 25, 300, 40
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        add(titleLabel);




        String[] columns = {

                "Rank",
                "Student ID",
                "Student Name",
                "Department",
                "Courses",
                "Average",
                "Percentage"
        };




        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };



        rankingTable =
                new JTable(tableModel);

        rankingTable.setRowHeight(32);

        rankingTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        rankingTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        rankingTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );

        rankingTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );



        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        for (int i = 0; i < rankingTable.getColumnCount(); i++) {

            rankingTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }



        rankingTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        rankingTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(100);

        rankingTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(220);

        rankingTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(190);

        rankingTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(90);

        rankingTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(120);

        rankingTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(130);




        JScrollPane scrollPane =
                new JScrollPane(rankingTable);

        scrollPane.setBounds(
                50, 90, 1100, 350
        );

        add(scrollPane);


        backButton =
                new JButton("Back");

        backButton.setBounds(
                550, 480, 100, 38
        );

        backButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        backButton.setFocusPainted(false);

        add(backButton);

        loadRanking();



        // BACK ACTION
        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame(
                    currentUser
            );
        });



        // SHOW FRAME

        setVisible(true);
    }


    // LOAD RANKING

    private void loadRanking() {

        tableModel.setRowCount(0);

        RankingDAO rankingDAO =
                new RankingDAO();

        List<Object[]> ranking =
                rankingDAO.getStudentRanking();

        for (Object[] row : ranking) {

            tableModel.addRow(row);
        }
    }
}