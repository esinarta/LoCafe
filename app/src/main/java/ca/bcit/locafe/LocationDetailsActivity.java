package ca.bcit.locafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.bcit.locafe.data.model.Business;

public class LocationDetailsActivity extends AppCompatActivity {

    TextView locationName;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String userId = currentUser.getUid();

        Intent intent = getIntent();
        final Business business = (Business) intent.getSerializableExtra("BUSINESS");
        final String businessId = business.getId();
        locationName = findViewById(R.id.locationTitle);
        locationName.setText(business.getName());

        Button searchBtn = (Button) findViewById(R.id.favouriteButton);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbUserFavourites = FirebaseDatabase.getInstance().getReference("users");
                dbUserFavourites.child(userId).child("favourites").push().setValue(businessId);

                System.out.println(businessId);
                System.out.println(userId);
            }
        });
    }
}