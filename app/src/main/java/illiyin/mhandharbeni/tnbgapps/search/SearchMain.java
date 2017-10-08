package illiyin.mhandharbeni.tnbgapps.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.databasemodule.TempNewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.search.adapter.SearchAdapter;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/6/17.
 */

public class SearchMain extends Fragment implements SessionListener {
    private View v;
    private Session session;
    private TempNewsModel newsModel;
    private Crud crud;
    private RealmRecyclerView result;
    private SearchAdapter searchAdapter;
    private String filter;
    private NewsModel nm;
    private Crud crudNews;
    private String from;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getActivity().getApplicationContext(), this);

        nm = new NewsModel();
        crudNews = new Crud(getActivity().getApplicationContext(), nm);

        newsModel = new TempNewsModel();
        crud = new Crud(getActivity().getApplicationContext(), newsModel);
        v = inflater.inflate(R.layout.search_layout, container, false);
        fetch_element();
        init_adapter();
        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from");
        }else{
            from = "class";
        }
        showResult();
        return v;
    }
    private void showResult(){
        if (!from.equalsIgnoreCase("nav")){
            String queryString = session.getCustomParams("Query", "nothing");
            upateTemp("tags",queryString);
        }
    }
    private void fetch_element(){
        result = v.findViewById(R.id.result);
    }
    private void init_adapter(){
        RealmResults newsModelx = crud.read();
        searchAdapter = new SearchAdapter(getActivity().getApplicationContext(), newsModelx, true);
        result.setAdapter(searchAdapter);
    }

    @Override
    public void sessionChange() {
        String queryString = session.getCustomParams("Query", "nothing");
        if (queryString != null){
            Log.d(TAG, "sessionChange: "+queryString);
            filter = queryString;
            upateTemp("title", filter);
        }
    }

    private void upateTemp(String key, String filters){
        RealmResults resultNews = crudNews.contains(key, filters);
        crud.deleteAll(newsModel.getClass());
        if (resultNews.size() > 0){
            for (int i=0;i<resultNews.size();i++){
                NewsModel result = (NewsModel) resultNews.get(i);
                TempNewsModel tmpResult = new TempNewsModel();
                tmpResult.setId(result.getId());
                tmpResult.setComment_status(result.getComment_status());
                tmpResult.setPublish(result.getPublish());
                tmpResult.setComment_count(result.getComment_count());
                tmpResult.setLike_count(result.getLike_count());
                tmpResult.setStatus(result.getStatus());
                tmpResult.setChild(result.getChild());
                tmpResult.setCategories(result.getCategories());
                tmpResult.setTitle(result.getTitle());
                tmpResult.setSlug(result.getSlug());
                tmpResult.setSort(result.getSort());
                tmpResult.setContent(result.getContent());
                tmpResult.setExcerpt(result.getExcerpt());
                tmpResult.setHashtag(result.getHashtag());
                tmpResult.setTags(result.getTags());
                tmpResult.setMedias(result.getMedias());
                tmpResult.setCreated_at(result.getCreated_at());
                tmpResult.setPublished_at(result.getPublished_at());
                tmpResult.setUpdate_at(result.getUpdate_at());
                tmpResult.setLiked(result.getLiked());
                tmpResult.setSubscribe(result.getSubscribe());
                crud.create(tmpResult);
            }
        }
    }
}
