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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.CES;
import persistencia.CES_depa;
import persistencia.CES_movs;
import persistencia.CES_prov;

/**
 *
 * @author michell
 */
@WebServlet(name = "Getfields", urlPatterns = {"/Getfields"})
public class Getfields extends HttpServlet {

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

        String codigo = request.getParameter("codigo");
        String uso = request.getParameter("uso");
        ArrayList<String> arr = new ArrayList<>();
        ArrayList<String> arr_depa = new ArrayList<>();
        CES_prov prov = new CES_prov();
        CES_depa depa = new CES_depa();
        String area = "";
        String d4="";
        try {
            if(codigo.length()==6 || codigo.length()==7){
                codigo+= "00";
            }
            if (codigo.length() ==8 || codigo.length()==9) { //[1,1,9999,01,$]
            for (int i = 0; i < codigo.length(); i++) {
                if (i > 1 && i <6) {
                    d4 += codigo.charAt(i);
                }
            }
            
            //System.out.println(codigo + "-" + codigo.length()+"-"+d4);
            
             Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String fechac =año+"-"+mes+"-"+dia;
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        String horas="";
        if(hora >9 ){
            horas =hora+":";
        }if(hora<10){
            horas="0"+hora+":";
        }if(minuto <10){
            horas=horas+"0"+minuto;
        }
        if(minuto >9){
            horas+=minuto;
        }
            String n_tarjeta=String.valueOf(codigo.charAt(6)) +codigo.charAt(7);
            CES_movs mov = new CES_movs();
            area = depa.busca_area_cod(codigo.charAt(0));// busca el area de acuerdo al codigo
            ArrayList<String> array ;
            PrintWriter out = response.getWriter();
            switch (Integer.parseInt(d4)) {
                    // proveedores
                    case 9999:                        
                        array=mov.search_lastmov(area, fechac, n_tarjeta,"prov"); //busca ultimo movimiento al uso de la tarjeta
                        select_tipo_user(n_tarjeta,array,out,mov,arr,arr_depa,prov,depa,codigo,area,"9999");
                        break;
                    // Sr pablo
                    case 9998:
                        if(codigo.charAt(0)=='6' && codigo.charAt(0)=='7'){
                            array=mov.search_lastmov(area, fechac, n_tarjeta,"invitado"); //busca ultimo movimiento al uso de la tarjeta
                        select_tipo_user(n_tarjeta,array,out,mov,arr,arr_depa,prov,depa,codigo,area,"9997");
                        }
                        break;
                    // invitados
                    case 9997:
                       // invitado_fields(area,out,depa);
                        array=mov.search_lastmov(area, fechac, n_tarjeta,"invitado"); //busca ultimo movimiento al uso de la tarjeta
                        select_tipo_user(n_tarjeta,array,out,mov,arr,arr_depa,prov,depa,codigo,area,"9997");
                        break;
                    // Personal y maquiladores
                    default:
                        //System.out.println("entre a def");
                        CES u = new CES();
                        ArrayList<String> arru =u.buscaru_clave(Integer.parseInt(d4));
                        //System.out.println(!arru.isEmpty()+"-"+arru.get(4)+","+area);
                        if(!arru.isEmpty() && area.equals(arru.get(4))){
                            tipo_usuario_pm(arru,area,fechac,horas,out,mov);
                        }else {out.print("<label>No se encontro Usuario o codigo registrado</label>");
                                out.print("<script>document.getElementById('codigo').value='';</script>");}
                        break;
                }
            } else {
                PrintWriter oute = response.getWriter();
                oute.print("<div class= container-fluid align=center><label>Escriba o pase su numero de credencial adecuada</label><div class=col-sm-3 align=center>");
                oute.print("<script>document.getElementById('codigo').value='';</script>");
            }

        } catch (ClassNotFoundException c) {
            System.out.println(c);
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2: Falta algun archivo por cargar o no se encuentra alguno, llame a un administrador<br>"+c+"</label>");
        } catch (IOException i) {
            System.out.println(i);
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2.1: Problemas al ingresar correctamente un dato</label>");
        } catch (SQLException ex) {
            Logger.getLogger(Getfields.class.getName()).log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2.2: Problemas con la base de datos, llame a un administrador<br>"+ex+"</label>");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2.3: Problemas al ingresar correctamente los datos<br>"+e+"</label>");
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

