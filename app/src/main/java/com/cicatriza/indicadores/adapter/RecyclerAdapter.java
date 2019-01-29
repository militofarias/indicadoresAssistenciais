package com.cicatriza.indicadores.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.model.Paciente;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Paciente> listaPacientes;

    public RecyclerAdapter(List<Paciente> lista) {
        this.listaPacientes = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemlista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recycler_item, parent, false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Paciente paciente = listaPacientes.get(position);

        holder.idPaciente.setText("Paciente " + paciente.getId());
        if (paciente.getAdmissao() == null) {
            holder.dataAdmissao.setText("Admissão: " + "PROCESSANDO");
        } else {
            holder.dataAdmissao.setText("Admissão: " + paciente.getAdmissao());
        }
        if (paciente.getAlta() == null) {
            holder.dataAlta.setText("Alta: " + "em Tratamento");
        } else {
            holder.dataAlta.setText("Alta: " + paciente.getAlta());
        }
    }

    @Override
    public int getItemCount() {
        return this.listaPacientes.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView idPaciente, dataAdmissao, dataAlta;

        public MyViewHolder(View itemView) {
            super(itemView);

            idPaciente = itemView.findViewById(R.id.textIdPacienteItem);
            dataAdmissao = itemView.findViewById(R.id.textAdmItem);
            dataAlta = itemView.findViewById(R.id.textAltaitem);
        }
    }

}
