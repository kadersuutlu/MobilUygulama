package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.market.cerceve.HomeFragment;

public class BeginningActivity extends AppCompatActivity {

    ImageView imageView;
    Animation animation;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginning);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.logo);
        handler=new Handler();

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        imageView.startAnimation(animation);

        Runnable runnable=new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(BeginningActivity.this,HomePageActivity.class);
                startActivity(intent);

            }
        };
        handler.postDelayed(runnable,3000);

    }
}