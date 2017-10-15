package illiyin.mhandharbeni.tnbgapps.notifikasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import illiyin.mhandharbeni.databasemodule.NewsModel;
import illiyin.mhandharbeni.databasemodule.NotifikasiModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.fragment.subfragment.ListComment;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by root on 10/11/17.
 */

public class NotifikasiAdapter extends RealmBasedRecyclerViewAdapter<NotifikasiModel, NotifikasiAdapter.ViewHolder> {
    private NewsModel newsModel;
    private Crud cruNewsModel;

    private NotifikasiModel notifikasiModel;
    private Crud crudNotifikasi;


    public NotifikasiAdapter(Context context, RealmResults<NotifikasiModel> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate, false);
        newsModel = new NewsModel();
        cruNewsModel = new Crud(getContext(), newsModel);

        notifikasiModel = new NotifikasiModel();
        crudNotifikasi = new Crud(getContext(), notifikasiModel);
    }

    public class ViewHolder extends RealmViewHolder {
        TextView artikel;
        RelativeLayout mainitem;
        public ViewHolder(RelativeLayout container) {
            super(container);
            mainitem = container.findViewById(R.id.mainitem);
            artikel = container.findViewById(R.id.artikel);
        }
    }
    @Override
    public NotifikasiAdapter.ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.notifikasi_item, viewGroup, false);
        return new NotifikasiAdapter.ViewHolder((RelativeLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(final NotifikasiAdapter.ViewHolder myViewHolder, int i) {
        final NotifikasiModel notifikasiModel = realmResults.get(i);
        if (notifikasiModel.getRead() == 0){
            myViewHolder.mainitem.setBackground(getContext().getResources().getDrawable(R.color.greyNotif));
        }
        myViewHolder.artikel.setText(Html.fromHtml(notifikasiModel.getContent()));
        myViewHolder.mainitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String slug = notifikasiModel.getLink().replace("/", "").split("#")[0];
                RealmResults results = cruNewsModel.read("slug", slug);
                if (results.size() > 0){

                    crudNotifikasi.openObject();
                    notifikasiModel.setRead(1);
                    crudNotifikasi.update(notifikasiModel);
                    crudNotifikasi.commitObject();

                    myViewHolder.mainitem.setBackground(getContext().getResources().getDrawable(R.color.textWhite));

                    NewsModel nm = (NewsModel) results.get(0);
                    Intent i = new Intent(getContext(), ListComment.class);
                    i.putExtra("idBerita", String.valueOf(nm.getId()));
                    i.putExtra("from", "parent");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(i);
                }
            }
        });
    }
}
