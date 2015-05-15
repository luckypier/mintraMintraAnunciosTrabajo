package com.mtpe.model;

import com.mtpe.model.Alert;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * Created by precuay on 4/19/15.
 */
@Entity
@Table(name = "JOBANNOUNCEMENT")
public class JobAnnouncement {

    public static Integer NEW_STATUS = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //0 new
    @NotNull
    private Integer status;

    @Size(max = 20)
    @NotEmpty
    private String numreg;

    @Size(max = 20)
    private String codinst;

    @Size(max = 150)
    private String link;

    @Size(max = 100)
    private String institucion;

    @Size(max = 500)
    private String cargo;

    @Size(max = 4000)
    private String requisitos;

    @Size(max = 2000)
    private String descripcion;

    @Size(max = 250)
    private String ocupacion;

    @Size(max = 50)
    private String nroVacantes;

    @Size(max = 50)
    private String nroConvocatoria;

    @Size(max = 500)
    private String anotacion;

    //private ArrayList<Alert> alerts;

    // G&S


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumreg() {
        return numreg;
    }

    public void setNumreg(String numreg) {
        this.numreg = numreg;
    }

    public String getCodinst() {
        return codinst;
    }

    public void setCodinst(String codinst) {
        this.codinst = codinst;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNroVacantes() {
        return nroVacantes;
    }

    public void setNroVacantes(String nroVacantes) {
        this.nroVacantes = nroVacantes;
    }

    public String getNroConvocatoria() {
        return nroConvocatoria;
    }

    public void setNroConvocatoria(String nroConvocatoria) {
        this.nroConvocatoria = nroConvocatoria;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    /* public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    } */
}
