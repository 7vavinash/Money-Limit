package com.avinash.moneylimit;

import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avinash.moneylimit.Models.Message;
import com.avinash.moneylimit.viewHolders.MessageViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mData;
    FirebaseAuth mAuth;
    EditText et_message;
    Button b_send;
    FirebaseUser user;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Message, MessageViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        mData = mDataBase.child("chats");
        recyclerView = (RecyclerView) findViewById(R.id.rv_messages);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager ll = new LinearLayoutManager(getApplication());
        ll.setReverseLayout(true);
        ll.setStackFromEnd(true);
        recyclerView.setLayoutManager(ll);


        et_message = (EditText) findViewById(R.id.et_message);
//        etphone = (EditText) findViewById(R.id.etphone);
//
        b_send = (Button) findViewById(R.id.b_send);

        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_message.getText().toString().isEmpty()){
                    sendMessage();
                }
            }
        });

    };

    @Override
    protected void onStart() {
        super.onStart();

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                HashMap userDetails = (HashMap<String, String>) dataSnapshot.getValue();
//                Log.v("userdetails", userDetails.toString());
//                etname.setText(userDetails.get("Name").toString());
//                etphone.setText(userDetails.get("Phone Number").toString());

                mAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(Message.class, R.layout.message_view, MessageViewHolder.class,
                        mData) {
                    @Override
                    protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                        viewHolder.bindToMessage(model);
                    }
                };
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

//    private void updateDetails(){
//        HashMap<String, Object> user_detials = new HashMap<>();
//        user_detials.put("Name",etname.getText().toString());
//        user_detials.put("Phone Number",etphone.getText().toString());
//        mData.setValue(user_detials);
//    }
    private void sendMessage(){
        DatabaseReference newMessage = mData.push();
        Message message = new Message();
        message.setMessage(et_message.getText().toString());
        message.setSender(user.getDisplayName());
        message.setTimestamp(new Date().toString());

        newMessage.setValue(message);

    }
}
