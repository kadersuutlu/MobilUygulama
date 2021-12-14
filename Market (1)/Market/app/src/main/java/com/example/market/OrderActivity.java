package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class OrderActivity extends AppCompatActivity {

    Button button;
    ImageView imageView;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        button=(Button) findViewById(R.id.button_home);
        imageView=(ImageView) findViewById(R.id.success);
        imageView.setImageResource(R.drawable.order);


        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.order_animate);
        imageView.startAnimation(animation);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}