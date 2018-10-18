package com.cicatriza.indicadores.model;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class TratEncaminhamento {

    private String idPaciente;
    private String data;
    private String enfermeiro;

    public TratEncaminhamento() {
    }

    public void salvar(String idPaciente) {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference admissaoRef = firebaseRef.child("tratamentos").child("encaminhamentos")
                .child(idPaciente);
        admissaoRef.push().setValue(this);
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
}
