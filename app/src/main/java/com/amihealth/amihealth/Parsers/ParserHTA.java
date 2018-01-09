package com.amihealth.amihealth.Parsers;

import com.amihealth.amihealth.Contract.ClasificacionesSA;
import com.amihealth.amihealth.Contract.ContractHTA;
import com.amihealth.amihealth.Models.MedidaHTA;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by amihealthmel on 11/15/17.
 */

public class ParserHTA {

    private MedidaHTA medidaHTA;
    private ArrayList<MedidaHTA> list;
    private String entrada;

    public ParserHTA(String entrada) {

        this.entrada    = entrada;
        this.list       = new ArrayList<>();

    }

    public ParserHTA(JsonObject jsonObject){

    }

    public ArrayList<MedidaHTA> getParser(){

        try{
            JSONObject JsonObj  = new JSONObject(entrada);
            JSONArray data      = JsonObj.getJSONArray(ClasificacionesSA.access);

            for (int i = 0; i < data.length(); i++){

                JSONObject dato     = data.getJSONObject(i);
                MedidaHTA medida    = new MedidaHTA(dato.getInt(ContractHTA.SYS),dato.getInt(ContractHTA.DIS),dato.getInt(ContractHTA.PULSO));
                medida              .setId_on_Server(dato.getString(ContractHTA._ID));
                DateFormat format   = new SimpleDateFormat("yyyy/MM/dd");
                JSONObject fecha    = dato.getJSONObject(ContractHTA.CREATED_AT);
                medida              .setFecha(format.parse(fecha.get("date").toString()));
                medida              .setSync(1);

                list                .add(medida);
            }

        } catch (JSONException e) {

            e.printStackTrace();

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return list;
    }
    public ArrayList<MedidaHTA> getParserFROMserver(){

        try{
            JSONObject JsonObj  = new JSONObject(entrada);
            JSONArray data      = JsonObj.getJSONArray(ClasificacionesSA.access);

            for (int i = 0; i < data.length(); i++){

                JSONObject dato     = data.getJSONObject(i);
                DateFormat format   = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                JSONObject fecha    = dato.getJSONObject(ContractHTA.CREATED_AT);
                MedidaHTA medida    = new MedidaHTA(
                                            dato.getString(ContractHTA._ID).toString(),
                                            dato.getInt(ContractHTA.SYS),
                                            dato.getInt(ContractHTA.DIS),
                                            dato.getInt(ContractHTA.PULSO),
                                            1,
                                            fecha.get("date").toString()
                                    );

                list.add(medida);
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return list;
    }
}
