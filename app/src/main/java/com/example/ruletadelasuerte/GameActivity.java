package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements RouletteFragment.OnFragmentInteractionListener {

    private ImageView ruleta;
    private int numPlayers;
    private String[] namePlayers;
    private float lastRDiff = 0;
    private String frase = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //Gets info from last Activity
        Intent intent = getIntent();

        this.numPlayers = intent.getIntExtra("numPlayers", numPlayers);
        this.namePlayers = intent.getStringArrayExtra("namePlayers");

        setAvatars(numPlayers);

        //Sets RouletteKeyboardAdapter to ViewPager
        ViewPager v = findViewById(R.id.roulette_keyboard);
        RouletteKeyboardAdapter adapter = new RouletteKeyboardAdapter(getSupportFragmentManager());
        v.setAdapter(adapter);


        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        this.frase = getResources().getStringArray(R.array.frases)[0];

        String[] fraseCortada = splitFrase();

        for(String i : fraseCortada)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(rowParams);

            for (char c : i.toCharArray())
            {
                TextView text = new TextView(this);
                text.setLayoutParams(rowParams);
                text.setPadding(10, 10, 10, 0);
                text.setTextSize(26f);
                text.setWidth(90);
                text.setHeight(130);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                text.setBackground(getDrawable(R.drawable.text_view_back));
                text.setText(c+"");

                tableRow.addView(text);
            }
            ((TableLayout)findViewById(R.id.panel)).addView(tableRow);
        }

    }


    private String[] splitFrase()
    {
        ArrayList<String> sol = new ArrayList<>();

        String[] palabras = this.frase.split(" ");
        String filaActual = "";

        for (String palabra : palabras)
        {
            if (filaActual.length()+palabra.length()+1 < 15)
            {
               filaActual += palabra + " ";
            }else
            {
                sol.add(filaActual);
                filaActual = "";
            }
        }

        if (filaActual.length() > 0)
        {
            sol.add(filaActual);
        }

        String lastStr = sol.get(sol.size()-1);

        sol.set(sol.size()-1, lastStr.substring(0, lastStr.length()-1));


        return sol.toArray(new String[sol.size()]);
    }


    private void setAvatars(int numPlayers)
    {
        ImageView player1 = findViewById(R.id.iconPlayer1);
        ImageView player2 = findViewById(R.id.iconPlayer2);
        ImageView player3 = findViewById(R.id.iconPlayer3);
        ImageView player4 = findViewById(R.id.iconPlayer4);

        int[] avatars = {R.drawable.ic_avatar_bota,
                        R.drawable.ic_avatar_carretilla,
                        R.drawable.ic_avatar_coche,
                        R.drawable.ic_avatar_dedal,
                        R.drawable.ic_avatar_perro,
                        R.drawable.ic_avatar_plancha,
                        R.drawable.ic_avatar_sombrero
        };

        Random rand = new Random();
        player1.setImageResource(avatars[rand.nextInt(avatars.length)]);
        player2.setImageResource(avatars[rand.nextInt(avatars.length)]);
        player3.setImageResource(avatars[rand.nextInt(avatars.length)]);
        player4.setImageResource(avatars[rand.nextInt(avatars.length)]);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
