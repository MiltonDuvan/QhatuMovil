package com.qhatusubasta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.IndexAdapter;
import Model.Producto;

public class Index extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Producto> list = new ArrayList<>();
    GridView gribTndex;
    public static String nombre,foto,descripcion,hora,fechainicio,fechacierre, valor;

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
        myRef.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSaaptdhot : snapshot.getChildren()){
                    Producto producto = objSaaptdhot.getValue(Producto.class);
                    list.add(producto);

                    IndexAdapter adapter = new IndexAdapter(Index.this,list);
                    gribTndex.setAdapter(adapter);

                    gribTndex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            nombre = list.get(position).getNombre();
                            foto = list.get(position).getFoto();
                            descripcion = list.get(position).getDescripcion();
                            hora = list.get(position).getHora_cierre();
                            fechainicio = list.get(position).getFecha_inicio();
                            fechacierre = list.get(position).getFecha_cierre();
                            valor = list.get(position).getOferta_inicial();


                            Intent detalle = new Intent(Index.this, Subasta.class);

                           startActivity(detalle);

                        }
                    });
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