package com.qhatusubasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Notificaciones extends AppCompatActivity implements View.OnClickListener {
    // Barras de navegacion
    ImageButton imgbHome, imgbFavoritos, imgbPerfil, imgbNotificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        referenciar();
    }

    private void referenciar() {
        // Barras de navegacion
        imgbHome = findViewById(R.id.imgbHome);
        imgbHome.setOnClickListener(this);
        imgbFavoritos = findViewById(R.id.imgbFavoritos);
        imgbFavoritos.setOnClickListener(this);
        imgbPerfil = findViewById(R.id.imgbPerfil);
        imgbPerfil.setOnClickListener(this);
        imgbNotificaciones = findViewById(R.id.imgbNotificaciones);
        imgbNotificaciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgbHome:
                Intent home = new Intent(Notificaciones.this,Index.class);
                startActivity(home);
                break;
            case R.id.imgbFavoritos:
                Intent favoritos = new Intent(Notificaciones.this,Favoritos.class);
                startActivity(favoritos);
                break;

            case R.id.imgbPerfil:
                Intent perfil = new Intent(Notificaciones.this,PerfilUser.class);
                startActivity(perfil);
                break;
            case R.id.imgbNotificaciones:
                Intent notificaciones = new Intent(Notificaciones.this,Notificaciones.class);
                startActivity(notificaciones);
                break;

        }
    }
}