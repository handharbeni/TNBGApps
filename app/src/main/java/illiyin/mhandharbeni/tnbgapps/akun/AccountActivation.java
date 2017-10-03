package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.utilslibrary.AttributeUtils;
import illiyin.mhandharbeni.utilslibrary.SnackBar;

/**
 * Created by root on 9/26/17.
 */

public class AccountActivation extends AppCompatActivity implements SessionListener {
    private static final String TAG = "AccounActivation";
    private Session session;
    private AttributeUtils attributeUtils;
    private ImageView back;
    private TextView titleappbar;
    private EditText activationcode;
    private Button actived;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext(), this);
        attributeUtils = new AttributeUtils(getApplicationContext());
        setContentView(R.layout.layout_activation);
        fetch_element();
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
        titleappbar.setText("Account Activation");
        activationcode = findViewById(R.id.activationcode);
        actived = findViewById(R.id.actived);
        actived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_activated();
            }
        });
    }
    private void do_activated(){
//        if (activationcode.getText().toString().equalsIgnoreCase(session.getCustomParams("code", "nothing"))){
//            /*aktivasi code sama*/
            try {
                sentServer();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }else{
//            Log.d(TAG, "do_activated: Code Tidak Valid");
//        }
    }
    private void sentServer() throws JSONException, IOException {
        JSONObject objectBody = new JSONObject();
        objectBody.put("code", activationcode.getText().toString());
        String url = "https://api.tnbg.news/api/activation";
        String response = attributeUtils.setActivation(url, objectBody.toString());
        JSONObject objectResponse = new JSONObject(response);
        if (objectResponse.getBoolean("success")){
            String message = objectResponse.getString("message");
            if (message.equalsIgnoreCase("Aktivasi selesai")){
                session.setCustomParams("LOGINSTATES", "false");
                session.deleteSession();
                finish();
            }
        }
    }
    private void showSnackBar(View v, String message){
        new SnackBar(getApplicationContext()).view(v).message(message).build().show();
    }
    @Override
    public void sessionChange() {

    }
}
