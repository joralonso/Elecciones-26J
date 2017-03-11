package es.alonsoftware.elecciones26j.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.alonsoftware.elecciones26j.ProvinciaActivity;
import es.alonsoftware.elecciones26j.dao.Provincia;

/**
 * Created by Jorge on 19/5/16.
 */
public class ProvinciasAdapter extends RecyclerView.Adapter<ProvinciasAdapter.ViewHolder> {

    private List<Provincia> provincias;

    public ProvinciasAdapter(List<Provincia> Provincias) {
        this.provincias = Provincias;
    }


    @Override
    public int getItemCount(){
        return provincias.size();
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
        holder.setProvincias(provincias.get(position));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private List<Provincia> datas;
        private List<Provincia> temporal;

        private Provincia provincia;

        private TextView text1;

        private Context c;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            c = itemView.getContext();
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setProvincias(Provincia Provincia) {
            this.provincia = Provincia;
            text1.setText(Provincia.getNombre());
        }



        @Override
        public void onClick(View v) {
            Log.d("ON CCLIE", "ON CLICKE");

            Intent i = new Intent(c, ProvinciaActivity.class);
            Resources res = c.getResources();
            i.putExtra("nombre", provincia.getNombre());
            i.putExtra("id", provincia.getPaisid());
            c.startActivity(i);

            /*
            Intent i = new Intent(c, ProvinciaActivity.class);
            Resources res = c.getResources();
            i.putExtra("nombre", res.getStringArray(R.array.provincias)[position]);
            i.putExtra("id", res.getStringArray(R.array.provincias_id)[position]);
            c.startActivity(i);
            */

        }

    }


}