package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by faizalqurni on 10/5/17.
 */

public class ChildModel extends RealmObject {
    @PrimaryKey
    int id;

    int post_id;
    String title, slug, sort, content, excerpt, hashtag;
    int comment_status, publich, comment_count, tags, like_count, status, child, categories;
    Boolean liked, subscribe;
    String medias, created_at, published_at, updated_at;


    public ChildModel() {
    }

    public ChildModel(int id, int post_id, String title, String slug, String sort, String content, String excerpt, String hashtag, int comment_status, int publich, int comment_count, int tags, int like_count, int status, int child, int categories, Boolean liked, Boolean subscribe, String medias, String created_at, String published_at, String updated_at) {
        this.id = id;
        this.post_id = post_id;
        this.title = title;
        this.slug = slug;
        this.sort = sort;
        this.content = content;
        this.excerpt = excerpt;
        this.hashtag = hashtag;
        this.comment_status = comment_status;
        this.publich = publich;
        this.comment_count = comment_count;
        this.tags = tags;
        this.like_count = like_count;
        this.status = status;
        this.child = child;
        this.categories = categories;
        this.liked = liked;
        this.subscribe = subscribe;
        this.medias = medias;
        this.created_at = created_at;
        this.published_at = published_at;
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

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public int getPublich() {
        return publich;
    }

    public void setPublich(int publich) {
        this.publich = publich;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getTags() {
        return tags;
    }

    public void setTags(int tags) {
        this.tags = tags;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}