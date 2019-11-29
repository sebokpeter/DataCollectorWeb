/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.DBA;

import BE.Descriptor;
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
public class DescriptorDBA implements DatabaseAccessInterface<Descriptor> {

    private final DataSource ds;

    public DescriptorDBA(DataSource ds) {
        this.ds = ds;
    }
    
    @Override
    public List<Descriptor> getAll() {
        List<Descriptor> descriptors = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DESCRIPTOR");
        ) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Descriptor d = new Descriptor();
                d.setId(rs.getInt("ID"));
                d.setD_ID(rs.getInt("D_ID"));
                d.setType(rs.getString("TYPE"));
                d.setNameSpace(rs.getInt("NAMESPACE"));
                d.setNodeId(rs.getString("NODEID"));
                d.setIdType(rs.getString("NODEID_TYPE"));
                descriptors.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorDBA.class.getName()).log(Level.SEVERE, null, ex);
        }

        return descriptors;
    }

    @Override
    public Descriptor getById(int id) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DESCRIPTOR WHERE ID = ?");
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Descriptor d = new Descriptor();
                d.setId(rs.getInt("ID"));
                d.setD_ID(rs.getInt("D_ID"));
                d.setType(rs.getString("TYPE"));
                d.setNameSpace(rs.getInt("NAMESPACE"));
                d.setNodeId(rs.getString("NODEID"));
                d.setIdType(rs.getString("NODEID_TYPE"));
                return d;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int save(Descriptor data) {
        int key = -1;
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DESCRIPTOR (D_ID, TYPE, NAMESPACE, NODEID, NODEID_TYPE) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setInt(1, data.getD_ID());
            stmt.setString(2, data.getType());
            stmt.setInt(3, data.getNameSpace());
            stmt.setString(4, data.getNodeId());
            stmt.setString(5, data.getIdType());
            int rows = stmt.executeUpdate();

            if (rows != 1) {
                return key;
            }

            ResultSet generatedID = stmt.getGeneratedKeys();

            if (generatedID.next()) {
                data.setId(generatedID.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return key;
    }

    @Override
    public boolean delete(int id) {
        int rows = Integer.MIN_VALUE;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM DESCRIPTOR WHERE ID = ?");
        ) {    
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DescriptorDBA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows == 1;
    }
    
}
