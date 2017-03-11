package es.alonsoftware.elecciones26j.dao;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 27/11/15.
 */
public class Municipio {

    private String nombre, paisid;

    public Municipio(JSONObject json) {
        try {
            this.nombre = json.getString("nombre");
        } catch (JSONException e) {
            this.nombre = "";
            e.printStackTrace();
        }
        try {
            this.paisid = json.getString("paisid")+".xml2";
        } catch (JSONException e) {
            this.paisid = "";
            e.printStackTrace();
        }
    }

    public Municipio(String nombre, String paisid) {
        this.nombre = nombre;
        this.paisid = paisid;
    }

    public Municipio(Bundle b){
        this.nombre = b.getString("nombre");
        this.paisid = b.getString("paisid");
    }

    public void setIntent(Intent i){
        i.putExtra("nombre", nombre);
        i.putExtra("paisid", paisid);
    }

    public String getNombre() {
        return nombre;
    }

    public String getPaisid() {
        return paisid;
    }

    public static List<Municipio> filter(List<Municipio> models, String query) {
        query = query.toLowerCase();

        final List<Municipio> filteredModelList = new ArrayList<>();
        for (Municipio model : models) {
            final String text = model.getNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
