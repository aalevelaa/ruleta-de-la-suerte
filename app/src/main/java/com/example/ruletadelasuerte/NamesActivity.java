package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NamesActivity extends AppCompatActivity {

    private ImageView playButton;
    private String clickedButton;

    private static final String PLAYER_2 = "2";
    private static final String PLAYER_3 = "3";
    private static final String PLAYER_4 = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);
        Intent intent = getIntent();
        clickedButton = intent.getStringExtra("clicked_button");

        switch (clickedButton)
        {
            case PLAYER_2:
                createTextFields(PLAYER_2);
                break;

            case PLAYER_3:
                createTextFields(PLAYER_3);
                break;

            case PLAYER_4:
                createTextFields(PLAYER_4);
                break;


            }



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


    public void createTextFields(String str)
    {
        int total = Integer.parseInt(str);
        LinearLayout lrLayout = findViewById(R.id.linearLayout);
        TextView tv = new TextView(this);

        tv.setText("¿CÓMO OS LLAMÁIS?");
        lrLayout.addView(tv);

        for(int i = 1; i <= total; i++)
        {
            TextView tvPlayer = new TextView(this);
            tvPlayer.setText("PLAYER " + i);

            lrLayout.addView(tvPlayer);
            lrLayout.addView(new EditText(this));

        }
    }
}
