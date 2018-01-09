package com.amihealth.amihealth.Models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by amihealthmel on 12/10/17.
 */

public class MedidasHTAgroupBy extends RealmObject {

    private int group;
    private RealmList<MedidaHTA> medidas;


}
