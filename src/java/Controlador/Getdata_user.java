/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Departamento;
import Modelo.TipoUsuario;
import Modelo.Usuario;
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
import persistencia.CES;
import persistencia.CES_depa;
import persistencia.CES_prov;


/**
 *
 * @author mich
 */
@WebServlet(name = "Getdata_user", urlPatterns = {"/Getdata_user"})
public class Getdata_user extends HttpServlet {

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
        HttpSession objSesion = request.getSession(false);
        try {
            String usuario = (String) objSesion.getAttribute("usuario");
        String tipos = (String) objSesion.getAttribute("tipo");
        if (usuario != null && tipos != null && (tipos.equals("ADMIN")||tipos.equals("USUARIO")||tipos.equals("ADMINPROV")) ) {
        }else response.sendRedirect("../index.jsp");
    
        String uso = request.getParameter("uso");
        CES bd = new CES();
        if(uso.equals("buscar")){
            String prov_field = request.getParameter("proveedor");
            String prov_activo = request.getParameter("activo");
            if(prov_activo.equals("A")){
                prov_activo="1";
                ArrayList<String> arr= bd.buscarusuario(prov_field, prov_activo);
             PrintWriter out = response.getWriter();
            if(!arr.isEmpty()){
                int cont=0;
            for(int i =0;i<arr.size();i++){
                if(cont==4){
                    out.print("<tr align=\"center\">\n" +
"                  <td>"+arr.get(i-3)+"</td>\n" +
"                  <td>"+arr.get(i-2)+"</td>\n" +
"                  <td>"+arr.get(i-1)+"</td>\n" +
"                  <td>"+arr.get(i)+"</td>\n" +
//"                  <td><a onclick=\"mod_prov('"+arr.get(i-4)+"')\"><img src=\"../images/modificar.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                  <td><a onclick=\"down_prov('"+arr.get(i-4)+"')\"><img src=\"../images/down.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                </tr>");
                    cont=0;
                }else cont++;
            }
            }
            }else {
            prov_activo="0";
                ArrayList<String> arr= bd.buscarusuario(prov_field, prov_activo);
             PrintWriter out = response.getWriter();
            if(!arr.isEmpty()){
                int cont=0;
            for(int i =0;i<arr.size();i++){
                if(cont==4){
                    out.print("<tr align=\"center\">\n" +
"                  <td>"+arr.get(i)+"</td>\n" +
"                  <td>"+arr.get(i-1)+"</td>\n" +
"                  <td>"+arr.get(i-2)+"</td>\n" +
"                  <td>"+arr.get(i-3)+"</td>\n" +
//"                  <td><a onclick=\"mod_prov('"+arr.get(i-4)+"')\"><img src=\"../images/modificar.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                  <td><a onclick=\"up_prov('"+arr.get(i-4)+"')\"><img src=\"../images/up.png\" class=\"img-fluid img_menus\" alt=\"\"></a></td>\n" +
"                </tr>");
                    cont=0;
                }else cont++;
            }
            }
            }
        }else if(uso.equals("nuevo")){
            CES_depa cdepa = new CES_depa();
            CES u = new CES();
            ArrayList<String> depa=new ArrayList<>();
            ArrayList<String> tipo_u=new ArrayList<>();
            depa=cdepa.busca_depa_general(depa);
            tipo_u=u.buscar_tipo_u(tipo_u);
            int cont =0;
            PrintWriter out = response.getWriter();
            out.print("<div align=center class=container-fluid><div class=col-sm   align=center><input type=text class=form-control "
                    + "id=prov_new1 placeholder=\"Nombre del usuario\" ><br><select id=\"depa\" onchange=\"\" class=\"form-control\"><option></option>");
            for(int i =0; i<depa.size();i++){
                if(cont ==1){
                    out.print("<option value=" + depa.get(i - 1) + ">" +depa.get(i) + "</option>");
                    cont=0;
                }else cont++;
            }cont=0;
                    out.print("</select><br><select id=\"tipo_usuario\" onchange=\"display_pass()\" class=\"form-control\"><option></option>");
                    for(int j =0; j<tipo_u.size();j++){
                if(cont ==1){
                    out.print("<option onchange=display_pass() value=" + tipo_u.get(j - 1) + ">" +tipo_u.get(j) + "</option>");
                    cont=0;
                }else cont++;
            }out.print("</select><br><label>Empresa:</label><select class=\"form-control\" id=\"empresa\"><option></option><option>ATH</option><option>ATT</option></select><br><div id=field_pass></div><br><button class=btn btn-success id=btn_nuevo_u onclick=nuevo_prov()>Ingresar Nuevo Usuario</button><br><div id=response_nprov></div></div></div>");
        }else if(uso.equals("nuevo_row")){// Nuevo Usuario uso: uso,user:user,depa:depa,tipo:tipo,pass:pass
            String name=request.getParameter("user").toUpperCase();
            int depa=Integer.parseInt(request.getParameter("depa").toUpperCase());
            int tipo=Integer.parseInt(request.getParameter("tipo").toUpperCase());
            String pass=request.getParameter("pass").toUpperCase();
            String tipo_cod=request.getParameter("tipo_cod").toUpperCase();
            String empresa=request.getParameter("empresa").toUpperCase();
            CES prov = new CES();            
            String mensaje="";
            String coloresp="";
            if(prov.buscarusuario(name,depa,tipo,pass)){
                mensaje="Ya existe el nombre de este usuario, intentelo de nuevo";                
                coloresp="non-input";
            }else{
                CES_depa depar = new CES_depa();
                Departamento departamento = new Departamento();
                TipoUsuario tp= new TipoUsuario();
                int area =depar.busca_area_depa(depa);
                String codigo="";
                if(usuario.equals("ADMINPROV")){
                codigo=area+tipo_cod+(codigo("1"));
                }else{codigo=area+tipo_cod+(codigo(String.valueOf(prov.lastuser()+1)));}
//                System.out.println(codigo);
                departamento.setClaveDepartamento(depa);
                tp.setClaveTipo(tipo);
                Usuario u = new Usuario();
                u.setCodigo(codigo);
                u.setDepartamento(departamento);
                u.setEmpresa(empresa);
                u.setNombre(name);
                u.setPass(pass);
                u.setStatuo('1');
                u.setTipoUsuario(tp);
                prov.nuevouser(u);
                mensaje="Nuevo usuario agregado";
                coloresp="non-inputok";
            }
            PrintWriter out = response.getWriter();
            out.print("<br><label class="+coloresp+">"+mensaje+"</label>");
        }else if(uso.equals("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES_prov db = new CES_prov();
            PrintWriter out = response.getWriter();
            if(db.buscarmov_prov(id)){
            out.print("No puedes Borrar un usuario que ya haya hecho movimientos");
            }else{
            db.borrarprov(id);
            out.print("Eliminacion exitosa"); 
            }
        }else if(uso.equals("baja")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES db = new CES();
            db.bajauser(id);
            PrintWriter out = response.getWriter();
            out.print("Baja exitosa");   
        }else if(uso.equals("alta")){
            int id = Integer.parseInt(request.getParameter("id"));
            CES db = new CES();
            db.altauser(id);
            PrintWriter out = response.getWriter();
            out.print("Alta exitosa");
        }
        } catch (SQLException ex) {
            System.out.println(ex+" error "+ex.getMessage());
            PrintWriter out = response.getWriter();
            out.print("Codigo 5: <br>"+ex);       
        } catch (ClassNotFoundException ex) {
            System.out.println(ex+" error "+ex.getMessage());
            Logger.getLogger(Getdata_user.class.getName()).log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.print("Codigo 5.1: <br>"+ex);
        }catch(Exception ex){
            Logger.getLogger(Getdata_user.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex+" error ");
            PrintWriter out = response.getWriter();
            out.print("Codigo 5.2: <br>"+ex);       
        }
    }
private String codigo(String usuario){
     char [] charestilo = usuario.toCharArray();
     char [] arr = {'0','0','0','0'};
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
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
