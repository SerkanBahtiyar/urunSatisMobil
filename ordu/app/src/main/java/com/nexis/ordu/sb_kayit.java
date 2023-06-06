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

public class sb_kayit extends AppCompatActivity {
    EditText sb_email, sb_sifre, sb_isim, sb_yas;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sb_kayit);
        sb_email = findViewById(R.id.sb_etxt_kayitol_email);
        sb_sifre = findViewById(R.id.sb_etxt_kayitol_sifre);
        sb_isim = findViewById(R.id.sb_etxt_kayitol_isim);
        sb_yas = findViewById(R.id.sb_etxt_kayitol_yas);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void sb_kayit_ol(View v) {
        String uemail = sb_email.getText().toString();
        String usifre = sb_sifre.getText().toString();
        if (!uemail.isEmpty() && !usifre.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(uemail, usifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("KayitOl", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(sb_kayit.this, sb_anasayfa.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("sb_kayit", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(sb_kayit.this, "Kullanıcı kaydı yapılamadı.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(sb_kayit.this, sb_giris_sayfasi.class));
                            }
                        }
                    });

        }


    }

}