/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

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
public class CES_prov extends conBD{
    // Busquedas--------------
public ArrayList<String> buscarprov(String nombre, String status) throws ClassNotFoundException, SQLException {
    ArrayList<String> lista= new ArrayList<>();    
    String query = "select * from proveedor where nombre like '%"+nombre+"%' and statuo='"+status+"'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            lista.add(df.getString("clave_proveedor"));
            lista.add(df.getString("nombre"));
        }
        //System.out.println(query);
        df.close();
        smt.close();
        return lista;
    }
public ArrayList<String> buscarprov(ArrayList<String> arr) throws ClassNotFoundException, SQLException {  
    String query = "select * from proveedor where statuo='1' order by nombre";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            arr.add(df.getString("clave_proveedor"));
            arr.add(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return arr;
    }
public boolean buscarprov(String nombre) throws ClassNotFoundException, SQLException {  
        boolean p=false;
    String query = "select nombre from proveedor where nombre='"+nombre+"'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {p=true;}
        df.close();
        smt.close();
        return p;
    }
public int buscarprov_id(String nombre) throws ClassNotFoundException, SQLException {  
       int p=0;
    String query = "select clave_proveedor from proveedor where nombre='"+nombre+"'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {p=df.getInt("clave_proveedor");}
        df.close();
        smt.close();
        return p;
    }
public boolean buscarmov_prov(int id) throws ClassNotFoundException, SQLException {  
        boolean p=false;
    String query = "select distinct p.nombre from movimiento m join proveedor p on m.clave_proveedor=p.clave_proveedor\n" +
                    "where p.clave_proveedor="+id;
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {p=true;}
        df.close();
        smt.close();
        return p;
    }

// Fin busquedas

    //insercion de datos a la bd
    public void nuevoprov(String p) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "insert into proveedor values('"+p+"','1')";
            st = getConexion().prepareStatement(s);
            st.executeUpdate();
        st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());

            }
        }
        st.close();    
    }
    public void borrarprov(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "delete proveedor where clave_proveedor="+id;
            st = getConexion().prepareStatement(s);
            st.executeUpdate();
        st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();   
    }
    public void bajaprov(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update proveedor set statuo='0' where clave_proveedor="+id;
            st = getConexion().prepareStatement(s);
            st.executeUpdate();
        st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();   
    }
    public void altaprov(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update proveedor set statuo='1' where clave_proveedor="+id;
            st = getConexion().prepareStatement(s);
            st.executeUpdate();
        st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();   
    }
    public void modificar_prov(int id,String nombre) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update proveedor set nombre='"+nombre+"' where clave_proveedor="+id;
            st = getConexion().prepareStatement(s);
            st.executeUpdate();
        st.close();
            getConexion().commit();
        } catch (Exception e) {
            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();   
    }
    //Actualizaciones o avances


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
}
