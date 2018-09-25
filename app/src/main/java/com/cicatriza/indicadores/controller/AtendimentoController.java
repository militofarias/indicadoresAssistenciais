package com.cicatriza.indicadores.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.fragment.TratamentosFragment;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AtendimentoController {

    private Context context;
    private FragmentActivity activity;
    private String idPaciente, idEnfermeiro;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacientesRef = firebaseRef.child("pacientes");
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");

    public AtendimentoController(Context context, FragmentActivity activity, String idPaciente,
                                 String idEnfermeiro) {
        this.context = context;
        this.activity = activity;
        this.idPaciente = idPaciente;
        this.idEnfermeiro = idEnfermeiro;
    }

    public void callAtendimento() {
        pacientesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(idPaciente)) {
                    Toast.makeText(context, "Não existe Admissão para este paciente.",
                            Toast.LENGTH_LONG).show();
                } else {
                    verificaAdmissao();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void verificaAdmissao() {
        Query lastQuery = admissoesRef.child(idPaciente).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    if(child.child("idAlta").getValue() != null) {
                        Toast.makeText(context, "Paciente " + idPaciente + " já recebeu alta." +
                                        "\nCaso seja necessário, abra outra admissão.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        vaiPraPaginaTratamentos();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void vaiPraPaginaTratamentos() {
        Bundle bundle = new Bundle();
        bundle.putString("idPaciente", idPaciente);
        bundle.putString("enfermeiro", idEnfermeiro);

        TratamentosFragment tratamentosFragment = new TratamentosFragment();
        tratamentosFragment.setArguments(bundle);

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, tratamentosFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
