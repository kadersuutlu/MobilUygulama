package com.example.market;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.market.Model.Product;
import com.example.market.ViewHolder.ProductViewHolder;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductActivity extends AppCompatActivity {

    Button btn_urun_ekle;
    Button sepete_ekle;

    EditText urunAdi;
    EditText urunFiyati;
    Button btnImageSelect, btnImageUpload;

    public static final int PICK_IMAGE_REQUEST = 71;
    Uri kaydetmeUrisi;

    String categoryID = "";
    String status = "";

    ActivityResultLauncher<Intent> imageActivityResultLauncher;

    //Firebase tanımlamaları
    FirebaseDatabase database;
    DatabaseReference kategoriYolu;
    FirebaseStorage storage;
    StorageReference resimYolu;

    //Category Firebase Reference
    private DatabaseReference urunYukle, urunCountRef;
    private ValueEventListener urunYukleListener, urunCountRefListener;

    //Category RecyclerView
    private RecyclerView recyclerView;
    ProductViewHolder recyclerAdapter;
    List<Product> liste = new ArrayList<>();
    int urunCount = 0;

    private List<Product> yeniUrun = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //Firebase tanımlamaları
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        resimYolu = storage.getReference();

        recyclerView = findViewById(R.id.recycler_product);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        btn_urun_ekle = (Button) findViewById(R.id.btn_urun_ekle);

        imageActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent data = result.getData();
            if (result.getResultCode() == Activity.RESULT_OK && data != null && data.getData() != null) {
                kaydetmeUrisi = data.getData();
                btnImageSelect.setText("SEÇİLDİ");
            }
        });
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            categoryID = bundle.getString("category");
            status = bundle.getString("status");
            if (status.equals("1")) {
                btn_urun_ekle.setVisibility(View.GONE);
            }
            //Toast.makeText(this, categoryID, Toast.LENGTH_SHORT).show();
        }
        btn_urun_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urunEklePenceresiGoster();
            }
        });
        pullProductCount();

        urunYukle();
    }


    private void urunYukle() {
        //urunCountRef = database.getReference("Category").child(categoryID).child("Product");
        urunCountRef = database.getReference("Product");
        urunCountRefListener = urunCountRef.orderByChild("categoryID").equalTo(categoryID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        liste.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            liste.add(new Product(dataSnapshot.child("productName").getValue(String.class),
                                    dataSnapshot.child("productImage").getValue(String.class),
                                    dataSnapshot.child("productPrice").getValue(String.class),
                                    dataSnapshot.child("categoryID").getValue(String.class),
                                    dataSnapshot.getKey()));
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

    private void recyclerViewRun(List<Product> liste) {
        recyclerAdapter = new ProductViewHolder(liste, this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerAdapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {
               Toast.makeText(getApplicationContext(), "Position ~> " + position, Toast.LENGTH_SHORT).show();

            /*    Intent gecis = new Intent(AdminPanelActivity.this, LoginActivity.class);
                startActivity(gecis);

             */
           }

           @Override
           public void onBasketClick(int position, String productID) {
               Toast.makeText(ProductActivity.this, "eklendi", Toast.LENGTH_SHORT).show();
               Basket.addLiveBasketListWithNotify(productID);
           }
       });
    }

    private void urunEklePenceresiGoster() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
        builder.setTitle("Yeni ürün ekle");
        builder.setMessage("Lütfen bilgilerinizi yazın..");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View product_add = layoutInflater.inflate(R.layout.product_add, null);


        builder.setView(product_add);
        builder.setIcon(R.drawable.ic_action_name);

        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Kategoriyi veritabanına ekleme
                if (yeniUrun != null) {
                    //kategoriYolu = database.getReference("Category/" + categoryID + "/Product/" + String.valueOf(urunCount + 1).trim());
                    kategoriYolu = database.getReference("Product/" + String.valueOf(urunCount + 1).trim());
                    kategoriYolu.setValue(yeniUrun.get(0));
                    Toast.makeText(ProductActivity.this, yeniUrun.get(0) + " ürünü eklendi", Toast.LENGTH_SHORT).show();
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

        btnImageSelect = (Button) product_add.findViewById(R.id.btnSecProduct);
        btnImageUpload = (Button) product_add.findViewById(R.id.btnYukleProduct);
        urunAdi = (EditText) product_add.findViewById(R.id.edtProductAdi);
        urunFiyati = (EditText) product_add.findViewById(R.id.edtProductFiyati);


        btnImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resimSec();
            }
        });

        btnImageUpload.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(ProductActivity.this, "Resim Yüklendi", Toast.LENGTH_SHORT).show();
                            resimDosyasi.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Resmi veritabanına yükleme
                                    // Resmi veri tabanın aktarma

                                    yeniUrun.clear();
                                    yeniUrun.add(new Product(urunAdi.getText().toString(), uri.toString(), urunFiyati.getText().toString(), categoryID, String.valueOf(urunCount+1) ));
                                    urunAdi.setText("");
                                    urunFiyati.setText("");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(ProductActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    urunAdi.setText("");
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

    private void pullProductCount() {
        //urunCountRef = database.getReference("Category/" + categoryID + "/Product/");
        urunCountRef = database.getReference("Product");
        urunCountRefListener = urunCountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.hasChildren()) {
                        //recyclerViewRun(liste);
                        urunCount = (int) snapshot.getChildrenCount();
                    } else {
                        urunCount = 0;
                    }
                } else {
                    urunCount = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}