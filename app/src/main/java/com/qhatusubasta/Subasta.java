package com.qhatusubasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Subasta extends AppCompatActivity implements View.OnClickListener {

    // Barras de navegacion
    ImageView imgsubasta;
    ImageButton imgbHome, imgbFavoritos, imgbPerfil, imgbNotificaciones;
    TextView txtnombresubasta,txtfotosubasta, txtdescripcion, txthorasubasta,txtfechainicio,txtfechacierre,txtvalorsubasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subasta);
        referenciar();
        txtnombresubasta.setText(Index.nombre);
        txtdescripcion.setText(Index.descripcion);
        txthorasubasta.setText(Index.hora);
        txtfechainicio.setText(Index.fechainicio);
        txtfechacierre.setText(Index.fechacierre);
        txtvalorsubasta.setText(Index.valor);
        //Toast.makeText(this, ""+Index.foto, Toast.LENGTH_SHORT).show();
        Picasso.get().load(Index.foto).into(imgsubasta);




    }

    private void referenciar() {

  txtnombresubasta = findViewById(R.id.txtNombreSubasta);

       // txtfotosubasta = findViewById(R.id.imgSubasta);
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

        }
    }
}
