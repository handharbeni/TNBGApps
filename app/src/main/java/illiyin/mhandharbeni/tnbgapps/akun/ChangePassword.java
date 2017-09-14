package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 9/13/17.
 */

public class ChangePassword extends Fragment {
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.changepassword_layout, container, false);
        return v;
    }
}
