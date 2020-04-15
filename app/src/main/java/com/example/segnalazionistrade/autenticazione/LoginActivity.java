package com.example.segnalazionistrade.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.segnalazionistrade.MainActivity;
import com.example.segnalazionistrade.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    EditText mEmail;
    EditText mPassword;

    @Override
    public void onStart() {
        super.onStart();

        // controlla se c'è un utente loggato
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFirebase();
        initUI();
    }

    private void initFirebase() {
        // Initializza Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void initUI() {
        //Collega le variabili ai Widgets
        mEmail = (EditText)findViewById(R.id.etLogEmail);
        mPassword = (EditText)findViewById(R.id.etLogPassword);
    }

    private void SendUserToRegisterActivity() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(registerIntent);
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(mainIntent);
    }

    private void updateUI(FirebaseUser currentUser) {

        //prendo l'utente corrente
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //verifica se c'è un utente loggato
        if (user != null)
            SendUserToMainActivity();
    }


    public void btnLoginClick(View view) {
        //estrae le stringhe
        String emailUser = mEmail.getText().toString();
        String passwordUser = mPassword.getText().toString();

        if(!emailCorrect(emailUser)){
            Toast.makeText(this, R.string.lunghezza_nome, Toast.LENGTH_SHORT).show();
        }
        else if(!passwordCorrect(passwordUser)){
            Toast.makeText(this,R.string.lunghezza_password, Toast.LENGTH_SHORT).show();
        }
        else {
            loginUser(emailUser, passwordUser);
        }
    }

    private boolean passwordCorrect(String passwordUser) {
        if(passwordUser.length() > 7)
            return true;
        else
            return false;
    }

    private boolean emailCorrect(String emailUser) {
        if((emailUser.length() > 7) && (emailUser.contains("@")) && (emailUser.contains(".")))
            return true;
        else
            return false;
    }

    private void loginUser(String emailUser, String passwordUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // il login ha avuto successo
                    SendUserToMainActivity();
                } else {
                    // il login è fallito
                    Toast.makeText(LoginActivity.this, R.string.login_fallito,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void btnRegisterClick(View view) {
        SendUserToRegisterActivity();
    }

}
