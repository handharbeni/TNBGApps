package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;

/**
 * Created by root on 01/08/17.
 */

public class ItemOrder extends RealmObject {
    int id, id_order, id_menu;
    String nama_menu, gambar, sha, jumlah, harga, total_harga, keterangan;

    public ItemOrder() {
    }

    public ItemOrder(int id, int id_order, int id_menu, String nama_menu, String gambar, String sha, String jumlah, String harga, String total_harga, String keterangan) {
        this.id = id;
        this.id_order = id_order;
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.gambar = gambar;
        this.sha = sha;
        this.jumlah = jumlah;
        this.harga = harga;
        this.total_harga = total_harga;
        this.keterangan = keterangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
