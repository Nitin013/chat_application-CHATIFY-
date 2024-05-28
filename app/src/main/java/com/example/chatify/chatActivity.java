package com.example.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatify.adapter.recycler_adaper;
import com.example.chatify.adapter.searchUserRecyclerViewAdapter;
import com.example.chatify.model.chatMessageModel;
import com.example.chatify.model.chat_room_model;
import com.example.chatify.model.userModel;
import com.example.chatify.utils.FirebaseUtil;
import com.example.chatify.utils.androidUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class chatActivity extends AppCompatActivity {
    userModel otherUser;
    recycler_adaper adapter;
EditText messageInput;
chat_room_model chatRoomModel;
String chatRoomId;
ImageButton sendMessageBtn;
ImageButton backBtn;
TextView otherUsername;
Timestamp s;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        otherUser = androidUtils.getUserModelFromIntent(getIntent());
        chatRoomId= FirebaseUtil.getChatRoomId(FirebaseUtil.currentUserId(),otherUser.getUserId());
        otherUsername=findViewById(R.id.otherUsername);
        messageInput=findViewById(R.id.sendMessageInput);
        sendMessageBtn=findViewById(R.id.message_send_btn);
        backBtn=findViewById(R.id.back_botton);
        recyclerView=findViewById(R.id.chat_recycler);

        backBtn.setOnClickListener(v->{
            onBackPressed();
        });
         otherUsername.setText(otherUser.getUsername());
         sendMessageBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String message=messageInput.getText().toString().trim();
                 if(message.isEmpty()){
                     return;
                 }
                 sendMessageToUser(message);
             }
         });

         getOrCreateChatRoomModel();
         setupChatRecyclerView();
    }
    void setupChatRecyclerView(){
        Query query = FirebaseUtil.getChatroomMessageReference(chatRoomId).orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<chatMessageModel> opttions =new FirestoreRecyclerOptions.Builder<chatMessageModel>().setQuery(query,chatMessageModel.class).build();
        adapter=new recycler_adaper(opttions,getApplicationContext());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }


    void getOrCreateChatRoomModel(){
        FirebaseUtil.getChatRoomReference(chatRoomId).get().addOnCompleteListener(task->{
           if(task.isSuccessful()){

               chatRoomModel = task.getResult().toObject(chat_room_model.class);

               if(chatRoomModel==null){

                   chatRoomModel=new chat_room_model(
                           chatRoomId,
                           Arrays.asList(FirebaseUtil.currentUserId(),otherUser.getUserId()),
                           Timestamp.now(),
                           ""
                           );

                   FirebaseUtil.getChatRoomReference(chatRoomId).set(chatRoomModel);

               }
           }
        });

    }
    void sendMessageToUser(String message){
        chatRoomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        chatRoomModel.setLastMessageTimestamp(Timestamp.now());
        FirebaseUtil.getChatRoomReference(chatRoomId).set(chatRoomModel);
        chatMessageModel chatmessagemodel=new chatMessageModel(message,FirebaseUtil.currentUserId(),Timestamp.now());
        FirebaseUtil.getChatroomMessageReference(chatRoomId).add(chatmessagemodel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    messageInput.setText("");
                }
            }
        });

    }
}