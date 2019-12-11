package com.example.ruletadelasuerte;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class RouletteKeyboardAdapter extends FragmentStatePagerAdapter {

    public RouletteKeyboardAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new RouletteFragment();
            case 1: return new KeyboardFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
