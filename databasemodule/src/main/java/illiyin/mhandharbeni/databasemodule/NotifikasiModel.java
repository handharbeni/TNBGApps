package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 10/3/17.
 */

public class NotifikasiModel extends RealmObject{
    @PrimaryKey
    private int id;

    private int user_id;
    private String content;
    private String link;
    private String created_at;
    private String updated_at;
    private int read;

    public NotifikasiModel() {
    }

    public NotifikasiModel(int id, int user_id, String content, String link, String created_at, String updated_at, int read) {
        this.id = id;
        this.user_id = user_id;
        this.content = content;
        this.link = link;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
