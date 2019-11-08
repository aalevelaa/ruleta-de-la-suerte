package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class NamesActivity extends AppCompatActivity {

    ImageView playButton;
    String clickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        Intent intent = getIntent();
        clickedButton = intent.getStringExtra("clicked_button");

        Log.i(clickedButton, "BOTON PULSADO" + clickedButton);

        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(gameIntent);

            }
        });
    }
}
