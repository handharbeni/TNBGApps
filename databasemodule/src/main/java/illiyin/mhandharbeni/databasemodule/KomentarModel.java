package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 9/29/17.
 */

public class KomentarModel extends RealmObject{
    @PrimaryKey
    int id;

    int post_id;
    String comment_author;
    String comment_email;
    String comment_website;
    String comment_ip;
    String flag;
    String content;
    int approved;
    int parent_id;
    int user_id;
    String created_at;
    String updated_at;

    public KomentarModel() {
    }

    public KomentarModel(int id, int post_id, String comment_author, String comment_email, String comment_website, String comment_ip, String flag, String content, int approved, int parent_id, int user_id, String created_at, String updated_at) {
        this.id = id;
        this.post_id = post_id;
        this.comment_author = comment_author;
        this.comment_email = comment_email;
        this.comment_website = comment_website;
        this.comment_ip = comment_ip;
        this.flag = flag;
        this.content = content;
        this.approved = approved;
        this.parent_id = parent_id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }

    public String getComment_email() {
        return comment_email;
    }

    public void setComment_email(String comment_email) {
        this.comment_email = comment_email;
    }

    public String getComment_website() {
        return comment_website;
    }

    public void setComment_website(String comment_website) {
        this.comment_website = comment_website;
    }

    public String getComment_ip() {
        return comment_ip;
    }

    public void setComment_ip(String comment_ip) {
        this.comment_ip = comment_ip;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}
