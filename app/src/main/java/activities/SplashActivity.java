package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.studreference.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask(){
            public void run() {
                startActivity(new Intent(SplashActivity.this, SignupActivity.class));
                finish();
            }
        }, 2000 );
        getSupportActionBar().hide();
    }
}