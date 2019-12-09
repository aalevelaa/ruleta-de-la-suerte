package com.example.ruletadelasuerte;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerCardAdapter extends RecyclerView.Adapter<PlayerCardAdapter.MyViewHolder>
{

    private Activity PlayersMenuActivity;
    private LayoutInflater inflater;
    private static int numHolder = 1;

    public PlayerCardAdapter(Context c)
    {
        this.PlayersMenuActivity = (Activity)c;
        this.inflater = PlayersMenuActivity.getLayoutInflater();
    }

    @NonNull
    @Override
    public PlayerCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = null;

        try {
            v = this.inflater.inflate(R.layout.player_name_layout, parent, false);
        }
        catch (Exception e)
        {
            Log.i("Informacion",e.getMessage().toString());
        }
        numHolder++;
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerCardAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
