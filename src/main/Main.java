package main;

import models.Users;
import models.Utils;

import java.util.ArrayList;

class Main {
    public static void main(String[] args) {
        Utils utils = new Utils();
        ArrayList<Users> usersArrayList =  utils.getUsers();
        Users user = utils.getUser(1);
        utils.addUser(user);
    }


}
