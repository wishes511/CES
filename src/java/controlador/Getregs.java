/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.Programa;
import persistencia.Avances;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gateway1
 */
@WebServlet(name = "Getregs", urlPatterns = {"/Getregs"})
public class Getregs extends HttpServlet {
     Calendar fecha = Calendar.getInstance();
    int year = fecha.get(Calendar.YEAR);
    int mes = fecha.get(Calendar.MONTH) + 1;
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    String fechac = dia + "-" + mes + "-" + year;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.o
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
private String codigo(String estilo){
     char [] charestilo = estilo.toCharArray();
     char [] arr = {'0','0','0','0','0','0'};
     int cont=charestilo.length-1;
     int cont1=arr.length-1;
     for(int i = cont1;i>= 0;i--){
         if(0<=cont){
             arr[i]=charestilo[cont];
             cont--;
         }else{
         cont--;
         }
     }
     String cod="";
     for(int i =0;i<=cont1;i++){
         cod=cod+arr[i];
     }
    return cod;
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
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes1 = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String fechac = año + "/" + mes1 + "/" + dia;
         HttpSession objSesion = request.getSession(true);
        char arr [] =new char[5];
//i_d

        String usuario = (String) objSesion.getAttribute("usuario");
        String tiposs = (String) objSesion.getAttribute("tipo");
        System.out.println(tiposs);
        if (usuario != null && tiposs != null ) {
            if(tiposs.equals("INTERMEDIO") || tiposs.equals("USUARIO") || tiposs.equals("ADMIN")){
            }else{
                response.sendRedirect("../index.jsp");
            }
        } else {
            response.sendRedirect("../index.jsp");
        }
        try {
        String ids = String.valueOf(objSesion.getAttribute("i_d"));
            PrintWriter out = response.getWriter();
            // tomar datos del html
            String f = request.getParameter("f");
            String f1 = request.getParameter("f1");
            String f2 = request.getParameter("f2");
            String f3 = request.getParameter("f3");
            String f4 = request.getParameter("f4");
            String f5 = request.getParameter("f5").toUpperCase();
            String f6 = request.getParameter("f6");
            String f8 = request.getParameter("f8");
            String uso = request.getParameter("uso");
            System.out.println("Inicio "+f+" uso " +uso);
                ArrayList<String> lista;
                 lista = (ArrayList<String>) objSesion.getAttribute("cap");
            
            // verificar que accion hara el servlet
            if (uso.equals("nuevo")) {// nuevo programa
                ArrayList<String> listas= new ArrayList<>();
                objSesion.setAttribute("cap", listas);
                Avances a = new Avances();
                Programa p = new Programa();
                p.setPrograma(Integer.parseInt(f));
                p.setLote(Integer.parseInt(f1));
                p.setEstilo(Integer.parseInt(f2));
                p.setPares(Integer.parseInt(f3));
                p.setCorrida(f4);
                p.setCombinacion(f5);
                p.setMes(Integer.parseInt(f6));
                p.setFecha(fechac);
                p.setCodigo(codigo(f1));
                p.setYear(year);
                if(regularexp(f,f1,f2,f3,f4,f5)){
                    if(a.checkprograma(p)){
                        out.print("<div class=container-fluid><div class=container><label class=ln>Registro repetido, Favor de modificar el registro anterior</label></div></div>");
                    }else{
                     a.nuevoprog(p);//ejecutar insercion
                out.print("<div class=container-fluid><div class=container><div class=row espacios-lg fondos jumbis><div class=row><div class=col-sm-2> "
                        + "<label class=ln>Programa</label><input class=form-control type=text name=programa id=programa disabled value="+p.getPrograma()+"></div></div><div class=row>"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Lote</label><input class=\"form-control\" type=\"text\" name=\"lote\" id=\"lote\" disabled value="+p.getLote()+">\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Estilo</label><input class=\"form-control\" type=\"text\" name=\"estilo\" id=\"estilo\" disabled value="+p.getEstilo()+">\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Pares</label><input class=\"form-control\" type=\"text\" name=\"pares\" id=\"pares\" disabled value="+p.getPares()+">\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Corrida</label><input class=\"form-control\" type=\"text\" name=\"corrida\" id=\"corrida\" disabled value="+p.getCorrida()+">\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Combinacion</label><input class=\"form-control\" type=\"text\" name=\"combinacion\" id=\"combinacion\" onchange=\"salto3()\" disabled value="+p.getCombinacion()+">\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Codigo</label><input class=\"form-control\" type=\"text\" name=\"codigo\" disabled value="+p.getCodigo()+"><br><br>\n"
                        + "</div>\n"
                        + "</div>\n"
                        + "</div></div></div>");
                    }
                }else{
                out.print("Insercion cancelada, Revise sus datos");
                }


            }else if(uso.equals("buscar")){//busqueda de lote
                 
                ArrayList<String> array = new ArrayList<>();
                Avances a = new Avances(); 
                int cont =0;
                if(explote(f1)){
                    objSesion.setAttribute("cap",a.getprog(Integer.parseInt(f1),Integer.parseInt(f6)));
                    array=a.getallprog(array, f1,Integer.parseInt(f6));
                    if(array.isEmpty()){
                        System.out.print("Vacio");
                   }else{
                        System.out.print("prog total"+array.size());
                    for(int i =0;i<(array.size());i++){
                        System.out.print("i="+i+"arr="+array.get(i));
                        if(cont==8){
                           
                          out.print("<div class=container><div class=row  fondos jumbis align=center><div align=center class=row ><div class=col-sm-2 > "
                        + "<label class=ln>Programa</label><br><label class=ln>"+array.get(i-8)+"</div></div><div class=row>"
                        + "<div class=\"col-sm-1 espacio-md-down\">\n"
                        + "<label class=\"ln\">Lote</label><br><label class=ln form-control>"+array.get(i-7)+"</label>\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-1 \">\n"
                        + "<label class=\"ln\">Estilo</label><br><label class=ln>"+array.get(i-6)+"</label>\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-1\">\n"
                        + "<label class=\"ln\">Pares</label><br><label class=ln>"+array.get(i-5)+"</label>\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Combinacion</label><br><label class=ln>"+array.get(i-4)+"</label>\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2 \">\n"
                        + "<label class=\"ln\">Corrida</label><br><label class=ln>"+array.get(i-3)+"</label>\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-1 \">\n"
                        + "<label class=\"ln\">mes</label><br><label class=ln>"+array.get(i-2)+"</label><br><br>\n"
                        + "</div>\n"
                        + "<div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Status</label><br><label class=ln>"+array.get(i-1)+"</label><br><br>\n"
                        + "</div><div class=\"col-sm-2\">\n"
                        + "<label class=\"ln\">Fecha</label><br><label class=ln>"+array.get(i)+"</label><br><br>\n"
                        + "</div>\n"
                        + "</div>\n"
                        + "</div></div>");
                        cont=0;
                        }else{
                        cont++;
                        }
                    }
                    }
                    
                }
                
            }else if(uso.equals("modificar")){
                System.out.print("Modificar "+f+" ");
                if(regularexp(f,f1,f2,f3,f4,f5)){
                int id_prod=Integer.parseInt(request.getParameter("idprod"));
                ArrayList<String> listas=new ArrayList<String>();
                System.out.println(id_prod);
                Avances a = new Avances();
                Programa p = new Programa();
                p.setPrograma(Integer.parseInt(f));
                p.setLote(Integer.parseInt(f1));
                p.setEstilo(Integer.parseInt(f2));
                p.setPares(Integer.parseInt(f3));
                p.setCorrida(f4);
                p.setCombinacion(f5);
                p.setMes(Integer.parseInt(f6));
               
                p.setCodigo(codigo(f1));
                p.setId(id_prod);
                a.modiprogram(p);
                objSesion.setAttribute("cap", listas);
                }else{
                System.out.println("Nada :C");
                }
            }else if(uso.equals("buscarp")){
                Avances a = new Avances();
                if(a.buscarprogram(f,mes1)){
                    System.out.println("entre a ok");
                    out.print("ok");
                }else{
                    out.print("nel");
                }
            //// aqui me quede para 1 nov 17 :)
            }else if(uso.equals("buscarpm")){
            Avances a = new Avances();
            int mes=0;
            if(a.buscarprogramm(f)!=0){
            mes =a.buscarprogramm(f);
            out.print("<label class=\"ln\">Mes</label><br><select id=\"mes\" name=\"mes\" onclick=\"presalto1()\" class=\"form-control\" value=\"<%=pro.getMes()%>\"><option>"+mes+"</option></select>");
            }else{}
            }else if(uso.equals("combupdate")){
                Avances a = new Avances();
                lista=a.getcoms();
                for(int i =0;i<lista.size();i++){
                out.println("<option>"+lista.get(i)+"</option>");
                }
            }else{
            response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            System.out.println("trycatch " +e.getMessage()+"/"+e);
                    Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);

            out.print("<label class=l1>NO SE PUDO modificar, favor de revisar sus datos</label>");
            //response.sendRedirect("index.jsp");
            
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

    public boolean regularexp(String prog, String lote, String estilo, String pares, String corrida, String combinacion){
        boolean flag =false;
       // String patt="\\d{1,2}\\-\\d{1,2}\\-\\d{4}";
        String patp="\\d{1,3}";
        String patl="\\d{1,6}";
        String pate="\\d{1,5}";
        String patpar="\\d{1,3}";
        String patc="\\d{1,2}";

               
               Pattern pat1 =Pattern.compile(patp);
               Matcher match1 = pat1.matcher(prog);
               Pattern pat2 =Pattern.compile(patl);
               Matcher match2 = pat2.matcher(lote);
               Pattern pat3 =Pattern.compile(pate);
               Matcher match3 = pat3.matcher(estilo);
               Pattern pat4 =Pattern.compile(patpar);
               Matcher match4 = pat4.matcher(pares);
               Pattern pat5 =Pattern.compile(patc);
               Matcher match5 = pat5.matcher(corrida);

               if( match1.matches() && match2.matches() && match3.matches()
                       && match4.matches() && match5.matches()){
               flag=true;
               }
        return flag;
    }
    public boolean explote(String lote){
    boolean flag =false;
        String patl="\\d{1,6}";
    Pattern pat2 =Pattern.compile(patl);
    Matcher match2 = pat2.matcher(lote);
    if(match2.matches()){
        flag=true;
     System.out.println("Lote bien :D");   
    }
    return flag;
    }
    
}
