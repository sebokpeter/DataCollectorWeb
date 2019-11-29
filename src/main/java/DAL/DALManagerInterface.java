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
public interface DALManagerInterface {

    //SQL Functions ------------------------------------------------------------
    /**
     * Retrieves a list of stored configurations.
     *
     * @return A list of SQL configurations.
     */
    public List<SQLConf> getSqlConfigs();

    /**
     * Saves a configuration into the H2 database.
     *
     * @param data The configuration that will be saved.
     * @return The id of the saved configuration, or -1 if no key was returned
     * by the database.
     */
    public int saveSqlConfig(SQLConf data);

    /**
     * Deletes a configuration with the specified ID.
     *
     * @param id The id of the configuration that will be deleted
     * @return True if successful, false otherwise.
     */
    public boolean deleteSqlConfig(int id);

    //Descriptor Functions -----------------------------------------------------
    /**
     * Saves a given descriptor to the H2 database.
     *
     * @param d The descriptor that will be saved
     * @return The id of the saved configuration, or -1 if no key was returned
     * by the database.
     */
    public int saveDescriptor(Descriptor d);

    //DescriptorConnection Functions -------------------------------------------
    /**
     * Retrieves all descriptor connections.
     *
     * @return A list of descriptor connections
     */
    public List<DescriptorConn> getDescriptorConns();

    /**
     * Returns a specific object by ID.
     *
     * @param id ID of the connection.
     * @return The connection with the specific ID if exists, null otherwise.
     */
    public DescriptorConn getDescriptorConnByID(int id);

    /**
     * Saves a descriptor connection to the database.
     *
     * @param data The connection that will be saved.
     * @return The ID of the connection, or -1 if no key was returned by the
     * database.
     */
    public int saveDescriptorConn(DescriptorConn data);

    //OPC Functions ------------------------------------------------------------
    /**
     * Retrieves a list of OPC configurations.
     *
     * @return The OPC configurations saved in the database.
     */
    public List<OPCConf> getOpcConfigs();

    /**
     * Saves an OPC connection configuration to the database.
     *
     * @param conf The configuration that will be saved.
     * @return The id of the saved configuration, or -1 if no key was returned
     * by the database.
     */
    public int saveOpcConfig(OPCConf conf);

    /**
     * Deletes a configuration with the specified ID.
     *
     * @param id The id of the configuration that will be deleted
     * @return True if successful, false otherwise.
     */
    public boolean deleteOpcConfig(int id);
}
