package com.avinash.moneylimit;

import android.content.Intent;
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

public class FriendChatActivity extends AppCompatActivity {
    DatabaseReference mDataBase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mData, mChat;
    FirebaseAuth mAuth;
    EditText et_message;
    Button b_send;
    FirebaseUser user;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Message, MessageViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_chat);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        Intent i = getIntent();
        String uid = i.getStringExtra("uid");
        String chat_key;
        if (uid.compareTo(user.getUid()) > 0 ){
            chat_key = uid+ user.getUid();
        }else{
            chat_key = user.getUid()+uid;
        }

        mData = mDataBase.child("user_chats");
        mChat = mData.child(chat_key);

        recyclerView = (RecyclerView) findViewById(R.id.rv_messages);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager ll = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(ll);

        mChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mAdapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(Message.class, R.layout.message_view, MessageViewHolder.class,
                        mChat) {
                    @Override
                    protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                        if (model.getSender().contentEquals(user.getDisplayName())) {
                            viewHolder.itemView.findViewById(R.id.tv_sender).setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                            viewHolder.itemView.findViewById(R.id.tv_message).setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                        }
                        viewHolder.bindToMessage(model);
                    }
                };
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        recyclerView.setAdapter(mAdapter);


        et_message = (EditText) findViewById(R.id.et_message);

        b_send = (Button) findViewById(R.id.b_send);

        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_message.getText().toString().isEmpty()) {
                    sendMessage();
                }
            }
        });

    }

    //    private void updateDetails(){
//        HashMap<String, Object> user_detials = new HashMap<>();
//        user_detials.put("Name",etname.getText().toString());
//        user_detials.put("Phone Number",etphone.getText().toString());
//        mData.setValue(user_detials);
//    }
    private void sendMessage() {
        DatabaseReference newMessage = mChat.push();
        Message message = new Message();
        message.setMessage(et_message.getText().toString());
        message.setSender(user.getDisplayName());
        message.setTimestamp(new Date().toString());

        newMessage.setValue(message);
        et_message.setText("");

    }
}
