package com.amihealth.amihealth.Configuraciones;

/**
 * Created by amihealthmel on 07/26/17.
 */

public class Configuracion {

   // public static  final String ROOT_SERVER ="https://saludmovil.utp.ac.pa/";
     //public static final String ROOT_SERVER = "http://10.168.2.113:8888/amihealth/public/";
   //public static  final  String ROOT_SERVER ="http://10.0.2.2:8000/";

    //public static final String SERVER = "http://10.192.64.10/api/";
  // public static final String SERVER = "http://10.168.2.106:8888/amihealth/public/api/";
  // public static final String SERVER = "http://192.168.0.201/api/";


    public static final String ROOT_SERVER = "https://saludmovil.utp.ac.pa/";
    public static final String SERVER = ROOT_SERVER + "api/";


    public static final String URL_NUEVA_MEDIDA = "nuevamedida";
    public static final String URL_Lista_MEDIDA = SERVER + "medidas/";
    public static final String URL_LOGIN = "oauth/token";
    public static final String URL_BUSCAR_MEDIDA_ID = SERVER + "medida/";
    public static final String URL_PROVINCIA = SERVER + "provincia";
    public static final String URL_DISTRITO = SERVER + "distritos/";
    public static final String URL_CORREGIMIENTO = SERVER + "corregimientos/";
    public static final String URL_REGISTRO = SERVER + "register";
    public static final String URL_GET_EMAIL = SERVER + "getEmail";
    public static final String URL_syncMedida = SERVER + "syncMedida";
    public static final String USER_ID = "USER_ID";
    public static final String URL_GET_PROVINCIA = SERVER + "provincias";
    public static final String URL_GET_PESOS= SERVER + "pesos/";
    public static final String URL_NUEVO_PESO= SERVER + "nuevo-peso";
    public static final String ID_PACIENTE = "id_paciente";
    public static final String URL_ETNIAS = SERVER + "etnias";
    public static final String URL_GETEMAIL = SERVER + "getEmail";
    public static final String URL_GETUSER = SERVER + "getUser";
    public static final String URL_GETCAT = SERVER + "getCAT";
   public static final String URL_GETCOLORS = SERVER + "getColors";
   //public static final String URL_UpdateHTA = SERVER + "updatehta";

   public static final String URL_IMG_ROOT = ROOT_SERVER + "uploads/avatars/";


   //SECCION PARA LOS ENDPOINT PARA RETROFIT

   public static final String URL_USER ="user";
    public static final String URL_GETMEDIDAS_HTA = "medidasHTA";
    public static final String URL_GETMEDIDAS_HTA_ORDER = "medidasHTAinOrder/{order}";
    public static final String URL_UpdateHTA =  "updatehta";
    public static final String URL_DELETE_HTA = "delete_hta";
    public static final String URL_GETMEDIDAS_HTA_ID = "getHTAbyID";
    public static final String URL_UpdateProfile = "update_profile";

    //SECCION PATRA LOS ENDPOINT DE PESO

    public static final String URL_GETMEDIDAS_PESO = "peso";
    public static final String URL_INSERT_PESO = "nuevo_peso";

}
