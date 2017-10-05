package illiyin.mhandharbeni.tnbgapps.search.adapter;

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

import illiyin.mhandharbeni.databasemodule.TempNewsModel;
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

/**
 * Created by root on 10/4/17.
 */

public class SearchAdapter extends RealmBasedRecyclerViewAdapter<TempNewsModel, SearchAdapter.MyViewHolder> implements SessionListener {
    private Session session;
    @Override
    public SearchAdapter.MyViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.itempost, viewGroup, false);
        return new SearchAdapter.MyViewHolder((CardView) v);
    }

    @Override
    public void onBindRealmViewHolder(final SearchAdapter.MyViewHolder myViewHolder, final int i) {
        final TempNewsModel m = realmResults.get(i);
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
        myViewHolder.text_progress.setText(m.getChild()+" "+getContext().getString(R.string.placeholder_progress));

        if (m.getLiked()){
            Glide.with(getContext()).load("").placeholder(R.drawable.like_filled).into(myViewHolder.like);
        }else{
            Glide.with(getContext()).load("").placeholder(R.drawable.like).into(myViewHolder.like);
        }

        Glide.with(getContext())
                .load(m.getMedias()+"?size=600x338")
                .placeholder(R.drawable.loading)
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
                            if (action.equalsIgnoreCase("like")){
                                int likeCount = m.getLike_count()+1;
                                myViewHolder.text_like.setText(likeCount+"  "+getContext().getString(R.string.like));
                                Glide.with(getContext()).load(R.drawable.like_filled).into(myViewHolder.like);
                            }else{
                                int likeCount = m.getLike_count()-1;
                                myViewHolder.text_like.setText(likeCount+"  "+getContext().getString(R.string.like));
                                Glide.with(getContext()).load(R.drawable.like).into(myViewHolder.like);
                            }
                        }
                    }else{
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
//        myViewHolder.iconsubscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    String login = session.getCustomParams("username", "nothing");
//                    if (!login.equalsIgnoreCase("nothing")){
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("post_id", m.getId());
//                        AttributeUtils attributeUtils = new AttributeUtils(getContext());
//                        String response  = attributeUtils.subscribe("https://api.tnbg.news/api/post/subscribe", jsonObject.toString());
//                        JSONObject jsonResponse = new JSONObject(response);
//                        Boolean success = jsonResponse.getBoolean("success");
//                        if (success){
//                            myViewHolder.text_subscribe.setText("Subscribed");
//                        }
//                    }else{
//                        Intent i = new Intent(getContext(), MainAccount.class);
//                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getContext().startActivity(i);
//                    }
//                } catch (IOException | JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public void sessionChange() {
        notifyDataSetChanged();
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
    public SearchAdapter(Context context, RealmResults<TempNewsModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
        session = new Session(getContext(), this);

    }
}
