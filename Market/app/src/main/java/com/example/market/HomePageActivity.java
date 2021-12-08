package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.market.Model.Category;
import com.example.market.cerceve.HomeFragment;
import com.example.market.cerceve.ProfileFragment;
import com.example.market.cerceve.SearchFragment;
import com.example.market.cerceve.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class HomePageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Fragment selectItem=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);




        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NotNull MenuItem menuItem){

                    switch (menuItem.getItemId())
                    {
                        case R.id.nav_home:
                            selectItem=new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectItem=new SearchFragment();
                            break;
                        case R.id.nav_shoppingcart:
                            selectItem=new ShoppingCartFragment();
                            break;
                        case R.id.nav_profile:
                            selectItem=new ProfileFragment();
                            break;

                    }

                    if(selectItem!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici,selectItem).commit();
                    }

                    return false;
                }
            };

      public void CardView(){
         Intent intent=new Intent(HomePageActivity.this,LoginActivity.class);
         startActivity(intent);
     }

}