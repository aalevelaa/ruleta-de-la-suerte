package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Animation rotateAnim;
    Animation bounceAnim;
    ImageView ruletaIntro;
    ImageView playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ruletaIntro = findViewById(R.id.ruletaIntro);
        playButton = findViewById(R.id.playButton);

        loadAnimations();

        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent activityPlayersMenuIntent = new Intent(getApplicationContext(), PlayersMenuActivity.class);
                startActivity(activityPlayersMenuIntent);
            }
        });


    }
        private void loadAnimations()
        {
            // Ruleta rotation
            rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
            ruletaIntro.startAnimation(rotateAnim);

            // Play button breathing
            bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 500);
            bounceAnim.setInterpolator(interpolator);
            playButton.startAnimation(bounceAnim);
        }
}

