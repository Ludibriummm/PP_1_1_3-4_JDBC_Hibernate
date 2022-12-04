package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(PreparedStatement preparedStatement = connection.
                prepareStatement("CREATE TABLE IF NOT EXISTS Users(" +
                                        "id INT NOT NULL AUTO_INCREMENT, " +
                                        "name VARCHAR(20) NOT NULL, " +
                                        "lastName VARCHAR(20) NOT NULL , " +
                                        "age INT NOT NULL," +
                                        "PRIMARY KEY (id))")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Users")){
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, lastName, age) " +
                                                                                    "VALUES(? ,?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Не удалось добавить пользователя");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE from users where id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить пользователя");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Не удалось создать список всех пользователей");
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE from Users")){
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Не удалось очистить таблицу");
            e.printStackTrace();
        }
    }
}
