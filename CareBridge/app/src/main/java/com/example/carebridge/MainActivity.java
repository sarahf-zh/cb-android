package com.example.carebridge;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageButton backButton;
    private ImageButton menuButton;
    private TextView mtitleTxt;

    private static final String TAG = "MainActivity2";

    public ImageButton getBackButton() {
        return backButton;
    }
    public ImageButton getMenuButton() {
        return menuButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtitleTxt = (TextView)findViewById(R.id.titleTxt);
        backButton = (ImageButton)findViewById(R.id.btnBack);
        menuButton = (ImageButton)findViewById(R.id.btnMenu);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            if (i==0) {
                tabLayout.getTabAt(i).setIcon(R.drawable.diagnosis /*.assestment_tab*/);
             } else if (i==1) {
                tabLayout.getTabAt(i).setIcon(R.drawable.pharmacy /*.profile_tab*/);
            } else if (i==2) {
                tabLayout.getTabAt(i).setIcon(R.drawable.hospital /*.profile_tab*/);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelection(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabSelection(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    private void changeTabSelection(TabLayout.Tab tab, boolean selected) {
        /*
        // Does not work with the new set of icons!!
        int color = selected ? R.color.tabSelectedColor : R.color.tabUnSelectedColor;
        int tabIconColor = ContextCompat.getColor(MainActivity.this, color);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        */
        if (selected) {
            int pos = tab.getPosition();
            mtitleTxt.setText(pos == 0? "Medical Help": (pos == 1? "Online Pharmacy" : "Nearby Care Provider"));
            backButton.setVisibility(pos == 1 ? View.VISIBLE : View.INVISIBLE);
            menuButton.setVisibility(pos == 1 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0:
                    DiagnosisTab tab1 = new DiagnosisTab();
                    return tab1;
                case 1:
                    PharmacyTab tab2 = new PharmacyTab();
                    return tab2;
                case 2:
                    HospitalTab tab3 = new HospitalTab();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            //return 2;
            return 3;
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {}

        @Override
        public void onPageSelected(int i) {}

        @Override
        public void onPageScrollStateChanged(int i) {}
    }
}
