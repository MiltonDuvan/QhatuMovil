package com.qhatusubasta;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//implementamos click
public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView txtRegisterLogin,txtOlvidopassLogin;
    private ImageButton btnSinginGoogle;
    private EditText edtEmailLogin,edtPasswordLogin;
    private Button btnIniciarSesion;
    private FirebaseAuth mAuth;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

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

        txtOlvidopassLogin=(TextView)findViewById(R.id.txtOlvidopassLogin);
        txtOlvidopassLogin.setOnClickListener(this);

        btnSinginGoogle=(ImageButton)findViewById(R.id.btnSinginGoogle);
        btnSinginGoogle.setOnClickListener(this);

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
            case R.id.txtOlvidopassLogin:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
            case R.id.btnSinginGoogle:
                SingInGoogle();
        }

    }

    private void SingInGoogle() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this,gso);

        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, 100);


    }
//verificar si salio bien o no SingInGoogle
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100) {
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                HomeActivity();
            } catch (ApiException e) {
                Toast.makeText(this, "Vuelve a intentarglo de nuevo", Toast.LENGTH_SHORT).show();
            }
        }  }

    private void HomeActivity() {
        finish();
        Intent intent=new Intent(getApplicationContext(),PerfilUser.class);
        startActivity(intent);
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
       if (password.length()<6){
           edtPasswordLogin.setError("la contraseña debe ser minimo a 6 caracteres");
       }

       mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   //valuidamos que el correo este verificado
                   FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                   if (user.isEmailVerified()){
                       //redirige al perfil del usuario
                       startActivity(new Intent(Login.this,Index.class));
                   }else{
                       user.sendEmailVerification();
                       Toast.makeText(Login.this, "Revisa tu bandeja de correos para verificar tu cuenta", Toast.LENGTH_LONG).show();
                   }

               }else{
                   Toast.makeText(Login.this, "Fallo al iniciar sesion, verifica tus credenciales", Toast.LENGTH_LONG).show();
               }
           }
       });


    }
}