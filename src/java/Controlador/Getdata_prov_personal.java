/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
import persistencia.CES_prov;
import persistencia.CES_provact;


/**
 *
 * @author mich
 */
@WebServlet(name = "Getdata_prov_personal", urlPatterns = {"/Getdata_prov_personal"})
public class Getdata_prov_personal extends HttpServlet {

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
        String uso = request.getParameter("uso");
        CES_provact bd = new CES_provact();
        if(uso.equals("buscar")){
            String prov_field = request.getParameter("activo");
            ArrayList<String> arr= bd.buscarprov_act_idprov((prov_field));
             PrintWriter out = response.getWriter();
            if(!arr.isEmpty()){
                int cont=0;
                out.print("<label>Persona Autorizada :</label><select id=autorizada class=form-control onchange=saltop()><option></option>");
                for(int i =0;i<arr.size();i++){
                if(cont==1){
                    out.print("<option value="+arr.get(i)+">"+arr.get(i)+"</option>");
                    cont=0;
                }else cont++;
            }
                out.print("</select>");
            }
            
        }else if(uso.equals("getfield_activos")){
        PrintWriter out = response.getWriter();
        autosearch_activo(out);
        }else if(uso.equals("buscar_activo")){
            String prov_field = request.getParameter("proveedor");
            String prov_activo = request.getParameter("activo");
            String metodo="";
            String imag="";
            if(prov_activo.equals("A")){
                prov_activo="1";
                metodo="down_prov_a";
                imag="down.png";
            }else {
                prov_activo="0";
                metodo="up_prov_a";
                imag="up.png";
            }
            
            ArrayList<String> arr=bd.buscarprov_autorizado(prov_field, prov_activo);
             PrintWriter out = response.getWriter();
            if(!arr.isEmpty()){
                int cont=0;
            for(int i =0;i<arr.size();i++){
                if(cont==2){
                    out.print("<tr align=\"center\">\n" +
"                  <td>"+arr.get(i-1)+" ("+arr.get(i)+")</td>\n" +
   //7     "          <td>"+arr.get(i)+"</td>\n" +
"                  <td><a class=\"btn-block\" onclick=\"delete_prov_a('"+arr.get(i-2)+"')\"><img src=\"../images/delete.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                  <td><a class=\"btn-block\" onclick=\"mod_prov_a('"+arr.get(i-2)+"')\"><img src=\"../images/modificar.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                  <td><a class=\"btn-block\" onclick=\""+metodo+"('"+arr.get(i-2)+"')\"><img src=\"../images/"+imag+"\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                </tr>");
                    cont=0;
                }else cont++;
            }
            }
        }else if(uso.equals("nuevo")){
            int cont=0;
        CES_prov prov = new CES_prov();    
        ArrayList<String> arr = new ArrayList<>();    
        PrintWriter out = response.getWriter();
        arr=prov.buscarprov(arr);
        out.print("<div align=center class=container-fluid><div class=col-sm   align=center><input type=text class=form-control id=prov_new1 placeholder=\"Nombre personal\"><br><select class=form-control id=proveedor><option></option>");
            for (int i = 0; i < arr.size(); i++) {
            if (cont == 1) {
                out.print("<option value=" + arr.get(i - 1) + ">" + arr.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        } //11999901$11999902/11999903+11999904%11999905011999906111999907211999908311999909411999910$
        out.print("</select><br><button class=btn btn-success onclick=nuevo_prov_autorizado()>Ingresar Nueva persona</button><br><div id=response_nprov></div></div></div>");
        }else if(uso.equals("nuevo_row")){
            String id=request.getParameter("id_prov");
            String name_prov=request.getParameter("prov").toUpperCase();
            //System.out.println(id+"/"+name_prov);
            CES_provact prov = new CES_provact();            
            String mensaje="";
            String coloresp="";
            if(prov.buscarprov_act(name_prov,Integer.parseInt(id))){
                mensaje="Ya existe el nombre de este proveedor, intentelo de nuevo";                
                coloresp="non-input";
            }else{
                prov.nuevoprov_autorizado(name_prov,Integer.parseInt(id));
                mensaje="Nuevo proveedor agregado";
                coloresp="non-inputok";
            }
            PrintWriter out = response.getWriter();
            out.print("<br><label class="+coloresp+">"+mensaje+"</label>");
            
        }else if(uso.equals("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_provact db = new CES_provact();
            PrintWriter out = response.getWriter();
            if(db.buscarmov_prov_a(id)){
            out.print("No puedes eliminar un personal que ya ha hecho movimientos");
            }else{
            db.borrarprov_a(id);
            out.print("Eliminacion exitosa");
            }
               
        }else if(uso.equals("baja")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_provact db = new CES_provact();
            db.bajaprov_a(id);
            PrintWriter out = response.getWriter();
            out.print("Baja exitosa");   
        }else if(uso.equals("altas")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_provact db = new CES_provact();
            db.altaprov_a(id);
            PrintWriter out = response.getWriter();
            out.print("Alta exitosa");
        }
        
        } catch (SQLException ex) {
            System.out.println(ex+" error "+ex.getMessage());
            PrintWriter out = response.getWriter();
            out.print("Codigo 4:<br> "+ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Getdata_prov_personal.class.getName()).log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.print("Codigo 4.1:<br> "+ex);
        } catch (Exception ex) {
            System.out.println(ex+" error "+ex.getMessage());
            PrintWriter out = response.getWriter();
            out.print("Codigo 4.2:<br> "+ex);
        }
    }
    private void autosearch_activo(PrintWriter out){
        out.print("" +
"          <h1>Proveedores personal</h1>\n" +
"          <section class=\"row text-center placeholders\" id=section_prov align=center>\n" +
"            <div class=\"col-6 col-sm-3 placeholder\">\n" +
"              <h4>Busqueda</h4>\n" +
"              <input type=\"text\" class=\"form-control\" id=\"search_prov\" onkeypress=\"autosearch_provs_activos()\">\n" +
"            </div>\n" +
"              <div class=\"col-6 col-sm-3 placeholder\">\n" +
"                  <br><div class=\"text-muted\">Activos</div><input type=\"radio\" name=\"gg\" id=\"gg\" value=\"A\" checked=\"checked\" />\n" +
"              </div>\n" +
"              <div class=\"col-6 col-sm-3 placeholder\">\n" +
"                  <br><div class=\"text-muted\">Inactivos</div><input type=\"radio\" name=\"gg\" id=\"gg1\" value=\"I\" />\n" +
"              </div>\n" +
"          </section>\n" +
"          <div id=table_prov class=\"table-responsive\">\n" +
"            <table class=\"table table-striped\">\n" +
"              <thead>\n" +
"                <tr align=\"center\">\n" +
"                  <td>Nombre</td>\n" +
"                  <td>Eliminar</td>\n" +
//"                  <td>Modificar</td>\n" +
"                  <td>Mover</td>\n" +
"                </tr>\n" +
"              </thead>\n" +
"              <tbody id=\"body_table\">\n" +
"              </tbody>\n" +
"            </table>\n" +
"          </div>\n" +
"        ");
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
