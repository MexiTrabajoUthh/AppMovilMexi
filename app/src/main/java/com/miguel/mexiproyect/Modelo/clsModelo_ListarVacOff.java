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

import com.miguel.mexiproyect.MiVacOffActivity;
import com.miguel.mexiproyect.MiVacanteActivity;
import com.miguel.mexiproyect.R;

import java.util.ArrayList;

public class clsModelo_ListarVacOff extends RecyclerView.Adapter<clsModelo_ListarVacOff.viewHolderListarVacOff>{
    ArrayList<clsMiVacante> misVacantes;

    public clsModelo_ListarVacOff(ArrayList<clsMiVacante> misVacantes){
        this.misVacantes = misVacantes;
    }

    @NonNull
    @Override
    public viewHolderListarVacOff onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mivacante,null,false);
        return new clsModelo_ListarVacOff.viewHolderListarVacOff(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderListarVacOff holder, int position) {
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

    public class viewHolderListarVacOff extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView txtNombreMiVac;
        TextView txtTurno;
        TextView txtIdVac;

        ImageView ivInfoMiVac;

        public viewHolderListarVacOff(@NonNull View itemView) {
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
                    Intent miVacanteOffAct = new Intent(context, MiVacOffActivity.class);
                    miVacanteOffAct.putExtra("intIdV",txtIdVac.getText());
                    context.startActivity(miVacanteOffAct);
                    break;
            }
        }
    }
}
