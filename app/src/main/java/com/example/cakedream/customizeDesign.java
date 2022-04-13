package com.example.cakedream;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cakedream.pre_made_Adapter.utility;

public class customizeDesign extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout mLinearLayout1, mLinearLayout2, mLinearLayout3, mLinearLayout4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_customize_design, container, false);
        mLinearLayout1 = view.findViewById(R.id.l1);
        mLinearLayout2 = view.findViewById(R.id.l2);
        mLinearLayout3 = view.findViewById(R.id.l3);
        mLinearLayout4 = view.findViewById(R.id.l4);

        mLinearLayout1.setOnClickListener(this);
        mLinearLayout2.setOnClickListener(this);
        mLinearLayout3.setOnClickListener(this);
        mLinearLayout4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.l1:
                Toast.makeText(getActivity(), "Design  :    Makeup ", Toast.LENGTH_SHORT).show();
                utility.design = "Makeup";
                break;
            case R.id.l2:
                Toast.makeText(getActivity(), "Design  :    Doll ", Toast.LENGTH_SHORT).show();
                utility.design = "Doll";
                break;
            case R.id.l3:
                Toast.makeText(getActivity(), "Design  :    Swan ", Toast.LENGTH_SHORT).show();
                utility.design = "swan";
                break;
            case R.id.l4:
                Toast.makeText(getActivity(), "Design  :    Teddy ", Toast.LENGTH_SHORT).show();
                utility.design = "Teddy";
                break;

        }

    }
}