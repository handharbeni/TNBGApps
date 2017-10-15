package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.TrendingModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.TrendingAdapter;
import io.realm.RealmResults;

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

    private void init_adapter(){
//        RealmResults rr = crud.readSorted("id", Sort.DESCENDING);
        RealmResults rr = crud.read();
        trendingAdapter = new TrendingAdapter(getActivity().getApplicationContext(), rr, false);
        rvtrending.setAdapter(trendingAdapter);
    }
    @Override
    public void onPause() {
        crud.closeRealm();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        trendingModel = new TrendingModel();
        crud = new Crud(getActivity().getApplicationContext(), trendingModel);
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
                        trendingModel = new TrendingModel();
                        crud = new Crud(getActivity().getApplicationContext(), trendingModel);
                        RealmResults results = crud.read();
                        trendingAdapter.updateRealmResults(results);
                    }
                });
            }
//            trendingAdapter.notifyDataSetChanged();
            return returns;
        }
    }
}
