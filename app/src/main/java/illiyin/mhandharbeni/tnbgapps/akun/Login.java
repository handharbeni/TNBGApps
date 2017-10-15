package illiyin.mhandharbeni.tnbgapps.akun;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.HomeMain;
import illiyin.mhandharbeni.utilslibrary.SnackBar;
import illiyin.mhandharbeni.utilslibrary.SnackBarListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/13/17.
 */

public class Login extends Fragment implements SessionListener, SnackBarListener {
    View v;
    private Session session;

    EditText username, password;
    TextView dosignout;
    Button signin;
    CallHttp callHttp;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog dialog;
    private String from;

    private TextView forgot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            from = bundle.getString("from");
        }else{
            from = "class";
        }
        callHttp = new CallHttp(getActivity().getApplicationContext());
        session = new Session(getActivity().getApplicationContext(), this);
        v = inflater.inflate(R.layout.login_layout, container, false);

        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        dosignout = v.findViewById(R.id.dosignout);
        signin = v.findViewById(R.id.signin);
        forgot = v.findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), ForgotPassword.class);
                getActivity().startActivity(i);
            }
        });

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
        if (username.getText().toString().isEmpty()){
            username.setError("Tidak Boleh Kosong");
        }else if(password.getText().toString().isEmpty()){
            password.setError("Tidak Boleh Kosong");
        }else{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDialog();
                    sentToServer("https://api.tnbg.news/api/auth");
                }
            });
        }
    }
    private void showDialog(){
        signin.setEnabled(false);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                signin.setEnabled(false);
//                signin.setText("Loginin..");
//                dialog = new ProgressDialog(getActivity());
//                dialog.setCancelable(false);
//                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                dialog.show();
//            }
//        });
    }
    private void dismissDialog(){
        signin.setEnabled(true);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                signin.setEnabled(true);
//                signin.setText(getString(R.string.placeholder_signin));
//                dialog.dismiss();
//            }
//        });
    }
    private void sentToServer(String url){
        try {
            JSONObject json;
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
                String sEmail = dataJson.getString("email")!="null"?dataJson.getString("email"):"";
                String sAvatar = dataJson.getString("avatar")!="null"?dataJson.getString("avatar"):"";
                String sPhone = dataJson.getString("phone");
                Integer iStatus = dataJson.getInt("status");

                session.setCustomParams("username", sUsername);
                session.setCustomParams("email", sEmail);
                session.setCustomParams("password", sPassword);
                session.setCustomParams("avatar", sAvatar);
                session.setCustomParams("phone", sPhone);
                session.setCustomParams("token", sToken);
                session.setCustomParams("status", iStatus);
                if (from.equalsIgnoreCase("nav")){
                    showSnackBar(v, "Login Berhasil");
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.mainframe, new HomeMain());
                    ft.commit();
                }else{
                    showSnackBar(v, "Login Berhasil");
                    getActivity().finish();
                }
                showSnackBar(v, "Login Berhasil");
            }else{
                if (jsonResponse.getString("message").equalsIgnoreCase("invalid_credentials")){
                    showSnackBar(v, "Username / Password tidak ditemukan");
                }

            }
        } catch (Exception ex) {
            showSnackBar(v, "Tidak ada koneksi");

        }
    }
    public void showSnackBar(View v, String message){
        new SnackBar(getActivity().getApplicationContext())
                .view(v)
                .message(message)
                .build()
                .listener(this)
                .show();
    }
    @Override
    public void sessionChange() {
    }

    @Override
    public void onDismiss() {
        dismissDialog();
    }
}
