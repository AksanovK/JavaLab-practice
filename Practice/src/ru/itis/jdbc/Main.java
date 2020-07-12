package ru.itis.jdbc;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/java_lab_practiceAksanov";
    private static final String USER = "postgres";
    private static final String PASSWORD = "11235813Akh";


    public static void main(String[] args) throws SQLException {
        SimpleDataSource dataSource = new SimpleDataSource();
        Connection connection = dataSource.openConnection(URL,USER,PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student");
        while (resultSet.next()) {
            System.out.println("==============================");
            System.out.println("ID - " + resultSet.getInt("id"));
            System.out.println("First Name - " + resultSet.getString("first_name"));
            System.out.println("Last Name - " + resultSet.getString("last_name"));
            System.out.println("Age - " + resultSet.getInt("age"));
            System.out.println("Group Number - " + resultSet.getInt("group_number"));
            System.out.println("==============================");
        }
        System.out.println("----------end------------");
        resultSet.close();
        resultSet = statement.executeQuery("select s.id as s_id, *\n" +
                "from student s\n" +
                "         full outer join mentor m on s.id = m." +
                "student_id;");

        while (resultSet.next()) {
            System.out.println("ID " + resultSet.getInt("s_id"));
        }


        statement.close();
        connection.close();
    }
}
