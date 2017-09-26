package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.DetailBerita;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import static android.content.ContentValues.TAG;

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
    public void onBindRealmViewHolder(HomeAdapter.MyViewHolder myViewHolder, int i) {
        final NewsModel m = realmResults.get(i);
        myViewHolder.tanggal.setText(m.getCreated_at());
        myViewHolder.status.setText(String.valueOf(m.getStatus()));
        myViewHolder.title.setText(m.getTitle());
        myViewHolder.frontcontent.setText(m.getExcerpt());
        myViewHolder.text_comment.setText(m.getComment_count()+" "+getContext().getString(R.string.comment));
        myViewHolder.text_like.setText(m.getLike_count()+"  "+getContext().getString(R.string.like));
        myViewHolder.text_subscribe.setText(m.getSubscribe()?"Subscribed":"Subscribe");
        Log.d(TAG, "onBindViewHolder: "+m.getMedias());
        Glide.with(getContext())
                .load(m.getMedias())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(false)
                .into(myViewHolder.imagetitle);
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
    }

    public class MyViewHolder extends RealmViewHolder {

        CardView parentpost;
        TextView tanggal, status, title, frontcontent, text_comment, text_like, text_subscribe;
        ImageView imagetitle, comment, like, subscribe;

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
        }
    }
    public HomeAdapter(Context context, RealmResults<NewsModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
    }
}
