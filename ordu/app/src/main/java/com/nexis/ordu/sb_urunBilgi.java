package com.nexis.ordu;

public class sb_urunBilgi {
    public String sb_isim;
    public String sb_fiyat;
    public String sb_tanim;
    public String sb_url;

    public sb_urunBilgi() {
    }

    public sb_urunBilgi(String isim, String fiyat, String tanim, String url) {
        this.sb_isim = isim;
        this.sb_fiyat = fiyat;
        this.sb_tanim = tanim;
        this.sb_url = url;
    }
}
