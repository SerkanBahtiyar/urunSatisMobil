package com.nexis.ordu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class sb_anasayfa extends AppCompatActivity {
    Spinner sbSpinner;
    FirebaseAuth mUser;
    RecyclerView sb_analiste;
    ArrayList<sb_urunBilgi> urunListe;
    FirebaseFirestore db;
    sb_urun_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_activity_main);
        mUser = FirebaseAuth.getInstance();
        sbSpinner = findViewById(R.id.sb_spinner);
        sb_analiste = findViewById(R.id.recyclerView);
        urunListe = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        adapter = new sb_urun_Adapter(urunListe);
        sb_analiste.setHasFixedSize(true);
        sb_analiste.setLayoutManager(new LinearLayoutManager(sb_anasayfa.this));
        sb_analiste.setAdapter(adapter);
        sb_tumVeriyiGetir();
    }

    public void sb_tumVeriyiGetir() {

        // [START get_all_users]
        db.collection("sb_ordu").orderBy("isim", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("getUrunler", document.getId() + " => " + document.getData());
                                Log.d("getUrunler", document.get("isim").toString());

                                urunListe.add(new sb_urunBilgi(document.get("fiyat").toString(),
                                        document.get("isim").toString(),
                                        document.get("tanım").toString(),
                                        document.get("url").toString()));
                            }

                            Log.d("getUrunler", urunListe.size() + "");

                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("getUrunler", "ürünleri sunucudan çekerken bir hata oluştu", task.getException());
                        }
                    }
                });
        // [END get_all_users]


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater minflater = getMenuInflater();
        //minflater.inflate(R.menu.kullanici_menu, menu);
        getMenuInflater().inflate(R.menu.kullanici_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.cikisyap:
                Toast.makeText(getApplicationContext(), "Çıkış yapılacak", Toast.LENGTH_LONG).show();
                mUser.signOut();
                startActivity(new Intent(sb_anasayfa.this, sb_giris_sayfasi.class));
                finish();
                return true;
            case R.id.yeniurun:
                Toast.makeText(getApplicationContext(), "Ordu ürün eklenecek", Toast.LENGTH_LONG).show();
                startActivity(new Intent(sb_anasayfa.this, sb_yeni_urun_ekle.class));
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "serkan bahtiyar", Toast.LENGTH_LONG).show();
                return true;
            case R.id.anasayfa:
                Toast.makeText(getApplicationContext(), "serkan bahtiyar", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}