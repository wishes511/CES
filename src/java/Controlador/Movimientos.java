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
import javax.servlet.http.HttpSession;
import persistencia.CES_movs;
import persistencia.CES_provact;

/**
 *
 * @author mich CONTROLADOR DE MOVIMIENTOS EN GENERAL
 */
@WebServlet(name = "Movimientos", urlPatterns = {"/Movimientos"})
public class Movimientos extends HttpServlet {

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
        response.sendRedirect("index.jsp");// sin entra por get lo regresa al inicio del sistema.
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
            Calendar fecha = Calendar.getInstance(); //intanciar informacion del calendiario respecto al sistema
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH) + 1;
            int dia = fecha.get(Calendar.DAY_OF_MONTH);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            int minuto = fecha.get(Calendar.MINUTE);
            String horas = "";
            if (hora > 9) {// verificar que la hora y minuto tengan 2 digitos
                horas = hora + ":";
            }
            if (hora < 10) {
                horas = "0" + hora + ":";
            }
            if (minuto < 10) {
                horas = hora + "0" + minuto;
            }
            if (minuto > 9) {
                horas += minuto;
            } else {
                horas = hora + ":" + minuto;
            }
            //System.out.println(horas);
            String fechac = año + "-" + mes + "-" + dia;//fecha formada por Calendar.getInstance();

            String uso = request.getParameter("uso");
            CES_movs bd = new CES_movs();
            if (uso.equals("proveedor")) {// solo acciones que convienen al proveedor
                String codigo = request.getParameter("codigo");
                String numero = String.valueOf(codigo.charAt(6)) + codigo.charAt(7);
                String prov = request.getParameter("prov");
                String prov_activo = request.getParameter("autorizada").toUpperCase();
                String depa = request.getParameter("depa");
                String area = request.getParameter("area").toUpperCase();
                CES_provact bda = new CES_provact();
                //System.out.println(codigo+"/"+numero);
                int clave_autorizado = bda.buscarprov_act_caseta(prov_activo, Integer.parseInt(prov));// busca si existe el nombre respecto al proveedor     
                if (clave_autorizado == 0) {// si no existe, lo crea
                    clave_autorizado = bda.nuevoprov_autorizado_caseta(prov_activo, Integer.parseInt(prov));
                }
                Movimiento m = new Movimiento(); // establecer obejeto para pasar datos a la consulta de la bd
                Departamento d = new Departamento();
                d.setClaveDepartamento(Integer.parseInt(depa));
                m.setClaveUsuario(0);
                m.setClaveProveedor(Integer.parseInt(prov));
                m.setClaveAutorizado(clave_autorizado);
                m.setNombre(prov_activo);
                m.setArea(area);
                m.setDepartamento(d);
                m.setFecha(fechac);
                m.setObservaciones("");
                PrintWriter out = response.getWriter();// instanciar objeto para escribir y responder hacia una pagina 
                out.print("<label>" + bd.nuevomov(m, horas, numero) + "</label>");// regreso <label>Entrada: o SALIDA</label> a las funciones de javascript 
            } else if (uso.equals("invitado")) {// acciones que solo conciernen al invitado
                String codigo = request.getParameter("codigo");
                String numero = String.valueOf(codigo.charAt(6)) + codigo.charAt(7);
                int depa = Integer.parseInt(request.getParameter("depa"));
                String prov = request.getParameter("nombre").toUpperCase();
                String obs = request.getParameter("obs").toUpperCase();
                String area = request.getParameter("area").toUpperCase();// datos provenientes del jsp, html
                PrintWriter out = response.getWriter();
                Movimiento m = new Movimiento();
                Departamento d = new Departamento();
                d.setClaveDepartamento(depa);
                m.setClaveUsuario(0);
                m.setClaveProveedor(0);
                m.setClaveAutorizado(0);
                m.setNombre(prov);
                m.setArea(area);
                m.setDepartamento(d);
                m.setFecha(fechac);
                m.setObservaciones(obs);
                out.print("<label>" + bd.nuevomov(m, horas, numero) + "</label>");// respuesta hacia la pagina del usuario
            }
            if (uso.equals("report")) {// {uso: uso,f1:f1,f2:f2,nombre:nombre,area:area,depa:dep,tipo:tipo}
                //Recordar que es la tabla que se cargara al filtrar los datos deseados  
                CES_movs mov = new CES_movs();
                String f1 = request.getParameter("f1");
                String f2 = request.getParameter("f2");
                String nombre = request.getParameter("nombre");
                String area = request.getParameter("area");
                String depa = request.getParameter("depa");
                String tipo = request.getParameter("tipo");
                PrintWriter out = response.getWriter();
                ArrayList<String> arr = new ArrayList<>();
                arr = mov.search_movs(f1, f2, nombre, area, depa, tipo);// carga los movimientos de acuerdo a los filtros y los guarda en una lista
                int cont = 0;
                String a = "";
                out.print("<tr></tr>");
                for (int i = 0; i < arr.size(); i++) {
                    if (cont == 9) {// como tiene 8 columnas, cada vez que sea igual ejecutara la nueva linea.
                        if (!a.equals(arr.get(i - 7))) {// verifica si el area es direfente para crear una nueva linea pero con el area nueva
                            out.print("<tr style=color:white;background-color:rgb(237,28,36) align=center><td colspan=7>" + arr.get(i - 7) + "</td></tr>");
                            a = arr.get(i - 7);
                        }
                        ///empezara a dibujar o escribir cada linea de informacion encontrada hacia el usuario    
                        out.print("<tr align=\"center\">\n"
                                + "                  <td>" + arr.get(i - 9) + "</td>\n"
                                + "                  <td>" + arr.get(i - 8) + "</td>\n"
                                + "                  <td>" + arr.get(i - 7) + "</td>\n"
                                + "                  <td>" + arr.get(i - 6) + "</td>\n"
                                + "                  <td>" + arr.get(i - 4) + " " + arr.get(i - 3) + "</td>\n"
                                + "                  <td>" + arr.get(i - 1) + "</td>\n"
                                + "                  <td>" + arr.get(i) + "</td>\n"
                                + "                </tr>");
                        ///////////////////////
                        cont = 0;
                    } else {
                        cont++;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Codigo 6: <br>" + ex);
            PrintWriter out = response.getWriter();
            out.print("Codigo 6: <br>" + ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Movimientos.class.getName()).log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.print("Codigo 6.1: <br>" + ex);
        } catch (Exception e) {
            System.out.println(e);
            PrintWriter out = response.getWriter();
            out.print("Codigo 6.2: <br>" + e);
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
