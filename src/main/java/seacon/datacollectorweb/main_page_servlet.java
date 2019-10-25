package seacon.datacollectorweb;

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
 * This servlet is responsible for creating the main page of the application.
 *
 * @author Peter
 */
public class main_page_servlet extends HttpServlet {
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
        try {
            request.setAttribute("sqlData", dataAccess.getSqlData());
            request.setAttribute("opcData", dataAccess.getOPCConfigurations());
            RequestDispatcher view = request.getRequestDispatcher("main.jsp");
            view.forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main_page_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String redirectUrl = "main";

        String delete = request.getParameter("deleteButton"); // Check if deletion was requested by the user

        if (delete != null) {
            int id = Integer.parseInt(delete);
            dataAccess.deleteConfig(id);
        }

        String deleteOPC = request.getParameter("deleteOPCButton");

        if (deleteOPC != null) {
            int id = Integer.parseInt(deleteOPC);
            dataAccess.deleteOpcConfig(id);
        }

        String sqlSelection = request.getParameter("selection");
        String opcSelection = request.getParameter("opc_selection");

        if (sqlSelection != null && opcSelection != null) {
            // Start the data collecting application
            request.getSession().setAttribute("sql", sqlSelection);
            request.getSession().setAttribute("opc", opcSelection);
            redirectUrl = "processdisplay";
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