    private void select_tipo_user(String n_tarjeta,ArrayList<String> array, PrintWriter out,CES_movs mov, ArrayList<String> arr,
            ArrayList<String> arr_depa, CES_prov prov, CES_depa depa,String codigo,String area,String cod_usuario) throws ClassNotFoundException, SQLException{
        //System.out.println(array.size());
        if (!array.isEmpty()){// verifica si encontro algun registro con anterioridad en el uso de la tarjeta
                            Departamento d = new Departamento();
                            Movimiento m = new Movimiento();
                            for(int i =0;i<array.size();i++){
                                if(i ==11 && (array.size()/12)==1){
                                d.setClaveDepartamento(Integer.parseInt(array.get(i-4)));
                            //    System.out.println(i+"/"+array.get(i-11)+","+array.get(i-10)+","+array.get(i-9)+","+array.get(i-8)+","+array.get(i-7)+","+array.get(i-6)+","+array.get(i-5)+","+array.get(i-4)+","+array.get(i-3)+","+array.get(i-2)+","+array.get(i-1)+","+array.get(i));
                            m.setFolio(Integer.parseInt(array.get(i-11)));
                            m.setClaveUsuario(Integer.parseInt(array.get(i-10)));
                            m.setClaveProveedor(Integer.parseInt(array.get(i-9)));
                            m.setClaveAutorizado(Integer.parseInt(array.get(i-8)));
                            m.setNombre(array.get(i-7));
                            m.setTipoMov(array.get(i-6));
                            m.setArea(array.get(i-5));
                            m.setDepartamento(d);
                            m.setObservaciones(array.get(i-3));
                            m.setFecha(array.get(i-2));
                            String hora_salida=array.get(i-1);
                            String credencial = array.get(i);
                            out.print("<label>"+mov.nuevomov(m, hora_salida, credencial)+"</label>");
                            out.print("<script>document.getElementById('codigo').value='';</script>");
                                
                            }else if((array.size()/12)>1 && i>11){// AQUI ME QUEDE COMPROBANDO LAS SALIDAS 
                                int h=12*((array.size()/12)-1);
                                if(array.get(h+5).equals("E")){
                            //System.out.println("entre a E");
                            d.setClaveDepartamento(Integer.parseInt(array.get(h+7)));
                            m.setFolio(Integer.parseInt(array.get(h)));
                            m.setClaveUsuario(Integer.parseInt(array.get(h+1)));
                            m.setClaveProveedor(Integer.parseInt(array.get(h+2)));
                            m.setClaveAutorizado(Integer.parseInt(array.get(h+3)));
                            m.setNombre(array.get(h+4));
                            m.setTipoMov(array.get(h+5));
                            m.setArea(array.get(h+6));
                            m.setDepartamento(d);
                            m.setObservaciones(array.get(h+8));
                            m.setFecha(array.get(h+9));
                            String hora_salida=array.get(h+10);
                            String credencial = array.get(h+11);
                            out.print("<label>"+mov.nuevomov(m, hora_salida, credencial)+"</label>"); //nuevasalida
                            out.print("<script>document.getElementById('codigo').value='';</script>");
                                }else{
                                if(cod_usuario.equals("9999")){
                                arr = prov.buscarprov(arr);//carga de proveedores
                                arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                                prov_fields(arr, arr_depa, area, out);// carga de menu de proveedor 
                                }else if(cod_usuario.equals("9997")){
                                arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                                invitado_fields(area, out,arr_depa);// carga menu de invitados
                                }    
                                }
                                //System.out.println(i+" h: "+h+"e.e /"+array.get(h)+","+array.get(h+1)+","+array.get(h+2)+","+array.get(h+3)+","+array.get(h+4)+","+array.get(h+5)+","+array.get(h+6)+","+array.get(h+7)+","+array.get(h+8)+","+array.get(h+9)+","+array.get(h+10)+","+array.get(h+11));
                                i=array.size();
                                }       
                            }
                        }else {// sino encontro registros, nueva entrada
                        if(cod_usuario.equals("9999")){
                        arr = prov.buscarprov(arr);//carga de proveedores
                        arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                        prov_fields(arr, arr_depa, area, out);
                        }else if(cod_usuario.equals("9997")){
                        arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                        invitado_fields(area, out,arr_depa);
                        }    
                        }
    }
    
    public void tipo_usuario_pm(ArrayList<String> arru, String area,String fecha, String horas,PrintWriter out, CES_movs movs) throws ClassNotFoundException, SQLException{
    Movimiento m = new Movimiento();
    Departamento dep = new Departamento();
    dep.setClaveDepartamento(Integer.parseInt(arru.get(2)));
    m.setClaveUsuario(Integer.parseInt(arru.get(0)));
    m.setClaveProveedor(0);
    m.setClaveAutorizado(0);
    m.setNombre(arru.get(1));
    m.setArea(area);
    m.setDepartamento(dep);
    m.setObservaciones(arru.get(3));
    m.setFecha(fecha);
    String credencial="";
    
    if(arru.get(2).equals("") || arru.get(0).equals("")){
        out.print("<label>Algun campo esta vacio,no se puede continuar</label>");
    out.print("<script>document.getElementById('codigo').value='';</script>");
    }else{
    out.print("<div>"+movs.nuevomov_maqp(m, horas, credencial)+"</div>");
    out.print("<script>document.getElementById('codigo').value='';</script>");
    }
    }
    
