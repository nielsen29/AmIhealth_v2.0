package com.amihealth.amihealth.Views.Fragmentos.Registro.iteractor;

import android.content.Context;

import com.amihealth.amihealth.Models.Corregimiento;
import com.amihealth.amihealth.Models.Distrito;
import com.amihealth.amihealth.Models.Etnia;
import com.amihealth.amihealth.Models.Provincia;
import com.amihealth.amihealth.Models.Registro;
import com.amihealth.amihealth.Views.Fragmentos.Registro.presenter.RegistroPresenterINT;
import com.amihealth.amihealth.Views.Fragmentos.Registro.repositorio.RegistroRepoIMP;
import com.amihealth.amihealth.Views.Fragmentos.Registro.repositorio.RegistroRepoINT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 09/12/17.
 */

public class RegistroIteractorIMP implements RegistroIteractorINT {

    private RegistroPresenterINT presenterINT;
    private RegistroRepoINT repoINT;

    public RegistroIteractorIMP(RegistroPresenterINT presenterINT) {
        this.presenterINT = presenterINT;
        this.repoINT = new RegistroRepoIMP(presenterINT,this);
    }

    @Override
    public void getProvincia(Context context) {
        repoINT.getProvincia(context);
    }

    public ArrayList<Provincia> getJSONProvincia(String response){
        Provincia provincia;
        ArrayList<Provincia> arr = new ArrayList<>();

        try{
            JSONObject JsonObj = new JSONObject(response);
            JSONArray provincias = JsonObj.getJSONArray("data");
            for (int i = 0; i < provincias.length(); i++){
                JSONObject prov = provincias.getJSONObject(i);
                provincia = new Provincia(prov.getInt("id_provincia"),prov.getString("nombre"));
                arr.add(provincia);
            }
            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Distrito> getJSONDistrito(String response){
        Distrito provincia;
        ArrayList<Distrito> arr = new ArrayList<>();

        try{
            JSONObject JsonObj = new JSONObject(response);
            JSONArray provincias = JsonObj.getJSONArray("data");
            for (int i = 0; i < provincias.length(); i++){
                JSONObject prov = provincias.getJSONObject(i);
                provincia = new Distrito(prov.getInt("id_distrito"),prov.getString("nombre"));
                arr.add(provincia);
            }
            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Corregimiento> getJSONCorregimiento(String response){
        Corregimiento provincia;
        ArrayList<Corregimiento> arr = new ArrayList<>();

        try{
            JSONObject JsonObj = new JSONObject(response);
            JSONArray provincias = JsonObj.getJSONArray("data");
            for (int i = 0; i < provincias.length(); i++){
                JSONObject prov = provincias.getJSONObject(i);
                provincia = new Corregimiento(prov.getInt("id_corregimiento"),prov.getString("nombre"));
                arr.add(provincia);
            }
            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Etnia> getJSONEtnias(String response){
        Etnia etnia;
        ArrayList<Etnia> arr = new ArrayList<>();

        try{
            JSONObject JsonObj = new JSONObject(response);
            JSONArray provincias = JsonObj.getJSONArray("data");
            for (int i = 0; i < provincias.length(); i++){
                JSONObject prov = provincias.getJSONObject(i);
                etnia = new Etnia(prov.getInt("id"),prov.getString("descrip"));
                arr.add(etnia);
            }
            return arr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getDistritos(Context context, int id_provincia) {
        repoINT.getDistritos(context, id_provincia);

    }

    @Override
    public void getCorregimientos(Context context, int id_distrito) {
        repoINT.getCorregimientos(context, id_distrito);
    }

    @Override
    public void getEtnias(Context context) {
        repoINT.getEtnias(context);
    }

    @Override
    public void newUser(Context context, Registro registro) {

        repoINT.newUser(context, registro);

    }

    @Override
    public void getProvinciaResponse(String response) {
        presenterINT.returnProvincia(getJSONProvincia(response));
    }

    @Override
    public void getDistritosResponse(String response) {

        presenterINT.returnDistritos(getJSONDistrito(response));

    }

    @Override
    public void getCorregimientosResponse(String response) {

        presenterINT.returnCorregimientos(getJSONCorregimiento(response));

    }

    @Override
    public void getEtniasResponse(String response) {
        presenterINT.returnEtnias(getJSONEtnias(response));
    }


}
