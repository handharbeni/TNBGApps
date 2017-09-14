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
import illiyin.mhandharbeni.databasemodule.TrendingModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import illiyin.mhandharbeni.tnbgapps.home.adapter.TrendingAdapter;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/5/17.
 */

public class TrendFragment extends Fragment {
    private View v;
    private TrendingModel trendingModel;
    private Crud crud;
    private List<TrendingModel> trendingList;
    private RecyclerView rvtrending;
    private TrendingAdapter trendingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        trendingModel = new TrendingModel();
        crud = new Crud(getActivity().getApplicationContext(), trendingModel);
        v = inflater.inflate(R.layout._layout_fragmenttrend, container, false);
        rvtrending = v.findViewById(R.id.rvtrending);
        setDummyData();
        init_list();
        init_adapter();
        return v;
    }
    private void setDummyData(){
        for (int i=0;i<9;i++){
            if (!crud.checkDuplicate("id", i)){
                TrendingModel trendingModel = new TrendingModel();
                trendingModel.setId(i);
                trendingModel.setName("Trending"+i);
                trendingModel.setCount(i);
                crud.create(trendingModel);
            }
        }
    }
    private void init_list(){
        trendingList = new ArrayList<>();
        RealmResults rr = crud.read();
        if (rr.size() > 0){
            for (int i=0;i<rr.size();i++){
                TrendingModel tm = (TrendingModel) rr.get(i);
                trendingList.add(tm);
            }
        }
    }
    private void init_adapter(){
        trendingAdapter = new TrendingAdapter(getActivity().getApplicationContext(), trendingList);
        rvtrending.setAdapter(trendingAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvtrending.setLayoutManager(llm);
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDummyData();
        init_list();
        init_adapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
