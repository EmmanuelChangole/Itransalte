package com.translateit.itransalte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.translateit.itransalte.fragments.ChatFragment;
import com.translateit.itransalte.fragments.TranslateFragment;
import com.translateit.itransalte.utils.Firebase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private ViewPager viewPager;
    private Context context;
    private TabLayout tabLayout = null;
    private FloatingActionButton floatButton;
    private ViewPagerAdapter adapter;
    private Firebase firebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;

          getView();
          setView();
          initTab();
          initFirebase();




    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.onChangeState();



    }

    private void initFirebase()
    {
        firebase=new Firebase(context);
        firebase.initFirebase();

    }


    /*initialize the tab view*/
    private void initTab()
    {

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorIndivateTab));
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }
                /*get the view from xml.links xml views with java*/
    private void getView()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        floatButton = (FloatingActionButton) findViewById(R.id.fab);

    }
                     /*Set the toolbar view*/
    private void setView()
    {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Itranslate");
        }
    }
                      /*Sets the view pager*/
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ChatFragment(), "Chats");
        adapter.addFrag(new TranslateFragment(),"Translate");
        floatButton.setOnClickListener(((ChatFragment) adapter.getItem(0)).onClickFloatButton.getInstance(this));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int position)
            {
                if(adapter.getItem(position) instanceof TranslateFragment )
                {   floatButton.setVisibility(View.GONE);}
                else
                    {
                        floatButton.setVisibility(View.VISIBLE);
                        floatButton.setOnClickListener(((ChatFragment) adapter.getItem(position)).onClickFloatButton.getInstance(MainActivity.this));
                        floatButton.setImageResource(R.drawable.ic_add);
                    }




            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
                     /*sets the tab view icons*/
    private void setupTabIcons()
    {
        int[] tabIcons = {
                R.drawable.ic_message,
                R.drawable.ic_message,
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);



    }




    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }
}
