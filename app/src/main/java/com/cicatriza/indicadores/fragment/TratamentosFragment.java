package com.cicatriza.indicadores.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.controller.AtendimentoController;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class TratamentosFragment extends Fragment {

    String idPaciente, enfermeiro, date;
    Button btnCurativos, btnAvaliacoes, btnEncaminhamentos, btnOutros;

    public static TratamentosFragment newInstance(String idPaciente, String enfermeiro, String date) {

        Bundle args = new Bundle();
        args.putString("idPaciente", idPaciente);
        args.putString("enfermeiro", enfermeiro);
        args.putString("date", date);
        TratamentosFragment fragment = new TratamentosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.idPaciente = this.getArguments().getString("idPaciente");
        this.enfermeiro = this.getArguments().getString("enfermeiro");
        this.date = this.getArguments().getString("date");

    }

    public TratamentosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tratamentos, container, false);
        System.out.println(idPaciente + " - " + enfermeiro + " - " + date);

        btnAvaliacoes = view.findViewById(R.id.buttonAvaliacao);
        btnCurativos = view.findViewById(R.id.buttonCurativos);
        btnEncaminhamentos = view.findViewById(R.id.buttonEncaminhamento);
        btnOutros = view.findViewById(R.id.buttonOutros);

//Click em Curativos
        btnCurativos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtendimentoController atendimentoController = new AtendimentoController(
                        getContext(), getActivity(), idPaciente, date, enfermeiro);
                atendimentoController.callCurativos();
            }
        });

//Click em Avaliação
        btnAvaliacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtendimentoController atendimentoController = new AtendimentoController(
                        getContext(), getActivity(), idPaciente, date, enfermeiro);
                atendimentoController.callAvaliacao();
            }
        });

//Click em Outros Procedimentos
        btnOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtendimentoController atendimentoController = new AtendimentoController(
                        getContext(), getActivity(), idPaciente, date, enfermeiro);
                atendimentoController.callOutro();
            }
        });

//Click em Encaminhamento
        btnEncaminhamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtendimentoController atendimentoController = new AtendimentoController(
                        getContext(), getActivity(), idPaciente, date, enfermeiro);
                atendimentoController.callEncaminhamento();
            }
        });

        return view;
    }

}
