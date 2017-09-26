package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/5/17.
 */

public class HomeFragment extends Fragment implements RealmRecyclerView.OnRefreshListener {
    private Integer lastPage = 0;
    private View v;
    private NewsModel newsModel;
    private Crud crud;
    private List<NewsModel> newsList;
    private RealmRecyclerView rvnews;
    private HomeAdapter homeadapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
        newsModel = new NewsModel();
        crud = new Crud(getActivity().getApplicationContext(), newsModel);
        v = inflater.inflate(R.layout._layout_fragmenthome, container, false);
        rvnews = v.findViewById(R.id.rvnews);
        rvnews.setOnRefreshListener(this);
        init_adapter();
        return v;
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
        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
        RealmResults<NewsModel> newsModelx = realm.where(NewsModel.class).findAllSorted("id", Sort.DESCENDING);
        homeadapter = new HomeAdapter(getActivity().getApplicationContext(), newsModelx, true);
        rvnews.setAdapter(homeadapter);
//        llm = new LinearLayoutManager(getActivity().getApplicationContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rvnews.setLayoutManager(llm);
    }
    private void update_adapter(){
        init_list();
        if (homeadapter != null){
            Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
            RealmResults<NewsModel> newsModelx = realm.where(NewsModel.class).findAllSorted("id");
            homeadapter = new HomeAdapter(getActivity().getApplicationContext(), newsModelx, true);
            homeadapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onPause() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

    @Override
    public void onStop() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onDestroy() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
//        getActivity().unregisterReceiver(this.receiver);
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

//    BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Bundle bundle = intent.getExtras();
//            String mode = bundle.getString("MODE");
//            switch (mode){
//                case "UPDATE LIST":
////                    update_adapter();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    public void onRefresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReloadData().execute("https://api.tnbg.news/api/posts?limit=10&page=1");
            }
        });
    }
    private class ReloadData extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Execute"+strings[0]);
            AdapterModel adapterModel = new AdapterModel(getActivity().getBaseContext());
            Boolean returns = adapterModel.syncNewsPaging(strings[0]);
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
    private class ShowPage extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
//            Log.d(TAG, "doInBackground: Execute"+strings[0]);
            AdapterModel adapterModel = new AdapterModel(getActivity().getBaseContext());
            Boolean returns = adapterModel.syncNewsPaging(strings[0]);
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
