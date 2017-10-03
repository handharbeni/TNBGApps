package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import illiyin.mhandharbeni.databasemodule.KomentarModel;
import illiyin.mhandharbeni.tnbgapps.R;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 9/29/17.
 */

public class KomentarAdapter extends RealmBasedRecyclerViewAdapter<KomentarModel, KomentarAdapter.MyViewHolder> {
    public KomentarAdapter(Context context, RealmResults<KomentarModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
    }

    @Override
    public MyViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_comment, viewGroup, false);
        return new KomentarAdapter.MyViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(MyViewHolder myViewHolder, int i) {
        final KomentarModel m = realmResults.get(i);
        myViewHolder.nama.setText(m.getComment_author());
        myViewHolder.komen.setText(m.getContent());
    }

    public class MyViewHolder extends RealmViewHolder {
        TextView nama;
        TextView komen;

        public MyViewHolder(LinearLayout container) {
            super(container);
            this.nama = container.findViewById(R.id.nama);
            this.komen = container.findViewById(R.id.komen);
        }
    }
}
