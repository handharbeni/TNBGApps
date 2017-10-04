package illiyin.mhandharbeni.tnbgapps.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import illiyin.mhandharbeni.sessionlibrary.Session;
import illiyin.mhandharbeni.sessionlibrary.SessionListener;
import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.akun.Login;

/**
 * Created by root on 10/4/17.
 */

public class SearchClass extends AppCompatActivity {
    private ImageView back;
    private TextView titleappbar;
    private String trending;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(getApplicationContext(), new SessionListener() {
            @Override
            public void sessionChange() {

            }
        });

        Intent intent = getIntent();
        trending = intent.getStringExtra("trending");

        setContentView(R.layout.layout_main_search);
        fetch_element();
        fill_element();

        session.setCustomParams("Query", trending);

        Bundle bundleSearch = new Bundle();
        bundleSearch.putString("from", "class");
        Fragment fragment = new SearchMain();
        fragment.setArguments(bundleSearch);
        change_fragment(fragment);
    }
    private void fetch_element(){
        back = findViewById(R.id.back);
        titleappbar = findViewById(R.id.titleappbar);
    }
    private void fill_element(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleappbar.setText("Tag ");
    }
    private void change_fragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.commit();
    }
}
