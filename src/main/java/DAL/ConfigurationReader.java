package DAL;

import java.io.File;
import java.io.IOException;
import org.ini4j.Wini;

/**
 * Reads configurations from Tomcat/conf/DataCollectorConf.ini file
 * @author Peter
 */
public class ConfigurationReader {
    
    public static String getPath(String filename) throws IOException {
        Wini ini = new Wini(new File(filename));
        String path = ini.get("Config", "Path", String.class);
        
        return path;
    }
    
}
