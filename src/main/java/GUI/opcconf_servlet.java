package GUI;

import DAL.DataAccess;
import BE.OPCConf;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create a form for inputting OPC server values
 * @author Peter
 */
@WebServlet(name = "opcconf_servlet", urlPatterns = {"/opcconfig"})
public class opcconf_servlet extends HttpServlet {
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
        RequestDispatcher view = request.getRequestDispatcher("opcconfig.jsp");
        view.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String confName = request.getParameter("name");
        String url = request.getParameter("url");
        String anon = request.getParameter("anonymous");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean allow_anonymous = anon != null;
        
        if (!allow_anonymous && (username == null || password == null)) {
            throw new IllegalArgumentException("Only anonymous access allowed without a name and a password!");
        }
        
        if (allow_anonymous) {
            username = "N/A";
            password = "N/A";
        }
        
        OPCConf conf = new OPCConf();
        conf.setName(confName);
        conf.setAnonymous(allow_anonymous);
        conf.setUserName(username);
        conf.setPassword(password);
        conf.setUrl(url);
        
        dataAccess.saveOPCConf(conf);
        
        response.sendRedirect("main");
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
