/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seacon.datacollectorweb;

import DAL.DataAccess;
import Utils.Utility;
import entity.DescriptorConn;
import entity.SQLData;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peter
 */
@WebServlet(name = "sqlconf_servlet", urlPatterns = {"/sqlconf_servlet"})
public class sqlconf_servlet extends HttpServlet {

    private final DataAccess dataAccess = new DataAccess();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        List<DescriptorConn> d = dataAccess.getDescriptors();
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>SQL configuration</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>SQL server parameter configuration</h1>");
            out.println("<form name=\"sql_form\" method=\"post\" action=\"sqlconf_servlet\"><br>");
            out.println("Login name: <input type=\"text\" name=\"login\" value=\"Peter\"><br>");
            out.println("Password: <input type=\"password\" name=\"pwd\" value=\"Password\"><br>");
            out.println("Database name: <input type=\"text\" name=\"db_name\" value=\"TEST\"><br>");
            out.println("Address: <input type=\"text\" name=\"address\" value=\"localhost\"><br>");
            out.println("Port: <input type=\"number\" name=\"port\" min=\"1\" max=\"65535\" value=\"1433\"><br>");
            out.println("Descriptor: <select name=\"conn\">");
            for (DescriptorConn descriptorConn : d) {
                out.println("<option value="+descriptorConn.getId() + ">" + descriptorConn.getName() + "</option>");
            }
            out.println("</select><br>");
            out.println("<input type=\"submit\" value=\"Send\">");
            out.println("</form>");
            out.println("<a href=\"main_page\">Back</a>");
            out.println("<a href=\"descriptor_servlet\">Create new descriptor</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(sqlconf_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter out = response.getWriter();

        
        // Parameters used to connect to the SQL database
        String name = request.getParameter("login"); // Name used to connect to the SQL server
        // TODO: don't save password plaintext?
        String pwd = request.getParameter("pwd"); // Password  used to connect to the SQL server
        String dbName = request.getParameter("db_name"); // Name of the database 
        String address = request.getParameter("address"); // Address of the database (pl: localhost)
        String portStr = request.getParameter("port");
        String conn = request.getParameter("conn");
        
        int port = -1;
        int connId = -1;
        
        if(Utility.tryParseInteger(portStr)) {
            port = Integer.parseInt(portStr);
        } else {
            throw new IllegalArgumentException("Port must be a number");
        }
        
        if(port < 1 || port > 65535) {
            throw  new IllegalArgumentException("Port number must be between 1 and 65535");
        }
        
        if(Utility.tryParseInteger(conn)) {
           connId = Integer.parseInt(conn);
        } else {
            throw new IllegalArgumentException("Connection ID required!"); // TODO: better name...
        }
        
        SQLData data = new SQLData();
        
        data.setName(name);
        data.setPassword(pwd);
        data.setDbName(dbName);
        data.setDbAddress(address);
        data.setDbPort(port);
        data.setConnID(connId);
               
        if(dataAccess.saveConfig(data)) {
            // Write the success response message, in an HTML page
            out.println("<html>");
            out.println("<head><title>Data addedd</title></head>");
            out.println("<body>");
            out.println("<h1>Added SQL server connection with the following parameters:</h1>");
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>Password: " + pwd + "</p>");
            out.println("<p>Database name: " + dbName + "</p>");
            out.println("<p>Database address: " + address + "</p>");
            out.println("<p>Database port: " + port + "</p>");
            out.println("<a href=\"main_page\">Back</a>");
            out.println("</body></html>");
            out.close();
        } else {
            // Write the faliure response message, in a HTML page
            out.println("<html>");
            out.println("<head><title>Data addedd</title></head>");
            out.println("<body>");
            out.println("<h1>Failed to add SQL server connection!</h1>");
            out.println("<a href=\"main_page\">Back</a>");
            out.println("</body></html>");
            out.close(); 
        }
        
    } 

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
