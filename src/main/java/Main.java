/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author Luã
 */
package com.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Estudante");
            System.out.println("2. Atualizar Estudante");
            System.out.println("3. Deletar Estudante");
            System.out.println("4. Listar todos os Estudante");
            System.out.println("5. Sair");
            System.out.println("Escolha uma opção: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 ->
                    addUser(userDao, scanner);
                case 2 -> {
                    listUsers(userDao);
                    updateUser(userDao, scanner);
                }
                case 3 ->
                    deleteUser(userDao, scanner);
                case 4 ->
                    listUsers(userDao);
                case 5 ->
                    System.out.println("Saindo ...");
                default ->
                    System.out.println("Opção inválida.");

            }

        } while (choice != 5);
    }

    private static void addUser(UserDao userDao, Scanner scanner) {
        System.out.println("Nome: ");
        String name = scanner.nextLine();
        
        System.out.println("Email: ");
        String email = scanner.nextLine();

        User newUser = new User(name, email);
        
        try {
            userDao.insertUser(newUser);
            System.out.println("Estudante inserido com sucesso!");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private static void updateUser(UserDao userDao, Scanner scanner) {
        System.out.println("ID do Estudante para atualizar: ");
        int id = scanner.nextInt();
        
        scanner.nextLine();

        User existingUser = userDao.selectUser(id);
        
        if (existingUser == null) {
            System.out.println("Estudante não encontrado.");
            return;
        }

        System.out.println("Novo nome: ");
        String name = scanner.nextLine();
        
        System.out.println("Novo email: ");
        String email = scanner.nextLine();

        existingUser.setName(name);
        existingUser.setEmail(email);
        
        try {
            userDao.updateUser(existingUser);
            System.out.println("Estudante atualizado com sucesso!");
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private static void deleteUser(UserDao userDao, Scanner scanner) {
        System.out.println("ID do estudante para deletar: ");
        int id = scanner.nextInt();
        
        scanner.nextLine();

        try {
            if (userDao.deleteUser(id)) {
                System.out.println("Estudante deletado com sucesso!");
            } else {
                System.out.println("Estudante não encontrado");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    private static void listUsers(UserDao userDao) {
        List<User> users = userDao.selectAllUsers();
        for (User user : users) {
            System.out.println(user.getId() + " - " + user.getName() + " - " + user.getEmail());
        }
    }
}
