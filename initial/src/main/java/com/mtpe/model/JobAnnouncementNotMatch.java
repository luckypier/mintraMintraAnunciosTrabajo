package com.mtpe.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by precuay on 4/30/15.
 */
@Entity
@Table(name = "JOBANNOUNCEMENTNOTMATCH")
public class JobAnnouncementNotMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(max = 20)
    @NotEmpty
    private String numreg;

    @Size(max = 20)
    private String codinst;

    // G&S


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
