package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    TextView textView=(TextView) findViewById(R.id.text3);
     //   TextView textView2=(TextView) findViewById(R.id.text4);

    }

    public void loginTiklama(View view) {
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void OrderTiklama(View view) {
        Intent intent=new Intent(MainActivity.this,OrderActivity.class);
        startActivity(intent);
    }
}