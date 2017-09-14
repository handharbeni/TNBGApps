package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.servicemodule.service.MainService;

/**
 * Created by root on 17/07/17.
 */

public class NewsService extends IntentService {
    public static final String
            ACTION_LOCATION_BROADCAST = MainService.class.getName();
    AdapterModel adapterModel;
    public NewsService() {
        super("Menu Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        adapterModel = new AdapterModel(getBaseContext());
        sendBroadCast();
        return super.onStartCommand(intent, flags, startId);
    }
    public void sendBroadCast(){
        this.sendBroadcast(new Intent().setAction("UPDATE NEWS").putExtra("MODE", "UPDATE LIST"));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncNews();
    }
}
