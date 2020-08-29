package com.Team.volunteer_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    final private String TAG = "RegisterActivity";
    EditText username;
    EditText user_email;
    EditText new_password;
    EditText confirm_password;
    EditText full_name;
    Button new_signup;
    String userid;
    String useremail;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username);
        user_email = findViewById(R.id.new_email);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        new_signup = findViewById(R.id.new_signup);
        full_name = findViewById(R.id.fullname);

        mAuth = FirebaseAuth.getInstance();


        firebaseUser = mAuth.getCurrentUser();
        userid = firebaseUser.getUid();
        useremail = firebaseUser.getEmail();

        new_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please wait a moment while it loads.");
                pd.show();

                if (username.getText().toString().isEmpty() || user_email.getText().toString().isEmpty() || new_password.getText().toString().isEmpty()  || confirm_password.getText().toString().isEmpty() || full_name.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();

                }
                else if (new_password.getText().toString().equals(confirm_password.getText().toString()))
                {
                    createNewUser();
                    //registertoDatabase();

                    pd.dismiss();
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText`(RegisterActivity.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createNewUser() {
        Task<AuthResult> authResultTask = mAuth.createUserWithEmailAndPassword(user_email.getText().toString(), new_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("RegisterActivity", "createUserWithEmail:success");
                            firebaseUser = mAuth.getCurrentUser();
                            db = FirebaseFirestore.getInstance();

                            Map<String, Object> map_user = new HashMap<>();
                            map_user.put("fullname", full_name.getText().toString());
                            map_user.put("id", userid);
                            map_user.put("email", user_email);
                            map_user.put("username", username.getText().toString());



                            // Add a new document with a generated ID
                            db.collection("users")
                                    .add(map_user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());



                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed. You may have an account already.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void registertoDatabase() {
        db = FirebaseFirestore.getInstance();

        Map<String, Object> map_user = new HashMap<>();
        map_user.put("fullname", full_name.getText().toString());
        map_user.put("id", userid);
        map_user.put("email", user_email);
        map_user.put("username", username.getText().toString());



        // Add a new document with a generated ID
        db.collection("users")
                .add(map_user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