    private void prov_fields(ArrayList<String> arr, ArrayList<String> arr_depa, String area, PrintWriter out) {//menu proveedor
        int cont = 0;
        out.print("<div class=\" container-fluid\" align=\"center\">\n"
                + "                        <div class=\"col-sm-3\" align=\"center\">\n"
                + "                           <label>Proveedor :</label><select id=\"proveedor\" name=\"proveedor\" onchange=\"searchactivo_id()\" class=\"form-control\"><option></option>");
        for (int i = 0; i < arr.size(); i++) {
            if (cont == 1) {
                out.print("<option onchange=searchactivo_id() value=" + arr.get(i - 1) + ">" + arr.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        }
        out.print("                             </select>\n"
                + "                        </div>\n"
                + "                        <div class=\"col-sm-3\" align=\"center\" id=\"p_activos\">\n"
                + "                            <label>Persona Autorizada :</label><select id=\"\" class=\"form-control\">\n"
                + "                            </select>\n"
                + "                        </div>\n"
                + "                        <br><div class=\"col-sm-3\" style=\"\" align=center>\n"
                + "                                <input type=text id=\"p_activos_n\" class=\"ln form-control\" >\n"
                + "                        </div><br>\n"   
                + "                        <div class=\"col-sm-3\">\n"
                + "                                <label>Departamento : </label><select id=\"depa\" onchange=\"\" class=\"form-control\"><option></option>\n");
        for (int i = 0; i < arr_depa.size(); i++) {
            if (cont == 1) {
                out.print("<option onchange=searchactivo_id() value=" + arr_depa.get(i - 1) + ">" + arr_depa.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        }
        out.print("                            </select>\n"
                + "                            </div>\n"             
                + "                            <div class=\"col-sm\" style=\"\" align=center>\n"
                + "                                <br><label class=\"ln\" style=\"color: red\" >Area : </label><input type=text id=\"area\" class=\"ln non-input\" value="+area+" disabled>\n"
                + "                            </div><br>\n"
                + "                        <input type=\"button\" class=\"btn\" value=\"Iniciar I/O\" onclick=\"inicio_io()\"><br><br>\n"
                + "                        <div id=ensa></div>\n"
                + "                    </div>");
    }
    
    private void invitado_fields(String area, PrintWriter out, ArrayList<String> arr_depa) {//menu invitado
        int cont =0;
        out.print("<div class=\" container-fluid\" align=\"center\">\n"
                + "                        <div class=\"col-sm-3\" align=\"center\">\n"
                + "                           <label>Nombre :</label>"
                + "                        </div><br><div class=\"col-sm-3\" style=\"\" align=center>\n"
                + "                                <input type=text id=\"nombre\" class=\"ln form-control\" onchange=saltodepinvi()>\n"
                + "                        </div><br>\n"
                 + "                        <div class=\"col-sm-3\">\n"
                + "                                <label>Departamento : </label><select id=\"depa\" onchange=\"saltoob()\" class=\"form-control\"><option></option>\n");
        for (int i = 0; i < arr_depa.size(); i++) {
            if (cont == 1) {
                out.print("<option onchange=searchactivo_id() value=" + arr_depa.get(i - 1) + ">" + arr_depa.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        }
        out.print("                        </select><br></div><br><div class=\"col-sm-3\" style=\"\" align=center>\n"
                + "                                <input type=text id=\"observaciones\" class=\"ln form-control\" placeholder='COMENTARIOS' onchange=saltoinvi()>\n"
                + "                        </div><br>\n" 
                + "                        <div class=\"col-sm\" style=\"\" align=center>\n"
                + "                                <br><label class=\"ln\" style=\"color: red\" >Area : </label><input type=text id=\"area\" class=\"ln non-input\" value="+area+" disabled>\n"
                + "                        </div><br>\n"
                + "                                 <input type=\"button\" class=\"btn\" id=binvi value=\"Iniciar I/O\" onclick=\"inicio_io_invi()\"><br><br>\n"
                + "                        <div id=ensa></div>\n"
                + "                    </div>");
    }

}
