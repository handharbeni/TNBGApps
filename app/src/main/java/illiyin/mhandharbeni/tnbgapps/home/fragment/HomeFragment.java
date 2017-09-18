package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.endless.Endless;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/5/17.
 */

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View v;
    private NewsModel newsModel;
    private Crud crud;
    private List<NewsModel> newsList;
    private RecyclerView rvnews;
    private HomeAdapter homeadapter;
    private Endless endless;
    private SwipeRefreshLayout swiperefreshlayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
        newsModel = new NewsModel();
        crud = new Crud(getActivity().getApplicationContext(), newsModel);
        v = inflater.inflate(R.layout._layout_fragmenthome, container, false);
        rvnews = v.findViewById(R.id.rvnews);
        swiperefreshlayout = v.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(this);
        init_list();
        init_adapter();
        init_paginate();
        return v;
    }
    private void init_paginate(){
        final View loadingView = View.inflate(getActivity().getApplicationContext(), R.layout.loading_layout, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        endless = Endless.applyTo(rvnews,
                loadingView
        );
        endless.setAdapter(homeadapter);
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                AsyncTask<String, Integer, Boolean> ShowPage = new ShowPage().execute("https://api.tnbg.news/api/posts?limit=10&page="+String.valueOf(page));
                try {
                    if (ShowPage.get()){
                        endless.loadMoreComplete();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setDummyData(){
        for (int i=0;i<9;i++){
            Log.d(TAG, "setDummyData: "+i);
            if (!crud.checkDuplicate("id", i)){
                Log.d(TAG, "setDummyData: "+crud.checkDuplicate("id", i));
                NewsModel newsModel = new NewsModel();
                newsModel.setId(i);
                newsModel.setCreated_at(getActivity().getString(R.string.placeholder_tanggal));
                newsModel.setStatus(i);
                newsModel.setTitle(getActivity().getString(R.string.placeholder_title));
                newsModel.setExcerpt(getActivity().getString(R.string.placeholder_frontcontent));
//                newsModel.setContent("<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><img class=\"img-responsive\" style=\"color: inherit; font-family: inherit; -webkit-text-size-adjust: 100%;\" src=\"//cdn.tnbg.news/image/public/2017/08/O05DhWrmc2aDT8I.jpeg\" /><span style=\"font-size: 12pt;\">Mimin terima DM dari follower ✌️</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">.</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">Kalo ada yg di Surabaya dan bisa nemuin si Bapak, info mimin ya</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">.</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">DM ???? Halo kak td siang aku ketemu bapak2 beliau jualan pulsa diatas sepeda yang dibuat sendiri, namanya bapak saiful kurang tau usianya berapa. waktu ditanya kenapa jualan pulsa katanya utk modal, terus bapak ini gak punya istri dan anak.bapak saiful (maaf) sepertinya kesulitan buat berjalan. untuk komunikasih pun sedikit susah.&nbsp;</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">mungkin bisa dibantu sebarkan kak kalo sekiranya untuk sekedar beli pulsa biar bisa sedikit membantu sedikit membantu penghidupan bapak saiful.</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">.</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">beliau biasanya mangkal di depan indomaret plus daerah tenggilis surabaya, setelah apartemen metropolis surabaya kak.</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">&nbsp;</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">Video bisa dilihat di</span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\"> <iframe src=\"//www.youtube.com/embed/So1giGNeRok\" width=\"560\" height=\"314\" allowfullscreen=\"allowfullscreen\"></iframe></span></p>\n<p style=\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\"><span style=\"font-size: 12pt;\">&nbsp;</span></p>");
                String sFromServer = "<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><img class=\\\"img-responsive\\\" style=\\\"color: inherit; font-family: inherit; -webkit-text-size-adjust: 100%;\\\" src=\\\"\\/\\/cdn.tnbg.news\\/image\\/public\\/2017\\/08\\/O05DhWrmc2aDT8I.jpeg\\\" \\/><span style=\\\"font-size: 12pt;\\\">Mimin terima DM dari follower ✌️<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">.<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">Kalo ada yg di Surabaya dan bisa nemuin si Bapak, info mimin ya<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">.<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">DM ???? Halo kak td siang aku ketemu bapak2 beliau jualan pulsa diatas sepeda yang dibuat sendiri, namanya bapak saiful kurang tau usianya berapa. waktu ditanya kenapa jualan pulsa katanya utk modal, terus bapak ini gak punya istri dan anak.bapak saiful (maaf) sepertinya kesulitan buat berjalan. untuk komunikasih pun sedikit susah.&nbsp;<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">mungkin bisa dibantu sebarkan kak kalo sekiranya untuk sekedar beli pulsa biar bisa sedikit membantu sedikit membantu penghidupan bapak saiful.<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">.<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">beliau biasanya mangkal di depan indomaret plus daerah tenggilis surabaya, setelah apartemen metropolis surabaya kak.<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">&nbsp;<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">Video bisa dilihat di<\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\"> <iframe src=\\\"\\/\\/www.youtube.com\\/embed\\/So1giGNeRok\\\" width=\\\"560\\\" height=\\\"314\\\" allowfullscreen=\\\"allowfullscreen\\\"><\\/iframe><\\/span><\\/p>\\n<p style=\\\"margin-bottom: 0px; font-size: 12px; line-height: normal; font-family: Helvetica;\\\"><span style=\\\"font-size: 12pt;\\\">&nbsp;<\\/span><\\/p>";
                newsModel.setContent(sFromServer.replaceAll("\\\\n","").replaceAll("\\\\", "").replaceAll("//www", "www"));
                newsModel.setComment_count(i);
                newsModel.setLike_count(i);
                newsModel.setSubscribe(false);
                newsModel.setMedias("https://cdn.tnbg.news/image/public/2017/08/O05DhWrmc2aDT8I.jpeg");
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
        homeadapter = new HomeAdapter(getActivity().getApplicationContext(), newsList);
        rvnews.setAdapter(homeadapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvnews.setLayoutManager(llm);
    }
    private void update_adapter(){
        init_list();
        if (homeadapter != null){
            homeadapter = new HomeAdapter(getActivity().getApplicationContext(), newsList);
            homeadapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String mode = bundle.getString("MODE");
            switch (mode){
                case "UPDATE LIST":
                    update_adapter();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        AsyncTask<String, Integer, Boolean> ReloadData = new ReloadData().execute("https://api.tnbg.news/api/posts?limit=10&page=1");
        try {
            if (ReloadData.get()){
                swiperefreshlayout.setRefreshing(false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private class ReloadData extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Execute"+strings[0]);
            AdapterModel adapterModel = new AdapterModel(getActivity().getBaseContext());
            Boolean returns = adapterModel.syncNews(strings[0]);
            return returns;
        }
    }
    private class ShowPage extends AsyncTask<String, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Execute"+strings[0]);
            AdapterModel adapterModel = new AdapterModel(getActivity().getBaseContext());
            Boolean returns = adapterModel.syncNews(strings[0]);
            return returns;
        }
    }
}
