package es.alonsoftware.elecciones26j.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import es.alonsoftware.elecciones26j.R;
import es.alonsoftware.elecciones26j.adapter.DividerItemDecoration;
import es.alonsoftware.elecciones26j.adapter.MunicipiosAdapter;
import es.alonsoftware.elecciones26j.dao.Municipio;

public class MunicipiosFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private List<Municipio> municipios;
    private MunicipiosAdapter adapter;

    public static MunicipiosFragment newInstance() {
        MunicipiosFragment fragment = new MunicipiosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MunicipiosFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recicler_edittext, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        EditText editText = (EditText) getView().findViewById(R.id.editText);

        municipios = new ArrayList<Municipio>();
        String[] municipiosArray = getResources().getStringArray(R.array.municipios);
        String[] municipiosid = getResources().getStringArray(R.array.municipiosid);

        for (int i = 0; i < municipiosArray.length; i++){
            if (municipiosid.length > i)
                municipios.add(new Municipio(municipiosArray[i], municipiosid[i]));
        }

        adapter = new MunicipiosAdapter(municipios);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<Municipio> temp = Municipio.filter(municipios, s.toString());

                adapter = new MunicipiosAdapter(temp);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }
}
