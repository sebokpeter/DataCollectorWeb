/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Peter
 */
public class Utility {
    
    /**
     * Checks if the supplied string can be safely parsed to an integer
     * @param num A string that will be checked.
     * @return True if the supplied string can be parsed into an integer, false otherwise.
     */
    public static boolean tryParseInteger(String num) {
        try{
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
