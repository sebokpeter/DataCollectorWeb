package GUI;

import BE.ProcessOutputPrinter;
import BE.SQLConf;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is responsible for creating the main page of the application.
 *
 * @author Peter
 */
public class main_page_servlet extends HttpServlet {

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
        try {
            List<SQLConf> sqlData = model.getSqlConfigs();
            request.setAttribute("sqlData", sqlData);
            request.setAttribute("opcData", model.getOpcConfigs());
            request.setAttribute("descriptorconns", model.getDescriptorConns());

            List<ProcessOutputPrinter> processList = model.getProcessList();
            if (!processList.isEmpty()) {
                request.setAttribute("processes", processList);
            }

            RequestDispatcher view = request.getRequestDispatcher("main.jsp");
            view.forward(request, response);
        } catch (IOException | ServletException ex) {
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
        try {
            String delete = request.getParameter("deleteButton"); // Check if deletion was requested by the user
            if (delete != null) {
                int id = Integer.parseInt(delete);
                model.deleteSqlConfig(id);
            }

            String deleteOPC = request.getParameter("deleteOPCButton");
            if (deleteOPC != null) {
                int id = Integer.parseInt(deleteOPC);
                model.deleteOpcConfig(id);
            }

            String stopProcess = request.getParameter("stopProcess");
            if (stopProcess != null) {
                int id = Integer.parseInt(stopProcess) - 1;
                model.stopProcess(id);
            }

            String sqlSelection = request.getParameter("sql_selection");
            String opcSelection = request.getParameter("opc_selection");
            boolean start = Boolean.valueOf(request.getParameter("startButton"));
            if (sqlSelection != null && opcSelection != null && start) {
                model.startProcess(sqlSelection, opcSelection);
            }

            response.sendRedirect("main");
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(main_page_servlet.class.getName()).log(Level.SEVERE, null, ex);
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
