package es.alonsoftware.elecciones26j.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import es.alonsoftware.elecciones26j.R;
import es.alonsoftware.elecciones26j.adapter.DividerItemDecoration;
import es.alonsoftware.elecciones26j.adapter.PartidoAdapter;
import es.alonsoftware.elecciones26j.dao.Lugar;
import es.alonsoftware.elecciones26j.dao.Partido;
import es.alonsoftware.elecciones26j.dao.PartidoDAO;

public class CongresoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout progress;
    private List<Partido> partidos;

    public CongresoFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recicler, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        progress = (RelativeLayout) getView().findViewById(R.id.progress);
        cargarProgramas();

        /*

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                d = new CapituloDialog(getContext(), R.style.DialogStyle, capitulos.get(position));
                d.getWindow().setGravity(Gravity.BOTTOM);
                d.setCancelable(true);
                d.show();
            }
        });
        */

    }


    public void cargarProgramas(){

        new Thread(){
            public void run(){
                partidos = new PartidoDAO().getResultados(new Lugar(Lugar.CONGRESO, 2015));
                handler.sendEmptyMessage(0);

            }
        }.start();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            progress.setVisibility(View.GONE);

            if (partidos != null ){
                PartidoAdapter itemAdapter = new PartidoAdapter(partidos, new Lugar(Lugar.CONGRESO, 2015), false, null);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
                recyclerView.setAdapter(itemAdapter);
                RecyclerView.LayoutManager l = new LinearLayoutManager(getContext());
                //l.addView(new MyView(getContext()));
                recyclerView.setLayoutManager(l);
            }

        }

    };

}
