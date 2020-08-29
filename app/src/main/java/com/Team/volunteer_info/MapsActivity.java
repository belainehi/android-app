package com.Team.volunteer_info;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.Team.volunteer_info.fragments.FragmentFeed;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    Button mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mapBtn=findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                Intent i = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        
        LatLng bloomfield = new LatLng(40.8068, -74.1854);
        mMap.addMarker(new MarkerOptions().position(bloomfield).title("Bloomfield, NJ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bloomfield));

        LatLng nj = new LatLng(40.808580, -74.211950);
        mMap.addMarker(new MarkerOptions().position(nj).title("First Choice Women's Resource Centers").snippet("Location: 180 Bloomfield Ave, Montclair, NJ 07042"));

        LatLng fl = new LatLng(30.433670, -84.290310);
        mMap.addMarker(new MarkerOptions().position(fl).title("Leon County Volunteer Center").snippet("Location: 918 Railroad Ave, Tallahassee, FL 32310"));

        LatLng cl = new LatLng(38.439670, -122.743930);
        mMap.addMarker(new MarkerOptions().position(cl).title("Volunteer Center of Sonoma County").snippet("Location: 153 Stony Cir #100, Santa Rosa, CA 95401"));

        LatLng at = new LatLng(33.754850, -84.385310);
        mMap.addMarker(new MarkerOptions().position(at).title("Robert W. Woodruff Volunteer Center").snippet("Location: 100 Edgewood Ave NE, Atlanta, GA 30303"));

        LatLng mi = new LatLng(42.478310, -83.254590);
        mMap.addMarker(new MarkerOptions().position(mi).title("Volunteers of America").snippet("Location: 21415 Civic Center Dr #306, Southfield, MI 48076"));

        LatLng ks = new LatLng(38.937840, -95.257020);
        mMap.addMarker(new MarkerOptions().position(ks).title("Roger Hill Volunteer Center").snippet("Location: 2518 Ridge Ct #200, Lawrence, KS 66046"));

        LatLng tx = new LatLng(32.686910, -97.364730);
        mMap.addMarker(new MarkerOptions().position(tx).title("VolunteerNow").snippet("Location: 4137 Stadium Dr, Fort Worth, TX 76133"));

        LatLng uh = new LatLng(37.049580, -112.527893);
        mMap.addMarker(new MarkerOptions().position(uh).title("Volunteer Center-Kane Co").snippet("Location: 76 N Main St, Kanab, UT 84741"));

        LatLng az= new LatLng(33.292570, -112.007460);
        mMap.addMarker(new MarkerOptions().position(az).title("Phoenix Volunteers").snippet("Location: 17001 S 34th Way, Phoenix, AZ 85048"));

        LatLng mn = new LatLng(44.961510, -93.186000);
        mMap.addMarker(new MarkerOptions().position(mn).title("HandsOn Twin Cities").snippet("Location: 672 Transfer Rd, St Paul, MN 55114"));
    }
}