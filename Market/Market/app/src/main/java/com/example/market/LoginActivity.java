package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.market.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText phone, password;
    private TextView txt,admin,notAdmin;
    private String txtPhone, txtPassword;
    private Button girisYap;

    private  String parentDbName="User";

    private CheckBox rememberMe;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        txt=(TextView)findViewById(R.id.register);
        girisYap=(Button)findViewById(R.id.giris_yap);
        admin=(TextView)findViewById(R.id.admin);
        notAdmin=(TextView)findViewById(R.id.notAdmin);

        rememberMe=(CheckBox)findViewById(R.id.rememberMe );

        mAuth=FirebaseAuth.getInstance();


        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentLogin);
            }
        });


        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

    }

    private void LoginUser() {
        txtPhone=phone.getText().toString();
        txtPassword=password.getText().toString();

        if(TextUtils.isEmpty(txtPhone)||TextUtils.isEmpty(txtPassword)){
            Toast.makeText(LoginActivity.this, "Lütfen bütün alanları  doldurun", Toast.LENGTH_SHORT).show();
        }
        else{
            AllowAccessToAccount(txtPhone,txtPassword);
        }

    }

    private void AllowAccessToAccount(String txtPhone, String txtPassword) {
        final DatabaseReference RootRef;
        RootRef=FirebaseDatabase.getInstance().getReference();

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(txtPhone).exists()){
                    User userData=snapshot.child(parentDbName).child(txtPhone).getValue(User.class);

                    if(userData.getUserPhone().equals(txtPhone)){
                        if(userData.getUserPassword().equals(txtPassword)){
                            Toast.makeText(LoginActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login"+txtPhone+"number", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Create a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}