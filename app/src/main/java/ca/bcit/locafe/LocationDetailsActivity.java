package ca.bcit.locafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.bcit.locafe.data.model.Booking;
import ca.bcit.locafe.data.model.Business;
import ca.bcit.locafe.data.model.Table;

import static android.text.TextUtils.isEmpty;

public class LocationDetailsActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    TextView locationName, locationAddress, locationDescription;
    EditText date, start, end, count;
    FirebaseAuth firebaseAuth;
    ArrayList<Table> tableArrayList, validBookingTableList, validQueueTableList;
    Business business;
    Button reserveBtn, queueBtn;
    String startString, endString;
    int numPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Intent intent = getIntent();
        business = (Business) intent.getSerializableExtra("BUSINESS");

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        renderBusiness();
        renderFavourites(currentUser.getUid());
        renderReservations();

        this.tableArrayList = new ArrayList<Table>();
        this.validBookingTableList = new ArrayList<Table>();
        this.validQueueTableList = new ArrayList<Table>();

        DatabaseReference tablesRef = FirebaseDatabase.getInstance().getReference("table").child(business.getId());
        ValueEventListener tablesListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableArrayList = new ArrayList<Table>();
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    tableArrayList.add(d.getValue(Table.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        tablesRef.addValueEventListener(tablesListener);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!isEmpty(start.getText().toString()) && !isEmpty(end.getText().toString()) && !isEmpty(date.getText().toString()) && !isEmpty(count.getText().toString())){
                    DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("booking");
                    ValueEventListener bookingsListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            numPeople = Integer.parseInt(count.getText().toString());

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String day = date.getText().toString();
                            String startTxt = start.getText().toString();
                            String endTxt = end.getText().toString();

                            startString = day+" "+startTxt;
                            endString = day+" "+endTxt;

                            Date resStart = null, resEnd = null;

                            try {
                                 resStart = format.parse(startString);
                                 resEnd = format.parse(endString);
                            }catch(ParseException e) {
                                e.printStackTrace();
                            }

                            validBookingTableList = new ArrayList<Table>(tableArrayList);
                            HashSet<String> table_ids = new HashSet<String>();
                            for(DataSnapshot d: dataSnapshot.getChildren()){
                                Booking booking = d.getValue(Booking.class);
                                if(booking.getBusinessId() == business.getId()){
                                    try {
                                        Date booking_start = booking.getStartDate();
                                        Date booking_end = booking.getEndDate();
                                        if((booking_start.before(resStart) && booking_end.after(resStart))|| (booking_start.before(resEnd) && booking_end.after(resEnd))){
                                            table_ids.add(booking.getTableId());
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            for(Table t: validBookingTableList){
                                if(t.getSeats()<numPeople)
                                    table_ids.add(t.getId());
                            }
                            removeTables(validBookingTableList, table_ids);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    };
                    bookingsRef.addValueEventListener(bookingsListener);
                }else{
                    validBookingTableList = new ArrayList<Table>();
                }
            }
        };

        date.addTextChangedListener(tw);
        start.addTextChangedListener(tw);
        end.addTextChangedListener(tw);
        count.addTextChangedListener(tw);

        reserveBtn = findViewById(R.id.reserveBtn);
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(count.getText())||isEmpty(date.getText())||isEmpty(start.getText())||isEmpty(end.getText())){
                    Toast.makeText(LocationDetailsActivity.this, "Please fill in all parameters.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(validBookingTableList.size() == 0){
                    Toast.makeText(LocationDetailsActivity.this, "No tables available.", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
                    DatabaseReference newBookingsRef = bookingsRef.push();

                    String bookingId = newBookingsRef.getKey();
                    newBookingsRef.setValue(new Booking(bookingId, currentUser.getUid(), business.getId(), validBookingTableList.get(0).getId() ,startString, endString, numPeople));

                    Toast.makeText(LocationDetailsActivity.this, "Booking successful.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button queueBtn = (Button) findViewById(R.id.queueBtn);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
        final DatabaseReference queuesRef = userRef.child("queues");

        queueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queuesRef.child("businessId").setValue(business.getId());
                queuesRef.child("businessName").setValue(business.getName());

                Toast.makeText(LocationDetailsActivity.this, "Queueing successful.", Toast.LENGTH_SHORT).show();
            }
        });

//        Button queueBtn = (Button) findViewById(R.id.queueBtn);
//        queueBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final int numPeople = Integer.parseInt(count.getText().toString());
//                final DatabaseReference queuesRef = FirebaseDatabase.getInstance()
//                        .getReference("users")
//                        .child(currentUser.getUid())
//                        .child("queues");
//
//                queuesRef.child("businessId").setValue(business.getId());
//                queuesRef.child("businessName").setValue(business.getName());
//                Toast.makeText(LocationDetailsActivity.this, "Queueing successful.", Toast.LENGTH_SHORT).show();
//
//                ValueEventListener queueListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
//                            validQueueTableList = new ArrayList<Table>();
//                            findQueueTable(numPeople);
//
//                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//                            Date startTime = new Date();
//                            Date endTime = new Date(System.currentTimeMillis() + (3600 * 1000));
//
//                            String startString = format.format(startTime);
//                            String endString = format.format(endTime);
//
//                            DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
//                            DatabaseReference newBookingsRef = bookingsRef.push();
//                            String bookingId = newBookingsRef.getKey();
//                            newBookingsRef.setValue(new Booking(bookingId, currentUser.getUid(), business.getId(), validBookingTableList.get(0).getTableId() ,startString, endString, numPeople));
//                            queuesRef.setValue(null);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                };
//                queuesRef.addValueEventListener(queueListener);
//            }
//        });
    }

    private void removeTables(ArrayList<Table> tableArrayList, Set<String> table_ids){
        List<Table> tables = new ArrayList<Table>();
        for(Table t : tableArrayList){
            if(table_ids.contains(t.getId())){
                tables.add(t);
            }
        }
        tableArrayList.removeAll(tables);
    }
    private void renderBusiness(){
        locationName = findViewById(R.id.locationTitle);
        locationName.setText(business.getName());

        locationAddress = findViewById(R.id.locationAddress);
        locationAddress.setText(business.getAddress());

        locationDescription = findViewById(R.id.locationDescription);
        locationDescription.setText(business.getDescription());
    }

    private void renderFavourites(final String userId){
        Button favBtn = (Button) findViewById(R.id.favouriteButton);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbUserFavourites = FirebaseDatabase.getInstance().getReference("users");
                String key = dbUserFavourites.child(userId).child("favourites").push().getKey();
                dbUserFavourites.child(userId).child("favourites").child(key).child("id").setValue(business.getId());
                dbUserFavourites.child(userId).child("favourites").child(key).child("name").setValue(business.getName());
                dbUserFavourites.child(userId).child("favourites").child(key).child("address").setValue(business.getAddress());
                dbUserFavourites.child(userId).child("favourites").child(key).child("key").setValue(key);

                Toast.makeText(LocationDetailsActivity.this, "Favourite added.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void renderReservations(){
        Button reserveBtn = (Button) findViewById(R.id.reserveBtn);
        date = findViewById(R.id.reservationDate);
        start = findViewById(R.id.reservationStart);
        end = findViewById(R.id.reservationEnd);
        count = findViewById(R.id.reservationCount);

        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(LocationDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        start.setInputType(InputType.TYPE_NULL);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(LocationDetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                start.setText(hourOfDay+":"+minute);
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        end.setInputType(InputType.TYPE_NULL);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minute = cldr.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(LocationDetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                end.setText(hourOfDay+":"+minute);
                            }
                        }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
    }
}