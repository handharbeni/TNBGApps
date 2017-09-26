package illiyin.mhandharbeni.tnbgapps;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import illiyin.mhandharbeni.servicemodule.ServiceAdapter;
import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.akun.ChangePassword;
import illiyin.mhandharbeni.tnbgapps.akun.Login;
import illiyin.mhandharbeni.tnbgapps.home.HomeMain;
import illiyin.mhandharbeni.tnbgapps.kontak.MainKontak;
import illiyin.mhandharbeni.tnbgapps.search.SearchMain;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SessionListener {

    private static final String TAG = "NavigationActivity";
    private Session session;
    private NavigationView navigationView;
    private SearchView searchView;
    private Menu menuSearchView;
    private TextView headertitle, headersub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = new String[11];
        permissions[0] = Manifest.permission.CAMERA;
        permissions[1] = Manifest.permission.INTERNET;
        permissions[2] = Manifest.permission.WAKE_LOCK;
        permissions[3] = Manifest.permission.LOCATION_HARDWARE;
        permissions[4] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[5] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[6] = Manifest.permission.READ_PHONE_STATE;
        permissions[7] = Manifest.permission.ACCESS_NETWORK_STATE;
        permissions[8] = Manifest.permission.ACCESS_WIFI_STATE;
        permissions[9] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        permissions[10] = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    5
            );
        }



        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());
        StrictMode.setThreadPolicy(old);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init_serviceadapter();
        setContentView(R.layout.activity_navigation);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        headersub = header.findViewById(R.id.headersub);
        headertitle = header.findViewById(R.id.headertitle);
        session = new Session(getApplicationContext(), this);
        session.setCustomParams("LastFragment", "home");
        setNavigation();
    }
    private void setNavigation(){
        Boolean login = session.getCustomParams("LOGINSTATE", false);
        Log.d(TAG, "setNavigation: "+login);
        if (login){
            headersub.setVisibility(View.VISIBLE);
            headertitle.setText(session.getCustomParams("username", "NOTHING"));
            headersub.setText(session.getCustomParams("email", "-"));
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_signin);
        }else{
            headersub.setVisibility(View.GONE);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_signout);
        }
        setFirstItemNavigationView();
    }
    private void init_serviceadapter(){
        ServiceAdapter serviceAdapter = new ServiceAdapter(getApplicationContext());
        serviceAdapter.startService();
    }
    private void setFirstItemNavigationView() {
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        menuSearchView = menu;
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setQueryHint(getString(R.string.placeholder_cariberita));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLayout();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Fragment fragment = new HomeMain();
            changeFragment(fragment, true, "home");
        }else if (id == R.id.nav_kontak){
            Fragment fragment = new MainKontak();
            changeFragment(fragment, true, "kontak");
            setNavigation();
        }else if(id == R.id.nav_notifikasi){

        }else if(id == R.id.nav_changepassword){
            Fragment fragment = new ChangePassword();
            changeFragment(fragment, true, "changePassword");
            setNavigation();
        }else if(id == R.id.nav_notifikasi){

        }else if(id == R.id.nav_signout){
            session.setCustomParams("LOGINSTATE", false);
            session.deleteSession();
            Fragment fragment = new Login();
            changeFragment(fragment, true, "login");
            setNavigation();
        }else if(id == R.id.nav_signin){
            Fragment fragment = new Login();
            changeFragment(fragment, true, "login");
            setNavigation();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void removeSearch(){
        String lastFragment = session.getCustomParams("LastFragment", "nothing");
        switch (lastFragment){
            case "home":
                changeFragment(new HomeMain(), true, "home");
                break;
            case "kontak" :
                changeFragment(new MainKontak(), true, "kontak");
                break;
            case "login" :
                changeFragment(new Login(), true, "login");
                break;
            case "changePassword" :
                changeFragment(new ChangePassword(), true, "changePassword");
                break;
            default:
                break;
        }
    }
    private void changeFragment(Fragment fragment, Boolean backStack, String name){
        Fragment lastFragment = getSupportFragmentManager().findFragmentByTag("FragmentMain");
        if (lastFragment != null){
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment, "FragmentMain");
        ft.commit();
        if (backStack){
            session.setCustomParams("LastFragment", name);
        }
    }
    private void searchLayout(){
        Fragment fragment = new SearchMain();
        changeFragment(fragment, false, "");
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        session.setCustomParams("Query", s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        session.setCustomParams("Query", s);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onClose() {
        removeSearch();
        return false;
    }

    @Override
    public void sessionChange() {
        Boolean login = session.getCustomParams("LOGINSTATE", true);
        if (login){
            headersub.setVisibility(View.VISIBLE);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_signin);
        }else{
            headersub.setVisibility(View.GONE);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_signout);
        }
    }

}
