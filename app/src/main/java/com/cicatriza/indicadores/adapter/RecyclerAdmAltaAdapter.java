package com.cicatriza.indicadores.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cicatriza.indicadores.R;
import com.cicatriza.indicadores.model.Admissao;
import com.cicatriza.indicadores.model.Alta;

import java.util.List;

public class RecyclerAdmAltaAdapter extends RecyclerView.Adapter<RecyclerAdmAltaAdapter.MyViewHolder> {

    private List<Admissao> admissaoList;
    private List<Alta> altaList;

    public RecyclerAdmAltaAdapter(List<Admissao> admList, List<Alta> altaList) {
        this.admissaoList = admList;
        this.altaList = altaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View admAltaItemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_recycler_iteracoes, parent, false);

        return new MyViewHolder(admAltaItemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Admissao admissao = admissaoList.get(position);
        Alta alta = altaList.get(position);

        holder.dataAdmissao.setText(admissao.getData());
        holder.enfAdmissao.setText(admissao.getEnfermeiro());
        if (admissao.getIdAlta().isEmpty()) {
            holder.dataAlta.setText("em Tratamento");
            holder.enfAlta.setText("-");
        } else {
            holder.dataAlta.setText(alta.getData());
            holder.enfAlta.setText(alta.getEnfermeiro());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dataAdmissao, dataAlta, enfAdmissao, enfAlta;

        public MyViewHolder(View itemView) {
            super(itemView);

            dataAdmissao = itemView.findViewById(R.id.textItemDataAdmissao);
            dataAlta = itemView.findViewById(R.id.textItemDataAlta);
            enfAdmissao = itemView.findViewById(R.id.textItemEnfAdmissao);
            enfAlta = itemView.findViewById(R.id.textItemEnfAlta);
        }
    }

}
