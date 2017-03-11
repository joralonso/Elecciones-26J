package es.alonsoftware.elecciones26j.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;

import es.alonsoftware.elecciones26j.R;
import es.alonsoftware.elecciones26j.adapter.DividerItemDecoration;
import es.alonsoftware.elecciones26j.adapter.Partido2Adapter;
import es.alonsoftware.elecciones26j.dao.Lugar;
import es.alonsoftware.elecciones26j.dao.Partido;
import es.alonsoftware.elecciones26j.dao.PartidoDAO;
import es.alonsoftware.elecciones26j.dao.Porcentaje;

public class Default2Fragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout progress;
    private RelativeLayout datos;
    private List<Partido> partidos;
    private List<Partido> partidos2;
    private Lugar lugar;
    private Lugar lugar2;
    private boolean porVoto = false;
    private Porcentaje porcentaje;
    private SwipeRefreshLayout refreshLayout;


    public static Default2Fragment newInstance(Lugar lugar, Lugar lugar2, boolean porVoto) {
        Default2Fragment fragment = new Default2Fragment();
        Bundle args = new Bundle();
        args = lugar.setArguments(args);
        args = lugar2.setArguments2(args);
        args.putBoolean("porVoto", porVoto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lugar = new Lugar(getArguments());
        lugar2 = new Lugar(getArguments(), 2);
        porVoto = getArguments().getBoolean("porVoto");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recicler, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        porcentaje = new Porcentaje(0);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        progress = (RelativeLayout) getView().findViewById(R.id.progress);
        datos = (RelativeLayout) getView().findViewById(R.id.datos);
        refreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefresh);

        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        cargarProgramas();
                    }
                }
        );

        final AdView adView = new AdView(getContext());
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getResources().getString(R.string.ads));
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        adView.loadAd(adRequestBuilder.build());
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                try {
                    ((LinearLayout) (getView().findViewById(R.id.add))).addView(adView);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        cargarProgramas();

    }


    public void cargarProgramas(){
        progress.setVisibility(View.VISIBLE);
        refreshLayout.setRefreshing(true);
        new Thread(){
            public void run(){
                partidos = new PartidoDAO().getResultados(lugar, porcentaje);
                partidos2 = new PartidoDAO().getResultados(lugar2, porcentaje);
                Log.d("PORCENTAJE", ""+porcentaje.getPorcentaje());
                handler.sendEmptyMessage(0);

            }
        }.start();
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            progress.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);

            if (partidos != null && partidos2 != null && partidos.size()  > 0 && partidos2.size() > 0 ){
                Partido2Adapter itemAdapter = new Partido2Adapter(partidos, partidos2, lugar, lugar2, porVoto);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
                recyclerView.setAdapter(itemAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }else{
                datos.setVisibility(View.VISIBLE);
            }

        }

    };

}
