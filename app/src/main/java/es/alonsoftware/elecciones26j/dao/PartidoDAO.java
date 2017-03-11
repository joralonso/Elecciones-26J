package es.alonsoftware.elecciones26j.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 23/5/16.
 */
public class PartidoDAO {

    public PartidoDAO() {
    }

    public List<Partido> getResultados(Lugar l, Porcentaje porcentaje){

        List<Partido> partidos = new ArrayList<Partido>();

        JSONArray jar = null;
        JSONObject json = null;
        try {
            json = new JSONObject(GetFromHtml.getStringFromUrl(l.getUrl()));
            jar = json.getJSONObject("resultados").getJSONArray("partido");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            porcentaje.setPorcentaje( json.getInt("porciento_escrutado"));

            Log.d("PORCENTAJE IN", ""+porcentaje.getPorcentaje());
        } catch (Exception e) {
            porcentaje.setPorcentaje(0);
            e.printStackTrace();
        }

        partidos = new ArrayList<Partido>();

        if (jar != null){
            for (int i = 0; i < jar.length(); i++){
                try {
                    partidos.add(new Partido(jar.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return partidos;

    }

    public List<Partido> getResultados(Lugar l){

        List<Partido> partidos = new ArrayList<Partido>();

        JSONArray jar = null;
        JSONObject json = null;
        try {
            json = new JSONObject(GetFromHtml.getStringFromUrl(l.getUrl()));
            jar = json.getJSONObject("resultados").getJSONArray("partido");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        partidos = new ArrayList<Partido>();

        if (jar != null){
            for (int i = 0; i < jar.length(); i++){
                try {
                    partidos.add(new Partido(jar.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return partidos;

    }
}
