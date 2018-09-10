package com.cicatriza.indicadores.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;
import android.widget.Toast;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.fragment.TratamentosFragment;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.helper.DateUtil;
import com.cicatriza.indicadores.model.Admissao;
import com.cicatriza.indicadores.model.Paciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AdmissaoController {

    private Context context;
    private FragmentActivity activity;
    private String idEnfermeiro;

    private Paciente paciente = new Paciente();
    private Admissao admissao = new Admissao();

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TratamentosFragment tratamentosFragment = new TratamentosFragment();

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacientesRef = firebaseRef.child("pacientes");
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");


    public AdmissaoController(Context context, FragmentActivity activity, String idEnfermeiro) {
        this.context = context;
        this.activity = activity;
        this.idEnfermeiro = idEnfermeiro;
    }


    public void admissaoPacienteNaoExiste(final String pac) {

        pacientesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(pac)) {
                    admissao.setEnfermeiro(idEnfermeiro);
                    admissao.setData(DateUtil.dataAtual());
                    admissao.salvar(pac);

                    paciente.setId(pac);
                    paciente.salvar(pac);
                    vaiPraPaginaTratamentos();
                } else {
                    pacientePossuiAdmissao(pac);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pacientePossuiAdmissao(final String pac) {
        admissoesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot admissaoUnit) {
                if(!admissaoUnit.hasChild(pac)) {
                    dialogPegarData(pac);

                } else {
                    admissaoPacientePossuiAlta(pac);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void admissaoPacientePossuiAlta(final String pac) {
        Query lastQuery = admissoesRef.child(pac).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    if (child.child("idAlta").getValue() == null) {
                        dialogPossuiAdmissao(pac);

                    } else {
                        admissao.setEnfermeiro(idEnfermeiro);
                        admissao.setData(DateUtil.dataAtual());
                        admissao.salvar(pac);
                        vaiPraPaginaTratamentos();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void dialogPegarData(final String pac) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context,
                android.R.style.Theme_Holo_Light_Dialog, dateSetListener,
                year, month, day);
        dialog.setTitle("Selecione data de admissão");
        dialog.show();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                admissao.setData(dayOfMonth + "/" + month + "/" + year);
                admissao.setEnfermeiro(idEnfermeiro);
                admissao.salvar(pac);
                vaiPraPaginaTratamentos();
            }
        };
    }

    public void dialogPossuiAdmissao(final String pac) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Atenção:");
        dialog.setMessage("Paciente " + pac + " já possui uma admissão em aberto. Deseja realmente criar outra admissão?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                admissao.setEnfermeiro(idEnfermeiro);
                admissao.setData(DateUtil.dataAtual());
                admissao.salvar(pac);
                vaiPraPaginaTratamentos();
            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Admissão Cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.create();
        dialog.show();
    }

    public void vaiPraPaginaTratamentos() {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, tratamentosFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
