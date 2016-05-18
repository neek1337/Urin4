package main;

import models.Group;
import models.Message;
import models.User;
import models.Utils;

import java.util.ArrayList;

/**
 * Created by neek on 05.05.2016.
 */
public class Test {
    public static void main(String[] args) {
        Utils utils = new Utils();
//        Message message = new Message();
//        message.setId(7);
//        message.setChecked(1);
//        message.setFromId(1);
//        message.setToId(1);
//        message.setHash("***");
//        message.setText("Hello");
//        utils.updateMessage(message);
        ArrayList<Group> groups = utils.getGroups();
        for (Group group : groups) {
            System.out.println(group.getName());
        }
    }
}
