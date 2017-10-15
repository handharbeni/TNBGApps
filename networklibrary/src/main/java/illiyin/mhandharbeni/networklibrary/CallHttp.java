package illiyin.mhandharbeni.networklibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import illiyin.mhandharbeni.httpcalllibrary.AndroidCall;
import okhttp3.RequestBody;

/**
 * Created by root on 17/07/17.
 */

public class CallHttp {
    Context context;
    AndroidCall androidCall;

    Boolean connected;

    public CallHttp(Context context) {
        this.context = context;
        androidCall = new AndroidCall(this.context);
    }
    public String get(String url){
        if (isOnlines()){
            try {
                return androidCall.get(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return null;
        }
        return null;
    }
    public String post(String url, RequestBody requestBody){
        if (isOnlines()){
            try {
                return androidCall.post(url, requestBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return null;
        }
        return null;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public boolean isOnlines() {
//        try {
//            int timeoutMs = 1500;
//            Socket sock = new Socket();
//            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 80);
//
//            sock.connect(sockaddr, timeoutMs);
//            sock.close();
//
//            return true;
//        } catch (IOException e) { return false; }
        return true;
    }
}
