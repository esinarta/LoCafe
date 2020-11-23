package ca.bcit.locafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.bcit.locafe.data.model.Business;

public class LocationDetailsActivity extends AppCompatActivity {

    TextView locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        Intent intent = getIntent();
        final Business business = (Business) intent.getSerializableExtra("BUSINESS");
        locationName = findViewById(R.id.locationTitle);
        locationName.setText(business.getName());

//        Button searchBtn = (Button) findViewById(R.id.favouriteButton);
//
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}