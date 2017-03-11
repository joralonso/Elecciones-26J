package es.alonsoftware.elecciones26j.dao;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import es.alonsoftware.elecciones26j.R;


/**
 * Created by Jorge on 20/11/15.
 */
public class PartidoAdapter  extends BaseAdapter {

    private List<Partido> datas;


    public PartidoAdapter(List<Partido> d){
        this.datas = d;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
            return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(parent.getContext(), R.layout.view_partido, null);
        ((TextView) v.findViewById(R.id.nombre)).setText(datas.get(position).getNombre());
        ((TextView) v.findViewById(R.id.votos)).setText("" + NumberFormat.getInstance().format(datas.get(position).getVotos_numero()) + " votos");
        ((TextView) v.findViewById(R.id.porcentaje)).setText("" + datas.get(position).getVotos_porciento() + "%");

        TextView electos = (TextView) v.findViewById(R.id.electos);
        electos.setText("" + datas.get(position).getElectos());
        electos.setBackgroundResource(R.drawable.rounded_background);

        GradientDrawable drawable = (GradientDrawable) electos.getBackground();
        drawable.setColor(datas.get(position).getColor());

        return v;
    }
}
