package com.example.cakedream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation topanim,bottomanim;
    ImageView image;
    TextView logo;

    private static int SPLASH_TIME = 7000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //animations
        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //hook
        image = findViewById(R.id.app_icon);
        logo = findViewById(R.id.app_line);

        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,pre_made_base.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME);
    }
}