package GUI;

import BE.Descriptor;
import DAL.DataAccess;
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
 * This servlet generates a form for inputting descriptor values.
 *
 * @author Peter
 */
@WebServlet(name = "descriptor_servlet", urlPatterns = {"/descriptor"})
public class descriptor_servlet extends HttpServlet {

    private final DataAccess dataAccess = new DataAccess();

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
        request.setAttribute("set_number", request.getParameter("set_number"));

        RequestDispatcher view = request.getRequestDispatcher("descriptor.jsp");
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
            // Retrieve parameter values from request
            String name = request.getParameter("name");
            String tableName = request.getParameter("table_name");
            String types[] = request.getParameterValues("type");
            String namespaces[] = request.getParameterValues("ns");
            String nodeIds[] = request.getParameterValues("nid");
            String idTypes[] = request.getParameterValues("id_type");

            for (String namespace : namespaces) {
                if (!Utils.Utility.tryParseInteger(namespace)) {
                    throw new IllegalArgumentException("Namespace index can only be number!");
                }
            }

            // Create a DESCRIPTOR_CONN
            int id = dataAccess.saveDescriptorConnection(name, tableName);
            boolean success = true;

            for (int i = 0; i < types.length; i++) {
                Descriptor d = new Descriptor();
                d.setD_ID(id);
                d.setType(types[i]);
                d.setNameSpace(Integer.parseInt(namespaces[i]));
                d.setNodeId(nodeIds[i]);
                d.setIdType(idTypes[i]);
                System.out.println("Descriptor " + name + ": " + d.toString());

                if (!dataAccess.saveDescriptor(d)) {
                    success = false;
                }
            }
            
            if (!success) {
                throw new Exception("The saving of one or two descriptor failed!");
            }
        } catch (Exception ex) {
            Logger.getLogger(descriptor_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("sqlconfig");
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
