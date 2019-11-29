/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.DBA;

import BE.DescriptorConn;
import java.util.List;
import javax.sql.DataSource;
import DAL.DatabaseAccessInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Developer
 */
public class DescriptorConnDBA implements DatabaseAccessInterface<DescriptorConn>{

    private final DataSource ds;

    public DescriptorConnDBA(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public List<DescriptorConn> getAll() {
        List<DescriptorConn> descriptorconns = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DESCRIPTOR_CONN");
        ) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DescriptorConn dc = new DescriptorConn();
                dc.setId(rs.getInt("D_ID"));
                dc.setName(rs.getString("NAME"));
                dc.setTableName(rs.getString("TABLE_NAME"));
                descriptorconns.add(dc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorConnDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return descriptorconns;
    }

    @Override
    public DescriptorConn getById(int id) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DESCRIPTOR_CONN WHERE D_ID = ?");
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DescriptorConn dc = new DescriptorConn();
                dc.setId(rs.getInt("D_ID"));
                dc.setName(rs.getString("NAME"));
                dc.setTableName(rs.getString("TABLE_NAME"));
                return dc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorConnDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int save(DescriptorConn data) {
        int key = -1;
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DESCRIPTOR_CONN (NAME, TABLE_NAME) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getTableName());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorConnDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;
    }

    @Override
    public boolean delete(int id) {
        int rows = Integer.MIN_VALUE;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM DESCRIPTOR_CONN WHERE ID = ?");
        ) {    
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorConnDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows == 1;
    }
    
}
