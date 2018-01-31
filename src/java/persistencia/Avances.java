/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import Modelo.Avance;
import Modelo.Programa;
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
public class Avances {
    //servidor local de pruebas
String url = "jdbc:sqlserver://192.168.6.75\\SQLEXPRESS:9205;" + "databaseName=avances;user=mich; password=mich;";
//    String url = "jdbc:sqlserver://192.168.6.75:9205;"
//            + "databaseName=avances;user=mich; password=mich;";
//    jdbc:sqlserver://192.168.6.75\SQLEXPRESS:9205;databaseName=avances
//   String url ="jdbc:sqlserver://192.168.6.8\\datos65:9205;databaseName=Avances;";
   String drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // Declaramos los sioguientes objetos
    Connection conexion = null;
    Statement stmt = null;
    ResultSet rs = null;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void abrir() throws ClassNotFoundException, SQLException {
        Class.forName(drive);
        conexion = DriverManager.getConnection(url, "mich", "mich");
//        conexion = DriverManager.getConnection(url, "sa", "Prok2001");
    }

    public void cerrar() throws SQLException {
        conexion.close();
    }

    // Busquedas--------------
public String buscardepa(ArrayList<String> arr,int i,int a) throws ClassNotFoundException, SQLException {
        String ids="0";
        String query = "select "+arr.get(i)+" from avance where id_prog="+a;
        //System.out.println(query);
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            ids=df.getString(arr.get(i));
        }
        df.close();
        smt.close();
        return ids;
    }
    public Usuario buscar(String user, String pass) throws ClassNotFoundException, SQLException {
        Usuario u = new Usuario();
        String query = "select * from usuarios where usuario='" + user + "' and contrasena ='" + pass + "'";
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            u.setUsuario(df.getString("usuario"));
            u.setTipo(df.getString("tipo"));
            u.setId_usuario(Integer.parseInt(df.getString("id_usuario")));
        }
        if (u.getTipo() != null) {
        } else {
            u.setTipo("n");
        }
        df.close();
        smt.close();
        return u;
    }
    public boolean checkprograma(Programa p) throws ClassNotFoundException, SQLException {
        boolean u=false ;
        String query = "select id_prog,prog from programa where prog="+p.getPrograma()+
        " and mes="+p.getMes()+" and lote ="+p.getLote()+" and years="+p.getYear();
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
           u=true;
        }
        query = "select lote from programa where lote ="+p.getLote()+" and years="+p.getYear();
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
           u=true;
        }
        df.close();
        smt.close();
        return u;
    }
       public void dateupdate(ArrayList arr,String fecha) throws ClassNotFoundException, SQLException {
           int cont=0;
        String query = "select id_prog from programa where ultima_fecha is NULL";
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            for(int i =arr.size()-1;i>=0;i--){
            if(cont ==2){
            String fechas= dateupdatebusca(arr,i,df.getInt("id_prog"));
                if(!fechas.equals(" ")){
                    Updatedate(Integer.parseInt(df.getString("id_prog")),fechas);
                   // System.out.println("Actualiza: ultfecha="+fechas+" id="+df.getString("id_prog"));
                    cont=arr.size();
                }else{
                    cont =0;
                }
           }else{
                cont++;
           }
           }
            cont=0;
        }
        
        df.close();
        smt.close();
        
    }
        public String dateupdatebusca(ArrayList arr,int i,int id) throws ClassNotFoundException, SQLException {
        int cont=0;
        String fecha=" ";
        String query = "select "+arr.get(i+1)+" from avance where "+arr.get(i)+" != 0 and id_prog="+id;
        //System.out.println(query);
        Statement smt;
        ResultSet st;
        abrir();
        smt = conexion.createStatement();
        st = smt.executeQuery(query);
        while (st.next()) {
            fecha=st.getString(""+arr.get(i+1));
        }
        st.close();
        smt.close();
        return fecha;
    }
    public ArrayList<String> searchstoppair(String f1, String f2,ArrayList<String> arr) throws ClassNotFoundException, SQLException {
        
        String query = "SELECT DISTINCT id, lote, prog, fecha, depar, dep_anterior\n" +
"FROM log_lote\n" +
"WHERE (fecha between '"+f1+"' and '"+f2+"') AND (prog <> -1) AND (depar <> 'mich1') AND (depar <> 'mich2') AND (depar <> 'mich2') AND (depar <> 'mich3') AND (depar <> 'mich')\n" +
"GROUP BY fecha,depar, prog, lote, id, dep_anterior\n" +
"ORDER BY fecha ";
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            arr.add(df.getString("id"));
            arr.add(df.getString("prog"));
            arr.add(df.getString("lote"));
            arr.add(df.getString("fecha"));
            arr.add(df.getString("depar"));
            arr.add(df.getString("dep_anterior"));
        }
        df.close();
        smt.close();
        
        return arr;
    }
    public boolean buscarprogram(String prog,int mes) throws ClassNotFoundException, SQLException {
        boolean u =false;
        String query = "select id_prog from programa where statuto !='COMPLETO' and prog="+Integer.parseInt(prog)+" and mes="+mes;
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
           u=true;
        }
       smt.close();
       df.close();
        return u;
    }
    public int buscarprogramm(String prog) throws ClassNotFoundException, SQLException {
        int u =0;
        String query = "select max(mes) as 'mes' from programa where statuto != 'COMPLETO' and prog="+Integer.parseInt(prog);
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
           u=df.getInt("mes");
        }
       smt.close();
       df.close();
        return u;
    }
    public ArrayList<String> viewdepa() throws ClassNotFoundException, SQLException {
        ArrayList arr = new ArrayList<>();
        Statement st;
        ResultSet rs;
        String query = "SELECT departamentos FROM departamento order by proceso";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            arr.add(rs.getString("departamentos"));
        }
        rs.close();
        st.close();
        return arr;
    }
    public ArrayList<String> searchfecha(String f1,String f2) throws ClassNotFoundException, SQLException {
         ArrayList<String> arr = new ArrayList<>();
        int corte =0,precorte =0,pes =0,des =0,oji =0,ins =0,prea =0,mont =0,pt =0;
         Statement st;
        ResultSet rs;
        String query = " select SUM(p.npares) as 'corte'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechacor between '"+f1+"' and '"+f2+"'\n";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//corte
          corte+= rs.getInt("corte");
        }//fin corte
        query = " select SUM(p.npares) as 'precorte'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechaprecor between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//precorte
          precorte+= rs.getInt("precorte");
        }//fin precorte
        query = " select SUM(p.npares) as 'pespunte'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechapes between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//pespunte
          pes+= rs.getInt("pespunte");
        }//fin pespunte
        query = " select SUM(p.npares) as 'deshebrado'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechades between '"+f1+"' and '"+f2+"'\n";

        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//deshebrado
         
          des+= rs.getInt("deshebrado");
        }//fin deshebrado
        query = " select SUM(p.npares) as 'ojillado'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechaoji between '"+f1+"' and '"+f2+"'\n";

        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//ojillado
          oji+= rs.getInt("ojillado");
        }//fin ojillado
        query = " select SUM(p.npares) as 'inspeccion' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechainsp between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//ojillado
          ins+= rs.getInt("inspeccion");
        }//fin inspecion
         query = " select SUM(p.npares) as 'preacabado' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechaprea between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//preacabado
          prea+= rs.getInt("preacabado");
        }//fin preacabado
         query = " select SUM(p.npares) as 'montado' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechamont between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//montado
          mont+= rs.getInt("montado");
        }//fin montado
         query = " select SUM(p.npares) as 'prodpt' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechapt between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//pt
          pt+= rs.getInt("prodpt");
        }//fin pt
        rs.close();
        st.close();
        arr.add(String.valueOf(corte));
        arr.add(String.valueOf(precorte));
        arr.add(String.valueOf(pes));
        arr.add(String.valueOf(des));
        arr.add(String.valueOf(oji));
        arr.add(String.valueOf(ins));
        arr.add(String.valueOf(prea));
        arr.add(String.valueOf(mont));
        arr.add(String.valueOf(pt));
        rs.close();
        st.close();
        return arr;  
    }
    public ArrayList<String> searchfechacompleto(String f1,String f2) throws ClassNotFoundException, SQLException {
         ArrayList<String> array = new ArrayList<>();
         Statement st;
        ResultSet rs;
        String query = "select p.prog,p.lote,p.estilo,p.npares,p.corrida,p.mes,p.combinacion \n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.fechapt between '"+f1+"' and '"+f2+"' and a.prodt=1 order by p.prog";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//corte
          array.add((rs.getString("prog")));
            array.add((rs.getString("lote")));
            array.add((rs.getString("estilo")));
            array.add((rs.getString("npares")));
            array.add((rs.getString("corrida")));
            array.add((rs.getString("mes")));
            array.add((rs.getString("combinacion")));
        }//fin corte
        rs.close();
        st.close();
        return array;  
    }
    public ArrayList<String> searchfecha(String f1,String f2, String maq) throws ClassNotFoundException, SQLException {
         ArrayList<String> arr = new ArrayList<>();
        int corte =0,precorte =0,pes =0,des =0,oji =0,ins =0,prea =0,mont =0,pt =0;
         Statement st;
        ResultSet rs;
        String query = " select SUM(p.npares) as 'corte'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.cormaq='"+maq+"' and a.fechacor between '"+f1+"' and '"+f2+"'\n";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//corte
          corte+= rs.getInt("corte");
        }//fin corte
        query = " select SUM(p.npares) as 'precorte'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where  a.precormaq='"+maq+"' and a.fechaprecor between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//precorte
          precorte+= rs.getInt("precorte");
        }//fin precorte
        query = " select SUM(p.npares) as 'pespunte'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.pesmaq='"+maq+"' and a.fechapes between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//pespunte
          pes+= rs.getInt("pespunte");
        }//fin pespunte
        query = " select SUM(p.npares) as 'deshebrado'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.desmaq='"+maq+"' and a.fechades between '"+f1+"' and '"+f2+"'\n";

        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//deshebrado
          des+= rs.getInt("deshebrado");
        }//fin deshebrado
        query = " select SUM(p.npares) as 'ojillado'\n" +
        " from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.ojimaq='"+maq+"' and a.fechaoji between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//ojillado
          oji+= rs.getInt("ojillado");
        }//fin ojillado
        query = " select SUM(p.npares) as 'inspeccion' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.inspmaq='"+maq+"' and a.fechainsp between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//ojillado
          ins+= rs.getInt("inspeccion");
        }//fin inspecion
         query = " select SUM(p.npares) as 'preacabado' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.preamaq='"+maq+"' and a.fechaprea between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//preacabado
          prea+= rs.getInt("preacabado");
        }//fin preacabado
         query = " select SUM(p.npares) as 'montado' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.montmaq='"+maq+"' and a.fechamont between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//montado
          mont+= rs.getInt("montado");
        }//fin montado
         query = " select SUM(p.npares) as 'prodpt' from programa p join avance a on a.id_prog = p.id_prog\n" +
        " where a.ptmaq='"+maq+"' and a.fechapt between '"+f1+"' and '"+f2+"'\n";
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {//pt
          pt+= rs.getInt("prodpt");
        }//fin pt
        rs.close();
        st.close();
        arr.add(String.valueOf(corte));
        arr.add(String.valueOf(precorte));
        arr.add(String.valueOf(pes));
        arr.add(String.valueOf(des));
        arr.add(String.valueOf(oji));
        arr.add(String.valueOf(ins));
        arr.add(String.valueOf(prea));
        arr.add(String.valueOf(mont));
        arr.add(String.valueOf(pt));
        rs.close();
        st.close();
        return arr;  
    }
    public int searchcod(String ids) throws ClassNotFoundException, SQLException {
        int id = 0;
        Statement st;
        ResultSet rs;
        String query = "SELECT max(id_prog) as id_prog FROM programa where statuto != 'COMPLETO' and codigo='" + ids+"'";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            id = (rs.getInt("id_prog"));
        }
        rs.close();
        st.close();
        return id;
    }
    // modificar detalle !!!! 14:31
    public ArrayList<String> getdetalledep(String f1, String f2, ArrayList<String> arr, int cont) throws ClassNotFoundException, SQLException {
        ArrayList<String> array= new ArrayList<>();
        Statement st;
        ResultSet rs;
        String query = "select p.prog,p.lote,p.estilo,p.npares,p.corrida,p.mes,p.combinacion,p.statuto from programa p join avance a on a.id_prog = p.id_prog\n" +
" where a."+arr.get(cont)+" between '"+f1+"' and '"+f2+"'\n" +
" order by prog";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            array.add((rs.getString("prog")));
            array.add((rs.getString("lote")));
            array.add((rs.getString("estilo")));
            array.add((rs.getString("npares")));
            array.add((rs.getString("corrida")));
            array.add((rs.getString("mes")));
            array.add((rs.getString("combinacion")));
            array.add((rs.getString("statuto")));
        }
        rs.close();
        st.close();
        return array;
    }
        public ArrayList<String> getdetalledep(String f1, String f2, ArrayList<String> arr, int cont,String maq) throws ClassNotFoundException, SQLException {
        ArrayList<String> array= new ArrayList<>();
        Statement st;
        ResultSet rs;
        String query = "select p.prog,p.lote,p.estilo,p.npares,p.corrida,p.mes,p.combinacion,p.statuto from programa p join avance a on a.id_prog = p.id_prog\n" +
" where a."+arr.get(cont+1)+"='"+maq+"' and a."+arr.get(cont)+" between '"+f1+"' and '"+f2+"'\n" +
" order by prog";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            array.add((rs.getString("prog")));
            array.add((rs.getString("lote")));
            array.add((rs.getString("estilo")));
            array.add((rs.getString("npares")));
            array.add((rs.getString("corrida")));
            array.add((rs.getString("mes")));
            array.add((rs.getString("combinacion")));
            array.add((rs.getString("statuto")));
        }
        rs.close();
        st.close();
        return array;
    }
    public ArrayList<String> getbanda(String f1, String f2) throws SQLException, ClassNotFoundException{
    ArrayList<String> arr= new ArrayList<>();
         Statement st;
        ResultSet rs;
    String query="select a.montado,SUM(p.npares) as 'pares' from programa p join avance a on a.id_prog = p.id_prog\n" +
                    " where a.montado !=0 and a.fechamont between '"+f1+"' and '"+f2+"' group by a.montado order by a.montado";
     abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
        arr.add(rs.getString("montado"));
        arr.add(rs.getString("pares"));
        }
    rs.close();
    st.close();
    cerrar();
    return arr;
    }
    public ArrayList<String> getbanda(String f1, String f2,String maq) throws SQLException, ClassNotFoundException{
    ArrayList<String> arr= new ArrayList<>();
         Statement st;
        ResultSet rs;
    String query="select a.montado,SUM(p.npares) as 'pares' from programa p join avance a on a.id_prog = p.id_prog\n" +
                    " where a.montado !=0 and a.fechamont between '"+f1+"' and '"+f2+"' and a.montmaq='"+maq+"' group by a.montado order by a.montado";
     abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
        arr.add(rs.getString("montado"));
        arr.add(rs.getString("pares"));
        }
    rs.close();
    st.close();
    cerrar();
    return arr;
    }
    public ArrayList<String> getprogavances(String id) throws SQLException, ClassNotFoundException{
    ArrayList<String> arr= new ArrayList<>();
         Statement st;
        ResultSet rs;
        abrir();
        if(id.equals("")){}else{
           String query="select * from programa where id_prog="+id;
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
        arr.add(rs.getString("prog"));
        arr.add(rs.getString("estilo"));
        arr.add(rs.getString("npares"));
        arr.add(rs.getString("combinacion"));
        arr.add(rs.getString("corrida"));
        arr.add(rs.getString("lote"));
        arr.add(rs.getString("statuto"));
        }
        rs.close();
    st.close();
        }
    return arr;
    }
    public ArrayList<String> getprog(int lote,int mes, int year) throws SQLException, ClassNotFoundException{
    ArrayList<String> arr= new ArrayList<>();
         Statement st;
        ResultSet rs;
        int id=0;
        String query = "SELECT max(id_prog) as id_prog FROM programa where mes="+mes+" and statuto != 'COMPLETO' and lote="+lote+" and years="+year;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            id = (rs.getInt("id_prog"));
        }
        if(id ==0){}else{
            query="select * from programa where mes="+mes+" and id_prog="+id;
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
        arr.add(rs.getString("prog"));
        arr.add(rs.getString("estilo"));
        arr.add(rs.getString("npares"));
        arr.add(rs.getString("combinacion"));
        arr.add(rs.getString("corrida"));
        arr.add(rs.getString("mes"));
        arr.add(rs.getString("fechaentrega"));
        arr.add(rs.getString("statuto"));
        arr.add(rs.getString("lote"));
        arr.add(rs.getString("id_prog"));
        }
        for(int i =0; i<arr.size();i++){}
        }
    rs.close();
    st.close();
    cerrar();
    return arr;
    }
        public ArrayList<String> getprog(int lote,int mes) throws SQLException, ClassNotFoundException{
    ArrayList<String> arr= new ArrayList<>();
         Statement st;
        ResultSet rs;
        int id=0;
        String query = "SELECT max(id_prog) as id_prog FROM programa where mes="+mes+" and statuto != 'COMPLETO' and lote="+lote;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            id = (rs.getInt("id_prog"));
        }
        if(id ==0){
        }else{
            query="select * from programa where mes="+mes+" and id_prog="+id;
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
        arr.add(rs.getString("prog"));
        arr.add(rs.getString("estilo"));
        arr.add(rs.getString("npares"));
        arr.add(rs.getString("combinacion"));
        arr.add(rs.getString("corrida"));
        arr.add(rs.getString("mes"));
        arr.add(rs.getString("fechaentrega"));
        arr.add(rs.getString("statuto"));
        arr.add(rs.getString("lote"));
        arr.add(rs.getString("id_prog"));
        }
        }
    rs.close();
    st.close();
    cerrar();
    return arr;
    }
    public Programa getprogcode(int code) throws SQLException, ClassNotFoundException{
            Programa p = new Programa();
    ArrayList<String> arr= new ArrayList<>();
         Statement st;
        ResultSet rs;
        boolean flag = false;
        String query = "SELECT * FROM programa where id_prog="+code+"";
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            p.setLote(Integer.parseInt(rs.getString("lote")));
            p.setPrograma(Integer.parseInt(rs.getString("prog")));
            flag=true;
        }
       if(flag){
       }else{
           p.setLote(0);
           p.setPrograma(-1);
       }
    rs.close();
    st.close();
    cerrar();
    return p;
    }
    public Avance getfechas(ArrayList arr) throws ClassNotFoundException, SQLException{
    Avance avan = new Avance();
     Statement st;
        ResultSet rs;
        if(arr.isEmpty()){
        }else{
        String query = "select p.statuto,p.fechaentrega,a.fechacor,a.fechaprecor,a.fechapes,a.fechades,a.fechaoji,a.fechainsp,a.fechaprea,a.fechamont,a.fechapt,"
                + "a.cormaq,a.precormaq,a.pesmaq,a.desmaq,a.ojimaq, a.inspmaq, a.preamaq, a.montmaq, a.ptmaq"
                + " from programa p join avance a on a.id_prog =p.id_prog "
                + "where p.id_prog="+arr.get(9);
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
           avan.setStatus(rs.getString("statuto"));
           avan.setFechaentrega(rs.getString("fechaentrega"));
           avan.setFechacorte(rs.getString("fechacor")+" "+rs.getString("cormaq"));
           avan.setFechaprecorte(rs.getString("fechaprecor")+" "+rs.getString("precormaq"));
           avan.setFechapespunte(rs.getString("fechapes")+" "+rs.getString("pesmaq"));
           avan.setFechadeshebrado(rs.getString("fechades")+" "+rs.getString("desmaq"));
           avan.setFechaojillado(rs.getString("fechaoji")+" "+rs.getString("ojimaq"));
           avan.setFechainspeccion(rs.getString("fechainsp")+" "+rs.getString("inspmaq"));
           avan.setFechapreacabado(rs.getString("fechaprea")+" "+rs.getString("preamaq"));
           avan.setFechamontado(rs.getString("fechamont")+" "+rs.getString("montmaq"));
           avan.setFechapt(rs.getString("fechapt")+" "+rs.getString("ptmaq"));
        }
        rs.close();
        st.close();
        cerrar();
        }
    return avan;
    }
    public boolean verificaraiz(ArrayList<String> arr, int k, String id) throws ClassNotFoundException, SQLException {
        boolean flag = false;
        Statement st;
        ResultSet rs;
        String query = "SELECT id_prog FROM avance where " + arr.get(k) + "=0 and id_prog=" + id;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            flag = true;
        }
        rs.close();
        st.close();
        cerrar();
        return flag;
    }
    public boolean checkback(ArrayList<String> arr, int k, String id) throws ClassNotFoundException, SQLException {
        int k2 = k-3;
        boolean flag = false;
        Statement st;
        ResultSet rs;
        String query = "select id_prog from avance where "+arr.get(k2)+" !=0 and id_prog="+id;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            flag = true;
        }
        rs.close();
        st.close();
        cerrar();
        return flag;
    }
    public boolean checkmontado(ArrayList<String> arr, int k, String id) throws ClassNotFoundException, SQLException {
        int k2 = k+3;
        boolean flag = false;
        Statement st;
        ResultSet rs;
        String query = "select id_prog from avance where "+arr.get(k2)+" !=0 and id_prog="+id;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            flag = true;
        }
        rs.close();
        st.close();
        cerrar();
        return flag;
    }
    public boolean checkpremontado(ArrayList<String> arr, int k, String id) throws ClassNotFoundException, SQLException {
        int k2 = k-6;
        boolean flag = false;
        Statement st;
        ResultSet rs;
        String query = "select id_prog from avance where "+arr.get(k2)+" !=0 and id_prog="+id;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            flag = true;
        }
        rs.close();
        st.close();
        cerrar();
        return flag;
    }  
    public ArrayList<String> getallprog(ArrayList<String> arr,String lote,int mes) throws ClassNotFoundException, SQLException {
        int prog=0;
        Statement st;
        ResultSet rs;
        String query = "select prog from programa where mes="+mes+" and statuto !='COMPLETO' and lote ="+Integer.parseInt(lote);
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            prog=rs.getInt("prog");
        }
        query ="select * from programa where mes="+mes+" and prog="+prog;
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while(rs.next()){
        arr.add(rs.getString("prog"));
        arr.add(rs.getString("lote"));
        arr.add(rs.getString("estilo"));
        arr.add(rs.getString("npares"));
        arr.add(rs.getString("combinacion"));
        arr.add(rs.getString("corrida"));
        arr.add(rs.getString("mes"));
        arr.add(rs.getString("statuto"));
        arr.add(rs.getString("ultima_fecha"));
        }
        for(int i=0;i<arr.size();i++){
        }
        rs.close();
        st.close();
        cerrar();
        return arr;
    }
   // Fin busquedas

    //insercion de datos a la bd
    public Programa nuevoprog(Programa p) throws ClassNotFoundException, SQLException {
        PreparedStatement st = null;
        int a=0, lote=p.getLote();
        try {
            abrir();
            conexion.setAutoCommit(false);
            String s = "insert into programa(prog,lote,estilo,npares,combinacion,corrida,mes,fechaentrega,statuto,codigo,ultima_fecha,years) values(" + p.getPrograma() + "," + p.getLote() + "," + p.getEstilo() + "," + p.getPares() + ",'" + p.getCombinacion() + "','" + p.getCorrida() + "'," + p.getMes() + ",'" + p.getFechae() + "','NO TERMINADO','" + p.getCodigo() + "','"+p.getFecha()+"',"+p.getYear()+")";
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            //-----------------------------------
            Statement sts;
        ResultSet rs;
        String query = "select MAX(id_prog) as id_prog from programa";
        sts = conexion.createStatement();
        rs = sts.executeQuery(query);
        while (rs.next()) {        
            
          a=rs.getInt("id_prog");
        }
        //----------------
        s = "insert into avance values("+a+","+lote+",0,NULL,'0',0,NULL,'0',0,NULL,'0',0,NULL,'0',0,NULL,'0',0,NULL,'0',0,NULL,'0',0,NULL,'0',0,NULL,'0')";
            st = conexion.prepareStatement(s);
            st.executeUpdate();
        //+++++++++++++++++
        rs.close();
        st.close();
        sts.close();
            conexion.commit();
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());

            }
        }
        st.close();
        return p;
    }
 public void loglote(String lote, String prog, String fecha,String depar, String id) throws ClassNotFoundException, SQLException {
     String oldstatus="";
     if(id.equals("0")){
     }else{
     Statement st;
        ResultSet rs;
        String query = "select statuto from programa where id_prog="+id;
        abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            oldstatus=rs.getString("statuto");
        }
     }
          PreparedStatement smt = null;
        try {
           abrir();
           conexion.setAutoCommit(false);
        String s = "insert into log_lote values('"+lote+"','"+prog+"','"+fecha+"','1','"+depar+"','"+oldstatus+"')";
       smt = conexion.prepareStatement(s);
            smt.executeUpdate();
        smt.close();
            conexion.commit();    
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
    }
    //Actualizaciones o avances
    public boolean avanceprimerdep(String a, String fecha, String m, ArrayList<String> arr, ArrayList<String> arr2) throws ClassNotFoundException, SQLException {
        boolean flag = false, retorno = false;
        String query = "select " + arr.get(0) + " from avance where id_prog=" + a + " and " + arr.get(0) + "=0";
        Statement smt;
        ResultSet df;
        abrir();
        smt = conexion.createStatement();
        df = smt.executeQuery(query);
        while (df.next()) {
            flag = true;
        }
        df.close();
        smt.close();
        if (flag) {
            PreparedStatement st = null;
            try {
                abrir();
                conexion.setAutoCommit(false);
                String s = "update avance set " + arr.get(0) + "=1, " + arr.get(1) + "='" + fecha + "', " + arr.get(2) + "='" + m + "' where id_prog=" + a;
                st = conexion.prepareStatement(s);
                st.executeUpdate();
                conexion.commit();
            } catch (Exception e) {
                Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
                try {
                    conexion.rollback();
                } catch (Exception o) {
                    System.out.println(o.getMessage());
                }
            }
            st.close();
            retorno = true;
        }
        return retorno;
    }
    public void avances(String a, String fecha, String m, ArrayList<String> arr, int k, int tamano, String f) throws SQLException{
    PreparedStatement st = null;
        try {// realizar avances
            abrir();
            conexion.setAutoCommit(false);
            String s = "update avance set "+arr.get(k)+"=1, "+arr.get(k+1)+"='"+fecha+"', "+arr.get(k+2)+"='"+m+"' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
             conexion.commit();
            if(arr.get(k).equals(arr.get(tamano-2))){
            s = "update programa set statuto ='COMPLETO' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
             conexion.commit();
            }else{
              modiavancestatus(arr,k,a,f,m.charAt(0));
            }
           st.close();
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }    
    }
    public void modiavancestatus(ArrayList<String> arr, int k,String a,String fechas,char maq){
    PreparedStatement st = null;
        try{//modificar status de programa
             abrir();
            conexion.setAutoCommit(false);
            String s = "update programa set statuto='"+arr.get(k).toUpperCase()+" "+maq+"', ultima_fecha='"+fechas+"' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
            st.close();
    }catch(Exception e){
        Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
    }
    }
    public void modiavancestatus(ArrayList<String> arr, int k,String a,String fechas,String banda,String maqbanda){
    PreparedStatement st = null;
        try{//modificar status de programa
             abrir();
            conexion.setAutoCommit(false);
            String s = "update programa set statuto='"+arr.get(k).toUpperCase()+" "+banda+" "+maqbanda+"', ultima_fecha='"+fechas+"' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
            st.close();
    }catch(Exception e){
        Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
    }
    }
    public void modiprogram(Programa p){
    PreparedStatement st = null;
        try{//modificar programa y avance
             abrir();
            conexion.setAutoCommit(false);
            String s = "update programa set prog="+p.getPrograma()+",lote="+p.getLote()+",estilo="+p.getEstilo()+
                       ",npares="+p.getPares()+",combinacion='"+p.getCombinacion()+"',corrida='"+p.getCorrida()+"',mes="+
                       p.getMes()+",fechaentrega='"+p.getFechae()+"',codigo='"+codigo(p.getCodigo())+"' where id_prog="+p.getId();
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            s = "update avance set lote="+p.getLote()+" where id_prog="+p.getId();
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
            st.close();
    }catch(Exception e){
        Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
    }
    }
     public void Updatedate(int id, String fecha) throws ClassNotFoundException, SQLException {
            PreparedStatement st = null;
            try {
                abrir();
                conexion.setAutoCommit(false);
                String s = "update programa set ultima_fecha='"+fecha+"' where id_prog="+id;
                st = conexion.prepareStatement(s);
                st.executeUpdate();
                conexion.commit();
            } catch (Exception e) {
                Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
                try {
                    conexion.rollback();
                } catch (Exception o) {
                    System.out.println(o.getMessage());

                }
            }
            st.close();
    }
    public void modiavancestatus(String a){
    PreparedStatement st = null;
        try{//modificar status de programa ultimo departamento
             abrir();
            conexion.setAutoCommit(false);
            String s = "update programa set statuto='COMPLETO' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
            st.close();
    }catch(Exception e){
        Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
    }
    }
    public void avancespreaca(String a, String fecha, String m, ArrayList<String> arr, int k) throws SQLException{
    PreparedStatement st = null;
        try {
            abrir();
            conexion.setAutoCommit(false);
            String s = "update avance set "+arr.get(k)+"=1, "+arr.get(k+1)+"='"+fecha+"', "+arr.get(k+2)+"='"+m+"' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();
    }
    public void avancesmontado(String a, String fecha, String m, ArrayList<String> arr, int k, String banda) throws SQLException{
    PreparedStatement st = null;
        try {
            abrir();
            conexion.setAutoCommit(false);
            String s = "update avance set "+arr.get(k)+"="+banda+", "+arr.get(k+1)+"='"+fecha+"', "+arr.get(k+2)+"='"+m+"' where id_prog="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();
    }
    public void lotesupdate(int a) throws SQLException{
    PreparedStatement st = null;
        try {
            abrir();
            conexion.setAutoCommit(false);
            String s = "update log_lote set statuto=0 where id="+a;
            st = conexion.prepareStatement(s);
            st.executeUpdate();
            conexion.commit();
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }
        st.close();
    }
    public ArrayList<String> getcoms() throws SQLException, ClassNotFoundException{
    ArrayList<String> arr = new ArrayList<>();
    String query="select distinct combinacion from programa order by combinacion";
    ResultSet rs;
    Statement st;
    abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
           arr.add(rs.getString("combinacion"));
        }
        rs.close();
        st.close();
        cerrar();
        arr.size();
    return arr;
    }
    public ArrayList<String> getmaquila() throws SQLException, ClassNotFoundException{
    ArrayList<String> arr = new ArrayList<>();
    String query="select distinct maquilas from maquila";
    ResultSet rs;
    Statement st;
    abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
           arr.add(rs.getString("maquilas"));
        }
        rs.close();
        st.close();
        cerrar();
        arr.size();
    return arr;
    }
    public ArrayList<String> getlogs() throws SQLException, ClassNotFoundException{
    ArrayList<String> arr = new ArrayList<>();
    String query="select * from log_lote where statuto='1'";
    ResultSet rs;
    Statement st;
    abrir();
        st = conexion.createStatement();
        rs = st.executeQuery(query);
        while (rs.next()) {
            arr.add(rs.getString("id"));
           arr.add(rs.getString("lote"));
           arr.add(rs.getString("prog"));
           arr.add(rs.getString("fecha"));
           arr.add(rs.getString("statuto"));
           arr.add(rs.getString("depar"));
           arr.add(rs.getString("dep_anterior"));
        }
        rs.close();
        st.close();
        cerrar();
    return arr;
    }
    public static void main (String args []){
    Avances a = new Avances();
             try {
                 a.abrir();
                  a.cerrar();
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, ex);
             } catch (SQLException ex) {
                 Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, ex);
             }
   
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
    public void Autoupdate_lotes(int a, String fecha, ArrayList<String> arr, int i,String banda) throws SQLException{
    PreparedStatement st = null;
    String s="";   
    try {// realizar avances
            abrir();
            conexion.setAutoCommit(false);
            if(arr.get(i).equals("montado")){
            s = "update avance set "+arr.get(i)+"="+banda+","+arr.get(i+1)+"='"+fecha+"',"+arr.get(i+2)+"='P' where id_prog="+a;
            }else{
            s = "update avance set "+arr.get(i)+"=1,"+arr.get(i+1)+"='"+fecha+"',"+arr.get(i+2)+"='P' where id_prog="+a;
            }
            st = conexion.prepareStatement(s);
            st.executeUpdate();
             conexion.commit();
           st.close();
        } catch (Exception e) {
            Logger.getLogger(Avances.class.getName()).log(Level.SEVERE, null, e);
            try {
                conexion.rollback();
            } catch (Exception o) {
                System.out.println(o.getMessage());
            }
        }    
    }
}
