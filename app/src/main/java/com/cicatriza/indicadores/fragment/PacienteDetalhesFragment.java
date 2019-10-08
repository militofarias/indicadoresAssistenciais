package com.cicatriza.indicadores.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.adapter.RecyclerAdmAltaAdapter;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.model.Paciente;
import com.google.firebase.database.DatabaseReference;

public class PacienteDetalhesFragment extends Fragment {

    public RecyclerView recyclerViewAdmAlta, recyclerViewTratamento;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacienteRef = firebaseRef.child("pacientes");
    private Paciente paciente;

    public PacienteDetalhesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_paciente_detalhes, container, false);

//        recyclerViewAdmAlta = view.findViewById(R.id.recyclerviewDetailAdmissoesAltas);
//        recyclerViewTratamento = view.findViewById(R.id.recyclerviewDetailTratamentos);


//        CONFIGURAR ADAPTER
//        RecyclerAdmAltaAdapter recyclerAdmAltaAdapter = new RecyclerAdmAltaAdapter();

//        CONFIGURAR RECYCLERVIEWS
//        RecyclerView.LayoutManager admAltaLayoutManager = new LinearLayoutManager(getContext());
//        recyclerViewAdmAlta.setLayoutManager(admAltaLayoutManager);
//        recyclerViewAdmAlta.setHasFixedSize(false);
//        recyclerViewAdmAlta.setAdapter(recyclerAdmAltaAdapter);

//        RecyclerView.LayoutManager tratamentoLayoutManager = new LinearLayoutManager(getContext());
//        recyclerViewTratamento.setLayoutManager(tratamentoLayoutManager);
//        recyclerViewAdmAlta.setHasFixedSize(false);
//        recyclerViewTratamento.setAdapter();

        return view;
    }
}