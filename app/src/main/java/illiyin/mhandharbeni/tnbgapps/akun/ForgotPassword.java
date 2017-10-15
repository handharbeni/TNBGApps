package illiyin.mhandharbeni.tnbgapps.akun;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.tnbgapps.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by root on 10/13/17.
 */

public class ForgotPassword extends AppCompatActivity {
    private ImageView back;
    private TextView titleappbar;
    private EditText username, notelp;
    private Button reset;

    private String jsonBody;

    private CallHttp callHttp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetch_class();
        setContentView(R.layout.forgotpassword_layout);

        fetch_element();
    }
    private void fetch_class(){
        callHttp = new CallHttp(getApplicationContext());
    }
    private void fetch_element(){
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleappbar = findViewById(R.id.titleappbar);
        titleappbar.setText("Reset Password");

        username = findViewById(R.id.username);
        notelp = findViewById(R.id.notelp);

        reset = findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    confirmation();
                }
            }
        });

    }
    private void confirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Reset Password?");
        builder.setPositiveButton("LANJUT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                        showProgressBar();
                        do_reset();
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressBar();
                }
            }
        });
        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create();
        builder.show();
    }
    private void onSuccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Password baru telah terkirim..");
        builder.setPositiveButton("KONFIRM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create();
        builder.show();
    }
    private void onFailed(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.create();
        builder.show();
    }
    private void showProgressBar(){
        progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }
    private void hideProgressBar(){
        if (progressDialog != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }
    private void fill_json() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("phone", notelp.getText().toString());
        object.put("username", username.getText().toString());
        jsonBody = object.toString();
    }
    private Boolean validate(){
        if (username.getText().toString().isEmpty()){
            username.setError("Tidak Boleh Kosong");
            return false;
        }else if (notelp.getText().toString().isEmpty()){
            notelp.setError("Tidak Boleh Kosong");
            return false;
        }else{
            return true;
        }
    }
    private void do_reset() throws JSONException {
        fill_json();
        String url = "https://api.tnbg.news/api/auth/reset";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonBody.toString());
        String response = callHttp.post(url, body);
        JSONObject jsonResponse = new JSONObject(response);
        Boolean success = jsonResponse.getBoolean("success");
        if (success){
            onSuccess();
            hideProgressBar();
        }else{
            String message = "";
            JSONObject errorJson = jsonResponse.getJSONObject("errors");
            if (errorJson.has("phone") && errorJson.has("username")){
                message = "Username dan Nomor Handphone Tidak Valid";
            }else if (errorJson.has("username")){
                message = "Username Tidak Valid";
            }else if (errorJson.has("phone")){
                message = "Nomor Handphone Tidak Valid";
            }
            onFailed(message);
            hideProgressBar();
        }
    }
}
