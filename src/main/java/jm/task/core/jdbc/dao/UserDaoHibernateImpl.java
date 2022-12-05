package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static final Connection connection = Util.getConnection();
    public UserDaoHibernateImpl() {

    }


    @Override
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

    @Override
    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS Users")){
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        User user = new User(name, lastName, age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
//        getSessionFactory().close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
//        session.createQuery("delete User where id = id").executeUpdate();
        User user = session.get(User.class, id);
        session.delete(user);
        session.getTransaction().commit();
//        getSessionFactory().close();
    }

    @Override
    public List<User> getAllUsers() {

        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> allUsers = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
//        getSessionFactory().close();
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
//        getSessionFactory().close();
    }
}
