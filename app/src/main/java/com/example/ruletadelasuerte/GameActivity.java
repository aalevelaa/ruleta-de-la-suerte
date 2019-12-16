package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

public class GameActivity extends AppCompatActivity implements RouletteFragment.OnFragmentInteractionListener, KeyboardFragment.OnFragmentInteractionListener {

    private int numPlayers;
    private String[] namePlayers;
    private String frase = "";
    private RouletteKeyboardAdapter adapter;
    private ViewPager viewPager;
    private int currentPlayer = 1;
    private int currentPoints = 0;

    private ArrayList<TextView> playersNames = new ArrayList<>();
    private ArrayList<TextView> playersPoints = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        //Gets info from last Activity
        Intent intent = getIntent();

        this.numPlayers = intent.getIntExtra("numPlayers", numPlayers);
        this.namePlayers = intent.getStringArrayExtra("namePlayers");

        //Sets all the text vies for the players data
        playersNames.add((TextView) findViewById(R.id.namePlayer1));
        playersNames.add((TextView) findViewById(R.id.namePlayer2));
        playersNames.add((TextView) findViewById(R.id.namePlayer3));
        playersNames.add((TextView) findViewById(R.id.namePlayer4));
        playersPoints.add((TextView) findViewById(R.id.pointsPlayer1));
        playersPoints.add((TextView) findViewById(R.id.pointsPlayer2));
        playersPoints.add((TextView) findViewById(R.id.pointsPlayer3));
        playersPoints.add((TextView) findViewById(R.id.pointsPlayer4));

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
                text.setTextColor(Color.WHITE);
                text.setWidth(90);
                text.setHeight(130);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                text.setText(c + "");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if(text.getText().toString().equals(" "))
                        {
                            text.setBackground(getDrawable(R.drawable.text_view_back));
                        }else
                        {
                            text.setBackground(getDrawable(R.drawable.text_view_white));
                        }
                    }
                }

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
        for(int i = 0; i < numPlayers; i++)
        {
            playersNames.get(i).setText(namePlayers[i]);
            playersPoints.get(i).setText("0");
        }
    }


    @Override
    public void onFragmentInteraction(String result)
    {
        RouletteFragment fragment = (RouletteFragment) this.adapter.getItem(0);
        if(result.toLowerCase().equals("quiebra"))
        {
            fragment.setSpinning(false);
            passTurn();
            this.playersPoints.get(this.currentPlayer-1).setText("0");
        }else if(result.toLowerCase().equals("turno"))
        {
            fragment.setSpinning(false);
            passTurn();
        }else
        {
            this.currentPoints = Integer.parseInt(result);
        }

    }

    @Override
    public void onKeyboardInteraction(String result)
    {
        RouletteFragment fragment = (RouletteFragment) this.adapter.getItem(0);

        int currentPlayerPoints = Integer.parseInt(playersPoints.get(this.currentPlayer - 1).getText().toString());
        ArrayList<TextView> matches = new ArrayList<TextView>();

        TableLayout panel = findViewById(R.id.panel);

        if(fragment.getSpinning())
        {
            for(int i = 0; i < panel.getChildCount(); i++)
            {
                TableRow row = (TableRow) panel.getChildAt(i);

                for (int j = 0; j < row.getChildCount(); j++)
                {
                    TextView letra = (TextView)row.getChildAt(j);
                    if(letra.getText().toString().toLowerCase().equals(result.toLowerCase()))
                    {
                        matches.add(letra);
                        //letra.setTextColor(Color.BLACK);
                        //currentPlayerPoints += this.currentPoints;
                    }
                }
            }
            if(Pattern.matches("[AEIOU]", result))
            {
                if(currentPlayerPoints < this.currentPoints*matches.size())
                {
                    Toast.makeText(this, "No tienes puntos suficientes", Toast.LENGTH_SHORT).show();
                    passTurn();
                }else
                {
                    for(TextView t:matches)
                    {
                        t.setTextColor(Color.BLACK);
                    }
                    currentPlayerPoints -= 30*matches.size();
                    this.playersPoints.get(this.currentPlayer - 1).setText(currentPlayerPoints+"");
                    passTurn();
                }
            }else if(result.toUpperCase().equals("RESOLVER"))
            {

            }else
            {
                for(TextView t:matches)
                {
                    t.setTextColor(Color.BLACK);
                }
                currentPlayerPoints += currentPoints*matches.size();
                this.playersPoints.get(this.currentPlayer - 1).setText(currentPlayerPoints+"");
                passTurn();
            }
        }
        fragment.setSpinning(false);
    }

    private void passTurn()
    {
        this.currentPlayer = (this.currentPlayer%this.numPlayers) + 1;
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
