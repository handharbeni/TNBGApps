package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 22/07/17.
 */

public class ModelOrder extends RealmObject {
    @PrimaryKey
    int id;

    int id_user, id_kurir;
    /*alamat outlet*/
    String alamat, latitude, longitude, tanggal_waktu, status, keterangan, delivery_fee;
    /*alamat customer*/
    String nama_customer, email_customer, alamat_customer, latitude_customer, longitude_customer;
    /*attribut order*/
    String total_belanja, tanggal, jam;
    String sha;

    public ModelOrder(int id, int id_user, int id_kurir, String alamat, String latitude, String longitude, String tanggal_waktu, String status, String keterangan, String delivery_fee, String nama_customer, String email_customer, String alamat_customer, String latitude_customer, String longitude_customer, String total_belanja, String tanggal, String jam, String sha) {
        this.id = id;
        this.id_user = id_user;
        this.id_kurir = id_kurir;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tanggal_waktu = tanggal_waktu;
        this.status = status;
        this.keterangan = keterangan;
        this.delivery_fee = delivery_fee;
        this.nama_customer = nama_customer;
        this.email_customer = email_customer;
        this.alamat_customer = alamat_customer;
        this.latitude_customer = latitude_customer;
        this.longitude_customer = longitude_customer;
        this.total_belanja = total_belanja;
        this.tanggal = tanggal;
        this.jam = jam;
        this.sha = sha;
    }

    public String getEmail_customer() {
        return email_customer;
    }

    public void setEmail_customer(String email_customer) {
        this.email_customer = email_customer;
    }

    public String getTotal_belanja() {
        return total_belanja;
    }

    public void setTotal_belanja(String total_belanja) {
        this.total_belanja = total_belanja;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public ModelOrder() {
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getLatitude_customer() {
        return latitude_customer;
    }

    public void setLatitude_customer(String latitude_customer) {
        this.latitude_customer = latitude_customer;
    }

    public String getLongitude_customer() {
        return longitude_customer;
    }

    public void setLongitude_customer(String longitude_customer) {
        this.longitude_customer = longitude_customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_kurir() {
        return id_kurir;
    }

    public void setId_kurir(int id_kurir) {
        this.id_kurir = id_kurir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTanggal_waktu() {
        return tanggal_waktu;
    }

    public void setTanggal_waktu(String tanggal_waktu) {
        this.tanggal_waktu = tanggal_waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(String delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
