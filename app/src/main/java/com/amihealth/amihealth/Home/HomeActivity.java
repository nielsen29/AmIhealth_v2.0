package com.amihealth.amihealth.Home;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.transition.Explode;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.AppConfig.MyPusherService;
import com.amihealth.amihealth.AppConfig.WebDialog;
import com.amihealth.amihealth.AppConfig.notification.NotificationActivity;
import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Login.SplashScreen;
import com.amihealth.amihealth.Models.Cintura;
import com.amihealth.amihealth.Models.Clasificaciones;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.Models.User;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.CinturaMod.CinturaActivity;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.GlucosaActivity;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.MedAntroMainActivity;
import com.amihealth.amihealth.ModuloHTA.HTAhomeActivity;
import com.amihealth.amihealth.ModuloHTA.MedidaHTAListActivity;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.UserActivity.UserActivity;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button launchHTA_btn;
    private Button launchHTA_peso;
    private Button launchUSER_btn;
    private User user;
    private Realm realm;
    private SessionManager sessionManager;
    private ImageView img_profile;
    private TextView email;
    private TextView nombre;
    private MyPusherService pusherService;
    private Button launchICA_btn;
    private Button launch_glucosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        if(sessionManager.isLoggedIn()){
            realm = Realm.getDefaultInstance();
            user = realm.where(User.class).equalTo("id_InServer", sessionManager.getUserLogin().get(SessionManager.KEY).toString()).findFirst();
            //getDATA();

        }
        if(user == null){
            sessionManager.logoutUser();
        }else{
            startService(new Intent(this,MyPusherService.class));
        }

       // startService(new Intent(this,MyPusherService.class));




// Create a new Pusher instance


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        launchHTA_btn = (Button) findViewById(R.id.btnlaunch_hta);
        launchHTA_peso = (Button) findViewById(R.id.btnlaunch_peso);
        launchUSER_btn = (Button) findViewById(R.id.btnlaunch_User);
        launchICA_btn = (Button) findViewById(R.id.btnlaunch_cintura);
        launch_glucosa=(Button)findViewById(R.id.btnlaunch_glucosa);
        launch_glucosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GlucosaActivity.class   );
                startActivity(i);
            }
        });
        launchHTA_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HTAhomeActivity.class);
                startActivity(i);
            }
        });
        launchHTA_peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MedAntroMainActivity.class);
                startActivity(i);
            }
        });
        launchUSER_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(i);
            }
        });
        launchICA_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CinturaActivity.class);
                startActivity(i);
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

       //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.inflateHeaderView(R.layout.nav_header_home);
        img_profile = (ImageView) v.findViewById(R.id.imageView_profile);
        email = (TextView) v.findViewById(R.id.correoNav);
        nombre = (TextView) v.findViewById(R.id.userNav);
        navigationView.setNavigationItemSelectedListener(this);

       if (sessionManager.isLoggedIn()){
           getUser();
           email.setText(user.getEmail());
           nombre.setText(user.getNombre()+ " " + user.getApellido());
           Picasso.with(getApplicationContext()).load(Configuracion.URL_IMG_ROOT + user.getImg()).into(img_profile);
       }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(getApplicationContext(), HTAhomeActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(getApplicationContext(), MedAntroMainActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(getApplicationContext(), CinturaActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_glucosa) {
            Intent i = new Intent(getApplicationContext(), GlucosaActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_manage) {
            Intent i = new Intent(getApplicationContext(), UserActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {
            showTerminos("https://saludmovil.utp.ac.pa/terms-and-conditions");

        } else if (id == R.id.nav_send) {
            showTerminos("https://saludmovil.utp.ac.pa/privicy-policy");

        }else if (id == R.id.nav_discl) {
            showTerminos("https://saludmovil.utp.ac.pa/disclaimer");
        }else if (id == R.id.btn_logout){
            sessionManager.logoutUser();
        }else if (id == R.id.nav_notification){
            Intent i = new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getUser(){
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();
        Call<User> call = retrofitAdapter.getClientService(sessionManager.getUserLogin().get(SessionManager.AUTH)).getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {
                if (response.isSuccessful()){
                    realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.insertOrUpdate(response.body());
                        }
                    });
                    realm.close();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        getUser();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUser();
    }

    public void showTerminos(String url){
        WebDialog webDialog =  WebDialog.newInstance(url);
        webDialog.show(getSupportFragmentManager(),"WEBDIALOG");
    }

    public void getDATA(){
        this.realm = Realm.getDefaultInstance();
        RealmResults<Clasificaciones> realmResults = realm.where(Clasificaciones.class).findAll();
        Toast.makeText(getApplicationContext(), realmResults.toString(),Toast.LENGTH_LONG).show();
    }
}
