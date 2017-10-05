package illiyin.mhandharbeni.servicemodule.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

import illiyin.mhandharbeni.databasemodule.AdapterModel;
import illiyin.mhandharbeni.servicemodule.service.MainService;

/**
 * Created by root on 10/3/17.
 */

public class NotifikasiService extends IntentService {
    public static final String
            ACTION_LOCATION_BROADCAST = MainService.class.getName();
    AdapterModel adapterModel;
    public NotifikasiService() {
        super("Notifikasi Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        adapterModel = new AdapterModel(getBaseContext());
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        adapterModel.syncNotifikasi();
    }
}
