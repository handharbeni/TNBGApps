package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmResults;

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
        try {
            JSONObject responseObject = new JSONObject(response);
            Boolean success = responseObject.getBoolean("success");
            if (success){
                JSONObject objectMeta = responseObject.getJSONObject("meta");
//                if (objectMeta.length() > 0){
                int currentPage = objectMeta.getInt("current_page");
                int totalPage = objectMeta.getInt("last_page");
                for (int i=currentPage+1;i<=totalPage;i++){
                /*dosyncpaging*/
                    String url = "https://api.tnbg.news/api/posts?limit=5&page="+i;
                    syncNewsPaging(url);
                }
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Boolean syncNewsPaging(String url){
        NewsModel newsModel = new NewsModel();
        Crud crudNews = new Crud(context, newsModel);
        String response = callHttp.get(url);
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
                                    }
                                    crudNews.update(itemModel);
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

        Crud crud = new Crud(context, trendingModel);
        crud.deleteAll(TrendingModel.class);
        crud.closeRealm();

        TrendingModel trendingModels = new TrendingModel();
        Crud cruds = new Crud(context, trendingModels);
        String response = callHttp.get(url);
        try {
            JSONObject jsonResponse = new JSONObject(response);
            Boolean success = jsonResponse.getBoolean("success");
            if (success){
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if (dataArray.length() > 0){
                    for (int i = 0; i < dataArray.length(); i++){
                        JSONObject objectArray = dataArray.getJSONObject(i);
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
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            cruds.closeRealm();
        }
        cruds.closeRealm();
        return true;
    }
    @Override
    public void sessionChange() {

    }
}
