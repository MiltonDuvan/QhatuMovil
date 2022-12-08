package com.qhatusubasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Favoritos extends AppCompatActivity implements View.OnClickListener {

    // Barras de navegacion
    ImageButton imgbHome, imgbFavoritos, imgbPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            // Barras de navegacion
            case R.id.imgbHome:
                Intent home = new Intent(Favoritos.this,Index.class);
                startActivity(home);
                break;
            case R.id.imgbFavoritos:
                Intent favoritos = new Intent(Favoritos.this,Favoritos.class);
                startActivity(favoritos);
                break;

            case R.id.imgbPerfil:
                Intent perfil = new Intent(Favoritos.this,PerfilUser.class);
                startActivity(perfil);
                break;
        }
    }
}