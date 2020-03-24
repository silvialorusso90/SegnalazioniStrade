package com.example.segnalazionistrade.chats;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnalazionistrade.R;
import com.example.segnalazionistrade.map.Segnalazione;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class SegnalationAdapter extends RecyclerView.Adapter<SegnalationAdapter.SegnalationViewHolder> {

    private Activity mActivity;
    private DatabaseReference mDataBaseRefence;

    //La classe DataSnapshot contiene tipo di dati provenienti da un database firebase alla nostra app
    private ArrayList<DataSnapshot> mDataSnapshot;



    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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


    public SegnalationAdapter(Activity mActivity, DatabaseReference mDataBaseRefence) {
        this.mActivity = mActivity;
        this.mDataBaseRefence = mDataBaseRefence.child("Segnalazioni");

        mDataSnapshot = new ArrayList<>();

        //Colleghiamo il database all'adapter e al listener
        mDataBaseRefence.addChildEventListener(mListener);

    }

    public class SegnalationViewHolder extends RecyclerView.ViewHolder {

        TextView txtTipo, txtGravita, txtIndirizzo;

        public SegnalationViewHolder(@NonNull View itemView) {
            super(itemView);

            //inizializzo gli elementi
            txtTipo = (TextView) itemView.findViewById(R.id.txtTipo);
            txtGravita = (TextView) itemView.findViewById(R.id.txtGravita);
            txtIndirizzo = (TextView) itemView.findViewById(R.id.txtAddress);
        }

    }

    @NonNull
    @Override
    public SegnalationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.segnalazione, parent, false);
        SegnalationViewHolder vh = new SegnalationViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SegnalationViewHolder holder, int position) {

        DataSnapshot snapshot = mDataSnapshot.get(position);

        //Leggere il contenuto di SnapShot e lo mette in msg
        Segnalazione segnalazione = snapshot.getValue(Segnalazione.class);

        //Riempiamo il ViewHolder


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
