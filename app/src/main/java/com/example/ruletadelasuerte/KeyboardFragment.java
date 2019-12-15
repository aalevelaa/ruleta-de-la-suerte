package com.example.ruletadelasuerte;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class KeyboardFragment extends Fragment implements View.OnClickListener
{

    private OnFragmentInteractionListener mListener;

    public KeyboardFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeyboardFragment.
     */

    public static KeyboardFragment newInstance(String param1, String param2)
    {
        KeyboardFragment fragment = new KeyboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_keyboard, container, false);
        addButtonEffect((ViewGroup) layout.findViewById(R.id.primeraFIlaConsonantes));
        addButtonEffect((ViewGroup) layout.findViewById(R.id.segundaFilaConsonantes));
        addButtonEffect((ViewGroup) layout.findViewById(R.id.filaVocales));

        return layout;
    }

    private void addButtonEffect(ViewGroup v)
    {
        for (int i = 0; i < v.getChildCount() ; i++)
        {
            View currentChild = v.getChildAt(i);

            if( currentChild instanceof Button)
            {
                currentChild.setOnClickListener(this);
            } else if (currentChild instanceof LinearLayout)
            {
                addButtonEffect((ViewGroup) currentChild);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String result)
    {
        if (mListener != null)
        {
            mListener.onKeyboardInteraction(result);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v)
    {
        Button b = (Button )v;
        String letra = (String) b.getText();

        onButtonPressed(letra);
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


    public interface OnFragmentInteractionListener
    {
        void onKeyboardInteraction(String result);
    }
}
