package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.HomeMain;

/**
 * Created by root on 9/13/17.
 */

public class Login extends Fragment implements SessionListener {
    View v;
    private Session session;

    EditText username, password;
    TextView dosignout;
    Button signin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getActivity().getApplicationContext(), this);
        v = inflater.inflate(R.layout.login_layout, container, false);

        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        dosignout = v.findViewById(R.id.password);
        signin = v.findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_login();
            }
        });
        dosignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toregister();
            }
        });

        return v;
    }
    public void toregister(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, new Register());
        ft.commit();
    }

    public void do_login(){
        session.setCustomParams("LOGINSTATE", true);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, new HomeMain());
        ft.commit();
    }
    @Override
    public void sessionChange() {

    }
}
