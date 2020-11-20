package ca.bcit.locafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.bcit.locafe.data.model.Business;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder>  {
    Context context;
    ArrayList<Business> businesses;
    public SearchResultsAdapter(Context context, ArrayList<Business> businesses) {
        this.context = context;
        this.businesses = businesses;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_row_layout, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
       Business business = businesses.get(position);
        holder.textViewName.setText(business.getName());
        holder.textViewAddress.setText(business.getAddress());
    }

    public int getItemCount() {
        return businesses.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewAddress;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.business_name);
            textViewAddress = itemView.findViewById(R.id.business_address);
        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//       Business business = getItem(position);
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_row_layout, parent, false);
//        }
//
//        TextView businessName = convertView.findViewById(R.id.business_name);
//        TextView businessAddress = convertView.findViewById(R.id.business_address);
//
//        businessName.setText(business.getName());
//        businessAddress.setText(business.getAddress());
//
//        return convertView;
//    }
}
