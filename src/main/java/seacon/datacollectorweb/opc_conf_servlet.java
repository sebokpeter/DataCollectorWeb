/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seacon.datacollectorweb;

import DAL.DataAccess;
import entity.OPCConf;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peter
 */
@WebServlet(name = "opc_conf_servlet", urlPatterns = {"/opc_conf_servlet"})
public class opc_conf_servlet extends HttpServlet {
    
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
            out.println("<title>OPC configuration</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>OPC configuration</h1>");
            out.println("<form name=\"opc_form\" method=\"post\" action=\"opc_conf_servlet\">");
            out.println("Name:<input type=\"text\" name=\"conf_name\">");
            out.println("OPC URL:<input type=\"text\" name=\"url\">");
            out.println("Anonymous access:<input type=\"checkbox\" name=\"anonymous\">");
            out.println("Username:<input type=\"text\" name=\"name\">");
            out.println("Password:<input type=\"password\" name=\"password\">");
            out.println("<input type=\"submit\" value=\"Send\"/>");
            out.println("</form>");
            out.println("</body>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String confName = request.getParameter("conf_name");
        String url = request.getParameter("url");
        String anon = request.getParameter("anonymous");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        
        boolean allow_anonymous = anon != null;
        
        if (!allow_anonymous && (name == null || password == null)) {
            throw new IllegalArgumentException("Only anonymous access allowed without a name and a password!");
        }
        
        if (allow_anonymous) {
            name = "N/A";
            password = "N/A";
        }
        
        OPCConf conf = new OPCConf();
        
        conf.setName(confName);
        conf.setAnonymous(allow_anonymous);
        conf.setUserName(name);
        conf.setPassword(password);
        conf.setUrl(url);
        
        dataAccess.saveOPCConf(conf);
        
        response.sendRedirect("main_page");
        
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
