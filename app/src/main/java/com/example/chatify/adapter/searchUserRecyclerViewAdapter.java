package com.example.chatify.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatify.utils.FirebaseUtil;
import com.example.chatify.R;
import com.example.chatify.chatActivity;
import com.example.chatify.model.userModel;
import com.example.chatify.utils.androidUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class searchUserRecyclerViewAdapter extends FirestoreRecyclerAdapter<userModel, searchUserRecyclerViewAdapter.UserModelViewHolder> {
    Context context;
    public searchUserRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<userModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull userModel model) {
           holder.username.setText(model.getUsername());
           holder.phoneNumber.setText(model.getPhone());
           if(model.getUserId().equals(FirebaseUtil.currentUserId())){
               holder.username.setText(model.getUsername()+"me");

           }
           holder.itemView.setOnClickListener(v ->{
                 Intent intent=new Intent(context, chatActivity.class);
                 androidUtils.passUserModelAsIntent(intent,model);
                 intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);

           });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row,parent,false);
        return new UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView phoneNumber;
        ImageView progile_pic;
        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.recycler_username);
            phoneNumber=itemView.findViewById(R.id.recycler_phone_number);
            progile_pic=itemView.findViewById(R.id.profile_layout);


        }
    }
}
