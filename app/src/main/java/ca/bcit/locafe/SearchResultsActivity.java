package ca.bcit.locafe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import ca.bcit.locafe.data.model.Business;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView lvResultsList;
    private SearchResultsAdapter adapter;
    private ArrayList<Business> businessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        lvResultsList = findViewById(R.id.results_list);
        businessList = new ArrayList<>();
        adapter = new SearchResultsAdapter(this, businessList);
        lvResultsList.setLayoutManager(new LinearLayoutManager(this));
        lvResultsList.setAdapter(adapter);

        final String searchText = getIntent().getStringExtra("SEARCH_TEXT");

        DatabaseReference dbBusiness = FirebaseDatabase.getInstance().getReference("businesses");
        Query query = dbBusiness.orderByChild("name").equalTo(searchText);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            businessList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Business business = snapshot.getValue(Business.class);
                    ArrayList<Business> temp = new ArrayList<>(businessList);
                    temp.add(business);
                    businessList.clear();
                    businessList.addAll(temp);
                    adapter = new SearchResultsAdapter(SearchResultsActivity.this, businessList);
                    lvResultsList.setAdapter(adapter);
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



//
//    private class GetBusiness extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            SearchResultsAdapter adapter = new SearchResultsAdapter(SearchResultsActivity.this, businessList);
//            lvResultsList.setAdapter(adapter);
//        }
//    }

}
