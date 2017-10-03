package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import illiyin.mhandharbeni.databasemodule.KomentarModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/29/17.
 */

public class AttributeUtils implements SessionListener {
    Context mContext;
    private CallHttp callHttp;
    private Session session;
    private Crud crud;
    private NewsModel newsModel;
    private Crud crudKomentar;
    private KomentarModel komentarModel;


    public AttributeUtils(Context mContext) {
        this.mContext = mContext;
        callHttp = new CallHttp(this.mContext);
        session = new Session(this.mContext, this);

        newsModel = new NewsModel();
        crud = new Crud(this.mContext, newsModel);

        komentarModel = new KomentarModel();
        crudKomentar = new Crud(this.mContext, komentarModel);
    }

    public String like(String url, String json) throws IOException, JSONException {
        String jsonSql = json;
        Log.d(TAG, "like: "+json.toString());
        JSONObject objectSql = new JSONObject(jsonSql);
        int id = objectSql.getInt("post_id");

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        Log.d(TAG, "like: "+responseString);
        String updateString = responseString;
        JSONObject objectLiked = new JSONObject(updateString);
        if (objectLiked.getBoolean("success")){
            String action = objectLiked.getString("action");
            Boolean liked = true;
            if (action.equalsIgnoreCase("like")){
                liked = true;
                updateLike(id, liked);
            }else{
                liked = false;
                updateLike(id, liked);
            }
        }
        return responseString;
    }
    public String subscribe(String url, String json) throws IOException, JSONException {
        String jsonSql = json;
        Log.d(TAG, "like: "+json.toString());

        JSONObject objectSql = new JSONObject(jsonSql);
        int id = objectSql.getInt("post_id");

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        Log.d(TAG, "like: "+responseString);
        String updateString = responseString;
        JSONObject objectSubscribe = new JSONObject(updateString);
        if (objectSubscribe.getBoolean("success")){
            updateSubscribe(id, true);
        }
        return responseString;
    }
    public Boolean koment(String url, String json) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        Boolean postComments = postComment(responseString);
        return postComments;
    }
    public String changePassword(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        return responseString;
    }
    public String getNotification(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        return responseString;
    }
    private Boolean updateLike(int id, Boolean liked){
        Log.d(TAG, "like: "+String.valueOf(id)+" "+liked.toString());
        RealmResults resultNm = crud.read("id", id);
        crud.openObject();
        NewsModel nm = (NewsModel) resultNm.get(0);
        nm.setLiked(liked);
        crud.update(nm);
        crud.commitObject();
        return true;
    }
    private Boolean updateSubscribe(int id, Boolean subscribe){
        Log.d(TAG, "like: "+String.valueOf(id)+" "+subscribe.toString());
        RealmResults resultNm = crud.read("id", id);
        crud.openObject();
        NewsModel nm = (NewsModel) resultNm.get(0);
        nm.setSubscribe(subscribe);
        crud.update(nm);
        crud.commitObject();
        return true;
    }
    private Boolean postComment(String json) throws JSONException {
        JSONObject objectResponse = new JSONObject(json);
        if (objectResponse.getBoolean("success")){
            JSONObject data = objectResponse.getJSONObject("data");
            String comment_author = data.getString("comment_author");
            Integer user_id = data.getInt("user_id");
            String comment_email = data.getString("comment_email");
            String comment_ip = data.getString("comment_ip");
            Integer post_id = data.getInt("post_id");
            String content = data.getString("content");
            String updated_at = data.getString("updated_at");
            String created_at = data.getString("created_at");
            Integer id = data.getInt("id");
            KomentarModel km = new KomentarModel();
            km.setComment_author(comment_author);
            km.setUser_id(user_id);
            km.setComment_email(comment_email);
            km.setComment_ip(comment_ip);
            km.setPost_id(post_id);
            km.setContent(content);
            km.setUpdated_at(updated_at);
            km.setCreated_at(created_at);
            km.setId(id);
            crudKomentar.create(km);
            return true;
        }else{
            return false;
        }
    }
    public String setActivation(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        return responseString;
    }
    @Override
    public void sessionChange() {

    }
}
