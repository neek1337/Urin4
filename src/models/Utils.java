package models;

import main.HibernateUtil;
import org.hibernate.*;

import java.util.ArrayList;

/**
 * Created by neek on 05.05.2016.
 */
public class Utils {

    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public User getUser(int id) {
        User user = new User();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user.setUser((User) session.get(User.class, id));
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public Integer getUserId(String login) {
        Integer result = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            result = (Integer) session.createSQLQuery("SELECT id FROM user WHERE user.login = :name").setParameter("name", login).list().get(0);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> usersArrayList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            usersArrayList = (ArrayList<User>) session.createCriteria(User.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usersArrayList;
    }

    public ArrayList<Group> getGroups() {
        ArrayList<Group> usersArrayList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            usersArrayList = (ArrayList<Group>) session.createQuery("SELECT g FROM  Group g GROUP BY g.name").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usersArrayList;
    }

    public void addGroup(Group group) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(group);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<User> getGroup(String name1) {
        ArrayList<User> usersArrayList = new ArrayList<>();
        Session session = null;
        ArrayList<String> ids = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            ids = (ArrayList<String>) session.createSQLQuery("SELECT groupcol FROM test.group WHERE group.name = :name").setParameter("name", name1).list();

            for (String id : ids) {
                if (id != null) {
                    usersArrayList.add(getUser(getUserId(id)));
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usersArrayList;
    }

    public void addMessage(Message messageEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(messageEntity);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateMessage(Message messageEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(messageEntity);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public ArrayList<Message> getMessagesTo(int userId) {
        ArrayList<Message> messages = new ArrayList<>();
        Session session = null;


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Message where toId = :code ");
            query.setParameter("code", userId);

            messages = (ArrayList<Message>) query.list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return messages;
    }

    public ArrayList<Message> getMessagesFrom(int userId) {
        ArrayList<Message> messages = new ArrayList<>();
        Session session = null;


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Message where fromId = :code ");
            query.setParameter("code", userId);

            messages = (ArrayList<Message>) query.list();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return messages;
    }


}
