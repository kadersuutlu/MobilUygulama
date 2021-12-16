package com.example.market;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.market.Model.Category;
import com.example.market.ViewHolder.CategoryViewHolder;
import com.example.market.cerceve.HomeFragment;
import com.example.market.cerceve.ProfileFragment;
import com.example.market.cerceve.SearchFragment;
import com.example.market.cerceve.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    Fragment selectItem = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici, homeFragment).commit();


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.cerceve_kapsayici, homeFragment).commit();
                return true;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.cerceve_kapsayici, searchFragment).commit();
                return true;
            case R.id.nav_shoppingcart:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.cerceve_kapsayici, shoppingCartFragment).commit();
                return true;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.cerceve_kapsayici, profileFragment).commit();
                return true;
        }
        return false;
    }

    public void Tikla(View v) {
        profileFragment.Tikla(v);
    }

    public void Address(View v) {
        profileFragment.Address(v);
    }

    public void CreditCard(View v) {
        profileFragment.CreditCard(v);
    }

    public void LogOut(View v) {
        profileFragment.LogOut(v);
    }


    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }
}

