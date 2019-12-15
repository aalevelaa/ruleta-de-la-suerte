package com.example.ruletadelasuerte;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RouletteFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String result = "";
    private boolean spinning = false;

    public RouletteFragment() {
        // Required empty public constructor
    }

    public void setSpinning(boolean spinning)
    {
       this.spinning = spinning;
    }

    public boolean getSpinning()
    {
        return this.spinning;
    }

    public static RouletteFragment newInstance() {
        RouletteFragment fragment = new RouletteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roulette, container, false);
        view.findViewById(R.id.mainRoulette).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               spinRoulette(v);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void spinRoulette(View v)
    {
        if(!this.spinning)
        {
            float r = (float) new Random().nextInt(360);

            final RotateAnimation animRotate = new RotateAnimation(0f, -r+5-360,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            animRotate.setDuration(3000);
            animRotate.setFillAfter(true);

            v.startAnimation(animRotate);

            String[] casillas = getResources().getStringArray(R.array.casillas);

            Log.i("grados", r+"");

            this.result = (casillas[(int) (Math.ceil(r/15)-1)]);
            onButtonPressed(this.result);
            this.spinning = true;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String result);
    }
}
