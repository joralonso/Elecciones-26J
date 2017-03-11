package es.alonsoftware.elecciones26j.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.alonsoftware.elecciones26j.MunicipioActivity;
import es.alonsoftware.elecciones26j.dao.Municipio;

/**
 * Created by Jorge on 19/5/16.
 */
public class MunicipiosAdapter extends RecyclerView.Adapter<MunicipiosAdapter.ViewHolder> {

    private List<Municipio> municipios;

    public MunicipiosAdapter(List<Municipio> municipios) {
        this.municipios = municipios;
    }


    @Override
    public int getItemCount(){
        return municipios.size();
    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMunicipios(municipios.get(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Filterable {


        private List<Municipio> datas;
        private List<Municipio> temporal;

        private Municipio municipio;

        private TextView text1;

        private Context c;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            c = itemView.getContext();
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setMunicipios(Municipio municipio) {
            this.municipio = municipio;
            text1.setText(municipio.getNombre());
        }



        @Override
        public void onClick(View v) {
            Log.d("ON CCLIE", "ON CLICKE");

            Intent i = new Intent(c, MunicipioActivity.class);
            Resources res = c.getResources();
            i.putExtra("nombre", municipio.getNombre());
            i.putExtra("id", municipio.getPaisid());
            c.startActivity(i);

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


}