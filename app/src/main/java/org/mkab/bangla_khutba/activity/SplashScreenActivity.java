package org.mkab.bangla_khutba.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.widget.Toast;

import org.mkab.bangla_khutba.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ActivityCompat.requestPermissions(SplashScreenActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        }, SPLASH_TIME_OUT);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SharedPreferences.Editor editor = getSharedPreferences("PHONE_NUMBER", MODE_PRIVATE).edit();
                    editor.putBoolean("signIn", true);
                    editor.commit();
                } else {
                    // permission denied, boo! Disable the
                    Toast.makeText(SplashScreenActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }

            startActivity(new Intent(this, BottomViewActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}