package ca.bcit.locafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import ca.bcit.locafe.ui.favourites.FavouriteItem;

public class ListArrayAdapter extends BaseAdapter {
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
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.favourites_row_layout, parent, false);
        name = convertView.findViewById(R.id.favourite_name);
        name.setText(arrayList.get(position).getName());
        return convertView;
    }
}
