package com.cicatriza.indicadores.controller;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;
import android.widget.Toast;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.fragment.DatePickerFragment;
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
    private String idPaciente, idEnfermeiro;

    private Paciente paciente = new Paciente();
    private Admissao admissao = new Admissao();

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TratamentosFragment tratamentosFragment = new TratamentosFragment();

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacientesRef = firebaseRef.child("pacientes");
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");



    public AdmissaoController(Context context, FragmentActivity activity, String idPaciente, String idEnfermeiro) {
        this.context = context;
        this.activity = activity;
        this.idPaciente = idPaciente;
        this.idEnfermeiro = idEnfermeiro;
    }


    public void admissaoPacienteNaoExiste() {

        pacientesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(idPaciente)) {
                    admissao.setEnfermeiro(idEnfermeiro);
                    admissao.setData(DateUtil.dataAtual());
                    admissao.salvar(idPaciente);

                    paciente.setId(idPaciente);
                    paciente.salvar(idPaciente);
                    vaiPraPaginaTratamentos();
                } else {
                    pacientePossuiAdmissao();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pacientePossuiAdmissao() {
        admissoesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot admissaoUnit) {
                if(!admissaoUnit.hasChild(idPaciente)) {
                    dialogPegarData();

                } else {
                    admissaoPacientePossuiAlta();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void admissaoPacientePossuiAlta() {
        Query lastQuery = admissoesRef.child(idPaciente).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    if (child.child("idAlta").getValue() == null) {
                        dialogPossuiAdmissao();

                    } else {
                        admissao.setEnfermeiro(idEnfermeiro);
                        admissao.setData(DateUtil.dataAtual());
                        admissao.salvar(idPaciente);
                        vaiPraPaginaTratamentos();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void dialogPegarData() {
        DatePickerFragment date = new DatePickerFragment();

        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putString("title", "Selecione data de admissão");
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month,
                                  int dayOfMonth) {
                month = month + 1;
                System.out.println("Resultado: " + dayOfMonth + "/" + month + "/" + year);
                admissao.setData(dayOfMonth + "/" + month + "/" + year);
                admissao.setEnfermeiro(idEnfermeiro);
                admissao.salvar(idPaciente);
                vaiPraPaginaTratamentos();
            }
        };

        date.setCallBack(ondate);
        date.show(activity.getFragmentManager(), "Date Picker");

    }
//    public void dialogPegarData() {
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog dialog = new DatePickerDialog(context,
//                android.R.style.Theme_Holo_Light_Dialog, dateSetListener,
//                year, month, day);
//        dialog.setTitle("Selecione data de admissão");
//        dialog.show();
//
//        dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                System.out.println("Resultado: " + dayOfMonth + "/" + month + "/" + year);
////                admissao.setData(dayOfMonth + "/" + month + "/" + year);
////                admissao.setEnfermeiro(idEnfermeiro);
////                admissao.salvar(idPaciente);
////                vaiPraPaginaTratamentos();
//            }
//        };
//    }

    public void dialogPossuiAdmissao() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Atenção:");
        dialog.setMessage("Paciente " + idPaciente + " já possui uma admissão em aberto. Deseja realmente criar outra admissão?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                admissao.setEnfermeiro(idEnfermeiro);
                admissao.setData(DateUtil.dataAtual());
                admissao.salvar(idPaciente);
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
