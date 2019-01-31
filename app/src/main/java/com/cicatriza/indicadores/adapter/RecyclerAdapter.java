package com.cicatriza.indicadores.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.model.Admissao;
import com.cicatriza.indicadores.model.Alta;
import com.cicatriza.indicadores.model.Paciente;

import java.util.List;
import java.util.Map;

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

        if (paciente.getAdmissao().isEmpty()) {
            holder.dataAdmissao.setText(Html.fromHtml(
                    "<b>Admissão:</b> " + "<i>-</i>", 1));
        } else {
            for (Map.Entry<String, Admissao> entry : paciente.getAdmissao().entrySet()) {
                holder.dataAdmissao.setText(Html.fromHtml(
                        "<b>Admissão:</b> " + "<i>" + entry.getValue().getData() + "</i>", 1));
            }
        }
        if (paciente.getAlta().isEmpty()) {
            holder.dataAlta.setText(Html.fromHtml(
                    "<b>Alta:</b> " + "<i>em Tratamento</i>", 1));
        } else {
            for (Map.Entry<String, Alta> entry : paciente.getAlta().entrySet()) {
                holder.dataAlta.setText(Html.fromHtml(
                        "<b>Alta:</b> " + "<i>" + entry.getValue().getData() + "</i>", 1));
            }
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
