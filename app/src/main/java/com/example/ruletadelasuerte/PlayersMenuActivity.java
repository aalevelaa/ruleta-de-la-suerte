package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        ImageView button2 = findViewById(R.id.player2Button);
        ImageView button3 = findViewById(R.id.player3Button);
        ImageView button4 = findViewById(R.id.player4Button);

        final RecyclerView rv = findViewById(R.id.namesRecycler);
        final PlayerCardAdapter adapter = new PlayerCardAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeNumCards(PLAYER_2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeNumCards(PLAYER_3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeNumCards(PLAYER_4);
            }
        });

        playButton = findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<String> names = new ArrayList<>();
                boolean emptyName = false;

                for (int i = 0; i < adapter.getItemCount(); i++) {
                    CardView nameView = (CardView)rv.getLayoutManager().findViewByPosition(i);
                    if (nameView.findViewById(R.id.editTextName) instanceof EditText) {
                        String name = ((EditText) nameView.findViewById(R.id.editTextName)).getText().toString();
                        if (name.length()==0)
                        {
                            Toast.makeText(getApplicationContext(), "Los nombres no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                            emptyName = true;

                        } else
                        {
                            names.add(name);
                        }
                    }
                }

                if (!emptyName)
                {
                    Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                    gameIntent.putExtra("numPlayers", adapter.getItemCount());
                    gameIntent.putExtra("namePlayers", names.toArray(new String[names.size()]));
                    startActivity(gameIntent);
                }
            }
        });
    }
}
