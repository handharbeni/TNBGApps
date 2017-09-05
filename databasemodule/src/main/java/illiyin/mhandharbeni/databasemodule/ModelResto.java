package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 17/07/17.
 */

public class ModelResto extends RealmObject {
    @PrimaryKey
    int id;

    String resto;
    String sha;

    public ModelResto(int id, String resto, String sha) {
        this.id = id;
        this.resto = resto;
        this.sha = sha;
    }

    public ModelResto() {
    }

    public Integer getId() {
        return id;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
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
