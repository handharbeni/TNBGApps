package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.TrendingModel;
import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 9/13/17.
 */

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder> {
    private Context mContext;
    private List<TrendingModel> trendingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trending, penggunatrending;

        public MyViewHolder(View view) {
            super(view);
            trending = view.findViewById(R.id.trending);
            penggunatrending = view.findViewById(R.id.penggunatrending);
        }
    }
    public TrendingAdapter(Context mContext, List<TrendingModel> trendingList){
        this.mContext = mContext;
        this.trendingList = trendingList;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final TrendingAdapter.MyViewHolder h, int position) {
        final TrendingModel m = this.trendingList.get(position);
        h.trending.setText(m.getName());
        h.penggunatrending.setText(m.getCount()+" Pengguna");

    }
    @Override
    public int getItemCount() {
        return this.trendingList.size();
    }

    @Override
    public TrendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemtrending ,parent, false);
        return new TrendingAdapter.MyViewHolder(v);
    }

}
