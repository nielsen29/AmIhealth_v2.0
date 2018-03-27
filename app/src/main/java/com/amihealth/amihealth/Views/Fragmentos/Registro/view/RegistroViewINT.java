package com.amihealth.amihealth.Views.Fragmentos.Registro.view;

import com.amihealth.amihealth.Models.Corregimiento;
import com.amihealth.amihealth.Models.Distrito;
import com.amihealth.amihealth.Models.Etnia;
import com.amihealth.amihealth.Models.Provincia;

import java.util.ArrayList;

/**
 * Created by amihealthmel on 09/12/17.
 */

public interface RegistroViewINT {
    void getProvincia(ArrayList<Provincia> provincias);
    void getDistritos(ArrayList<Distrito> distritos);
    void getCorregimientos(ArrayList<Corregimiento> corregimientos);
    void getEtnias(ArrayList<Etnia> etnias);

    void responseNewUser();
}
