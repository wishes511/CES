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
public class CES_depa extends conBD{
//-----Busquedas
public ArrayList<String> busca_depa_cod(ArrayList<String> arr, char area) throws ClassNotFoundException, SQLException {  
    String query = "select clave_departamento,nombre from departamento where statuo='1' and clave_area='"+area+"' order by nombre";
        Statement smt;
        ResultSet df;
        abrir();
        smt = getConexion().createStatement();
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
        abrir();
        smt = getConexion().createStatement();
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
        
        abrir();
        smt = getConexion().createStatement();
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
        
        abrir();
        smt = 
                getConexion().createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            clave=Integer.parseInt(df.getString("clave_area"));
        }
        df.close();
        smt.close();
        return clave;
    }


}
