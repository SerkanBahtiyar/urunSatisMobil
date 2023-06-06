package com.nexis.ordu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sb_yeni_urun_ekle extends AppCompatActivity {
    ImageView sb_fotoYeri;
    EditText sb_iisim, sb_ffiyat, sb_ttanım;
    CheckBox sb_chk_kiraz, sb_chk_uzum;
    RadioButton sb_kirmizi, sb_mavi;
    Switch sb_muzik;
    Button urunTanimlama;
    Uri sb_fotoData;
    Bitmap sb_fotom;
    FirebaseStorage sb_storage;
    FirebaseFirestore sb_veritabani;
    Spinner sb_urun_miktar;
    String fotoURL;
    MediaPlayer sbMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_yeni_urun_ekle);
        sb_fotoYeri = findViewById(R.id.sb_iv_urunfoto_ekle);
        sb_iisim = findViewById(R.id.sb_etxt_ue_isim);
        sb_ttanım = findViewById(R.id.sb_etxt_cins_urunekle);
        sb_ffiyat = findViewById(R.id.sb_etxt_ue_fiyat);
        sb_chk_kiraz = findViewById(R.id.sb_chk_kiraz);
        sb_chk_uzum = findViewById(R.id.sb_chk_uzum);
        sb_kirmizi = findViewById(R.id.sb_rdb_kirmizi);
        sb_mavi = findViewById(R.id.sb_rdb_mavi);
        sb_urun_miktar = findViewById(R.id.sb_spinner);
        sb_muzik = findViewById(R.id.sb_switch1);
        sbMedia=MediaPlayer.create(getApplicationContext(),R.raw.sb);
        urunTanimlama = findViewById(R.id.sb_btn_ue_urungir);
        sb_storage = FirebaseStorage.getInstance();
        sb_veritabani = FirebaseFirestore.getInstance();
        urunTanimlama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urunTanimlama_click(view);
            }
        });

        sb_fotoYeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fotoSec();
            }
        });
        Button ana = findViewById(R.id.sb_btn_ana);
        ana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sb_yeni_urun_ekle.this, sb_anasayfa.class));
                finish();
            }
        });
        sb_mavi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(sb_yeni_urun_ekle.this, "ürün kutusu mavi seçildi",
                            Toast.LENGTH_SHORT).show();
            }
        });
        sb_kirmizi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(sb_yeni_urun_ekle.this, "ürün kutusu kırmızı seçildi",
                        Toast.LENGTH_SHORT).show();
            }
        });
        sb_chk_uzum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(sb_yeni_urun_ekle.this, "ürüne üzüm eklendi",
                            Toast.LENGTH_SHORT).show();
            }
        });
        sb_chk_kiraz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(sb_yeni_urun_ekle.this, "ürüne kiraz eklendi",
                        Toast.LENGTH_SHORT).show();
            }
        });
        String[] spinnerEleman = {"kilo", "tane", "kutu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerEleman);
        sb_urun_miktar.setAdapter(adapter);
        sb_muzik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean sb) {
                if (sb)
                    sbMedia.start();
                else
                    sbMedia.pause();
            }
        });

    }

    public void fotoSec() {
        Toast.makeText(sb_yeni_urun_ekle.this, "foto seç çağrıldı",
                Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(sb_yeni_urun_ekle.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(sb_yeni_urun_ekle.this, new String[]{Manifest.permission.
                    READ_EXTERNAL_STORAGE}, 60);
        } else {
            Intent intentfoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentfoto, 61);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 60) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {

                Intent intentfoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentfoto, 61);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 61 && resultCode == RESULT_OK &&
                data != null) {
            sb_fotoData = data.getData();

            try {

                sb_fotom = MediaStore.Images.Media.getBitmap(this.getContentResolver(), sb_fotoData);
                sb_fotoYeri.setImageBitmap(sb_fotom);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void urunTanimlama_click(View view) {
// Create a storage reference from our app
        StorageReference storageRef = sb_storage.getReference();
        long sb_zaman = System.nanoTime();
        // Create a reference to "mountains.jpg"
        // StorageReference mountainsRef = storageRef.child("mountains.jpg");

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("orduUrunleri/img" + String.valueOf(sb_zaman) + ".jpg");


        // [START upload_memory]
        // Get the data from an ImageView as bytes
        //imageView.setDrawingCacheEnabled(true);
        //imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) sb_fotoYeri.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(sb_yeni_urun_ekle.this, "Foto yüklenirken hata oluştu", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                //Toast.makeText(sb_yeni_urun_ekle.this, "Foto yükleme başarılı", Toast.LENGTH_SHORT).show();
                if (taskSnapshot.getMetadata().getReference() != null) {
                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fotoURL = uri.toString();
                            //createNewPost(imageUrl);
                            Toast.makeText(sb_yeni_urun_ekle.this, "Foto başarılı şekilde yüklendi",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("URL", fotoURL);
                            Log.d("URL", sb_iisim.getText().toString());
                            Log.d("URL", sb_ffiyat.getText().toString());
                            sb_Urun_Kaydet(sb_iisim.getText().toString(),
                                    sb_ffiyat.getText().toString(), sb_ttanım.getText().toString(), fotoURL
                            );
                        }
                    });
                }


            }
        });


    }

    private void sb_Urun_Kaydet(String sb_isim, String sb_Fiyat, String sb_tanım, String fotoURL) {
        // Create a new user with a first, middle, and last name
        Map<String, Object> sb_orduUrun = new HashMap<>();
        sb_orduUrun.put("isim", sb_isim);
        sb_orduUrun.put("fiyat", sb_Fiyat);
        sb_orduUrun.put("tanım", sb_tanım);
        sb_orduUrun.put("url", fotoURL);


        sb_veritabani.collection("sb_ordu").add(sb_orduUrun)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("yeni veri", "yeni veri eklenmiştir " + documentReference.getId());
                        Toast.makeText(sb_yeni_urun_ekle.this, "veriler kayıt edildi",
                                Toast.LENGTH_SHORT).show();
                        sb_fotoYeri.setImageResource(R.mipmap.ic_launcher);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("yeni veri", "Beklenmeyen bir hata oluştu", e);
                    }
                });
    }
}


