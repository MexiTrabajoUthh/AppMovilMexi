package com.miguel.mexiproyect.Modelo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miguel.mexiproyect.PostulacionActivity;
import com.miguel.mexiproyect.R;

import java.util.ArrayList;

public class clsModelo_Listar extends RecyclerView.Adapter<clsModelo_Listar.viewHolderListaPostulaciones> {

    ArrayList<clsPostulacion> postulaciones;

    public clsModelo_Listar(ArrayList<clsPostulacion> postulaciones){
        this.postulaciones = postulaciones;
    }


    @NonNull
    @Override
    public viewHolderListaPostulaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listapostulaciones,null,false);
        return new viewHolderListaPostulaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderListaPostulaciones holder, int position) {
        holder.txtNombreP.setText(postulaciones.get(position).getNombre());
        holder.txtDirecc.setText(postulaciones.get(position).getDireccion());
        holder.txtIdP.setText(String.valueOf(postulaciones.get(position).getIdPostulacion()));
        Integer clase = Integer.valueOf(postulaciones.get(position).getClaseCandidato());
        String textobtn = "";
        switch (clase){
            case 0:
                textobtn = "Malo";
                holder.btnClase.setBackgroundColor(Color.rgb(255,59,59));
                break;
            case 1:
                textobtn = "Regular";
                holder.btnClase.setBackgroundColor(Color.rgb(237,181,38));
                break;
            case 2:
                textobtn = "Bueno";
                holder.btnClase.setBackgroundColor(Color.rgb(100,150,221));
                break;
            case 3:
                textobtn = "Excelente";
                holder.btnClase.setBackgroundColor(Color.rgb(143,219,61));
                break;
        }
        holder.btnClase.setText(textobtn);

        //events
        holder.setOnClickListeners();
    }


    @Override
    public int getItemCount() {
        return postulaciones.size();
    }


    public class viewHolderListaPostulaciones extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        TextView txtNombreP;
        TextView txtDirecc;
        TextView txtIdP;
        Button btnClase;

        ImageView ivInfo;

        public viewHolderListaPostulaciones(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            txtNombreP = itemView.findViewById(R.id.txtNombreP);
            txtDirecc = itemView.findViewById(R.id.txtDirecc);
            txtIdP = itemView.findViewById(R.id.txtIdP);
            ivInfo = itemView.findViewById(R.id.ivInfo);
            btnClase = itemView.findViewById((R.id.btnClase));
        }

        public void setOnClickListeners(){
            ivInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.ivInfo:
                    Intent postulacion = new Intent(context, PostulacionActivity.class);
                    postulacion.putExtra("intIdP",txtIdP.getText());
                    context.startActivity(postulacion);
                    break;
            }
        }
    }

}
