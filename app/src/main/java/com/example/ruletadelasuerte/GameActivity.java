package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

import android.os.Bundle;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    private ImageView ruleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ruleta = findViewById(R.id.ruletaIntro);

        FlingAnimation fling = new FlingAnimation(ruleta, DynamicAnimation.SCROLL_Y);

        fling.setStartVelocity(-3)
                .setMinValue(0)
                .setMaxValue(20)
                .setFriction(1.1f)
                .start();

    }
}
