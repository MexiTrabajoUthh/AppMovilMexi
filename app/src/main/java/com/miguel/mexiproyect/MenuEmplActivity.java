package com.miguel.mexiproyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class MenuEmplActivity extends AppCompatActivity {

    String user;
    TextView txtUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_empl);

        txtUser = (TextView) findViewById(R.id.txtUser);

        Bundle parametros = getIntent().getExtras();
        String dato = parametros.getString("usuario");
        user = dato;
        txtUser.setText(user);
    }

    public void listaPostulaciones(View view) {
        Intent vistaPostulaciones = new Intent(getApplicationContext(), ListaPostulacionesActivity.class);
        vistaPostulaciones.putExtra("usuario", user);
        startActivity(vistaPostulaciones);
    }

    public void listaTusVacantes(View view) {
        Intent vistaMisVacantes = new Intent(getApplicationContext(), ListaMisVacantesActivity.class);
        vistaMisVacantes.putExtra("usuario", user);
        startActivity(vistaMisVacantes);
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