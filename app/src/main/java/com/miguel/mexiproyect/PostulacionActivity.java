package com.miguel.mexiproyect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

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

public class PostulacionActivity extends AppCompatActivity{

    NotificationCompat.Builder mBuilder;
    int mNotification = 1;
    String channelId = "my_channel _01";

    Integer intIdP;
    String vacante;
    String nombre;
    String apellidoP;
    String apellidoM;
    String direccion;
    String sexo="0";
    String postulante;
    String disponib="0";
    String escolaridad="0";

    String telefono = "";
    String correo = "";

    TextView txtVacante;
    TextView txtNameP;
    TextView txtApellidoP;
    TextView txtApellidoM;
    TextView txtDireccionP;
    TextView txtSexo;
    TextView txtEscolar;
    TextView txtDispon;

    String URL = "https://appmiguel.proyectoarp.com/MexiTrabajo/postulacionDetail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postulacion);

        txtVacante=(TextView)findViewById(R.id.txtVacante);
        txtNameP=(TextView)findViewById(R.id.txtNameP);
        txtApellidoP=(TextView)findViewById(R.id.txtApellidoP);
        txtApellidoM=(TextView)findViewById(R.id.txtApellidoM);
        txtDireccionP=(TextView)findViewById(R.id.txtDireccionP);
        txtSexo=(TextView)findViewById(R.id.txtSexo);
        txtEscolar=(TextView)findViewById(R.id.txtEscolar);
        txtDispon=(TextView)findViewById(R.id.txtDispon);

        Bundle parametros = getIntent().getExtras();
        Integer dato = Integer.valueOf(parametros.getString("intIdP"));
        intIdP = dato;


        StringRequest respuesta = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("null")){
                    try{
                        JSONArray datos=new JSONArray(response);
                        for(int x=0; x<datos.length();x++){
                            JSONObject valores = datos.getJSONObject(x);
                            vacante = valores.getString("varPuesto");
                            nombre = valores.getString("varNombre");
                            apellidoP = valores.getString("varAPatern");
                            apellidoM = valores.getString("varAMatern");
                            direccion = valores.getString("varMunicipio");
                            sexo = valores.getString("intgenero");
                            postulante = valores.getString("intIdPostulante");
                            disponib = valores.getString("Disponibilidad");
                            escolaridad = valores.getString("UltimosEstudios");
                            fillEdT();
                        }
                    }catch(Exception e){
                        //Error Extracción de los datos, posiblemente contraseña incorrecta
                        Toast.makeText(PostulacionActivity.this, "No se pudo 1", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(PostulacionActivity.this, "No se pudo 2", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(PostulacionActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Id",intIdP.toString());
                return parametros;
            }
        };
        RequestQueue envio = Volley.newRequestQueue(getApplicationContext());
        envio.add(respuesta);
    }

    public void fillEdT(){
        txtVacante.setText(String.valueOf(vacante));
        txtNameP.setText(String.valueOf(nombre));
        txtApellidoP.setText(String.valueOf(apellidoP));
        txtApellidoM.setText(String.valueOf(apellidoM));
        txtDireccionP.setText(String.valueOf(direccion));
        if(Integer.valueOf(sexo)==1)
            txtSexo.setText("Hombre");
        else if(Integer.valueOf(sexo)==2)
            txtSexo.setText("Mujer");

        switch (Integer.valueOf(disponib)) {
            case 0:
                txtDispon.setText("Medio Tiempo");
                break;
            case 1:
                txtDispon.setText("Mixto");
                break;
            case 2:
                txtDispon.setText("Tiempo Completo");
                break;
        }
        switch (Integer.valueOf(escolaridad)) {
            case 0:
                txtEscolar.setText("Sin Estudios");
                break;
            case 1:
                txtEscolar.setText("Básica");
                break;
            case 2:
                txtEscolar.setText("Media Superior");
                break;
            case 3:
                txtEscolar.setText("Superior");
                break;
            case 4:
                txtEscolar.setText("Post Grado");
                break;
        }
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
                        Toast.makeText(PostulacionActivity.this, "No se pudo 1", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    //Error Usuario no encontrado posiblemente nunca entre aquí
                    Toast.makeText(PostulacionActivity.this, "No se pudo 2", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Fallo en la solicitud
                Toast.makeText(PostulacionActivity.this, "ERROR No se pudo", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("Id",postulante);
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
                .setContentText("Recuerda ser amable al contactarte con el Trabajador");

        mBuilder.setChannelId(channelId);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(mNotification,mBuilder.build());
    }

}