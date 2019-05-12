package app.company.androidtask.activities.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;

import app.company.androidtask.R;
import app.company.androidtask.Utills.Constant;
import app.company.androidtask.activities.Home.HomeActivity;
import app.company.androidtask.activities.Login.LoginActivity;
import io.fabric.sdk.android.Fabric;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        new Handler().postDelayed(() -> {
            SharedPreferences mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
            Intent intent;
            if ((mSharedPreferences.getBoolean(Constant.rememberMe, false))) {
                intent = new Intent(Splash.this, HomeActivity.class);

            } else {
                intent = new Intent(Splash.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();


        }, 3000);
        setContentView(R.layout.activity_splash);

    }
}
