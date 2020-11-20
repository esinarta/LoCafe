package ca.bcit.locafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.bcit.locafe.data.model.Business;

public class SearchResultsAdapter extends ArrayAdapter<Business> {
    Context _context;
    ArrayList<Business> businesses;
    public SearchResultsAdapter(Context context, ArrayList<Business> businesses) {
        super(context, 0, businesses);
        _context = context;
        this.businesses = businesses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Business business = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_row_layout, parent, false);
        }

        TextView businessName = convertView.findViewById(R.id.business_name);
        TextView businessAddress = convertView.findViewById(R.id.business_address);

        businessName.setText(business.getName());
        businessAddress.setText(business.getAddress());

        return convertView;
    }
}
