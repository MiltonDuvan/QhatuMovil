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

//implementamos click
public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView txtRegisterLogin;

    private EditText edtEmailLogin,edtPasswordLogin;
    private Button btnIniciarSesion;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegisterLogin=(TextView) findViewById(R.id.txtRegisterLogin);
        txtRegisterLogin.setOnClickListener(this);

        btnIniciarSesion=(Button) findViewById(R.id.btnIniciarSesion);
        btnIniciarSesion.setOnClickListener(this);

        edtEmailLogin=(EditText) findViewById(R.id.edtEmailLogin);
        edtPasswordLogin=(EditText) findViewById(R.id.edtPasswordLogin);

        mAuth=FirebaseAuth.getInstance();
    }

    //metodo click
    @Override
    public void onClick(View v) {
        //usamos case
        switch (v.getId()){
            case R.id.txtRegisterLogin:
                startActivity(new Intent(this,RegistroUsuario.class));
                break;

            case R.id.btnIniciarSesion:
                btnIniciarSesion();
                break;
        }

    }

    private void btnIniciarSesion() {
       String email= edtEmailLogin.getText().toString().trim();
       String password=edtPasswordLogin.getText().toString().trim();

       if (email.isEmpty()){
           edtEmailLogin.setError("Por favor ingresa un correo");
           edtEmailLogin.requestFocus();
           return;
       }
       if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           edtEmailLogin.setError("ingresa un correo valido");
           edtEmailLogin.requestFocus();
           return;
       }
       if (password.isEmpty()){
           edtPasswordLogin.setError("Por favor ingresa una contraseña");
           edtPasswordLogin.requestFocus();
           return;
       }
       if (password.length()<8){
           edtPasswordLogin.setError("la contraseña debe ser minimo a 8 caracteres");
       }

       mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   //redirige al perfil del usuario
                   startActivity(new Intent(Login.this,PerfilUser.class));
               }else{
                   Toast.makeText(Login.this, "Fallo al iniciar sesion, verifica tus credenciales", Toast.LENGTH_LONG).show();
               }
           }
       });


    }
}