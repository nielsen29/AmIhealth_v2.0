package com.amihealth.amihealth.Parsers;

import com.amihealth.amihealth.Models.Clasificaciones;
import com.amihealth.amihealth.Contract.ClasificacionesSA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 11/13/17.
 */

public class ParserClasificacion {
    private Clasificaciones clasificaciones;
    private ArrayList<Clasificaciones> List;
    private String entrada;

    public ParserClasificacion(String entrada) {
        this.entrada = entrada;
        this.List = new ArrayList<>();
    }

    public ArrayList<Clasificaciones> getParser(){

        try{
            JSONObject JsonObj = new JSONObject(entrada);
            JSONArray data= JsonObj.getJSONArray(ClasificacionesSA.access);
            for (int i = 0; i < data.length(); i++){
                JSONObject dato = data.getJSONObject(i);
                Clasificaciones clasificaciones = new Clasificaciones(dato.getString(ClasificacionesSA.DESCRIP));
                List.add(clasificaciones);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return List;
    }
}
