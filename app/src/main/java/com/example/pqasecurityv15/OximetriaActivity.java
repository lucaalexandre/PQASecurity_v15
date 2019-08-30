package com.example.pqasecurityv15;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class OximetriaActivity extends AppCompatActivity {

    Button BtConctar;

    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONECCAO = 2;


    boolean coneccao = false;

    private static String MAC = null;

    //////// Parametro Bluetooth//////
    BluetoothAdapter meuBluetoothAdapter = null;

    BluetoothDevice meuDevice = null;

    BluetoothSocket meuSocket = null;

    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oximetria);

        BtConctar = (Button)findViewById(R.id.BtConectar);

        /////////////Identificacao do bluetooth///////
        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (meuBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Seu Bluetooth nao esta ligado", Toast.LENGTH_SHORT).show();
        }else if(!meuBluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }
        ///////// Ativando a Tela de Bluetooth ligados////////
        BtConctar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (coneccao){
                    /// Desconectar
                    try{
                        meuSocket.close();
                        coneccao = false;
                        BtConctar.setText("Conectar");
                        Toast.makeText(getApplicationContext(), "Bluetooth desconectado " , Toast.LENGTH_SHORT).show();

                    }catch (IOException erro){
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro " + erro, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //Conectar
                    Intent abreLista = new Intent(OximetriaActivity.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONECCAO );
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case SOLICITA_ATIVACAO:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "O Bluetooth foi ativado", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Bluetooth nao foi ativado, ative-o ou use sua digital", Toast.LENGTH_SHORT).show();
                    //////// COLOCAR A TELA DE FINGER PRINT AKI PARA INICIAR/////////
                    finish();
                }
                break;


            case SOLICITA_CONECCAO:
                if (resultCode == Activity.RESULT_OK){

                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);

                    //Toast.makeText(getApplicationContext(), "MAC Final: " + MAC, Toast.LENGTH_SHORT).show();
                    meuDevice = meuBluetoothAdapter.getRemoteDevice(MAC);

                    try{
                        meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID);

                        meuSocket.connect();

                        coneccao = true;




                        BtConctar.setText("Desconectar");

                        Toast.makeText(getApplicationContext(), "Voce foi conectado com: " + MAC, Toast.LENGTH_SHORT).show();

                    }catch (IOException erro){

                        coneccao = false;

                        Toast.makeText(getApplicationContext(), "Ocorreu um erro " + erro, Toast.LENGTH_SHORT).show();

                    }



                }else {
                    Toast.makeText(getApplicationContext(), "Falha em obter o MAC", Toast.LENGTH_SHORT).show();
                }

        }

    }



}