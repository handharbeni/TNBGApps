package illiyin.mhandharbeni.tnbgapps.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import illiyin.mhandharbeni.tnbgapps.R;
import illiyin.mhandharbeni.tnbgapps.home.adapter.TabsPagerAdapter;

/**
 * Created by root on 9/5/17.
 */

public class HomeMain extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fab;
    private TabsPagerAdapter mAdapter;
    private TabLayoutHelper mTabLayoutHelper;

    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_main, container, false);
        mAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = v.findViewById(R.id.pager);
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager.setAdapter(mAdapter);
        mTabLayoutHelper = new TabLayoutHelper(tabLayout, viewPager);
        mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.blueCustom));
        tabLayout.setTabTextColors(getResources().getColor(R.color.greyCustom), getResources().getColor(R.color.blueCustom));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
        return v;

    }
}
