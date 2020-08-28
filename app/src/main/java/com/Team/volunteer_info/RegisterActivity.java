package com.Team.volunteer_info;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText user_email;
    EditText new_password;
    EditText confirm_password;
    Button new_signup;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.username);
        user_email = findViewById(R.id.new_email);
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        new_signup = findViewById(R.id.new_signup);

        mAuth = FirebaseAuth.getInstance();

        new_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().isEmpty() || user_email.getText().toString().isEmpty() || new_password.getText().toString().isEmpty()  || confirm_password.getText().toString().isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "All fields required!", Toast.LENGTH_SHORT).show();

                }
                else if (new_password.getText().toString().equals(confirm_password.getText().toString()))
                {
                    createNewUser();
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Passwords must match!", Toast.LENGTH_SHORT).show();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed. You may have an account already.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
