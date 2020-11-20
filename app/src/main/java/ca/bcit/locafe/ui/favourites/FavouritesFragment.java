package ca.bcit.locafe.ui.favourites;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Objects;

import ca.bcit.locafe.FavouritesListArrayAdapter;
import ca.bcit.locafe.LocationDetailsActivity;
import ca.bcit.locafe.R;

public class FavouritesFragment extends Fragment {

    ListView listView;
    ArrayList<FavouriteItem> arrayList = new ArrayList<>();
    FavouritesListArrayAdapter adapter;

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
        listView = Objects.requireNonNull(getView()).findViewById(R.id.list_favourites);
        arrayList.add(new FavouriteItem("Favourites 1"));
        arrayList.add(new FavouriteItem("Favourites 1"));
        arrayList.add(new FavouriteItem("Favourites 1"));
        arrayList.add(new FavouriteItem("Favourites 1"));
        arrayList.add(new FavouriteItem("Favourites 1"));
        arrayList.add(new FavouriteItem("Favourites 1"));
        arrayList.add(new FavouriteItem("Favourites 1"));
        adapter = new FavouritesListArrayAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        Button searchBtn = (Button) getView().findViewById(R.id.btnDeleteFav);
//
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }


}