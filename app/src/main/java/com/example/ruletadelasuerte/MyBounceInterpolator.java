package com.example.ruletadelasuerte;

class MyBounceInterpolator implements android.view.animation.Interpolator
{
    private double mAmplitude = 10;
    private double mFrequency = 6;

    MyBounceInterpolator(double amplitude, double frequency)
    {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time)
    {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}