package ca.bcit.locafe.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
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
import ca.bcit.locafe.SearchResultListArrayAdapter;
import ca.bcit.locafe.SearchResultsActivity;
import ca.bcit.locafe.SearchResultsContainerFragment;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.text_search);
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button searchBtn = (Button) getView().findViewById(R.id.btnSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                TextView textView = getView().findViewById(R.id.text_search);
                String searchText = textView.getText().toString();
                intent.putExtra("SEARCH_TEXT", searchText);
                startActivity(intent);
            }
        });
    }

}