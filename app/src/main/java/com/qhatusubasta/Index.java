package com.qhatusubasta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.RegistroProductoAdapter;
import Model.Producto;

public class Index extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Producto> list = new ArrayList<>();
    GridView gribTndex;

    // Barras de navegacion
    ImageButton imgbHome, imgbFavoritos, imgbPerfil, imgbNotificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Lista();
        referenciar();
    }
    private void referenciar() {
        gribTndex = findViewById(R.id.gridIndex);

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

    public void Lista(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSaaptdhot : snapshot.getChildren()){
                    Producto producto = objSaaptdhot.getValue(Producto.class);
                    list.add(producto);

                    RegistroProductoAdapter adapter = new RegistroProductoAdapter(Index.this,list);
                    gribTndex.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgbHome:
                Intent home = new Intent(Index.this,Index.class);
                startActivity(home);
                break;
            case R.id.imgbFavoritos:
                Intent favoritos = new Intent(Index.this,Favoritos.class);
                startActivity(favoritos);
                break;

            case R.id.imgbPerfil:
                Intent perfil = new Intent(Index.this,PerfilUser.class);
                startActivity(perfil);
                break;
            case R.id.imgbNotificaciones:
                    Intent notificaciones = new Intent(Index.this,Notificaciones.class);
                    startActivity(notificaciones);
                    break;

        }
    }
}