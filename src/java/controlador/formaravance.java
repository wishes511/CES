/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import Modelo.Programa;
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
import persistencia.Avances;

/**
 *
 * @author gateway1
 */
@WebServlet(name = "formaravance", urlPatterns = {"/formaravance"})
public class formaravance extends HttpServlet {

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

    public ArrayList<String> alldepcharge(ArrayList arr) {
        arr.add("corte");
        arr.add("fechacor");
        arr.add("cormaq");
        arr.add("precorte");
        arr.add("fechaprecor");
        arr.add("precormaq");
        arr.add("pespunte");
        arr.add("fechapes");
        arr.add("pesmaq");
        arr.add("deshebrado");
        arr.add("fechades");
        arr.add("desmaq");
        arr.add("ojillado");
        arr.add("fechaoji");
        arr.add("ojimaq");
        arr.add("inspeccion");
        arr.add("fechainsp");
        arr.add("inspmaq");
        arr.add("preacabado");
        arr.add("fechaprea");
        arr.add("preamaq");
        arr.add("montado");
        arr.add("fechamont");
        arr.add("montmaq");
        arr.add("prodt");
        arr.add("fechapt");
        arr.add("ptmaq");
        return arr;
    }

    public ArrayList<String> depaload(ArrayList arr) throws ClassNotFoundException, SQLException {
        arr.add("corte");
        arr.add("precorte");
        arr.add("pespunte");
        arr.add("deshebrado");
        arr.add("ojillado");
        arr.add("inspeccion");
        arr.add("preacabado");
        arr.add("montado");
        arr.add("prodt");
        return arr;
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
        try{
            HttpSession objSesion = request.getSession(false);
objSesion.invalidate();
response.sendRedirect("index.jsp");
        }catch(Exception e){
        response.sendRedirect("index.jsp");
        }
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
//i_d
        boolean estado;
        String usuario = (String) objSesion.getAttribute("usuario");
        String tipos = (String) objSesion.getAttribute("tipo");
        String ids = String.valueOf(objSesion.getAttribute("i_d"));

        //out.print(carrito.size());
        // out.println("" + tipos+"/"+ids);
        if (usuario != null && tipos != null && tipos.equals("BASICO") || tipos.equals("MEDIOBASICO")|| tipos.equals("PREINTERMEDIO")||tipos.equals("INTERMEDIO")||tipos.equals("ADMIN")) {
            //out.println(carrito.size());
        } else {
            response.sendRedirect("index.jsp");
        }
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        String fechac = año + "/" + mes + "/" + dia;
        String horas = hora + ":" + minuto;

        String charmaquila = null;
        String maquila = request.getParameter("marcas");
        String codigo = request.getParameter("codigo");
        String banda = request.getParameter("banda");
        String depar = request.getParameter("depar");
        int k = 0, k2 = 0;
        String a = "";
        int b=0;
        //carga de lista con departamento,fecha y maquila
        ArrayList<String> array = new ArrayList<>();
        ArrayList<String> array2 = new ArrayList<>();
        PrintWriter out = response.getWriter();
        System.out.println(maquila+"/"+codigo+"/montado banda: "+banda);
        if (usuario.equals("leon")) {//maquila de leon
            charmaquila="L";
             ArrayList<String> loadprog = new ArrayList<>();
            try{
            boolean respuesta =false;
            array = alldepcharge(array);
            array2 = depaload(array2);
            Avances av = new Avances();
            b= av.searchcod(codigo);
            
            System.out.println(depar);
            if (b ==0) {
//                    out.println("<script type=\"text/javascript\">");
//                    out.println("alert('No se han encontrado registros o ya esta completado el proceso con ese codigo');");
//                  //  out.println("location='planta/index.jsp';");
//                    out.println("</script>");
            out.println("<label>No se han encontrado registros o ya esta completado el proceso con este lote</label>");
            }else{
                a=String.valueOf(b);
                loadprog=av.getprogavances(a);
                //inicio es corte
                //System.out.println(array2.get(0)+" corte "+(depar));
                    if (array2.get(0).equals(depar)) {
                        System.out.println("Soy corte de leon .v");
                        respuesta = av.avanceprimerdep(a, fechac, charmaquila, array, array2);
                        if (respuesta) {
                            av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
//                            out.println("<script type=\"text/javascript\">");
//                            out.println("alert('AVANCE COMPLETO!!');");
//                            out.println("location='Cierresesion';");
//                            out.println("</script>");
                            out.println("<label>Avance Completo Exitosamente:)</label>");
                           // System.out.println("COMPLETOS .v");
                        } else {
//                            out.println("<script type=\"text/javascript\">");
//                            out.println("alert('YA EXISTE AVANCE DE ESTE DEPARTAMENTO');");
//                          //  out.println("location='planta/index.jsp';");
//                            out.println("</script>");
                            //System.out.println("YA EXISTE AVANCE .v");
                            out.println("<label>Ya existe Avance de este departamento</label>");
                        }
                    } else {
                        // de precorte en adelante esto es lo que prosigue\
                        //Establecer punto donde se encuentra el departamento en la lista
                        for (int i = 0; i < array.size(); i++) {
                            if (array.get(i).equals(depar)) {
                                k = i;
                                i = array.size();
                            }
                        }
                        if (av.verificaraiz(array, k, a)) {// inicio de verificacion del usuario con la lista
                            if (av.checkback(array, k, a)){//verifica el departamento anterior
                                    av.avances(a, fechac, charmaquila, array, k, (array.size() - 1),fechac);
                                    av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
//                                    out.println("<script type=\"text/javascript\">");
//                                    out.println("alert('AVANCE COMPLETO!!');");
//                                   // out.println("location='Cierresesion';");
//                                    out.println("</script>");
                                    out.println("<label>Avance Completo exitosamente!!</label>");
                                  //  System.out.println("HECHO");                               
                            } else {
//                                out.println("<script type=\"text/javascript\">");
//                                out.println("alert('FALTA LA CAPTURA DE AVANCE DEL DEPARTAMENTO ANTERIOR');");
//                               // out.println("location='planta/index.jsp';");
//                                out.println("</script>");
                              //  System.out.println("Gomene... falta el avance del departamento anterior :C");
                              out.println("<label>Falta la Captura del departamento anterior</label>");
                            }
                        } else {
//                            out.println("<script type=\"text/javascript\">");
//                            out.println("alert('YA EXISTE AVANCE DE ESTE DEPARTAMENTO');");
//                            out.println("location='planta/index.jsp';");
//                            out.println("</script>");
                          //  System.out.println("Ya existes avances .v");
                            //  response.sendRedirect("planta/index.jsp");
                            out.println("<label>Ya existe Avance de este departamento</label>");
                        }
                    }
                        if(loadprog.isEmpty()){
            }else{
                out.println(" <div class=\"row\" style=\"padding-top: 15px\">\n" +
"                                   <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">PROGRAMA:"+loadprog.get(0)+"</label><br>\n" +
"                                    <label class=\"ln\">LOTE:"+loadprog.get(5)+"</label> \n" +
"                                </div>\n" +
"                                 <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">ESTILO:"+loadprog.get(1)+"</label><br>\n" +
"                                    <label class=\"ln\">PARES:"+loadprog.get(2)+"</label> \n" +
"                                </div>\n" +
"                                 <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">COMBINACION:"+loadprog.get(3)+"</label>\n" +
"                                </div><div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">TERMINO :"+loadprog.get(6)+"</label>\n" +
"                                </div> \n" +
"                                </div>");
            }
            }
            }catch (Exception e) {
                Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            }
            
        } else if (maquila.equals("PLANTA") && (tipos.equals("BASICO"))|| tipos.equals("MEDIOBASICO")) {//planta y capturista
            System.out.println("PLNATA");
            boolean respuesta;
            boolean respuesta1;
            try {
                ArrayList<String> loadprog = new ArrayList<>();
                Avances av = new Avances();
                charmaquila = "P";
                Programa pr = new Programa();
                // carga de datos sobre listas
                array = alldepcharge(array);
                array2 = depaload(array2);

              //  System.out.println(usuario + "/" + tipos);
              //  System.out.println("Total array: " + array.size() + "/total de depas: " + array2.size());
                b = av.searchcod(codigo);
                pr=av.getprogcode(b);
                // verifica que nos haya devuelto un id distinto a cero
                
                if (b == 0) {
//                    out.println("<script type=\"text/javascript\">");
//                    out.println("alert('No se han encontrado registros o ya esta completado el proceso con ese codigo');");
//                    //out.println("location='planta/index.jsp';");
//                    out.println("</script>");
                    System.out.println("Entro a error");
                    av.loglote(codigo, String.valueOf(pr.getPrograma()), fechac,usuario+banda,a);
                    out.println("<label>Lote inexistente, intentelo de nuevo</label>");

                } else {
                    a=String.valueOf(b);
                    loadprog=av.getprogavances(a);
                    //inicio es corte
                    if (array2.get(0).equals(usuario)) {
                        System.out.println("Soy corte .v");
                        respuesta = av.avanceprimerdep(a, fechac, charmaquila, array, array2);
                        if (respuesta) {
                            av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
//                            out.println("<script type=\"text/javascript\">");
//                            out.println("alert('AVANCE COMPLETO!!');");
//                          //  out.println("location='Cierresesion';");
//                            out.println("</script>");
                          //  System.out.println("COMPLETOS .v");
                          out.println("<label>Avance Completo Exitosamente:)</label>");
                        } else {
//                            out.println("<script type=\"text/javascript\">");
//                            out.println("alert('YA EXISTE AVANCE DE ESTE DEPARTAMENTO');");
//                          //  out.println("location='index.jsp';");
//                            out.println("</script>");
                           // System.out.println("YA EXISTE AVANCE .v");
                           out.println("<label>Ya existe Avance de este departamento</label>");
                        }
                    } else {
                        // de precorte en adelante esto es lo que prosigue\
                        //Establecer punto donde se encuentra el departamento en la lista
                        for (int i = 0; i < array.size(); i++) {
                            if (array.get(i).equals(usuario)) {
                                k = i;
                                i = array.size();
                            }
                        }
                        if (av.verificaraiz(array, k, a)) {// inicio de verificacion del usuario con la lista
                            if (av.checkback(array, k, a) || usuario.equals("preacabado")) {//verifica el departamento anterior
                                if (usuario.equals("preacabado")) {//solo si el usuario es preacabado
                                    if (av.checkmontado(array, k, a)) {// verifica avance en montado
//                                        out.println("<script type=\"text/javascript\">");
//                                        out.println("alert('NO SE PUEDE REALIZAR AVANCE DE PREACABADO SI YA SE TIENE MONTADO, CONTACTE A UN ADMINISTRADOR.');");
//                                     //   out.println("location='planta/index.jsp';");
//                                        out.println("</script>");
 av.loglote(String.valueOf(pr.getLote()), String.valueOf(pr.getPrograma()), fechac,usuario+banda,a);
                                        out.println("<label>No se puede realizar avance de preacabado si ya se tiene montado, Contacte a un administrador</label>");
                                    //    System.out.println("No puedes hacer preacabado si ya se ha hecho montado");
                                    } else {
                                        av.avancespreaca(a, fechac, charmaquila, array, k);
//                                        out.println("<script type=\"text/javascript\">");
//                                        out.println("alert('AVANCE COMPLETO!!');");
//                                      //  out.println("location='Cierresesion';");
//                                        out.println("</script>");
                                        //System.out.println("Completo Q.Q");
                                        out.println("<label>Avance Completo Exitosamente:)</label>");
                                    }
                                } else if (usuario.equals("montado")) {//entra a usuariomontado

                                    if (av.checkpremontado(array, k, a)) {//verifica inspeccion de calidad
                                        av.avancesmontado(a, fechac, charmaquila, array, k, banda);
                                        av.modiavancestatus(array, k, a,fechac,banda,charmaquila);
//                                        out.println("<script type=\"text/javascript\">");
//                                        out.println("alert('AVANCE COMPLETO!');");
//                                    //    out.println("location='Cierresesion';");
//                                        out.println("</script>");
                                            out.println("<label>Avance Completo Exitosamente:)</label>");
                                    } else {// si aun no se tiene avance de inspeccion            
//                                        out.println("<script type=\"text/javascript\">");
//                                        out.println("alert('FALTA AVANCE DEL DEPARTAMENTO DE INSPECCION DE CALIDAD');");
//                                      //  out.println("location='planta/index.jsp';");
//                                        out.println("</script>");
av.loglote(String.valueOf(pr.getLote()), String.valueOf(pr.getPrograma()), fechac,(usuario+banda),a);
                                        out.println("<label>Falta Captura de Inspeccion de calidad</label>");
                                    }
                                } else {
                                    av.avances(a, fechac, charmaquila, array, k, (array.size() - 1),fechac);
//                                    out.println("<script type=\"text/javascript\">");
//                                    out.println("alert('AVANCE COMPLETO!!');");
//                                  //  out.println("location='Cierresesion';");
//                                    out.println("</script>");
                                   // System.out.println("HECHO");
                                   out.println("<label>Avance Completo Exitosamente:)</label>");
                                    if (array.get(k).equals(array.get(array.size() - 3))) {
                                        av.modiavancestatus(a);
                                    } else {
                                        av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
                                    }
                                }
                            } else {
//                                out.println("<script type=\"text/javascript\">");
//                                out.println("alert('FALTA LA CAPTURA DE AVANCE DEL DEPARTAMENTO ANTERIOR');");
//                              // out.println("location='planta/index.jsp';");
//                                out.println("</script>");
av.loglote(String.valueOf(pr.getLote()), String.valueOf(pr.getPrograma()), fechac,usuario+banda,a);
                                out.println("<label>Falta captura del departamento anterior</label>");
                              //  System.out.println("Gomene... falta el avance del departamento anterior :C");
                            }
                        } else {
//                            out.println("<script type=\"text/javascript\">");
//                            out.println("alert('YA EXISTE AVANCE DE ESTE DEPARTAMENTO');");
//                          //  out.println("location='index.jsp';");
//                            out.println("</script>");
                            out.println("<label>Ya existe Avance de este departamento</label>");
                        //    System.out.println("Ya existes avances .v");
                            //  response.sendRedirect("planta/index.jsp");
                        }
                    }
//                    out.println("<script type=\"text/javascript\">");
//                    out.println("alert('YA EXISTE AVANCE DE ESTE DEPARTAMENTO');");
//                    out.println("location='planta/index.jsp';");
//                    out.println("</script>");
                    //  response.sendRedirect("planta/index.jsp");
                }
            if(loadprog.isEmpty()){
            }else{
                out.println(" <div class=\"row\" style=\"padding-top: 15px\">\n" +
"                                   <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">PROGRAMA:"+loadprog.get(0)+"</label><br>\n" +
"                                    <label class=\"ln\">LOTE:"+loadprog.get(5)+"</label> \n" +
"                                </div>\n" +
"                                 <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">ESTILO:"+loadprog.get(1)+"</label><br>\n" +
"                                    <label class=\"ln\">PARES:"+loadprog.get(2)+"</label> \n" +
"                                </div>\n" +
"                                 <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">COMBINACION:"+loadprog.get(3)+"</label>\n" +
"                                </div> <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">TERMINO :"+loadprog.get(6)+"</label>\n" +
"                                </div>\n" +
"                                </div>");
            }
            } catch (Exception e) {
                response.sendRedirect("../index.jsp");
                Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            }
        }else if(tipos.equals("PREINTERMEDIO") || tipos.equals("INTERMEDIO")|| tipos.equals("ADMIN")){
            System.out.println("usuario PREINTERMEDIO");
            String maquilas = request.getParameter("maquila");
            String depmaquila = request.getParameter("depmaquila");
            boolean respuesta;
            boolean respuesta1;
            try {
                ArrayList<String> loadprog = new ArrayList<>();
                Avances av = new Avances();
                charmaquila =String.valueOf(maquilas.charAt(0));
                // carga de datos sobre listas
                array = alldepcharge(array);
                array2 = depaload(array2);
                b = av.searchcod(codigo);
                Programa pr = new Programa();
                
                pr=av.getprogcode(b);
                // verifica que nos haya devuelto un id distinto a cero
                 if (b == 0) {
                     av.loglote(codigo, String.valueOf(pr.getPrograma()), fechac,usuario+banda,String.valueOf(b));
                    out.println("<label>LOTE NO ENCONTRADO, VUELVA A INTERNTARLO O LLAME A UN ADMINISTRADOR</label>");

                } else {
                    a=String.valueOf(b);
                    loadprog=av.getprogavances(a);
                    //inicio es corte
                    System.out.println(array2.get(0)+"*"+depmaquila);
                    if (array2.get(0).equals(depmaquila)) {
                        System.out.println("Soy corte ");
                        respuesta = av.avanceprimerdep(a, fechac, charmaquila, array, array2);
                        if (respuesta) {
                            av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
                          out.println("<label>Avance Completo Exitosamente :)</label>");
                        } else {
                           out.println("<label>Ya existe Avance de este departamento</label>");
                        }
                    } else {
                        // de precorte en adelante esto es lo que prosigue\
                        //Establecer punto donde se encuentra el departamento en la lista
                        for (int i = 0; i < array.size(); i++) {
                            if (array.get(i).equals(depmaquila)) {
                                k = i;
                                i = array.size();
                            }
                        }
                        if (av.verificaraiz(array, k, a)) {// inicio de verificacion del usuario con la lista
                            if (av.checkback(array, k, a) || depmaquila.equals("preacabado")) {//verifica el departamento anterior

                                if (depmaquila.equals("preacabado")) {//solo si el usuario es preacabado
                                    if (av.checkmontado(array, k, a)) {
                                        
                                        av.loglote(String.valueOf(pr.getLote()), String.valueOf(pr.getPrograma()), fechac,usuario+banda,a);
                                        out.println("<label>No se puede realizar avance de preacabado si ya se tiene montado, Contacte a un administrador</label>");
                                    //    System.out.println("No puedes hacer preacabado si ya se ha hecho montado");
                                    } else {
                                        av.avancespreaca(a, fechac, charmaquila, array, k);
//                                        out.println("<script type=\"text/javascript\">");
//                                        out.println("alert('AVANCE COMPLETO!!');");
//                                      //  out.println("location='Cierresesion';");
//                                        out.println("</script>");
                                        //System.out.println("Completo Q.Q");
                                        out.println("<label>Avance Completo Exitosamente:)</label>");
                                    }
                                } else if (depmaquila.equals("montado")) {//entra a usuariomontado

                                    if (av.checkpremontado(array, k, a)) {//verifica inspeccion de calidad
                                        av.avancesmontado(a, fechac, charmaquila, array, k, banda);
                                        av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
                                            out.println("<label>Avance Completo Exitosamente:)</label>");
                                    } else {// si aun no se tiene avance de inspeccion   
                                        av.loglote(String.valueOf(pr.getLote()), String.valueOf(pr.getPrograma()), fechac,usuario+banda,a);
                                        out.println("<label>Falta Captura de Inspecciond de calidad</label>");
                                    }
                                } else {
                                    av.avances(a, fechac, charmaquila, array, k, (array.size() - 1),fechac);
                                   out.println("<label>Avance Completo Exitosamente:)</label>");
                                    if (array.get(k).equals(array.get(array.size() - 3))) {
                                        av.modiavancestatus(a);
                                    } else {
                                        av.modiavancestatus(array, k, a,fechac,charmaquila.charAt(0));
                                    }
                                }
                            } else {
                                av.loglote(String.valueOf(pr.getLote()), String.valueOf(pr.getPrograma()), fechac,usuario+banda,a);
                                out.println("<label>Falta captura del departamento anterior</label>");
                            }
                        } else {
                            out.println("<label>Ya existe Avance de este departamento</label>");
                        }
                    }
                }
            if(loadprog.isEmpty()){
            }else{
                out.println(" <div class=\"row\" style=\"padding-top: 15px\">\n" +
"                                   <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">PROGRAMA:"+loadprog.get(0)+"</label><br>\n" +
"                                    <label class=\"ln\">LOTE:"+loadprog.get(5)+"</label> \n" +
"                                </div>\n" +
"                                 <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">ESTILO:"+loadprog.get(1)+"</label><br>\n" +
"                                    <label class=\"ln\">PARES:"+loadprog.get(2)+"</label> \n" +
"                                </div>\n" +
"                                 <div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">COMBINACION:"+loadprog.get(3)+"</label>\n" +
"                                </div><div class=\"col-sm-4\">\n" +
"                                    <label class=\"ln\">TERMINO :"+loadprog.get(6)+"</label>\n" +
"                                </div> \n" +
"                                </div>");
            }
            } catch (Exception e) {
                response.sendRedirect("index.jsp");
                System.out.println(e.getMessage());
                Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            }
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
