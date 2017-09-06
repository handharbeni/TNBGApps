package illiyin.mhandharbeni.tnbgapps.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/6/17.
 */

public class SearchMain extends Fragment implements SessionListener {
    View v;
    private Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getActivity().getApplicationContext(), this);
        v = inflater.inflate(R.layout.search_layout, container, false);
        return v;
    }

    @Override
    public void sessionChange() {
        String queryString = session.getCustomParams("Query", "nothing");
        if (queryString != null){
            Log.d(TAG, "sessionChange: "+queryString);
        }
    }
}
