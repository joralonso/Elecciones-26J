package es.alonsoftware.elecciones26j.dao;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jorge on 23/5/16.
 */
public class Lugar {

    public static final String SENADO = "senado";
    public static final String CONGRESO = "congreso";


    private String lugar, camara, nombre = "";
    private int year;

    public Lugar(String nombre, String lugar, String camara, int year) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.camara = camara;
        this.year = year;
    }

    public Lugar(String camara, int year) {
        this.camara = camara;
        this.year = year;
        this.lugar = "";
    }

    public Lugar (Bundle b){
        try{
            this.camara = b.getString("camara");
        } catch (Exception e){
            camara = CONGRESO;
        }
        this.year = b.getInt("year");
        this.lugar = b.getString("lugar");
    }

    public Lugar (Bundle b, int i){
        this.camara = b.getString("camara2");
        this.year = b.getInt("year2");
        this.lugar = b.getString("lugar2");
    }

    public Bundle setArguments(Bundle b){
        b.putString("camara", camara);
        b.putInt("year", year);
        b.putString("lugar", lugar);
        return b;
    }

    public Bundle setArguments2(Bundle b){
        b.putString("camara2", camara);
        b.putInt("year2", year);
        b.putString("lugar2", lugar);
        return b;
    }

    public String getUrl(){
        Log.d("URL", "http://alonsoftware.es/20d/resultados.php?year="+year+"&camara="+camara+"&lugar="+lugar);
        return "http://alonsoftware.es/20d/resultados.php?year="+year+"&camara="+camara+"&lugar="+lugar;
    }

    public String getTitulo(){

        if (nombre.equalsIgnoreCase(""))
            return camara.toUpperCase()+ " "+year;
        else
            return nombre.toUpperCase()+ " "+camara.toUpperCase()+ " "+year;
    }

    public String getTitulo(Lugar lugar){

        if (nombre.equalsIgnoreCase(""))
            return camara.toUpperCase()+ " "+year+" / "+lugar.year;
        else
            return nombre.toUpperCase()+ " "+camara.toUpperCase()+ " "+year+" / "+lugar.year;
    }


    public String getLugar() {
        return lugar;
    }

    public String getCamara() {
        return camara;
    }

    public String getNombre() {
        return nombre;
    }

    public int getYear() {
        return year;
    }
}
