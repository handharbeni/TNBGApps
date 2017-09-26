package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.HomeMain;
import illiyin.mhandharbeni.tnbgapps.home.fragment.HomeFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/13/17.
 */

public class Login extends Fragment implements SessionListener {
    View v;
    private Session session;

    EditText username, password;
    TextView dosignout;
    Button signin;
    CallHttp callHttp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callHttp = new CallHttp(getActivity().getApplicationContext());
        session = new Session(getActivity().getApplicationContext(), this);
        v = inflater.inflate(R.layout.login_layout, container, false);

        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        dosignout = v.findViewById(R.id.dosignout);
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sentToServer("https://api.tnbg.news/api/auth");
            }
        });
    }
    private void sentToServer(String url){
        JSONObject json;
        try {
            json = new JSONObject();
            json.put("username", username.getText().toString());
            json.put("password", password.getText().toString());

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, json.toString());
            String response = callHttp.post(url, body);
            JSONObject jsonResponse = new JSONObject(response);
            Boolean success = jsonResponse.getBoolean("success");
            if (success){
                String sToken = jsonResponse.getString("token");
                JSONObject dataJson = jsonResponse.getJSONObject("data");
                String sUsername = username.getText().toString();
                String sPassword = session.encryptedString(password.getText().toString());
                String sEmail = dataJson.getString("email");
                String sAvatar = dataJson.getString("avatar");
                String sPhone = dataJson.getString("phone");
                session.setCustomParams("username", sUsername);
                session.setCustomParams("email", sEmail);
                session.setCustomParams("password", sPassword);
                session.setCustomParams("avatar", sAvatar);
                session.setCustomParams("phone", sPhone);
                session.setCustomParams("token", sToken);
                session.setCustomParams("LOGINSTATE", true);
            }else{
                session.setCustomParams("LOGINSTATE", false);
            }
        } catch (Exception ex) {
            session.setCustomParams("LOGINSTATE", false);
        }
    }
    @Override
    public void sessionChange() {
        if (session.getCustomParams("LOGINSTATE", false)){
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, new HomeMain());
            ft.commit();
        }
    }
}
