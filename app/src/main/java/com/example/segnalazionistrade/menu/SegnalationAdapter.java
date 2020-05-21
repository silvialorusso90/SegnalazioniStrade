package com.example.segnalazionistrade.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnalazionistrade.MainActivity;
import com.example.segnalazionistrade.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SegnalationAdapter extends RecyclerView.Adapter<SegnalationAdapter.SegnalationViewHolder> {

    private Activity mActivity;
    private DatabaseReference mDataBaseRefence;
    private String mDisplayName;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String idUser;


    private long idS;

    //La classe DataSnapshot contiene tipo di dati provenienti da un database firebase alla nostra app
    private ArrayList<DataSnapshot> mDataSnapshot;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.child("idUser").getValue().equals(currentUser.getUid())){
                mDataSnapshot.add(dataSnapshot);
                notifyDataSetChanged();
            }
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
        TextView tipo, indirizzo, data, ora;
        ImageView imgTipoSegnalazione;
        ConstraintLayout.LayoutParams params;

        public SegnalationViewHolder(@NonNull View itemView) {
            super(itemView);

            tipo = itemView.findViewById(R.id.txtTipo);
            indirizzo = itemView.findViewById(R.id.txtAddress);
            imgTipoSegnalazione = itemView.findViewById(R.id.imgSegnalazione);
            data = itemView.findViewById(R.id.txtData);
            ora = itemView.findViewById(R.id.txtOra);
            params = (ConstraintLayout.LayoutParams) tipo.getLayoutParams();
        }
    }

    @NonNull
    @Override
    public SegnalationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.segnalazione, parent, false);
        SegnalationViewHolder viewHolder = new SegnalationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SegnalationViewHolder holder, int position) {
        final String stipo, sgravita, sintensita, stipoSOS, sindirizzo, sId, sData, sOra;

        //receive the vector from database
        DataSnapshot snapshot = mDataSnapshot.get(position);

        idS = snapshot.child("id").getValue(Long.class);
        stipo = snapshot.child("tipo").getValue(String.class);
        sgravita = snapshot.child("gravita").getValue(String.class);
        sintensita = snapshot.child("intensita").getValue(String.class);
        stipoSOS = snapshot.child("tipoSos").getValue(String.class);
        sindirizzo = snapshot.child("indirizzo").getValue(String.class);
        sData = snapshot.child("data").getValue(String.class);
        sOra = snapshot.child("ora").getValue(String.class);
        sId = String.valueOf(idS);

        if (!(sgravita == null)){
            holder.tipo.setText(stipo + " " + sgravita);
            holder.indirizzo.setText(sindirizzo);
            holder.data.setText(sData);
            holder.ora.setText(sOra);
            holder.imgTipoSegnalazione.setImageResource(R.drawable.incidente);
        }
        else if (!(sintensita == null)){
            holder.tipo.setText(stipo + " " + sintensita);
            holder.indirizzo.setText(sindirizzo);
            holder.data.setText(sData);
            holder.ora.setText(sOra);
            holder.imgTipoSegnalazione.setImageResource(R.drawable.traffico);
        }
        else if (!(stipoSOS == null)){
            holder.tipo.setText(stipo + " " + stipoSOS);
            holder.indirizzo.setText(sindirizzo);
            holder.data.setText(sData);
            holder.ora.setText(sOra);
            holder.imgTipoSegnalazione.setImageResource(R.drawable.sos);
        }
        else {
            holder.tipo.setText(stipo);
            holder.indirizzo.setText(sindirizzo);
            holder.data.setText(sData);
            holder.ora.setText(sOra);
            holder.imgTipoSegnalazione.setImageResource(R.drawable.strada_chiusa);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mActivity, holder.tipo);
                popupMenu.inflate(R.menu.remove_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteS(sId);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void deleteS(String sId) {
        DatabaseReference drSegnalation = FirebaseDatabase.getInstance().
                getReference("Segnalazioni").child(sId);
        drSegnalation.removeValue();
        Toast.makeText(mActivity, "Segnalazione rimossa", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(i);

    }

    @Override
    public int getItemCount() {
        return mDataSnapshot.size();
    }

    void clean(){
        mDataBaseRefence.removeEventListener(mListener);
    }

}
