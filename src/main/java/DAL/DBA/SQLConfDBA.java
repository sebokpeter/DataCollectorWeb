/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.DBA;

import BE.SQLConf;
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
public class SQLConfDBA implements DatabaseAccessInterface<SQLConf> {

    private final DataSource ds;

    public SQLConfDBA(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public List<SQLConf> getAll() {
        List<SQLConf> data = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SQLDATA");
        ) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                SQLConf conf = new SQLConf();
                conf.setId(results.getLong("ID"));
                conf.setName(results.getString("NAME"));
                conf.setPassword(results.getString("PASSWORD"));
                conf.setDbName(results.getString("DB_NAME"));
                conf.setDbAddress(results.getString("DB_ADDRESS"));
                conf.setDbPort(results.getInt("DB_PORT"));
                conf.setConnID(results.getInt("D_ID"));
                data.add(conf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }

    @Override
    public SQLConf getById(int id) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SQLDATA WHERE ID = ?");
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SQLConf conf = new SQLConf();
                conf.setId(rs.getLong("ID"));
                conf.setName(rs.getString("NAME"));
                conf.setPassword(rs.getString("PASSWORD"));
                conf.setDbName(rs.getString("DB_NAME"));
                conf.setDbAddress(rs.getString("DB_ADDRESS"));
                conf.setDbPort(rs.getInt("DB_PORT"));
                conf.setConnID(rs.getInt("D_ID"));
                return conf;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorConnDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int save(SQLConf data) {
        int key = -1;
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SQLDATA (NAME, PASSWORD, DB_NAME, DB_ADDRESS, DB_PORT, D_ID) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getPassword());
            stmt.setString(3, data.getDbName());
            stmt.setString(4, data.getDbAddress());
            stmt.setInt(5, data.getDbPort());
            stmt.setInt(6, data.getConnID());

            int affected = stmt.executeUpdate();
            if (affected != 1) {
                return key;
            }
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;
    }

    @Override
    public boolean delete(int id) {
        int rows = Integer.MIN_VALUE;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SQLDATA WHERE ID = ?");
        ) {    
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SQLConfDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows == 1;
    }
    
}
