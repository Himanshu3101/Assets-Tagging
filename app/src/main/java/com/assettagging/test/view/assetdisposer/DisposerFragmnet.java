package com.assettagging.test.view.assetdisposer;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assettagging.test.R;
import com.assettagging.test.model.asset_disposal.CreatedDisposalList;
import com.assettagging.test.view.assetdisposer.completed.CompletedAssetsFragment;
import com.assettagging.test.view.assetdisposer.existing.ExistingAssetsFragment;
import com.assettagging.test.view.assetdisposer.yet_to_submit.YetToSubmitDisposerFragment;
import com.assettagging.test.view.navigation.NavigationActivity;
import com.assettagging.test.controller.DataBaseHelper;
import com.assettagging.test.model.login.ChangePassword;
import com.assettagging.test.view.schedule.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class DisposerFragmnet extends Fragment {

    @BindView(R.id.tabs)
    public
    TabLayout tabLayout;
    @BindView(R.id.pager)
    public
    ViewPager pager;
    public static DisposerFragmnet instance;


    private ScheduleAdapter scheduleAdapter;
    private Dialog dialogChangePassword;
    Call<ChangePassword> call;
    public static int position = 0;
    private DataBaseHelper dataBaseHelper;
    private DisoserViewPagerAdapter adapter;

    View view;
    private boolean firstTime = false;
    public List<CreatedDisposalList> CompledeDisposalAssetList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_schedule, container, false);
        ButterKnife.bind(this, view);
        instance = this;
        dataBaseHelper = new DataBaseHelper(getActivity());
        setupViewPager();
        tabLayout.setupWithViewPager(DisposerFragmnet.getInstance().pager);
        return view;
    }


    public void setupViewPager() {

        adapter = null;
        adapter = new DisoserViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new YetToSubmitDisposerFragment(), getString(R.string.yetToStarted));
        adapter.addFragment(new ExistingAssetsFragment(), getString(R.string.existing));
        adapter.addFragment(new CompletedAssetsFragment(), getString(R.string.Completed));
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);

        if (position == 0) {
            NavigationActivity.getInstance().menuitem.setVisible(true);
            NavigationActivity.getInstance().menuitemfilter.setVisible(true);
            pager.setCurrentItem(0, true);
        } else if (position == 1) {
            NavigationActivity.getInstance().menuitem.setVisible(false);
            NavigationActivity.getInstance().menuitemfilter.setVisible(true);
            pager.setCurrentItem(1, true);
        } else if (position == 2) {
            NavigationActivity.getInstance().menuitem.setVisible(false);
            NavigationActivity.getInstance().menuitemfilter.setVisible(true);
            pager.setCurrentItem(2, true);
        }
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                position = i;
                if (position == 0) {
                    NavigationActivity.getInstance().menuitem.setVisible(true);
                    NavigationActivity.getInstance().menuitemfilter.setVisible(true);
                    pager.setCurrentItem(0, true);
                } else if (position == 1) {
                    NavigationActivity.getInstance().menuitem.setVisible(false);
                    NavigationActivity.getInstance().menuitemfilter.setVisible(true);
                    pager.setCurrentItem(1, true);
                } else if (position == 2) {
                    NavigationActivity.getInstance().menuitem.setVisible(false);
                    NavigationActivity.getInstance().menuitemfilter.setVisible(true);
                    pager.setCurrentItem(2, true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    public static DisposerFragmnet getInstance() {
        return instance;
    }


}
