package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Animation rotateAnimation;
    ImageButton ruletaIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ruletaIntro = (ImageButton) findViewById(R.id.ruletaIntroButton);

        rotateAnimation();

    }
        private void rotateAnimation(){
            rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
            ruletaIntro.startAnimation(rotateAnimation);
        }



}

