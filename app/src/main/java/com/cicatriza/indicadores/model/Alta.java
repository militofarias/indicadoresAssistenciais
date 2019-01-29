package com.cicatriza.indicadores.model;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Alta {

    private String idPaciente;
    private String data;
    private String enfermeiro;
    private String idAdmissao;
    private  Paciente paciente;

    public Alta() {
    }

    public String salvar(String idPaciente) {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference altaRef = firebaseRef.child("altas").child(idPaciente);
        String newRef = altaRef.push().getKey();
        altaRef.child(newRef).setValue(this);

        paciente = new Paciente();
        paciente.setAlta(newRef, this);
        paciente.salvar(idPaciente);

        return newRef;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String id) {
        this.idPaciente = id;
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

    public String getIdAdmissao() {
        return idAdmissao;
    }

    public void setIdAdmissao(String idAdmissao) {
        this.idAdmissao = idAdmissao;
    }
}
