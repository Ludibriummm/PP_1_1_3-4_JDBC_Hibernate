package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.cleanUsersTable();
        userService.saveUser("Nic", "Gal", (byte)17);
        userService.saveUser("Nic", "Gal", (byte)17);
        System.out.println(userService.getAllUsers());
//        userService.saveUser("Nic", "Gal", (byte)17);
    }
}
