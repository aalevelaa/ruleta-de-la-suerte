package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements RouletteFragment.OnFragmentInteractionListener, KeyboardFragment.OnFragmentInteractionListener {

    private int numPlayers;
    private String[] namePlayers;
    private String frase = "";
    private RouletteKeyboardAdapter adapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //Gets info from last Activity
        Intent intent = getIntent();

        this.numPlayers = intent.getIntExtra("numPlayers", numPlayers);
        this.namePlayers = intent.getStringArrayExtra("namePlayers");

        setAvatars();
        setNames();

        //Sets RouletteKeyboardAdapter to ViewPager
        viewPager = findViewById(R.id.roulette_keyboard);
        this.adapter = new RouletteKeyboardAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        text.setBackground(getDrawable(R.drawable.text_view_back));
                    }
                }
                text.setText(c + "");

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


    private void setAvatars()
    {
        ArrayList<ImageView> playersAvatars = new ArrayList<>();

        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer1));
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer2));
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer3));
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer4));

        int[] avatars = {R.drawable.ic_avatar_bota,
                        R.drawable.ic_avatar_carretilla,
                        R.drawable.ic_avatar_coche,
                        R.drawable.ic_avatar_dedal,
                        R.drawable.ic_avatar_perro,
                        R.drawable.ic_avatar_plancha,
                        R.drawable.ic_avatar_sombrero
        };

        Random rand = new Random();

        for(int i = 0; i < numPlayers; i++)
        {
            playersAvatars.get(i).setImageResource(avatars[rand.nextInt(avatars.length)]);
        }
    }

    private void setNames()
    {
        ArrayList<TextView> playersNames = new ArrayList<>();

        playersNames.add((TextView) findViewById(R.id.namePlayer1));
        playersNames.add((TextView) findViewById(R.id.namePlayer2));
        playersNames.add((TextView) findViewById(R.id.namePlayer3));
        playersNames.add((TextView) findViewById(R.id.namePlayer4));

        for(int i = 0; i < numPlayers; i++)
        {
            playersNames.get(i).setText(namePlayers[i]);
        }
    }


    @Override
    public void onFragmentInteraction(String result)
    {
        TextView actual = this.findViewById(R.id.textView2);
        actual.setText(result);

    }

    @Override
    public void onKeyboardInteraction(String result)
    {
        RouletteFragment fragment = (RouletteFragment) this.adapter.getItem(0);
        fragment.setSpinning(false);
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("SALIR DE LA PARTIDA")
                .setMessage("Si vuelves perderás el progreso de la partida\n¿Estás seguro de que quieres salir?")
                .setPositiveButton("VOLVER", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("SEGUIR JUGANDO", null)
                .show();
    }
}
