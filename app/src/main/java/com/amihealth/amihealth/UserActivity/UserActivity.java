package com.amihealth.amihealth.UserActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.ApiAmIHealth.RetrofitAdapter;
import com.amihealth.amihealth.AppConfig.OnDialogResponse;
import com.amihealth.amihealth.AppConfig.StaticError;
import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Configuraciones.SessionManager;
import com.amihealth.amihealth.Models.Estatura;
import com.amihealth.amihealth.Models.User;
import com.amihealth.amihealth.R;
import com.amihealth.amihealth.Views.Fragmentos.Registro.view.FechaFragment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class UserActivity extends AppCompatActivity implements OnDialogResponse {

    private static final String URI_RAIZ = "img_tmp/";
    private static final String URI_NEW_IMG = URI_RAIZ + "tmp";
    private User user;
    private EditText nombre;
    private TextView apellido;
    private TextView fecha_nacimiento;
    private TextView genero;
    private TextView estatura;
    private Spinner sexo;
    private Button fecha;
    private Button btn_update;

    private File filetoUp = null;

    public final static int CODE_GALERIA = 300;
    public final static int CODE_CAMERA = 301;

    String path_foto = "";

    private ImageView imageView;

    private SessionManager sessionManager;

    private Realm realm;

    private ArrayList<String> sp_label = new ArrayList<>();
    private ArrayAdapter<String> sp_sex_adapter;

    private StaticError staticError;
    private android.support.v7.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
        realm = Realm.getDefaultInstance();

        showtoolbar("Editar Perfil",true);
        staticError = new StaticError(this);
        alertDialog = staticError.getErrorDialogAlert(this,StaticError.ESPERA);

        imageView = (CircleImageView) findViewById(R.id.circleImageView);
        nombre = (EditText) findViewById(R.id.profile_name);
        apellido = (EditText) findViewById(R.id.profile_lastname);
        fecha_nacimiento = (EditText) findViewById(R.id.profile_fecha_nacimiento);
        estatura = (EditText) findViewById(R.id.profile_altura);
        sexo = (Spinner) findViewById(R.id.profile_sp_sexo);
        btn_update = (Button) findViewById(R.id.btn_actualizar_profile);
        //btn_update.setEnabled(false);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean cambios = false;

                MultipartBody.Part cuerpo = null;

                if(filetoUp != null) {
                    File file = filetoUp;
                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                    cuerpo = MultipartBody.Part.createFormData("img", file.getName(), reqFile);
                    cambios = true;
                    //Toast.makeText(getApplicationContext(),"cambio img",Toast.LENGTH_LONG).show();
                }
                Map<String, RequestBody> requestbody = new HashMap<String, RequestBody>();

                if( !nombre.getText().toString().equals(user.getNombre())){
                    RequestBody mNombre = RequestBody.create(MediaType.parse("plain/text"), nombre.getText().toString());
                    requestbody.put("nombre",mNombre);
                    cambios = true;
                    //Toast.makeText(getApplicationContext(),"cambio nom",Toast.LENGTH_LONG).show();

                }
                if( !apellido.getText().toString().equals(user.getApellido())){
                    RequestBody mApellido= RequestBody.create(MediaType.parse("plain/text"), apellido.getText().toString());
                    requestbody.put("apellido",mApellido);
                    cambios = true;
                    //Toast.makeText(getApplicationContext(),"cambio apl",Toast.LENGTH_LONG).show();

                }
                if(!estatura.getText().toString().equals(String.valueOf(user.getPaciente().getEstatura()))){
                    RequestBody mEstatura = RequestBody.create(MediaType.parse("plain/text"),estatura.getText().toString());
                    requestbody.put("estatura",mEstatura);
                    cambios = true;
                    //Toast.makeText(getApplicationContext(),"cambio estat",Toast.LENGTH_LONG).show();

                }
                if(!fecha_nacimiento.getText().toString().equals(user.getPaciente().getFecha_nacimiento())){
                    RequestBody mFechaN = RequestBody.create(MediaType.parse("plain/text"),fecha_nacimiento.getText().toString());
                    requestbody.put("fecha_nacimiento",mFechaN);
                    cambios = true;
                    //Toast.makeText(getApplicationContext(),"cambio fecha",Toast.LENGTH_LONG).show();

                }
                if(sexo.getSelectedItemId() != user.getPaciente().getSexo()){
                    RequestBody mSexo = RequestBody.create(MediaType.parse("plain/text"),String.valueOf(sexo.getSelectedItemId()));
                    requestbody.put("sexo",mSexo);
                    cambios = true;
                    //Toast.makeText(getApplicationContext(),"cambio fecha",Toast.LENGTH_LONG).show();

                }


                if(cambios){
                    RetrofitAdapter retrofit = new RetrofitAdapter();


                    Call<User> call = retrofit
                            .getClientService(sessionManager.getUserLogin()
                                    .get(SessionManager.AUTH))
                            .update_profile(cuerpo, requestbody);
                    showAlarm();

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            if(response.isSuccessful()){

                                actualizarDatosUser(response.body());

                                //Toast.makeText(getApplicationContext(),"CARGADO img",Toast.LENGTH_LONG).show();
                            }else {
                                staticError.getErrorD(getApplicationContext(),StaticError.CONEXION);
                               // Toast.makeText(getApplicationContext(),response.errorBody().toString(),Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            staticError.getErrorD(getApplicationContext(),StaticError.CONEXION);

                        }
                    });
                }else{

                    //Toast.makeText(getApplicationContext(),"no realizo cambios",Toast.LENGTH_LONG).show();
                }


                    //MultipartBody aki = MultipartBody.Builder().



            }
        });

        sp_label.add(0,getString(R.string.m));
        sp_label.add(1,getString(R.string.h));
        sp_sex_adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,sp_label);

        sexo.setAdapter(sp_sex_adapter);
        //sexo.setBackgroundColor(Color.BLUE);


        fecha = (Button) findViewById(R.id.btn_fecha_profile);



        //sexo.setAdapter(new SimpleAdapter(getApplicationContext(),sp_label, Adapter.IGNORE_ITEM_VIEW_TYPE));





        if(sessionManager.isLoggedIn()){
            user = realm.where(User.class).equalTo("id_InServer", sessionManager.getUserLogin().get(SessionManager.KEY).toString()).findFirst();
            Picasso.with(getApplicationContext()).load(Configuracion.URL_IMG_ROOT + user.getImg()).into(imageView);
            estatura.setText(String.valueOf(user.getPaciente().getEstatura()));
            nombre.setText(user.getNombre());
            apellido.setText(user.getApellido());
            fecha_nacimiento.setText(user.getPaciente().getFecha_nacimiento());
            sexo.setSelection(user.getPaciente().getSexo());
            fecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFechaDialogo();
                }
            });
        }

        if(validaPermisos()){
            cargarClickListener_img();

        }else{



        }


    }

    private void showAlarm() {
        this.alertDialog.show();
    }
    private void cancelAlarm(){
        this.alertDialog.cancel();
    }

    private void actualizarDatosUser(final User body) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(body);
            }
        });
        realm.close();
        cancelAlarm();
    }

    private void cargarClickListener_img() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagenGaleria();
            }
        });
    }


    public void showFechaDialogo(){

        DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        Date fechas = new Date();
        try {
            fechas = fd.parse(fecha_nacimiento.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechas);
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        new FechaFragment(this,fechaListener,year,mes,dia).show();
    }

    private DatePickerDialog.OnDateSetListener fechaListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            fecha_nacimiento.setText(String.valueOf(i)+"-"+String.valueOf(i1 + 1)+"-"+String.valueOf(i2));
        }
    };

    public void showtoolbar(String titulo, boolean mUpbtn){
        Toolbar toolbar         = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar()   .setTitle(titulo);
        getSupportActionBar()   .setDisplayHomeAsUpEnabled(mUpbtn);
    }

    public void cargarImagenGaleria(){
        final CharSequence[] opciones= {getString(R.string.tomarfoto),getString(R.string.cargarImg), getString(R.string.cancel)};
        final AlertDialog.Builder alertFoto = new AlertDialog.Builder(this);
        alertFoto.setTitle(getString(R.string.titulo_foto_dialog));
        alertFoto.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals(getString(R.string.tomarfoto))){

                    tomarfoto();

                }else{
                    if(opciones[i].equals(getString(R.string.cargarImg))){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"seleccionar la app"), CODE_GALERIA);

                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertFoto.show();
    }


    private void tomarfoto(){

        File imagen = new File(Environment.getExternalStorageDirectory(),URI_NEW_IMG);

        boolean creada = imagen.exists();
        String nombre_img="";

        if(!creada){
            creada = imagen.mkdirs();
        }

        if(creada){
            nombre_img = (System.currentTimeMillis()/1000)+".jpg";
        }

        path_foto = Environment.getExternalStorageDirectory()
                +File.separator
                +URI_NEW_IMG
                +File.separator
                +nombre_img;
        File nueva_img = new File(path_foto);
        this.filetoUp = nueva_img;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String autho = getApplicationContext().getPackageName()+".provider";
            Uri imagenUri = FileProvider.getUriForFile(UserActivity.this,autho,nueva_img);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imagenUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(nueva_img));
        }
        startActivityForResult(intent,CODE_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case CODE_GALERIA:
                    final Uri path = data.getData();

                    //path_foto = data.getData().getEncodedPath();
                    imageView.setImageURI(path);

                    filetoUp = new File(getPath_from_uri(path));

                    break;
                case CODE_CAMERA:
                    MediaScannerConnection.scanFile(this, new String[]{path_foto}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            Log.i("RUTA de almacenamiento", "PATH: "+path_foto);
                        }
                    });
                    Bitmap bitmap = BitmapFactory.decodeFile(path_foto);
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    }


    public String getPath_from_uri(Uri uri){
        String[] datos = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(
                this,
                uri, datos, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},100);
        }

        return false;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                cargarClickListener_img();
                //botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(UserActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                validaPermisos();
            }
        });
        dialogo.show();
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(UserActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }


    @Override
    public void retryConection() {

    }

    @Override
    public void retryBusqueda() {

    }

    @Override
    public void declineBusqueda() {

    }
}
