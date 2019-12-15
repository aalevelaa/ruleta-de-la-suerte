package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayersMenuActivity extends AppCompatActivity {

    private ImageView playButton;
    private String clickedButton = "";

    private static final int PLAYER_2 = 2;
    private static final int PLAYER_3 = 3;
    private static final int PLAYER_4 = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_menu);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        /*
        ImageView[] buttons = {findViewById(R.id.player2Button), findViewById(R.id.player3Button), findViewById(R.id.player4Button)};

        for (ImageView i: buttons)
        {
            i.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent activityNamesIntent = new Intent(getApplicationContext(), NamesActivity.class);
                    activityNamesIntent.putExtra("clicked_button", v.toString());
                    startActivity(activityNamesIntent);
                    }
            });
        }
        */

        ImageView button2 = findViewById(R.id.player2Button);
        ImageView button3 = findViewById(R.id.player3Button);
        ImageView button4 = findViewById(R.id.player4Button);

        RecyclerView rv = findViewById(R.id.namesRecycler);
        PlayerCardAdapter adapter = new PlayerCardAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTextFields(PLAYER_2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTextFields(PLAYER_3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTextFields(PLAYER_4);
            }
        });

        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout lrLayout = findViewById(R.id.namesLayout);
                int numTextViews = lrLayout.getChildCount();

                ArrayList<String> names = new ArrayList<>();

                for (int i = 2; i < numTextViews; i++) {
                    if (lrLayout.getChildAt(i) instanceof EditText) {
                        names.add(((EditText) lrLayout.getChildAt(i)).getText().toString());
                    }
                }

                Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                gameIntent.putExtra("numPlayers", clickedButton);
                gameIntent.putExtra("namePlayers", names.toArray(new String[names.size()]));
                startActivity(gameIntent);

            }
        });
    }


        public void createTextFields(int numPlayers)
        {
            LinearLayout lrLayout = findViewById(R.id.namesLayout);

            for(int i = 1; i <= numPlayers; i++)
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
