package seacon.datacollectorweb;

import entity.ProcessOutputPrinter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new process (the desktop monitoring applications), and assigns it to a ProcessOutputWriter
 * @author Peter
 */
public class process_output_display extends HttpServlet {

    private final String DATA_COLLECTOR_PATH = "C:\\Users\\Peter\\Documents\\NetBeansProjects\\DataCollectorDesktop\\target\\DataCollector-jar-with-dependencies.jar"; // Path of the application, testing purposes only
    
    private ProcessOutputPrinter printer;
    private Process p;
        
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
           
           try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Process</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Process started</h1>");
                
                out.println("<form name=\"stop_form\" method=\"post\" action=\"process_output_display\">");
                out.println("<input type=\"submit\" name=\"stop\" value=\"Stop\"><br> ");
                out.println("</table>");
           }
        
        try {
            processRequest(request, response);
            String sql = (String)request.getSession().getAttribute("sql");
            String opc = (String)request.getSession().getAttribute("opc");
            
            System.out.println("SQL: " + sql);
            System.out.println("OPC: " + opc);
            
            System.out.println("Process starting");
            
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", DATA_COLLECTOR_PATH, sql, opc);
            
            printer = new ProcessOutputPrinter();

            pb.redirectErrorStream(true);
            p = pb.start();
            
            printer.setProcess(p);
            printer.start();
            
        } catch (Exception ex) {
            Logger.getLogger(process_output_display.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String redirect = "main_page";
        
        if(request.getParameter("stop") != null) {
            System.out.println("Stopping data collector...");
            printer.terminate();
        }
        response.sendRedirect(redirect);
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
