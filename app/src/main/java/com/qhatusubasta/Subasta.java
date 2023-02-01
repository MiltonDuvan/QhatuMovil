package com.qhatusubasta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Adapter.ChatAdapter;
import Model.Oferta;
import Model.Producto;

public class Subasta extends AppCompatActivity implements View.OnClickListener {
    Button btnComentarOferta;
    EditText edtComentarOferta;
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Oferta> list = new ArrayList<>();
    ListView listaChat;

    // Barras de navegacion
    ImageView imgsubasta;
    ImageButton imgbHome, imgbFavoritos, imgbPerfil, imgbNotificaciones;
    TextView txtnombresubasta, txtdescripcion, txthorasubasta,txtfechainicio,txtfechacierre,txtvalorsubasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subasta);
        ListarOferta();
        referenciar();
        txtnombresubasta.setText(Index.nombre);
        txtdescripcion.setText(Index.descripcion);
        txthorasubasta.setText(Index.hora);
        txtfechainicio.setText(Index.fechainicio);
        txtfechacierre.setText(Index.fechacierre);
        txtvalorsubasta.setText(Index.valor);
        Picasso.get().load(Index.foto).into(imgsubasta);

    }
    public void ComentarOferta(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Oferta oferta = new Oferta();
        oferta.setId(UUID.randomUUID().toString());
        oferta.setOferta(edtComentarOferta.getText().toString());
        myRef.child("Oferta").child(oferta.getId()).setValue(oferta);
        Toast.makeText(this, "Oferta Exitosa", Toast.LENGTH_SHORT).show();
    }

    public void ListarOferta (){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Oferta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSaaptdhot : snapshot.getChildren()){
                    Oferta oferta = objSaaptdhot.getValue(Oferta.class);
                   list.add(oferta);

                    ChatAdapter chatAdapter = new ChatAdapter(Subasta.this,list);
                    listaChat.setAdapter(chatAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void referenciar() {
        listaChat = findViewById(R.id.listaChat);
        edtComentarOferta = findViewById(R.id.edtComentarOferta);
        btnComentarOferta = findViewById(R.id.btnComentarOferta);
        btnComentarOferta.setOnClickListener(this);

  txtnombresubasta = findViewById(R.id.txtNombreSubasta);
  txtdescripcion = findViewById(R.id.txtDescripcionSubasta);
  txthorasubasta = findViewById(R.id.txtHoraSubasta);
  txtfechainicio = findViewById(R.id.txtFechaInicioSubasta);
  txtfechacierre = findViewById(R.id.txtFechaFinSubasta);
  txtvalorsubasta =findViewById(R.id.txtValorSubasta);
  imgsubasta = findViewById(R.id.imgSubasta);

        // de navegacion
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
                Intent home = new Intent(Subasta.this,Index.class);
                startActivity(home);
                break;
            case R.id.imgbFavoritos:
                Intent favoritos = new Intent(Subasta.this,Favoritos.class);
                startActivity(favoritos);
                break;

            case R.id.imgbPerfil:
                Intent perfil = new Intent(Subasta.this,PerfilUser.class);
                startActivity(perfil);
                break;
            case R.id.imgbNotificaciones:
                Intent notificaciones = new Intent(Subasta.this,Notificaciones.class);
                startActivity(notificaciones);
                break;
            case  R.id.btnComentarOferta:
                ComentarOferta();
                edtComentarOferta.setText("");
                break;

        }
    }
}
