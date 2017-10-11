package illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.databasemodule.KomentarModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.akun.MainAccount;
import illiyin.mhandharbeni.tnbgapps.home.adapter.KomentarAdapter;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.SnackBar;
import illiyin.mhandharbeni.utilslibrary.SnackBarListener;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by root on 9/28/17.
 */

public class ListComment extends AppCompatActivity implements RealmRecyclerView.OnRefreshListener, SessionListener, SnackBarListener {
    private static final String TAG = "List Comment";
    private String idBerita;
    private KomentarAdapter komentarAdapter;
    private RealmRecyclerView listkomentar;
    private ImageView back;
    private TextView titleappbar;
    private Crud crud;
    private KomentarModel komentarModel;
    private CallHttp callHttp;
    private EditText komentar;
    private Button kirim;
    private Session session;
    private NewsModel newsModel;
    private Crud crudNewsModel;
    private ShimmerFrameLayout shimmer_view_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext(), this);

        newsModel = new NewsModel();
        crudNewsModel = new Crud(getApplicationContext(), newsModel);

        komentarModel  = new KomentarModel();
        crud = new Crud(getApplicationContext(), komentarModel);


        callHttp = new CallHttp(getApplicationContext());

        idBerita = getIntent().getStringExtra("idBerita");
        setContentView(R.layout.komentar_layout);

        showLoading();
        loading();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    private void loading(){
        Thread fetchTread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                    reloadComment();
                } catch (Exception e) {

                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fetch_element();
                            init_adapter();
                        }
                    });
                }
            }
        };
        fetchTread.start();
    }
    private void showLoading(){
        shimmer_view_container = findViewById(R.id.shimmer_view_container);
        shimmer_view_container.startShimmerAnimation();
    }
    private void hideLoading(){
        shimmer_view_container.stopShimmerAnimation();
        shimmer_view_container.setVisibility(View.GONE);
        listkomentar.setVisibility(View.VISIBLE);
    }
    private void fetch_element(){

        listkomentar = findViewById(R.id.listkomentar);

        listkomentar.setVisibility(View.GONE);

        listkomentar.setOnRefreshListener(this);
        back = findViewById(R.id.back);
        titleappbar = findViewById(R.id.titleappbar);
        titleappbar.setText("Komentar");

        komentar = findViewById(R.id.komentar);
        komentar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String login = session.getCustomParams("username", "nothing");
                if (login.equalsIgnoreCase("nothing")){
                    /*show login page*/
                    Intent i = new Intent(getApplicationContext(), MainAccount.class);
                    startActivity(i);
                }
            }
        });
        kirim = findViewById(R.id.kirim);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = session.getCustomParams("username", "nothing");
                if (login.equalsIgnoreCase("nothing")){
                    /*show login page*/
                    Intent i = new Intent(getApplicationContext(), MainAccount.class);
                    startActivity(i);
                }else{
                    kirim.setEnabled(false);
                    postKoment();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setCustomParams("reply", "nothing");
                finish();
            }
        });
    }
    private void postKoment(){
        try {
            JSONObject object = new JSONObject();
            object.put("post_id", Integer.valueOf(idBerita));
            object.put("content", komentar.getText().toString());
            String url = "https://api.tnbg.news/api/comments";
            AttributeUtils attributeUtils = new AttributeUtils(getApplicationContext());
            String returns = attributeUtils.koment(url, object.toString());
            if (returns.equalsIgnoreCase("Berhasil komen")){
                /*insert berhasil*/
                updateCommentNews(Integer.valueOf(idBerita));
            }else{
                if (returns.equalsIgnoreCase("Token expired")){
                    showSnackBar("Token Expired, Silakan Login ulang");
                    session.setCustomParams("LOGINSTATES", "false");
                    session.deleteSession();
                    Intent i = new Intent(getApplicationContext(), MainAccount.class);
                    startActivity(i);

                }else{
                    showSnackBar("Gagal mengirim komentar, Silakan ulangi lagi");
                }
            }
            komentar.setText("");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateCommentNews(int id){
        RealmResults results = crudNewsModel.read("id", id);

        NewsModel nm = (NewsModel) results.get(0);
        int lastCount = nm.getComment_count();

        crudNewsModel.openObject();
        nm.setComment_count(lastCount+1);
        crudNewsModel.commitObject();
    }
    private void init_adapter(){
        RealmResults komenModel = crud.read("post_id", Integer.valueOf(idBerita));
        komentarAdapter = new KomentarAdapter(getApplicationContext(), komenModel, true);
        listkomentar.setAdapter(komentarAdapter);
        hideLoading();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private Boolean reloadComment(){
        try {
            String endpoint = "http://api.tnbg.news/api/post/"+idBerita+"/comments?limit=100000&page=1";
            AdapterModel adapterModel = new AdapterModel(getApplicationContext());
            Boolean returns = adapterModel.syncComment(endpoint);
            return returns;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void showSnackBar(String message){
        new SnackBar(getApplicationContext()).view(findViewById(R.id.rlkoment))
                .message(message)
                .build(Gravity.TOP)
                .listener(this)
                .show();
    }
    private void reloadComments(){
        try {
            String endpoint = "http://api.tnbg.news/api/post/"+idBerita+"/comments?limit=100000&page=1";
            AdapterModel adapterModel = new AdapterModel(getApplicationContext());
            Boolean returns = adapterModel.syncComment(endpoint);
            listkomentar.setRefreshing(false);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                }
//            });
        } catch (JSONException e) {
            e.printStackTrace();
            listkomentar.setRefreshing(false);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                }
//            });
        }
    }

    @Override
    public void onRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                reloadComments();
            }
        });

    }

    @Override
    protected void onPause() {
        crud.closeRealm();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        crud.closeRealm();
        super.onDestroy();
    }

    @Override
    public void sessionChange() {
        String reply = session.getCustomParams("reply", "nothing");
        if (!reply.equalsIgnoreCase("nothing")){
            komentar.setText("@"+reply+" ");
            komentar.requestFocus();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        session.setCustomParams("reply", "nothing");
        finish();
    }

    @Override
    public void onDismiss() {
        kirim.setEnabled(true);
    }
}
