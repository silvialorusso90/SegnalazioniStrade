package com.example.segnalazionistrade.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.segnalazionistrade.MainActivity;
import com.example.segnalazionistrade.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    private ProgressDialog loadingBar;
    private EditText mName, mSurname, mEmail, mPassword, mPasswordConfirm;
    TextView txtLogin;
    private Button mBtnRegister;
    private String name, surname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        initUI();
    }

    private void initUI() {
        mName = (EditText)findViewById(R.id.etRegName);
        mSurname = (EditText) findViewById(R.id.etRegSurname);
        mEmail = (EditText)findViewById(R.id.etRegEmail);
        mPassword = (EditText)findViewById(R.id.etRegPassword);
        mPasswordConfirm = (EditText)findViewById(R.id.etRegPasswordConfirm);
        mBtnRegister = (Button) findViewById(R.id.btnRegister);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
    }

    public void clickRegister(View view) {
        name = mName.getText().toString();
        surname = mSurname.getText().toString();
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        Log.d("Reg", "Button Registrati clicked");
        if(!nameCorrect(name)){
            Toast.makeText(this,"Il nome deve contenere almeno 3 lettere", Toast.LENGTH_SHORT).show();
        }
        else if(!surnameCorrect(surname)){
            Toast.makeText(this,"Il cognome deve contenere almeno 4 lettere", Toast.LENGTH_SHORT).show();
        }
        else if(!emailCorrect(email)){
            Toast.makeText(this,"L'email deve contenere la @e il .", Toast.LENGTH_SHORT).show();
        }
        else if(!pwCorrect(password)){
            Toast.makeText(this,"La password deve avere almeno 8 caratteri", Toast.LENGTH_SHORT).show();
        }
        else {
            createFirebaseUser(email, password, name, surname);
        }
    }

    private void createFirebaseUser(final String email, String password, final String name, final String surname){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    String currentUserID = mAuth.getCurrentUser().getUid();
                    String fullName = name + " " + surname;
                    User user = new User(name, surname, email);
                    mDatabaseReference.child("Utenti").child(currentUserID).setValue(user);
                    setName(fullName);
                    SendUserToMainActivity();
                    //loadingBar.dismiss();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("Reg", "createUserWithEmail:failure", task.getException());
                    showDialog("Errore", "Errore nella registrazione", android.R.drawable.ic_dialog_alert);
                    //loadingBar.dismiss();
                }
            }
        });

    }

    //carica il nome utente in firebase
    private void setName(String fullName) {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build();

        firebaseUser.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("setNome", "Nome Caricato con successo");
                }else{
                    Log.i("setNome", "Errore nel caricamento del nome");
                }
            }
        });
    }

    public void clickLogin(View view) {
        SendUserToLoginActivity();
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    //il nome deve avere almeno 3 lettere
    private boolean nameCorrect(String nome){
        if (nome.length() > 2)
            return true;
        else
            return false;
    }

    //il cognome deve avere almeno 4 lettere
    private boolean surnameCorrect(String cognome){
        if (cognome.length() > 3)
            return true;
        else
            return false;
    }

    //l'email deve contenere il carattere @
    private boolean emailCorrect(String email){
        return email.contains("@");
    }

    //la password deve avere 8 lettere
    private boolean pwCorrect(String password){
        String confermaPassword = mPasswordConfirm.getText().toString();
        return confermaPassword.equals(password) && password.length() > 7;
    }

    //dialog da mostrare dopo la registrazione
    private void showDialog(String title, String message, int icon){
        //creiamo un oggetto anonimo
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(icon)
                .show();
    }
}