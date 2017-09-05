package illiyin.mhandharbeni.sessionlibrary;

import android.content.Context;
import android.util.Log;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 17/07/17.
 */

public class Session implements EncryptedPreferences.OnSharedPreferenceChangeListener{
    private SessionListener sessionListener;
    String defaultKey = "aJ5QElpvadHaiz7mcPNPVQx0P3Xxx0P3Xx";
    String NAMA = "NAMA",
            ALAMAT = "ALAMAT",
            NOTELP = "NOTELP",
            EMAIL = "EMAIL",
            KEY = "KEY",
            STATUS = "STATUS",
            CONNECTION = "CONNECTION";
    Context context;
    EncryptedPreferences encryptedPreferences;
    public Session(Context context, SessionListener sessionListener) {
        this.context = context;
        encryptedPreferences = new EncryptedPreferences.Builder(context)
                .withEncryptionPassword(this.context.getString(R.string.password)).build();
        encryptedPreferences.registerOnSharedPreferenceChangeListener(this);
        this.sessionListener = sessionListener;
    }
    public String getToken(){
        return encryptedPreferences.getUtils().decryptStringValue(encryptedPreferences.getString(KEY, defaultKey));
    }
    public void setCustomParams(String key, String value){
        encryptedPreferences.edit()
                .putString(key, value)
                .apply();
    }
    public void setCustomParams(String key, Integer value){
        encryptedPreferences.edit()
                .putInt(key, value)
                .apply();
    }
    public void setCustomParams(String key, Long value){
        encryptedPreferences.edit()
                .putLong(key, value)
                .apply();
    }
    public void setCustomParams(String key, Boolean value){
        encryptedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }
    public void setCustomParams(String key, Float value){
        encryptedPreferences.edit()
                .putFloat(key, value)
                .apply();
    }
    public String getCustomParams(String key, String method){
        return encryptedPreferences.getString(key, method);
    }
    public Integer getCustomParams(String key, Integer method){
        return encryptedPreferences.getInt(key, method);
    }
    public Long getCustomParams(String key, Long method){
        return encryptedPreferences.getLong(key, method);
    }
    public Boolean getCustomParams(String key, Boolean method){
        return encryptedPreferences.getBoolean(key, method);
    }
    public Float getCustomParams(String key, Float method){
        return encryptedPreferences.getFloat(key, method);
    }

    public void setSession(String nama, String alamat, String notelp, String email, String key, String status){
        encryptedPreferences.edit()
                .putString(NAMA, nama)
                .putString(ALAMAT, alamat)
                .putString(NOTELP, notelp)
                .putString(EMAIL, email)
                .putString(KEY, encryptedPreferences.getUtils().encryptStringValue(key))
                .putString(STATUS, status)
                .apply();
    }
    public Boolean checkSession(){
        Log.d(TAG, "checkSession: "+encryptedPreferences.getUtils().decryptStringValue(encryptedPreferences.getString(KEY, defaultKey)));
        if (encryptedPreferences.getUtils().decryptStringValue(encryptedPreferences.getString(KEY, defaultKey)) != null){
            if (encryptedPreferences.getUtils().decryptStringValue(
                    encryptedPreferences.getString(KEY, defaultKey)).equalsIgnoreCase("NOTHING")
                    && encryptedPreferences.getString(STATUS, "0").equalsIgnoreCase("0")){
                return false;
            }
            return true;
        }
        return false;
    }
    public void deleteSession(){
        encryptedPreferences.forceDeleteExistingPreferences();
    }

    public void setConnectionState(String state){
        encryptedPreferences.edit().putString(CONNECTION, state).apply();
    }
    public void setTrackKurir(int id_order, String id_kurir, String nama_kurir){
        encryptedPreferences.edit().putInt("id_order", id_order).putString("id_kurir", id_kurir).putString("nama_kurir", nama_kurir).apply();
    }
    public String getTrackKurir(){
        return encryptedPreferences.getString("id_kurir", "nothing");
    }
    public String getNamaKurir(){
        return encryptedPreferences.getString("nama_kurir", "nothing");
    }
    public void setLocKurir(String latitude, String longitude){
        encryptedPreferences.edit().putString("lat"+getTrackKurir(), latitude).putString("long"+getTrackKurir(), longitude).apply();
    }
    public String getLocKurir(String mode, String id_kurir){
        return encryptedPreferences.getString(mode+id_kurir, "nothing");
    }
    public String getConnectionState(){
        return encryptedPreferences.getString(CONNECTION, "0");
    }

    public void destroyListener(){
        encryptedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(EncryptedPreferences encryptedPreferences, String key) {
        sessionListener.sessionChange();
    }
}
