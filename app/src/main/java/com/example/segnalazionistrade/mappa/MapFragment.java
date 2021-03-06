package com.example.segnalazionistrade.mappa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.segnalazionistrade.R;
import com.example.segnalazionistrade.segnalazioni.LocationH;
import com.example.segnalazionistrade.segnalazioni.LocationHIncidente;
import com.example.segnalazionistrade.segnalazioni.LocationHSos;
import com.example.segnalazionistrade.segnalazioni.LocationHTraffico;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private float latitude, longitude;
    private int zoom = 15;

    private ImageButton mVoicebtn;
    private TextToSpeech textToSpeach;

    private String idUser, tipoSegnalazione, gravitaSegnalazione, confermaSegnalazione, locationAddress;
    private int idTimeMillis = (int) (System.currentTimeMillis() / 1000);

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //constantly updates the user's location
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.
                    ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, locationListener);
            }
        }
    }

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        View mView = inflater.inflate(R.layout.fragment_map, container, false);

        init();

        mVoicebtn = mView.findViewById(R.id.voiceBtn);

        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.fab);

        textToSpeach = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = textToSpeach.setLanguage(Locale.ITALIAN);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.d("lang", "lingua non supportata");
                    }
                    else {
                        mVoicebtn.setEnabled(true);
                    }
                }
                else {
                    Log.d("lang", "inizializzazione fallita");
                }

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passa all'activity
                BottomSheetFragment button = new BottomSheetFragment();
                button.show(getFragmentManager(), "open");
            }
        });

        mVoicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speech();
            }
        });

        return mView;
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        idUser = currentUser.getUid();

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Segnalazioni");
    }

    private void speech() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ITALIAN);

        //start intent
        try {
            //no error
            startActivityForResult(i, REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e){
            //error
            Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {

                //get text array from voice intent
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                //set to textview
                String voiceListen = result.get(0);

                LocationHIncidente helper = new LocationHIncidente(idTimeMillis, latitude, longitude, idUser);
                myRef = mDatabase.getReference("Segnalazioni");

                myRef.child(String.valueOf(idTimeMillis)).child("id").setValue(idTimeMillis);
                myRef.child(String.valueOf(idTimeMillis)).child("latitude").setValue(latitude);
                myRef.child(String.valueOf(idTimeMillis)).child("longitude").setValue(longitude);
                myRef.child(String.valueOf(idTimeMillis)).child("indirizzo").setValue(locationAddress);


                myRef.child(String.valueOf(idTimeMillis)).child("idUser").setValue(currentUser.getUid());
                tipoSegnalazione = voiceListen;
                if ((tipoSegnalazione.equals("Incidente")) || (tipoSegnalazione.equals("incidente"))) {

                    myRef.child(String.valueOf(idTimeMillis)).child("tipo").setValue(tipoSegnalazione);
                    textToSpeach.speak("Inserisci la gravità: lieve, moderata, alta",
                            TextToSpeech.QUEUE_FLUSH, null);

                    mVoicebtn.performClick();
                }
                gravitaSegnalazione = voiceListen;
                if (((tipoSegnalazione.equals("Incidente")) || (tipoSegnalazione.equals("incidente"))) &&
                        (gravitaSegnalazione.equals("Lieve")) || (gravitaSegnalazione.equals("lieve"))) {
                    myRef.child(String.valueOf(idTimeMillis)).child("gravita").setValue(gravitaSegnalazione);
                    textToSpeach.speak("hai detto lieve, confermi?", TextToSpeech.QUEUE_FLUSH, null);
                    mVoicebtn.performClick();
                }
                if (((tipoSegnalazione.equals("Incidente")) || (tipoSegnalazione.equals("incidente"))) &&
                        (gravitaSegnalazione.equals("Moderata")) || (gravitaSegnalazione.equals("moderata"))) {
                    myRef.child(String.valueOf(idTimeMillis)).child("gravita").setValue(gravitaSegnalazione);
                    textToSpeach.speak("hai detto moderata, confermi?", TextToSpeech.QUEUE_FLUSH, null);
                    mVoicebtn.performClick();

                }
                if (((tipoSegnalazione.equals("Incidente")) || (tipoSegnalazione.equals("incidente"))) &&
                        (gravitaSegnalazione.equals("Grave")) || (gravitaSegnalazione.equals("grave"))) {
                    textToSpeach.speak("hai detto alta, confermi?", TextToSpeech.QUEUE_FLUSH, null);
                    mVoicebtn.performClick();
                }
                confermaSegnalazione = voiceListen;
                if ((tipoSegnalazione.equals("Incidente")) || (tipoSegnalazione.equals("incidente")) &&
                        (gravitaSegnalazione.equals("Lieve")) || (gravitaSegnalazione.equals("lieve")) ||
                        (gravitaSegnalazione.equals("Moderata")) || (gravitaSegnalazione.equals("moderata"))
                        || (gravitaSegnalazione.equals("Grave")) || (gravitaSegnalazione.equals("grave")) &&
                        (confermaSegnalazione.equals("Ok")) || (confermaSegnalazione.equals("ok"))) {
                    textToSpeach.speak("segnalazione inviata", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        MapsInitializer.initialize(Objects.requireNonNull(getContext()));
        locationManager = (LocationManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                latitude = (float) location.getLatitude();
                longitude = (float) location.getLongitude();

                locationAddress = getLcationAddress();

                //save locationaddress
                mDatabase.getReference("Indirizzo corrente").setValue(locationAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });

                LocationH helper = new LocationH(longitude, latitude);

                //save location
                mDatabase.getReference("Current Location")
                        .setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

                LatLng posizioneutente = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(posizioneutente).title("Posizione utente"));

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            LocationHIncidente helperI = child.getValue(LocationHIncidente.class);
                            LocationH helper = child.getValue(LocationH.class);
                            LocationHTraffico helperT = child.getValue(LocationHTraffico.class);
                            LocationHSos helperS = child.getValue(LocationHSos.class);

                            LatLng segnalazione = new LatLng(helperI.getLatitude(), helperI.getLongitude());
                            if (helperI.getTipo().equals("incidente")){
                                mMap.addMarker(new MarkerOptions().position(segnalazione).title("incidente " +
                                        helperI.getGravita()).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                            }
                            else if(helper.getTipo().equals("strada chiusa")){
                                mMap.addMarker(new MarkerOptions().position(segnalazione).title("strada chiusa ")
                                        .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            }
                            else if (helperT.getTipo().equals("traffico")){
                                mMap.addMarker(new MarkerOptions().position(segnalazione).title("traffico " +
                                        helperT.getIntensita()).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                            }
                            else{
                                mMap.addMarker(new MarkerOptions().position(segnalazione).title("SOS " +
                                        helperS.getTipoSos()).icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Se SDK < 23 (nel nostro caso non è possibile perchè abbiamo messo 23 come api minima)
        if(Build.VERSION.SDK_INT >= 23){

            //verifica permessi
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){

                //non abbiamo il permesso quindi lo chiediamo
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.
                        ACCESS_FINE_LOCATION}, 1);
            }
            else {
                //abbiamo già i permessi
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, locationListener);

                Location ultima_posizione = locationManager.getLastKnownLocation(LocationManager.
                        GPS_PROVIDER);
                if(ultima_posizione != null){
                    LatLng posizioneutente = new LatLng(ultima_posizione.getLatitude(),
                            ultima_posizione.getLongitude());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(posizioneutente).title("Ultima posizione"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posizioneutente, zoom));

                }
                else
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
            }

        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                    0, locationListener);
        }
    }

    private String getLcationAddress() {
        String stringLocation = "";
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> list = geocoder.getFromLocation(latitude, longitude, 1);
            Address address = list.get(0);
            StringBuffer str = new StringBuffer();
            str.append(list.get(0).getAddressLine(0) + " ");
            stringLocation = str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.getMessage();
        }
        return stringLocation;
    }

}