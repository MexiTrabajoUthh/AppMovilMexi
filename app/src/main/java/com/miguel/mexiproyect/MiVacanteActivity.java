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

public class MiVacanteActivity extends AppCompatActivity {

    NotificationCompat.Builder mBuilder;
    int mNotification = 1;
    String channelId = "my_channel _01";

    Integer intIdV;
    String user;
    String vacante;
    Double Salario;
    String Descripcion;
    String Turno;
    String Direccion;

    TextView txtVacante;
    TextView txtV1_1;
    TextView txtV2;
    TextView txtV2_1;
    TextView txtV3;
    TextView txtV3_1;
    TextView txtV4;
    TextView txtV4_1;

    String URL = "https://appmiguel.proyectoarp.com/MexiTrabajo/getMiVacanteDetail.php";
    String URL2 = "https://appmiguel.proyectoarp.com/MexiTrabajo/turnOffVacante.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_vacante);

        txtVacante=(TextView)findViewById(R.id.txtMiVacante);
        txtV1_1=(TextView)findViewById(R.id.txtMiV1_1);
        txtV2=(TextView)findViewById(R.id.txtMiV2);
        txtV2_1=(TextView)findViewById(R.id.txtMiV2_1);
        txtV3=(TextView)findViewById(R.id.txtMiV3);
        txtV3_1=(TextView)findViewById(R.id.txtMiV3_1);
        txtV4=(TextView)findViewById(R.id.txtMiV4);
        txtV4_1=(TextView)findViewById(R.id.txtMiV4_1);

        Bundle parametros = getIntent().getExtras();
        Integer dato = Integer.valueOf(parametros.getString("intIdV"));
        intIdV = dato;

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
                            Direccion = valores.getString("varUbicacion");
                            Turno = valores.getString("intTurno");
                            user = valores.getString("varEmail");
                        }
                        llenaPersona();
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(MiVacanteActivity.this, "Error al Procesar", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(MiVacanteActivity.this, "Fallo al Procesar", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(MiVacanteActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id",intIdV.toString());
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);
    }

    public void llenaPersona(){
        txtVacante.setText(String.valueOf(vacante));
        txtV1_1.setText(String.valueOf(Descripcion));
        txtV2.setText("Horario:");
        txtV2_1.setText(String.valueOf(Turno));
        txtV3.setText("Dirección");
        txtV3_1.setText(String.valueOf(Direccion));
        txtV4_1.setText(String.valueOf(Salario));
    }

    public void desactivar(View view) {
        StringRequest respuesta = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("NO")){
                    notificar();
                    //Toast.makeText(MiVacanteActivity.this, "Se ha eliminado Correctamente", Toast.LENGTH_SHORT).show();
                    Intent vistaMisVacantes = new Intent(getApplicationContext(), ListaMisVacantesActivity.class);
                    vistaMisVacantes.putExtra("usuario", user);
                    startActivity(vistaMisVacantes);
                }
                else{
                    Toast.makeText(MiVacanteActivity.this, "No se ha podido eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(MiVacanteActivity.this, "ERROR FALTA CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("IdVac",intIdV.toString());
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
            String description = "Desactivar Vacante";
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
                .setContentText("Se ha desactivado tu vacante Exitosamente");

        mBuilder.setChannelId(channelId);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(mNotification,mBuilder.build());
    }

}