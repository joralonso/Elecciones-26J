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
import es.alonsoftware.elecciones26j.adapter.ProvinciasAdapter;
import es.alonsoftware.elecciones26j.dao.Provincia;

public class ProvinciasFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private List<Provincia> provincias;
    private ProvinciasAdapter adapter;

    public static ProvinciasFragment newInstance() {
        ProvinciasFragment fragment = new ProvinciasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProvinciasFragment() {
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

        provincias = new ArrayList<Provincia>();
        String[] provinciasArray = getResources().getStringArray(R.array.provincias);
        String[] provinciasid = getResources().getStringArray(R.array.provincias_id);

        for (int i = 0; i < provinciasArray.length; i++){
            if (provinciasid.length > i)
                provincias.add(new Provincia(provinciasArray[i], provinciasid[i]));
        }

        adapter = new ProvinciasAdapter(provincias);
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

                List<Provincia> temp = Provincia.filter(provincias, s.toString());

                adapter = new ProvinciasAdapter(temp);
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
