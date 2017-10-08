package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.SnackBar;

/**
 * Created by root on 9/13/17.
 */

public class ChangePassword extends Fragment implements SessionListener {
    View v;
    private EditText oldpassword, newpassword, repeatpassword;
    private Button changepassword;
    private Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = new Session(getActivity().getApplication(), this);
        v = inflater.inflate(R.layout.changepassword_layout, container, false);
        fetch_element();
        return v;
    }
    private void fetch_element(){
        oldpassword = v.findViewById(R.id.oldpassword);
        newpassword = v.findViewById(R.id.newpassword);
        repeatpassword = v.findViewById(R.id.repeatpassword);
        changepassword = v.findViewById(R.id.changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    do_change();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void do_change() throws JSONException, IOException {
        JSONObject objectForm = new JSONObject();
        objectForm.put("old", oldpassword.getText().toString());
        objectForm.put("password", newpassword.getText().toString());
        objectForm.put("password_confirmation", repeatpassword.getText().toString());

        String url = "https://api.tnbg.news/api/change/password";

        AttributeUtils attributeUtils = new AttributeUtils(getActivity().getApplicationContext());
        String response = attributeUtils.changePassword(url, objectForm.toString());
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.getBoolean("success")){
            /*do logout*/
            session.setCustomParams("LOGINSTATES", "false");
            session.deleteSession();
            Bundle bundleLogin = new Bundle();
            bundleLogin.putString("from", "nav");
            Fragment fragment = new Login();
            fragment.setArguments(bundleLogin);
            changeFragment(fragment, true, "login");

        }else{
            new SnackBar(getActivity().getApplicationContext())
                    .view(v)
                    .message("Change Password Failed")
                    .build()
                    .show();
        }
    }
    private void changeFragment(Fragment fragment, Boolean backStack, String name){
        Fragment lastFragment = getActivity().getSupportFragmentManager().findFragmentByTag("FragmentMain");
        if (lastFragment != null){
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment, "FragmentMain");
        ft.commit();
        if (backStack){
            session.setCustomParams("LastFragment", name);
        }
    }
    @Override
    public void sessionChange() {

    }
}
