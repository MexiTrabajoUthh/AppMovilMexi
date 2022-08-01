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

import com.miguel.mexiproyect.PostulacionActivity;
import com.miguel.mexiproyect.R;
import com.miguel.mexiproyect.VacanteActivity;

import java.util.ArrayList;

public class clsModelo_ListarVacantesNear extends RecyclerView.Adapter<clsModelo_ListarVacantesNear.viewHolderListarVacantesNear>{
    ArrayList<clsVacanteList> vacantesNear;

    public clsModelo_ListarVacantesNear(ArrayList<clsVacanteList> vacantesNear){
        this.vacantesNear = vacantesNear;
    }

    @NonNull
    @Override
    public viewHolderListarVacantesNear onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacantecerca,null,false);
        return new viewHolderListarVacantesNear(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderListarVacantesNear holder, int position) {
        holder.txtNombreV.setText(vacantesNear.get(position).getVacante());
        holder.txtEmpleador.setText(vacantesNear.get(position).getNombreEmpleador());
        holder.txtIdVacante.setText(String.valueOf(vacantesNear.get(position).getIdVacante()));
        holder.txtIdTrb.setText(String.valueOf(vacantesNear.get(position).getIdTrabajador()));

        //Eventos
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return vacantesNear.size();
    }

    public class viewHolderListarVacantesNear extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView txtNombreV;
        TextView txtEmpleador;
        TextView txtIdVacante;
        TextView txtIdTrb;

        ImageView ivInfoV;

        public viewHolderListarVacantesNear(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            txtNombreV = itemView.findViewById(R.id.txtNombreV);
            txtEmpleador = itemView.findViewById(R.id.txtEmpleador);
            txtIdVacante = itemView.findViewById(R.id.txtIdVacante);
            txtIdTrb = itemView.findViewById(R.id.txtIdTrb);
            ivInfoV = itemView.findViewById(R.id.ivInfoV);
        }

        public void setOnClickListeners() {
            ivInfoV.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ivInfoV:
                    Intent vacanteAct = new Intent(context, VacanteActivity.class);
                    vacanteAct.putExtra("intIdV",txtIdVacante.getText());
                    vacanteAct.putExtra("idUser",txtIdTrb.getText());
                    context.startActivity(vacanteAct);
                    break;
            }
        }
    }
}
