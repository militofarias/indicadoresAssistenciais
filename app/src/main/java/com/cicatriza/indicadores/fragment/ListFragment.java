package com.cicatriza.indicadores.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.adapter.RecyclerListAdapter;
import com.cicatriza.indicadores.helper.ConfiguracaoFirebase;
import com.cicatriza.indicadores.helper.RecyclerItemClickListener;
import com.cicatriza.indicadores.model.Paciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView countTotal;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
    private DatabaseReference pacienteRef = firebaseRef.child("pacientes");
    private ValueEventListener valueEventListenerPacientes;

    private RecyclerListAdapter adapter;
    final private List<Paciente> listaPacientes = new ArrayList<>();

    public ListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.database_recyclerview);
        countTotal = view.findViewById(R.id.textEntradasSaidasLbl);


        //Click nos Itens
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Paciente paciente = listaPacientes.get(position);
                        Toast.makeText(getContext(), "Click no Paciente " + paciente.getId(), Toast.LENGTH_SHORT).show();
//                        PacienteDetalhesFragment detalhesFragment = new PacienteDetalhesFragment();
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.ListFrameLayout, detalhesFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Paciente paciente = listaPacientes.get(position);
                        Toast.makeText(getContext(), "Click LONGO no Paciente " + paciente.getId(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
        );

        return view;
    }

    public void carregarListaPacientes() {

        //Listar Pacientes
        valueEventListenerPacientes = pacienteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaPacientes.clear();

                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                    Paciente paciente = childSnapShot.getValue(Paciente.class);
                    listaPacientes.add(paciente);
                }

                adapter.notifyDataSetChanged();

                countTotal.setText(Html.fromHtml("<b>Total: </b>" +
                        String.valueOf(listaPacientes.size()), 1));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Configurar Adapter
        adapter = new RecyclerListAdapter(listaPacientes);

        //Configurar recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        carregarListaPacientes();
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        pacienteRef.removeEventListener(valueEventListenerPacientes);
    }
}
