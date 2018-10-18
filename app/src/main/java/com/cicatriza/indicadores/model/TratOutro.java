package com.cicatriza.indicadores.model;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class TratOutro {

    private String idPaciente;
    private String data;
    private String especificacao;
    private String enfermeiro;

    public TratOutro() {
    }

    public void salvar(String idPaciente) {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference admissaoRef = firebaseRef.child("tratamentos").child("outros")
                .child(idPaciente);
        admissaoRef.push().setValue(this);
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public String getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(String enfermeiro) {
        this.enfermeiro = enfermeiro;
    }
}
