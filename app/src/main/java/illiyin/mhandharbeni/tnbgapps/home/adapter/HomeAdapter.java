package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.DetailBerita;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/12/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mContext;
    private List<NewsModel> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView parentpost;
        TextView tanggal, status, title, frontcontent, text_comment, text_like, text_subscribe;
        ImageView imagetitle, comment, like, subscribe;

        public MyViewHolder(View view) {
            super(view);
            parentpost = view.findViewById(R.id.parentpost);
            tanggal = view.findViewById(R.id.tanggal);
            status = view.findViewById(R.id.status);
            title = view.findViewById(R.id.title);
            frontcontent = view.findViewById(R.id.frontcontent);
            text_comment = view.findViewById(R.id.text_comment);
            text_like = view.findViewById(R.id.text_like);
            text_subscribe = view.findViewById(R.id.text_subscribe);
            imagetitle = view.findViewById(R.id.imagetitle);
            comment = view.findViewById(R.id.comment);
            like = view.findViewById(R.id.like);
            subscribe = view.findViewById(R.id.subscribe);
        }
    }
    public HomeAdapter(Context mContext, List<NewsModel> newsList){
        this.mContext = mContext;
        this.newsList = newsList;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder h, int position) {
        final NewsModel m = this.newsList.get(position);
        h.tanggal.setText(m.getCreated_at());
        h.status.setText(String.valueOf(m.getStatus()));
        h.title.setText(m.getTitle());
        h.frontcontent.setText(m.getExcerpt());
        h.text_comment.setText(m.getComment_count()+" "+mContext.getString(R.string.comment));
        h.text_like.setText(m.getLike_count()+"  "+mContext.getString(R.string.comment));
        h.text_subscribe.setText(m.getSubscribe()?"Subscribed":"Subscribe");
        Log.d(TAG, "onBindViewHolder: "+m.getMedias());
        Glide.with(mContext)
                .load(m.getMedias())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(false)
                .into(h.imagetitle);
        h.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
        h.frontcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailBerita.class);
                i.putExtra("idBerita", String.valueOf(m.getId()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.newsList.size();
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itempost ,parent, false);
        return new HomeAdapter.MyViewHolder(v);
    }

}
