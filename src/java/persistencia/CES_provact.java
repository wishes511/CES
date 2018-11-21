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
public class CES_provact extends conBD{
    // Busquedas--------------
public ArrayList<String> buscarprov_act_idprov(int clave) throws ClassNotFoundException, SQLException {
    ArrayList<String> lista= new ArrayList<>();    
    String query = "select * from p_autorizado where clave_proveedor="+clave+" and statuo='1'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            lista.add(df.getString("clave_autorizado"));
            lista.add(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return lista;
    }
public ArrayList<String> buscarprov_act_idprov(String name) throws ClassNotFoundException, SQLException {
    ArrayList<String> lista= new ArrayList<>();    
    String query = "select pa.clave_autorizado as 'clave_autorizado',pa.nombre as 'nombre' from proveedor p "
            + "join p_autorizado pa on pa.clave_proveedor=p.clave_proveedor where p.nombre='"+name+"' and p.statuo='1'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            lista.add(df.getString("clave_autorizado"));
            lista.add(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return lista;
    }
public ArrayList<String> buscarprov_autorizado(String nombre, String status) throws ClassNotFoundException, SQLException {
    ArrayList<String> lista= new ArrayList<>();    
    String query = "select pa.clave_autorizado,pa.nombre,p.nombre as 'nprov' from p_autorizado pa join proveedor p \n" +
"on p.clave_proveedor=pa.clave_proveedor where (pa.nombre like '%"+nombre+"%' or p.nombre like '%"+nombre+"%') and pa.statuo='"+status+"'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            lista.add(df.getString("clave_autorizado"));
            lista.add(df.getString("nombre"));
            lista.add(df.getString("nprov"));
        }
        //System.out.println(query);
        df.close();
        smt.close();
        return lista;
    }
public boolean buscarprov_act(String nombre, int clave_prov) throws ClassNotFoundException, SQLException {  
        boolean p=false;
    String query = "select nombre from p_autorizado where nombre='"+nombre+"' and clave_proveedor="+clave_prov;
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
public String buscarfoto(String codigo) throws ClassNotFoundException, SQLException {  
        String ruta="";
    String query = "select ruta from codigos p join usuario u on p.clave_usuario=u.clave_usuario\n" +
"where u.codigo='"+codigo+"'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {ruta=df.getString("ruta");}
        df.close();
        smt.close();
        return ruta;
    }
public int buscarprov_act_caseta(String nombre, int clave_prov) throws ClassNotFoundException, SQLException {  
        int id=0;
    String query = "select clave_autorizado from p_autorizado where nombre='"+nombre+"' and clave_proveedor="+clave_prov;
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {id=df.getInt("clave_autorizado");}
        df.close();
        smt.close();
        return id;
    }
public boolean buscarmov_prov_a(int id) throws ClassNotFoundException, SQLException {  
        boolean p=false;
    String query = "select distinct p.nombre from movimiento m join p_autorizado p on m.clave_autorizado=p.clave_autorizado\n" +
                   "where p.clave_autorizado="+id;
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

  public void nuevoprov_autorizado(String p,int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "insert into p_autorizado values("+id+",'"+p+"','1')";
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
  public int nuevoprov_autorizado_caseta(String p,int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "insert into p_autorizado values("+id+",'"+p+"','1')";
            st = getConexion().prepareStatement(s);
            st.executeUpdate();
        st.close();
            getConexion().commit();
            
    String query = "select clave_autorizado from p_autorizado where clave_proveedor="+id+" and nombre ='"+p+"'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {a=df.getInt("clave_autorizado");}
        df.close();
        smt.close();
        
        } catch (Exception e) {
            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
            try {
                getConexion().rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());

            }
        }
        st.close();
     return a;   
    }
      public void borrarprov_a(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "delete p_autorizado where clave_autorizado="+id;
//            System.out.println(s);
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
     public void bajaprov_a(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update p_autorizado set statuo='0' where clave_autorizado="+id;
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
      public void altaprov_a(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update p_autorizado set statuo='1' where clave_autorizado="+id;
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
    //insercion de datos a la bd
    public int nuevoprog(int p) throws ClassNotFoundException, SQLException {
//        PreparedStatement st = null;
//        int a=0;
//        try {
//            abrir();
//            .setAutoCommit(false);
//            String s = "insert into programa";
//            st = conexion.prepareStatement(s);
//            st.executeUpdate();
//        rs.close();
//        st.close();
//            conexion.commit();
//        } catch (Exception e) {
//            Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
//            try {
//                conexion.rollback();
//            } catch (Exception o) {
//                System.out.println(o.getMessage());
//
//            }
//        }
//        st.close();
        return p;
    }
    //Actualizaciones o avances
public void modiavancestatus(ArrayList<String> arr, int k,String a,String fechas,char maq){
//    PreparedStatement st = null;
//        try{//modificar status de programa
//             abrir();
//            conexion.setAutoCommit(false);
//            String s = "update programa set statuto='"+arr.get(k).toUpperCase()+" "+maq+"', ultima_fecha='"+fechas+"' where id_prog="+a;
//            st = conexion.prepareStatement(s);
//            st.executeUpdate();
//            conexion.commit();
//            st.close();
//    }catch(Exception e){
//        Logger.getLogger(CES_prov.class.getName()).log(Level.SEVERE, null, e);
//            try {
//                conexion.rollback();
//            } catch (Exception o) {
//                System.out.println(o.getMessage());
//            }
//    }
    }//

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
