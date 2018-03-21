/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.CES;


/**
 *
 * @author mich
 */
@WebServlet(name = "Validar", urlPatterns = {"/Validar"})
public class Validar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession objSesion = request.getSession(true);
        try {
        String nombre = request.getParameter("usu");
        String contrasena = request.getParameter("pass");
        //System.out.println("," + nombre + "," + contrasena + ",");
        boolean flag=false;
        int interv =180;
         PrintWriter out = response.getWriter();
        //control de acceso
        if (nombre.equals(null) || contrasena.equals(null) || nombre.equals("") || contrasena.equals("")) {
                out.println("<script type=\"text/javascript\">");
                out.println("location='index.jsp';");
                out.println("</script>");
            flag =true;
        }
        if(regularexp(nombre,contrasena) && flag){
            System.out.println("entro aregular");
                out.println("<script type=\"text/javascript\">");
                out.println("location='index.jsp';");
                out.println("</script>");
        }else{
        
        // Definir variable de referencia a un objeto de tipo Usuario
        String tipo = "";
        // Consultar Base de datos
        CES a = new CES();
        
            tipo = a.buscaru(nombre, contrasena);            
            if (tipo.equals("n")) {                
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Usuario o contrasena incorrectos');");
                out.println("location='index.jsp';");
                out.println("</script>");
            } else {
                switch (tipo) {
                    case "ADMIN":
                        objSesion.setMaxInactiveInterval(interv+10000);
                        objSesion.setAttribute("usuario", nombre);
                        objSesion.setAttribute("tipo", tipo);
                        response.sendRedirect("usuario/index.jsp");
                        break;
                    case "USUARIO":
                        //usuario normal
                        objSesion.setMaxInactiveInterval(interv+1000);
                        objSesion.setAttribute("usuario", nombre);
                        objSesion.setAttribute("tipo", tipo);
                        response.sendRedirect("usuario/index.jsp");
                        break;
                    default:
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Usuario o contrasena incorrectos');");
                        out.println("location='index.jsp';");
                        out.println("</script>");
                        break;
                }
            }   
        }
        } catch (SQLException ex) {
            System.out.println(ex+" error "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Redireccionar a una pagina de respuesta

        // Redireccionar a una pagina de respuesta
    }
    private boolean regularexp(String name, String pass){
        boolean flag =true;
       // String patt="\\d{1,2}\\-\\d{1,2}\\-\\d{4}";
        String patp="[a-zA-Z]+";               
               Pattern pat1 =Pattern.compile(patp);
               Matcher match1 = pat1.matcher(name);
               Pattern pat2 =Pattern.compile(patp);
               Matcher match2 = pat2.matcher(pass);
               if( match1.matches() && match2.matches()){
               flag=false;
               }
        return flag;
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
        processRequest(request, response);
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
