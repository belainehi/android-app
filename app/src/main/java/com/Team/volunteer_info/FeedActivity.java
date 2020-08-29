package com.Team.volunteer_info;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = "FeedActivity";

    private EditText etDescription;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private TextView cancelAdd;
    //private boolean pictureAdded;
    private String timestamp;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    Uri downloadUri;

    //String username;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        timestamp = String.valueOf(System.currentTimeMillis());
        mAuth = FirebaseAuth.getInstance();

        btnCaptureImage = findViewById(R.id.btnCapturePicture);
        ivPostImage = findViewById(R.id.ivPostImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        etDescription = findViewById(R.id.etDescription);
        cancelAdd = findViewById(R.id.cancelAdd);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        pd = new ProgressDialog(this);

        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FeedActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageClick(v);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();

                if(description.isEmpty()) {
                    Toast.makeText(FeedActivity.this, "Description cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if( ivPostImage==null){
                    Toast.makeText(FeedActivity.this, "No image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(description, mAuth, db);
            }
        });

    }

    private void savePost(final String description, FirebaseAuth mAuth, final FirebaseFirestore db) {
        new ProgressDialog(FeedActivity.this);
        pd.setMessage("Publishing post...");
        pd.show();

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        final String userID = firebaseUser.getUid();

        //handleUpload();

        username = getUserName(firebaseUser, db);

        if(downloadUri!= null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("uId", userID);
            map.put("uName", firebaseUser.getDisplayName());
            map.put("postId", timestamp);
            map.put("description", description);
            map.put("image", downloadUri.toString());
            db.collection("posts")
                    .add(map)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(FeedActivity.this, "Post made", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            pd.dismiss();
                            Intent i = new Intent(FeedActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FeedActivity.this, "Error posting", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Error adding document", e);
                            pd.dismiss();
                        }
                    });
        }
        else{
            pd.dismiss();
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
        }
    }


    public String getUserName(FirebaseUser firebaseUser, FirebaseFirestore db){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return null;
    }

    public void handleImageClick(View view) {
        //Take an image from the App. Start a new Intent to for Capturing an image.
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 0);
                    }
                }
                else if (options[which].equals("Choose from Gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if(options[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    //Set Image to your Profile
                    ivPostImage.setImageBitmap(bitmap);
                    handleUpload();
            }
        }
        if (requestCode == 1) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = null;
                    if (data != null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            //Toast.makeText(AddPostActivity.this, "Error, " + e, Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    ivPostImage.setImageBitmap(bitmap);
                    handleUpload();
            }
        }
    }

    private void handleUpload() {
        new ProgressDialog(FeedActivity.this);
        pd.setMessage("Adding image...");
        pd.show();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("postImages")
                .child(timestamp + ".jpeg");
        // Get the data from an ImageView as bytes
        ivPostImage.setDrawingCacheEnabled(true);
        ivPostImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivPostImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    pd.dismiss();
                    //Toast.makeText(AddPostActivity.this, "Successful" + downloadUri, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failures
                    // ...
                    pd.dismiss();
                    Toast.makeText(FeedActivity.this, "Unsuccessful" + downloadUri, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
