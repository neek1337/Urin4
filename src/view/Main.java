package view;

import models.Message;
import models.Users;
import models.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Supplier;

/**
 * Created by neek on 05.05.2016.
 */
public class Main {
    static Utils utils = new Utils();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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
                    Users user = null;
                    try {
                        user = utils.getUser(utils.getUserId(login));
                    } catch (Exception e) {
                        System.out.println("Неверный логин или пароль");
                        break;
                    }
                    if (user.getPassword().equals(password)) {
                        menu(user);
                    } else {
                        System.out.println("Неверный логин или пароль");
                    }
                    break;
                case 2:
                    System.out.println("Введите логин");
                    login = scanner.next();
                    System.out.println("Введите пароль");
                    password = scanner.next();
                    user = new Users();
                    user.setLogin(login);
                    user.setPassword(password);
                    utils.addUser(user);
                    break;
                default:
                    System.out.println("Введите 1 или 2");
            }
        }
    }

    public static void menu(Users user) {
        while (true) {
            System.out.println("Введите номер действия:\n" +
                    "0-выход \n" +
                    "1-читать входящие \n" +
                    "2-читать исходящие \n" +
                    "3-создать письмо"
            );
            int i = scanner.nextInt();
            switch (i) {
                case 0:
                    return;
                case 1:
                    int j = 0;
                    System.out.println("0 - Назад;");
                    j++;
                    ArrayList<Message> list = utils.getMessagesTo(user.getId());
                    for (Message message : list) {
                        System.out.println(j + " - " + message + ";");
                        j++;
                    }
                    int ans = scanner.nextInt();
                    if (ans > 0 && ans <= list.size()) {
                        Message message = list.get(ans - 1);
                        System.out.println(message.getText());
                        message.setChecked(1);
                        utils.updateMessage(message);
                    }
                    System.out.println("0 - Назад;");
                    ans = scanner.nextInt();
                    break;
                case 2:
                    j = 0;
                    System.out.println("0 - Назад;");
                    j++;
                    list = utils.getMessagesFrom(user.getId());
                    for (Message message : list) {
                        System.out.println(j + " - " + message + ";");
                        j++;
                    }
                    ans = scanner.nextInt();
                    if (ans > 0 && ans <= list.size()) {
                        Message message = list.get(ans - 1);
                        System.out.println(message.getText());
                    }
                    System.out.println("0 - Назад;");
                    ans = scanner.nextInt();
                    break;
                case 3:
                    System.out.printf("Выберите получателя");
                    ArrayList<Users> users = utils.getUsers();
                default:
                    System.out.println("Введите 1 - 3");
            }
        }
    }

    public static String md5Custom(String st) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
