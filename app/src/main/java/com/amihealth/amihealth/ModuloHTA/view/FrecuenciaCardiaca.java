package com.amihealth.amihealth.ModuloHTA.view;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.Adaptadores.DeviceListActivity;
import com.amihealth.amihealth.AppConfig.StaticError;
import com.amihealth.amihealth.ModuloHTA.HTAhomeActivity;
import com.amihealth.amihealth.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class FrecuenciaCardiaca extends AppCompatActivity {

    Button ObtenerBoton, btnOff,cancelar,guardar;
    TextView txtArduino, txtString, txtStringLength, sensorView0;
    TextView txtSendorLDR;
    Handler bluetoothIn;
    EditText valorglucosa;
    final int handlerState = 0;                         //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket;
    private StringBuilder recDataString = new StringBuilder();
    private ProgressDialog progressDialog;
    private ConnectedThread mConnectedThread;
    private StaticError staticError;
    private AlertDialog alertDialog;

    //____________________________________________________________________________________________
    Thread hilo_bt;
    OutputStream mmOutputStream = null;
    InputStream mmInputStream = null;
    boolean stopWork;


    private boolean is_btSet = false;

    private static BluetoothAdapter mBtAdapter = null;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address = null;

    private Spinner sp_bt;

    private Button btn_getDataBT;
    private Button btn_btStop;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frecuencia_cardiaca);
        //Link the buttons and textViews to respective views


        sp_bt = (Spinner) findViewById(R.id.sp_bt);
        btn_getDataBT = (Button) findViewById(R.id.btn_getDataBT);
        btn_btStop = (Button) findViewById(R.id.btn_btStop);

        if(is_btSet){
            btn_getDataBT.setVisibility(View.VISIBLE);
        }else{
            btn_getDataBT.setVisibility(View.INVISIBLE);
            btn_btStop.setVisibility(View.INVISIBLE);
        }

        btn_getDataBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_btSet){
                    mConnectedThread.interrupt();
                    getBTdata("2");
                }
            }
        });

        btn_btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //getBTdata("3");
                    mConnectedThread.interrupt();
                    btSocket.close();

                    btn_btStop.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        bluetoothIn = new Handler(){
            public void handleMessage(android.os.Message msg){
                if (msg.what == handlerState) {                                        //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                    //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);

                        // extract string
                       // txtString.setText("Datos recibidos = " + dataInPrint);
                        // int dataLength = dataInPrint.length();                            //get length of data received
                        // txtStringLength.setText("Tamaño del String = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')                                //if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);
                            Toast.makeText(getApplicationContext(),sensor0,Toast.LENGTH_SHORT).show();
                            //get sensor value from string between indices 1-5
                           // valorglucosa.setText(sensor0);
                            /*if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }*/

                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();



        sp_bt.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,getBT_bounded()));
        sp_bt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    String info = ((TextView) view).getText().toString();
                    address = info.substring(info.length() - 17);

                        use_thisBT(address);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private ArrayList<String> getBT_bounded() {

        ArrayList<String> aux = new ArrayList<>();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        checkBTState();

        aux.add("SELECT DEVICE");

        // Add previosuly paired devices to the array
        if (pairedDevices.size() > 0) {
            //findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
            for (BluetoothDevice device : pairedDevices) {
                aux.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            aux.add(noDevices);
        }
        return aux;
    }

    private void use_thisBT(String bt_add){

        BluetoothDevice device = btAdapter.getRemoteDevice(bt_add);
        // Establish the Bluetooth socket connection.

        try {
            this.btSocket = createBluetoothSocket(device);
            this.btSocket.connect();
            this.mmOutputStream = btSocket.getOutputStream();
            this.mmInputStream = btSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        hilo_bt = new Thread(new Runnable() {
            @Override
            public void run() {

                byte[] buffer = new byte[256];
                int bytes;

                while (!Thread.currentThread().isInterrupted() && !stopWork){


                    try {
                        bytes = mmInputStream.read(buffer);            //read bytes from input buffer
                        String readMessage = new String(buffer, 0, bytes);
                        // Send the obtained bytes to the UI Activity via handler


                        bluetoothIn.post(new Runnable() {
                            @Override
                            public void run() {
                                btn_getDataBT.setVisibility(View.VISIBLE);
                                btn_getDataBT.setText(getMSJ(readMessage));
                            }
                        });
                        bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                    } catch (IOException e) {
                        stopWork = true;
                        break;
                    }



                }


               /* mConnectedThread = new ConnectedThread(btSocket);
                mConnectedThread.start();
                mConnectedThread.write("x");
                */
            }
        });

        hilo_bt.start();
        write("1");



       // Toast.makeText(getApplicationContext(),String.valueOf(device.getBondState()),Toast.LENGTH_LONG).show();
        //mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called


    }
    public void write(String input) {
        byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
        try {
            mmOutputStream.write(msgBuffer);
            is_btSet = true;
            btn_getDataBT.setVisibility(View.VISIBLE);
            //write bytes over BT connection via outstream
        } catch (IOException e) {
            //if you cannot write, close the application
            Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
            is_btSet = false;
            btn_getDataBT.setVisibility(View.INVISIBLE);
            try {
                btSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    public String getMSJ(String readMessage){
        String sensor0;
        recDataString.append(readMessage);                                    //keep appending to string until ~
        int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
        if (endOfLineIndex > 0) {                                           // make sure there data before ~
            String dataInPrint = recDataString.substring(0, endOfLineIndex);

            // extract string
            // txtString.setText("Datos recibidos = " + dataInPrint);
            // int dataLength = dataInPrint.length();                            //get length of data received
            // txtStringLength.setText("Tamaño del String = " + String.valueOf(dataLength));

            if (recDataString.charAt(0) == '#')                                //if it starts with # we know it is what we are looking for
            {
                sensor0 = recDataString.substring(1, 5);
                return  sensor0;

            }

            recDataString.delete(0, recDataString.length());                    //clear all string data
            // strIncom =" ";
            dataInPrint = " ";
            return sensor0 = "ESTA DAMIER NO FIFLA 1";

        }
        return sensor0 = "ESTA DAMIER NO FIFLA 2";
    }

    public void getBTdata(String x){
        if(btSocket.isConnected()) {
            mConnectedThread.write(x);
            btn_btStop.setVisibility(View.VISIBLE);
        }else{

                use_thisBT(((String)sp_bt.getSelectedItem().toString()).substring((sp_bt.getSelectedItem().toString()).length() - 17));

            mConnectedThread.write(x);
            btn_btStop.setVisibility(View.VISIBLE);
        }
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();
        checkBTState();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        //address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        //Log.i("ramiro", "adress : " + address);

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



    //create new class for connect thread
    private class ConnectedThread extends Thread {
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
            while (!this.isInterrupted()) {
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
                mmOutStream.write(msgBuffer);
                is_btSet = true;
                btn_getDataBT.setVisibility(View.VISIBLE);
                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
               is_btSet = false;
               btn_getDataBT.setVisibility(View.INVISIBLE);
                try {
                    btSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}
