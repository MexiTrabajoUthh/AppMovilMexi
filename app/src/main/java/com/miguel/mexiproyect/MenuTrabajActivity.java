package com.miguel.mexiproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MenuTrabajActivity extends AppCompatActivity {

    String user;
    String Direccion;
    Integer idUsr;
    TextView txtUser2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_trabaj);

        txtUser2 = (TextView) findViewById(R.id.txtUser2);
        Bundle parametros = getIntent().getExtras();
        String dato = parametros.getString("usuario");
        Integer idUser = parametros.getInt("idUser");
        String direcc = parametros.getString("direccion");

        user = dato;
        Direccion = direcc;
        idUsr = idUser;
        txtUser2.setText(user);
        //Toast.makeText(this, idUsr.toString(), Toast.LENGTH_SHORT).show();
    }

    public void listaSolicitudes(View view) {
        Intent vistaSolicitudes = new Intent(getApplicationContext(), ListaSolicitudesActivity.class);
        vistaSolicitudes.putExtra("usuario", user);
        startActivity(vistaSolicitudes);
    }

    public void listaTrabajosCerca(View view) {
        Intent vistaTrabajosNear = new Intent(getApplicationContext(), ListaVacatesCercaActivity.class);
        vistaTrabajosNear.putExtra("usuario", user);
        vistaTrabajosNear.putExtra("idUser", idUsr);
        vistaTrabajosNear.putExtra("Direccion", Direccion);
        startActivity(vistaTrabajosNear);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            login();
        }
        return super.onKeyDown(keyCode, event);
    }

    void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}