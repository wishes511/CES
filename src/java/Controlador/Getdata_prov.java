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


/**
 *
 * @author mich
 */
@WebServlet(name = "Getdata_prov", urlPatterns = {"/Getdata_prov"})
public class Getdata_prov extends HttpServlet {

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
    if (usuario != null && tipos != null && (tipos.equals("ADMIN")||tipos.equals("USUARIO")) ) {// verificar si el tipo de usuario esta en session
    }else response.sendRedirect("../index.jsp");
    
        String uso = request.getParameter("uso");
        CES_prov bd = new CES_prov();
        if(uso.equals("buscar")){// accion para el string de uso(buscar)
            String prov_field = request.getParameter("proveedor");
            String prov_activo = request.getParameter("activo");
            if(prov_activo.equals("A")){// comprobar si es una proveedor activo
                prov_activo="1";
                ArrayList<String> arr= bd.buscarprov(prov_field, prov_activo);// busca y almacenaen una lista los proveeedores activos
             PrintWriter out = response.getWriter();
            if(!arr.isEmpty()){// verifica si la lista esta vacia
                int cont=0;
            for(int i =0;i<arr.size();i++){// retorno del contenido de la tabla con datos hacia la pagina
                if(cont==1){// cont para saber elnumero 
                    out.print("<tr align=\"center\">\n" +
"                  <td>"+arr.get(i)+"</td>\n" +
"                  <td><a onclick=\"delete_prov('"+arr.get(i-1)+"')\"><img src=\"../images/delete.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
//"                  <td><a onclick=\"mod_prov('"+arr.get(i-1)+"')\"><img src=\"../images/modificar.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                  <td><a onclick=\"down_prov('"+arr.get(i-1)+"')\"><img src=\"../images/down.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                </tr>");
                    cont=0;
                }else cont++;
            }
            }
            }else {
            prov_activo="0";
                ArrayList<String> arr= bd.buscarprov(prov_field, prov_activo);//busca proveedores inactivos
             PrintWriter out = response.getWriter();
            if(!arr.isEmpty()){
                int cont=0;
            for(int i =0;i<arr.size();i++){// retorno del contenido de la tabla con datos hacia la pagina
                if(cont==1){
                    out.print("<tr align=\"center\">\n" +
"                  <td>"+arr.get(i)+"</td>\n" +
"                  <td><a onclick=\"delete_prov('"+arr.get(i-1)+"')\"><img src=\"../images/delete.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
//"                  <td><a onclick=\"mod_prov('"+arr.get(i-1)+"')\"><img src=\"../images/modificar.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                  <td><a onclick=\"up_prov('"+arr.get(i-1)+"')\"><img src=\"../images/up.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                </tr>");
                    cont=0;
                }else cont++;
            }
            }
            }
            
            
        }else if(uso.equals("nuevo")){// despliega interfaz de un nuevo prov
            PrintWriter out = response.getWriter();
            out.print("<div align=center class=container-fluid><div class=col-sm   align=center><input type=text class=form-control id=prov_new1 "
                    + "placeholder=\"Nombre del Proveedor\" onchange=nuevo_prov()><br><button class=btn btn-success onclick=nuevo_prov()>Ingresar Nuevo Proveedor</button><br><div id=response_nprov></div></div></div>");
        }else if(uso.equals("nuevo_row")){
            String name_prov=request.getParameter("prov").toUpperCase();
            CES_prov prov = new CES_prov();            
            String mensaje="";
            String coloresp="";
            if(prov.buscarprov(name_prov)){
                mensaje="Ya existe el nombre de este proveedor, intentelo de nuevo";                
                coloresp="non-input";
            }else{
                prov.nuevoprov(name_prov);
                mensaje="Nuevo proveedor agregado";
                coloresp="non-inputok";
            }
            PrintWriter out = response.getWriter();
            out.print("<br><label class="+coloresp+">"+mensaje+"</label>");
        }else if(uso.equals("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_prov db = new CES_prov();
            PrintWriter out = response.getWriter();
            if(db.buscarmov_prov(id)){
            out.print("No puedes Borrar un proveedor que ya haya hecho movimientos");
            }else{
            db.borrarprov(id);
            out.print("Eliminacion exitosa"); 
            }
              
        }else if(uso.equals("baja")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_prov db = new CES_prov();
            db.bajaprov(id);
            PrintWriter out = response.getWriter();
            out.print("Baja exitosa");   
        }else if(uso.equals("alta")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_prov db = new CES_prov();
            db.altaprov(id);
            PrintWriter out = response.getWriter();
            out.print("Alta exitosa");
        }
        
        } catch (SQLException ex) {
            System.out.println(ex+" error "+ex.getMessage());
            PrintWriter out = response.getWriter();
            out.print("Codigo 3: No se puede eliminar proveedor debido a que ya se hicieron movimientos<br> "+ex);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Getdata_prov.class.getName()).log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.print("Codigo 3.1: No se encuentra algun archivo, llame a un administrador<br> "+ex);
        }catch(Exception e){
            PrintWriter out = response.getWriter();
            out.print("Codigo 3.2 Error al procesar datos<br> "+e);
        
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
