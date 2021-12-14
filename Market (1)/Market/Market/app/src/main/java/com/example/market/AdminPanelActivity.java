package com.example.market;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminPanelActivity extends AppCompatActivity {

    Button btn_kategori_ekle;

    EditText kategoriAdi;
    Button btnSec,btnYukle;

    public static final int PICK_IMAGE_REQUEST = 71;
    Uri kaydetmeUrisi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());


        btn_kategori_ekle=(Button) findViewById(R.id.btn_kategori_ekle);
        btnSec=(Button) findViewById(R.id.btnSec);
        btnYukle=(Button) findViewById(R.id.btnYukle);
        kategoriAdi=(EditText)findViewById(R.id.edtKategoriAdi);

        btn_kategori_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kategoriEklePenceresiGoster();
            }
        });

        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resimSec();
            }
        });


    }



    private void kategoriEklePenceresiGoster() {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminPanelActivity.this);
        builder.setTitle("Yeni kategori ekle");
        builder.setMessage("Lütfen bilgilerinizi yazın..");

        LayoutInflater layoutInflater=this.getLayoutInflater();
        View category_add=layoutInflater.inflate(R.layout.category_add,null);

        builder.setView(category_add);
        builder.setIcon(R.drawable.ic_action_name);

        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            kaydetmeUrisi = data.getData();
            btnSec.setText("SEÇİLDİ");
        }
    }

    private void resimSec() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Resim Seç"),PICK_IMAGE_REQUEST);
    }
}