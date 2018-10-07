package com.cicatriza.indicadores.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cicatriza.indicadores.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TratamentosFragment extends Fragment {

    String idPaciente, enfermeiro, date;

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
        return view;
    }

}
