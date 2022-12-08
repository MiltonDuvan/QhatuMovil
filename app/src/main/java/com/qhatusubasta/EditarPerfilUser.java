package com.qhatusubasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditarPerfilUser extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference BASE_DE_DATOS;
    TextView edtnombre, edtapellido;
    Button btnguardarcambios;
    TextView txtnombre,txtapellido;

    // Barras de navegacion
    ImageButton imgbHome, imgbFavoritos, imgbPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_user);
        referenfciar();
    }

    private void referenfciar() {
        edtnombre = findViewById(R.id.edtnombre);
        edtapellido = findViewById(R.id.edtapellido);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        btnguardarcambios = findViewById(R.id.btnguardarcambios);


        btnguardarcambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Editar Datos

                String nombre = edtnombre.getText().toString();
                String apellido= edtapellido.getText().toString();
                UpdateData (nombre,apellido);
            }

            private void UpdateData(String nombre, String apellido) {
                HashMap Users=new HashMap();
                Users.put("nombre",nombre);
                Users.put("apellido",apellido);


                BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference("Users");
                BASE_DE_DATOS.child(user.getUid()).updateChildren(Users).addOnCompleteListener(new OnCompleteListener<Void>() {


                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){


                            Toast.makeText(EditarPerfilUser.this, "Datos Actualizados", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditarPerfilUser.this,PerfilUser.class));
                        }else{
                            Toast.makeText(EditarPerfilUser.this, "Fallo ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        //mostrar datos
        txtnombre=findViewById(R.id.edtnombre);
        txtapellido=findViewById(R.id.edtapellido);


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



                    //guardar datos en los editext

                    txtnombre.setText(nombre);
                    txtapellido.setText(apellido);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imgbHome:
                Intent home = new Intent(EditarPerfilUser.this,Index.class);
                startActivity(home);
                break;
            case R.id.imgbFavoritos:
                Intent favoritos = new Intent(EditarPerfilUser.this,Favoritos.class);
                startActivity(favoritos);
                break;

            case R.id.imgbPerfil:
                Intent perfil = new Intent(EditarPerfilUser.this,PerfilUser.class);
                startActivity(perfil);
                break;
        }
        
    }
}