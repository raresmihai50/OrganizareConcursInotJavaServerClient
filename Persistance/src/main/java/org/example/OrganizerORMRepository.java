package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class OrganizerORMRepository implements OrganizerRepository<Organizer, Integer>{

    private JdbcUtils dbUtils;
    static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    public OrganizerORMRepository(){//Properties props) {
        logger.info("Initializing OrganizerORMRepository ");
        //dbUtils = new JdbcUtils(props);
    }
    @Override
    public void addOrganizer(Organizer elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void deleteOrganizer(Organizer elem) {

    }

    @Override
    public void updateOrganizer(Organizer elem, Integer integer) {

    }

    @Override
    public Organizer findByIdOrganizer(Integer id) {
        /*try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Organizer where id=:idM ", Organizer.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull();
        }*/
        try(Session session=sessionFactory.openSession()){
            return session.find(Organizer.class,id);
        }catch (Exception e){
            System.err.println("Eroare la findOrganizer " +e);
        }
        return null;
    }

    @Override
    public List<Organizer> findAllOrganizer() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Organizer ", Organizer.class).getResultList();
        }
    }
    public Organizer findByUsernameOrganizer(String username){
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Organizer where username=:usn ", Organizer.class)
                    .setParameter("usn", username)
                    .getSingleResultOrNull();
        }
        /*try(Session session=sessionFactory.openSession()){
            return session.find(Organizer.class,username);
        }catch (Exception e){
            System.err.println("Eroare la findOrganizer " +e);
        }
        return null;*/
    }
    public Organizer findByUsernameAndPasswordOrganizer(String username, String password) {
        Organizer org = findByUsernameOrganizer(username);
        if (org != null) {
            if (Objects.equals(password, org.getPassword())) {
                return org;
            }
        }
        return null;
    }
}
