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

import java.util.ArrayList;

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

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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

                LinearLayout lrLayout = findViewById(R.id.linearLayout);
                int numTextViews = lrLayout.getChildCount();

                ArrayList<String> names = new ArrayList<>();

                for(int i = 2; i < numTextViews; i++)
                {
                    if(lrLayout.getChildAt(i) instanceof EditText)
                    {
                        names.add(((EditText)lrLayout.getChildAt(i)).getText().toString());
                    }
                }

                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("numPlayers", clickedButton);
                gameIntent.putExtra("namePlayers", names.toArray(new String[names.size()]));
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

            EditText evPlayer = new EditText(this);
            evPlayer.setMaxLines(1);

            lrLayout.addView(evPlayer);
        }
    }
}
