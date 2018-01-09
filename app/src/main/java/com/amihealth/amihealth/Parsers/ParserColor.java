package com.amihealth.amihealth.Parsers;

import com.amihealth.amihealth.Contract.ClasificacionesSA;
import com.amihealth.amihealth.Contract.ContractColor;
import com.amihealth.amihealth.Models.Clasificaciones;
import com.amihealth.amihealth.Models.Colors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 11/18/17.
 */

public class ParserColor {

    private String entrada;
    private ArrayList<Colors> list;
    private Colors colors;

    public ParserColor(String entrada) {
        this.entrada = entrada;
        this.list = new ArrayList<>();

    }

    public ArrayList<Colors> getParser(){

        try{
            JSONObject JsonObj = new JSONObject(entrada);
            JSONArray data= JsonObj.getJSONArray(ContractColor.access);
            for (int i = 0; i < data.length(); i++){
                JSONObject dato = data.getJSONObject(i);
                Colors colors  = new Colors(dato.getString(ContractColor.DESCRIP));
                list.add(colors);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
