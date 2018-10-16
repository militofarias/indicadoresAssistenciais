package com.cicatriza.indicadores.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.fragment.PatientFragment;
import com.cicatriza.indicadores.fragment.TratamentosFragment;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.helper.DateUtil;
import com.cicatriza.indicadores.model.TratCurativo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Integer.parseInt;

public class AtendimentoController {

    private Context context;
    private FragmentActivity activity;
    private String idPaciente, idEnfermeiro, date;

    private TratCurativo curativos = new TratCurativo();

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacientesRef = firebaseRef.child("pacientes");
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");

    private DatabaseReference tratamentosRef = firebaseRef.child("tratamentos");
    private DatabaseReference curativosRef = tratamentosRef.child("curativos");
    private DatabaseReference avaliacoesRef = tratamentosRef.child("avaliacoes");
    private DatabaseReference encaminhamentosRef = tratamentosRef.child("encaminhamentos");
    private DatabaseReference outrosRef = tratamentosRef.child("outros");

    public AtendimentoController(Context context, FragmentActivity activity, String idPaciente,
                                 String date, String idEnfermeiro) {
        this.context = context;
        this.activity = activity;
        this.idPaciente = idPaciente;
        this.date = date;
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


    public void callCurativos() {
        curativos.setIdPaciente(this.idPaciente);
        curativos.setEnfermeiro(this.idEnfermeiro);
        curativos.setData(this.date);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context);
        alertDialog.setMessage("Informe a quantidade de curativos feitos:");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setNeutralButton("Enviar", null);

        alertDialog.setView(input);
        final AlertDialog ad = alertDialog.create();
        ad.show();

        ad.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString().matches("") ||
                        input.getText().toString().matches("0")) {
                    Toast.makeText(context, "Insira a quantidade de curativos válida.",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    curativos.setQtde(parseInt(input.getText().toString()));
                    ad.dismiss();
                    curativos.salvar(idPaciente);
                    Toast.makeText(context, "Paciente " + idPaciente + " foi atendido.",
                            Toast.LENGTH_LONG).show();
                    vaiPraPaginaInicial();
                }
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
        if (this.date.matches("")) {
            this.date = DateUtil.dataAtual();
        }

        TratamentosFragment tratamentosFragment = TratamentosFragment.newInstance(this.idPaciente,
                this.idEnfermeiro, this.date);

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, tratamentosFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void vaiPraPaginaInicial() {

        PatientFragment patientFragment = new PatientFragment();

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, patientFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
