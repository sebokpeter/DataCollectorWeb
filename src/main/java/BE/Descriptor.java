package BE;

/**
 * Relationship mapping between data provided by an OPC server and a database
 * table; TODO: better name
 *
 * @author Peter
 */
public class Descriptor {

    private int id;
    private int D_ID; // ID of the connection between the SQL data and the descriptor
    private String type; // Datatype of the recieved data
    private int nameSpace; // OPC server namespace 
    private String nodeId; // OPC server NodeID
    private String idType; // OPC server NodeID type

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Descriptor{" + "id=" + id + ", D_ID=" + D_ID + ", type=" + type + ", nameSpace=" + nameSpace + ", nodeId=" + nodeId + ", idType=" + idType + '}';
    }

}
