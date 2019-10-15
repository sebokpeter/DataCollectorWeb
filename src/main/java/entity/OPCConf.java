package entity;

/**
 * Data used to connect to the OPC-UA server
 * @author Peter
 */
public class OPCConf {
    
    private int id; 
    private String url; // OPC-UA server URL
    private String name; // OPC-UA server name
    private String userName; // OPC-UA username (if anonymous connection is not possible)
    private String password; // OPC-UA password (if anonymous connection is not possible)
    private boolean anonymous; // Whether or not anonymous connection is possible

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
    
    
}
