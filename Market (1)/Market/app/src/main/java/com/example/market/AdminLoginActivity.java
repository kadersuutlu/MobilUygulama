package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {

    Button girisYap;
    EditText userName,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        girisYap=(Button) findViewById(R.id.girisYap);
        userName=(EditText) findViewById(R.id.kullanici_adi);
        password=(EditText) findViewById(R.id.Password);

        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("Admin") && password.getText().toString().equals("admin")){
                    Intent intent=new Intent(AdminLoginActivity.this,AdminPanelActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(AdminLoginActivity.this, "Giriş yapılamadı. Bilgileri kontrol edin.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}