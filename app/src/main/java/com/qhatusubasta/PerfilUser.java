package com.qhatusubasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilUser extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private Button btnLogout;
    Button btneditar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference BASE_DE_DATOS;
    TextView txtnombre,txtapellido,txtcorreo;

    // Barras de navegacion
    ImageButton imgbHome, imgbFavoritos, imgbPerfil, btnProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);
        referenciar();


    }

    private void referenciar() {

        btneditar=findViewById(R.id.btneditar);
        btneditar.setOnClickListener(this);

        btnLogout = (Button) findViewById(R.id.btnLogout);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                gsc.signOut();
                finish();
                startActivity(new Intent(PerfilUser.this,Login
                        .class));
            }
        });
        //mostrar datos
        txtnombre=findViewById(R.id.txtnombre);
        txtapellido=findViewById(R.id.txtpellido);
        txtcorreo=findViewById(R.id.txtcorreo);


        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        BASE_DE_DATOS= FirebaseDatabase.getInstance().getReference("Users");
        BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //si el usuario eiste
                if (snapshot.exists()){
                    String uid=""+snapshot.child("uid").getValue();
                    String nombre=""+ snapshot.child("nombre").getValue();
                    String apellido=""+ snapshot.child("apellido").getValue();
                    String email=""+ snapshot.child("email").getValue();


                    //guardar datos en los editext

                    txtnombre.setText(nombre);
                    txtapellido.setText(apellido);
                    txtcorreo.setText(email);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Barras de navegacion
        imgbHome = findViewById(R.id.imgbHome);
        imgbHome.setOnClickListener(this);
        imgbFavoritos = findViewById(R.id.imgbFavoritos);
        imgbFavoritos.setOnClickListener(this);
        imgbPerfil = findViewById(R.id.imgbPerfil);
        imgbPerfil.setOnClickListener(this);
        btnProducto = findViewById(R.id.btnProducto);
        btnProducto.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btneditar:
                Intent editar = new Intent(PerfilUser.this,EditarPerfilUser.class);
                startActivity(editar);
                break;
            case R.id.imgbHome:
                Intent home = new Intent(PerfilUser.this,Index.class);
                startActivity(home);
                break;
            case R.id.imgbFavoritos:
                Intent favoritos = new Intent(PerfilUser.this,Favoritos.class);
                startActivity(favoritos);
                break;

            case R.id.imgbPerfil:
                Intent perfil = new Intent(PerfilUser.this,PerfilUser.class);
                startActivity(perfil);
                break;

            case R.id.btnProducto:
                Intent newProducto = new Intent(PerfilUser.this,RegistroProducto.class);
                startActivity(newProducto);
                break;
        }

    }
}