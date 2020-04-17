package com.example.segnalazionistrade.segnalazioni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class IncidenteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "inc";

    private Spinner spinner;
    private Button btn;
    private String gravita, sLatitude, sLongitude, indirizzo, idUser, tipo;
    private float latitude, longitude;
    private int idTimeMillis = (int) (System.currentTimeMillis() / 1000);

    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);

        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Segnala un incidente");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initUI();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        idUser = currentUser.getUid();

        tipo = "incidente";

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.gravita_incidente, android.R.layout.simple_spinner_dropdown_item);
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
                Integer i = Integer.valueOf((int) (latitude*1000));
                sLatitude = String.valueOf(i);
                Log.d(TAG, "Value latitude is: " + latitude);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
                Integer i = Integer.valueOf((int) (longitude*1000));
                sLongitude = String.valueOf(i);
                Log.d(TAG, "Value longitude is: " + longitude);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
                Log.d(TAG, "Value indirizzo is: " + indirizzo);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }




    private void initUI() {
        spinner = (Spinner)findViewById(R.id.spinnerIncidente);
        btn = (Button) findViewById(R.id.btn_invia);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //prendo il valore dell'elemento dello spinner selezionato
        gravita = parent.getItemAtPosition(position).toString();

        //visualizzo l'elemento selezionato
        /*Snackbar.make(view, item, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
         */
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }


    public void inviaSegnalazione(View view) {

        Calendar calForDate = Calendar.getInstance();
        //SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM, dd, yyyy");
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String data = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        String ora = currentTimeFormat.format(calForTime.getTime());



        //indirizzo = convertiIndirizzo(latitude, longitude);
        LocationHIncidente helper = new LocationHIncidente(idTimeMillis, longitude, latitude,
                idUser, tipo, indirizzo, data, ora, gravita);
        if (helper.getGravita().equals("Gravità"))
          Toast.makeText(this, "selezionare la gravità", Toast.LENGTH_SHORT).show();
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
