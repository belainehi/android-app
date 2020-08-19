package com.Team.volunteer_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent a = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(a);
                        return true;
                    case R.id.action_map:
                        Intent b = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(b);
                        return true;
                    case R.id.action_profile:
                        Intent c = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(c);
                        return true;
                    default: return true;
                }
            }
        });
    }
}