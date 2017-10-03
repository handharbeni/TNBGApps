package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.DetailBerita;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.ListComment;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.DateFormat;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 9/12/17.
 */

public class HomeAdapter extends RealmBasedRecyclerViewAdapter<NewsModel, HomeAdapter.MyViewHolder> {
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
        myViewHolder.status.setText(String.valueOf(m.getStatus()==0?"On Going":"Complete"));
        myViewHolder.title.setText(m.getTitle());
        myViewHolder.frontcontent.setText(m.getExcerpt());
        myViewHolder.text_comment.setText(m.getComment_count()+" "+getContext().getString(R.string.comment));
        myViewHolder.text_like.setText(m.getLike_count()+"  "+getContext().getString(R.string.like));
        myViewHolder.text_subscribe.setText(m.getSubscribe()?"Subscribed":"Subscribe");

        if (m.getLiked()){
            Glide.with(getContext()).load(R.drawable.like_filled).into(myViewHolder.like);
        }else{
            Glide.with(getContext()).load(R.drawable.like).into(myViewHolder.like);
        }

        Glide.with(getContext())
                .load(m.getMedias()+"?size=600x338")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(myViewHolder.imagetitle);
        myViewHolder.imagetitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.frontcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.iconcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ListComment.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });
        myViewHolder.iconlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("post_id", m.getId());
                    AttributeUtils attributeUtils = new AttributeUtils(getContext());
                    String response  = attributeUtils.like("https://api.tnbg.news/api/reaction", jsonObject.toString());
                    JSONObject jsonResponse = new JSONObject(response);
                    Boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        String action = jsonResponse.getString("action");
                        if (action.equalsIgnoreCase("like")){
                            int likeCount = m.getLike_count()+1;
                            myViewHolder.text_subscribe.setText(likeCount+"  "+getContext().getString(R.string.like));
                            Glide.with(getContext()).load(R.drawable.like_filled).into(myViewHolder.like);
                        }else{
                            int likeCount = m.getLike_count()-1;
                            myViewHolder.text_subscribe.setText(likeCount+"  "+getContext().getString(R.string.like));
                            Glide.with(getContext()).load(R.drawable.like).into(myViewHolder.like);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        myViewHolder.iconsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("post_id", m.getId());
                    AttributeUtils attributeUtils = new AttributeUtils(getContext());
                    String response  = attributeUtils.subscribe("https://api.tnbg.news/api/post/subscribe", jsonObject.toString());
                    JSONObject jsonResponse = new JSONObject(response);
                    Boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        myViewHolder.text_subscribe.setText("Subscribed");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class MyViewHolder extends RealmViewHolder {

        CardView parentpost;
        TextView tanggal, status, title, frontcontent, text_comment, text_like, text_subscribe;
        ImageView imagetitle, comment, like, subscribe;
        LinearLayout iconcomment, iconlike, iconsubscribe;

        public MyViewHolder(CardView container) {
            super(container);
            this.parentpost = container.findViewById(R.id.parentpost);
            this.tanggal = container.findViewById(R.id.tanggal);
            this.status = container.findViewById(R.id.status);
            this.title = container.findViewById(R.id.title);
            this.frontcontent = container.findViewById(R.id.frontcontent);
            this.text_comment = container.findViewById(R.id.text_comment);
            this.text_like = container.findViewById(R.id.text_like);
            this.text_subscribe = container.findViewById(R.id.text_subscribe);
            this.imagetitle = container.findViewById(R.id.imagetitle);
            this.comment = container.findViewById(R.id.comment);
            this.like = container.findViewById(R.id.like);
            this.subscribe = container.findViewById(R.id.subscribe);
            this.iconcomment = container.findViewById(R.id.iconcomment);
            this.iconlike = container.findViewById(R.id.iconlike);
            this.iconsubscribe = container.findViewById(R.id.iconsubscribe);
        }
    }
    public HomeAdapter(Context context, RealmResults<NewsModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
    }
}
