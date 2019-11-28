package DAL;

import BE.Descriptor;
import BE.DescriptorConn;
import BE.OPCConf;
import BE.SQLConf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Used to access to data located in the H2 database.
 *
 * @author Peter
 */
public class DataAccessManager implements DataAccessInterface {
    
    private Context ctx = null;
    private DataSource ds = null;

    public DataAccessManager() {
        try {
            Class.forName("org.h2.Driver");
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/paramsDS");
        } catch (NamingException | ClassNotFoundException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //SQL Functions ------------------------------------------------------------
    
    @Override
    public List<SQLConf> getSqlConfigs() {
        List<SQLConf> data = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SQLDATA");
        ) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                SQLConf current = new SQLConf();
                current.setId(results.getLong("ID"));
                current.setName(results.getString("NAME"));
                current.setPassword(results.getString("PASSWORD"));
                current.setDbName(results.getString("DB_NAME"));
                current.setDbAddress(results.getString("DB_ADDRESS"));
                current.setDbPort(results.getInt("DB_PORT"));
                current.setConnID(results.getInt("D_ID"));
                data.add(current);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }

    @Override
    public boolean saveSqlConfig(SQLConf data) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO SQLDATA (NAME, PASSWORD, DB_NAME, DB_ADDRESS, DB_PORT, D_ID) VALUES (?, ?, ?, ?, ?, ?)");
        ) {
            stmt.setString(1, data.getName());
            stmt.setString(2, data.getPassword());
            stmt.setString(3, data.getDbName());
            stmt.setString(4, data.getDbAddress());
            stmt.setInt(5, data.getDbPort());
            stmt.setInt(6, data.getConnID());

            int affected = stmt.executeUpdate();

            if (affected != 1) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteSqlConfig(int id) {
        int rows = Integer.MIN_VALUE;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM SQLDATA WHERE ID=?");
        ) {    
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows == 1;
    }

    //Descriptor Functions -----------------------------------------------------

    @Override
    public boolean saveDescriptor(Descriptor d) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DESCRIPTOR (D_ID, TYPE, NAMESPACE, NODEID, NODEID_TYPE) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setInt(1, d.getD_ID());
            stmt.setString(2, d.getType());
            stmt.setInt(3, d.getNameSpace());
            stmt.setString(4, d.getNodeId());
            stmt.setString(5, d.getIdType());
            int rows = stmt.executeUpdate();

            if (rows != 1) {
                return false;
            }

            ResultSet generatedID = stmt.getGeneratedKeys();

            if (generatedID.next()) {
                d.setId(generatedID.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    //DescriptorConn Functions -------------------------------------------------
    
    @Override
    public List<DescriptorConn> getDescriptorConns() {
        List<DescriptorConn> descriptorconns = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DESCRIPTOR_CONN");
        ) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DescriptorConn c = new DescriptorConn();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setTableName(rs.getString(3));
                descriptorconns.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return descriptorconns;
    }
    
    @Override
    public DescriptorConn getDescriptorConnByID(int id) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM DESCRIPTOR_CONN WHERE D_ID = ?");
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            DescriptorConn d = new DescriptorConn();

            while (rs.next()) {
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                d.setTableName(rs.getString(3));
            }

            return d;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public int saveDescriptorConn(String name, String tableName) {
        int key = -1;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO DESCRIPTOR_CONN (NAME, TABLE_NAME) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, name);
            stmt.setString(2, tableName);

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;
    }

    //OPC Functions ------------------------------------------------------------
    
    @Override
    public List<OPCConf> getOpcConfigs() {
        List<OPCConf> configs = new ArrayList<>();

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM OPCDATA");
        ) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String url = rs.getString("URL");
                String name = rs.getString("NAME");
                String userName = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                boolean anon = rs.getBoolean("ANON");

                OPCConf con = new OPCConf();
                con.setId(id);
                con.setUrl(url);
                con.setName(name);
                con.setAnonymous(anon);
                con.setUserName(userName);
                con.setPassword(password);

                configs.add(con);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return configs;
    }
    
    @Override
    public boolean saveOpcConfig(OPCConf conf) {
        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO OPCDATA (NAME, URL, ANON, USERNAME, PASSWORD) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, conf.getName());
            stmt.setString(2, conf.getUrl());
            stmt.setBoolean(3, conf.isAnonymous());
            stmt.setString(4, conf.getUserName());
            stmt.setString(5, conf.getPassword());

            int rows = stmt.executeUpdate();

            if (rows != 1) {
                return false;
            }

            ResultSet generatedId = stmt.getGeneratedKeys();

            if (generatedId.next()) {
                conf.setId(generatedId.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    @Override
    public boolean deleteOpcConfig(int id) {
        int rows = Integer.MIN_VALUE;

        try (
            Connection conn = ds.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM OPCDATA WHERE ID=?");
        ) {
            stmt.setInt(1, id);
            rows = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rows == 1;
    }
    
    
    
}
