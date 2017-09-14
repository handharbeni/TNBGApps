package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.location.Geocoder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import illiyin.mhandharbeni.networklibrary.CallHttp;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 06/08/17.
 */

public class Address {
    private Context mContext;
    private CallHttp callHttp;

    public Address(Context mContext) {
        this.mContext = mContext;
        this.callHttp = new CallHttp(mContext);
    }

    public Integer getDistance(String addres1, String addres2){
        String address = addres1;
        String preaddress = addres2;
        Integer valuesm = 0;
            /*url */
        String urlx = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+address+"&destinations="+preaddress+"&key=AIzaSyDhOfaPFLAfGaIckHLT73PWhXd0jNEDYqA";
//        Log.d(TAG, "getDistance: "+urlx);
        String response = callHttp.get(urlx);
//        Log.d(TAG, "getDistance: "+response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("OK")){
                JSONArray arrayRows = jsonObject.getJSONArray("rows");
                if (arrayRows.length() > 0) {
                    for (int i=0;i<arrayRows.length();i++){
                        JSONObject objectElements = arrayRows.getJSONObject(i);
                        JSONArray arrayElements = objectElements.getJSONArray("elements");
                        if (arrayElements.length()>0){
                            for (int j=0;j<arrayElements.length();j++){
                                JSONObject objectElement = arrayElements.getJSONObject(j);
                                String statusElement = objectElement.getString("status");
                                if (statusElement.equalsIgnoreCase("OK")){
                                    JSONObject distance = objectElement.getJSONObject("distance");
                                    valuesm += distance.getInt("value");
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valuesm;
    }
    public String getCurrentAddress(Double latitude, Double longitude){
        Geocoder geocoder;
        List<android.location.Address> addresses = null;
        geocoder = new Geocoder(mContext, Locale.getDefault());
        String address = "";
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0)+" "+addresses.get(0).getLocality()+" "+addresses.get(0).getAdminArea(); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
