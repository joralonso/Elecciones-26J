package es.alonsoftware.elecciones26j.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import es.alonsoftware.elecciones26j.R;
import es.alonsoftware.elecciones26j.adapter.DividerItemDecoration;

public class AppsFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout progress;
    private RelativeLayout datos;
    private SwipeRefreshLayout refreshLayout;

    public static AppsFragment newInstance() {
        AppsFragment fragment = new AppsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AppsFragment() {
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
        datos = (RelativeLayout) getView().findViewById(R.id.datos);
        refreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefresh);

        datos.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);

        List<App> apps = new ArrayList<>();
        apps.add(new App("Memes Políticos", "es.alonsoftware.memespoliticos"));
        apps.add(new App("El Pactometro", "es.alonsoftware.pactometro"));
        apps.add(new App("Menéame App", "es.alonsoftware.mename"));
        apps.add(new App("ElDiario.es", "es.alonsoftware.eldiarioes"));
        apps.add(new App("Guia TV", "es.alonsoftware.tvguia"));
        apps.add(new App("¿Quién Soy?", "es.alonsoftware.quiensoy"));
        apps.add(new App("Cocina Fácil", "es.alonsoftware.cocinafcil"));

        AppAdapter itemAdapter = new AppAdapter(apps);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


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


    }



    private class App {
        private String nombre, pack;

        public App(String nombre, String pack) {
            this.nombre = nombre;
            this.pack = pack;
        }

        public String getNombre() {
            return nombre;
        }

        public String getPack() {
            return pack;
        }
    }

    public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

        private List<App> apps;

        public AppAdapter(List<App> apps) {
            this.apps = apps;
        }


        @Override
        public int getItemCount(){
            return apps.size();
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
            holder.setApps(apps.get(position));
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private App apps;
            private TextView text1;
            private Context c;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                c = itemView.getContext();
                text1 = (TextView) itemView.findViewById(android.R.id.text1);
            }
            public void setApps(App municipio) {
                this.apps = municipio;
                text1.setText(municipio.getNombre());
            }
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+ apps.getPack())));
            }
        }


    }

}
