/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import Modelo.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mich
 */
public class CES_movs extends conBD {

    // Busquedas--------------
// Fin busquedas
    //insercion de datos a la bd
    public String nuevomov(Movimiento m, String horas, String credencial) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        Statement smt;
        ResultSet rs;
        String nombre = "";
        String retorno = "";
        String movimiento = "";
        int folio = 0;
        String hora = "";
        int conta = 0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String query = "";
            if (m.getClaveAutorizado() == 0 && m.getClaveProveedor() == 0 && m.getClaveUsuario() == 0) {
                query = "SELECT nombre,tipo_mov as 'tipo',folio as 'folio', hora as 'hora'"
                        + " from  movimiento where fecha='" + m.getFecha() + "' and n_credencial='" + credencial + "' and clave_proveedor=0 and area='" + m.getArea() + "'";
            } else {
                query = "SELECT p.clave_proveedor,p.nombre,m.tipo_mov as 'tipo',m.folio as 'folio', m.hora as 'hora'"
                        + " from proveedor p join movimiento m "
                        + "on m.clave_proveedor=p.clave_proveedor where p.clave_proveedor=" + m.getClaveProveedor()
                        + " and m.fecha='" + m.getFecha() + "' and n_credencial='" + credencial + "'";
            }
            //System.out.println(m.getClaveAutorizado()+"-"+ m.getClaveProveedor()+"-"+m.getClaveUsuario()+" sql "+query );    
            smt = getConexion().createStatement();
            rs = smt.executeQuery(query);
            while (rs.next()) {
                conta++;
                movimiento = rs.getString("tipo");
                folio = rs.getInt("folio");
                hora = rs.getString("hora");
                nombre = rs.getString("nombre");
            }// fin de busqueda del proveedor en los movimientos actuales
            rs.close();
            String sentenciaSQL = "";
            if (conta == 0) {// verifica si hubo registros en la consulta anterior,si no hubo insertar
                String s = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                        + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                        + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "','',0,'" + m.getDirigido() + "','" + m.getAsunto() + "','" + m.getTipo_transporte() + "','" + m.getTipo_usuario() + "')";
                retorno = "<div class=letra_entrada>Entrada:" + m.getNombre() + "</div><audio src=\"../images/ok.mp3\" autoplay></audio>";
//                System.out.println(s);
                st = getConexion().prepareStatement(s);
                st.executeUpdate();
                st.close();
            } else if (conta != 0) {
                if (movimiento.equals("S")) {
                    sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "','',0,'" + m.getDirigido() + "','" + m.getAsunto() + "','" + m.getTipo_transporte() + "','" + m.getTipo_usuario() + "')";
                    retorno = "<div class=letra_entrada>Entrada: " + m.getNombre() + "</div><audio src=\"../images/ok.mp3\" autoplay></audio>";
                } else {
                    sentenciaSQL = "update movimiento set horasalida='" + horas + "', tiempo=" + tiempo(hora, horas) + ",tipo_mov='S' where folio=" + folio;
                    retorno = "<div class=letra_salida>Salida: " + m.getNombre() + "<br><br> Hora de entrada: " + hora + "<br> Hora de salida: " + horas + "</div><audio src=\"../images/exit.mp3\" autoplay></audio>";
                }
            }
            st = getConexion().prepareStatement(sentenciaSQL);
            st.executeUpdate();
            st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_movs.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                Logger.getLogger(CES_movs.class.getName()).log(Level.SEVERE, null, o);
            }
        }
        return retorno;
    }

    public ArrayList<String> searchlast_movuser(ArrayList<String> arr, String fecha){
      ArrayList<String> array= new ArrayList<>();
        Statement smt;
        ResultSet rs;
        try {
            abrir();
            String query = "select d.nombre as 'nombres',m.tipo_mov as 'tipo',m.folio as 'folio', m.hora as 'hora', m.horasalida as 'salida',m.tipo_persona as tpersona "
                    + " from departamento d join movimiento m on m.clave_departamento = d.clave_departamento where "
                    + "m.clave_departamento=" +arr.get(2)+ " and m.clave_usuario=" + arr.get(0) + " and m.fecha='" + fecha + "'";
            //System.out.println("query nuevo_mov"+query);    
            smt = getConexion().createStatement();
            rs = smt.executeQuery(query);
            while (rs.next()) {
               array.add(rs.getString("tipo")); 
               array.add(rs.getString("tpersona"));
            }// fin de busqueda del proveedor en los movimientos actuales
            rs.close();
        }catch(Exception e){}
        return array;
    }
    
    public String nuevomov_maqp(Movimiento m, String horas, String credencial) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        Statement smt;
        ResultSet rs;
        String nombre = "";
        String retorno = "";
        String movimiento = "";
        int folio = 0;
        String hora = "";
        int conta = 0;
        String salida="";
        char tipomov;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String query = "select d.nombre as 'nombres',m.tipo_mov as 'tipo',m.folio as 'folio', m.hora as 'hora', m.horasalida as 'salida' "
                    + " from departamento d join movimiento m on m.clave_departamento = d.clave_departamento where m.clave_departamento=" + m.getDepartamento().getClaveDepartamento() + " and m.clave_usuario=" + m.getClaveUsuario() + " and m.fecha='" + m.getFecha() + "'";
            //System.out.println("query nuevo_mov"+query);    
            smt = getConexion().createStatement();
            rs = smt.executeQuery(query);
            while (rs.next()) {
                conta++;
                nombre = rs.getString("nombres");
                movimiento = rs.getString("tipo");
                folio = rs.getInt("folio");
                hora = rs.getString("hora");
                salida=rs.getString("salida");
            }// fin de busqueda del proveedor en los movimientos actuales
            rs.close();
            String sentenciaSQL = "";
            if (conta == 0) {// verifica si hubo registros en la consulta anterior,si no hubo insertar
                String s = "";
                if (m.getTipo_usuario().equals("M")) {
                    s = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "','',0,'','"+m.getAsunto()+"','" + m.getTipo_transporte() + "','" + m.getTipo_usuario() + "')";
                } else {
                    s = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','S','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','','" + credencial + "','" + horas + "',0,'','"+m.getAsunto()+"','" + m.getTipo_transporte() + "','" + m.getTipo_usuario() + "')";
                }

                if (m.getTipo_usuario().equals("M")) {
                    retorno = "<div class=letra_entrada><br><label>Pertenece a&nbsp&nbsp</label><label>" + m.getObservaciones() + "-" + m.getArea() + "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><br><br><label><big>ENTRADA</big></label></div><audio src=\"../images/ok.mp3\" autoplay></audio>";
                } else {
                    retorno = "<div class=letra_entrada><br><label>Area:&nbsp</label><label>" + m.getArea() + "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><br><br><label><big>SALIDA</big></label></div><audio src=\"../images/ok.mp3\" autoplay></audio>";
                }
                st = getConexion().prepareStatement(s);
                st.executeUpdate();
                st.close();
            } else if (conta != 0) {
                if (movimiento.equals("S")) {
                    if (m.getTipo_usuario().equals("M")) {
                        sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                                + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                                + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "','',0,'','"+m.getAsunto()+"','" + m.getTipo_transporte() + "','" + m.getTipo_usuario() + "')";
                    } else {
                        sentenciaSQL = "update movimiento set hora='" + horas + "', tiempo=" + tiempo(salida, horas) + ",tipo_mov='E' where folio=" + folio;
                    }
                    if (m.getTipo_usuario().equals("M")) {
                        retorno = "<div class=letra_entrada><br><label>Area: </label><label>" + m.getArea() + "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><br><br><label><big>ENTRADA</big></label></div><audio src=\"../images/ok.mp3\" autoplay></audio>";
                    } else {
                        retorno = "<div class=letra_entrada><br><label>Area :</label><label>" + m.getArea() + "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Depto:&nbsp" + nombre + "</label><br><br><label><big>ENTRADA</big></label></div><audio src=\"../images/ok.mp3\" autoplay></audio>";
                    }
                } else {
                    if (m.getTipo_usuario().equals("M")) {
                        sentenciaSQL = "update movimiento set horasalida='" + horas + "', tiempo=" + tiempo(hora, horas) + ",tipo_mov='S' where folio=" + folio;
                    } else {
                        sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                                + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','S','" + m.getArea() + "',"
                                + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','','" + credencial + "','" + horas + "',0,'','"+m.getAsunto()+"','" + m.getTipo_transporte() + "','" + m.getTipo_usuario() + "')";
                    }
                    if (m.getTipo_usuario().equals("M")) {
                        retorno = "<div class=letra_salida><br><label>Area: </label><label>" + m.getArea() + "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Departamento:&nbsp" + nombre + "</label><br><br><label><big>SALIDA</big><br> Hora de entrada: " + hora + "<br> Hora de salida: " + horas + "</label></div><audio src=\"../images/exit.mp3\" autoplay></audio>";
                    } else {
                        retorno = "<div class=letra_salida><br><label>Area: </label><label>" + m.getArea() + "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Departamento:&nbsp" + nombre + "</label><br><br><label><big>SALIDA</big><br> Hora de salida: " + horas + "</label></div><audio src=\"../images/exit.mp3\" autoplay></audio>";
                    }
//                    sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
//                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','S','" + m.getArea() + "',"
//                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
//                    retorno = "<div class=letra_salida><br><label>Pertenece a&nbsp&nbsp</label><label>" + m.getObservaciones()+"-"+m.getArea()+ "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Area:&nbsp" + m.getArea() + "</label><br><label>Depto:&nbsp" + nombre + "</label><br><br><label><big>SALIDA</big></label></div>";
//retorno="Salida: "+nombre;
                }
            }
            //System.out.println(sentenciaSQL);
            st = getConexion().prepareStatement(sentenciaSQL);
            st.executeUpdate();
            st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_movs.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                Logger.getLogger(CES_movs.class.getName()).log(Level.SEVERE, null, o);
            }
        }

        return retorno;
    }

    public ArrayList<String> search_lastmov(String area, String fecha, String numero, String tipo_mov) throws ClassNotFoundException, SQLException {
        ArrayList<String> list = new ArrayList<>();
        Statement smt;
        ResultSet rs;
        String clave = "";
        abrir();
        getConexion().setAutoCommit(false);
        String query;

        if (tipo_mov.equals("prov")) {
            query = "SELECT m.folio,m.clave_usuario,m.clave_proveedor,m.clave_autorizado,m.nombre,m.tipo_mov,m.area,"
                    + "m.clave_departamento,m.observaciones,m.fecha,m.hora,m.n_credencial,p.nombre as nombres from proveedor p join movimiento m "
                    + "on m.clave_proveedor=p.clave_proveedor where fecha='" + fecha + "' and n_credencial='" + numero + "' and area ='" + area + "'";
        } else {
            query = "SELECT * from movimiento where fecha='" + fecha + "' and n_credencial='" + numero + "' and area ='" + area + "' and clave_proveedor =0";
        }

        //System.out.println(query);
        smt = getConexion().createStatement();
        rs = smt.executeQuery(query);
        while (rs.next()) {
            list.add(rs.getString("folio"));
            list.add(rs.getString("clave_usuario"));
            list.add(rs.getString("clave_proveedor"));
            list.add(rs.getString("clave_autorizado"));
            list.add(rs.getString("nombre"));
            list.add(rs.getString("tipo_mov"));
            list.add(rs.getString("area"));
            list.add(rs.getString("clave_departamento"));
            list.add(rs.getString("observaciones"));
            list.add(rs.getString("fecha"));
            list.add(rs.getString("hora"));
            list.add(rs.getString("n_credencial"));
        }// fin de busqueda del proveedor en los movimientos actuales
        rs.close();
        return list;
    }

    public ArrayList<String> search_movs(String f1, String f2, String nombre, String narea, String ndepa, String mov, String transporte, String destino) throws ClassNotFoundException, SQLException {
        ArrayList<String> list = new ArrayList<>();
        Statement smt;
        ResultSet rs;
        abrir();
        String query = "SELECT m.nombre as 'nombre',isnull(p.nombre,'') as proveedor,a.nombre as 'area',d.nombre as 'depa',u.empresa as 'empresa',m.fecha,"
                + "m.hora,m.tipo_mov,m.horasalida,(+'0'+cast((SUM(m.tiempo)/60)as varchar))+':'+(cast((SUM(m.tiempo)%60) as varchar)+':00') as tiempo,m.observaciones as 'observacion',m.visita as 'visita' from movimiento m join departamento d on m.clave_departamento= d.clave_departamento\n"
                + "join area a on a.clave_area=d.clave_area\n"
                + "left join usuario u on m.clave_usuario =u.clave_usuario\n"
                + "left join proveedor p on m.clave_proveedor=p.clave_proveedor\n"
                + "where (m.nombre like '%" + nombre + "%' or p.nombre like '%" + nombre + "%') and m.visita like '%" + destino + "%' and m.tipo_transporte like '%" + transporte + "%' and m.area like '%" + narea + "%' and d.nombre like '%" + ndepa + "%' and m.tipo_mov like '%"
                + mov + "%' and m.fecha between '" + f1 + "' and '" + f2 + "' group by a.nombre,m.tipo_persona,m.visita,d.nombre,m.fecha,m.nombre,p.nombre,u.empresa,m.hora,m.tipo_mov,m.horasalida,m.tiempo,m.observaciones,tipo_transporte";
        //System.out.println(query);
        smt = getConexion().createStatement();
        rs = smt.executeQuery(query);
        while (rs.next()) {
            list.add(rs.getString("nombre"));
            list.add(rs.getString("proveedor"));
            list.add(rs.getString("area"));
            list.add(rs.getString("depa"));
            list.add(rs.getString("empresa"));
            list.add(rs.getString("fecha"));
            list.add(rs.getString("hora"));
            list.add(rs.getString("horasalida"));
            list.add(rs.getString("tiempo"));
            list.add(rs.getString("observacion"));
            list.add(rs.getString("visita"));

        }// fin de busqueda 
        rs.close();
        return list;
    }

    public Connection getconexion() {
        return getConexion();
    }

    private int tiempo(String h1, String h2) {// calculo de tiempo vol 2.0
//        System.out.println(h1+"-"+h2);
        int arr[] = new int[2];
        int arr1[] = new int[2];
        int hora = 0;
        arr[0] = Integer.parseInt(h1.charAt(0) + "" + h1.charAt(1));
        arr[1] = Integer.parseInt(h2.charAt(0) + "" + h2.charAt(1));
        arr1[0] = Integer.parseInt(h1.charAt(3) + "" + h1.charAt(4));
        arr1[1] = Integer.parseInt(h2.charAt(3) + "" + h2.charAt(4));
        int horas = 0;
        int mins = 0;
        if (arr[0] == arr[1]) {
            mins = arr1[1] - arr1[0];
        } else {
            mins = (60 - arr1[0]) + arr1[1];
        }
        if (mins < 0) {
            mins = mins * (-1);
        }
        if (arr[0] != arr[1]) {
            mins = mins + ((arr[1] - arr[0]) - 1) * 60;
        }

        // System.out.println(hora+" min");
        return mins;
    }

