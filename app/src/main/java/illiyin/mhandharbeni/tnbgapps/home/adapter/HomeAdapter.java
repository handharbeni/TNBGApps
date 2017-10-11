package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.akun.MainAccount;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.DetailBerita;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.ListComment;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.DateFormat;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/12/17.
 */

public class HomeAdapter extends RealmBasedRecyclerViewAdapter<NewsModel, HomeAdapter.MyViewHolder> implements SessionListener {
    private Session session;
    private NewsModel newsModel;
    private Crud crud;
    @Override
    public HomeAdapter.MyViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.itempost, viewGroup, false);
        return new HomeAdapter.MyViewHolder((CardView) v);
    }

    @Override
    public void onBindRealmViewHolder(final HomeAdapter.MyViewHolder myViewHolder, final int i) {
        final NewsModel m = realmResults.get(i);
        DateFormat dateFormat = new DateFormat();

        try {
            myViewHolder.tanggal.setText(dateFormat.format(m.getCreated_at()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myViewHolder.status.setText(String.valueOf(m.getStatus()==0?getContext().getString(R.string.placeholder_onprogress):getContext().getString(R.string.placeholder_oncomplete)));
        myViewHolder.title.setText(m.getTitle());
        myViewHolder.frontcontent.setText(m.getExcerpt());
        myViewHolder.text_comment.setText(m.getComment_count()+" "+getContext().getString(R.string.comment));
        myViewHolder.text_like.setText(m.getLike_count()+" "+getContext().getString(R.string.like));
        myViewHolder.text_progress.setText(m.getChild()+" "+getContext().getString(R.string.placeholder_progress));

        if (m.getLiked()){
            Glide.with(getContext()).load("").placeholder(R.drawable.like_filled).into(myViewHolder.like);
        }else{
            Glide.with(getContext()).load("").placeholder(R.drawable.like).into(myViewHolder.like);
        }
        if (m.getMedias().equalsIgnoreCase("nothing")){
            myViewHolder.imagetitle.setVisibility(View.GONE);
        }else{
            Glide.with(getContext())
                    .load(m.getMedias()+"?size=900x538")
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .priority(Priority.HIGH)
                    .fitCenter()
                    .into(myViewHolder.imagetitle);
        }
        myViewHolder.imagetitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.putExtra("from", "parent");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.putExtra("from", "parent");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.frontcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.putExtra("from", "parent");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.iconcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ListComment.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.putExtra("from", "parent");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.iconlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String login = session.getCustomParams("username", "nothing");
                    if (!login.equalsIgnoreCase("nothing")){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("post_id", m.getId());
                        AttributeUtils attributeUtils = new AttributeUtils(getContext());
                        String response  = attributeUtils.like("https://api.tnbg.news/api/reaction", jsonObject.toString());
                        JSONObject jsonResponse = new JSONObject(response);
                        Boolean success = jsonResponse.getBoolean("success");
                        if (success){
                            String action = jsonResponse.getString("action");
                            int likeCount = 0;
                            if (action.equalsIgnoreCase("like")){
                                likeCount = m.getLike_count()+1;
                                updateLiked(m.getId(), true, likeCount);
                                Glide.with(getContext()).load("").placeholder(R.drawable.like_filled).into(myViewHolder.like);
                            }else{
                                likeCount = m.getLike_count()-1;
                                updateLiked(m.getId(), false, likeCount);
                                Glide.with(getContext()).load("").placeholder(R.drawable.like).into(myViewHolder.like);
                            }
                            myViewHolder.text_like.setText(String.valueOf(likeCount)+"  "+getContext().getApplicationContext().getString(R.string.like));
                        }else{
                            if (jsonResponse.getString("message").equalsIgnoreCase("Token has expired")){
                                Intent i = new Intent(getContext(), MainAccount.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getContext().startActivity(i);
                            }
                        }
                    }else{
                        session.setCustomParams("LOGINSTATES", "false");
                        session.deleteSession();
                        Intent i = new Intent(getContext(), MainAccount.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(i);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        myViewHolder.iconprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.putExtra("from", "child");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
    }

    @Override
    public void sessionChange() {

    }
    private void updateLiked(Integer id, Boolean liked, Integer count){
        RealmResults results = crud.read("id", id);
        NewsModel nm = (NewsModel) results.get(0);
        crud.openObject();
        nm.setLiked(liked);
        nm.setLike_count(count);
        crud.commitObject();
    }
    public class MyViewHolder extends RealmViewHolder {

        CardView parentpost;
        TextView tanggal, status, title, frontcontent, text_comment, text_like, text_progress;
        ImageView imagetitle, comment, like, progress;
        LinearLayout iconcomment, iconlike, iconprogress;

        public MyViewHolder(CardView container) {
            super(container);
            this.parentpost = container.findViewById(R.id.parentpost);
            this.tanggal = container.findViewById(R.id.tanggal);
            this.status = container.findViewById(R.id.status);
            this.title = container.findViewById(R.id.title);
            this.frontcontent = container.findViewById(R.id.frontcontent);
            this.text_comment = container.findViewById(R.id.text_comment);
            this.text_like = container.findViewById(R.id.text_like);
            this.text_progress = container.findViewById(R.id.text_progress);
            this.imagetitle = container.findViewById(R.id.imagetitle);
            this.comment = container.findViewById(R.id.comment);
            this.like = container.findViewById(R.id.like);
            this.progress = container.findViewById(R.id.progress);
            this.iconcomment = container.findViewById(R.id.iconcomment);
            this.iconlike = container.findViewById(R.id.iconlike);
            this.iconprogress = container.findViewById(R.id.iconprogress);
        }
    }
    public HomeAdapter(Context context, RealmResults<NewsModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
        session = new Session(getContext(), this);
        newsModel = new NewsModel();
        crud = new Crud(getContext(), newsModel);
    }
}
