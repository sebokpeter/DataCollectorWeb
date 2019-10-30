package GUI;

import BE.ProcessOutputPrinter;
import DAL.DataAccess;
import java.io.IOException;
import java.util.ArrayList;
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

    private final DataAccess dataAccess = new DataAccess();
    private final String DATA_COLLECTOR_PATH = "C:\\Users\\Developer\\Documents\\GitHub\\DataCollectorDesktop\\target\\DataCollector-jar-with-dependencies.jar"; // Path of the application, testing purposes only
    private final ArrayList<ProcessOutputPrinter> processList = new ArrayList<>();

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
            if (!processList.isEmpty()) {
                request.setAttribute("processes", processList);
            }

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
        try {
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

            String stopProcess = request.getParameter("stopProcess");
            if (stopProcess != null) {
                int id = Integer.parseInt(stopProcess) - 1;
                processList.get(id).terminate();
                processList.remove(id);
            }

            String sqlSelection = request.getParameter("sql_selection");
            String opcSelection = request.getParameter("opc_selection");
            boolean start = Boolean.valueOf(request.getParameter("startButton"));
            if (sqlSelection != null && opcSelection != null && start) {
                // Start the data collecting application
                startProcess(sqlSelection, opcSelection);
            }
            
            response.sendRedirect("main");
        } catch (Exception ex) {
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

    /**
     * Starts the data collecting application.
     *
     * @param sql The id of the SQL configuration.
     * @param opc The id of the OPC configuration.
     */
    private void startProcess(String sql, String opc) {
        try {
            System.out.println("SQL: " + sql);
            System.out.println("OPC: " + opc);
            System.out.println("Process starting");

            ProcessBuilder pb = new ProcessBuilder("java", "-jar", DATA_COLLECTOR_PATH, sql, opc);

            ProcessOutputPrinter printer = new ProcessOutputPrinter();

            pb.redirectErrorStream(true);
            Process p = pb.start();

            printer.setSqlConfigId(sql);
            printer.setOpcConfigId(opc);
            printer.setProcess(p);
            printer.start();

            processList.add(printer);
        } catch (Exception ex) {
            Logger.getLogger(main_page_servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
