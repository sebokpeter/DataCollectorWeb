/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 * Relationship mapping between data provided by an OPC server and a database table;
 * @author Peter
 */
public class Descriptor {
    private int id; 
    private int D_ID; // ID of the connection between the SQL data and the descriptor
    private String db_field; // Associated field in the database
    private String type; // Datatype of the recieved data
    private int nameSpace;
    private String nodeId;
    private String idType;
    private int itemOrder; 

    
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
