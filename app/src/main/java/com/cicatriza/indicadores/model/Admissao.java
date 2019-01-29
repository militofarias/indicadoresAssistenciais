package com.cicatriza.indicadores.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


public class Admissao {

    private String idPaciente;
    private String data;
    private String enfermeiro;
    private String idAlta;
    private Paciente paciente;

    public Admissao() {
    }

    public void salvar(final String idPaciente) {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference admissaoRef = firebaseRef.child("admissoes").child(idPaciente);
        String newRef = admissaoRef.push().getKey();
        admissaoRef.child(newRef).setValue(this);

        paciente = new Paciente();
        paciente.setAdmissao(newRef , this);
        paciente.salvar(idPaciente);

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
