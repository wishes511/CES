/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.Avances;

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
        String nombre = request.getParameter("nombrelog");
        String contrasena = request.getParameter("contrasenalog");
        //System.out.println("," + nombre + "," + contrasena + ",");
        boolean flag=false;
        char arr[];
        char arr1[];
        int interv =180;
         PrintWriter out = response.getWriter();
        //control de acceso
        //System.out.println("," + nombre + "," + contrasena + ",");
        if (nombre.equals(null) || contrasena.equals(null) || nombre.equals("") || contrasena.equals("")) {
                out.println("<script type=\"text/javascript\">");
                out.println("location='index.jsp';");
                out.println("</script>");
            flag =true;
        } else {
            arr = nombre.toCharArray();
            arr1 = contrasena.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == '|' || arr[i] == '\'' || arr[i] == '\"' || arr[i] == '=' || arr[i] == '!') {
                    flag=true;
                    
                out.println("<script type=\"text/javascript\">");
                out.println("location='index.jsp';");
                out.println("</script>");
                    i = arr.length;
                }
            }
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] == '|' || arr1[i] == '\'' || arr1[i] == '\"' || arr1[i] == '=' || arr1[i] == '!') {
                    flag=true;                   
                out.println("<script type=\"text/javascript\">");
                out.println("location='index.jsp';");
                out.println("</script>");
                    i = arr1.length;
                }
            }
        }
        if(flag){
                out.println("<script type=\"text/javascript\">");
                out.println("location='index.jsp';");
                out.println("</script>");
        }else{
        String tipo = "";
        // Definir variable de referencia a un objeto de tipo Usuario
        Usuario u = new Usuario();
        // Consultar Base de datos
        Avances a = new Avances();
        try {
            u = a.buscar(nombre, contrasena);
            System.out.println(u.getTipo());
            
            if (u.getTipo().equals("n")) {                
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Usuario o contrasena incorrectos');");
                out.println("location='index.jsp';");
                out.println("</script>");
            } else {
                tipo = u.getTipo();
                //System.out.println("e.e"+tipo);
                switch (tipo) {
                    case "ADMIN":
                        objSesion.setMaxInactiveInterval(interv+1000);
                        objSesion.setAttribute("usuario", nombre);
                        objSesion.setAttribute("tipo", tipo);
                        objSesion.setAttribute("i_d", u.getId_usuario());
                        request.setAttribute("usuario1", u);
                        response.sendRedirect("admin/index.jsp");
                        break;
                    case "BASICO":
                    case "MEDIOBASICO":
                    case "PREINTERMEDIO":
                        //usuarios de planta
                        System.out.println(tipo+"/Planta");
                        objSesion.setMaxInactiveInterval(interv);
                        objSesion.setAttribute("usuario", nombre);
                        objSesion.setAttribute("tipo", tipo);
                        objSesion.setAttribute("i_d", u.getId_usuario());
                        response.sendRedirect("planta/index.jsp");
                        break;
                    case "USUARIO":
                        //usuario normal
                        ArrayList<String> array1= new ArrayList<>();
                        objSesion.setMaxInactiveInterval(interv+860);
                        objSesion.setAttribute("usuario", nombre);
                        objSesion.setAttribute("tipo", tipo);
                        objSesion.setAttribute("i_d", u.getId_usuario());
                        objSesion.setAttribute("cap",array1);
                        System.out.println(tipo + "pana");
                        request.setAttribute("usuario1", u);
                        response.sendRedirect("usuario/index.jsp");
                        break;
                    default:
                        // capturista
                        ArrayList<String> array= new ArrayList<>();
                        System.out.println("capturista");
                       // objSesion.setMaxInactiveInterval(interv+860);
                        objSesion.setAttribute("usuario", nombre);
                        objSesion.setAttribute("tipo", tipo);
                        objSesion.setAttribute("i_d", u.getId_usuario());
                        objSesion.setAttribute("cap",array);
                        request.setAttribute("usuario1", u);
                        response.sendRedirect("capturador/index.jsp");
                        break;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex+" error "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        

        // Redireccionar a una pagina de respuesta
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
