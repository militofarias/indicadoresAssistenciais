package com.cicatriza.indicadores.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.model.Admissao;
import com.google.firebase.database.DatabaseReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerguntaAdmissaoFragment extends Fragment {

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");

    private EditText dataAdmissao;
    private Button entrarData;

    private Admissao admissao = new Admissao();

    public PerguntaAdmissaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pergunta_admissao, container, false);



        return view;
    }

}
