package illiyin.mhandharbeni.databasemodule;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import illiyin.mhandharbeni.networklibrary.CallHttp;
import illiyin.mhandharbeni.realmlibrary.Crud;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 17/07/17.
 */

public class AdapterModel implements SessionListener{
    String server;

    private String endPointMenu;
    private String endPointOutlet;
    private String endPointResto;
    private String endPointOrder;
    private String endPointKurir;
    private String endPointFeeDeliv;
    private DatabaseListener databaseListener;


    Context context;
    Crud crud;
    CallHttp callHttp;
    Session session;

    public AdapterModel(Context context) {
        this.context = context;
        callHttp = new CallHttp(context);
        session = new Session(context, this);
        initEndPoint();
    }

    private void initEndPoint(){
        server = context.getString(R.string.module_server);
        endPointMenu = server+context.getString(R.string.endpointmenu);
        endPointOutlet = server+context.getString(R.string.endpointoutlet);
        endPointResto = server+context.getString(R.string.endpointresto);
        endPointOrder = server+context.getString(R.string.endpointorder);
        endPointKurir = server+context.getString(R.string.endpointkurir);
        endPointFeeDeliv = server+context.getString(R.string.endpointfeedeliv);
    }
    public void syncDataMenu(RealmObject realmObject){
        String token = session.getToken();
        crud = new Crud(context, realmObject);

        endPointMenu += token;
        String response = callHttp.get(endPointMenu);
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean returns = jsonObject.getBoolean("return");
            if (returns){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (!crud.checkDuplicate("id", Integer.valueOf(object.getString("id")))){
                            /*insert*/
                            ModelMenu modelMenu = new ModelMenu();
                            modelMenu.setId(object.getInt("id"));
                            modelMenu.setNama(object.getString("nama"));
                            modelMenu.setGambar(object.getString("gambar"));
                            modelMenu.setHarga(object.getString("harga"));
                            modelMenu.setKategori(object.getString("kategori"));
                            modelMenu.setSha(object.getString("sha"));
                            crud.create(modelMenu);
//                            databaseListener.onMenuChange();
                        }else{
                            /*check apakah sha nya berubah*/
                            RealmResults results = crud.read("id", Integer.valueOf(object.getString("id")));
                            ModelMenu menuResult = (ModelMenu) results.get(0);
                            if (!menuResult.getSha().equalsIgnoreCase(object.getString("sha"))){
                                /*update menu*/

                                crud.openObject();
                                ModelMenu updateMenu = (ModelMenu) crud.getRealmObject("id", Integer.valueOf(object.getString("id")));
                                updateMenu.setNama(object.getString("nama"));
                                updateMenu.setGambar(object.getString("gambar"));
                                updateMenu.setHarga(object.getString("harga"));
                                updateMenu.setKategori(object.getString("kategori"));
                                updateMenu.setSha(object.getString("sha"));
                                crud.update(updateMenu);
                                crud.commitObject();
//                                databaseListener.onMenuChange();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        crud.closeRealm();
        session.destroyListener();
    }
    public void syncDataOutlet(RealmObject realmObject){
        crud = new Crud(context, realmObject);

        String response = callHttp.get(endPointOutlet);
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean returns = jsonObject.getBoolean("return");
            if (returns){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0){
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (!crud.checkDuplicate("id", Integer.valueOf(object.getString("id_outlet")))){
                            /*insert new*/
                            ModelOutlet modelOutlet = new ModelOutlet();
                            modelOutlet.setId(Integer.valueOf(object.getString("id_outlet")));
                            modelOutlet.setOutlet(object.getString("nama_outlet"));
                            modelOutlet.setAlamat(object.getString("alamat"));
                            modelOutlet.setLatitude(object.getString("latitude"));
                            modelOutlet.setLongitude(object.getString("longitude"));
                            modelOutlet.setSha(object.getString("sha"));
                            crud.create(modelOutlet);
                        }else{
                            /*check if sha different*/
                            RealmResults results = crud.read("id", Integer.valueOf(object.getString("id_outlet")));
                            ModelOutlet modelOutlet = (ModelOutlet) results.get(0);
                            if (!modelOutlet.getSha().equalsIgnoreCase(object.getString("sha"))){
                                /*update data*/
                                crud.openObject();
                                ModelOutlet updateOutlet = (ModelOutlet) crud.getRealmObject("id", Integer.valueOf(object.getString("id_outlet")));
                                updateOutlet.setOutlet(object.getString("nama_outlet"));
                                updateOutlet.setAlamat(object.getString("alamat"));
                                updateOutlet.setLatitude(object.getString("latitude"));
                                updateOutlet.setLongitude(object.getString("longitude"));
                                updateOutlet.setSha(object.getString("sha"));
                                crud.update(updateOutlet);
                                crud.commitObject();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        crud.closeRealm();
        session.destroyListener();
    }
    public void syncDataResto(RealmObject realmObject){
        crud = new Crud(context, realmObject);
        crud.closeRealm();
        session.destroyListener();
        /*get data from server*/

        /*cek apakah sudah tersedia didalam database*/

        /*jika sudah maka cek sha*/

        /*jika sha tidak sama maka update data*/

        /*jika sama biarkan*/

        /*jika belum ada dalam database maka insert data baru tersebut*/
    }
    public void syncOrder(RealmObject realmObject){
        String token = session.getToken();
        endPointOrder += token;
        String response = callHttp.get(endPointOrder);
        try {
            JSONObject jsonObject = new JSONObject(response);
            Boolean returns = jsonObject.getBoolean("return");
            if (returns){
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length() > 0){
                    for (int i=0;i<jsonArray.length();i++){
                        crud = new Crud(context, realmObject);
                        JSONObject dataOrder = jsonArray.getJSONObject(i);

                        if (!crud.checkDuplicate("id", dataOrder.getInt("id_order"))){
                            /*new insert*/
                            ModelOrder modelOrder = new ModelOrder();
                            modelOrder.setId(Integer.valueOf(dataOrder.getString("id_order")));
                            modelOrder.setId_user(Integer.valueOf(dataOrder.getString("id_user")));

                            if (!dataOrder.getString("kurir").equalsIgnoreCase("nothing")){
                                JSONArray arrayKurir = dataOrder.getJSONArray("kurir");
                                if (arrayKurir.length() > 0){
                                    for (int j=0;j<arrayKurir.length();j++){
                                        JSONObject objectKurir = arrayKurir.getJSONObject(j);
                                        modelOrder.setId_kurir(Integer.valueOf(objectKurir.getString("id")));
                                    }
                                }else{
                                    modelOrder.setId_kurir(0);
                                }
                            }else{
                                modelOrder.setId_kurir(0);
                            }

                            JSONArray arrayOutlet = dataOrder.getJSONArray("outlet");
                            if (arrayOutlet.length()>0){
                                for (int k=0;k<arrayOutlet.length();k++){
                                    JSONObject objecOutlet = arrayOutlet.getJSONObject(k);
                                    modelOrder.setAlamat(objecOutlet.getString("outlet"));
                                    modelOrder.setLatitude(objecOutlet.getString("latitude"));
                                    modelOrder.setLongitude(objecOutlet.getString("longitude"));
                                }
                            }else{
                                modelOrder.setAlamat("");
                                modelOrder.setLatitude("");
                                modelOrder.setLongitude("");
                            }

                            modelOrder.setNama_customer(dataOrder.getString("nama_user"));
                            modelOrder.setLatitude_customer(dataOrder.getString("lat_order"));
                            modelOrder.setLongitude_customer(dataOrder.getString("long_order"));

                            modelOrder.setAlamat_customer(dataOrder.getString("alamat_order"));
                            modelOrder.setDelivery_fee(dataOrder.getString("delivery_fee"));

                            modelOrder.setEmail_customer(dataOrder.getString("email"));
                            modelOrder.setTotal_belanja(dataOrder.getString("total_belanja"));
                            modelOrder.setTanggal(dataOrder.getString("tanggal"));
                            modelOrder.setJam(dataOrder.getString("jam"));
                            modelOrder.setStatus(dataOrder.getJSONObject("status").getString("key"));
                            modelOrder.setSha(dataOrder.getString("sha"));
                            crud.create(modelOrder);
                            JSONArray arrayItem = dataOrder.getJSONArray("items");
                            if (arrayItem.length() > 0){
                                for (int z = 0; z< arrayItem.length();z++){
                                    JSONObject objectItem = arrayItem.getJSONObject(z);
                                    ItemOrder itemOrder = new ItemOrder();
                                    itemOrder.setId(Integer.valueOf(objectItem.getString("id")));
                                    itemOrder.setId_order(Integer.valueOf(objectItem.getString("id_order")));
                                    itemOrder.setId_menu(Integer.valueOf(objectItem.getJSONObject("menu").getString("id_menu")));
                                    itemOrder.setNama_menu(objectItem.getJSONObject("menu").getString("nama_menu"));
                                    itemOrder.setGambar(objectItem.getJSONObject("menu").getString("gambar"));
                                    itemOrder.setSha(objectItem.getJSONObject("menu").getString("sha"));
                                    itemOrder.setJumlah(objectItem.getString("jumlah"));
                                    itemOrder.setHarga(objectItem.getString("harga"));
                                    itemOrder.setTotal_harga(objectItem.getString("total_harga"));
                                    itemOrder.setKeterangan(objectItem.getString("keterangan"));
                                    addItemOrder(itemOrder);
                                }
                            }
                        }else{
                            /*check update*/
                            RealmResults results = crud.read("id", Integer.valueOf(dataOrder.getString("id_order")));
                            ModelOrder modelOrder = (ModelOrder) results.get(0);
                            if (!modelOrder.getSha().equalsIgnoreCase(dataOrder.getString("sha"))){
                                /*delete old id*/
                                crud.delete("id",Integer.valueOf(dataOrder.getString("id_order")));
                                /*update data order*/
                                ModelOrder modelOrderx = new ModelOrder();
                                modelOrderx.setId(Integer.valueOf(dataOrder.getString("id_order")));
                                modelOrderx.setId_user(Integer.valueOf(dataOrder.getString("id_user")));
                                if (!dataOrder.getString("kurir").equalsIgnoreCase("nothing")){
                                    JSONArray arrayKurir = dataOrder.getJSONArray("kurir");
                                    if (arrayKurir.length() > 0){
                                        for (int j=0;j<arrayKurir.length();j++){
                                            JSONObject objectKurir = arrayKurir.getJSONObject(j);
                                            modelOrderx.setId_kurir(Integer.valueOf(objectKurir.getString("id")));
                                        }
                                    }else{
                                        modelOrderx.setId_kurir(0);
                                    }
                                }else{
                                    modelOrderx.setId_kurir(0);
                                }

                                JSONArray arrayOutlet = dataOrder.getJSONArray("outlet");
                                if (arrayOutlet.length()>0){
                                    for (int k=0;k<arrayOutlet.length();k++){
                                        JSONObject objecOutlet = arrayOutlet.getJSONObject(k);
                                        modelOrderx.setAlamat(objecOutlet.getString("outlet"));
                                        modelOrderx.setLatitude(objecOutlet.getString("latitude"));
                                        modelOrderx.setLongitude(objecOutlet.getString("longitude"));
                                    }
                                }else{
                                    modelOrderx.setAlamat("");
                                    modelOrderx.setLatitude("");
                                    modelOrderx.setLongitude("");
                                }


                                modelOrderx.setNama_customer(dataOrder.getString("nama_user"));

                                modelOrderx.setAlamat_customer(dataOrder.getString("alamat_order"));
                                modelOrderx.setDelivery_fee(dataOrder.getString("delivery_fee"));

                                modelOrderx.setEmail_customer(dataOrder.getString("email"));
                                modelOrderx.setTotal_belanja(dataOrder.getString("total_belanja"));
                                modelOrderx.setTanggal(dataOrder.getString("tanggal"));
                                modelOrderx.setJam(dataOrder.getString("jam"));
                                modelOrderx.setStatus(dataOrder.getJSONObject("status").getString("key"));
                                modelOrderx.setSha(dataOrder.getString("sha"));
                                crud.create(modelOrder);
                                JSONArray arrayItem = dataOrder.getJSONArray("items");
                                if (arrayItem.length() > 0){
                                    for (int z = 0; z< arrayItem.length();z++){
                                        JSONObject objectItem = arrayItem.getJSONObject(z);
                                        ItemOrder itemOrder = new ItemOrder();
                                        itemOrder.setId(Integer.valueOf(objectItem.getString("id")));
                                        itemOrder.setId_order(Integer.valueOf(objectItem.getString("id_order")));
                                        itemOrder.setId_menu(Integer.valueOf(objectItem.getJSONObject("menu").getString("id_menu")));
                                        itemOrder.setNama_menu(objectItem.getJSONObject("menu").getString("nama_menu"));
                                        itemOrder.setGambar(objectItem.getJSONObject("menu").getString("gambar"));
                                        itemOrder.setSha(objectItem.getJSONObject("menu").getString("sha"));
                                        itemOrder.setJumlah(objectItem.getString("jumlah"));
                                        itemOrder.setHarga(objectItem.getString("harga"));
                                        itemOrder.setTotal_harga(objectItem.getString("total_harga"));
                                        itemOrder.setKeterangan(objectItem.getString("keterangan"));
                                        addItemOrder(itemOrder);
                                    }
                                }
                            }

                        }

                        crud.closeRealm();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        session.destroyListener();
    }

    public void addItemOrder(ItemOrder itemOrder){
        crud = new Crud(context, itemOrder);
        crud.delete("id_order", itemOrder.getId_order());


        ItemOrder newItemOrder = new ItemOrder();
        newItemOrder.setId(itemOrder.getId());
        newItemOrder.setId_order(itemOrder.getId_order());
        newItemOrder.setId_menu(itemOrder.getId_menu());
        newItemOrder.setNama_menu(itemOrder.getNama_menu());
        newItemOrder.setGambar(itemOrder.getGambar());
        newItemOrder.setSha(itemOrder.getSha());
        newItemOrder.setJumlah(itemOrder.getJumlah());
        newItemOrder.setHarga(itemOrder.getHarga());
        newItemOrder.setTotal_harga(itemOrder.getTotal_harga());
        newItemOrder.setKeterangan(itemOrder.getKeterangan());

        crud.create(newItemOrder);
        crud.closeRealm();
    }

    public void trackKurir(){
        String token = session.getToken();
        String id_kurir = session.getTrackKurir()!="nothing"?session.getTrackKurir():"0";

        endPointKurir += "?kurir_id="+id_kurir+"&token="+token;
        String response = callHttp.get(endPointKurir);
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean returns = jsonObject.getBoolean("return");
            if (returns){
                /*idkurir ditemukan*/
                JSONObject objectData = jsonObject.getJSONObject("data");
                String sIdKurir = objectData.getString("id_kurir");
                String sNamaKurir = objectData.getString("nama_kurir");
                String sLatitude = objectData.getString("latitude");
                String sLongitude = objectData.getString("longitude");

                session.setLocKurir(sLatitude, sLongitude);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getFeeDeliv(){
        String response = callHttp.get(endPointFeeDeliv);
        try {
            JSONObject jsonObject = new JSONObject(response);
            boolean returns = jsonObject.getBoolean("return");
            if (returns){
                JSONArray arrayData = jsonObject.getJSONArray("data");
                if (arrayData.length()>0){
                    for (int i=0;i<arrayData.length();i++){
                        JSONObject objectData = arrayData.getJSONObject(i);
                        String id = objectData.getString("id");
                        if (id.equalsIgnoreCase("1")){
                            session.setCustomParams("FEEDELIV", objectData.getString("value"));
                            Log.d(TAG, "getFeeDeliv: "+objectData.getString("value"));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void sessionChange() {

    }
}
