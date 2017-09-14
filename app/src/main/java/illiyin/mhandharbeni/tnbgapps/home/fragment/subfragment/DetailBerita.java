package illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import io.realm.RealmResults;

/**
 * Created by root on 9/14/17.
 */

public class DetailBerita extends AppCompatActivity {
    private NewsModel newsModel;
    private Crud crud;
    private TextView tanggal, status, title, text_comment, text_like, text_subscribe;
    private ImageView media;
    private WebView contenthtml;
    private String idBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsModel = new NewsModel();
        crud = new Crud(getApplicationContext(), newsModel);

        idBerita = getIntent().getStringExtra("idBerita");

        setContentView(R.layout.detailberita_layout);

        fetch_element();
        fetch_data();
    }
    private void fetch_element(){
        tanggal = findViewById(R.id.tanggal);
        status = findViewById(R.id.status);
        title = findViewById(R.id.title);
        text_comment = findViewById(R.id.text_comment);
        text_like = findViewById(R.id.text_like);
        text_subscribe = findViewById(R.id.text_subscribe);
        media = findViewById(R.id.media);
        contenthtml = findViewById(R.id.contenthtml);
//        contenthtml.getSettings().setLoadWithOverviewMode(true);
        contenthtml.getSettings().setUseWideViewPort(true);
    }
    private void fetch_data(){
        if (!idBerita.isEmpty()){
            RealmResults results = crud.read("id", Integer.valueOf(idBerita));
            if (results.size() > 0){
                NewsModel newsResult = (NewsModel) results.get(0);
                tanggal.setText(newsResult.getCreated_at());
                status.setText(String.valueOf(newsResult.getStatus()));
                title.setText(newsResult.getTitle());
                text_comment.setText(newsResult.getComment_count()+" "+getString(R.string.placeholder_comment));
                text_like.setText(newsResult.getLike_count()+" "+getString(R.string.placeholder_like));
                Glide.with(this)
                        .load(newsResult.getMedias())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(false)
                        .into(media);
                contenthtml.loadData(newsResult.getContent(), "text/html","UTF-8");
            }
        }
    }
}