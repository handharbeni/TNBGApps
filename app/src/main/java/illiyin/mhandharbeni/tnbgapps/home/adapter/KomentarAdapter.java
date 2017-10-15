package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import illiyin.mhandharbeni.databasemodule.KomentarModel;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 9/29/17.
 */

public class KomentarAdapter extends RealmBasedRecyclerViewAdapter<KomentarModel, KomentarAdapter.MyViewHolder> {
    private Session session;
    public KomentarAdapter(Context context, RealmResults<KomentarModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
        setHasStableIds(true);
        session = new Session(getContext(), new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });
    }

    @Override
    public MyViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_comment, viewGroup, false);
        return new KomentarAdapter.MyViewHolder((RelativeLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(MyViewHolder myViewHolder, int i) {
        final KomentarModel m = realmResults.get(i);
        myViewHolder.nama.setText(m.getComment_author());
        myViewHolder.komen.setText(m.getContent());
        if (m.getComment_author().equalsIgnoreCase(session.getCustomParams("username", "nothing"))){
            myViewHolder.reply.setVisibility(View.GONE);
        }
        myViewHolder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setCustomParams("reply", m.getComment_author());
            }
        });
    }

    public class MyViewHolder extends RealmViewHolder {
        TextView nama;
        TextView komen;
        TextView reply;

        public MyViewHolder(RelativeLayout container) {
            super(container);
            this.nama = container.findViewById(R.id.nama);
            this.komen = container.findViewById(R.id.komen);
            this.reply = container.findViewById(R.id.reply);
        }
    }
}
