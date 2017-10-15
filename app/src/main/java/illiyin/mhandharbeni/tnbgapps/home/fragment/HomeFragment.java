package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.NavigationActivity;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by root on 9/5/17.
 */

public class HomeFragment extends Fragment implements RealmRecyclerView.OnRefreshListener {
    private View v;
    private NewsModel newsModel;
    private Crud crud;
    private List<NewsModel> newsList;
    private RealmRecyclerView rvnews;
    private HomeAdapter homeadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsModel = new NewsModel();
        crud = new Crud(getActivity().getApplicationContext(), newsModel);
        v = inflater.inflate(R.layout._layout_fragmenthome, container, false);
        rvnews = v.findViewById(R.id.rvnews);
        rvnews.setOnRefreshListener(this);
        init_adapter();
        return v;
    }
    private void init_adapter(){
        RealmResults newsModelx = crud.readSorted("id", Sort.DESCENDING);
        homeadapter = new HomeAdapter(getActivity().getApplicationContext(), newsModelx, true);
        rvnews.setAdapter(homeadapter);
    }
    @Override
    public void onPause() {
        crud.closeRealm();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        crud.closeRealm();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        crud.closeRealm();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReloadData().execute("https://api.tnbg.news/api/posts?limit=20&page=1");
            }
        });
    }
    private class ReloadData extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            AdapterModel adapterModel = new AdapterModel(getActivity().getBaseContext());
            Boolean returns = null;
            try {
                returns = adapterModel.syncNewsPaging(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (returns){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvnews.setRefreshing(false);
                    }
                });
            }
            return returns;
        }
    }
    private boolean checkIsRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
