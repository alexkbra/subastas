/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.poli.subastas.runnable.dto;

/**
 *
 * @author 57304
 */
public class Data {

    String clickAction;
    String id;
    String status;
    String puja;
    
    String idEventos;
    String idSubastas;
    String idLotes;

    public Data(String clickAction, String id, String status, String puja) {
        this.clickAction = clickAction;
        this.id = id;
        this.status = status;
        this.puja = puja;
    }

    public Data() {
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPuja() {
        return puja;
    }

    public void setPuja(String puja) {
        this.puja = puja;
    }

    public String getIdEventos() {
        return idEventos;
    }

    public void setIdEventos(String idEventos) {
        this.idEventos = idEventos;
    }

    public String getIdSubastas() {
        return idSubastas;
    }

    public void setIdSubastas(String idSubastas) {
        this.idSubastas = idSubastas;
    }

    public String getIdLotes() {
        return idLotes;
    }

    public void setIdLotes(String idLotes) {
        this.idLotes = idLotes;
    }

    

}
