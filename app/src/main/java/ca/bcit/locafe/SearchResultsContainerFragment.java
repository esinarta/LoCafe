package ca.bcit.locafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import ca.bcit.locafe.ui.search.SearchResultItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsContainerFragment extends Fragment {

    private static final String ARG_PARAM1 = "Search";
    private String mParam1;

    private ListView listView;
    private ArrayList<SearchResultItem> arrayList = new ArrayList<>();
    private SearchResultListArrayAdapter adapter;

    public SearchResultsContainerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @return A new instance of fragment SearchResultsContainerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultsContainerFragment newInstance(String param1) {
        SearchResultsContainerFragment fragment = new SearchResultsContainerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_results_container, container, false);
        listView = Objects.requireNonNull(getView()).findViewById(R.id.search_result_list);
        arrayList.add(new SearchResultItem("Business 1"));
        arrayList.add(new SearchResultItem("Business 1"));
        arrayList.add(new SearchResultItem("Business 1"));
        arrayList.add(new SearchResultItem("Business 1"));
        arrayList.add(new SearchResultItem("Business 1"));
        arrayList.add(new SearchResultItem("Business 1"));
        arrayList.add(new SearchResultItem("Business 1"));
        adapter = new SearchResultListArrayAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return v;
    }
}