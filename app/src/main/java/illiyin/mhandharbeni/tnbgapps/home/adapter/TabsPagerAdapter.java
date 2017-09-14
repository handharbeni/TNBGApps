package illiyin.mhandharbeni.tnbgapps.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import illiyin.mhandharbeni.tnbgapps.home.fragment.ArsipFragment;
import illiyin.mhandharbeni.tnbgapps.home.fragment.HomeFragment;
import illiyin.mhandharbeni.tnbgapps.home.fragment.TrendFragment;

/**
 * Created by root on 9/5/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new HomeFragment();
            case 1:
                // Games fragment activity
                return new TrendFragment();
            case 2:
                // Movies fragment activity
                return new ArsipFragment();
        }

        return new HomeFragment();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Home";
        }
        else if (position == 1)
        {
            title = "Trend";
        }
        else if (position == 2)
        {
            title = "Arsip";
        }
        return title;
    }
}