package illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by root on 9/28/17.
 */

public class ListComment extends AppCompatActivity implements RealmRecyclerView.OnRefreshListener, SessionListener {
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
        reloadComment();
        setContentView(R.layout.komentar_layout);
        fetch_element();
        init_adapter();
    }
    private void fetch_element(){
        listkomentar = findViewById(R.id.listkomentar);
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
                    postKoment();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            Boolean returns = attributeUtils.koment(url, object.toString());
            if (returns){
                /*insert berhasil*/
                updateCommentNews(Integer.valueOf(idBerita));
            }else{
                /*insert gagal*/
                showSnackBar("Gagal mengirim komentar");
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
//        Realm realm = Realm.getInstance(Realm.getDefaultConfiguration());
//        RealmResults<KomentarModel> komenModel = crud.read("post_id", Integer.valueOf(idBerita));
        RealmResults komenModel = crud.read("post_id", Integer.valueOf(idBerita));
        komentarAdapter = new KomentarAdapter(getApplicationContext(), komenModel, true);
        listkomentar.setAdapter(komentarAdapter);
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
                .build(Gravity.BOTTOM)
                .show();
    }
    private void reloadComments(){
        try {
            String endpoint = "http://api.tnbg.news/api/post/"+idBerita+"/comments?limit=100000&page=1";
            AdapterModel adapterModel = new AdapterModel(getApplicationContext());
            Boolean returns = adapterModel.syncComment(endpoint);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listkomentar.setRefreshing(false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listkomentar.setRefreshing(false);
                }
            });
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

    }
}
