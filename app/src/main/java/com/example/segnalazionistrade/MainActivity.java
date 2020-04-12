package com.example.segnalazionistrade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.segnalazionistrade.autenticazione.LoginActivity;
import com.example.segnalazionistrade.segnalazioni.SegnalationActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFirebase();

        initUI();

    }

    private void initUI() {
        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.utente) + currentUser.getDisplayName());

        ViewPager myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        TabsAccessorAdapter myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);

        TabLayout myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //chiamato quando un item del menu viene selezionato
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        //se è stato premuto il tasto logout
        if (id == R.id.logoutItem) {
            Log.i("Main", "Logout selezionato");

            //logout
            mAuth.signOut();
            updateUI();

        }
        else if (id == R.id.segnalationItem){
            sendToSegnalationActivity();
        }

        else if (id == R.id.infoItem){
            sendToInfoActivity();

        }
        else{
            sendToProfileActivity();

        }
        return true;
    }

    private void sendToSegnalationActivity() {
        Intent segnalationIntent = new Intent(this, SegnalationActivity.class);
        startActivity(segnalationIntent);
    }

    private void sendToProfileActivity() {
        Intent profiloIntent = new Intent(this, ProfiloActivity.class);
        startActivity(profiloIntent);
    }

    private void sendToInfoActivity() {
        Intent infoIntent = new Intent(this, InfoActivity.class);
        startActivity(infoIntent);
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(loginIntent);
    }

    private void updateUI() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
            SendUserToLoginActivity();
    }

}