/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BE.Descriptor;
import BE.DescriptorConn;
import BE.OPCConf;
import BE.ProcessOutputPrinter;
import BE.SQLConf;
import DAL.ConfigurationReader;
import DAL.DALManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import DAL.DALManagerInterface;
import java.lang.module.Configuration;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author Developer
 */
public class Model {

    private static DALManagerInterface dalManager;
    private static Model instance;
    private final String DATA_COLLECTOR_PATH = "C:\\Users\\Developer\\Documents\\GitHub\\DataCollectorDesktop\\target\\DataCollector-jar-with-dependencies.jar"; // Path of the application, testing purposes only
    private final ArrayList<ProcessOutputPrinter> processList = new ArrayList<>();

    private Model() {
        dalManager = new DALManager();
    }

    public static Model getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new Model();
            return instance;
        }
    }

    /**
     * Starts the data collecting application.
     *
     * @param sql The id of the SQL configuration.
     * @param opc The id of the OPC configuration.
     */
    public void startProcess(String sql, String opc) {
        try {
            System.out.println("SQL: " + sql);
            System.out.println("OPC: " + opc);
            System.out.println("Process starting");

            String path = ConfigurationReader.getPath("../conf/DataCollectorConf.ini");           // Since it is a web app running under tomcat the cwd will be Tomcat/bin

            ProcessBuilder pb = new ProcessBuilder("java", "-jar", path, sql, opc);

            ProcessOutputPrinter printer = new ProcessOutputPrinter();

            pb.redirectErrorStream(true);
            Process p = pb.start();

            printer.setSqlConfigId(sql);
            printer.setOpcConfigId(opc);
            printer.setProcess(p);
            printer.start();

            processList.add(printer);
        } catch (IOException | IllegalAccessException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stops the data collecting application with the matching id.
     *
     * @param id The id of the process
     */
    public void stopProcess(int id) {
        processList.get(id).terminate();
        processList.remove(id);
    }

    public List<ProcessOutputPrinter> getProcessList() {
        return this.processList;
    }

    public List<SQLConf> getSqlConfigs() {
        return dalManager.getSqlConfigs();
    }

    public List<OPCConf> getOpcConfigs() {
        return dalManager.getOpcConfigs();
    }

    public List<DescriptorConn> getDescriptorConns() {
        return dalManager.getDescriptorConns();
    }

    public boolean deleteSqlConfig(int id) {
        return dalManager.deleteSqlConfig(id);
    }

    public boolean deleteOpcConfig(int id) {
        return dalManager.deleteOpcConfig(id);
    }

    public boolean saveOpcConfig(OPCConf conf) {
        return dalManager.saveOpcConfig(conf) > 0;
    }

    public boolean saveSqlConfig(SQLConf data) {
        return dalManager.saveSqlConfig(data) > 0;
    }

    public int saveDescriptorConn(String name, String tableName) {
        DescriptorConn temp = new DescriptorConn();
        temp.setName(name);
        temp.setTableName(tableName);
        return dalManager.saveDescriptorConn(temp);
    }

    public boolean saveDescriptor(Descriptor d) {
        return dalManager.saveDescriptor(d) > 0;
    }

}
