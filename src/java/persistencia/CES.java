/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import Modelo.Movimiento;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class CES extends conBD {

public ArrayList<String> buscar_tipo_u(ArrayList<String> arr) throws ClassNotFoundException, SQLException {
        
        String query = "select clave_tipo,nombre from tipo_usuario";
        //System.out.println(query);
        Statement smt;
        ResultSet df;
        abrir();
        Connection conect=getConexion();
        smt = conect.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            arr.add(df.getString("clave_tipo"));
            arr.add(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return arr;
    }
    // Busquedas--------------
        public Movimiento buscaru(String usuario, String pass) throws ClassNotFoundException, SQLException {
        Movimiento m = new Movimiento();
        m.setTipo_usuario("n");
        String query = "select t.nombre,u.empresa from usuario u join tipo_usuario t on u.clave_tipo=t.clave_tipo where statuo='1' and u.nombre ='"+usuario+"' and pass='"+pass+"'";
        Statement smt;
        ResultSet df;
        abrir();
        Connection conect=getConexion();
        smt = conect.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            m.setTipo_usuario(df.getString("nombre"));
            m.setNombre_empresa(df.getString("empresa"));
        }
        df.close();
        smt.close();
        return m;
    }
public ArrayList<String> buscaru_clave(int clave, String otro) throws ClassNotFoundException, SQLException {
        ArrayList<String> ids= new ArrayList<>();
        String query = "select clave_usuario,u.nombre as 'nombre',u.clave_departamento as 'clave_departamento',empresa,a.nombre as 'area',tu.nombre as 'tipo' from tipo_usuario tu join usuario u on u.clave_tipo=tu.clave_tipo "
                + "join departamento p on u.clave_departamento = p.clave_departamento join area a on p.clave_area=a.clave_area where u.statuo!='0' and u.clave_usuario="+clave;
        Statement smt;
        ResultSet df;
        abrir();
        Connection conect=getConexion();
        smt = conect.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            ids.add(df.getString("clave_usuario"));
            ids.add(df.getString("nombre"));
            ids.add(df.getString("clave_departamento"));
            ids.add(df.getString("empresa"));
            ids.add(df.getString("area"));
            ids.add(df.getString("tipo"));
            
        }
        df.close();
        smt.close();
        return ids;
    }
public boolean buscarusuarios() throws ClassNotFoundException, SQLException {
        boolean respo =false;
        String query = "select top(1) nombre from usuario";
        Statement smt;
        ResultSet df;
        abrir();
        Connection conect=getConexion();
        smt = conect.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            respo=true;
        }
        df.close();
        smt.close();
        return respo;
    }

public ArrayList<String> buscarusuario(String nombre, String status) throws ClassNotFoundException, SQLException {
    ArrayList<String> lista= new ArrayList<>();    
    String query = "select u.clave_usuario as 'clave_usuario',u.nombre as 'nombre',d.nombre as 'departamento',t.nombre as 'tipo',u.empresa as 'empresa' \n" +
"from usuario u join tipo_usuario t on u.clave_tipo =t.clave_tipo\n" +
"join departamento d on u.clave_departamento=d.clave_departamento where (u.nombre like '%"+nombre+"%' or u.empresa like '%"+nombre+"%' or d.nombre like '%"+nombre+"%') and u.statuo='"+status+"'";
   // System.out.println(query);
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            lista.add(df.getString("clave_usuario"));
            lista.add(df.getString("nombre"));
            lista.add(df.getString("departamento"));
            lista.add(df.getString("tipo"));
            lista.add(df.getString("empresa"));
        }
        //System.out.println(query);
        df.close();
        smt.close();
        return lista;
    }
public boolean buscarusuario(String nombre, int depa, int tipo, String pass) throws ClassNotFoundException, SQLException {  
        boolean p=false;
    String query = "select nombre from usuario where nombre='"+nombre+"' and clave_departamento="+depa;
        Statement smt;
        ResultSet df;
        abrir();
        smt =getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {p=true;}
        df.close();
        smt.close();
        return p;
    }
public int lastuser() throws ClassNotFoundException, SQLException {  
        int p=0;
    String query = "select max(clave_usuario) as clave_usuario from usuario";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            p=Integer.parseInt(df.getString("clave_usuario"));
        }
        df.close();
        smt.close();
        return p;
    }
// Fin busquedas
    //insercion de datos a la bd
public void nuevouser(Usuario u) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "insert into usuario values('"+u.getNombre()+"',"+u.getTipoUsuario().getClaveTipo()+","+u.getDepartamento().getClaveDepartamento()+",'"
                    +u.getCodigo()+"','"+u.getStatuo()+"','"+u.getPass()+"','"+u.getEmpresa()+"')";
            st = getConexion().prepareStatement(s);
//            System.out.println(s);
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
    public void bajauser(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update usuario set statuo='0' where clave_usuario="+id;
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
    public void altauser(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0;
        try {
            abrir();
            getConexion().setAutoCommit(false);
            String s = "update usuario set statuo='1' where clave_usuario="+id;
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

}
