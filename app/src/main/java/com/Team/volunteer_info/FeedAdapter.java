package com.Team.volunteer_info;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FeedAdapter extends FirestoreRecyclerAdapter<Feed, FeedAdapter.FeedHolder> {
    private static final String TAG = "PostsAdapter";

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FeedAdapter(@NonNull FirestoreRecyclerOptions<Feed> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final FeedHolder holder, int position, @NonNull final Feed model) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()){
//                                if(model.getuName().equals(document.get("username"))) {
//                                    model.setUserImage(document.get("imageurl").toString());
//                                    Picasso.get().load(model.getUserImage()).into(holder.userImage);
//                                }
//                            }
//
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

        holder.uName.setText(model.getuName());
        holder.description.setText(model.getDescription());
        Picasso.get().load(model.getImage()).into(holder.image);
    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed,
                parent,false);
        return new FeedHolder(v);
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        TextView uName;
        TextView description;
        ImageView image;
        ImageView userImage;
        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            uName = itemView.findViewById(R.id.uName);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }
}
