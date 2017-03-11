package es.alonsoftware.elecciones26j.dao;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jorge on 20/11/15.
 */
public class Partido {

    private int id_partido, electos, votos_numero;
    private double votos_porciento;
    private String nombre;
    private int color;

    public Partido(JSONObject json){
        try {
            this.id_partido = json.getInt("id_partido");
        } catch (JSONException e) {
            this.id_partido = 0;
            e.printStackTrace();
        }
        try {
            this.electos = json.getInt("electos");
        } catch (JSONException e) {
            this.electos = 0;
        }
        try {
            this.votos_numero = json.getInt("votos_numero");
        } catch (JSONException e) {
            this.votos_numero = 0;
            e.printStackTrace();
        }
        try {
            this.votos_porciento = json.getDouble("votos_porciento");
        } catch (JSONException e) {
            this.votos_porciento = 0;
            e.printStackTrace();
        }
        try {
            this.nombre = json.getString("nombre");
        } catch (JSONException e) {
            this.nombre = "";
            e.printStackTrace();
        }

        color = setColor();
    }

    public int getId_partido() {
        return id_partido;
    }

    public int getElectos() {
        return electos;
    }

    public int getVotos_numero() {
        return votos_numero;
    }

    public double getVotos_porciento() {
        return votos_porciento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getColor() {
        return color;
    }

    public int setColor(){

        if (nombre.toUpperCase().contains("PP")) return Color.parseColor("#0174DF");
        if (nombre.toUpperCase().contains("PSOE")) return Color.parseColor("#FE2E2E");
        if (nombre.toUpperCase().contains("PSE")) return Color.parseColor("#FE2E2E");
        if (nombre.toUpperCase().contains("UPYD")) return Color.parseColor("#FE2EF7");
        if (nombre.toUpperCase().contains("PODEMOS")) return Color.parseColor("#5F04B4");
        if (nombre.toUpperCase().contains("UP")) return Color.parseColor("#5F04B4");
        if (nombre.toUpperCase().contains("PODEM")) return Color.parseColor("#5F04B4");
        if (nombre.toUpperCase().contains("COMÚ")) return Color.parseColor("#5F04B4");
        if (nombre.toUpperCase().contains("CIU")) return Color.parseColor("#DF7401");
        if (nombre.toUpperCase().contains("DL")) return Color.parseColor("#DF7401");
        if (nombre.toUpperCase().contains("PNV")) return Color.parseColor("#38610B");
        if (nombre.toUpperCase().contains("ERC")) return Color.parseColor("#DBA901");
        if (nombre.toUpperCase().contains("BILDU")) return Color.parseColor("#58FA82");
        if (nombre.toUpperCase().contains("CCA-PNC")) return Color.parseColor("#2E9AFE");
        if (nombre.toUpperCase().contains("PACMA")) return Color.parseColor("#A8C200");
        if (nombre.toUpperCase().contains("IU")) return Color.parseColor("#088A08");
        if (nombre.toUpperCase().contains("UNIDAD")) return Color.parseColor("#088A08");
        if (nombre.toUpperCase().contains("UP")) return Color.parseColor("#088A08");
        if (nombre.toUpperCase().contains("CS")) return Color.parseColor("#FE9A2E");
        if (nombre.toUpperCase().contains("C'S")) return Color.parseColor("#FE9A2E");
        if (nombre.toUpperCase().contains("CIUDADANOS")) return Color.parseColor("#FE9A2E");
        if (nombre.toUpperCase().contains("VOX")) return Color.parseColor("#40FF00");
        if (nombre.toUpperCase().contains("UPYD")) return Color.parseColor("#FE2EF7");
        return Color.parseColor("#585858");
    }

    public static String setURL(String year, String camara, String lugar){
        return "http://alonsoftware.es/20d/resultados.php?year="+year+"&camara="+camara+"&lugar="+lugar;
    }

    public String getShare(){
        return this.nombre+" escaños: "+this.getElectos()+". votos: "+votos_porciento+"%.\n";
    }

    public String getShareMunicipio(){
        return this.nombre+" votos: "+votos_numero+" ("+votos_porciento+"%).\n";
    }
}
