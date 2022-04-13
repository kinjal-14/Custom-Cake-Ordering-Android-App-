package com.example.cakedream;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cakedream.pre_made_Adapter.utility;

import java.util.UUID;

public class customizeCreate extends FragmentActivity {
    TextView type, shape, size, flavour, design;
    ImageView home_btn, cart_btn;
    AppCompatButton continue_btn;
    private  int value = 1;
    String uniqueId;
    UUID uuid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_customize_create);

        type = findViewById(R.id.type);
        shape = findViewById(R.id.shape);
        size = findViewById(R.id.size);
        flavour = findViewById(R.id.flavour);
        design = findViewById(R.id.design);


        continue_btn = findViewById(R.id.continue_btn);


        home_btn = findViewById(R.id.home1);
        cart_btn = findViewById(R.id.cart_btn);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (value){
                    case 1:
                        replaceFragment(new customizeType());
                        break;
                    case 2:
                        replaceFragment(new customizeShape());
                        break;
                    case 3:
                        replaceFragment(new customizeSize());
                        break;
                    case 4:
                        replaceFragment(new customizeFlavour());
                        break;
                    case 5:
                        replaceFragment(new customizeDesign());
                        break;
                    case  6:
                        customizeDetails();
                        break;


                }
                value++;
                if(value>6){
                    value=1;
                }



            }

            private void customizeDetails() {
                String uniqueId = UUID.randomUUID().toString();
                value = 6;
                Intent intent = new Intent(customizeCreate.this,customizeDetails.class);
                intent.putExtra("uniqueid",uniqueId);
                intent.putExtra("caketype", utility.type);
                intent.putExtra("cakesize", utility.size);
                intent.putExtra("cakeshape", utility.shape);
                intent.putExtra("cakeflavour", utility.flavour);
                intent.putExtra("cakedesign", utility.design);
                startActivity(intent);


            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(customizeCreate.this,pre_made_base.class);
                startActivity(intent);
            }
        });

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeType());
                value=1;
            }
        });
        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeShape());
                value=2;
            }
        });
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeSize());
                value=3;
            }
        });
        flavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeFlavour());
                value=4;
            }
        });
        design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new customizeDesign());
                value=5;
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_to_left_anim,R.anim.back_right_to_left_anim,
                R.anim.left_to_right_anim,R.anim.back_left_to_right_anim);
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

}