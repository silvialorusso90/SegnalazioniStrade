package com.example.segnalazionistrade.segnalazioni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.segnalazionistrade.MainActivity;
import com.example.segnalazionistrade.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class TrafficoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private Button btn;
    private String indirizzo, idUser, tipo, intensita;
    private float latitude, longitude;
    private int idTimeMillis = (int) (System.currentTimeMillis() / 1000);

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffico);

        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Segnala presenza di traffico");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUI();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        idUser = currentUser.getUid();

        tipo = "traffico";

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.intensita_traffico, android.R.layout.simple_spinner_dropdown_item);
        //creazione dell'adapter per lo spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner click listener
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //legge la latitudine
        myRef = mDatabase.getReference("Current Location").child("latitude");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                latitude = dataSnapshot.getValue(float.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //legge la longitudine
        myRef = mDatabase.getReference("Current Location").child("longitude");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                longitude = dataSnapshot.getValue(float.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //legge l'indirizzo
        myRef = mDatabase.getReference("Indirizzo corrente");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                indirizzo = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    private void initUI() {
        spinner = (Spinner)findViewById(R.id.spinnerTraffico);
        btn = (Button) findViewById(R.id.btn_invia);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //prendo il valore dell'elemento dello spinner selezionato
        intensita = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }


    public void inviaSegnalazione(View view) {
        //indirizzo = convertiIndirizzo(latitude, longitude);
        LocationHTraffico helper = new LocationHTraffico(idTimeMillis, longitude, latitude, idUser, tipo, indirizzo, intensita);
        if (helper.getIntensita().equals("Traffico"))
            Toast.makeText(this, "selezionare l'intensità", Toast.LENGTH_SHORT).show();
        else {
            myRef = mDatabase.getReference("Segnalazioni");
            myRef.child(String.valueOf(idTimeMillis)).setValue(helper);

            Intent i = new Intent(this, MainActivity.class);
            finish();
            startActivity(i);
        }

    }

    private String convertiIndirizzo(float latitude, float longitude) {
        String indirizzo = "";
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> list = geocoder.getFromLocation(latitude, longitude, 1);
            Address address = list.get(0);
            StringBuffer str = new StringBuffer();
            str.append(list.get(0).getAddressLine(0) + " ");
            indirizzo = str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indirizzo;
    }
}
