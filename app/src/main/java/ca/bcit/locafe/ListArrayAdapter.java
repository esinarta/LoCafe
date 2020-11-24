package ca.bcit.locafe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.bcit.locafe.data.model.Business;
import ca.bcit.locafe.ui.favourites.FavouriteItem;

public class ListArrayAdapter extends BaseAdapter {
    FirebaseAuth firebaseAuth;
    private Context context;
    private ArrayList<FavouriteItem> arrayList;
    private TextView name;
    public ListArrayAdapter(Context context, ArrayList<FavouriteItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public FavouriteItem getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.favourites_row_layout, parent, false);
        name = convertView.findViewById(R.id.favourite_name);
        name.setText(arrayList.get(position).getName());

        Button delete = convertView.findViewById(R.id.btnDeleteFav);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                final String userId = currentUser.getUid();

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                final DatabaseReference favouritesRef = userRef.child("favourites");
                favouritesRef.child(arrayList.get(position).getKey()).removeValue();

                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        LinearLayout ll = convertView.findViewById(R.id.favourite_item);
        final View finalConvertView = convertView;
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String businessId = arrayList.get(position).getId();

                DatabaseReference businessRef = FirebaseDatabase.getInstance().getReference("businesses");
                Query query = businessRef.orderByChild("id").equalTo(businessId);

               query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                            Business business = postSnapshot.getValue(Business.class);
                            Intent intent = new Intent(finalConvertView.getContext(), LocationDetailsActivity.class);
                            intent.putExtra("BUSINESS", business);

                            finalConvertView.getContext().startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return convertView;
    }
}
