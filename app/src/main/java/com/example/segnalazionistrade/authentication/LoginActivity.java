package com.example.segnalazionistrade.authentication;

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

    private static final String TAG = "LoginActivity";

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initUI();

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
        Log.d("LoginActivity", "Login Button Click");
        //estrae le stringhe
        String emailUser = mEmail.getText().toString();
        String passwordUser = mPassword.getText().toString();

        if(!emailCorrect(emailUser)){
            Toast.makeText(this,"Il nome deve contenere almeno 3 lettere", Toast.LENGTH_SHORT).show();
        }
        else if(!passwordCorrect(passwordUser)){
            Toast.makeText(this,"la password deve contenere 8 caratteri", Toast.LENGTH_SHORT).show();
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
        if((emailUser.length() > 7) && (emailUser.contains("@")))
            return true;
        else
            return false;
    }

    private void loginUser(String emailUser, String passwordUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            SendUserToMainActivity();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void btnRegisterClick(View view) {
        Log.d("LoginActivity", "Registrati Click");

        SendUserToRegisterActivity();

    }

}
