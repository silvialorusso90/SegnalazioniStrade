package com.example.segnalazionistrade.segnalazioni;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnalazionistrade.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class SegnalationAdapter extends RecyclerView.Adapter<SegnalationAdapter.SegnalationViewHolder> {

    private Activity mActivity;
    private DatabaseReference mDataBaseRefence;
    private String mDisplayName;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private String idUser;

    //La classe DataSnapshot contiene tipo di dati provenienti da un database firebase alla nostra app
    private ArrayList<DataSnapshot> mDataSnapshot;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mDataSnapshot.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    public SegnalationAdapter(Activity mActivity, DatabaseReference mDataBaseRefence, String mDisplayName) {
        this.mActivity = mActivity;
        this.mDataBaseRefence = mDataBaseRefence.child("Segnalazioni");
        this.mDisplayName = mDisplayName;

        mDataSnapshot = new ArrayList<>();

        mDataBaseRefence.addChildEventListener(mListener);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        idUser = currentUser.getUid();

        //Toast.makeText(mActivity, idUser, Toast.LENGTH_SHORT).show();
    }



    public class SegnalationViewHolder extends RecyclerView.ViewHolder{

        TextView tipo, gravita, indirizzo;

        ConstraintLayout.LayoutParams params;

        public SegnalationViewHolder(@NonNull View itemView) {
            super(itemView);

            tipo = itemView.findViewById(R.id.txtTipo);
            gravita = itemView.findViewById(R.id.txtGravita);
            indirizzo = itemView.findViewById(R.id.txtAddress);

            params = (ConstraintLayout.LayoutParams) tipo.getLayoutParams();
        }
    }



    @NonNull
    @Override
    public SegnalationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.segnalazione, parent, false);
        SegnalationViewHolder viewHolder = new SegnalationViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SegnalationViewHolder holder, int position) {
        String stipo, sgravita, sindirizzo;

        double slat, slong;
        //dal database riceviamo il vettore
        DataSnapshot snapshot = mDataSnapshot.get(position);
        String sid;
        sid = snapshot.child("idUser").getValue(String.class);

        if (idUser.equals(sid)){
            stipo = snapshot.child("tipo").getValue(String.class);
            sgravita = snapshot.child("gravita").getValue(String.class);
            slat = snapshot.child("latitude").getValue(double.class);
            slong = snapshot.child("longitude").getValue(double.class);
            //sindirizzo = getLcationAddress(slat, slong);
            sindirizzo = snapshot.child("indirizzo").getValue(String.class);



            holder.tipo.setText(stipo);
            holder.gravita.setText(sgravita);
            holder.indirizzo.setText(sindirizzo);
        }








    }

    /*private String getLcationAddress(double slat, double slong) {
        String stringLocation = "";
        try {
            Geocoder geocoder = new Geocoder(mActivity);
            List<Address> list = geocoder.getFromLocation(slat, slong, 1);
            Address address = list.get(0);
            StringBuffer str = new StringBuffer();
            str.append(list.get(0).getAddressLine(0) + " ");
            stringLocation = str.toString();
            //Toast.makeText(getContext(), indirizzo, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringLocation;
    }

     */

    @Override
    public int getItemCount() {
        return mDataSnapshot.size();
    }

    public void clean(){
        mDataBaseRefence.removeEventListener(mListener);
    }

}
