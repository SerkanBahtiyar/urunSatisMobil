package com.nexis.ordu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sb_giris_sayfasi extends AppCompatActivity {
    EditText sb_email, sb_sifre;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_activity_giris);
        sb_email = findViewById(R.id.sb_etxt_email);
        sb_sifre = findViewById(R.id.sb_etxt_sifre);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser kullanici = mAuth.getCurrentUser();
        if (kullanici != null) {
            startActivity(new Intent(sb_giris_sayfasi.this, sb_anasayfa.class));
            finish();
        }
    }

    public void sb_kayit_ol(View v) {
        startActivity(new Intent(sb_giris_sayfasi.this, sb_kayit.class));

    }

    public void sb_giris_yap(View v) {
        mAuth.signInWithEmailAndPassword(sb_email.getText().toString(), sb_sifre.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("giris", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(sb_giris_sayfasi.this, "Login başarılı yeni sayfaya yönlendirilicekseniz.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(sb_giris_sayfasi.this, sb_anasayfa.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("giris", "signInWithEmail:failure", task.getException());
                            Toast.makeText(sb_giris_sayfasi.this, "Belirtilen email veya sifre hatalıdır",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}