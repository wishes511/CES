/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

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
public class CES_depa {
    private conBD db = new conBD();
//-----Busquedas
public ArrayList<String> busca_depa_cod(ArrayList<String> arr, char area) throws ClassNotFoundException, SQLException {  
    String query = "select clave_departamento,nombre from departamento where statuo='1' and clave_area='"+area+"' order by nombre";
        Statement smt;
        ResultSet df;
        db.abrir();
        smt = db.getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            arr.add(df.getString("clave_departamento"));
            arr.add(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return arr;
    }
    
public String busca_area_cod(char area) throws ClassNotFoundException, SQLException {  
    String areas="";
    String query = "select nombre from area where statuo='1' and clave_area='"+area+"'";
        Statement smt;
        ResultSet df;
        db.abrir();
        smt = db.getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            areas=(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return areas;
    }
public ArrayList<String> busca_depa_general(ArrayList<String> lista) throws ClassNotFoundException, SQLException {  
    
    String query = "select clave_departamento,nombre from departamento where statuo='1' order by nombre";
        Statement smt;
        ResultSet df;
        db.abrir();
        smt = db.getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            lista.add(df.getString("clave_departamento"));
            lista.add(df.getString("nombre"));
        }
        df.close();
        smt.close();
        return lista;
    }
public int busca_area_depa(int claved) throws ClassNotFoundException, SQLException {  
        int clave=0;
    String query = "select clave_area from departamento where statuo='1' and clave_departamento="+claved;
        Statement smt;
        ResultSet df;
        db.abrir();
        smt = db.getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            clave=Integer.parseInt(df.getString("clave_area"));
        }
        df.close();
        smt.close();
        return clave;
    }

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
