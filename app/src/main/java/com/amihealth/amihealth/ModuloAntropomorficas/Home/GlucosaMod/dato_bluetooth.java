package com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.AppConfig.StaticError;
import com.amihealth.amihealth.Models.Glucosa;
import com.amihealth.amihealth.Adaptadores.DeviceListActivity;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Fragments.InterfaceGlucosaView;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.GlucosaPresenterIMP;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.GlucosaMod.Presenter.InterfaceGlucosaPresenter;
import com.amihealth.amihealth.R;
public class dato_bluetooth extends AppCompatActivity implements InterfaceGlucosaPresenter, InterfaceGlucosaView {

    Button ObtenerBoton, btnOff,cancelar,guardar;
    TextView txtArduino, txtString, txtStringLength, sensorView0;
    TextView txtSendorLDR;
    Handler bluetoothIn;
    EditText valorglucosa;
    final int handlerState = 0;                         //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    private ProgressDialog progressDialog;
    private ConnectedThread mConnectedThread;
    private GlucosaPresenterIMP presenterglucosa;
    private StaticError staticError;
    private AlertDialog alertDialog;
    private Spinner sp_kg;
    private int pos;
    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetoothadd);
        //Link the buttons and textViews to respective views
        ObtenerBoton = (Button) findViewById(R.id.buttonOn);
        //btnOff = (Button) findViewById(R.id.buttonOff);
        txtString = (TextView) findViewById(R.id.txtString);

        cancelar=(Button)findViewById(R.id.cancelar_amicheck);
        guardar=(Button)findViewById(R.id.guardar_amicheck);
        valorglucosa=(EditText) findViewById(R.id.nuevo_glucosa_amicheck);
        txtSendorLDR = (TextView) findViewById(R.id.tv_sendorldr);
        valorglucosa.setHint("(mg/dl)");
        Glucosa glucosa=new Glucosa();

        progressDialog = new ProgressDialog(dato_bluetooth.this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        sp_kg = (Spinner) findViewById(R.id.spinnertipo);
        String[] s = {"Antes de comer","2 horas poscomida"};
        sp_kg.setAdapter(new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,s));
        sp_kg.setEnabled(true);
        sp_kg.setVisibility(View.VISIBLE);


        sp_kg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

            presenterglucosa = new GlucosaPresenterIMP( this,getApplicationContext());
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                        //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                    //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        txtString.setText("Datos recibidos = " + dataInPrint);
                       // int dataLength = dataInPrint.length();                            //get length of data received
                       // txtStringLength.setText("Tamaño del String = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')                                //if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            valorglucosa.setText(sensor0);
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }

                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };
        valorglucosa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(valorglucosa.getText().length() <= 0){
                    valorglucosa.setError("Este campo no puede estar vacio");

                }
            }
        });
        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED


        ObtenerBoton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1");    // Send "1" via Bluetooth
                progressDialog.setMessage("Recolectando Data");
                progressDialog.show();
            }
        });

        cancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                valorglucosa.setText("");
                finish();
            }
        });

        guardar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(valorglucosa.getText().length() <= 0){
                    valorglucosa.setError("Este campo no puede estar vacio");
                    valorglucosa.setText("");

                }else{
                    glucosa.setGlucosa(valorglucosa.getText().toString());
                    //int pos2= pos+1;
                    String tipo= String.valueOf(pos+1);
                    glucosa.setTipolectura(tipo);
                    presenterglucosa.RequestInsert(glucosa);
                    progressDialog.setMessage("Guardando...Espere Por favor");
                    progressDialog.show();
                }
            }
        });
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        //Log.i("ramiro", "adress : " + address);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @Override
    public void OnGetAllResponse() {

        if(progressDialog.isShowing())
        {
            progressDialog.dismiss();

        }
        Toast.makeText(getApplicationContext(),"Dato Insertado!",Toast.LENGTH_LONG).show();
       // Intent i = new Intent(getApplicationContext(), GlucosaActivity.class);
        //startActivity(i);
        finish();

    }
    protected void onDestroy() {
        super.onDestroy();
        Intent previousScreen = new Intent(getApplicationContext(), GlucosaActivity.class);
        startActivity(previousScreen);
    }

    @Override
    public void OnInsertResponse() {
        //Toast.makeText(getApplicationContext(),"Inserto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDeleteResponse() {
       // Toast.makeText(getApplicationContext(),"Inserto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnUpdateResponse() {
        //Toast.makeText(getApplicationContext(),"Inserto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnErrorResponse(String error) {
       //Toast.makeText(getApplicationContext(),"Inserto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnErrorMedida(String error) {
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void RespuestaActivity(int cargar) {
        //Toast.makeText(getApplicationContext(),"Inserto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickMenuItem_EDIT(String id) {

    }

    @Override
    public void onClickMenuItem_DELETE(String id) {

    }

    @Override
    public void RequestGetAll() {
        //Toast.makeText(getApplicationContext(),"Inserto",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RequestInsert(Glucosa glucosa) {

    }

    @Override
    public void RequestUpdate(Glucosa glucosa) {

    }

    @Override
    public void RequestDelete(Glucosa glucosa) {

    }

    //create new class for connect thread
    public class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

}