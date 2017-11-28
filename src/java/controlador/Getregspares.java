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
@WebServlet(name = "Getregspares", urlPatterns = {"/Getregspares"})
public class Getregspares extends HttpServlet {

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
        HttpSession objSesion = request.getSession(false);
        try {
            String usuario = (String) objSesion.getAttribute("usuario");
            String tiposs = (String) objSesion.getAttribute("tipo");
            String ids = String.valueOf(objSesion.getAttribute("i_d"));

            if (usuario != null && tiposs != null && tiposs.equals("USUARIO")) {

            } else {
                response.sendRedirect("../index.jsp");
            }

            String uso = request.getParameter("uso");
            // verificar que accion hara el servlet
            ArrayList<String> arr = new ArrayList<>();
            ArrayList<String> depa = new ArrayList<>();
            ArrayList<String> banda = new ArrayList<>();
            Avance avan = new Avance();
            Avances a = new Avances();
            Programa p = new Programa();
            String f1 = request.getParameter("f1");
            String f2 = request.getParameter("f2");
            String maq = request.getParameter("maq");
            String patt = "\\d{1,2}\\-\\d{1,2}\\-\\d{4}";
            Pattern pat = Pattern.compile(patt);
            Matcher match = pat.matcher(f1);
            if (match.matches()) {
                if (uso.equals("fechas")) {

                    int con = 0;
                    if (maq.equals("TODOS")) {
                        arr = a.searchfecha(f1, f2);
                        banda = a.getbanda(f1, f2);
                    } else {
                        maq = String.valueOf(maq.charAt(0));
                        banda = a.getbanda(f1, f2, maq);
                        arr = a.searchfecha(f1, f2, maq);
                    }
                    depa = avan.depaload(depa);

                    System.out.println("mmmm " + arr.size() + "/ banda " + banda.size());
                    while (con < 9) {
                        System.out.println("mmmmta " + con);
                        if (depa.get(con).equals("montado")) {
                            switch (banda.size()) {
                                case 6:
                                    out.print("<tr onclick=mostrarVentanas('"+depa.get(con)+"') class=ttd><td ><div class=col-sm-6>" + depa.get(con) + "</div><div class=col-sm-6>banda " + banda.get(0) + "->" + banda.get(1) + "<br>banda " + banda.get(2) + "->" + banda.get(3) + "<br>banda " + banda.get(4) + "->" + banda.get(5) + "</div> </td><td>" + arr.get(con) + "</td></tr>");
                                    break;
                                case 4:
                                    out.print("<tr onclick=mostrarVentanas('"+depa.get(con)+"') class=ttd><td><div class=col-sm-6>" + depa.get(con) + "</div><div class=col-sm-6>banda " + banda.get(0) + "->" + banda.get(1) + "<br>banda " + banda.get(2) + "->" + banda.get(3) + "</td><td>" + arr.get(con) + "</div></td></tr>");
                                    break;
                                case 2:
                                    out.print("<tr onclick=mostrarVentanas('"+depa.get(con)+"') class=ttd><td><div class=col-sm-6>" + depa.get(con) + "</div><div class=col-sm-6>banda " + banda.get(0) + "->" + banda.get(1) + "</td><td>" + arr.get(con) + "</div></td></tr>");
                                    break;

                                default:
                                    out.print("<tr onclick=mostrarVentanas('"+depa.get(con)+"') class=ttd><td>" + depa.get(con) + "</td><td>" + arr.get(con) + "</td></tr>");
                                    break;
                            }
                        } else {
                            out.print("<tr onclick=mostrarVentanas('"+depa.get(con)+"') class=ttd><a> <td >" + depa.get(con) + "</td><td>" + arr.get(con) + "</td></a></tr>");
                        }
                        con++;
                    }
                    
                    System.out.println("hecho e.e");
                }else if(uso.equals("detalle")){
                    System.out.println("Detalle");
                    int cont =0;
                String dep = request.getParameter("dep");
                depa=avan.alldepcharge(arr);
                for(int i =0;i<depa.size();i++){
                    if(depa.get(i).equals(dep)){
                        cont =i;
                        i=depa.size();
                    }
                }
                arr=a.getdetalledep(f1, f2, arr, (cont+1));
                cont=0;
                int pares =0;
                for(int i =0;i<arr.size();i++){
                    if(cont==7){
                        out.print("<tr class=ttd style=overflow:auto><td>"+arr.get(i-7)+"</td><td>"+arr.get(i-6)+"</td><td>"
                             +arr.get(i-5)+"</td><td>"+arr.get(i-4)+"</td><td>"+arr.get(i-3)+"</td><td>"
                             +arr.get(i-2)+"</td><td>"+arr.get(i-1)+"</td><td>"+arr.get(i)+"</td></tr>");
                        pares+=Integer.parseInt(arr.get(i-4));
                    cont =0;
                    }else{
                    cont++;
                    }
                }
                out.print("<tr><td>Total de pares</td><td></td><td></td><td>"+pares+"</td></tr>");
                }else if(uso.equals("completo")){
                    System.out.println("completos");
                    ArrayList<String> ar = new ArrayList<>();
                           ar= a.searchfechacompleto(f1, f2);
           int cont=0;
           int pares =0;
            out.print("<div class=\"container\" style=\"\">\n" +          
"            <div class=\" \" style=\"\">\n" +
"                <h4 class=\"h4\">Lotes completos</h4>\n" +
"                <table  id=\"tablesorter-demo\" class=\"table table-hover table-responsive table-condensed table-bordered\" style=\"overflow: scroll\">\n" +
"                    <thead class=\"redondeado\" style=\"background-color:white; \">\n" +
"                        <tr>\n" +
"                            <td>Programa</td>\n" +
"                            <td>Lote</td>\n" +
"                            <td>Estilo</td>\n" +
"                            <td>Pares</td>\n" +
"                            <td>Corrida</td>\n" +
"                            <td>Mes</td>\n" +
"                            <td>Combinacion</td>\n" +
"                        </tr>\n" +
"                    </thead>\n" +
"                    <tbody >");
                for(int i =0;i<ar.size();i++){
                    if(cont==6){
                        out.print("<tr class=ttd style=overflow:auto><td>"+ar.get(i-6)+"</td><td>"+ar.get(i-5)+"</td><td>"
                             +ar.get(i-4)+"</td><td>"+ar.get(i-3)+"</td><td>"+ar.get(i-2)+"</td><td>"
                             +ar.get(i-1)+"</td><td>"+ar.get(i)+"</td></tr>");
                        pares+=Integer.parseInt(ar.get(i-3));
                    cont =0;
                    }else{
                    cont++;
                    }
                }
                out.print("<tr><td>Total de pares</td><td></td><td></td><td>"+pares+"</td></tr>");
            out.print("</tbody> \n" +
            "                </table>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "       ");
            }
            } else {
                System.out.println("No es fecha");
            }// lotes completos
            

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
