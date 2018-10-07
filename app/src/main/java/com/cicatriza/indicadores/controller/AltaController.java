package com.cicatriza.indicadores.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.fragment.TratamentosFragment;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.helper.DateUtil;
import com.cicatriza.indicadores.model.Admissao;
import com.cicatriza.indicadores.model.Alta;
import com.cicatriza.indicadores.model.Paciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AltaController {

    private Context context;
    private FragmentActivity activity;
    private String idPaciente, idEnfermeiro, date;

    private Admissao admissao = new Admissao();
    private Alta alta = new Alta();

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacientesRef = firebaseRef.child("pacientes");
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");



    public AltaController(Context context, FragmentActivity activity, String idPaciente, String date, String idEnfermeiro) {
        this.context = context;
        this.activity = activity;
        this.idPaciente = idPaciente;
        this.idEnfermeiro = idEnfermeiro;
        this.date = date;
    }

    public void callAlta() {

        pacientesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(idPaciente)) {
                    Toast.makeText(context, "Não existe Admissão para este paciente.",
                            Toast.LENGTH_LONG).show();
                } else {
                    insereAlta();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void insereAlta() {
        if (date.matches("")) {
            date = DateUtil.dataAtual();
            alta.setData(date);
        } else {
            alta.setData(date);
        }
        alta.setEnfermeiro(this.idEnfermeiro);

        Query lastQuery = admissoesRef.child(idPaciente).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    if(child.child("idAlta").getValue() != null) {
                        Toast.makeText(context, "Paciente " + idPaciente + " já recebeu alta.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        alta.setIdAdmissao(child.getKey().toString());
                        String newAltaRef = alta.salvar(idPaciente);
                        admissao.setIdAlta(newAltaRef);
                        admissoesRef.child(idPaciente).child(child.getKey().toString()).
                                child("idAlta").setValue(newAltaRef);

                        Toast.makeText(context, "Paciente " + idPaciente + " recebeu alta.",
                                Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
