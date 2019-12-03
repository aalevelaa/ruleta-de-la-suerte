package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private ImageView ruleta;
    private int numPlayers = 0;
    private String[] namePlayers;
    private float lastRDiff = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();

        this.numPlayers = Integer.valueOf(intent.getStringExtra("numPlayers"));
        this.namePlayers = intent.getStringArrayExtra("namePlayers");

        addRuletaAnimation();

    }

    private void addRuletaAnimation()
    {
        ruleta = findViewById(R.id.ruletaIntro);

        ruleta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                float r = (float) new Random().nextInt(360);

                final RotateAnimation animRotate = new RotateAnimation(0.0f, -r+5-360,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);


                animRotate.setDuration(3000);
                animRotate.setFillAfter(true);

                ruleta.startAnimation(animRotate);

                //lastRDiff = 360f - r;

                TextView tv = findViewById(R.id.textView2);

                String[] casillas = getResources().getStringArray(R.array.casillas);

                tv.setText(casillas[(int) (Math.ceil(r/15)-1)]);
            }
        });
    }


}
