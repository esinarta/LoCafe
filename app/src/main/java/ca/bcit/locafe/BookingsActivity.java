package ca.bcit.locafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.bcit.locafe.data.model.Booking;

public class BookingsActivity extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        final FirebaseUser currentUser =  FirebaseAuth.getInstance().getCurrentUser();

        lv = findViewById(R.id.bookings_lv);

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");

        ValueEventListener bookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();
                for(DataSnapshot bookingDS : dataSnapshot.getChildren()){
                    Booking booking = bookingDS.getValue(Booking.class);
                    if(booking.getUserId().equals(currentUser.getUid())){
                        bookingArrayList.add(booking);
                    }
                }
                BookingsAdapter adapter = new BookingsAdapter(BookingsActivity.this, bookingArrayList);
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        bookingsRef.addValueEventListener(bookingsListener);
    }
}