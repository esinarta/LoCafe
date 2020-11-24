package ca.bcit.locafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

        return convertView;
    }
}
