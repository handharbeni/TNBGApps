package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.servicemodule.service.MainService;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 9/19/17.
 */

public class LoadMoreService extends IntentService {
    public static final String
            ACTION_LOCATION_BROADCAST = MainService.class.getName();
    AdapterModel adapterModel;
    private String url;
    public LoadMoreService() {
        super("LoadMore Service");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Bundle extras = intent.getExtras();
        if(extras != null) {
            url = (String) extras.get("url");
            Log.d(TAG, "onHandleIntent: "+url);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        adapterModel = new AdapterModel(getBaseContext());
        sendBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }
    public void sendBroadCast(){
        this.sendBroadcast(new Intent().setAction("UPDATE NEWS").putExtra("MODE", "LOAD MORE"));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: Prosess "+url);
        try {
            adapterModel.syncNewsPaging(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
