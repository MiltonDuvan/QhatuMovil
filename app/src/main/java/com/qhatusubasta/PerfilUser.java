package com.qhatusubasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilUser extends AppCompatActivity {
    private TextView mail;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        mail=(TextView)findViewById(R.id.mail);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            String email=account.getDisplayName();
            mail.setText(email);
        }



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                gsc.signOut();
                finish();
                startActivity(new Intent(PerfilUser.this,MainActivity.class));
            }
        });
    }
}