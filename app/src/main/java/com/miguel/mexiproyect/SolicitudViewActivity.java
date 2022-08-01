package com.miguel.mexiproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SolicitudViewActivity extends AppCompatActivity {

    Integer intIdS;
    String servicio;
    String nombre;
    String apellidoP;
    String apellidoM;
    String direccion;
    String sexo="0";
    String solicitante;

    String telefono = "";
    String correo = "";

    TextView txtVacanteS;
    TextView txtS1_1;
    TextView txtS2;
    TextView txtS2_1;
    TextView txtS2_2;
    TextView txtS3;
    TextView txtS3_1;
    TextView txtS4;
    TextView txtS4_1;

    String URL = "https://appmiguel.proyectoarp.com/MexiTrabajo/solicitudDetail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudp);

        Bundle parametros = getIntent().getExtras();
        Integer dato = Integer.valueOf(parametros.getString("intIdS"));

        /*StringRequest respuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            servicio = valores.getString("varPuesto");
                            nombre = valores.getString("varNombre");
                            apellidoP = valores.getString("varAPatern");
                            apellidoM = valores.getString("varAMatern");
                            direccion = valores.getString("varMunicipio");
                            sexo = valores.getString("intgenero");
                            solicitante = valores.getString("intIdPostulante");
                        }
                        if(sexo.isEmpty()){
                            Toast.makeText(SolicitudViewActivity.this, "Empresa", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(SolicitudViewActivity.this, "No se pudo 1", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(SolicitudViewActivity.this, "No se pudo 2", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(SolicitudViewActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("IdS",intIdS.toString());
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);*/

    }

    public void mostrarContacto(View view) {
    }
}