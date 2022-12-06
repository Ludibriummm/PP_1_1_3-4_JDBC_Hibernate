package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte)34);
        userService.saveUser("Petr", "Ivanov", (byte)43);
        userService.saveUser("Kirill", "Sherbakov", (byte)31);
        userService.saveUser("Sherbak", "Kirillov", (byte)13);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
