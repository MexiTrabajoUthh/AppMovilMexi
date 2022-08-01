package com.miguel.mexiproyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguel.mexiproyect.Modelo.clsModelo_ListarSolic;
import com.miguel.mexiproyect.Modelo.clsModelo_ListarVacantesNear;
import com.miguel.mexiproyect.Modelo.clsSolicitud;
import com.miguel.mexiproyect.Modelo.clsVacanteList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaVacatesCercaActivity extends AppCompatActivity {

    String user;
    Integer idUsr=0;
    ArrayList<clsVacanteList> vacantes;
    String Direccion;
    TextView txtUT1;
    RecyclerView rvTrabajosNear;
    String URL = "https://appmiguel.proyectoarp.com/MexiTrabajo/getVacantesNear.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacates_cerca);

        vacantes = new ArrayList<clsVacanteList>();
        rvTrabajosNear = (RecyclerView) findViewById(R.id.rvacantesnear);
        txtUT1 = (TextView) findViewById(R.id.txtUT1);
        rvTrabajosNear.setLayoutManager(new GridLayoutManager(this, 1));

        Bundle parametros = getIntent().getExtras();
        String dato = parametros.getString("usuario");
        String direcc = parametros.getString("Direccion");
        Integer idUser = parametros.getInt("idUser");
        user = dato;
        Direccion = direcc;
        idUsr = idUser;
        txtUT1.setText(user);
        //Toast.makeText(this, idUsr.toString(), Toast.LENGTH_SHORT).show();

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
                            String varPuesto = valores.getString("varPuesto");
                            String varNombre = valores.getString("varNombre");
                            Integer intIdOfertaLaboral = valores.getInt("intIdOfertaLaboral");
                            vacantes.add(new clsVacanteList(varPuesto,varNombre,intIdOfertaLaboral,idUsr));
                        }
                        clsModelo_ListarVacantesNear Modelo_ListarVac =  new clsModelo_ListarVacantesNear(vacantes);
                        rvTrabajosNear.setAdapter(Modelo_ListarVac);
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(ListaVacatesCercaActivity.this, "Error al Procesar", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(ListaVacatesCercaActivity.this, "Oops No hay vacantes Cerca", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(ListaVacatesCercaActivity.this, "Error al procesar la solicitud", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("direccion",Direccion);
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);
    }//Fin Metodo

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            menu();
        }
        return super.onKeyDown(keyCode, event);
    }

    void menu(){
        Intent intent = new Intent(this, MenuTrabajActivity.class);
        intent.putExtra("usuario", user);
        intent.putExtra("usuario", user);
        intent.putExtra("idUser", idUsr);
        intent.putExtra("direccion",Direccion);
        startActivity(intent);
    }
}