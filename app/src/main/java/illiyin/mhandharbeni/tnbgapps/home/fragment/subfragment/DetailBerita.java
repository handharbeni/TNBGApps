package illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.DateFormat;
import io.realm.RealmResults;

/**
 * Created by root on 9/14/17.
 */

public class DetailBerita extends AppCompatActivity {
    private static final String TAG = "DetailBerita";
    private NewsModel newsModel;
    private Crud crud;
    private TextView tanggal, status, title, text_comment, text_like, text_subscribe;
    LinearLayout iconcomment, iconlike, iconsubscribe;
    private ImageView media, back;
    ImageView like, subscribe;
    private WebView contenthtml;
    private String idBerita;
    private String content;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsModel = new NewsModel();
        crud = new Crud(getApplicationContext(), newsModel);
        dateFormat = new DateFormat();


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
        text_subscribe = findViewById(R.id.text_subscribe);

        like = findViewById(R.id.like);

        iconcomment = findViewById(R.id.iconcomment);
        iconlike = findViewById(R.id.iconlike);
        iconsubscribe = findViewById(R.id.iconsubscribe);

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
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("post_id", Integer.valueOf(idBerita));
                    AttributeUtils attributeUtils = new AttributeUtils(getApplicationContext());
                    String response  = attributeUtils.like("https://api.tnbg.news/api/reaction", jsonObject.toString());
                    JSONObject jsonResponse = new JSONObject(response);
                    Boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        String action = jsonResponse.getString("action");
                        if (action.equalsIgnoreCase("like")){
                            Glide.with(getApplicationContext()).load(R.drawable.like_filled).into(like);
                        }else{
                            Glide.with(getApplicationContext()).load(R.drawable.like).into(like);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        iconsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("post_id", Integer.valueOf(idBerita));
                    AttributeUtils attributeUtils = new AttributeUtils(getApplicationContext());
                    String response  = attributeUtils.subscribe("https://api.tnbg.news/api/post/subscribe", jsonObject.toString());
                    JSONObject jsonResponse = new JSONObject(response);
                    Boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        text_subscribe.setText("Subscribed");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        media = findViewById(R.id.media);
        contenthtml = findViewById(R.id.contenthtml);
        contenthtml.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        contenthtml.setLongClickable(false);
        contenthtml.setHapticFeedbackEnabled(false);
        WebSettings contentsetting = contenthtml.getSettings();
        contentsetting.setJavaScriptEnabled(false);
        contentsetting.setUseWideViewPort(false);
        contentsetting.setLoadWithOverviewMode(true);
    }
    private void fetch_data() throws ParseException {
        if (!idBerita.isEmpty()){
            RealmResults results = crud.read("id", Integer.valueOf(idBerita));
            if (results.size() > 0){
                NewsModel newsResult = (NewsModel) results.get(0);
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
                        .load(newsResult.getLiked()?R.drawable.like_filled:R.drawable.like)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(false)
                        .into(like);
                content = "<style>body > img.img-responsive{display:none;} body > p:first-child > img.img-responsive{display:none;} body > div:first-child > img.img-responsive{display:none;}.img-responsive{display: inline; height: auto; max-width: 100%; margin: 2px}div{margin-top:3px; margin-bottom:3px}</style>";
                content += newsResult.getContent().replace("//cdn", "https://cdn");
                Log.d(TAG, "fetch_data: "+content);
                contenthtml.loadData(content, "text/html","UTF-8");
            }
        }
    }
}
