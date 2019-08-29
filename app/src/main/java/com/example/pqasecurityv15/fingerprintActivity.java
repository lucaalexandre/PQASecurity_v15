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

public class fingerprintActivity extends AppCompatActivity {

    // Declarando Variavel

    private TextView mHaddingLabel;
    private ImageView mFingerprintImage;
    private  TextView mParaLabel;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Colocando as imagens nas variaveis

        mHaddingLabel = (TextView) findViewById(R.id.headdingLabel);
        mFingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);
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
                mParaLabel.setText("Fingerprint Scanner not detected in Device");

                //Verificando se tem permissao de entrada
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager. PERMISSION_GRANTED){
                mParaLabel.setText("Permission not granted to use Fingerprint Scanner");

                // Se pedindo para o usuario add uma chave biometrica no celular caso nao tenha
            } else  if (!keyguardManager.isKeyguardSecure()){
                mParaLabel.setText("Add Lock to your Phone in Settings");
            } else  if (!fingerprintManager.hasEnrolledFingerprints()){
                mParaLabel.setText("You should add atleast 1 Fingerprint to use this Feature");


            } else {
                mParaLabel.setText("Please your Finger on Scanner to Access the App.");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);
            }
        }
    }
}
