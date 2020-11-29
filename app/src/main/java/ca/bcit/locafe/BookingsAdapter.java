package ca.bcit.locafe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ca.bcit.locafe.data.model.Booking;
import ca.bcit.locafe.data.model.Business;

public class BookingsAdapter extends ArrayAdapter<Booking>{
    private Context _context;

    public BookingsAdapter(Context context, ArrayList<Booking> bookingList) {
        super(context, 0, bookingList);
        _context = context;
    }

    public View getView(final int bookingIndex, View convertView, ViewGroup parent){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bookings_row_layout, parent, false);
        final Booking booking = getItem(bookingIndex);

        final TextView textViewBusiness_id = view.findViewById(R.id.business_id);
        TextView textViewDate = view.findViewById(R.id.date);
        TextView textViewTable = view.findViewById(R.id.table);

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("businesses").child(booking.getBusinessId());

        ValueEventListener bookingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Business business = dataSnapshot.getValue(Business.class);
                textViewBusiness_id.setText(""+ business.getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        bookingsRef.addValueEventListener(bookingsListener);

        textViewDate.setText(""+booking.getStartString()+" - "+booking.getEndString());
        textViewTable.setText("Table ID: "+booking.getTableId());

        return view;
    }
}
