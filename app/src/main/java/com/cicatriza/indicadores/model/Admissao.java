package com.cicatriza.indicadores.model;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class Admissao {

    private Integer id;
    private String data;
    private String enfermeiro;
    private String pacienteId;

    public Admissao() {
    }

    public void salvar() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference admissaoRef = firebaseRef.child("admissao");
        admissaoRef.push().setValue(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(String enfermeiro) {
        this.enfermeiro = enfermeiro;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }
}
