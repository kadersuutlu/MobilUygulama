package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.market.cerceve.HomeFragment;
import com.example.market.cerceve.ProfileFragment;
import com.example.market.cerceve.SearchFragment;
import com.example.market.cerceve.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity {

    private TextView profile,address,creditCard,logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*profile=(TextView) findViewById(R.id.profile);
        address=(TextView) findViewById(R.id.address);
        creditCard=(TextView) findViewById(R.id.creditCard);
        logOut=(TextView) findViewById(R.id.logOut);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(R.id.profile)){
                    startActivity(new Intent(ProfileActivity.this,PersonalActivity.class));
                }
            }
        });*/







    }



}