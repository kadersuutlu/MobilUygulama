package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText eMail, password,nameSurname,phoneNumber;
    private TextView textView;
    private String txtPhone,txtNameSurname,txtEmail, txtPassword;
    private Button kayitOl;
    //private FirebaseAuth mAuth;

    FirebaseAuth yetki;
    DatabaseReference yol;
    //ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eMail = (EditText) findViewById(R.id.eMail);
        password = (EditText) findViewById(R.id.password);
        nameSurname = (EditText) findViewById(R.id.nameSurname);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        textView=(TextView)findViewById(R.id.login);
        kayitOl=(Button) findViewById(R.id.kayit_ol);

        yetki = FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Register();
            }
        });


    }

    public void Register( ) {
        txtPhone=phoneNumber.getText().toString();
        txtNameSurname=nameSurname.getText().toString();
        txtEmail=eMail.getText().toString();
        txtPassword=password.getText().toString();

        if(TextUtils.isEmpty(txtPhone)||TextUtils.isEmpty(txtNameSurname)||
                TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPassword)){
            Toast.makeText(RegisterActivity.this, "Lütfen bütün alanları  doldurun", Toast.LENGTH_SHORT).show();
        }
        else if(txtPassword.length()<6){
            Toast.makeText(RegisterActivity.this, "Şifreniz en az 6 karakter olmalıdır.", Toast.LENGTH_SHORT).show();
        }
        else{
            ValidatephoneNumber(txtNameSurname,txtPhone,txtEmail,txtPassword);
        }

    }

    private void ValidatephoneNumber(String txtNameSurname, String txtPhone, String txtEmail, String txtPassword) {
        final DatabaseReference RootRef;
        RootRef=FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("User").child(txtPhone).exists())){
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("NameSurname",txtNameSurname);
                    hashMap.put("EMail",txtEmail);
                    hashMap.put("Password",txtPassword);

                    RootRef.child("User").child(txtPhone).updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(RegisterActivity.this,HomePageActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "This"+txtPhone+"already exits", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Tekrar deneyin", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(RegisterActivity.this,HomePageActivity.class);
                    startActivity(intent);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}