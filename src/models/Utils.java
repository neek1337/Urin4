package models;

import main.HibernateUtil;
import org.hibernate.*;

import java.util.ArrayList;

/**
 * Created by neek on 05.05.2016.
 */
public class Utils {

    public void addUser(Users user) {
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

    public Users getUser(int id) {
        Users user = new Users();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user.setUser((Users) session.get(Users.class, id));
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
            result = (Integer) session.createSQLQuery("SELECT id FROM users WHERE users.login = :name").setParameter("name", login).list().get(0);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public ArrayList<Users> getUsers() {
        ArrayList<Users> usersArrayList = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            usersArrayList = (ArrayList<Users>) session.createCriteria(Users.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usersArrayList;
    }

    public void addGroup(Groups group) {
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

    public ArrayList<Users> getGroup(String name1) {
        ArrayList<Users> usersArrayList = new ArrayList<>();
        Session session = null;
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();

            ids = (ArrayList<Integer>) session.createSQLQuery("SELECT user_id FROM groups WHERE groups.name = :name").setParameter("name", name1).list();

            for (Integer id : ids) {
                usersArrayList.add(getUser(id));
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
