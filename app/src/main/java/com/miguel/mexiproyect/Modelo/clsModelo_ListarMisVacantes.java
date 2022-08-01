package com.miguel.mexiproyect.Modelo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel.mexiproyect.MiVacanteActivity;
import com.miguel.mexiproyect.R;
import com.miguel.mexiproyect.VacanteActivity;

import java.util.ArrayList;

public class clsModelo_ListarMisVacantes extends RecyclerView.Adapter<clsModelo_ListarMisVacantes.viewHolderListarMisVacantes>{
    ArrayList<clsMiVacante> misVacantes;

    public clsModelo_ListarMisVacantes(ArrayList<clsMiVacante> misVacantes){
        this.misVacantes = misVacantes;
    }

    @NonNull
    @Override
    public viewHolderListarMisVacantes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mivacante,null,false);
        return new viewHolderListarMisVacantes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderListarMisVacantes holder, int position) {
        holder.txtNombreMiVac.setText(misVacantes.get(position).getVacante());
        holder.txtTurno.setText(misVacantes.get(position).getTurno());
        holder.txtIdVac.setText(String.valueOf(misVacantes.get(position).getIdVacante()));

        //Eventos
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return misVacantes.size();
    }

    public class viewHolderListarMisVacantes extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView txtNombreMiVac;
        TextView txtTurno;
        TextView txtIdVac;

        ImageView ivInfoMiVac;

        public viewHolderListarMisVacantes(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            txtNombreMiVac = itemView.findViewById(R.id.txtNombreMiVac);
            txtTurno = itemView.findViewById(R.id.txtTurno);
            txtIdVac = itemView.findViewById(R.id.txtIdVac);
            ivInfoMiVac = itemView.findViewById(R.id.ivInfoMiVac);
        }

        public void setOnClickListeners() {
            ivInfoMiVac.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ivInfoMiVac:
                    Intent miVacanteAct = new Intent(context, MiVacanteActivity.class);
                    miVacanteAct.putExtra("intIdV",txtIdVac.getText());
                    context.startActivity(miVacanteAct);
                    break;
            }
        }
    }
}
