package ru.itis;

import ru.itis.jdbc.SimpleDataSource;
import ru.itis.models.Student;
import ru.itis.repositories.StudentRepositoryJdbcImpl;
import ru.itis.repositories.StudentsRepository;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/java_lab_practiceAksanov";
    private static final String USER = "postgres";
    private static final String PASSWORD = "11235813Akh";


    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        StudentsRepository studentsRepository = new StudentRepositoryJdbcImpl(connection);
        System.out.println("======================testByAge====================================");
        System.out.println(studentsRepository.findAllByAge(24));
        System.out.println("======================testUpdate===================================");
        Student student = new Student(6L, "Григорий", "Зиновьев", 56, 901);
        studentsRepository.update(student);
        System.out.println("======================testSave=====================================");
        Student student2 = new Student(null, "Булат", "Вафин", 19,901);
        studentsRepository.save(student2);;
        System.out.println("id check - " + student2.getId());
        System.out.println("======================testFindAll==================================");
        System.out.println(studentsRepository.findAll());
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
