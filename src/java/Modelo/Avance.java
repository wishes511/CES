/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author gateway1
 */
public class Avance {
    private String fechaentrega="",fechacorte="",fechaprecorte="",fechapespunte="",fechadeshebrado="",fechaojillado="",
            fechainspeccion="",fechapreacabado="",fechamontado="",fechapt="",status="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(String fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public String getFechacorte() {
        return fechacorte;
    }

    public void setFechacorte(String fechacorte) {
        this.fechacorte = fechacorte;
    }

    public String getFechaprecorte() {
        return fechaprecorte;
    }

    public void setFechaprecorte(String fechaprecorte) {
        this.fechaprecorte = fechaprecorte;
    }

    public String getFechapespunte() {
        return fechapespunte;
    }

    public void setFechapespunte(String fechapespunte) {
        this.fechapespunte = fechapespunte;
    }

    public String getFechadeshebrado() {
        return fechadeshebrado;
    }

    public void setFechadeshebrado(String fechadeshebrado) {
        this.fechadeshebrado = fechadeshebrado;
    }

    public String getFechaojillado() {
        return fechaojillado;
    }

    public void setFechaojillado(String fechaojillado) {
        this.fechaojillado = fechaojillado;
    }

    public String getFechainspeccion() {
        return fechainspeccion;
    }

    public void setFechainspeccion(String fechainspeccion) {
        this.fechainspeccion = fechainspeccion;
    }

    public String getFechapreacabado() {
        return fechapreacabado;
    }

    public void setFechapreacabado(String fechapreacabado) {
        this.fechapreacabado = fechapreacabado;
    }

    public String getFechamontado() {
        return fechamontado;
    }

    public void setFechamontado(String fechamontado) {
        this.fechamontado = fechamontado;
    }

    public String getFechapt() {
        return fechapt;
    }

    public void setFechapt(String fechapt) {
        this.fechapt = fechapt;
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
}
