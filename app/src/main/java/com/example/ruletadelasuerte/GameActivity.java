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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import org.w3c.dom.Text;

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
    private boolean resolver = false;
    private boolean fieldColored = false;
    private TextView fieldColoredView;
    private RouletteFragment fragment;

    Random rand = new Random();

    Animation bounceAnim;

    private ArrayList<String> lettersSaid = new ArrayList<>();
    private ArrayList<ImageView> playersAvatars = new ArrayList<>();
    private ArrayList<TextView> playersNames = new ArrayList<>();
    private ArrayList<TextView> playersPoints = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        // Gets info from last Activity
        Intent intent = getIntent();

        this.numPlayers = intent.getIntExtra("numPlayers", numPlayers);
        this.namePlayers = intent.getStringArrayExtra("namePlayers");

        //
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer1));
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer2));
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer3));
        playersAvatars.add((ImageView) findViewById(R.id.iconPlayer4));

        // Sets all the textviews for the players data
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

        // Sets breath animation to first player's icon
        ImageView currentAvatar = playersAvatars.get(currentPlayer - 1);

        this.setAnimation(currentAvatar);

        // Sets RouletteKeyboardAdapter to ViewPager
        viewPager = findViewById(R.id.roulette_keyboard);
        this.adapter = new RouletteKeyboardAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        this.fragment = (RouletteFragment) this.adapter.getItem(0);
        this.fragment.setSpinning(false);


        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        // Establish randomly the sentence to be guessed
        String[] array = getResources().getStringArray(R.array.frases);
        this.frase = array[rand.nextInt(array.length)];

        String[] fraseCortada = splitFrase();

        for(String i : fraseCortada)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(rowParams);

            for (char c : i.toCharArray())
            {
                TextView text = new TextView(this);
                text.setLayoutParams(rowParams);
                text.setPadding(6, 8, 6, 8);
                text.setTextSize(26f);
                text.setTextColor(Color.WHITE);
                text.setWidth(90);
                text.setHeight(130);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                {
                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                text.setText((c + "").toUpperCase());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        if (text.getText().toString().equals(" "))
                        {
                            text.setVisibility(View.INVISIBLE);
                        } else
                        {
                            text.setBackground(getDrawable(R.drawable.text_view_white));
                        }
                    }
                }

                tableRow.addView(text);
            }
            ((TableLayout) findViewById(R.id.panel)).addView(tableRow);
        }
    }


    private String[] splitFrase()
    {
        ArrayList<String> sol = new ArrayList<>();

        String[] palabras = this.frase.split(" ");
        String filaActual = "";

        for (String palabra : palabras)
        {
            if (filaActual.length()+palabra.length()+1 < 12)
            {
               filaActual += palabra + " ";
            }else
            {
                sol.add(filaActual);
                filaActual = palabra + " ";
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


    private void setNames()
    {
        for(int i = 0; i < numPlayers; i++)
        {
            playersNames.get(i).setText(namePlayers[i]);
            playersPoints.get(i).setText("0");
        }
    }


    private void setAvatars()
    {
        ArrayList<Integer> imagesAvatars = new ArrayList<>();

        imagesAvatars.add(R.drawable.ic_avatar_bota);
        imagesAvatars.add(R.drawable.ic_avatar_carretilla);
        imagesAvatars.add(R.drawable.ic_avatar_dedal);
        imagesAvatars.add(R.drawable.ic_avatar_perro);
        imagesAvatars.add(R.drawable.ic_avatar_plancha);
        imagesAvatars.add(R.drawable.ic_avatar_sombrero);

        for (int i = 0; i < numPlayers; i++)
        {
            int random = rand.nextInt(imagesAvatars.size());
            playersAvatars.get(i).setImageResource(imagesAvatars.get(random));
            //playersAvatars.remove(random);
        }
    }


    private void setAnimation(ImageView avatar)
    {
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5, 900);
        bounceAnim.setInterpolator(interpolator);
        avatar.startAnimation(bounceAnim);
    }


    @Override
    public void onFragmentInteraction(String result)
    {
        if (result.toLowerCase().equals("quiebra"))
        {
            Toast.makeText(getApplicationContext(), "QUIEBRAAAAA", Toast.LENGTH_SHORT).show();

            this.playersPoints.get(this.currentPlayer - 1).setText("0");

            this.passTurn();

        } else if (result.toLowerCase().equals("turno"))
        {
            Toast.makeText(getApplicationContext(), "PIERDES EL TURNO", Toast.LENGTH_SHORT).show();

            this.passTurn();
        } else
        {
            this.currentPoints = Integer.parseInt(result);
        }

    }

    @Override
    public void onKeyboardInteraction(String result)
    {

        int currentPlayerPoints = Integer.parseInt(playersPoints.get(this.currentPlayer - 1).getText().toString());
        ArrayList<TextView> matches = new ArrayList<>();

        TableLayout panel = findViewById(R.id.panel);

        if (fragment.getSpinning() && !this.resolver)
        {
            for (int i = 0; i < panel.getChildCount(); i++)
            {
                TableRow row = (TableRow) panel.getChildAt(i);

                for (int j = 0; j < row.getChildCount(); j++)
                {
                    TextView letra = (TextView)row.getChildAt(j);
                    if(letra.getText().toString().toUpperCase().equals(result.toUpperCase()))
                    {
                        matches.add(letra);


                        letra.setEnabled(false);
                        //currentPlayerPoints += this.currentPoints;
                    }
                }
            }
            if (Pattern.matches("[AEIOU]", result))
            {
                if (currentPlayerPoints < 30 * matches.size())
                {
                    Toast.makeText(this, "No tienes puntos suficientes", Toast.LENGTH_SHORT).show();
                } else if(!this.lettersSaid.contains(result))
                {
                    this.lettersSaid.add(result);
                    if(matches.size()!=0)
                    {
                        Toast.makeText(getApplicationContext(), "Hay " + result, Toast.LENGTH_SHORT).show();
                    }
                    for (TextView t : matches)
                    {
                        t.setTextColor(Color.BLACK);
                    }
                    checkVictory();
                    currentPlayerPoints -= 30 * matches.size();
                    this.playersPoints.get(this.currentPlayer - 1).setText(currentPlayerPoints + "");
                }else if(this.lettersSaid.contains(result))
                {
                    Toast.makeText(this, "Ya se ha dicho la letra " + result, Toast.LENGTH_SHORT).show();
                }
                passTurn();
            } else if (result.toUpperCase().equals("RESOLVER"))
            {
                this.resolver = true;
                selectNextEmptyLetter();
            } else
            {
                if(!this.lettersSaid.contains(result))
                {
                    if(matches.size()!=0)
                    {
                        Toast.makeText(getApplicationContext(), "Hay " + result, Toast.LENGTH_SHORT).show();
                    }
                    for (TextView t : matches)
                    {
                        t.setTextColor(Color.BLACK);
                    }
                    checkVictory();
                    this.lettersSaid.add(result);
                    currentPlayerPoints += currentPoints * matches.size();
                    this.playersPoints.get(this.currentPlayer - 1).setText(currentPlayerPoints + "");
                }else
                {
                    Toast.makeText(this, "Ya se ha dicho la letra " + result, Toast.LENGTH_SHORT).show();
                }
                passTurn();
            }
        }else if(resolver)
        {
            if(!this.fieldColored)
            {
                selectNextEmptyLetter();
            }else if(Pattern.matches("[A-Z]", result.toUpperCase()))
            {
                if(this.fieldColoredView.getText().equals(result))
                {
                    this.fieldColoredView.setTextColor(Color.BLACK);
                    this.fieldColoredView.setBackgroundColor(Color.WHITE);
                    selectNextEmptyLetter();
                }else
                {

                    new androidx.appcompat.app.AlertDialog.Builder(this)
                            .setTitle("Letra erronea")
                            .setCancelable(true)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    fieldColoredView.setTextColor(Color.WHITE);
                                    fieldColoredView.setBackgroundColor(Color.WHITE);
                                    fieldColored = false;
                                    resolver = false;
                                }
                            })
                            .create().show();
                }
            }
        }
    }

    private void checkVictory()
    {
        TableLayout panel = (TableLayout) findViewById(R.id.panel);
        boolean founded = false;

        for(int i = 0; i < panel.getChildCount(); i++)
        {
            TableRow row = (TableRow) panel.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++)
            {
                if(((TextView)row.getChildAt(j)).getCurrentTextColor() == Color.WHITE
                        && !((TextView)row.getChildAt(j)).getText().equals(" "))
                {
                    founded = true;
                    break;
                }
            }
            if(founded)
            {
                break;
            }
        }
        if(!founded)
        {
            String winningPlayer = this.playersNames.get(this.currentPlayer-1).getText().toString();
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Has ganado " + winningPlayer)
                    .setCancelable(true)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), PlayersMenuActivity.class);
                            startActivity(intent);
                        }
                    })
                    .create().show();
        }

    }

    private void selectNextEmptyLetter()
    {
        TableLayout panel = (TableLayout) findViewById(R.id.panel);
        boolean founded = false;

        for(int i = 0; i < panel.getChildCount(); i++)
        {
            TableRow row = (TableRow) panel.getChildAt(i);
            for(int j = 0; j < row.getChildCount(); j++)
            {
                if(((TextView)row.getChildAt(j)).getCurrentTextColor() == Color.WHITE
                        && !((TextView)row.getChildAt(j)).getText().equals(" "))
                {
                    ((TextView)row.getChildAt(j)).setBackgroundColor(Color.YELLOW);
                    ((TextView)row.getChildAt(j)).setTextColor(Color.YELLOW);
                    founded = true;
                    this.fieldColored = true;
                    this.fieldColoredView = ((TextView)row.getChildAt(j));
                    break;
                }
            }
            if(founded)
            {
               break;
            }
        }
        if(!founded)
        {
            String winningPlayer = this.playersNames.get(this.currentPlayer-1).getText().toString();
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Has ganado " + winningPlayer)
                    .setCancelable(true)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getApplicationContext(), PlayersMenuActivity.class);
                            startActivity(intent);
                        }
                    })
                    .create().show();
        }
    }

    private void passTurn()
    {
        RouletteFragment fragment = (RouletteFragment) this.adapter.getItem(0);
        playersAvatars.get(this.currentPlayer - 1).clearAnimation();
        this.currentPlayer = (this.currentPlayer % this.numPlayers) + 1;
        this.setAnimation(playersAvatars.get(this.currentPlayer - 1));
        this.fragment.setSpinning(false);
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
