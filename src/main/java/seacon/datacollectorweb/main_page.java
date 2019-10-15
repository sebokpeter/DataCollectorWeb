package seacon.datacollectorweb;

import DAL.DataAccess;
import entity.DescriptorConn;
import entity.OPCConf;
import entity.SQLData;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for creating the main page of the application.
 * @author Peter
 */
public class main_page extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            List<SQLData> data = dataAccess.getSqlData();
            List<OPCConf> opcData = dataAccess.getOPCConfigurations();
            
            System.out.println("SQL data retrieved, number of entries: " + data.size());
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Index</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Seacon DataCollector</h1>");
            //Build form for selecting SQL config
           
            out.println("<table border=\"1\">");
            out.println("<form name=\"selection_form\" method=\"post\" action=\"main_page\">");
            out.println("<tr>");
            out.println("<td>ID</td>");
            out.println("<td>NAME</td>");
            out.println("<td>PASSWORD</td>");
            out.println("<td>DATABASE NAME</td>");
            out.println("<td>DATABASE ADDRESS</td>");
            out.println("<td>DATABASE PORT</td>");
            out.println("<td>DESCRIPTOR</td>");
            out.println("<td>SELECTED</td>");
            out.println("<td>DELETE</td>");
            out.println("</tr>");
            for (SQLData sqlData : data) {
                out.println("<tr>");
                DescriptorConn d = dataAccess.getDescriptorConnByID(sqlData.getConnID());
                out.println("<td>" + sqlData.getId()+ "</td>");
                out.println("<td>" + sqlData.getName() + "</td>");
                out.println("<td>" + sqlData.getPassword() + "</td>");
                out.println("<td>" + sqlData.getDbName()+ "</td>");
                out.println("<td>" + sqlData.getDbAddress()+ "</td>");
                out.println("<td>" + sqlData.getDbPort() + "</td>");
                out.println("<td>" + d.getName() + "</td>");
                out.println("<td><input type=\"radio\" name=\"selection\" value="+sqlData.getId()+" ></td>");
                out.println("<td><button type=\"submit\" name=\"deleteButton\" value=" + sqlData.getId() + ">Delete</td>");
                out.println("</tr>");
            }
            out.println("<input type=\"submit\" value=\"Start\"><br> ");
            out.println("</table>");
            out.println("<h2><a href=\"sqlconf_servlet\">Add New Configuration</a></h2>\n");
            
            
            // Build a form for selecting OPC configurations
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<td>ID</td>");
            out.println("<td>NAME</td>");
            out.println("<td>URL</td>");
            out.println("<td>USERNAME</td>");
            out.println("<td>PASSWORD</td>");
            out.println("<td>ANONYMOUS</td>");
            out.println("<td>SELECTED</td>");
            out.println("<td>DELETE</td>");
            out.println("</tr>");
            for (OPCConf oPCConf : opcData) {
                out.println("<tr>");
                out.println("<td>" + oPCConf.getId() + "</td>");
                out.println("<td>" + oPCConf.getName() + "</td>");
                out.println("<td>" + oPCConf.getUrl() + "</td>");
                out.println("<td>" + oPCConf.getUserName() + "</td>");
                out.println("<td>" + oPCConf.getPassword() + "</td>");
                out.println("<td>" + oPCConf.isAnonymous() + "</td>");
                out.println("<td><input type=\"radio\" name=\"opc_selection\" value="+oPCConf.getId()+" ></td>");
                out.println("<td><button type=\"submit\" name=\"deleteOPCButton\" value=" + oPCConf.getId() + ">Delete</td>");
                out.println("</tr>");
            }
            out.println("</form>");
            out.println("</table>");
            out.println("<h2><a href=\"opc_conf_servlet\">OPC configuration</a></h2>\n");
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main_page.class.getName()).log(Level.SEVERE, null, ex);
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
        
            String redirectUrl = "main_page";
        
            String delete = request.getParameter("deleteButton"); // Check if deletion was requested by the user

            if(delete != null) {
               int id = Integer.parseInt(delete);
               dataAccess.deleteConfig(id);
            }
            
            String deleteOPC = request.getParameter("deleteOPCButton");
            
            if(deleteOPC != null) {
               int id = Integer.parseInt(deleteOPC);
               dataAccess.deleteOpcConfig(id);
            }
            
            String sqlSelection = request.getParameter("selection");
            String opcSelection = request.getParameter("opc_selection");
            
            if (sqlSelection != null && opcSelection != null) {
                // Start the data collecting application
                request.getSession().setAttribute("sql", sqlSelection);
                request.getSession().setAttribute("opc", opcSelection);
                redirectUrl = "process_output_display";
            }
            response.sendRedirect(redirectUrl);
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
