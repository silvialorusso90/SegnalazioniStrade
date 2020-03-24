package com.example.segnalazionistrade.segnalazioni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.segnalazionistrade.R;
import com.example.segnalazionistrade.chats.SegnalationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SegnalationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    private SegnalationAdapter segnalationAdapter;
    private RecyclerView rvSegnalation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnalation);

        mAuth = FirebaseAuth.getInstance();

        //Riferimento alla locazione del database generale
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //Riferimento alla RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSegnalation.setLayoutManager(linearLayoutManager);

       // segnalationAdapter = new SegnalationAdapter(this, mDatabaseReference);
        rvSegnalation.setAdapter(segnalationAdapter);
    }
}
