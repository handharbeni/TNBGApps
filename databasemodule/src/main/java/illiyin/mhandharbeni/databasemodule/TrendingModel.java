package illiyin.mhandharbeni.databasemodule;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 9/13/17.
 */

public class TrendingModel extends RealmObject {
    @PrimaryKey int id;

    String tag_group_id, slug, name;
    int suggest, count;
    String created_at, updated_at;

    public TrendingModel(int id, String tag_group_id, String slug, String name, int suggest, int count, String created_at, String updated_at) {
        this.id = id;
        this.tag_group_id = tag_group_id;
        this.slug = slug;
        this.name = name;
        this.suggest = suggest;
        this.count = count;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public TrendingModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag_group_id() {
        return tag_group_id;
    }

    public void setTag_group_id(String tag_group_id) {
        this.tag_group_id = tag_group_id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuggest() {
        return suggest;
    }

    public void setSuggest(int suggest) {
        this.suggest = suggest;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
