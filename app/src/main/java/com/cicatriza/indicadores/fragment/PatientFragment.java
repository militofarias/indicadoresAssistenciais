package com.cicatriza.indicadores.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.controller.AdmissaoController;
import com.cicatriza.indicadores.controller.AltaController;
import com.cicatriza.indicadores.controller.AtendimentoController;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.helper.DateUtil;
import com.cicatriza.indicadores.model.Admissao;
import com.cicatriza.indicadores.model.Paciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference nurseRef = firebaseRef.child("enfermeiros");

    private EditText idPaciente;
    private Button btnAdmissao, btnAtendimento, btnAlta;
    private Spinner spinEnf;

    public PatientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.patient_id, container, false);

        idPaciente = view.findViewById(R.id.patientIdField);
        btnAdmissao = view.findViewById(R.id.buttonAdmissao);
        btnAtendimento = view.findViewById(R.id.buttonAtendimento);
        btnAlta = view.findViewById(R.id.buttonAlta);
        spinEnf = view.findViewById(R.id.spinnerEnfermeiros);

        //CarregarSpinner
        nurseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> enfermeiros = new ArrayList<String>();

                for (DataSnapshot enfermeiroSnapshot : dataSnapshot.getChildren()) {
                    String enfermeiroName = enfermeiroSnapshot.getKey().toString();
                    enfermeiros.add(enfermeiroName);
                }

                Spinner enfSpinner = (Spinner) view.findViewById(R.id.spinnerEnfermeiros);
                ArrayAdapter<String> enfermeirosAdapter = new ArrayAdapter<String>(
                        getContext(), android.R.layout.simple_spinner_item, enfermeiros);
                enfermeirosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                enfSpinner.setAdapter(enfermeirosAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Fim do carregarSpinner


        //Click em Admissao
        btnAdmissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNumPaciente = idPaciente.getText().toString();

                if(idNumPaciente.matches("")) {
                    Toast.makeText(getContext(), "Insira o ID do Paciente.",
                            Toast.LENGTH_SHORT).show();

                } else {

                    try {
                        AdmissaoController admissaoController = new AdmissaoController(getContext(),
                                getActivity(), idNumPaciente, spinEnf.getSelectedItem().toString());
                        admissaoController.callAdmissao();

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //Click em Atendimento
        btnAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNumPaciente = idPaciente.getText().toString();

                if(idNumPaciente.matches("")) {
                    Toast.makeText(getContext(), "Insira o ID do Paciente.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    try {
                        AtendimentoController atendimentoController = new AtendimentoController(
                                getContext(), getActivity(),
                                idNumPaciente,  spinEnf.getSelectedItem().toString());
                        atendimentoController.callAtendimento();

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //Click em Alta
        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNumPaciente = idPaciente.getText().toString();

                if(idNumPaciente.matches("")) {
                    Toast.makeText(getContext(), "Insira o ID do Paciente.",
                            Toast.LENGTH_SHORT).show();

                } else {

                    try {
                        AltaController altaController = new AltaController(getContext(),
                                getActivity(), idNumPaciente,  spinEnf.getSelectedItem().toString());
                        altaController.callAlta();

                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

}
