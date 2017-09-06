package illiyin.mhandharbeni.tnbgapps;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.home.HomeMain;
import illiyin.mhandharbeni.tnbgapps.kontak.MainKontak;
import illiyin.mhandharbeni.tnbgapps.search.SearchMain;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SessionListener {

    private Session session;
    private NavigationView navigationView;
    private SearchView searchView;
    private Menu menuSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext(), this);
        session.setCustomParams("LastFragment", "home");
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
        Boolean login = true;
        if (login){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_signin);
        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_signout);
        }
        setFirstItemNavigationView();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            collapseSearchView();
            Fragment fragment = new HomeMain();
            changeFragment(fragment, true, "home");
        }else if (id == R.id.nav_kontak){
            collapseSearchView();
            Fragment fragment = new MainKontak();
            changeFragment(fragment, true, "kontak");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void collapseSearchView(){
    }
    private void removeSearch(){
        String lastFragment = session.getCustomParams("LastFragment", "nothing");
        switch (lastFragment){
            case "home":
                changeFragment(new HomeMain(), true, "home");
                break;
            case "kontak" :
                changeFragment(new MainKontak(), true, "kontak");
            default:
                break;
        }
    }
    private void changeFragment(Fragment fragment, Boolean backStack, String name){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
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

    }
}
