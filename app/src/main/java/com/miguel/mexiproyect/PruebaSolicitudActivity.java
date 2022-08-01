package com.miguel.mexiproyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

public class PruebaSolicitudActivity extends AppCompatActivity {

    NotificationCompat.Builder mBuilder;
    int mNotification = 1;
    String channelId = "my_channel _01";

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

    TextView txtServicio;
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
        setContentView(R.layout.activity_prueba_solicitud);

        txtServicio=(TextView)findViewById(R.id.txtServicio);
        txtS1_1=(TextView)findViewById(R.id.txtS1_1);
        txtS2=(TextView)findViewById(R.id.txtS2);
        txtS2_1=(TextView)findViewById(R.id.txtS2_1);
        txtS2_2=(TextView)findViewById(R.id.txtS2_2);
        txtS3=(TextView)findViewById(R.id.txtS3);
        txtS3_1=(TextView)findViewById(R.id.txtS3_1);
        txtS4=(TextView)findViewById(R.id.txtS4);
        txtS4_1=(TextView)findViewById(R.id.txtS4_1);

        Bundle parametros = getIntent().getExtras();
        Integer dato = Integer.valueOf(parametros.getString("intIdS"));
        intIdS = dato;

        StringRequest respuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            servicio = valores.getString("varNombreTrabahjo");
                            nombre = valores.getString("varNombre");
                            apellidoP = valores.getString("varAPatern");
                            apellidoM = valores.getString("varAMatern");
                            direccion = valores.getString("varMunicipio");
                            sexo = valores.getString("intgenero");
                            solicitante = valores.getString("intIdSolicitante");
                        }
                        if(sexo=="null"){
                            llenaEmpresa();
                        }
                        else {
                            llenaPersona();
                        }
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(PruebaSolicitudActivity.this, "No se pudo 1", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(PruebaSolicitudActivity.this, "No se pudo 2", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(PruebaSolicitudActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
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
        envio.add(respuesta);
    }

    public void llenaEmpresa() {
        txtServicio.setText(String.valueOf(servicio));
        txtS1_1.setText(String.valueOf(nombre));
        txtS2.setText("Dirección");
        txtS2_1.setText(String.valueOf(direccion));
        txtS3.setText("Es Empresa");
    }

    public void llenaPersona(){
        txtServicio.setText(String.valueOf(servicio));
        txtS1_1.setText(String.valueOf(nombre));
        txtS2.setText("Apellidos");
        txtS2_1.setText(String.valueOf(apellidoP));
        txtS2_2.setText(String.valueOf(apellidoM));
        txtS3.setText("Dirección");
        txtS3_1.setText(String.valueOf(direccion));
        txtS4.setText("Sexo:");
        if(Integer.valueOf(sexo)==1)
            txtS4_1.setText("Hombre");
        else if(Integer.valueOf(sexo)==2)
            txtS4_1.setText("Mujer");
    }

    public void mostrarContacto(View view) {
        String URL2 = "https://appmiguel.proyectoarp.com/MexiTrabajo/getContact.php";
        StringRequest respuesta = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            telefono = valores.getString("varNoTelefono");
                            correo = valores.getString("varEmail");
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Datos de contacto")
                                .setMessage("Teléfono:\t" + telefono + "\nCorreo:\t" + correo)
                                .setNeutralButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        notificar();
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(PruebaSolicitudActivity.this, "No se pudo 1", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(PruebaSolicitudActivity.this, "No se pudo 2", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(PruebaSolicitudActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Id",solicitante);
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
            String description = "Contacto";
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
                .setContentTitle("MexiTrabajo")
                .setContentText("Recuerda ser amable al contactarte con el empleador");

        mBuilder.setChannelId(channelId);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(mNotification,mBuilder.build());
    }
}