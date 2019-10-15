package Utils;

/**
 * Utility methods
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
