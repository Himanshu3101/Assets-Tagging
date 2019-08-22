package com.assettagging.test.view.AddAssetLocation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.assettagging.test.R;
import com.assettagging.test.model.user_tracking.TrackingStatus_;
import com.assettagging.test.view.BaseActivity;
import com.assettagging.test.view.custom_control.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class LocationWiseProjectsActivity extends BaseActivity {
    String Id, Name;
    @BindView(R.id.recyclerViewData)
    RecyclerView recyclerViewData;
    @BindView(R.id.textViewNoData)
    TextView textViewNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_content);
        ButterKnife.bind(this);
        getIntentData();
//        getData();
    }

    private void getIntentData() {
//        Id = getIntent().getStringExtra(Constants.ID);
//        Name = getIntent().getStringExtra(Constants.NAME);
    }

//    private void getData() {
//        final String userId = Preferance.getUserId(this);
//        CustomProgress.startProgress(this);
//        Call<TrackingStatus_> call = MyApplication.apiInterface.GetProjectLocationWise(new UserProjectLocationWise(userId, Name));
//        call.enqueue(new Callback<TrackingStatus_>() {
//            @Override
//            public void onResponse(Call<TrackingStatus_> call, Response<TrackingStatus_> response) {
//                setResponse(response);
//                CustomProgress.endProgress();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<TrackingStatus_> call, Throwable t) {
//                setResponse(null);
//                CustomProgress.endProgress();
//
//            }
//        });
//    }

    private void setResponse(Response<TrackingStatus_> response) {
        if (response == null) {
            CustomToast.showToast(this, getResources().getString(R.string.something_bad_happened));
        } else {
            if (response.body().getStatus().equals("success")) {
                recyclerViewData.setVisibility(View.GONE);
                textViewNoData.setVisibility(View.VISIBLE);
///setAdapterhere
            } else {
                recyclerViewData.setVisibility(View.GONE);
                textViewNoData.setVisibility(View.VISIBLE);
                CustomToast.showToast(this, getResources().getString(R.string.something_bad_happened));
            }
        }
    }
}
