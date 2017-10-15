package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.databasemodule.TrendingModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.search.SearchClass;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 9/13/17.
 */

public class TrendingAdapter extends RealmBasedRecyclerViewAdapter<TrendingModel, TrendingAdapter.ViewHolder> {

    public TrendingAdapter(Context context, RealmResults<TrendingModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
        setHasStableIds(true);
    }

    public class ViewHolder extends RealmViewHolder {

        public TextView trending, penggunatrending;
        public LinearLayout layouttrending;
        public ViewHolder(LinearLayout container) {
            super(container);
            this.trending = container.findViewById(R.id.trending);
            this.penggunatrending = container.findViewById(R.id.penggunatrending);
            this.layouttrending = container.findViewById(R.id.layouttrending);
        }
    }

    @Override
    public TrendingAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.itemtrending, viewGroup, false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(final TrendingAdapter.ViewHolder myViewHolder, int i) {
        final TrendingModel trendingModel = realmResults.get(i);
        myViewHolder.trending.setText("#"+trendingModel.getName());
        myViewHolder.penggunatrending.setText(trendingModel.getCount()+" Pengguna");
        myViewHolder.layouttrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myViewHolder.layouttrending.getContext(), SearchClass.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("trending", trendingModel.getName());
                myViewHolder.layouttrending.getContext().startActivity(intent);
            }
        });
    }
}
