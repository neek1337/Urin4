package main;

import models.Group;
import models.User;
import models.Utils;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Utils utils = new Utils();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите логин");
        String login = scanner.next();
        System.out.println("Введите пароль");
        String password = scanner.next();
        if (utils.getUser(utils.getUserId(login)).getPassword().equals(password)) {
            User currentUser = utils.getUser(utils.getUserId(login));
            while (true) {
                System.out.println("1 - Написать письмо; \n" +
                        "2 - входящие; \n" +
                        "3 - исходящие;\n" +
                        "4 - выход");
                int ans = scanner.nextInt();
                switch (ans) {
                    case 1:
                        System.out.println("Выберите получателя:");
                        ArrayList<User> users = utils.getUsers();
                        ArrayList<Group> groups = utils.getGroups();
                        System.out.println("пользователи:");
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println(i + " - " + users.get(i).getLogin());
                        }
                        System.out.println("группы:");
                        for (int i = users.size(); i < users.size() + groups.size(); i++) {
                            System.out.println(i + " - " + groups.get(i - users.size()).getName());
                        }
                        int i = scanner.nextInt();
                        if (i < users.size()) {
                            System.out.println(users.get(i).getLogin());
                        } else if (i < users.size() + groups.size()) {
                            System.out.printf(groups.get(i - users.size()).getName());
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
                        return;
                    default:
                        System.out.println("Должно быть введено число от 1 до 4");
                }
            }
        } else {
            System.out.println("Неправильно введен логин или пароль");
        }
    }


}
