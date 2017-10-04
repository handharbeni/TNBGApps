package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.databasemodule.TrendingModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import illiyin.mhandharbeni.tnbgapps.home.adapter.TrendingAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/5/17.
 */

public class TrendFragment extends Fragment implements RealmRecyclerView.OnRefreshListener {
    private View v;
    private TrendingModel trendingModel;
    private Crud crud;
    private List<TrendingModel> trendingList;
    private RealmRecyclerView rvtrending;
    private SwipeRefreshLayout swipetrending;
    private TrendingAdapter trendingAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        trendingModel = new TrendingModel();
        crud = new Crud(getActivity().getApplicationContext(), trendingModel);
        v = inflater.inflate(R.layout._layout_fragmenttrend, container, false);
        rvtrending = v.findViewById(R.id.rvtrending);
        rvtrending.setOnRefreshListener(this);
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
    private void init_adapter(){
//        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
//        RealmResults<TrendingModel> rr = realm.where(TrendingModel.class).findAll();
        RealmResults rr = crud.readSorted("id", Sort.DESCENDING);
        trendingAdapter = new TrendingAdapter(getActivity().getApplicationContext(), rr, true);
        rvtrending.setAdapter(trendingAdapter);
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        init_adapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReloadData().execute("http://api.tnbg.news/api/trending");
            }
        });
    }

    private class ReloadData extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            AdapterModel adapterModel = new AdapterModel(getActivity().getBaseContext());
            Boolean returns = adapterModel.syncTrending(strings[0]);
            if (returns){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvtrending.setRefreshing(false);
                    }
                });
            }
            return returns;
        }
    }
}
