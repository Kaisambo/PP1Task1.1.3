package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String CreateUsersTableSQL = """
                CREATE TABLE users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(50),
                lastname VARCHAR(50),
                age INTEGER
                )
                """;
        try (Connection connection = Util.getConnection() ;
             Statement statement = connection.createStatement()) {
             statement.execute(CreateUsersTableSQL);
        } catch (SQLException e) {
            System.out.println("Table already exists");
        }
    }

    public void dropUsersTable() {
        String dropUsersTableSQL = """
            DROP TABLE users
            """;
        try (Connection connection = Util.getConnection() ;
             Statement statement = connection.createStatement()) {
             statement.execute(dropUsersTableSQL);
        } catch (SQLException e) {
            System.out.println("Table does not exist");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSQL = """
           INSERT INTO users (name, lastname, age)
           Values
           ( ?, ?, ?)
           """;

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveUserSQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в БД");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserSQL = """
           DELETE FROM users WHERE id = ?
           """;

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(removeUserSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("User deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String getAllUserSQL = """
           SELECT id, name, lastname, age FROM users
           """;

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery(getAllUserSQL)) {
            while (resSet.next()) {
                String name = resSet.getString(2);
                String lastName = resSet.getString(3);
                byte age = resSet.getByte(4);
                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String removeUserSQL = """
           DELETE FROM users
           """;

        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(removeUserSQL)) {
            statement.executeUpdate();
            System.out.println("Table cleared");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
