package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.ArsipAdapter;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/5/17.
 */

public class ArsipFragment extends Fragment {
    private View v;
    private NewsModel newsModel;
    private Crud crud;
    private List<NewsModel> newsList;
    private RecyclerView rvarsip;
    private ArsipAdapter arsipadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsModel = new NewsModel();
        crud = new Crud(getActivity().getApplicationContext(), newsModel);
        v = inflater.inflate(R.layout._layout_fragmentarsip, container, false);
//        setDummyData();
        init_list();
        init_adapter();
        return v;
    }
    private void setDummyData(){
        for (int i=0;i<9;i++){
            if (!crud.checkDuplicate("id", i)){
                NewsModel newsModel = new NewsModel();
                newsModel.setId(i);
                newsModel.setCreated_at(getActivity().getString(R.string.placeholder_tanggal));
                newsModel.setStatus(i);
                newsModel.setTitle(getActivity().getString(R.string.placeholder_title));
                newsModel.setExcerpt(getActivity().getString(R.string.placeholder_frontcontent));
                newsModel.setComment_count(i);
                newsModel.setLike_count(i);
                newsModel.setSubscribe(false);
                newsModel.setMedias("https://cdn.tnbg.news/image/public/2017/08/5zI0zkgXPiqKdOv.jpeg");
                crud.create(newsModel);
            }
        }
    }
    private void init_list(){
        newsList = new ArrayList<>();
        RealmResults rr = crud.read();
        if (rr.size() > 0){
            for (int i=0;i<rr.size();i++){
                NewsModel nm= (NewsModel) rr.get(i);
                newsList.add(nm);
            }
        }
    }
    private void init_adapter(){
        rvarsip = v.findViewById(R.id.rvarsip);
        arsipadapter = new ArsipAdapter(getActivity().getApplicationContext(), newsList);
        rvarsip.setAdapter(arsipadapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvarsip.setLayoutManager(llm);
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
