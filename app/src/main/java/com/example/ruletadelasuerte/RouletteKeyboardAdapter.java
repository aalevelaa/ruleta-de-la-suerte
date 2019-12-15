package com.example.ruletadelasuerte;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class RouletteKeyboardAdapter extends FragmentStatePagerAdapter {

    private RouletteFragment roulette;
    private KeyboardFragment keyboard;

    public RouletteKeyboardAdapter(FragmentManager fm){
        super(fm);
        this.roulette = new RouletteFragment();
        this.keyboard = new KeyboardFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return this.roulette;
            case 1: return this.keyboard;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
