package DAL;

import BE.Descriptor;
import BE.DescriptorConn;
import BE.OPCConf;
import BE.SQLConf;
import DAL.DBA.DescriptorConnDBA;
import DAL.DBA.DescriptorDBA;
import DAL.DBA.OPCConfDBA;
import DAL.DBA.SQLConfDBA;
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
public class DALManager implements DALManagerInterface {

    private Context ctx;
    private DataSource ds;
    private DatabaseAccessInterface<SQLConf> sql;
    private DatabaseAccessInterface<OPCConf> opc;
    private DatabaseAccessInterface<Descriptor> descriptor;
    private DatabaseAccessInterface<DescriptorConn> descriptorconn;

    public DALManager() {
        try {
            Class.forName("org.h2.Driver");
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/paramsDS");

            this.sql = new SQLConfDBA(ds);
            opc = new OPCConfDBA(ds);
            descriptor = new DescriptorDBA(ds);
            descriptorconn = new DescriptorConnDBA(ds);
        } catch (NamingException | ClassNotFoundException ex) {
            Logger.getLogger(DALManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //SQL Functions ------------------------------------------------------------
    @Override
    public List<SQLConf> getSqlConfigs() {
        return sql.getAll();
    }

    @Override
    public int saveSqlConfig(SQLConf data) {
        return sql.save(data);
    }

    @Override
    public boolean deleteSqlConfig(int id) {
        return sql.delete(id);
    }

    //OPC Functions ------------------------------------------------------------
    @Override
    public List<OPCConf> getOpcConfigs() {
        return opc.getAll();
    }

    @Override
    public int saveOpcConfig(OPCConf conf) {
        return opc.save(conf);
    }

    @Override
    public boolean deleteOpcConfig(int id) {
        return opc.delete(id);
    }

    //Descriptor Functions -----------------------------------------------------
    @Override
    public int saveDescriptor(Descriptor d) {
        return descriptor.save(d);
    }

    //DescriptorConn Functions -------------------------------------------------
    @Override
    public List<DescriptorConn> getDescriptorConns() {
        return descriptorconn.getAll();
    }

    @Override
    public DescriptorConn getDescriptorConnByID(int id) {
        return descriptorconn.getById(id);
    }

    @Override
    public int saveDescriptorConn(DescriptorConn data) {
        return descriptorconn.save(data);
    }

}
