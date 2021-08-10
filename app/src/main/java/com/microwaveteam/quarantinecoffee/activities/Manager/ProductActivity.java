package com.microwaveteam.quarantinecoffee.activities.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.microwaveteam.quarantinecoffee.R;
import com.microwaveteam.quarantinecoffee.models.Product;
import com.squareup.picasso.Picasso;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 1;

    private StringBuilder imgFireBase = new StringBuilder();

    Button btn_mn_ProductAddEdit;
    Button btn_mn_ProductDelete;
    Button btnFind;
    EditText txt_mn_ProductName,txt_mn_Price,txt_mn_ProductAmount;
    Spinner sp_mn_Category;
    String arr[]={ "Trà sữa", "Cà phê", "Nước ngọt", "Bánh", "Hạt"};
    ArrayList<String> productTypeList = new ArrayList<String>();
    TextView txt_mn_selection_list;
    ImageView imageView;
    Uri imageUri;

    DatabaseReference myRef;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_product);
        //txt_mn_selection_list =(TextView) findViewById(R.id.txt_mn_selection_list);
        bind();
    }
    private void bind(){
        txt_mn_ProductName = findViewById(R.id.txt_mn_ProductName);
        txt_mn_Price = findViewById(R.id.txt_mn_Price);
        txt_mn_ProductAmount = findViewById(R.id.txt_mn_ProductAmount);
        sp_mn_Category = findViewById(R.id.sp_mn_Category);
        imageView = findViewById(R.id.imageView_mn_product);

        btnFind = findViewById(R.id.btn_mn_find_product);
        btn_mn_ProductAddEdit = findViewById(R.id.btn_mn_ProductAddEdit);
        btn_mn_ProductDelete = findViewById(R.id.btn_mn_ProductDelete);

        btn_mn_ProductAddEdit.setOnClickListener(view ->
        {
            btnAddClicked();
        });
        btn_mn_ProductDelete.setOnClickListener(view ->
        {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Product");

            myRef.child(sp_mn_Category.getSelectedItem().toString()).child(txt_mn_ProductName.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ProductActivity.this, "Delete succeed", Toast.LENGTH_LONG);
                }
            });

        });

        btnFind.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Product");
            if (txt_mn_ProductName.getText().toString().isEmpty()) {
                Toast.makeText(ProductActivity.this, "please fill the name field and choose the correct product type!", Toast.LENGTH_LONG).show();
            } else {
                myRef.child(sp_mn_Category.getSelectedItem().toString()).child(txt_mn_ProductName.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() || snapshot.getValue() != null) {
                            Product p = snapshot.getValue(Product.class);
                            txt_mn_Price.setText(p.getPrice() + "");
                            txt_mn_ProductAmount.setText(p.getAmount() + "");
                            if (p.getImage() != null) {
                                Picasso.get().load(p.getImage()).into(imageView);
                            }
                        } else {
                            Toast.makeText(ProductActivity.this, "Product name doesn't exist!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

        });

        imageView.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, REQUEST_CODE);

        });

        loadProductTypeList();
        sp_mn_Category = findViewById(R.id.sp_mn_Category);

    }
    //========================================================================================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
    //========================================================================================================
    private void btnAddClicked() {
        myRef = FirebaseDatabase.getInstance().getReference("Product");
        Product product = new Product(txt_mn_ProductName.getText().toString(), Long.parseLong(txt_mn_Price.getText().toString())  ,
                Integer.parseInt(txt_mn_ProductAmount.getText().toString()) , sp_mn_Category.getSelectedItem().toString()
        );
        if (imageUri != null) {
            imageToFireBase(imageUri);
            product.setImage(imgFireBase.toString());
        }
        myRef.child(product.getCategory()).child(product.getProductName()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProductActivity.this, "Add succeed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void btnAddClickedWithoutSendingImage() {
        myRef = FirebaseDatabase.getInstance().getReference("Product");
        Product product = new Product(txt_mn_ProductName.getText().toString(), Long.parseLong(txt_mn_Price.getText().toString())  ,
                Integer.parseInt(txt_mn_ProductAmount.getText().toString()) , sp_mn_Category.getSelectedItem().toString()
        );
        if (imageUri != null) {
            product.setImage(imgFireBase.toString());
        }
        myRef.child(product.getCategory()).child(product.getProductName()).setValue(product);
    }
    //========================================================================================================
    private void loadProductTypeList() {
        myRef = FirebaseDatabase.getInstance().getReference("ProductType");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() || snapshot != null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String p = dataSnapshot.getValue(String.class);
                        productTypeList.add(p);
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(ProductActivity.this, android.R.layout.simple_spinner_item, productTypeList);
                    //hien thi ds cho spiner
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sp_mn_Category.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void imageToFireBase(Uri uri) {
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        bindToImgFirebase(uri);
                    }
                });

            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Upload image to firebase failed", Toast.LENGTH_SHORT).show();
        });
    }

    private void bindToImgFirebase(Uri uri) {
        imgFireBase.delete(0, imgFireBase.length());
        imgFireBase.append(uri.toString());
        Log.e("ProductActivity", "uri: " + uri.toString() + "\n" + imgFireBase.toString());
        btnAddClickedWithoutSendingImage();
        Toast.makeText(ProductActivity.this, "Upload image to firebase succeed, path: " + imgFireBase.toString(), Toast.LENGTH_LONG).show();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }
    //========================================================================================================
}