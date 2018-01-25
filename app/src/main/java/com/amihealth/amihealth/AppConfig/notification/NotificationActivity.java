package com.amihealth.amihealth.AppConfig.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.R;

public class NotificationActivity extends AppCompatActivity implements NotificationAction {


    private RecyclerView recyclerView;
    private SessionManager sessionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_notify);





    }


    @Override
    public void aceptar(String id) {

    }

    @Override
    public void declinar(String id) {

    }
}
