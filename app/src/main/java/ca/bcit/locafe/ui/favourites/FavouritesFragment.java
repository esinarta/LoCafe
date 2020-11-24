package ca.bcit.locafe.ui.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ca.bcit.locafe.ListArrayAdapter;
import ca.bcit.locafe.LocationDetailsActivity;
import ca.bcit.locafe.R;
import ca.bcit.locafe.SearchResultsActivity;
import ca.bcit.locafe.data.model.Business;

public class FavouritesFragment extends Fragment {

    ListView listView;
    ArrayList<FavouriteItem> arrayList = new ArrayList<>();
    ListArrayAdapter adapter;
    FirebaseAuth firebaseAuth;

    private FavouritesViewModel favouritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel =
                ViewModelProviders.of(this).get(FavouritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourites, container, false);
        final TextView textView = root.findViewById(R.id.text_favourites);
        favouritesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String userId = currentUser.getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        DatabaseReference favouritesRef = userRef.child("favourites");

        final List<FavouriteItem> favouriteList = new ArrayList<>();
        favouritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                favouriteList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    FavouriteItem favItem = postSnapshot.getValue(FavouriteItem.class);
                    System.out.println(favItem.getName());
                    favouriteList.add(favItem);
                    arrayList.add(new FavouriteItem(favItem.getId(), favItem.getName(), favItem.getAddress(), favItem.getKey()));
                    listView = Objects.requireNonNull(getView()).findViewById(R.id.list_favourites);
                    adapter = new ListArrayAdapter(getActivity(), arrayList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView = getView().findViewById(R.id.list_favourites);
        adapter = new ListArrayAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button deleteBtn = (Button) getView().findViewById(R.id.btnDeleteFav);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        final String userId = currentUser.getUid();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        DatabaseReference favouritesRef = userRef.child("favourites");

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println();
            }
        });
    }

}