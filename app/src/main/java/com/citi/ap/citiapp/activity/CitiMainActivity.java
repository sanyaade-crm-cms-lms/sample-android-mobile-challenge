package com.citi.ap.citiapp.activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.anypresence.sdk.citi.models.LoginInfo;
import com.citi.ap.citiapp.R;
import com.citi.ap.citiapp.fragment.BaseContainerFragment;
import com.citi.ap.citiapp.fragment.container.Tab1Container;
import com.citi.ap.citiapp.fragment.container.Tab2Container;
import com.citi.ap.citiapp.fragment.container.Tab3Container;

public class CitiMainActivity extends FragmentActivity {

    private static final String TAB_1_TAG = "tab_1";
    private static final String TAB_2_TAG = "tab_2";
    private static final String TAB_3_TAG = "tab_3";

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citi_main);
        Bundle extras = getIntent().getExtras();
        LoginInfo loginInfo = (LoginInfo) extras.getSerializable("loginInfo");
        initView();
    }

    private void initView() {
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);


        mTabHost.addTab(setIndicator(CitiMainActivity.this, mTabHost.newTabSpec(TAB_1_TAG),
                R.drawable.tab_indicator_gen, "Account", R.drawable.account),Tab1Container.class,null);
        mTabHost.addTab(setIndicator(CitiMainActivity.this,mTabHost.newTabSpec(TAB_2_TAG),
                R.drawable.tab_indicator_gen,"Payee",R.drawable.gear),Tab2Container.class,null);
        mTabHost.addTab(setIndicator(CitiMainActivity.this,mTabHost.newTabSpec(TAB_3_TAG),
                R.drawable.tab_indicator_gen,"Txf Funds",R.drawable.txf),Tab3Container.class,null);
    }

    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        String currentTabTag = mTabHost.getCurrentTabTag();

        if (currentTabTag.equals(TAB_1_TAG)) {
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_1_TAG)).popFragment();
        } else if (currentTabTag.equals(TAB_2_TAG)) {
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_2_TAG)).popFragment();
        }
        else if (currentTabTag.equals(TAB_3_TAG)) {
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag(TAB_3_TAG)).popFragment();
        }

        if (!isPopFragment) {
            finish();
        }
    }

    private TabHost.TabSpec setIndicator(Context ctx, TabHost.TabSpec spec,
                                 int resid, String string, int genresIcon) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.tab_item, null);
        v.setBackgroundResource(resid);
        TextView tv = (TextView)v.findViewById(R.id.txt_tabtxt);
        ImageView img = (ImageView)v.findViewById(R.id.img_tabtxt);

        tv.setText(string);
        img.setBackgroundResource(genresIcon);
        return spec.setIndicator(v);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_citi_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
