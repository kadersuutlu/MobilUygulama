package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    FirebaseAuth yetki;
    DatabaseReference yol;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        eMail = (EditText) findViewById(R.id.eMail);
        password = (EditText) findViewById(R.id.password);
        nameSurname = (EditText) findViewById(R.id.nameSurname);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        textView = (TextView) findViewById(R.id.login);
        kayitOl = (Button) findViewById(R.id.kayit_ol);

        yetki = FirebaseAuth.getInstance();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtPhone = phoneNumber.getText().toString();
                txtNameSurname = nameSurname.getText().toString();
                txtEmail = eMail.getText().toString();
                txtPassword = password.getText().toString();

                if (TextUtils.isEmpty(txtPhone) || TextUtils.isEmpty(txtNameSurname) ||
                        TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(RegisterActivity.this, "Lütfen bütün alanları  doldurun", Toast.LENGTH_SHORT).show();
                } else if (txtPassword.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Şifreniz en az 6 karakter olmalıdır.", Toast.LENGTH_SHORT).show();
                } else {
                    yetki.createUserWithEmailAndPassword(txtEmail, txtPassword)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = yetki.getCurrentUser();

                                        //txtPhone=firebaseUser.getUid();


                                        yol = FirebaseDatabase.getInstance().getReference()
                                                .child("User").child(txtPhone);
                                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                        //hashMap.put("id",userId);
                                        hashMap.put("NameSurname", txtNameSurname);
                                        //hashMap.put("PhoneNumber",txtPhone);
                                        hashMap.put("EMail", txtEmail);
                                        hashMap.put("Password", txtPassword);

                                        yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //pd.dismiss();
                                                    Intent intent = new Intent(RegisterActivity.this, HomePageActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                    } else {
                                        //pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Telefon numaranız ya da e-posta adresiniz ile kayıt oluşturulamadı.",
                                                Toast.LENGTH_LONG).show();
                                    }


                                }
                            });
                }

            }
        });
    }

}