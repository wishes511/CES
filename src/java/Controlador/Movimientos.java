/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;


import Modelo.Departamento;
import Modelo.Movimiento;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import persistencia.CES_movs;
import persistencia.CES_provact;


/**
 *
 * @author mich
 */
@WebServlet(name = "Movimientos", urlPatterns = {"/Movimientos"})
public class Movimientos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
        
        HttpSession objSesion = request.getSession(true);
        try {
            String usuario = (String) objSesion.getAttribute("usuario");
       String tipos = (String) objSesion.getAttribute("tipo");
    Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        String fechac =año+"-"+mes+"-"+dia;
        String horas =hora+":"+minuto;
        String uso = request.getParameter("uso");
        CES_movs bd = new CES_movs();
        if(uso.equals("proveedor")){
            String codigo=request.getParameter("codigo");
            String numero=String.valueOf(codigo.charAt(6)) +codigo.charAt(7);
            
            String prov = request.getParameter("prov");
            String prov_activo = request.getParameter("autorizada").toUpperCase();
            String depa = request.getParameter("depa");
            String area = request.getParameter("area").toUpperCase();
            CES_provact bda= new CES_provact();
            //System.out.println(codigo+"/"+numero);
            int clave_autorizado=bda.buscarprov_act_caseta(prov_activo, Integer.parseInt(prov));     
            if(clave_autorizado==0){
                clave_autorizado=bda.nuevoprov_autorizado_caseta(prov_activo, Integer.parseInt(prov));
            }
            Movimiento m = new Movimiento();
            Departamento d= new Departamento();
            d.setClaveDepartamento(Integer.parseInt(depa));
            m.setClaveUsuario(0);
            m.setClaveProveedor(Integer.parseInt(prov));
            m.setClaveAutorizado(clave_autorizado);
            m.setNombre(prov_activo);
            m.setArea(area);
            m.setDepartamento(d);
            m.setFecha(fechac);
            m.setObservaciones("");
            PrintWriter out = response.getWriter();
            out.print("<label>"+bd.nuevomov(m,horas,numero)+"</label>");
        }else if(uso.equals("invitado")){
            String codigo=request.getParameter("codigo");
            String numero=String.valueOf(codigo.charAt(6)) +codigo.charAt(7);
            
            int depa=Integer.parseInt(request.getParameter("depa"));
            String prov = request.getParameter("nombre").toUpperCase();
            String obs = request.getParameter("obs").toUpperCase();
            String area = request.getParameter("area").toUpperCase();
            PrintWriter out = response.getWriter();
            Movimiento m = new Movimiento();
            Departamento d= new Departamento();
            d.setClaveDepartamento(depa);
            m.setClaveUsuario(0);
            m.setClaveProveedor(0);
            m.setClaveAutorizado(0);
            m.setNombre(prov);
            m.setArea(area);
            m.setDepartamento(d);
            m.setFecha(fechac);
            m.setObservaciones(obs);
            out.print("<label>"+bd.nuevomov(m,horas,numero)+"</label>");
        }if(uso.equals("report")){// {uso: uso,f1:f1,f2:f2,nombre:nombre,area:area,depa:dep,tipo:tipo}
            CES_movs mov = new CES_movs();
            String f1=request.getParameter("f1");
            String f2 = request.getParameter("f2");
            String nombre=  request.getParameter("nombre");
            String area=  request.getParameter("area");
            String depa= request.getParameter("depa");
            String tipo= request.getParameter("tipo");
            PrintWriter out = response.getWriter();
            ArrayList<String> arr =new ArrayList<>();
            arr=mov.search_movs(f1, f2, nombre, area, depa, tipo);
            int cont=0;
            for(int i =0; i<arr.size();i++){
                if(cont ==7){
                    
                out.print("<tr align=\"center\">\n" +
"                  <td>"+arr.get(i-7)+"</td>\n" +
"                  <td>"+arr.get(i-6)+"</td>\n" +
"                  <td>"+arr.get(i-5)+"</td>\n" +
"                  <td>"+arr.get(i-4)+"</td>\n" +
"                  <td>"+arr.get(i-2)+" "+arr.get(i-1)+"</td>\n" +        
"                  <td>"+arr.get(i)+"</td>\n" +
"                </tr>");
                cont =0;
                }else cont++;
            
            }
            
        }
        
        } catch (SQLException ex) {
            System.out.println(ex+" error "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println(e);
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
