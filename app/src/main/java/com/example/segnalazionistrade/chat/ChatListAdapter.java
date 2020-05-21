package com.example.segnalazionistrade.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.segnalazionistrade.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

import static com.example.segnalazionistrade.R.color.colorAccent;
import static com.example.segnalazionistrade.R.color.colorPrimary;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>{

    private Activity mActivity;
    private DatabaseReference mDataBaseRefence;
    private String mDisplayName;

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

    ChatListAdapter(Activity activity, DatabaseReference ref, String name){
        mActivity = activity;
        mDataBaseRefence = ref.child("Chat");
        mDisplayName = name;
        mDataSnapshot = new ArrayList<>();

        mDataBaseRefence.addChildEventListener(mListener);

    }

    class ChatViewHolder extends RecyclerView.ViewHolder{

        private TextView autore;
        private TextView messaggio;
        private TextView data;
        private TextView ora;
        private LinearLayout.LayoutParams params;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            autore = (TextView) itemView.findViewById(R.id.tv_autore);
            messaggio = (TextView) itemView.findViewById(R.id.tv_messaggio);
            data = (TextView) itemView.findViewById(R.id.txtData);
            ora = (TextView) itemView.findViewById(R.id.txtOra);
            params = (LinearLayout.LayoutParams) autore.getLayoutParams();
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.chat_msg_row, parent, false);
        ChatViewHolder vh = new ChatViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        DataSnapshot snapshot = mDataSnapshot.get(position);
        Message msg = snapshot.getValue(Message.class);

        String testo = msg.getAuthor() + " " + msg.getDate() + " " + msg.getHour()
                + "\n" + msg.getMessage();
        holder.messaggio.setText(testo);

        boolean sonoIo = msg.getAuthor().equals(mDisplayName);
        setChatItemStyle(sonoIo, holder);

    }

    @SuppressLint("ResourceAsColor")
    private void setChatItemStyle(boolean sonoIo, ChatViewHolder holder){
        if (sonoIo){
            holder.params.gravity = Gravity.END;
            holder.messaggio.setBackgroundResource(R.drawable.sender_messages_layout);
            holder.messaggio.setTextColor(Color.WHITE);
        }
        else {
            holder.params.gravity = Gravity.START;
            holder.messaggio.setBackgroundResource(R.drawable.receiver_messages_layout);
            holder.messaggio.setTextColor(Color.WHITE);

        }
        holder.messaggio.setLayoutParams(holder.params);
    }

    @Override
    public int getItemCount() {
        return mDataSnapshot.size();
    }

    void clean(){
        mDataBaseRefence.removeEventListener(mListener);
    }

}




