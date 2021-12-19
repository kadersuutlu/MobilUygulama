package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.market.cerceve.HomeFragment;
import com.example.market.cerceve.ProfileFragment;
import com.example.market.cerceve.SearchFragment;
import com.example.market.cerceve.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity {

    Button button;
    EditText name,phone,eMAil;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


    button=(Button) findViewById(R.id.profile);
    name=(EditText) findViewById(R.id.nameSurname);
    phone=(EditText) findViewById(R.id.phoneNumber);
    eMAil=(EditText) findViewById(R.id.eMail);










    }



}