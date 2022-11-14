package com.qhatusubasta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText edtResetPassword;
    private Button btnResetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtResetPassword = (EditText) findViewById(R.id.edtResetPassword);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);

        auth=FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private  void resetPassword(){
        String email = edtResetPassword.getText().toString().trim();

        if (email.isEmpty()){
            edtResetPassword.setError("Campo requerido");
            edtResetPassword.requestFocus();
            return;

        } if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtResetPassword.setError("Por favor escribe un correo valido");
            edtResetPassword.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Se envio un correo para restablecer la contraseña", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ForgotPassword.this, "Algo salio mal, puedes intentarlo de nuevo!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}