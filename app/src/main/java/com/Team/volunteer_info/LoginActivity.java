package com.Team.volunteer_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    EditText email;
    EditText password;
    Button login;
    TextView signup;

    FirebaseAuth auth;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        Intent i = new Intent(LoginActivity.this , MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        auth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: Registration required.");
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked();
            }
        });




    }

    private void loginClicked() {
        Log.d(TAG, "loginClicked: Checking login credentials");
        if (email.getText().toString() == null || password.getText().toString() == null){
            Toast.makeText(this, "Both email and password are required!", Toast.LENGTH_SHORT).show();}
        else {
            auth.signInWithEmailAndPassword(String.valueOf(email), String.valueOf(password))
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: Login successful, going to MainActivity");
                                FirebaseUser user = auth.getCurrentUser();
                                Intent i = new Intent(LoginActivity.this , MainActivity.class);
                                startActivity(i);
//
                            }
                            else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}