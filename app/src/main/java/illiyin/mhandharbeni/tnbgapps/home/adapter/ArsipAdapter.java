package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by root on 9/13/17.
 */

public class ArsipAdapter  extends RecyclerView.Adapter<ArsipAdapter.MyViewHolder> {
    private Context mContext;
    private List<NewsModel> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, tanggal;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
            title = view.findViewById(R.id.title);
            tanggal = view.findViewById(R.id.tanggal);
        }
    }
    public ArsipAdapter(Context mContext, List<NewsModel> newsList){
        this.mContext = mContext;
        this.newsList = newsList;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final ArsipAdapter.MyViewHolder h, int position) {
        final NewsModel m = this.newsList.get(position);
        h.tanggal.setText(m.getCreated_at());
        h.title.setText(m.getTitle());
        Glide.with(mContext)
                .load(m.getMedias())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(false)
                .into(h.thumbnail);
    }
    @Override
    public int getItemCount() {
        return this.newsList.size();
    }

    @Override
    public ArsipAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemarsip ,parent, false);
        return new ArsipAdapter.MyViewHolder(v);
    }

}
