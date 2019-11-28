package GUI;

import Utils.Utility;
import BE.SQLConf;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create a form for inputting SQL server values
 *
 * @author Peter
 */
@WebServlet(name = "sqlconf_servlet", urlPatterns = {"/sqlconfig"})
public class sqlconf_servlet extends HttpServlet {

    private final Model model = Model.getInstance();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("descriptors", model.getDescriptorConns());
        RequestDispatcher view = request.getRequestDispatcher("sqlconfig.jsp");
        view.forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(sqlconf_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        if (Utility.tryParseInteger(portStr)) {
            port = Integer.parseInt(portStr);
        } else {
            throw new IllegalArgumentException("Port must be a number");
        }

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port number must be between 1 and 65535");
        }

        if (Utility.tryParseInteger(conn)) {
            connId = Integer.parseInt(conn);
        } else {
            throw new IllegalArgumentException("Connection ID required!"); // TODO: better name...
        }

        SQLConf data = new SQLConf();

        data.setName(name);
        data.setPassword(pwd);
        data.setDbName(dbName);
        data.setDbAddress(address);
        data.setDbPort(port);
        data.setConnID(connId);

        if (model.saveSqlConfig(data)) {
           response.sendRedirect("main");
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
    }

}
