package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
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

/**
 * Created by root on 9/13/17.
 */

public class Register extends Fragment implements SessionListener {
    View v;
    private Session session ;
    EditText username, notelp, password, repeatpassword;
    Button signout;
    TextView dosignin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getActivity().getApplicationContext(), this);
        v = inflater.inflate(R.layout.register_layout, container, false);
        username = v.findViewById(R.id.username);
        notelp = v.findViewById(R.id.notelp);
        password = v.findViewById(R.id.password);
        repeatpassword = v.findViewById(R.id.repeatpassword);

        signout = v.findViewById(R.id.signout);

        dosignin = v.findViewById(R.id.dosignin);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_register();
            }
        });

        dosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tologin();
            }
        });

        return v;
    }
    public void do_register(){
        session.setCustomParams("LOGINSTATE", false);
    }
    public void tologin(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, new Login());
        ft.commit();
    }
    @Override
    public void sessionChange() {

    }
}
