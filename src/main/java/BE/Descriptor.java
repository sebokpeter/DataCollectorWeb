package BE;

/**
 * Relationship mapping between data provided by an OPC server and a database table;
 * TODO: better name
 * @author Peter
 */
public class Descriptor {
    private int id; 
    private int D_ID; // ID of the connection between the SQL data and the descriptor
    private String db_field; // Associated field in the database (unused)
    private String type; // Datatype of the recieved data
    private int nameSpace; // OPC server namespace 
    private String nodeId; // OPC server NodeID
    private String idType; // OPC server NodeID type
    private int itemOrder; // Order of items in the target database (unused)

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getD_ID() {
        return D_ID;
    }

    public void setD_ID(int D_ID) {
        this.D_ID = D_ID;
    }

    public String getDb_field() {
        return db_field;
    }

    public void setDb_field(String db_field) {
        this.db_field = db_field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

    public int getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(int nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @Override
    public String toString() {
        return "Descriptor{" + "id=" + id + ", D_ID=" + D_ID + ", db_field=" + db_field + ", type=" + type + ", nameSpace=" + nameSpace + ", nodeId=" + nodeId + ", idType=" + idType + ", itemOrder=" + itemOrder + '}';
    }
    
}
