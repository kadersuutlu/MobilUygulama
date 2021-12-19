package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText eMail, password;
    private TextView txt, admin;
    private String txtEmail, txtPassword;
    private Button girisYap;

    private String parentDbName = "User";
    int intenCount = 0;
    FirebaseAuth mAuth;
    private FirebaseUser currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        eMail = (EditText) findViewById(R.id.eMail);
        password = (EditText) findViewById(R.id.password);
        txt = (TextView) findViewById(R.id.register);
        girisYap = (Button) findViewById(R.id.giris_yap);
        admin = (TextView) findViewById(R.id.admin);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (!(currentUser == null)) {
            currentUser.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(LoginActivity.this, "Succesfully Logged In", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            currentUser.reload().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof FirebaseAuthInvalidUserException) {
                        Log.d("MainActivity", "user doesn't exist anymore");

                    }
                }
            });
        }
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intentLogin);
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentLogin);
            }
        });


        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmail = eMail.getText().toString();
                txtPassword = password.getText().toString();

                if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(LoginActivity.this, "Lütfen bütün alanları doldurun", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference yolGiris = FirebaseDatabase.getInstance().getReference()
                                                .child("User").child(mAuth.getCurrentUser().getUid());
                                        yolGiris.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                //pd.dismiss();
                                                if (intenCount == 0) {
                                                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                    intenCount++;
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                //pd.dismiss();
                                            }
                                        });
                                    } else {
                                        //pd.dismiss();
                                        Toast.makeText(LoginActivity.this, "Giriş başarısız", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}