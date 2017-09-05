package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/07/17.
 */

public class ModelOutlet extends RealmObject {
    @PrimaryKey
    int id;

    String outlet, alamat, latitude, longitude;
    String sha;

    public ModelOutlet(int id, String outlet, String alamat, String latitude, String longitude, String sha) {
        this.id = id;
        this.outlet = outlet;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sha = sha;
    }

    public ModelOutlet(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
