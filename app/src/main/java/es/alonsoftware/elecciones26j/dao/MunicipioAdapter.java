package es.alonsoftware.elecciones26j.dao;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 20/11/15.
 */
public class MunicipioAdapter extends BaseAdapter  implements Filterable {

    private List<Municipio> datas;
    private List<Municipio> temporal;


    public MunicipioAdapter(List<Municipio> d){
        this.datas = d;
        temporal=datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return temporal.size();
    }

    @Override
    public Object getItem(int position) {
            return temporal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(parent.getContext(), android.R.layout.simple_list_item_1, null);
        ((TextView) v.findViewById(android.R.id.text1)).setText(temporal.get(position).getNombre());
        return v;
    }

    @Override
    public Filter getFilter() {


        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                temporal = (ArrayList<Municipio>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Municipio> FilteredList = new ArrayList<Municipio>();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = datas;
                    results.count = datas.size();
                } else {
                    for (int i = 0; i < datas.size(); i++) {
                        Municipio data = datas.get(i);
                        if (data.getNombre().toLowerCase().contains(constraint.toString())) {
                            FilteredList.add(data);
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }
                return results;
            }
        };

        return filter;
    }
}
