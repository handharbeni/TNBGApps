package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

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
 * Created by root on 17/07/17.
 */

public class AdapterModel implements SessionListener{
    String server;
    String endpointnews = "https://api.tnbg.news/api/posts?limit=5&page=1";

    private DatabaseListener databaseListener;


    Context context;
    Crud crud;
    CallHttp callHttp;
    Session session;

    public AdapterModel(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.context = context;
        callHttp = new CallHttp(context);
        session = new Session(context, this);
        initEndPoint();
    }

    private void initEndPoint(){
    }
    public Boolean syncNews(){
        String response = callHttp.get(endpointnews);
        if (response != null){
            try {
                JSONObject responseObject = new JSONObject(response);
                Boolean success = responseObject.getBoolean("success");
                if (success){
                    JSONObject objectMeta = responseObject.getJSONObject("meta");
                    int currentPage = objectMeta.getInt("current_page");
                    int totalPage = objectMeta.getInt("last_page");
                    for (int i=1;i<=totalPage;i++){
                        String url = "https://api.tnbg.news/api/posts?limit=5&page="+i;
                        syncNewsPaging(url);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    public void checkChild(){
        NewsModel newsModel = new NewsModel();
        Crud crudNewsModel = new Crud(context, newsModel);
        RealmResults resultNews = crudNewsModel.read();
        if (resultNews.size() > 0){
            for (int i=0;i<resultNews.size();i++){
                NewsModel nm = (NewsModel) resultNews.get(i);
                if (nm.getChild() > 0){
                    /*have an child*/
                    try {
                        fetchChild(nm.getId(), "https://api.tnbg.news/api/posts?page=1&limit=20000&parent_id="+nm.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        crudNewsModel.closeRealm();
    }
    private void fetchChild(int post_id, String url) throws IOException, JSONException {
        ChildModel childModel = new ChildModel();
        Crud crudChildModel = new Crud(context, childModel);
        String response = "";
        String login = session.getCustomParams("username", "nothing");
        if (login.equalsIgnoreCase("nothing")){
            response = callHttp.get(url);
        }else{
            response = getChild(url);
        }
        if (response != null){
            if (!response.equalsIgnoreCase("")){
                JSONObject objectResponse = new JSONObject(response);
                if (objectResponse.getBoolean("success")){
                    JSONArray arrayData = objectResponse.getJSONArray("data");
                    if (arrayData.length() > 0){
                        for (int i=0;i<arrayData.length();i++){
                            JSONObject objectData = arrayData.getJSONObject(i);
                            int id = objectData.getInt("id");
                            String title = objectData.getString("title");
                            String slug = objectData.getString("slug");
                            String sort = objectData.getString("sort");
                            String content = objectData.getString("content");
                            String excerpt = objectData.getString("excerpt");
                            int comment_status = objectData.getInt("comment_status");
                            int publish = objectData.getInt("publish");
                            int comment_count = objectData.getInt("comment_count");
                            String hashtag = objectData.getString("hashtag");
//                        int tags = objectData.getInt("tags");
                            int like_count = objectData.getInt("like_count");
                            int status = objectData.getInt("status");
                            int child = objectData.getInt("child");
//                        int categories = objectData.getInt("categories");
                            Boolean liked = objectData.getBoolean("liked");
                            Boolean subscribe = objectData.getBoolean("subscribe");
                            String medias;
                            JSONArray arrayMedia = objectData.getJSONArray("medias");
                            if (arrayMedia.length() > 0) {
                                JSONObject objectMedia = arrayMedia.getJSONObject(0);
                                medias = "https://" + objectMedia.getString("src").replace("\\", "").replaceAll("//cdn", "cdn");
                            }else{
                                medias = "nothing";
                            }
                            String created_at = objectData.getString("created_at");
                            String published_at = objectData.getString("published_at");
                            String updated_at = objectData.getString("updated_at");
                            if (crudChildModel.checkDuplicate("id", id)){
                                RealmResults results = crudChildModel.read("id", id);
                                if (results.size() > 0){
                                    ChildModel cmresult = (ChildModel) results.get(0);
                                    if (!updated_at.equalsIgnoreCase(cmresult.getUpdated_at())){
                                    /* update */
                                        crudChildModel.openObject();
                                        cmresult.setId(id);
                                        cmresult.setPost_id(post_id);
                                        cmresult.setTitle(title);
                                        cmresult.setSlug(slug);
                                        cmresult.setSort(sort);
                                        cmresult.setContent(content);
                                        cmresult.setExcerpt(excerpt);
                                        cmresult.setComment_status(comment_status);
                                        cmresult.setPublich(publish);
                                        cmresult.setComment_count(comment_count);
                                        cmresult.setHashtag(hashtag);
                                        cmresult.setLike_count(like_count);
                                        cmresult.setStatus(status);
                                        cmresult.setChild(child);
                                        cmresult.setLiked(liked);
                                        cmresult.setSubscribe(subscribe);
                                        cmresult.setMedias(medias);
                                        cmresult.setCreated_at(created_at);
                                        cmresult.setPublished_at(published_at);
                                        cmresult.setUpdated_at(updated_at);
                                        crudChildModel.update(cmresult);
                                        crudChildModel.commitObject();
                                    }
                                }
                            }else{
                                ChildModel cm = new ChildModel();
                                cm.setId(id);
                                cm.setPost_id(post_id);
                                cm.setTitle(title);
                                cm.setSlug(slug);
                                cm.setSort(sort);
                                cm.setContent(content);
                                cm.setExcerpt(excerpt);
                                cm.setComment_status(comment_status);
                                cm.setPublich(publish);
                                cm.setComment_count(comment_count);
                                cm.setHashtag(hashtag);
                                cm.setLike_count(like_count);
                                cm.setStatus(status);
                                cm.setChild(child);
                                cm.setLiked(liked);
                                cm.setSubscribe(subscribe);
                                cm.setMedias(medias);
                                cm.setCreated_at(created_at);
                                cm.setPublished_at(published_at);
                                cm.setUpdated_at(updated_at);
                                crudChildModel.create(cm);
                            }
                        }
                    }
                }
            }
        }
        crudChildModel.closeRealm();
    }
    public Boolean syncNewsPaging(String url) throws IOException {
        NewsModel newsModel = new NewsModel();
        Crud crudNews = new Crud(context, newsModel);
        String response = "";
        String login = session.getCustomParams("username", "nothing");
        if (login.equalsIgnoreCase("nothing")){
            response = callHttp.get(url);
        }else{
            response = getNews(url);
        }
        try {
            if (response != null){

                JSONObject sResponses = new JSONObject(response);
                if (sResponses != null){
                    Boolean success = sResponses.getBoolean("success");
                    if (success) {
                        JSONArray arrayData = sResponses.getJSONArray("data");
                        if (arrayData.length() > 0) {
                            for (int i = 0; i < arrayData.length(); i++) {

                                JSONObject itemNews = arrayData.getJSONObject(i);
                                RealmResults results = crudNews.read("id", itemNews.getInt("id"));
                                if (results.size() > 0) {
                                    NewsModel checkModel = (NewsModel) results.get(0);
                                    //                            if (!checkModel.getUpdate_at().equalsIgnoreCase(itemNews.getString("updated_at"))){
                                    crudNews.openObject();
//                                    NewsModel itemModel = new NewsModel();
//                                    checkModel.setId(itemNews.getInt("id"));
                                    checkModel.setTitle(itemNews.getString("title"));
                                    checkModel.setSlug(itemNews.getString("slug"));
                                    checkModel.setSort(itemNews.getString("sort"));
                                    checkModel.setContent(itemNews.getString("content"));
                                    checkModel.setExcerpt(itemNews.getString("excerpt"));
                                    checkModel.setComment_status(itemNews.getInt("comment_status"));
                                    checkModel.setPublish(itemNews.getInt("publish"));
                                    checkModel.setComment_count(itemNews.getInt("comment_count"));
                                    checkModel.setHashtag(itemNews.getString("hashtag"));
                                    checkModel.setLike_count(itemNews.getInt("like_count"));
                                    checkModel.setStatus(itemNews.getInt("status"));
                                    checkModel.setChild(itemNews.getInt("child"));
                                    checkModel.setLiked(itemNews.getBoolean("liked"));
                                    checkModel.setSubscribe(itemNews.getBoolean("subscribe"));
                                    checkModel.setCreated_at(itemNews.getString("created_at"));
                                    checkModel.setPublished_at(itemNews.getString("published_at"));
                                    checkModel.setUpdate_at(itemNews.getString("updated_at"));

                                    JSONArray arrayMedia = itemNews.getJSONArray("medias");
                                    if (arrayMedia.length() > 0) {
                                        JSONObject objectMedia = arrayMedia.getJSONObject(0);
                                        checkModel.setMedias("https://" + objectMedia.getString("src").replace("\\", "").replaceAll("//cdn", "cdn"));
                                    }else{
                                        checkModel.setMedias("nothing");
                                    }
                                    crudNews.update(checkModel);
                                    crudNews.commitObject();
                                    //                            }

                                } else {
                                    NewsModel itemModel = new NewsModel();
                                    itemModel.setId(itemNews.getInt("id"));
                                    itemModel.setTitle(itemNews.getString("title"));
                                    itemModel.setSlug(itemNews.getString("slug"));
                                    itemModel.setSort(itemNews.getString("sort"));
                                    itemModel.setContent(itemNews.getString("content"));
                                    itemModel.setExcerpt(itemNews.getString("excerpt"));
                                    itemModel.setComment_status(itemNews.getInt("comment_status"));
                                    itemModel.setPublish(itemNews.getInt("publish"));
                                    itemModel.setComment_count(itemNews.getInt("comment_count"));
                                    itemModel.setHashtag(itemNews.getString("hashtag"));
                                    itemModel.setLike_count(itemNews.getInt("like_count"));
                                    itemModel.setStatus(itemNews.getInt("status"));
                                    itemModel.setChild(itemNews.getInt("child"));
                                    itemModel.setLiked(itemNews.getBoolean("liked"));
                                    itemModel.setSubscribe(itemNews.getBoolean("subscribe"));
                                    itemModel.setCreated_at(itemNews.getString("created_at"));
                                    itemModel.setPublished_at(itemNews.getString("published_at"));
                                    itemModel.setUpdate_at(itemNews.getString("updated_at"));

                                    JSONArray arrayMedia = itemNews.getJSONArray("medias");
                                    if (arrayMedia.length() > 0) {
                                        JSONObject objectMedia = arrayMedia.getJSONObject(0);
                                        itemModel.setMedias("https://" + objectMedia.getString("src").replace("\\", "").replaceAll("//cdn", "cdn"));
                                    }else{
                                        itemModel.setMedias("nothing");
                                    }
                                    crudNews.create(itemModel);
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        crudNews.closeRealm();
        return true;
    }
    public Boolean syncTrending(String url){
        TrendingModel trendingModel = new TrendingModel();

//        Crud crud = new Crud(context, trendingModel);
//        crud.deleteAll(TrendingModel.class);
//        crud.closeRealm();

        TrendingModel trendingModels = new TrendingModel();
        Crud cruds = new Crud(context, trendingModels);
        String response = callHttp.get(url);
        if (response != null){
            try {
                JSONObject jsonResponse = new JSONObject(response);
                Boolean success = jsonResponse.getBoolean("success");
                if (success){
                    JSONArray dataArray = jsonResponse.getJSONArray("data");
                    if (dataArray.length() > 0){
                        for (int i = 0; i < dataArray.length(); i++){
                            JSONObject objectArray = dataArray.getJSONObject(i);
                            TrendingModel tms = new TrendingModel();
                            Crud newCrud = new Crud(context, tms);
                            if (!newCrud.checkDuplicate("id", objectArray.getInt("id"))){
                                TrendingModel tm = new TrendingModel();
                                tm.setId(objectArray.getInt("id"));
                                tm.setTag_group_id(objectArray.getString("tag_group_id"));
                                tm.setSlug(objectArray.getString("slug"));
                                tm.setName(objectArray.getString("name"));
                                tm.setSuggest(objectArray.getInt("suggest"));
                                tm.setCount(objectArray.getInt("count"));
                                tm.setCreated_at(objectArray.getString("created_at"));
                                tm.setUpdated_at(objectArray.getString("updated_at"));
                                cruds.create(tm);
                            }
                            newCrud.closeRealm();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                cruds.closeRealm();
            }
        }
        cruds.closeRealm();
        return true;
    }
    public Boolean syncComment(String url) throws JSONException {
        KomentarModel komentarModel = new KomentarModel();

        Crud crud = new Crud(context, komentarModel);
        String response = callHttp.get(url);
        if (response != null){
            JSONObject jsonResponse = new JSONObject(response);
            Boolean success = jsonResponse.getBoolean("success");
            if (success){
                JSONArray arrayResponse = jsonResponse.getJSONArray("data");
                if (arrayResponse.length() > 0){
                    for (int i=0;i<arrayResponse.length();i++){
                        JSONObject objectData = arrayResponse.getJSONObject(i);
                        int id = objectData.getInt("id");
                        int post_id = objectData.getInt("post_id");
                        String comment_author = objectData.getString("comment_author");
                        String comment_email = objectData.getString("comment_email");
                        String comment_website = objectData.getString("comment_website");
                        String comment_ip = objectData.getString("comment_ip");
                        String flag = objectData.getString("flag");
                        String content = objectData.getString("content");
                        int approved = objectData.getInt("approved");
                        int parent_id = objectData.getInt("parent_id");
                        int user_id = objectData.getInt("user_id");
                        String created_at = objectData.getString("created_at");
                        String updated_at = objectData.getString("updated_at");
                        Boolean checkDuplicate = crud.checkDuplicate("id", id);
                        if (!checkDuplicate){
                        /*new komen*/
                            KomentarModel km = new KomentarModel();
                            km.setId(id);
                            km.setPost_id(post_id);
                            km.setComment_author(comment_author);
                            km.setComment_email(comment_email);
                            km.setComment_website(comment_website);
                            km.setComment_ip(comment_ip);
                            km.setFlag(flag);
                            km.setContent(content);
                            km.setApproved(approved);
                            km.setParent_id(parent_id);
                            km.setUser_id(user_id);
                            km.setCreated_at(created_at);
                            km.setUpdated_at(updated_at);
                            crud.create(km);
                        }else{
                        /*update komen*/
                            RealmResults result = crud.read("id", id);
                            KomentarModel resultKomentar = (KomentarModel) result.get(0);
                            String oldUpdate = resultKomentar.getUpdated_at();
                            if (!oldUpdate.equalsIgnoreCase(updated_at)){
                                crud.delete("id", id);
                                KomentarModel km = new KomentarModel();
                                km.setId(id);
                                km.setPost_id(post_id);
                                km.setComment_author(comment_author);
                                km.setComment_email(comment_email);
                                km.setComment_website(comment_website);
                                km.setComment_ip(comment_ip);
                                km.setFlag(flag);
                                km.setContent(content);
                                km.setApproved(approved);
                                km.setParent_id(parent_id);
                                km.setUser_id(user_id);
                                km.setCreated_at(created_at);
                                km.setUpdated_at(updated_at);
                                crud.create(km);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public void syncNotifikasi(){
        String login = session.getCustomParams("username", "nothing");
        if (!login.equalsIgnoreCase("nothing")){
            NotifikasiModel notifikasiModel = new NotifikasiModel();
            Crud crud = new Crud(this.context, notifikasiModel);
            String url = "https://api.tnbg.news/api/notifications";
            try {
                String responses = getNotification(url);
                JSONObject objectResponse = new JSONObject(responses);

                if (objectResponse.getBoolean("success")){
                    JSONArray arrayResponse = objectResponse.getJSONArray("data");
                    if (arrayResponse.length() > 0){
                        for (int i=0;i<arrayResponse.length();i++){
                            JSONObject objectData = arrayResponse.getJSONObject(i);
                            Integer id = objectData.getInt("id");
                            Integer user_id = objectData.getInt("user_id");
                            String content = objectData.getString("content");
                            String link = objectData.getString("link");
                            String created_at = objectData.getString("created_at");
                            String updated_at = objectData.getString("updated_at");
                            Integer read = objectData.getInt("read");

                            if (crud.checkDuplicate("id", id)){
                            /*sudah ada data*/
                            /*update atau biarkan*/
                                RealmResults resulst = crud.read("id", id);
                                NotifikasiModel nmResult = (NotifikasiModel) resulst.get(0);
                                if (!updated_at.equalsIgnoreCase(nmResult.getUpdated_at())){
                                /*update*/

                                    crud.openObject();
                                    nmResult.setUser_id(user_id);
                                    nmResult.setContent(content);
                                    nmResult.setLink(link);
                                    nmResult.setCreated_at(created_at);
                                    nmResult.setUpdated_at(updated_at);
                                    nmResult.setRead(read);
                                    crud.update(nmResult);
                                    crud.commitObject();
                                }
                            }else{
                            /*insert baru*/
                                NotifikasiModel nm = new NotifikasiModel();
                                nm.setId(id);
                                nm.setUser_id(user_id);
                                nm.setContent(content);
                                nm.setLink(link);
                                nm.setCreated_at(created_at);
                                nm.setUpdated_at(updated_at);
                                nm.setRead(read);
                                crud.create(nm);
                            }
                        }
                    }
                }
                crud.closeRealm();
            } catch (JSONException  | IOException e) {
                e.printStackTrace();
                crud.closeRealm();
            }
            crud.closeRealm();
        }
    }
    @Override
    public void sessionChange() {

    }
    public String getNotification(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("cookie", "__cfduid=d89a508a0b4acae97f6e3b78192b948b41504498596")
                .addHeader("authorization", "Bearer "+session.getCustomParams("token", ""))
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        return responseString;
    }
    public String getNews(String url) throws IOException {
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
    public String getChild(String url) throws IOException {
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

}
