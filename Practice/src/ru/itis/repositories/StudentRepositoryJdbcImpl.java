package ru.itis.repositories;

import ru.itis.models.Mentor;
import ru.itis.models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class StudentRepositoryJdbcImpl implements StudentsRepository {

    //:Language = SQL
    private static final String SQL_SELECT_BY_ID = "select * from student where id = ";
    private static final String SQL_SELECT_BY_AGE = "select * from student where age = ";

    private Connection connection;

    public StudentRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> findAllByAge(int age) {
        List<Student> resultList = new LinkedList<>();
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_BY_AGE + age);
            while (result.next()) {
                Student student = new Student(
                        result.getLong("id"),
                        result.getString("first_name").trim(),
                        result.getString("last_name").trim(),
                        result.getInt("age"),
                        result.getInt("group_number")
                );
                resultList.add(student);
            }
            return resultList;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    @Override
    public List<Student> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        LinkedList<Student> student_list = new LinkedList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from student left join mentor m on student.id = m.student_id");
            while (resultSet.next()) {
                Student student = new Student (
                        resultSet.getLong("id"),
                        resultSet.getString("first_name").trim(),
                        resultSet.getString("last_name").trim(),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number")
                );
                if (student.getMentors() == null) {
                    student.setMentors(new LinkedList<>());
                }
                if (resultSet.getString("m_first_name") != null) {
                    Mentor mentor = new Mentor(
                            resultSet.getLong("m_id"),
                            resultSet.getString("m_first_name").trim(),
                            resultSet.getString("m_last_name").trim()
                    );
                    if (!student_list.contains(student)) {
                        List<Mentor> mentor_list = student.getMentors();
                        mentor_list.add(mentor);
                        student.setMentors(mentor_list);
                        student_list.add(student);
                    } else for (Student s : student_list) {
                        if (s.getId().equals(student.getId())) {
                            student.setMentors(s.getMentors());
                            student_list.remove(s);
                            List<Mentor> mentor_list = student.getMentors();
                            mentor_list.add(mentor);
                            student.setMentors(mentor_list);
                            student_list.add(student);
                        }
                    }
                }
                else student_list.add(student);
            }
            return student_list;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    public Student findById(long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_BY_ID + id);
            if (resultSet.next()) {  //выставляем итератор в начальное положение (на первую строчку результата ответ)
                return new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name").trim(),
                        resultSet.getString("last_name").trim(),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    //Language = SQL
    private static final String INSERT = "insert into student(first_name, last_name, age, group_number)";

    @Override
    public void save(Student entity) {
        Statement statement = null;
        Statement statement1 = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(INSERT + " values (" + "'" + entity.getFirstName() + "'" + ", " + "'"+ entity.getLastName() + "'" +
                    ", " + entity.getAge() +
                    ", " + entity.getGroupNumber() + "); ");
            statement1 = connection.createStatement();
            result = statement1.executeQuery("select max(id) as A from student");
            if (result.next()) {
                long l = result.getLong("A");
                entity.setId(l);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    private static final String UpDATE_F = "update student set first_name = ";
    private static final String UPDATE_L = ", last_name = ";
    private static final String UPDATE_AGE = ", age = ";
    private static final String UPDATE_GR = ", group_number = ";
    private static final String ID = " where id = ";

    @Override
    public void update(Student entity) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(UpDATE_F + "'" +entity.getFirstName() + "'" + UPDATE_L + "'" + entity.getLastName() + "'" + UPDATE_AGE +
                    entity.getAge() + UPDATE_GR + entity.getGroupNumber() + ID + entity.getId());
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }
}
