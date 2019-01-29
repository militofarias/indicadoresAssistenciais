package com.cicatriza.indicadores.model;

import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Paciente implements Serializable {

    private String id;
    private HashMap<String, Admissao> admissao = new HashMap<>();
    private HashMap<String, Alta> alta = new HashMap<>();
    private List<TratCurativo> curativo;
    private List<TratAvaliacao> avaliacao;
    private List<TratEncaminhamento> encaminhamento;
    private List<TratOutro> outro;

    public Paciente() {
    }

    public void salvar(String paciente) {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference pacienteRef = firebaseRef.child("pacientes");
        setId(paciente);
        pacienteRef.child(paciente).setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Admissao> getAdmissao() {
        return admissao;
    }

    public void setAdmissao(String key, Admissao admissao) {
        this.admissao.put(key, admissao);
    }

    public HashMap<String, Alta> getAlta() {
        return alta;
    }

    public void setAlta(String key, Alta alta) {
        this.alta.put(key, alta);
    }

    public List<TratCurativo> getCurativo() {
        return curativo;
    }

    public void setCurativo(List<TratCurativo> curativo) {
        this.curativo = curativo;
    }

    public List<TratAvaliacao> getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(List<TratAvaliacao> avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<TratEncaminhamento> getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(List<TratEncaminhamento> encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public List<TratOutro> getOutro() {
        return outro;
    }

    public void setOutro(List<TratOutro> outro) {
        this.outro = outro;
    }
}
