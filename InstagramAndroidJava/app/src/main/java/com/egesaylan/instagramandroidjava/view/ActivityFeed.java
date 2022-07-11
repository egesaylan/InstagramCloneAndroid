package com.egesaylan.instagramandroidjava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.egesaylan.instagramandroidjava.R;
import com.egesaylan.instagramandroidjava.adapter.Adapter;
import com.egesaylan.instagramandroidjava.databinding.ActivityFeedBinding;
import com.egesaylan.instagramandroidjava.databinding.ActivityMainBinding;
import com.egesaylan.instagramandroidjava.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class ActivityFeed extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore store;
    ArrayList<Post> postArrayList;
    private ActivityFeedBinding binding;
    Adapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new Adapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);
    }

    private void getData(){

        store.collection("Posts").orderBy("Date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(ActivityFeed.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String userMail = (String) data.get("UserMail");
                        String downloadUrl = (String) data.get("DownloadUrl");
                        String comment = (String) data.get("Comment");

                        Post post = new Post(userMail,comment,downloadUrl);
                        postArrayList.add(post);

                    }

                    postAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == R.id.post){
            Intent intentToUploadPage = new Intent(ActivityFeed.this,ActivityUpload.class);
            startActivity(intentToUploadPage);
        }else if( item.getItemId() == R.id.signOut){

            auth.signOut();

            Intent intentToLoginPage = new Intent(ActivityFeed.this,MainActivity.class);
            startActivity(intentToLoginPage);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}