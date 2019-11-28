/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Descriptor;
import BE.DescriptorConn;
import BE.OPCConf;
import BE.SQLConf;
import java.util.List;

/**
 * Used to access the functions of the Data Access Manager.
 *
 * @author Developer
 */
public interface DataAccessInterface {

    //SQL Functions ------------------------------------------------------------
    /**
     * Retrieve a list of stored configurations
     *
     * @return
     */
    public List<SQLConf> getSqlConfigs();

    /**
     * Save a configuration into the H2 database.
     *
     * @param data The configuration that will be saved.
     * @return True if successful (the number of affected rows is 1), false
     * otherwise
     */
    public boolean saveSqlConfig(SQLConf data);

    /**
     * Deletes a configuration with the specified ID
     *
     * @param id The id of the configuration that will be deleted
     * @return True if successful, false otherwise.
     */
    public boolean deleteSqlConfig(int id);

    //Descriptor Functions -----------------------------------------------------
    /**
     * Save a given descriptor to the H2 database
     *
     * @param d The descriptor that will be saved
     * @return True if the descriptor was saved (the number of modified rows is
     * 1), false otherwise.
     */
    public boolean saveDescriptor(Descriptor d);

    //DescriptorConnection Functions -------------------------------------------
    /**
     * Retrieve all descriptor connections
     *
     * @return A list of descriptor connections
     */
    public List<DescriptorConn> getDescriptorConns();

    /**
     * Return a specific object by ID.
     *
     * @param id ID of the connection.
     * @return The connection with the specific ID if exists, null otherwise.
     */
    public DescriptorConn getDescriptorConnByID(int id);

    /**
     * Save a descriptor connection to the database.
     *
     * @param name The name of the connection.
     * @param tableName Table name in the SQL database.
     * @return The ID of the connection, or -1 if no key was returned by the
     * database.
     */
    public int saveDescriptorConn(String name, String tableName);

    //OPC Functions ------------------------------------------------------------
    /**
     * Retrieve a list of OPC configurations
     *
     * @return THe OPC configurations saved in the database.
     */
    public List<OPCConf> getOpcConfigs();

    /**
     * Saves an OPC connection configuration to the database.
     *
     * @param conf The configuration that will be saved.
     * @return True if successful (the number of updated rows equals 1), false
     * otherwise.
     */
    public boolean saveOpcConfig(OPCConf conf);

    /**
     * Deletes a configuration with the specified ID
     *
     * @param id The id of the configuration that will be deleted
     * @return True if successful, false otherwise.
     */
    public boolean deleteOpcConfig(int id);
}
