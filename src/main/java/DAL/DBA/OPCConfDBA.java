/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.DBA;

import BE.OPCConf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import DAL.DatabaseAccessInterface;
import java.sql.Statement;

/**
 *
 * @author Developer
 */
public class OPCConfDBA implements DatabaseAccessInterface<OPCConf> {

    private final DataSource ds;

    public OPCConfDBA(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public List<OPCConf> getAll() {
        List<OPCConf> configs = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OPCDATA");
        ) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OPCConf conf = new OPCConf();
                conf.setId(rs.getInt("ID"));
                conf.setName(rs.getString("NAME"));
                conf.setUrl(rs.getString("URL"));
                conf.setAnonymous(rs.getBoolean("ANON"));
                conf.setUserName(rs.getString("USERNAME"));
                conf.setPassword(rs.getString("PASSWORD"));
                configs.add(conf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OPCConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return configs;
    }

    @Override
    public OPCConf getById(int id) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OPCDATA WHERE ID = ?");
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                OPCConf conf = new OPCConf();
                conf.setId(rs.getInt("ID"));
                conf.setName(rs.getString("NAME"));
                conf.setUrl(rs.getString("URL"));
                conf.setAnonymous(rs.getBoolean("ANON"));
                conf.setUserName(rs.getString("USERNAME"));
                conf.setPassword(rs.getString("PASSWORD"));
                return conf;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OPCConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public int save(OPCConf data) {
        int key = -1;
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO OPCDATA (NAME, URL, ANON, USERNAME, PASSWORD) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getUrl());
            stmt.setBoolean(3, data.isAnonymous());
            stmt.setString(4, data.getUserName());
            stmt.setString(5, data.getPassword());

            int rows = stmt.executeUpdate();

            if (rows != 1) {
                return key;
            }

            ResultSet generatedId = stmt.getGeneratedKeys();

            if (generatedId.next()) {
                data.setId(generatedId.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(OPCConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;
    }

    @Override
    public boolean delete(int id) {
        int rows = Integer.MIN_VALUE;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM OPCDATA WHERE ID = ?");
        ) {
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OPCConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows == 1;
    }
    
}
