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

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by root on 9/13/17.
 */

public class Register extends Fragment implements SessionListener {
    View v;
    private Session session ;
    EditText username, notelp, password, repeatpassword;
    Button signout;
    TextView dosignin;
    CallHttp callHttp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callHttp = new CallHttp(getActivity().getApplicationContext());
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
        try {
            session.setCustomParams("LOGINSTATE", false);
            sentToServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void tologin(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, new Login());
        ft.commit();
    }
    @Override
    public void sessionChange() {

    }
    private void sentToServer() throws JSONException {
        String url = "https://api.tnbg.news/api/register";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("username", username.getText().toString());
        jsonBody.put("phone", notelp.getText().toString());
        jsonBody.put("password", password.getText().toString());
        jsonBody.put("password_confirmation", repeatpassword.getText().toString());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody.toString());
        String response = callHttp.post(url, body);
        JSONObject jsonResponse = new JSONObject(response);
        Boolean success = jsonResponse.getBoolean("success");
        /*
        *
        * 161493
        * 082372523531
        * */
        if(success){
            JSONObject objectData = jsonResponse.getJSONObject("data");
            String sUsername = objectData.getString("username");
            String sPhone = objectData.getString("phone");
            String sStatus = objectData.getString("status");
            String sProvider = objectData.getString("provider");
            Integer sCode = objectData.getInt("code");
            String sUpdated_at = objectData.getString("updated_at");
            String sCreated_at = objectData.getString("created_at");
            String sToken = jsonResponse.getString("token");

            session.setCustomParams("username", sUsername);
            session.setCustomParams("phone", sPhone);
            session.setCustomParams("status", sStatus);
            session.setCustomParams("provider", sProvider);
            session.setCustomParams("code", String.valueOf(sCode));
            session.setCustomParams("updated_at", sUpdated_at);
            session.setCustomParams("created_at", sCreated_at);
            session.setCustomParams("token", sToken);
            session.setCustomParams("password", password.getText().toString());
        }
    }
}
