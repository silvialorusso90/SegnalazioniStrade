package com.example.segnalazionistrade.segnalazioni;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

public class IncidenteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "inc";

    Spinner spinner;
    Button btn;
    String gravita, sLatitude, sLongitude, indirizzo, idUser, tipo;
    TextView lat, lon;
    float latitude, longitude;
    int idTimeMillis = (int) (System.currentTimeMillis() / 1000);

    FirebaseDatabase mDatabase;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);


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
                lat.setText(sLatitude);
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
                lon.setText(sLongitude);
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
        spinner = (Spinner)findViewById(R.id.spinner);
        btn = (Button) findViewById(R.id.btn_invia);
        lat = (TextView) findViewById(R.id.textView6);
        lon = (TextView) findViewById(R.id.textView7);
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
        //indirizzo = convertiIndirizzo(latitude, longitude);
        LocationHIncidente helper = new LocationHIncidente(idTimeMillis, longitude, latitude, idUser, gravita, tipo, indirizzo);
        if (helper.getGravita().isEmpty())
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
