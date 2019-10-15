package entity;

/**
 * Used to connect a group of descriptors
 * TODO: find a better name
 * @author Peter
 */
public class DescriptorConn {
    int id;
    String name; // Identifying name
    String tableName; // Target database table name

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    
}
