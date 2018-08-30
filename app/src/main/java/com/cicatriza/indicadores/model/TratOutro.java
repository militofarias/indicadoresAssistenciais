package com.cicatriza.indicadores.model;

import java.util.Date;

public class TratOutro {

    private Integer id;
    private Date data;
    private String especificacao;
    private Usuario enfermeiro;

    public TratOutro() {
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

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public Usuario getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(Usuario enfermeiro) {
        this.enfermeiro = enfermeiro;
    }
}
