package com.example.chatify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chatify.adapter.searchUserRecyclerViewAdapter;
import com.example.chatify.model.userModel;
import com.example.chatify.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class search_user extends AppCompatActivity {
       EditText search_user_edittext;
       ImageButton search_user_image;
       ImageButton back_btn;
       RecyclerView recyclerView;
       searchUserRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        search_user_edittext=findViewById(R.id.search_user_edittext);
        search_user_image=findViewById(R.id.search_user_btn);
        back_btn=findViewById(R.id.back_botton);
        recyclerView=findViewById(R.id.search_user_recyler_view);

        search_user_edittext.requestFocus();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        search_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm=search_user_edittext.getText().toString();
                if(searchTerm.isEmpty()||searchTerm.length()<3){
                    search_user_edittext.setError("invalid username");
                }
                setupSearchRecyclerView(searchTerm);
            }
        });
    }
    void setupSearchRecyclerView(String searchTerm){
        Query query = FirebaseUtil.alluserCollectionReference().whereGreaterThanOrEqualTo("username",searchTerm);
        FirestoreRecyclerOptions<userModel> opttions =new FirestoreRecyclerOptions.Builder<userModel>().setQuery(query,userModel.class).build();
         adapter=new searchUserRecyclerViewAdapter(opttions,getApplicationContext());
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(adapter);
         adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }
}