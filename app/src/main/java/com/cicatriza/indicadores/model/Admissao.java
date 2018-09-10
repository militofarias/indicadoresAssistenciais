package com.cicatriza.indicadores.model;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class Admissao {

    private String idPaciente;
    private String data;
    private String enfermeiro;
    private String idAlta;

    public Admissao() {
    }

    public void salvar(String idPaciente) {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference admissaoRef = firebaseRef.child("admissoes").child(idPaciente);
        admissaoRef.push().setValue(this);
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

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdAlta() {
        return idAlta;
    }

    public void setIdAlta(String idAlta) {
        this.idAlta = idAlta;
    }
}
