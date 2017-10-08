package illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import illiyin.mhandharbeni.databasemodule.ChildModel;
import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.akun.MainAccount;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.DateFormat;
import io.realm.RealmResults;

/**
 * Created by root on 9/14/17.
 */

public class DetailBerita extends AppCompatActivity implements SessionListener {
    private static final String TAG = "DetailBerita";
    private NewsModel newsModel;
    private Crud crud;

    private ChildModel childModel;
    private Crud crudChild;

    private TextView tanggal, status, title, text_comment, text_like;
    LinearLayout iconcomment, iconlike, iconshare;
    private ImageView media, back;
    ImageView like;
    private WebView contenthtml;
    private String idBerita;
    private String content;
    private DateFormat dateFormat;
    private Session session;
    private String from;
    private LinearLayout listcontent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(getApplicationContext(), this);

        newsModel = new NewsModel();
        crud = new Crud(getApplicationContext(), newsModel);

        childModel = new ChildModel();
        crudChild = new Crud(getApplicationContext(), childModel);

        dateFormat = new DateFormat();

        from = getIntent().getStringExtra("from");
        idBerita = getIntent().getStringExtra("idBerita");

        setContentView(R.layout.detailberita_layout);

        fetch_element();
        try {
            fetch_data();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void fetch_element(){
        listcontent = findViewById(R.id.listcontent);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tanggal = findViewById(R.id.tanggal);
        status = findViewById(R.id.status);
        title = findViewById(R.id.title);
        text_comment = findViewById(R.id.text_comment);
        text_like = findViewById(R.id.text_like);

        like = findViewById(R.id.like);

        iconcomment = findViewById(R.id.iconcomment);
        iconlike = findViewById(R.id.iconlike);
        iconshare = findViewById(R.id.iconshare);

        iconcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListComment.class);
                i.putExtra("idBerita", idBerita);
                startActivity(i);
            }
        });
        iconlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String login = session.getCustomParams("username", "nothing");
                    if (!login.equalsIgnoreCase("nothing")){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("post_id", Integer.valueOf(idBerita));
                        AttributeUtils attributeUtils = new AttributeUtils(getApplicationContext());
                        String response  = attributeUtils.like("https://api.tnbg.news/api/reaction", jsonObject.toString());
                        JSONObject jsonResponse = new JSONObject(response);
                        Boolean success = jsonResponse.getBoolean("success");
                        if (success){
                            String action = jsonResponse.getString("action");
                            if (action.equalsIgnoreCase("like")){
                                Glide.with(getApplicationContext()).load("").placeholder(R.drawable.like_filled).into(like);
                            }else{
                                Glide.with(getApplicationContext()).load("").placeholder(R.drawable.like).into(like);
                            }
                        }
                    }else{
                        Intent i = new Intent(getApplicationContext(), MainAccount.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        media = findViewById(R.id.media);
        contenthtml = findViewById(R.id.contenthtml);
        contenthtml.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        contenthtml.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        contenthtml.setLongClickable(false);
        contenthtml.setHapticFeedbackEnabled(false);
        WebSettings contentsetting = contenthtml.getSettings();
        contentsetting.setJavaScriptEnabled(true);
        contentsetting.setUseWideViewPort(false);
        contentsetting.setLoadWithOverviewMode(true);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void fetch_data() throws ParseException {
        if (!idBerita.isEmpty()){
            RealmResults results = crud.read("id", Integer.valueOf(idBerita));
            if (results.size() > 0){
                final NewsModel newsResult = (NewsModel) results.get(0);
                tanggal.setText(dateFormat.format(newsResult.getCreated_at()));
                status.setText(String.valueOf(newsResult.getStatus()==0?"On Going":"Complete"));
                title.setText(newsResult.getTitle());
                text_comment.setText(newsResult.getComment_count()+" "+getString(R.string.placeholder_comment));
                text_like.setText(newsResult.getLike_count()+" "+getString(R.string.placeholder_like));
                Glide.with(this)
                        .load(newsResult.getMedias())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(false)
                        .into(media);
                Glide.with(this)
                        .load("")
                        .placeholder(newsResult.getLiked()?R.drawable.like_filled:R.drawable.like)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(false)
                        .into(like);
                content = "<style>body > img.img-responsive{display:none;} body > p:first-child > img.img-responsive{display:none;} body > div:first-child > img.img-responsive{display:none;}.img-responsive{display: inline; height: auto; max-width: 100%; margin: 2px}div{margin-top:3px; margin-bottom:3px}iframe{top:0;left:0;width:100%;}</style>";
                content += newsResult.getContent().replace("//cdn", "https://cdn").replace("//www", "https://www");
//                Log.d(TAG, "fetch_data: "+content);
                contenthtml.loadData(content, "text/html","UTF-8");
                iconshare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://tnbg.news/"+ newsResult.getSlug());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                });
                Log.d(TAG, "fetch_data: "+from);
                Log.d(TAG, "fetch_data: "+String.valueOf(newsResult.getChild()));
                Log.d(TAG, "fetch_data: "+String.valueOf(newsResult.getId()));
                if (from != null){
                    if (from.equalsIgnoreCase("child") && newsResult.getChild()>0){
                        fetch_child(newsResult.getId());
                    }
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void fetch_child(int post_id) throws ParseException {
        DateFormat dateFormat = new DateFormat();
        String contents;
        RealmResults resultChild = crudChild.read("post_id", post_id);
        Log.d(TAG, "fetch_child: "+String.valueOf(resultChild.size()));
        if (resultChild.size() > 0){
            for (int i=0;i<resultChild.size();i++){
                ChildModel resultChildModel = (ChildModel) resultChild.get(i);

                TextView txt = new TextView(this);
                txt.setText(R.string.placeholder_updatebaru);
                txt.setTextSize(16);
                txt.setPadding(30,30,30,30);
                listcontent.addView(txt);

                RelativeLayout rl = new RelativeLayout(this);
                rl.setPadding(30,30,30,30);
                rl.setBackground(getResources().getDrawable(R.color.textWhite));

                TextView txtTanggal = new TextView(this);
                txtTanggal.setGravity(View.TEXT_ALIGNMENT_CENTER);
                txtTanggal.setText(dateFormat.format(resultChildModel.getCreated_at()));
                rl.addView(txtTanggal);

                RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView txtStatus = new TextView(this);
                txtStatus.setBackground(getResources().getDrawable(R.drawable.roundcorner_onprogress));
                newParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                final boolean hasSdk17 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
                if (hasSdk17){
                    newParams.addRule(RelativeLayout.ALIGN_PARENT_END, 1);
                }else{
                    newParams.addRule(RelativeLayout.ALIGN_RIGHT, 1);
                }
                txtStatus.setLayoutParams(newParams);
                txtStatus.setPadding(8,8,8,8);
                txtStatus.setText("Complete");
                rl.addView(txtStatus);

                listcontent.addView(rl);


                TextView txtTitle = new TextView(this);
                txtTitle.setText(resultChildModel.getTitle());
                txtTitle.setTextSize(20);
                txtTitle.setPadding(15,15,15,15);
                txtTitle.setTextColor(getResources().getColor(R.color.textBlack));
                txtTitle.setBackground(getResources().getDrawable(R.color.textWhite));
                listcontent.addView(txtTitle);

                WebView wv = new WebView(this);
                wv.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
                wv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return true;
                    }
                });
                wv.setLongClickable(false);
                wv.setHapticFeedbackEnabled(false);
                WebSettings cs = wv.getSettings();
                cs.setJavaScriptEnabled(true);
                cs.setUseWideViewPort(false);
                cs.setLoadWithOverviewMode(true);
                contents = "<style>body > img.img-responsive{display:none;} body > p:first-child > img.img-responsive{display:none;} body > div:first-child > img.img-responsive{display:none;}.img-responsive{display: inline; height: auto; max-width: 100%; margin: 2px}div{margin-top:3px; margin-bottom:3px}iframe{top:0;left:0;width:100%;}</style>";
                contents += resultChildModel.getContent().replace("//cdn", "https://cdn").replace("//www", "https://www");
                listcontent.addView(wv);
                wv.loadData(contents, "text/html","UTF-8");
            }
        }
    }

    @Override
    protected void onPause() {
        crud.closeRealm();
        crudChild.closeRealm();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        crud.closeRealm();
        crudChild.closeRealm();
        super.onDestroy();
    }

    @Override
    public void sessionChange() {

    }
}
