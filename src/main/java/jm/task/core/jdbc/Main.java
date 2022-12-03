package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.saveUser("Nic", "Gal", (byte)17);//проблемы с генерацией id(?)
    }
}