//    private String tiempo(String h1, String h2){// calculo de tiempo vol 2.0
//
//    int arr []=new int[2];
//    int arr1 []=new int[2];
//    String hora="";
//    arr[0]=Integer.parseInt(h1.charAt(0)+""+h1.charAt(1));
//    arr[1]=Integer.parseInt(h2.charAt(0)+""+h2.charAt(1));
//    arr1[0]=Integer.parseInt(h1.charAt(3)+""+h1.charAt(4));
//    arr1[1]=Integer.parseInt(h2.charAt(3)+""+h2.charAt(4));
//    int horas=0;
//    int mins=0;
//    int cont =1;
//    int aux=0;
//    if(arr[0]==arr[1]){
//        mins=arr1[1]-arr1[0];
//    }else{
//    mins=(60-arr1[0])+arr1[1];
//    }
//    if(mins <0){
//        mins = mins*(-1);
//    }
//    if(arr[0]!=arr[1]){
//    horas=(arr[1]-arr[0])-1;
//    }
//    if(mins==60){
//    horas++;
//    mins=0;
//    }
//        if(mins <10){
//            hora=horas+".0"+mins;
//        }else{
//            hora=horas+"."+mins;
//        }
//       // System.out.println(hora+" min");
//    return hora;
//    }
//    private String tiempo(String h1, String h2){
//
//    int arr []=new int[2];
//    int arr1 []=new int[2];
//    String hora="";
//    arr[0]=Integer.parseInt(h1.charAt(0)+""+h1.charAt(1));
//    arr[1]=Integer.parseInt(h2.charAt(0)+""+h2.charAt(1));
//    arr1[0]=Integer.parseInt(h1.charAt(3)+""+h1.charAt(4));
//    arr1[1]=Integer.parseInt(h2.charAt(3)+""+h2.charAt(4));
//    int horas=0;
//    int mins=0;
//    int cont =1;
//    System.out.println(arr[0]+"-"+arr[1]);
//    if(arr[0]==arr[1]){
//        System.out.println(arr[0]+"-"+arr[1]);
//        mins=arr1[1]-arr1[0];
//    }else{
//    mins=(60-arr1[0])+arr1[1];
//    System.out.println((60-arr1[0])+"+"+arr1[1]);
//    }
//    
//    if(mins <0){
//        mins = mins*(-1);
//    }
//    System.out.println(mins+"="+arr[0]+"*"+arr[1]);
//    if((mins %60==0 || mins >59) && mins !=0){
//        
//        horas++;
//        mins=mins-60;
//        if(mins <10){
//            hora=horas+":0"+mins;
//        }else{
//            hora=horas+":"+mins;
//        } 
////        System.out.println(hora+" hora");
//    }else{
//        if(mins <10){
//            hora=horas+":0"+mins;
//        }else{
//            hora=horas+":"+mins;
//        }
//       // System.out.println(hora+" min");
//    }
//    return hora;
//    }
}
