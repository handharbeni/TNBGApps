package illiyin.mhandharbeni.tnbgapps.akun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 10/3/17.
 */

public class MainAccount extends AppCompatActivity {
    private ImageView back;
    private TextView titleappbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_account);
        fetch_element();
        fill_element();

        Bundle bundleLogin = new Bundle();
        bundleLogin.putString("from", "class");
        Fragment fragment = new Login();
        fragment.setArguments(bundleLogin);
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
        titleappbar.setText("You Must Login");
    }
    private void change_fragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.commit();
    }
}
