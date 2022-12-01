package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(PreparedStatement preparedStatement = getConnection().
                prepareStatement("CREATE TABLE Users (id INT not null auto_increment primary key, " +
                        "name VARCHAR(20), " +
                        "lastName VARCHAR(20), " +
                        "age INT)")) {
            dropUsersTable();
            preparedStatement.execute();
            System.out.println("Создана таблица Users.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("DROP TABLE IF EXISTS Users")){
            preparedStatement.execute();
            System.out.println("Таблица Users удалена.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO users VALUES(?, ? ,?, ?)")){
            User user = new User(name, lastName, age);

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.execute();
            System.out.println("В таблицу Users добавлен " + name + ".");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE from users where id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            System.out.println("Из таблицы удален User с id: " + id + ".");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                allUsers.add(user);
            }
            System.out.println("В таблице находятся:\n" + allUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE from Users")){
            preparedStatement.execute();
            System.out.println("Таблица Users очищена.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
