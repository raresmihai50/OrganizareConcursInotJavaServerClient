package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.JdbcUtils;
import org.example.domain.Organizer;
import org.example.domain.Trial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class TrialDBRepository implements TrialRepository<Trial,Integer>{
    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TrialDBRepository(Properties props) {
        logger.info("Initializing TrialDBRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public void addTrial(Trial elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Trial (id,type,details) values (?,?,?)")) {
            preStmt.setInt(1, elem.getId());
            preStmt.setString(2, elem.gettype());
            preStmt.setString(3, elem.getdetails());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void deleteTrial(Trial elem) {

    }

    @Override
    public void updateTrial(Trial elem, Integer integer) {

    }

    @Override
    public Trial findByIdTrial(Integer integer) {
        logger.traceEntry("finding task with id {} ", integer);
        Connection con = dbUtils.getConnection();
        Trial trial = null;
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Trial WHERE id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    String type = result.getString("type");
                    String details = result.getString("details");
                    trial = new Trial(integer, type, details);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(trial);
        return trial;
    }

    @Override
    public List<Trial> findAllTrial() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Trial> trials = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM Trial")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String type = result.getString("type");
                    String details = result.getString("details");
                    Trial trial = new Trial(id, type, details);
                    trials.add(trial);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(trials);
        return trials;
    }

}
