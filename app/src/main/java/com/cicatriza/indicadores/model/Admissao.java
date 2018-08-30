package com.cicatriza.indicadores.model;

import java.util.Date;

public class Admissao {

    private Integer id;
    private Date data;
    private Usuario enfermeiro;

    public Admissao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(Usuario enfermeiro) {
        this.enfermeiro = enfermeiro;
    }
}
