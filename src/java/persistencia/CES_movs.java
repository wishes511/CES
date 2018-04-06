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
public class CES_movs extends conBD{

    // Busquedas--------------
// Fin busquedas

    //insercion de datos a la bd
    public String nuevomov(Movimiento m, String horas, String credencial) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        Statement smt;
        ResultSet rs;
        String nombre = "";
        String retorno = "";
        int conta = 0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String query = "";
            if (m.getClaveAutorizado() == 0 && m.getClaveProveedor() == 0 && m.getClaveUsuario() == 0) {
                query = "SELECT nombre from  movimiento where fecha='" + m.getFecha() + "' and n_credencial='" + credencial + "' and clave_proveedor=0";
            } else {
                query = "SELECT p.clave_proveedor,p.nombre from proveedor p join movimiento m "
                        + "on m.clave_proveedor=p.clave_proveedor where p.clave_proveedor=" + m.getClaveProveedor()
                        + " and m.fecha='" + m.getFecha() + "' and n_credencial='" + credencial + "'";
            }
            //System.out.println("query nuevo_mov"+query);    
            smt = getConexion().createStatement();
            rs = smt.executeQuery(query);
            while (rs.next()) {
                conta++;
                nombre = rs.getString("nombre");
            }// fin de busqueda del proveedor en los movimientos actuales
            rs.close();
            String sentenciaSQL = "";
            if (conta == 0) {// verifica si hubo registros en la consulta anterior,si no hubo insertar
                String s = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                        + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                        + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
                retorno = "Entrada";
                st = getConexion().prepareStatement(s);
                st.executeUpdate();
                st.close();
            } else if (conta != 0) {
                if (conta % 2 == 0) {
                    sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
                    retorno = "Entrada: " + m.getNombre();
                } else {
                    sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','S','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
                    retorno = "Salida: " + nombre;
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

    public String nuevomov_maqp(Movimiento m, String horas, String credencial) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        Statement smt;
        ResultSet rs;
        String nombre = "";
        String retorno = "";
        String movimiento="";
        int folio=0;
        int conta = 0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String query = "select d.nombre as 'nombres',m.tipo_mov as 'tipo',m.follio as 'folio' from departamento d join movimiento m on m.clave_departamento = d.clave_departamento where m.clave_departamento=" + m.getDepartamento().getClaveDepartamento() + " and m.clave_usuario=" + m.getClaveUsuario() + " and m.fecha='" + m.getFecha() + "'";
            System.out.println("query nuevo_mov"+query);    
            smt = getConexion().createStatement();
            rs = smt.executeQuery(query);
            while (rs.next()) {
                conta++;
                nombre = rs.getString("nombres");
            }// fin de busqueda del proveedor en los movimientos actuales
            rs.close();
            String sentenciaSQL = "";
            if (conta == 0) {// verifica si hubo registros en la consulta anterior,si no hubo insertar
                String s = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                        + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                        + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
                retorno = "<div class=letra_entrada><br><label>Pertenece a&nbsp&nbsp</label><label>" + m.getObservaciones()+"-"+m.getArea()+"</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Area:&nbsp" + m.getArea() + "</label><br><label>Depto:&nbsp" + nombre + "</label><br><br><br><label><big>ENTRADA</big></label></div>";
                st = getConexion().prepareStatement(s);
                st.executeUpdate();
                st.close();
            } else if (conta != 0) {
                if (conta % 2 == 0) {
                    sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','E','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
                    retorno = "<div class=letra_entrada><br><label>Pertenece a&nbsp&nbsp</label><label>" + m.getObservaciones()+"-"+m.getArea()+ "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Area:&nbsp" + m.getArea() + "</label><br><label>Depto:&nbsp" + nombre + "</label><br><br><label><big>ENTRADA</big></label></div>";
//     retorno="Entrada: "+nombre; 48402
                } else {
                    sentenciaSQL = "insert into movimiento values(" + m.getClaveUsuario() + "," + m.getClaveProveedor()
                            + "," + m.getClaveAutorizado() + ",'" + m.getNombre() + "','S','" + m.getArea() + "',"
                            + m.getDepartamento().getClaveDepartamento() + ",'" + m.getObservaciones() + "','" + m.getFecha() + "','" + horas + "','" + credencial + "')";
                    retorno = "<div class=letra_salida><br><label>Pertenece a&nbsp&nbsp</label><label>" + m.getObservaciones()+"-"+m.getArea()+ "</label><br><label>Personal:&nbsp" + m.getNombre() + "</label><br><label>Area:&nbsp" + m.getArea() + "</label><br><label>Depto:&nbsp" + nombre + "</label><br><br><label><big>SALIDA</big></label></div>";
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
        public ArrayList<String> search_movs(String f1,String f2,String nombre, String narea,String ndepa,String mov) throws ClassNotFoundException, SQLException {
        ArrayList<String> list = new ArrayList<>();
        Statement smt;
        ResultSet rs;
        abrir();
        String query= "SELECT m.nombre as 'nombre',isnull(p.nombre,'') as proveedor,a.nombre as 'area',d.nombre as 'depa',u.empresa as 'empresa',m.fecha,"+
"m.hora,m.tipo_mov from movimiento m join departamento d on m.clave_departamento= d.clave_departamento\n" +
"join area a on a.clave_area=d.clave_area\n" +
"left join usuario u on m.clave_usuario =u.clave_usuario\n" +
"left join proveedor p on m.clave_proveedor=p.clave_proveedor\n" +
"where (m.nombre like '%"+nombre+"%' or p.nombre like '%"+nombre+"%') and m.area like '%"+narea+"%' and d.nombre like '%"+ndepa+"%' and m.tipo_mov like '%"+mov+"%' and m.fecha between '"+f1+"' and '"+f2+"' order by fecha";
        System.out.println(query);
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
            list.add(rs.getString("tipo_mov"));
        }// fin de busqueda 
        rs.close();
        return list;
    }
        public Connection getconexion() {
        return getConexion();
    }
}
