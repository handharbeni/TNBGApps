package illiyin.mhandharbeni.tnbgapps.notifikasi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import illiyin.mhandharbeni.databasemodule.NotifikasiModel;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.notifikasi.adapter.NotifikasiAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by root on 10/2/17.
 */

public class MainNotifikasi extends Fragment {
    private View v;
    private NotifikasiModel notifikasiModel;
    private Crud crud;
    private RealmRecyclerView listnotifikasi;
    private NotifikasiAdapter notifikasiAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notifikasiModel = new NotifikasiModel();
        crud = new Crud(getActivity().getApplicationContext(), notifikasiModel);
        v = inflater.inflate(R.layout._layout_notifikasi, container, false);
        fetch_element();
        fetch_data();
        return v;
    }

    private void fetch_element(){
        listnotifikasi = v.findViewById(R.id.listnotifikasi);
    }
    private void fetch_data(){
        RealmResults rr = crud.readSorted("id", Sort.DESCENDING);
        notifikasiAdapter = new NotifikasiAdapter(getActivity().getApplicationContext(), rr, true);
        listnotifikasi.setAdapter(notifikasiAdapter);
    }
}
