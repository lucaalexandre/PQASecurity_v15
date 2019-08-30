package com.example.pqasecurityv15;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FingerPrintActivity extends AppCompatActivity {


        // Declarando Variavel

        private TextView mHaddingLabel;
        private ImageView mFingerprintImage;
        private  TextView mParaLabel;

        private FingerprintManager fingerprintManager;
        private KeyguardManager keyguardManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_finger_print);

            // Colocando as imagens nas variaveis

            mHaddingLabel = (TextView) findViewById(R.id.headdingLabel);
            mFingerprintImage = (ImageView) findViewById(R.id.mfingerprintImage);
            mParaLabel = (TextView) findViewById(R.id.paraLabel);

            // Tarefas

            // Check 1: Android version should be greater or equal to Marshmallow
            // Check 2: Device has Fingerprint Scanner
            // Check 3: Have permission to use fingerprint scanner in the app
            // Check 4: Lock screen is is secured with atleast 1 type of lock
            // Check 5: Atleast 1 Fingerprint is registered
            // Check 6: Colocar uma tela de sucesso apos a autenticacao

            // Decretando um limite minimo de versao do android
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                //Verificando se tem o leitor de impressao digital
                if (!fingerprintManager.isHardwareDetected()){
                    mParaLabel.setText("Não foi localizado uma Autenticação Biométria ");

                    //Verificando se tem permissao de entrada
                } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager. PERMISSION_GRANTED){
                    mParaLabel.setText("Sua permissão não foi permitida.");

                    // Se pedindo para o usuario add uma chave biometrica no celular caso nao tenha
                } else  if (!keyguardManager.isKeyguardSecure()){
                    mParaLabel.setText("Faça uma Autenticação biométrica na sua conta Santander.");
                } else  if (!fingerprintManager.hasEnrolledFingerprints()){
                    mParaLabel.setText("Você deve adicionar pelo menos 1 impressão digital para usar este recurso");


                } else {
                    mParaLabel.setText("Por favor coloque seu dedo na Auntenticação biométrica do seu smartphone.");

                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                    fingerprintHandler.startAuth(fingerprintManager, null);
                }
            }
        }


    }
