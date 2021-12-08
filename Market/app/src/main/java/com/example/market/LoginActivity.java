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

    private EditText eMail, password;
    private TextView txt;
    private String txtEmail, txtPassword;
    private Button girisYap;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eMail = (EditText) findViewById(R.id.eMail);
        password = (EditText) findViewById(R.id.password);
        txt=(TextView)findViewById(R.id.register);
        girisYap=(Button)findViewById(R.id.giris_yap);

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
                //ProgressDialog pd=new ProgressDialog(LoginActivity.this);
                //pd.setMessage("Giriş yapılıyor");
                //pd.show();

                txtEmail=eMail.getText().toString();
                txtPassword=password.getText().toString();

                if(TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(LoginActivity.this, "Lütfen bütün alanları doldurun", Toast.LENGTH_LONG).show();
                }
                else {
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

                                                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
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

    /*public void Login(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    /*public void girisYap(View view) {
        txtEmail=eMail.getText().toString();
        txtPassword=password.getText().toString();

        if (!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPassword)) {
            mAuth.signInWithEmailAndPassword(txtEmail,txtPassword)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser=mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Başarıyla giriş yapıldı", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,HomePageActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(this, "E-posta ve şifre alanları boş olamaz", Toast.LENGTH_LONG).show();
        }
    }

    public void register(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }*/
}