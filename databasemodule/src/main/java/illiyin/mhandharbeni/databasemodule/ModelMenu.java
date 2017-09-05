package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/07/17.
 */

public class ModelMenu extends RealmObject {
    @PrimaryKey
    int id;

    String nama, gambar, harga, kategori, sha;


    public ModelMenu(int id, String nama, String gambar, String harga, String kategori, String sha) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.harga = harga;
        this.kategori = kategori;
        this.sha = sha;
    }

    public ModelMenu(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}

