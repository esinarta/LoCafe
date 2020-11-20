import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import ca.bcit.locafe.R;
import ca.bcit.locafe.ui.search.SearchResultItem;

public class SearchResultListArrayAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SearchResultItem> arrayList;
    private TextView name;
    public SearchResultListArrayAdapter(Context context, ArrayList<SearchResultItem> arrayList) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.search_results_row_layout, parent, false);
        name = convertView.findViewById(R.id.business_name);
        name.setText(arrayList.get(position).getName());
        return convertView;
    }
}
