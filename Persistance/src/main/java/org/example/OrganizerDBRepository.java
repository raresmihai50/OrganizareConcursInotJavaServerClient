package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class OrganizerDBRepository implements OrganizerRepository<Organizer, Integer> {
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public OrganizerDBRepository(Properties props) {
        logger.info("Initializing OrganizerDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void addOrganizer(Organizer elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Organizer (id,username,password) values (?,?,?)")) {
            preStmt.setInt(1, elem.getId());
            preStmt.setString(2, elem.getUsername());
            preStmt.setString(3, elem.getPassword());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();

    }

    @Override
    public void deleteOrganizer(Organizer elem) {

    }

    @Override
    public void updateOrganizer(Organizer elem, Integer integer) {

    }

    @Override
    public Organizer findByIdOrganizer(Integer integer) {
        logger.traceEntry("finding organizer task with id {} ", integer);
        Connection con = dbUtils.getConnection();
        Organizer organizer = null;
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Organizer WHERE id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    organizer = new Organizer(integer, username, password);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(organizer);
        return organizer;

    }

    public Organizer findByUsernameOrganizer(String username) {
        logger.traceEntry("finding organizer task with username {} ", username);
        Connection con = dbUtils.getConnection();
        Organizer organizer = null;
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Organizer WHERE username=?")) {
            preStmt.setString(1, username);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String password = result.getString("password");
                    organizer = new Organizer(id, username, password);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(organizer);
        return organizer;

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

    @Override
    public List<Organizer> findAllOrganizer() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Organizer> orgs = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Organizer")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    Organizer org = new Organizer(id, username, password);
                    org.setId(id);
                    orgs.add(org);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(orgs);
        return orgs;
    }

}
