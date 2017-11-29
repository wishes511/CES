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
@WebServlet(name = "Getregslote", urlPatterns = {"/Getregslote"})
public class Getregslote extends HttpServlet {

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
       response.sendRedirect("index.jsp");
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
        if (usuario != null && tiposs != null ) {
            if(tiposs.equals("USUARIO") || tiposs.equals("ADMIN")){
            }else
                response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
        
           // PrintWriter out = response.getWriter();
            // tomar datos del html
            String f1 = request.getParameter("f1");
            String uso = request.getParameter("uso");
            // verificar que accion hara el servlet
            if (uso.equals("check")) {
               
                Avance avan = new Avance();
                Avances a = new Avances();
                Programa p = new Programa();
                p.setLote(Integer.parseInt(f1));
                 ArrayList<String> arr =a.getprog(Integer.parseInt(f1));
                //p.setFechae(f7);
                avan= a.getfechas(a.getprog(Integer.parseInt(f1)));
                if(arr.isEmpty()){
                
                }else{
                out.println("<div class=fondowhite><div class=row><div class=\"col-sm-2\">\n" +
"                                <label class=\"ln\">Programa</label><label class=\"form-control\">"+arr.get(0)+"</label>\n" +
"                            </div>\n" +
"                        <div class=\"row\">\n" +
"                            <div class=\"col-sm-2\">\n" +
"                                 <label class=\"ln\">Estilo</label><br><label >"+arr.get(1)+"</label>\n" +
"                            </div>\n" +
"                            <div class=\"col-sm-2\">\n" +
"                               <label class=\"ln\">Pares</label> <br><label >"+arr.get(2)+"</label>\n" +
"                            </div>\n" +
"                            <div class=\"col-sm-2\">\n" +
"                                <label class=\"ln\">Corrida</label><br>\n" +
"                                <label >"+arr.get(4)+"</label>\n" +
"                               </div>\n" +
"                            <div class=\"col-sm-2\">\n" +
"                                <label class=\"ln\" >Combinacion</label><br>\n" +
"                                <label >"+arr.get(3)+"</label>\n" +
"                             </div>\n" +
"                             <div class=\"col-sm-1\">\n" +
"                                 <label class=\"ln\">Mes</label><br>\n" +
"                                 <label >"+arr.get(5)+"</label>\n" +
"                            </div>\n" +
"                        </div></div>");
                out.print("<br><div class=\"row\">\n" +
"                                     <div class=col-sm-offset-4><div class=\"col-sm-7\">\n" +
"                                      <label class=l1 lateral>  Status: "+avan.getStatus()+"</label><br>\n" +
"                                 </div></div>\n" +
"                                 </div>\n" +
"                                 <div class=\"row\">\n" +
"                                     <div ><div class=\"col-sm-4 spacetop\"><div class=redfont>\n" +
"                                         <label class=ln>Corte : "+avan.getFechacorte()+"</label><br>\n" +
"                                         <label class=ln>Precorte: "+avan.getFechaprecorte()+"</label><br>\n" +
"                                         <label class=ln>Pespunte: "+avan.getFechapespunte()+"</label><br>\n" +
"                                 </div></div>\n" +
"                                     <div class=\"col-sm-4 spacetop\"><div class=redfont>\n" +
"                                      <label class=ln>Deshebrado : "+avan.getFechadeshebrado()+"</label><br>\n" +
"                                         <label class=ln>Ojillado: "+avan.getFechaojillado()+"</label><br>\n" +
"                                         <label class=ln>Inspeccion: "+avan.getFechainspeccion()+"</label><br>\n" +
"                                 </div></div>\n" +
"                                     <div class=\"col-sm-4 spacetop\"><div class=redfont>\n" +
"                                      <label class=ln>Preacabado: "+avan.getFechapreacabado()+"</label><br>\n" +
"                                         <label class=ln>Montado: "+avan.getFechamontado()+"</label><br>\n" +
"                                         <label class=ln>PT: "+avan.getFechapt()+"</label><br>\n" +
"                                 </div></div>\n" +
"                                 </div></div></div>");

            }}
        }catch(NullPointerException ex){
            System.out.println(ex.getMessage());
             response.sendRedirect("../index.jsp");
        
        }catch(Exception e){
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
