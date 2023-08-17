package com.example.qrscannerandgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanQRActivity extends AppCompatActivity {

    private ScannerLiveView scannerLiveView;
    private TextView scannerTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        scannerLiveView=findViewById(R.id.camView);
        scannerTv=findViewById(R.id.idTVSannedData);
        if(checkPermission()){
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        }else{
            requestPermission();
        }

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScanQRActivity.this, "Scanner Started..", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScanQRActivity.this, "Scanner Stopped..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {

            }

            @Override
            public void onCodeScanned(String data) {
                scannerTv.setText(data);

            }
        });
    }
    private boolean checkPermission(){
        int camera_permission= ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA_SERVICE);
        int vibrate_permission=ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATOR_SERVICE);
        return camera_permission== PackageManager.PERMISSION_GRANTED && vibrate_permission==PackageManager.PERMISSION_GRANTED;

    }
    private void requestPermission(){
        int permissionCode=200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA_SERVICE,VIBRATOR_SERVICE},permissionCode);
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        ZXDecoder decoder=new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
            boolean vibrateionAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
            if(cameraAccepted && vibrateionAccepted){
                Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission Denied \n You can't use the app without permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
