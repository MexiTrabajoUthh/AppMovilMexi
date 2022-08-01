package com.miguel.mexiproyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VacanteActivity extends AppCompatActivity {

    NotificationCompat.Builder mBuilder;
    int mNotification = 1;
    String channelId = "my_channel _01";

    Integer intIdV;
    Integer idUsr;
    String user = "Micorreo";
    Integer boolPost = 1;
    String vacante;
    Double Salario;
    String Descripcion;
    String NombreEmpleador;
    String Direccion;
    String DirecPostulante ="";

    TextView txtVacante;
    TextView txtV1_1;
    TextView txtV2;
    TextView txtV2_1;
    TextView txtV3;
    TextView txtV3_1;
    TextView txtV4;
    TextView txtV4_1;
    Button buttonPostularme;

    String URL = "https://appmiguel.proyectoarp.com/MexiTrabajo/detalleVacante.php";
    String URL2 = "https://appmiguel.proyectoarp.com/MexiTrabajo/checkPostVacante.php";
    String URL3 = "https://appmiguel.proyectoarp.com/MexiTrabajo/postularmeVacante.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacante);

        txtVacante=(TextView)findViewById(R.id.txtVacante);
        txtV1_1=(TextView)findViewById(R.id.txtV1_1);
        txtV2=(TextView)findViewById(R.id.txtV2);
        txtV2_1=(TextView)findViewById(R.id.txtV2_1);
        txtV3=(TextView)findViewById(R.id.txtV3);
        txtV3_1=(TextView)findViewById(R.id.txtV3_1);
        txtV4=(TextView)findViewById(R.id.txtV4);
        txtV4_1=(TextView)findViewById(R.id.txtV4_1);
        buttonPostularme = (Button)findViewById(R.id.buttonPostularme);

        Bundle parametros = getIntent().getExtras();
        Integer dato = Integer.valueOf(parametros.getString("intIdV"));
        Integer idUser = Integer.valueOf(parametros.getString("idUser"));
        idUsr = idUser;
        intIdV = dato;

        //Toast.makeText(this, idUsr.toString(), Toast.LENGTH_SHORT).show();


        StringRequest respuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            vacante = valores.getString("varPuesto");
                            Salario = valores.getDouble("decSalario");
                            Descripcion = valores.getString("varDescripcion");
                            NombreEmpleador = valores.getString("varNombre");
                            Direccion = valores.getString("varUbicacion");
                        }
                        llenaPersona();
                        checkPost();
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(VacanteActivity.this, "Error al procesar", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(VacanteActivity.this, "Fallo en el proceso", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(VacanteActivity.this, "ERROR de Conexión", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idVacante",intIdV.toString());
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);

        takeUser();
    }

    public void llenaPersona(){

        txtVacante.setText(String.valueOf(vacante));
        txtV1_1.setText(String.valueOf(Descripcion));
        txtV2.setText("Empleador:");
        txtV2_1.setText(String.valueOf(NombreEmpleador));
        txtV3.setText("Dirección");
        txtV3_1.setText(String.valueOf(Direccion));
        txtV4_1.setText(String.valueOf(Salario));
    }

    public void checkPost(){
        StringRequest respuesta = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    //Toast.makeText(VacanteActivity.this, "Ya estás postulado a esta vacante", Toast.LENGTH_SHORT).show();
                    buttonPostularme.setText("Postulado");
                    buttonPostularme.setBackgroundColor(Color.DKGRAY);
                    boolPost = 1;
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    //Toast.makeText(VacanteActivity.this, "Puede Postularse", Toast.LENGTH_SHORT).show();
                    buttonPostularme.setBackgroundColor(Color.rgb(143,219,61));
                    boolPost = 0;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(VacanteActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idVacante",intIdV.toString());
                parametros.put("idUser",idUsr.toString());
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);
    }

    public void postularme(View view) {
        if(boolPost==0){
            //Toast.makeText(this, "No postulado", Toast.LENGTH_SHORT).show();
            StringRequest respuesta = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("SI")){
                        Toast.makeText(VacanteActivity.this, "Listo!!", Toast.LENGTH_SHORT).show();
                        Intent vistaNearVacantes = new Intent(getApplicationContext(), ListaVacatesCercaActivity.class);
                        vistaNearVacantes.putExtra("usuario", user);
                        vistaNearVacantes.putExtra("Direccion", DirecPostulante);
                        vistaNearVacantes.putExtra("idUser", idUsr);
                        startActivity(vistaNearVacantes);
                        notificar();
                    }
                    else{
                        //Error Usuario no encontrado posiblemente nunca entre aquí
                        Toast.makeText(VacanteActivity.this, "No se pudo postular", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Fallo en la solicitud
                    Toast.makeText(VacanteActivity.this, "ERROR de Conexión", Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("IdVac",intIdV.toString());
                    parametros.put("IdPost",idUsr.toString());
                    return parametros;
                }
            };
            RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
            envio.add(respuesta);
        }
    }

    public void takeUser(){
        String URLUsr = "https://appmiguel.proyectoarp.com/MexiTrabajo/getUser.php";
        StringRequest respuesta = new StringRequest(Request.Method.POST, URLUsr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            String correo = valores.getString("varEmail");
                            String DirecPostul = valores.getString("varMunicipio");
                            user = correo;
                            DirecPostulante = DirecPostul;
                            //Toast.makeText(VacanteActivity.this, DirecPostul, Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(VacanteActivity.this, "Fallo al Procesar", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(VacanteActivity.this, "Error al Procesar", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(VacanteActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Id",idUsr.toString());
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);
    }

    void notificar(){
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this,"");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "MexiTrabajo";
            String description = "Felicidades te has postulado a: "+ vacante;
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(channelId,name,importance);

            mChannel.setDescription(description);
            mChannel.enableLights(true);

            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            mNotificationManager.createNotificationChannel(mChannel);

            mBuilder = new NotificationCompat.Builder(this,channelId);
        }

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender();


        mBuilder.setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Postulado a una nueva Vacante")
                .setContentText("Felicidades te has postulado a: "+ vacante);

        mBuilder.setChannelId(channelId);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(mNotification,mBuilder.build());
    }
}