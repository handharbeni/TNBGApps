package illiyin.mhandharbeni.tnbgapps.kontak;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 9/6/17.
 */

public class MainKontak extends Fragment {
    View v;
    TextView kontak;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.kontak_layout, container, false);
        kontak = v.findViewById(R.id.kontak);
        kontak.setText(Html.fromHtml(getString(R.string.placeholder_kontak)));
        return v;
    }
}
