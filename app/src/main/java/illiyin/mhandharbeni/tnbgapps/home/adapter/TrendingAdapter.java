package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import illiyin.mhandharbeni.databasemodule.TrendingModel;
import illiyin.mhandharbeni.tnbgapps.R;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 9/13/17.
 */

public class TrendingAdapter extends RealmBasedRecyclerViewAdapter<TrendingModel, TrendingAdapter.ViewHolder> {


    public TrendingAdapter(Context context, RealmResults<TrendingModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
    }

    public class ViewHolder extends RealmViewHolder {

        public TextView trending, penggunatrending;
        public ViewHolder(LinearLayout container) {
            super(container);
            this.trending = container.findViewById(R.id.trending);
            penggunatrending = container.findViewById(R.id.penggunatrending);
        }
    }

    @Override
    public TrendingAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.itemtrending, viewGroup, false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(TrendingAdapter.ViewHolder myViewHolder, int i) {
        TrendingModel trendingModel = realmResults.get(i);
        myViewHolder.trending.setText("#"+trendingModel.getName());
        myViewHolder.penggunatrending.setText(trendingModel.getCount()+" Pengguna");
    }
}
