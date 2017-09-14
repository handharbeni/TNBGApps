package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 9/12/17.
 */

public class NewsModel extends RealmObject {
    @PrimaryKey
    int id;

    int comment_status, publish, comment_count, like_count, status, child, categories;
    String title, slug, sort, content, excerpt, hashtag, tags, medias, created_at, published_at, update_at;
    Boolean liked, subscribe;
    public NewsModel(){

    }
    public NewsModel(int id, int comment_status, int publish, int comment_count, int like_count, int status, int child, int categories, String title, String slug, String sort, String content, String excerpt, String hashtag, String tags, String medias, String created_at, String published_at, String update_at, Boolean liked, Boolean subscribe) {
        this.id = id;
        this.comment_status = comment_status;
        this.publish = publish;
        this.comment_count = comment_count;
        this.like_count = like_count;
        this.status = status;
        this.child = child;
        this.categories = categories;
        this.title = title;
        this.slug = slug;
        this.sort = sort;
        this.content = content;
        this.excerpt = excerpt;
        this.hashtag = hashtag;
        this.tags = tags;
        this.medias = medias;
        this.created_at = created_at;
        this.published_at = published_at;
        this.update_at = update_at;
        this.liked = liked;
        this.subscribe = subscribe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public int getPublish() {
        return publish;
    }

    public void setPublish(int publish) {
        this.publish = publish;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getCategories() {
        return categories;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMedias() {
        return medias;
    }

    public void setMedias(String medias) {
        this.medias = medias;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }
}
