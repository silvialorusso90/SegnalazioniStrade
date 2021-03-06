package com.example.segnalazionistrade.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.segnalazionistrade.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


public class ChatFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    //UI
    private EditText mInputText;

    private ChatListAdapter chatListAdapter;
    private RecyclerView rvChatMsg;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
        ChatsViewModel chatsViewModel = ViewModelProviders.of(this).get(ChatsViewModel.class);
            root = getLayoutInflater().inflate(R.layout.fragment_chats, container, false);

            mAuth = FirebaseAuth.getInstance();

            initUI();

            //Riferimento alla RecyclerView
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvChatMsg.setLayoutManager(linearLayoutManager);

            //Riferimento alla locazione del database generale
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

            //Creo l'oggetto Adapter
            chatListAdapter = new ChatListAdapter(getActivity(), mDatabaseReference,
                    Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());
            rvChatMsg.setAdapter(chatListAdapter);
            return root;
        }

        private void initUI() {
            mInputText = (EditText) root.findViewById(R.id.etMessaggio);
            Button mButtonInvia = (Button) root.findViewById(R.id.btn_invia_m);
            rvChatMsg = (RecyclerView) root.findViewById(R.id.rv_chat);

            //Tasto enter
            mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    inviaMessaggio();
                    return true;
                }
            });

            mButtonInvia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inviaMessaggio();

                }
            });
        }

        private void inviaMessaggio() {

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            String currentTime = currentTimeFormat.format(calForTime.getTime());
            String inputMsg = mInputText.getText().toString();
            if (!inputMsg.equals("")) {

                //New message
                Message chat = new Message(inputMsg, Objects.requireNonNull(mAuth.getCurrentUser())
                        .getDisplayName(),
                        currentDate, currentTime);

                //Save the message
                mDatabaseReference.child("Chat").push().setValue(chat);
                mInputText.setText("");
            }
        }

        //Quando l'activity entra in onstop possiamo rimuovere il listener
        @Override
        public void onStop() {
            super.onStop();

            chatListAdapter.clean();
        }

}
