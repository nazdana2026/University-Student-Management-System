package Test;

import dao.StudentDAO;

public class TestStudent {

    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        studentDAO.deleteStudent(2);


    }
}