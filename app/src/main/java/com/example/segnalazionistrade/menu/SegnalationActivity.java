package com.example.segnalazionistrade.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.segnalazionistrade.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SegnalationActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    private SegnalationAdapter segnalationAdapter;
    private RecyclerView rvSegnalation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnalation);

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Le mie segnalazioni");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvSegnalation = (RecyclerView) findViewById(R.id.rv_segnalazione);

        mAuth = FirebaseAuth.getInstance();

        //Riferimento alla RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSegnalation.setLayoutManager(linearLayoutManager);

        //Riferimento alla locazione del database generale
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Segnalazioni");

        segnalationAdapter = new SegnalationAdapter(this, mDatabaseReference,
                mAuth.getCurrentUser().getEmail());

       // segnalationAdapter = new SegnalationAdapter(this, mDatabaseReference);
        rvSegnalation.setAdapter(segnalationAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        segnalationAdapter.clean();
    }
}
