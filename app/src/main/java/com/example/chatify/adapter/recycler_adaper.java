package com.example.chatify.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatify.utils.FirebaseUtil;
import com.example.chatify.R;
import com.example.chatify.chatActivity;
import com.example.chatify.model.chatMessageModel;
import com.example.chatify.utils.androidUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class recycler_adaper extends FirestoreRecyclerAdapter<chatMessageModel, recycler_adaper.ChatModelViewHolder> {
    Context context;
    public recycler_adaper(@NonNull FirestoreRecyclerOptions<chatMessageModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull chatMessageModel model) {
          if(model.getSenderId().equals((FirebaseUtil.currentUserId()))){
              holder.leftChatLayout.setVisibility(View.GONE);
              holder.rightChatLayout.setVisibility(View.VISIBLE);
              holder.rightChatTextView.setText(model.getMessage());

          }
          else{
              holder.rightChatLayout.setVisibility(View.GONE);
              holder.leftChatLayout.setVisibility(View.VISIBLE);
              holder.leftChatTextView.setText(model.getMessage());
          }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row,parent,false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftChatLayout,rightChatLayout;
        TextView leftChatTextView,rightChatTextView;
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout=itemView.findViewById(R.id.sender_layout);
            rightChatLayout=itemView.findViewById(R.id.reciever_layout);
            leftChatTextView=itemView.findViewById(R.id.sender_textview);
            rightChatTextView=itemView.findViewById(R.id.reciever_textview);



        }
    }
}
