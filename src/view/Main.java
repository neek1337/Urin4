package view;

import models.Group;
import models.Message;
import models.User;
import models.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by neek on 05.05.2016.
 */
public class Main {
    static Utils utils = new Utils();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws NoSuchAlgorithmException {
        while (true) {
            System.out.println("Введите номер действия:\n" +
                    "1-вход \n" +
                    "2-регистрация");
            int i = scanner.nextInt();
            switch (i) {
                case 1:
                    System.out.println("Введите логин");
                    String login = scanner.next();
                    System.out.println("Введите пароль");
                    String password = scanner.next();
                    User user = null;
                    try {
                        user = utils.getUser(utils.getUserId(login));
                    } catch (Exception e) {
                        System.out.println("Неверный логин или пароль");
                        break;
                    }
                    if (user.getPassword().equals(password)) {
                        menu(user);
                        return;
                    } else {
                        System.out.println("Неверный логин или пароль");
                    }
                    break;
                case 2:
                    System.out.println("Введите логин");
                    login = scanner.next();
                    System.out.println("Введите пароль");
                    password = scanner.next();
                    user = new User();
                    user.setLogin(login);
                    user.setPassword(password);
                    utils.addUser(user);
                    break;
                default:
                    System.out.println("Введите 1 или 2");
            }
        }
    }

    public static void menu(User currentUser) throws NoSuchAlgorithmException {
        ArrayList<User> administrators = utils.getGroup("Admins");
        boolean isAdmin = false;
        if (administrators.contains(currentUser)) {
            isAdmin = true;
        }
        while (true) {
            System.out.println("Введите номер действия:\n" +
                    "0-выход \n" +
                    "1-читать входящие \n" +
                    "2-читать исходящие \n" +
                    "3-создать письмо"
            );
            if (isAdmin) {
                System.out.println("4-добавить пользвателя в группу");
                System.out.println("5-создать новую группу");
            }

            int nextInt = scanner.nextInt();
            switch (nextInt) {
                case 0:
                    return;
                case 1:
                    int j = 0;
                    System.out.println("0 - Назад;");
                    j++;
                    ArrayList<Message> list = utils.getMessagesTo(currentUser.getId());
                    for (Message message : list) {
                        System.out.println(j + " - " + message + ";");
                        j++;
                    }
                    int ans = scanner.nextInt();
                    if (ans > 0 && ans <= list.size()) {
                        System.out.println("Текст сообщения:");
                        Message message = list.get(ans - 1);
                        System.out.println(message.getText());
                        message.setChecked(1);
                        utils.updateMessage(message);
                    }
                    break;
                case 2:
                    j = 0;
                    System.out.println("0 - Назад;");
                    j++;
                    list = utils.getMessagesFrom(currentUser.getId());
                    for (Message message : list) {
                        System.out.println(j + " - " + message + ";");
                        j++;
                    }
                    ans = scanner.nextInt();
                    if (ans > 0 && ans <= list.size()) {
                        System.out.println("Текст сообщения:");
                        Message message = list.get(ans - 1);
                        System.out.println(message.getText());
                    }
                    break;
                case 3:
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
                    ArrayList<User> recipients = new ArrayList<>();
                    if (i < users.size()) {
                        recipients.add(users.get(i));
                    } else if (i < users.size() + groups.size()) {
                        recipients = utils.getGroup(groups.get(i - users.size()).getName());
                    } else {
                        System.out.println("Введено некорректное значение");
                        break;
                    }
                    scanner.nextLine();
                    System.out.println("Введите текст сообщения");
                    String text = scanner.nextLine();
                    System.out.println("Сообщение секретно? \n" +
                            "0 - нет: \n" +
                            "1 - да.");
                    int secret = scanner.nextInt();
                    if (secret != 1 && secret != 0) {
                        break;
                    }
                    for (User recipient : recipients) {
                        Message message = new Message();
                        message.setChecked(0);
                        message.setFromId(currentUser.getId());
                        message.setHash(md5(text));
                        message.setText(text);
                        message.setToId(recipient.getId());
                        message.setIsSecret(secret);
                        utils.addMessage(message);
                    }
                    System.out.println("Сообщение отправленно.");
                    break;
                case 4:
                    if (isAdmin) {
                        users = utils.getUsers();
                        groups = utils.getGroups();
                        System.out.println("Выберите пользователя:");
                        for (i = 0; i < users.size(); i++) {
                            System.out.println(i + " - " + users.get(i).getLogin());
                        }
                        i = scanner.nextInt();
                        if (!(i >= 0 && i < users.size())) {
                            System.out.println("Некорректные данные");
                            break;
                        }
                            User user = users.get(i);
                            System.out.println("Выберите группу, в которую надо добавить пользователя:");
                            for (i = 0; i < groups.size(); i++) {
                                System.out.println(i + " - " + groups.get(i).getName());
                            }
                            i = scanner.nextInt();
                        if (!(i >= 0 && i < groups.size())) {
                            System.out.println("Некорректные данные");
                            break;
                        }
                            Group group = new Group();
                            group.setGroupcol(user.getLogin());
                            group.setName(groups.get(i).getName());
                            utils.addGroup(group);
                            System.out.println("Пользователь добавлен в группу");
                        } else {
                            System.out.println("Введите 1 - 3");
                        }
                        break;
                        case 5:
                            if (isAdmin) {
                                System.out.println("Введите название группы");
                                Group group = new Group();
                                group.setName(scanner.next());
                                utils.addGroup(group);
                                System.out.println("Группа создана");
                            } else {
                                System.out.println("Введите 1 - 3");
                            }
                            break;
                        default:
                            if (isAdmin) {
                                System.out.println("Введите 1 - 5");
                            } else {
                                System.out.println("Введите 1 - 3");
                            }
                    }
            }
        }

    public static String md5(String st) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
