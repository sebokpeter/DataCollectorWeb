/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.util.List;

/**
 * Used to access the functions of the database access classes.
 *
 * @author Developer
 * @param <T> The type of the data that the class handles.
 */
public interface DatabaseAccessInterface<T> {

    /**
     * Returns a list of objects of the given type.
     *
     * @return A list of objects of the given type.
     */
    public List<T> getAll();

    /**
     * Returns a single object of the given type, with matching id.
     *
     * @param id The id of the object.
     * @return An object of the given type.
     */
    public T getById(int id);

    /**
     * Saves the provided object to the database.
     * 
     * @param data The object to be saved.
     * @return The id of the saved object, or -1 if no key was returned
     * by the database.
     */
    public int save(T data);

    /**
     * Deletes the database entry with matching id.
     * 
     * @param id The id of the database entry.
     * @return True if successful, false otherwise.
     */
    public boolean delete(int id);
}
