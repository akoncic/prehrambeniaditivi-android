package com.dekoraktiv.android.pa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dekoraktiv.android.pa.adapters.DynamicFragmentPagerAdapter;
import com.dekoraktiv.android.pa.constants.Extras;
import com.dekoraktiv.android.pa.fragments.AlertDialogFragment;
import com.dekoraktiv.android.pa.fragments.CommonFragment;
import com.dekoraktiv.android.pa.fragments.DescriptionFragment;
import com.dekoraktiv.android.pa.models.Additive;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private DynamicFragmentPagerAdapter mDynamicFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Additive additive = (Additive) getIntent()
                .getSerializableExtra(Extras.INTENT_EXTRA);

        setTheme(getThemeByRisk(additive.getRisk()));
        setTitle(additive.toString());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_layout);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(getColorByRisk(additive.getRisk())));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setBackgroundColor(getResources().getColor(getColorByRisk(additive.getRisk())));

        mDynamicFragmentPagerAdapter =
                new DynamicFragmentPagerAdapter(getSupportFragmentManager());

        addFragments(additive);

        viewPager.setAdapter(mDynamicFragmentPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();

            return true;
        } else if (id == R.id.action_glossary) {
            final AlertDialogFragment alertDialogFragment =
                    AlertDialogFragment.newInstance(getString(R.string.dialog_glossary_message));
            alertDialogFragment.show(getFragmentManager(), Extras.INTENT_EXTRA);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getThemeByRisk(int risk) {
        switch (risk) {
            case 1:
                return R.style.AppThemeNoActionBarLow;
            case 2:
                return R.style.AppThemeNoActionBarModerate;
            case 3:
                return R.style.AppThemeNoActionBarHigh;
            default:
                return R.style.AppThemeNoActionBarDefault;
        }
    }

    private int getColorByRisk(int risk) {
        switch (risk) {
            case 1:
                return R.color.risk_low;
            case 2:
                return R.color.risk_moderate;
            case 3:
                return R.color.risk_high;
            default:
                return R.color.primary;
        }
    }

    private void addFragments(Additive additive) {
        if (additive.getDescription() != null) {
            mDynamicFragmentPagerAdapter.add(DescriptionFragment.newInstance(additive),
                    getString(R.string.tab_title_description));
        }

        if (additive.getPermittedUse() != null) {
            mDynamicFragmentPagerAdapter.add(CommonFragment.newInstance(additive.getPermittedUse()),
                    getString(R.string.tab_title_permitted_use));
        }

        if (additive.getAdi() != null) {
            mDynamicFragmentPagerAdapter.add(CommonFragment.newInstance(additive.getAdi()),
                    getString(R.string.tab_title_adi));
        }
    }
}
