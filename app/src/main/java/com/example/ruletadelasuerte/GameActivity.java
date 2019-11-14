package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ImageView ruleta;
    private AnimationSet animSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ruleta = findViewById(R.id.ruletaIntro);

        animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        float r = (float) new Random().nextInt(360);

        RotateAnimation animRotate = new RotateAnimation(0.0f, 2160 + r,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);


        animRotate.setDuration(3000);
        animRotate.setFillAfter(true);

        animSet.addAnimation(animRotate);



        ruleta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ruleta.startAnimation(animSet);
            }
        });
    }


}
