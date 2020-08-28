package com.Team.volunteer_info;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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
                else
                {
                    createNewUser();
                }
            }
        });

    }

    private void createNewUser() {

    }
}
