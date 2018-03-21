/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gateway1
 */
public class conBD {
    private String url = "jdbc:sqlserver://192.168.6.75\\SQLEXPRESS:9205;databaseName=CES;databaseName=CES;user=mich; password=mich;";
//   String url ="jdbc:sqlserver://192.168.6.8\\datos65:9205;databaseName=CES;";
    private String drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private Connection conexion = null;
    public Connection getConexion() {
        return this.conexion;
    }
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    public void abrir() throws ClassNotFoundException, SQLException {
        Class.forName(drive);
        conexion = DriverManager.getConnection(url, "mich", "mich");
        System.out.println();
//        conexion = DriverManager.getConnection(url, "sa", "Prok2001");
    }
    public void cerrar() throws SQLException {
        conexion.close();
    }
}
