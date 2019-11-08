package com.example.ruletadelasuerte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PlayersMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_menu);

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

    }
}
