/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.Avance;
import Modelo.Programa;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.Avances;

/**
 *
 * @author gateway1
 */
@WebServlet(name = "Getstopairs", urlPatterns = {"/Getstopairs"})
public class Getstopairs extends HttpServlet {

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
        response.sendRedirect("../index.jsp");
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
        processRequest(request, response);
        PrintWriter out = response.getWriter();
        HttpSession objSesion = request.getSession(true);
        try {
            String usuario = (String) objSesion.getAttribute("usuario");
            String tiposs = (String) objSesion.getAttribute("tipo");
            String ids = String.valueOf(objSesion.getAttribute("i_d"));

            if (usuario != null && tiposs != null && (tiposs.equals("USUARIO") || tiposs.equals("ADMIN"))) {

            } else {
                response.sendRedirect("../index.jsp");
            }

            String uso = request.getParameter("uso");
            // verificar que accion hara el servlet
            ArrayList<String> arr = new ArrayList<>();
            Avance avan = new Avance();
            Avances a = new Avances();
            Programa p = new Programa();
            String f1 = request.getParameter("f1");
            String f2 = request.getParameter("f2");
            String patt = "\\d{1,2}\\-\\d{1,2}\\-\\d{4}";
            Pattern pat = Pattern.compile(patt);
            Matcher match = pat.matcher(f1);
            if (match.matches()) {
                
            } else {
                System.out.println("No es fecha");
                response.sendRedirect("../index.jsp");
            }// lotes detenidos
            
            arr=a.searchstoppair(f1, f2,arr);
            int cont =0;
            for(int i =0;i<arr.size();i++){
                if(cont==5){                    
                out.print("<tr class=ttd style=overflow:auto><td>"+arr.get(i-4)+"</td><td>"+arr.get(i-3)+"</td><td>"
                             +arr.get(i-2)+"</td><td>"+arr.get(i-1)+"</td><td>"+arr.get(i)+"</td></tr>");
                    cont=0;
                }else{
                cont++;
                }
            }

//                
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
            response.sendRedirect("../index.jsp");

        } catch (Exception e) {
            System.out.println(e);
            response.sendRedirect("../index.jsp");
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
    }// </editor-fold>

}
