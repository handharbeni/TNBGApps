package illiyin.mhandharbeni.tnbgapps.kontak;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 9/6/17.
 */

public class MainKontak extends Fragment {
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.kontak_layout, container, false);
        return v;
    }
}
