package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        User Oleg = new User("Oleg", "Romanov", (byte) 23);
        User Dima =  new User("Dima", "Ivanov", (byte) 45);
        User Jalibek = new User("Jalibek", "Ju", (byte) 32);


    try (Connection connection = Util.getConnection()) {
        if (connection != null) System.out.println("Connection established");
        UserDaoJDBCImpl statement = new UserDaoJDBCImpl();
        statement.dropUsersTable();
        statement.createUsersTable();
        statement.saveUser(Oleg.getName(), Oleg.getLastName(), Oleg.getAge());
        statement.saveUser(Dima.getName(), Dima.getLastName(), Dima.getAge());
        statement.saveUser(Jalibek.getName(), Jalibek.getLastName(), Jalibek.getAge());
        statement.removeUserById(1);
        System.out.println(statement.getAllUsers().toString());
        //statement.cleanUsersTable();
        }
    }
}

