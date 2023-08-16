package com.example.qrscannerandgenerator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import eu.livotov.labs.android.camview.ScannerLiveView;

public class ScanQRActivity extends AppCompatActivity {

    private ScannerLiveView scannerLiveView;
    private TextView scannerTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        scannerLiveView=findViewById(R.id.camView);
        scannerTv=findViewById(R.id.idTVSannedData);
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
}