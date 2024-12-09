package com.document.documentauth.Repository;

import com.document.documentauth.Domain.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository{

    private final SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    public void add(User user){

        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error with add user");

        }
    }
    public Optional<User> getUserByLogin(String login){

        try(Session session = factory.openSession()){

            session.beginTransaction();

            Query<User> query = session.createQuery("from User where login = :login", User.class);
            query.setParameter("login", login);

            User user = query.uniqueResult();
            session.getTransaction().commit();

            return Optional.ofNullable(user);
        } catch (Exception ex){
            System.out.println("Error with get user");
            return Optional.empty();
        }
    }
    public boolean existsByUsername(String login){

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where login = :login", User.class);
            query.setParameter("login", login);
            User user = query.uniqueResult();
            session.getTransaction().commit();

            return user == null;
        } catch (Exception ex) {
            System.out.println("Error method existsByUsername" + ex);
            return false;
        }
    }
}
