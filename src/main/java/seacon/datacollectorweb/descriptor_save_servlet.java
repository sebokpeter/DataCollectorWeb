package seacon.datacollectorweb;

import DAL.DataAccess;
import entity.Descriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Save descriptors to H2 database.
 * @author Peter
 */
@WebServlet(name = "descriptor_save_servlet", urlPatterns = {"/descriptor_save"})
public class descriptor_save_servlet extends HttpServlet {
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet descriptor_save_servlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet descriptor_save_servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {            
            // Retrieve parameter values from request
            String name = request.getParameter("name");
            String tableName = request.getParameter("table_name");
            String dbFields[] = request.getParameterValues("db_field");
            String types[] = request.getParameterValues("type");
            String namespaces[] = request.getParameterValues("ns");
            String nodeIds[] = request.getParameterValues("nid");
            String idTypes[] = request.getParameterValues("id_type");
            String order[] = request.getParameterValues("order");
                        
            for (String ord : order) {
                if (!Utils.Utility.tryParseInteger(ord)) {
                    throw new IllegalArgumentException("Order value can only be number!");
                }
            }
           
            for (String namespace : namespaces) {
                if (!Utils.Utility.tryParseInteger(namespace)) {
                    throw new IllegalArgumentException("Namespace index can only be number!");
                }
            }
            
            // Create a DESCRIPTOR_CONN
            int id = dataAccess.saveDescriptorConnection(name, tableName);
            
            
            for (int i = 0; i < dbFields.length; i++) {
                Descriptor d = new Descriptor();
                d.setD_ID(id);
                d.setDb_field(dbFields[i]);
                d.setType(types[i]);
                d.setNameSpace(Integer.parseInt(namespaces[i]));
                d.setNodeId(nodeIds[i]);
                d.setIdType(idTypes[i]);
                d.setItemOrder(Integer.parseInt(order[i]));
                System.out.println("Descriptor " + name + ": " + d.toString());
                
                dataAccess.saveDescriptor(d);
            }
            
            response.sendRedirect("sqlconf_servlet");
        } catch (Exception ex) {
            Logger.getLogger(descriptor_save_servlet.class.getName()).log(Level.SEVERE, null, ex);
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
