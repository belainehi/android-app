package com.Team.volunteer_info.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Team.volunteer_info.Feed;
import com.Team.volunteer_info.FeedActivity;
import com.Team.volunteer_info.FeedAdapter;
import com.Team.volunteer_info.LoginActivity;
import com.Team.volunteer_info.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FragmentFeed extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference postRef = db.collection("posts");
    private FeedAdapter adapter;

    final private String POSTS_FRAGMENT = "EventsFragment";

    private ImageView addPost;
    private Button btnLogout;
    DatabaseReference ref;

    public FragmentFeed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView(view);

        addPost = view.findViewById(R.id.addPost);
        btnLogout = view.findViewById(R.id.logoutBtn);

        ref = FirebaseDatabase.getInstance().getReference().child("posts");
        final FirebaseAuth instance = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(POSTS_FRAGMENT, "Logging out.");
                //logs user out using firebase
                Log.d(POSTS_FRAGMENT, "logging off: "+instance.getCurrentUser());
                signOut(instance);
                goLoginActivity();
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FeedActivity.class));
            }
        });
    }

    public void goLoginActivity()
    {
        //kicks user back out to the login page.
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        intent.putExtra("finish", true);
        // allows us to clear the activities that were being held in the stack prior to logging out
        // this means that the user cannot do a 'shadow login' when they hit back after signing out and be
        // signed in as a 'null' user
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void signOut(FirebaseAuth instance)
    {
        instance.signOut();
    }

    private void setUpRecyclerView(View view){

        Query query = postRef;
        FirestoreRecyclerOptions<Feed> options = new FirestoreRecyclerOptions.Builder<Feed>()
                .setQuery(query,Feed.class)
                .build();
        adapter = new FeedAdapter(options);
        RecyclerView recyclerView = view.findViewById(R.id.rvPosts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
