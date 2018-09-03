package com.cicatriza.indicadores.fragment;

import android.app.DatePickerDialog;
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
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.helper.DateUtil;
import com.cicatriza.indicadores.model.Admissao;
import com.cicatriza.indicadores.model.Paciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacientesRef = firebaseRef.child("pacientes");
    private DatabaseReference admissoesRef = firebaseRef.child("admissoes");
    private DatabaseReference nurseRef = firebaseRef.child("enfermeiros");

    private EditText idPaciente;
    private Button btnAdmissao, btnAtendimento, btnAlta;
    private Spinner spinEnf;
    private TratamentosFragment tratamentosFragment = new TratamentosFragment();
    private PerguntaAdmissaoFragment perguntaAdmissaoFragment = new PerguntaAdmissaoFragment();
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Paciente paciente = new Paciente();
    private Admissao admissao = new Admissao();

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

                try {
                    admissaoPacienteNaoExiste(idNumPaciente);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //Click em Atendimento
        btnAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    vaiPraPaginaTratamentos();

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //Click em Alta
        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    vaiPraPaginaTratamentos();

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void admissaoPacienteNaoExiste(final String pac) {

        pacientesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(pac)) {
                    admissao.setEnfermeiro(spinEnf.getSelectedItem().toString());
                    admissao.setData(DateUtil.dataAtual());
                    admissao.salvar(pac);

                    paciente.setId(pac);
                    paciente.salvar(pac);
                    vaiPraPaginaTratamentos();
                } else {
                    pacienteNaoPossuiAdmissao(pac);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void pacienteNaoPossuiAdmissao(final String pac) {
        admissoesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(pac)) {
                    dialogPegarData(pac);

                } else {
                    admissaoPacienteNaoPossuiAlta(pac);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void admissaoPacienteNaoPossuiAlta(String pac) {
        Toast.makeText(getContext(), "Paciente ja possui Admissao!", Toast.LENGTH_SHORT).show();

    }

    public void dialogPegarData(final String pac) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog, dateSetListener,
                year, month, day);
        dialog.setTitle("Selecione data de admissão");
        dialog.show();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                admissao.setData(dayOfMonth + "/" + month + "/" + year);
                admissao.setEnfermeiro(spinEnf.getSelectedItem().toString());
                admissao.salvar(pac);
                vaiPraPaginaTratamentos();
            }
        };
    }

    public void vaiPraPaginaTratamentos() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, tratamentosFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
