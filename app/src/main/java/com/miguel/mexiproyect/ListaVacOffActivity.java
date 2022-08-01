package com.miguel.mexiproyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguel.mexiproyect.Modelo.clsMiVacante;
import com.miguel.mexiproyect.Modelo.clsModelo_ListarMisVacantes;
import com.miguel.mexiproyect.Modelo.clsModelo_ListarVacOff;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaVacOffActivity extends AppCompatActivity {


    String user;
    ArrayList<clsMiVacante> misVacantes;
    TextView txUE1;
    RecyclerView rvvacantesoff;
    String URL = "https://appmiguel.proyectoarp.com/MexiTrabajo/getMisVacOff.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vac_off);

        misVacantes = new ArrayList<clsMiVacante>();
        rvvacantesoff = (RecyclerView) findViewById(R.id.rvvacantesoff);
        txUE1 = (TextView) findViewById(R.id.txUE1);
        rvvacantesoff.setLayoutManager(new GridLayoutManager(this, 1));

        Bundle parametros = getIntent().getExtras();
        String dato = parametros.getString("usuario");
        user = dato;
        txUE1.setText(user);

        mostrarLista();
    }

    public void mostrarLista ()
    {//Inicio Metodo
        StringRequest respuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            String Nombre = valores.getString("varPuesto");
                            String Turno = valores.getString("intTurno");
                            Integer idVacante = valores.getInt("intIdOfertaLaboral");
                            misVacantes.add(new clsMiVacante(Nombre,Turno,idVacante));
                        }
                        clsModelo_ListarVacOff Modelo_ListarMisVacOff = new clsModelo_ListarVacOff(misVacantes);
                        rvvacantesoff.setAdapter(Modelo_ListarMisVacOff);
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(ListaVacOffActivity.this, "Datos de Usuario fallidos", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(ListaVacOffActivity.this, "No tienes vacantes Desactivadas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(ListaVacOffActivity.this, "Error al procesar la solicitud", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("usuario",user);
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);
    }//Fin Metodo
}