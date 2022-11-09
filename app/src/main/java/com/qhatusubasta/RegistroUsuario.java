package com.qhatusubasta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroUsuario extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText nombreRegistro,apellidoRegistro,emailRegistro,passwordRegistro;
    private Button btnRegistrarse;
    private TextView titlesubasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //iniciamos variable de autenticacion
        mAuth = FirebaseAuth.getInstance();

        titlesubasta=findViewById(R.id.titlesubasta);
        titlesubasta.setOnClickListener(this);

        btnRegistrarse=findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(this);

        nombreRegistro=(EditText) findViewById(R.id.nombreRegistro);
        apellidoRegistro=(EditText) findViewById(R.id.apellidoRegistro);
        emailRegistro=(EditText) findViewById(R.id.emailRegistro);
        passwordRegistro=(EditText) findViewById(R.id.passwordRegistro);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titlesubasta:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.btnRegistrarse:
                //creamos el metodo registro
                btnRegistrarse();
                break;
        }

    }

    //metodo registro
    private void btnRegistrarse() {
        String email= emailRegistro.getText().toString().trim();
        String password = passwordRegistro.getText().toString().trim();
        String nombre = nombreRegistro.getText().toString().trim();
        String apellido= apellidoRegistro.getText().toString().trim();

        if (nombre.isEmpty()){
            nombreRegistro.setError("Este campo es requerido");
            nombreRegistro.requestFocus();
            return;
        }

        if(apellido.isEmpty()){
            apellidoRegistro.setError("Este campo es obligatorio");
            apellidoRegistro.requestFocus();
            return;
        }

        if (email.isEmpty()){
            emailRegistro.setError("Este campo es obligatorio");
            emailRegistro.requestFocus();
            return;
        }
        //validar correo @./ etc
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailRegistro.setError("Escribe un email valido");
            emailRegistro.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passwordRegistro.setError("Este campo es obligatorio");
            passwordRegistro.requestFocus();
            return;
        }

        if (password.length()<8) {
        passwordRegistro.setError("Escribe una contraseña minimo de 8 caracteres");
        passwordRegistro.requestFocus();
        return;
        }

        //cancelamos nuestro em y creamos un usuario con correo y contraseña
        mAuth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { //verificamos si fue exitoso el registro
                if (task.isSuccessful()){
                    Usuario user = new Usuario(nombre,apellido,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        //si el usuario se registro y sus datos se han insertado en firebase nos notifica este mensaje
                                        Toast.makeText(RegistroUsuario.this, "Su registro fue exitoso!!", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(RegistroUsuario.this, "Fallo al registrar, intentalo de nuevo", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegistroUsuario.this, "Fallo al registrar, intentalo de nuevo", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}