package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class GameActivity extends AppCompatActivity {

    private ImageView ruleta;
    private int numPlayers = 0;
    private String[] namePlayers;
    private float lastRDiff = 0;
    private String frase = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();

        this.numPlayers = Integer.valueOf(intent.getStringExtra("numPlayers"));
        this.namePlayers = intent.getStringArrayExtra("namePlayers");

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


}
