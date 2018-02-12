/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author gateway1
 */
public class Programa {
    private int programa,lote,estilo,pares,mes,id,year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getPares() {
        return pares;
    }

    public void setPares(int pares) {
        this.pares = pares;
    }
    private String corrida="",combinacion="",fechae="",codigo="",status="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getPrograma() {
        return programa;
    }

    public void setPrograma(int programa) {
        this.programa = programa;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public int getEstilo() {
        return estilo;
    }

    public void setEstilo(int estilo) {
        this.estilo = estilo;
    }
    public String getCorrida() {
        return corrida;
    }

    public void setCorrida(String corrida) {
        this.corrida = corrida;
    }

    public String getCombinacion() {
        return combinacion;
    }

    public void setCombinacion(String combinacion) {
        this.combinacion = combinacion;
    }

    public String getFechae() {
        return fechae;
    }

    public void setFechae(String fechae) {
        this.fechae = fechae;
    }
}
