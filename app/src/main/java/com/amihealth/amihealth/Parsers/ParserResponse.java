package com.amihealth.amihealth.Parsers;

import com.amihealth.amihealth.Contract.ClasificacionesSA;
import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.MedidaHTA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by amihealthmel on 11/16/17.
 */

public class ParserResponse {

    private String entrada;
    private String message;
    private String status;
    private ArrayList<String> list;

    public static final String ACCESS = "data";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";


    public ParserResponse(String entrada) {
        this.entrada = entrada;
        this.list = new ArrayList<>();
    }

    public ArrayList<String> getParser(){

        try{
            JSONObject JsonObj = new JSONObject(entrada);
            JSONObject dato     = JsonObj.getJSONObject(ACCESS);
            list.add(dato.getString(MESSAGE));
            list.add(dato.getString(STATUS));

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return list;
    }

}
