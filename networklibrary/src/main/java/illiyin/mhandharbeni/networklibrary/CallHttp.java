package illiyin.mhandharbeni.networklibrary;

import android.content.Context;

import java.io.IOException;

import illiyin.mhandharbeni.httpcalllibrary.AndroidCall;
import okhttp3.RequestBody;

/**
 * Created by root on 17/07/17.
 */

public class CallHttp {
    Context context;
    AndroidCall androidCall;
    public CallHttp(Context context) {
        this.context = context;
        androidCall = new AndroidCall(this.context);
    }
    public String get(String url){
        try {
            return androidCall.get(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String post(String url, RequestBody requestBody){
        try {
            return androidCall.post(url, requestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
