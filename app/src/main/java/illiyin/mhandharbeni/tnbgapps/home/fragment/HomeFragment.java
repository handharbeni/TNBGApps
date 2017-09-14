package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.ybq.endless.Endless;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.HomeAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/5/17.
 */

public class HomeFragment extends Fragment {
    private View v;
    private NewsModel newsModel;
    private Crud crud;
    private List<NewsModel> newsList;
    private RecyclerView rvnews;
    private HomeAdapter homeadapter;
    private Endless endless;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(this.receiver, new IntentFilter("UPDATE NEWS"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsModel = new NewsModel();
        crud = new Crud(getActivity().getApplicationContext(), newsModel);
        v = inflater.inflate(R.layout._layout_fragmenthome, container, false);
        rvnews = v.findViewById(R.id.rvnews);
//        setDummyData();
        init_list();
        init_adapter();
        init_paginate();
        return v;
    }
    private void init_paginate(){
        View loadingView = View.inflate(getActivity().getApplicationContext(), R.layout.loading_layout, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        endless = Endless.applyTo(rvnews,
                loadingView
        );
        endless.setAdapter(homeadapter);
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                Toast.makeText(getActivity().getApplicationContext(), "Page "+String.valueOf(page), Toast.LENGTH_SHORT).show();
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
        homeadapter = new HomeAdapter(getActivity().getApplicationContext(), newsList);
        homeadapter.notifyDataSetChanged();
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
}
