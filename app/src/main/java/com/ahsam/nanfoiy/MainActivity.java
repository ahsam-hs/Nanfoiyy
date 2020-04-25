package com.ahsam.nanfoiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.ahsam.nanfoiy.FragmentActivity.BoysFragment;
import com.ahsam.nanfoiy.FragmentActivity.GirlsFragment;
import com.ahsam.nanfoiy.FragmentActivity.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity {

    final Fragment boysFragment = new BoysFragment();
    final Fragment girlsFragment = new GirlsFragment();
    final Fragment infoFragment = new InfoFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = boysFragment;
    LinearLayout letterLayout;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_boys:
                    fm.beginTransaction()
                            .hide(active).show(boysFragment).commit();
                    active = boysFragment;
                    return true;

                case R.id.navigation_girls:
                    fm.beginTransaction()
//                            .setCustomAnimations(
//                                    R.animator.card_flip_right_in,
//                                    R.animator.card_flip_right_out,
//                                    R.animator.card_flip_left_in,
//                                    R.animator.card_flip_left_out
//                            )
                            .hide(active).show(girlsFragment).commit();
                    active = girlsFragment;
                    return true;

                case R.id.navigation_info:
                    fm.beginTransaction()
                            .hide(active).show(infoFragment).commit();
                    active = infoFragment;
                    return true;

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Instantiate a ViewPager2 and a PagerAdapter.
//        viewPager = findViewById(R.id.pager);
//        pagerAdapter = new ScreenSlidePagerAdapter(this);
//        viewPager.setAdapter(pagerAdapter);

        fm.beginTransaction().add(R.id.fragmentcontainer, infoFragment,"3").hide(infoFragment).commit();
        fm.beginTransaction().add(R.id.fragmentcontainer, girlsFragment,"2").hide(girlsFragment).commit();
        fm.beginTransaction().add(R.id.fragmentcontainer, boysFragment,"1").commit();

        letterLayout = findViewById(R.id.letters);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

//    @Override
//    public void onBackPressed() {
//        if (viewPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }

//    /**
//     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
//     * sequence.
//     */
//    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
//        public ScreenSlidePagerAdapter(FragmentActivity fa) {
//            super(fa);
//        }
//
//        @Override
//        public Fragment createFragment(int position) {
//            return new BoysFragment();
//        }
//
//        @Override
//        public int getItemCount() {
//            return NUM_PAGES;
//        }
//    }


}
