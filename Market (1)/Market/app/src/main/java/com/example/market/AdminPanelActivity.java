package com.example.market;

import static com.example.market.R.id.recycler_kategori;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.market.Interface.ItemClickListener;
import com.example.market.Model.Category;
import com.example.market.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminPanelActivity extends AppCompatActivity {

    Button btn_kategori_ekle;

    EditText kategoriAdi;
    Button btnSec, btnYukle;

    public static final int PICK_IMAGE_REQUEST = 71;
    Uri kaydetmeUrisi;

    ActivityResultLauncher<Intent> imageActivityResultLauncher;

    //Firebase tanımlamaları
    FirebaseDatabase database;
    DatabaseReference kategoriYolu;
    FirebaseStorage storage;
    StorageReference resimYolu;

    //Category Firebase Reference
    private DatabaseReference categoryYukle, categoryCountRef;
    private ValueEventListener categoryYukleListener, categoryCountRefListener;

    //Category RecyclerView
    private RecyclerView recyclerView;
    CategoryViewHolder recyclerAdapter;
    List<Category> liste = new ArrayList<>();
    int categoryCount = 0;

    private List<Category> yeniKategori = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        //Firebase tanımlamaları
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        resimYolu = storage.getReference();

        recyclerView = findViewById(R.id.recycler_kategori);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);


        btn_kategori_ekle = (Button) findViewById(R.id.btn_kategori_ekle);

        btn_kategori_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kategoriEklePenceresiGoster();
            }
        });

        imageActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();
            if (result.getResultCode() == Activity.RESULT_OK && data != null && data.getData() != null) {
                kaydetmeUrisi = data.getData();
                btnSec.setText("SEÇİLDİ");
            }
        });

        kategoriYukle();

    }

    private void kategoriYukle() {
        categoryCountRef = database.getReference("Category/");
        categoryCountRefListener = categoryCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        liste.clear();
                        for (int listeSize = 0; listeSize < snapshot.getChildrenCount(); listeSize++) {
                            liste.add(new Category(snapshot.child(String.valueOf(listeSize + 1)).child("name").getValue(String.class),
                                    snapshot.child(String.valueOf(listeSize + 1)).child("image").getValue(String.class)));
                        }
                        recyclerViewRun(liste);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void recyclerViewRun(List<Category> mliste) {
        recyclerAdapter = new CategoryViewHolder(mliste, this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerAdapter.setOnItemClickListener(new CategoryViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                //Toast.makeText(getApplicationContext(), "Position ~> " + position, Toast.LENGTH_SHORT).show();

                Intent gecis = new Intent(AdminPanelActivity.this, ProductActivity.class);
                startActivity(gecis);


            }
        });
    }


    private void kategoriEklePenceresiGoster() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminPanelActivity.this);
        builder.setTitle("Yeni kategori ekle");
        builder.setMessage("Lütfen bilgilerinizi yazın..");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View category_add = layoutInflater.inflate(R.layout.category_add, null);


        builder.setView(category_add);
        builder.setIcon(R.drawable.ic_action_name);

        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Kategoriyi veritabanına ekleme
                if (yeniKategori != null) {
                    kategoriYolu = database.getReference(("Category/" + pullCategoryCount()).trim());
                    kategoriYolu.setValue(yeniKategori.get(0));
                    Toast.makeText(AdminPanelActivity.this, yeniKategori.get(0) + " kategorisi eklendi", Toast.LENGTH_SHORT).show();
                }
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

        btnSec = (Button) category_add.findViewById(R.id.btnSec);
        btnYukle = (Button) category_add.findViewById(R.id.btnYukle);
        kategoriAdi = (EditText) category_add.findViewById(R.id.edtKategoriAdi);


        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resimSec();
            }
        });

        btnYukle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimYukle();
            }
        });

        builder.show();
    }


    private void resimSec() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imageActivityResultLauncher.launch(intent);
    }

    private void resimYukle() {
        if (kaydetmeUrisi != null) {
            ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Yükleniyor");
            mDialog.show();
            String resimAdi = UUID.randomUUID().toString();
            StorageReference resimDosyasi = resimYolu.child("resimler/" + resimAdi);
            resimDosyasi.putFile(kaydetmeUrisi)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(AdminPanelActivity.this, "Resim Yüklendi", Toast.LENGTH_SHORT).show();
                            resimDosyasi.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Resmi veritabanına yükleme
                                    yeniKategori.clear();
                                    yeniKategori.add(new Category(kategoriAdi.getText().toString(), uri.toString()));
                                    kategoriAdi.setText("");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(AdminPanelActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    kategoriAdi.setText("");
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    mDialog.setMessage("% " + progress + " yüklendi");
                }
            });
        }
    }

    private int pullCategoryCount() {
        categoryCountRef = database.getReference("Category/");
        categoryCountRefListener = categoryCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        //recyclerViewRun(liste);
                        categoryCount = (int) snapshot.getChildrenCount();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return categoryCount + 1;
    }
}