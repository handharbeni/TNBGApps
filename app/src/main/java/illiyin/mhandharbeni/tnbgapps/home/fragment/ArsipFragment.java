package illiyin.mhandharbeni.tnbgapps.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 9/5/17.
 */

public class ArsipFragment extends Fragment {
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout._layout_fragmentarsip, container, false);
        return v;
    }
}
