package ca.bcit.locafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import ca.bcit.locafe.data.model.Booking;
import ca.bcit.locafe.data.model.Business;

public class LocationDetailsActivity extends AppCompatActivity {

    TextView locationName, locationAddress, locationDescription;
    DatePicker date;
    TimePicker start, end;
    EditText count;
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
        final String businessName = business.getName();
        final String businessAddress = business.getAddress();
        final String businessDescription = business.getDescription();

        locationName = findViewById(R.id.locationTitle);
        locationName.setText(businessName);

        locationAddress = findViewById(R.id.locationAddress);
        locationAddress.setText(businessAddress);

        locationDescription = findViewById(R.id.locationDescription);
        locationDescription.setText(businessDescription);

        Button favBtn = (Button) findViewById(R.id.favouriteButton);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbUserFavourites = FirebaseDatabase.getInstance().getReference("users");
                String key = dbUserFavourites.child(userId).child("favourites").push().getKey();
                dbUserFavourites.child(userId).child("favourites").child(key).child("id").setValue(businessId);
                dbUserFavourites.child(userId).child("favourites").child(key).child("name").setValue(businessName);
                dbUserFavourites.child(userId).child("favourites").child(key).child("address").setValue(businessAddress);
                dbUserFavourites.child(userId).child("favourites").child(key).child("key").setValue(key);

                Toast.makeText(LocationDetailsActivity.this, "Favourite added.", Toast.LENGTH_SHORT).show();
            }
        });

        Button reserveBtn = (Button) findViewById(R.id.reserveBtn);
        date = findViewById(R.id.reservationDate);
        start = findViewById(R.id.reservationStart);
        end = findViewById(R.id.reservationEnd);
        count = findViewById(R.id.reservationCount);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        final DatabaseReference bookingsRef = userRef.child("bookings");

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calStart = new GregorianCalendar(TimeZone.getTimeZone("PST"));
                calStart.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), start.getHour(), start.getMinute());

                final Calendar calEnd = new GregorianCalendar(TimeZone.getTimeZone("PST"));
                calEnd.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), end.getHour(), end.getMinute());

                final int numPeople = Integer.parseInt(count.getText().toString());

                bookingsRef.child("businessId").setValue(businessId);
                bookingsRef.child("startTime").setValue(calStart);
                bookingsRef.child("endTime").setValue(calEnd);
                bookingsRef.child("numPeople").setValue(numPeople);

                Toast.makeText(LocationDetailsActivity.this, "Booking successful.", Toast.LENGTH_SHORT).show();

            }
        });

        Button queueBtn = (Button) findViewById(R.id.queueBtn);
        final DatabaseReference queuesRef = userRef.child("queues");

        queueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queuesRef.child("businessId").setValue(businessId);
                queuesRef.child("businessName").setValue(businessName);

                Toast.makeText(LocationDetailsActivity.this, "Queueing successful.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}