package com.example.ruletadelasuerte;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Random;

public class RouletteKeyboardAdapter extends FragmentStatePagerAdapter {

    public RouletteKeyboardAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new RouletteFragment();
            case 1: return new RouletteFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
