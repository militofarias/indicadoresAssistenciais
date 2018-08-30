package com.cicatriza.indicadores.model;

public class Paciente {

    private Integer id;
    private Admissao admissao;
    private Alta alta;
    private TratCurativo curativo;
    private TratAvaliacao avaliacao;
    private TratEncaminhamento encaminhamento;
    private TratOutro outro;

    public Paciente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Admissao getAdmissao() {
        return admissao;
    }

    public void setAdmissao(Admissao admissao) {
        this.admissao = admissao;
    }

    public Alta getAlta() {
        return alta;
    }

    public void setAlta(Alta alta) {
        this.alta = alta;
    }

    public TratCurativo getCurativo() {
        return curativo;
    }

    public void setCurativo(TratCurativo curativo) {
        this.curativo = curativo;
    }

    public TratAvaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(TratAvaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public TratEncaminhamento getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(TratEncaminhamento encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public TratOutro getOutro() {
        return outro;
    }

    public void setOutro(TratOutro outro) {
        this.outro = outro;
    }
}
