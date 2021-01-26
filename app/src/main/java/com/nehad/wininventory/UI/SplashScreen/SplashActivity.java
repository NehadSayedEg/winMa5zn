package com.nehad.wininventory.UI.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nehad.wininventory.R;
import com.nehad.wininventory.UI.FilesActivity.FilesActivity;

public class SplashActivity extends AppCompatActivity {
    private  static  int  splashScreen  = 2000;
  Animation topAnimation ,bottomAnimation;
  ImageView imageView ;
  TextView textWin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation( this , R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation( this , R.anim.bottom_animation);

        textWin =  findViewById(R.id.textWin);
        imageView =findViewById(R.id.imageView);

        imageView.setAnimation(topAnimation);
        textWin.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this , FilesActivity.class);
                startActivity(intent);
                finish();

            }
        }, splashScreen);
    }
}