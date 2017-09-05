package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 22/07/17.
 */

public class ModelCart  extends RealmObject {
    @PrimaryKey
    int id;

    String nama, gambar, harga, kategori, sha;
    int jumlah;


    public ModelCart(int id, String nama, String gambar, String harga, String kategori, String sha, Integer jumlah) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.harga = harga;
        this.kategori = kategori;
        this.sha = sha;
        this.jumlah = jumlah;
    }

    public ModelCart(){

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

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}

